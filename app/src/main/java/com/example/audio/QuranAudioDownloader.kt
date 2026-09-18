package com.example.audio

import android.content.Context
import android.util.Log
import com.example.data.QuranData
import com.example.model.Reciter
import com.example.model.Surah
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

data class DownloadedSurahItem(
    val reciterId: String,
    val reciterNameArabic: String,
    val surahId: Int,
    val surahNameArabic: String,
    val ayahsCount: Int,
    val totalSizeBytes: Long
)

object QuranAudioDownloader {
    private const val TAG = "QuranAudioDownloader"
    private var appContext: Context? = null
    private val scope = CoroutineScope(Dispatchers.IO)
    private var currentDownloadJob: Job? = null

    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    // Key: "${reciterId}_${surahId}" -> Progress 0..100
    private val _downloadProgress = MutableStateFlow<Map<String, Int>>(emptyMap())
    val downloadProgress: StateFlow<Map<String, Int>> = _downloadProgress.asStateFlow()

    // Set of currently downloading keys
    private val _activeDownloads = MutableStateFlow<Set<String>>(emptySet())
    val activeDownloads: StateFlow<Set<String>> = _activeDownloads.asStateFlow()

    // Set of fully downloaded keys
    private val _downloadedSurahsSet = MutableStateFlow<Set<String>>(emptySet())
    val downloadedSurahsSet: StateFlow<Set<String>> = _downloadedSurahsSet.asStateFlow()

    fun init(context: Context) {
        if (appContext != null) return
        appContext = context.applicationContext
        refreshDownloadedSurahs()
    }

    private fun getAudioBaseDir(): File? {
        val ctx = appContext ?: return null
        val dir = File(ctx.getExternalFilesDir(null) ?: ctx.filesDir, "quran_offline_audio")
        if (!dir.exists()) dir.mkdirs()
        return dir
    }

    private fun getSurahDir(reciterId: String, surahId: Int): File? {
        val base = getAudioBaseDir() ?: return null
        val s = surahId.toString().padStart(3, '0')
        val dir = File(base, "$reciterId/$s")
        if (!dir.exists()) dir.mkdirs()
        return dir
    }

    fun makeKey(reciterId: String, surahId: Int): String = "${reciterId}_$surahId"

    fun isSurahDownloaded(reciterId: String, surahId: Int): Boolean {
        val surah = QuranData.surahs.find { it.id == surahId } ?: return false
        val dir = getSurahDir(reciterId, surahId) ?: return false
        if (!dir.exists()) return false

        val reciter = QuranData.reciters.find { it.id == reciterId }
        val s = surahId.toString().padStart(3, '0')

        if (reciter?.isVerseByVerse == false) {
            val file = File(dir, "$s.mp3")
            return file.exists() && file.length() > 1024
        }

        // Check if all ayahs exist and have non-zero size
        for (a in 1..surah.versesCount) {
            val aPad = a.toString().padStart(3, '0')
            val file = File(dir, "$s$aPad.mp3")
            if (!file.exists() || file.length() < 1024) {
                return false
            }
        }
        return true
    }

    fun getLocalAyahFile(reciterId: String, surahId: Int, ayahNumber: Int = 1): File? {
        val dir = getSurahDir(reciterId, surahId) ?: return null
        val reciter = QuranData.reciters.find { it.id == reciterId }
        val s = surahId.toString().padStart(3, '0')

        if (reciter?.isVerseByVerse == false) {
            val file = File(dir, "$s.mp3")
            return if (file.exists() && file.length() > 1024) file else null
        }

        val a = ayahNumber.toString().padStart(3, '0')
        val file = File(dir, "$s$a.mp3")
        return if (file.exists() && file.length() > 1024) file else null
    }

    fun getDownloadedAyahsCount(reciterId: String, surahId: Int): Int {
        val surah = QuranData.surahs.find { it.id == surahId } ?: return 0
        val dir = getSurahDir(reciterId, surahId) ?: return 0
        if (!dir.exists()) return 0

        val s = surahId.toString().padStart(3, '0')
        var count = 0
        for (a in 1..surah.versesCount) {
            val aPad = a.toString().padStart(3, '0')
            val file = File(dir, "$s$aPad.mp3")
            if (file.exists() && file.length() > 1024) {
                count++
            }
        }
        return count
    }

    fun refreshDownloadedSurahs() {
        scope.launch {
            val base = getAudioBaseDir() ?: return@launch
            val fullyDownloaded = mutableSetOf<String>()
            for (reciter in QuranData.reciters) {
                for (surah in QuranData.surahs) {
                    if (isSurahDownloaded(reciter.id, surah.id)) {
                        fullyDownloaded.add(makeKey(reciter.id, surah.id))
                    }
                }
            }
            _downloadedSurahsSet.value = fullyDownloaded
        }
    }

    fun downloadSurah(
        reciter: Reciter,
        surah: Surah,
        onProgressUpdate: ((progress: Int) -> Unit)? = null,
        onCompletion: ((Boolean) -> Unit)? = null
    ) {
        val key = makeKey(reciter.id, surah.id)
        if (_activeDownloads.value.contains(key)) return

        _activeDownloads.value = _activeDownloads.value + key
        updateProgress(key, 0)

        if (!reciter.isVerseByVerse) {
            currentDownloadJob = scope.launch {
                val dir = getSurahDir(reciter.id, surah.id)
                if (dir == null) {
                    _activeDownloads.value = _activeDownloads.value - key
                    onCompletion?.invoke(false)
                    return@launch
                }
                val sPad = surah.id.toString().padStart(3, '0')
                val targetFile = File(dir, "$sPad.mp3")
                updateProgress(key, 20)
                val url = QuranData.getAudioUrl(reciter, surah.id, 1)
                val success = downloadFile(url, targetFile)
                _activeDownloads.value = _activeDownloads.value - key
                if (success && targetFile.exists() && targetFile.length() > 1024) {
                    _downloadedSurahsSet.value = _downloadedSurahsSet.value + key
                    updateProgress(key, 100)
                    Log.d(TAG, "Full Surah ${surah.nameArabic} downloaded successfully for ${reciter.nameArabic}")
                    onCompletion?.invoke(true)
                } else {
                    onCompletion?.invoke(false)
                }
            }
            return
        }

        currentDownloadJob = scope.launch {
            var allSuccess = true
            val dir = getSurahDir(reciter.id, surah.id)
            if (dir == null) {
                _activeDownloads.value = _activeDownloads.value - key
                onCompletion?.invoke(false)
                return@launch
            }

            val total = surah.versesCount
            val sPad = surah.id.toString().padStart(3, '0')

            for (a in 1..total) {
                val aPad = a.toString().padStart(3, '0')
                val targetFile = File(dir, "$sPad$aPad.mp3")

                if (!targetFile.exists() || targetFile.length() < 1024) {
                    val url = QuranData.getAudioUrl(reciter, surah.id, a)
                    val success = downloadFile(url, targetFile)
                    if (!success) {
                        allSuccess = false
                        Log.e(TAG, "Failed downloading ayah $a of surah ${surah.id} for reciter ${reciter.nameAr}")
                        break
                    }
                }

                val percent = ((a.toFloat() / total) * 100).toInt().coerceIn(0, 100)
                updateProgress(key, percent)
                onProgressUpdate?.invoke(percent)
            }

            _activeDownloads.value = _activeDownloads.value - key

            if (allSuccess && isSurahDownloaded(reciter.id, surah.id)) {
                _downloadedSurahsSet.value = _downloadedSurahsSet.value + key
                updateProgress(key, 100)
                Log.d(TAG, "Surah ${surah.nameArabic} (${surah.id}) downloaded successfully for ${reciter.nameArabic}")
                onCompletion?.invoke(true)
            } else {
                onCompletion?.invoke(false)
            }
        }
    }

    private fun downloadFile(url: String, destination: File): Boolean {
        val tempFile = File(destination.parentFile, "${destination.name}.tmp")
        try {
            val request = Request.Builder().url(url).build()
            val response = httpClient.newCall(request).execute()
            if (!response.isSuccessful) {
                response.close()
                return false
            }

            val body = response.body ?: return false
            body.byteStream().use { input ->
                FileOutputStream(tempFile).use { output ->
                    input.copyTo(output)
                }
            }
            response.close()

            if (tempFile.exists() && tempFile.length() > 512) {
                if (destination.exists()) destination.delete()
                return tempFile.renameTo(destination)
            }
            return false
        } catch (e: Exception) {
            Log.e(TAG, "Error downloading $url: ${e.message}")
            if (tempFile.exists()) tempFile.delete()
            return false
        }
    }

    fun cancelDownload(reciterId: String, surahId: Int) {
        val key = makeKey(reciterId, surahId)
        if (_activeDownloads.value.contains(key)) {
            currentDownloadJob?.cancel()
            _activeDownloads.value = _activeDownloads.value - key
            val map = _downloadProgress.value.toMutableMap()
            map.remove(key)
            _downloadProgress.value = map
        }
    }

    fun deleteSurah(reciterId: String, surahId: Int): Boolean {
        val dir = getSurahDir(reciterId, surahId) ?: return false
        val key = makeKey(reciterId, surahId)
        val success = if (dir.exists()) {
            dir.deleteRecursively()
        } else true

        val downloaded = _downloadedSurahsSet.value.toMutableSet()
        downloaded.remove(key)
        _downloadedSurahsSet.value = downloaded

        val map = _downloadProgress.value.toMutableMap()
        map.remove(key)
        _downloadProgress.value = map

        return success
    }

    private fun updateProgress(key: String, progress: Int) {
        val map = _downloadProgress.value.toMutableMap()
        map[key] = progress
        _downloadProgress.value = map
    }

    fun getTotalStorageUsedMb(): Float {
        val base = getAudioBaseDir() ?: return 0f
        return calculateDirSizeBytes(base) / (1024f * 1024f)
    }

    fun getDownloadedAudioSizeBytes(): Long {
        val base = getAudioBaseDir() ?: return 0L
        return calculateDirSizeBytes(base)
    }

    fun clearAllDownloads(): Boolean {
        val base = getAudioBaseDir() ?: return false
        val success = if (base.exists()) base.deleteRecursively() else true
        _downloadedSurahsSet.value = emptySet()
        _downloadProgress.value = emptyMap()
        return success
    }

    private fun calculateDirSizeBytes(dir: File): Long {
        if (!dir.exists()) return 0L
        var total = 0L
        val files = dir.listFiles() ?: return 0L
        for (f in files) {
            total += if (f.isDirectory) calculateDirSizeBytes(f) else f.length()
        }
        return total
    }

    fun getDownloadedSurahsList(): List<DownloadedSurahItem> {
        val list = mutableListOf<DownloadedSurahItem>()
        for (reciter in QuranData.reciters) {
            for (surah in QuranData.surahs) {
                if (isSurahDownloaded(reciter.id, surah.id)) {
                    val dir = getSurahDir(reciter.id, surah.id)
                    val size = if (dir != null) calculateDirSizeBytes(dir) else 0L
                    list.add(
                        DownloadedSurahItem(
                            reciterId = reciter.id,
                            reciterNameArabic = reciter.nameArabic,
                            surahId = surah.id,
                            surahNameArabic = surah.nameArabic,
                            ayahsCount = surah.versesCount,
                            totalSizeBytes = size
                        )
                    )
                }
            }
        }
        return list
    }
}
