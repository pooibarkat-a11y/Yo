package com.example.audio

import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.TimeUnit

object NasheedDownloader {
    private const val TAG = "NasheedDownloader"
    private val scope = CoroutineScope(Dispatchers.IO)

    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .build()

    // Map of nasheedId -> download progress percentage (0..100)
    private val _downloadProgress = MutableStateFlow<Map<String, Int>>(emptyMap())
    val downloadProgress: StateFlow<Map<String, Int>> = _downloadProgress.asStateFlow()

    // Set of currently downloading nasheed IDs
    private val _activeDownloads = MutableStateFlow<Set<String>>(emptySet())
    val activeDownloads: StateFlow<Set<String>> = _activeDownloads.asStateFlow()

    // Set of downloaded nasheed IDs
    private val _downloadedIds = MutableStateFlow<Set<String>>(emptySet())
    val downloadedIds: StateFlow<Set<String>> = _downloadedIds.asStateFlow()

    fun init(context: Context) {
        refreshDownloaded(context)
    }

    fun getNasheedsDir(context: Context): File {
        val baseDir = context.getExternalFilesDir(null) ?: context.filesDir
        val nasheedsDir = File(baseDir, "downloaded_nasheeds")
        if (!nasheedsDir.exists()) {
            nasheedsDir.mkdirs()
        }
        return nasheedsDir
    }

    fun isDownloaded(context: Context, id: String): Boolean {
        val file = File(getNasheedsDir(context), "$id.mp3")
        return file.exists() && file.length() > 50_000
    }

    fun getLocalFile(context: Context, id: String): File? {
        val file = File(getNasheedsDir(context), "$id.mp3")
        return if (file.exists() && file.length() > 50_000) file else null
    }

    fun refreshDownloaded(context: Context) {
        scope.launch {
            val dir = getNasheedsDir(context)
            val files = dir.listFiles { f -> f.extension == "mp3" && f.length() > 50_000 }
            val ids = files?.map { it.nameWithoutExtension }?.toSet() ?: emptySet()
            _downloadedIds.value = ids
        }
    }

    fun downloadNasheed(context: Context, id: String, audioUrl: String, title: String) {
        if (_activeDownloads.value.contains(id) || isDownloaded(context, id)) return

        scope.launch {
            _activeDownloads.value = _activeDownloads.value + id
            _downloadProgress.value = _downloadProgress.value + (id to 0)

            val dir = getNasheedsDir(context)
            val tempFile = File(dir, "$id.tmp")
            val targetFile = File(dir, "$id.mp3")

            try {
                val request = Request.Builder()
                    .url(audioUrl)
                    .addHeader("User-Agent", "Mozilla/5.0 (Android; Mobile)")
                    .build()

                val response = httpClient.newCall(request).execute()
                if (!response.isSuccessful || response.body == null) {
                    Log.e(TAG, "Failed download for $title: code ${response.code}")
                    _activeDownloads.value = _activeDownloads.value - id
                    _downloadProgress.value = _downloadProgress.value - id
                    return@launch
                }

                val body = response.body!!
                val contentLength = body.contentLength()
                val inputStream = body.byteStream()
                val outputStream = FileOutputStream(tempFile)

                val buffer = ByteArray(8 * 1024)
                var bytesRead: Int
                var totalBytesRead: Long = 0

                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                    totalBytesRead += bytesRead
                    if (contentLength > 0) {
                        val progress = ((totalBytesRead * 100) / contentLength).toInt().coerceIn(0, 100)
                        _downloadProgress.value = _downloadProgress.value + (id to progress)
                    }
                }

                outputStream.flush()
                outputStream.close()
                inputStream.close()

                if (tempFile.exists() && tempFile.length() > 50_000) {
                    if (targetFile.exists()) targetFile.delete()
                    tempFile.renameTo(targetFile)
                    _downloadedIds.value = _downloadedIds.value + id
                } else {
                    tempFile.delete()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error downloading nasheed $id", e)
                if (tempFile.exists()) tempFile.delete()
            } finally {
                _activeDownloads.value = _activeDownloads.value - id
                _downloadProgress.value = _downloadProgress.value - id
            }
        }
    }

    fun deleteNasheed(context: Context, id: String) {
        scope.launch {
            val file = File(getNasheedsDir(context), "$id.mp3")
            if (file.exists()) {
                file.delete()
            }
            _downloadedIds.value = _downloadedIds.value - id
        }
    }
}
