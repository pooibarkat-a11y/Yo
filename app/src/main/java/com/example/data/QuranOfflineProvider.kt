package com.example.data

import android.content.Context
import android.util.Log
import com.example.model.Ayah
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.concurrent.ConcurrentHashMap

/**
 * Offline Quran Provider:
 * Loads the complete 114 Surahs with authentic Uthmani Quranic text and Tafsir Al-Muyassar
 * from bundled offline assets without requiring any internet connection.
 */
object QuranOfflineProvider {
    private const val TAG = "QuranOfflineProvider"
    private const val ASSET_FILE = "quran_offline.json"

    private val surahMap = ConcurrentHashMap<Int, List<Ayah>>()
    @Volatile
    private var isInitialized = false
    @Volatile
    private var isFullyLoaded = false
    @Volatile
    private var rootJson: JSONObject? = null
    private var appContext: Context? = null

    fun init(context: Context) {
        appContext = context.applicationContext
        if (isInitialized) return
        isInitialized = true

        CoroutineScope(Dispatchers.IO).launch {
            loadAllFromAssets(context.applicationContext)
        }
    }

    private fun getRootJsonObject(context: Context): JSONObject? {
        rootJson?.let { return it }
        synchronized(this) {
            rootJson?.let { return it }
            return try {
                val jsonString = readAssetString(context, ASSET_FILE) ?: return null
                val obj = JSONObject(jsonString)
                rootJson = obj
                obj
            } catch (e: Exception) {
                Log.e(TAG, "Failed parsing root JSON: ${e.message}", e)
                null
            }
        }
    }

    private fun parseSurahJsonArray(surahId: Int, ayahsArray: org.json.JSONArray): List<Ayah> {
        val list = ArrayList<Ayah>(ayahsArray.length())
        for (i in 0 until ayahsArray.length()) {
            val obj = ayahsArray.getJSONObject(i)
            val ayahNum = obj.getInt("n")
            var text = obj.getString("t").replace("\uFEFF", "").trim()
            val tafsir = obj.optString("tf", "")
            val page = obj.optInt("p", 1)
            val juz = obj.optInt("j", 1)

            val formattedText = if (!text.contains("۝")) {
                "$text ۝$ayahNum"
            } else {
                text
            }

            list.add(
                Ayah(
                    surahId = surahId,
                    ayahNumber = ayahNum,
                    textUthmani = formattedText,
                    translation = "Verse $ayahNum",
                    tafsir = if (tafsir.isNotBlank()) tafsir else "التفسير الميسر للآية الكريمة المباركة",
                    pageNumber = page,
                    juzNumber = juz
                )
            )
        }
        return list
    }

    private fun loadAllFromAssets(context: Context) {
        try {
            val root = getRootJsonObject(context) ?: return
            val keys = root.keys()
            while (keys.hasNext()) {
                val key = keys.next()
                val surahId = key.toIntOrNull() ?: continue
                if (!surahMap.containsKey(surahId)) {
                    val ayahsArray = root.getJSONArray(key)
                    val list = parseSurahJsonArray(surahId, ayahsArray)
                    surahMap[surahId] = list
                }
            }
            isFullyLoaded = true
            Log.d(TAG, "Successfully pre-loaded all ${surahMap.size} surahs offline from assets")
        } catch (e: Exception) {
            Log.e(TAG, "Error loading offline Quran from assets: ${e.message}", e)
        }
    }

    private fun readAssetString(context: Context, filename: String): String? {
        return try {
            context.assets.open(filename).use { inputStream ->
                BufferedReader(InputStreamReader(inputStream, Charsets.UTF_8)).use { reader ->
                    reader.readText()
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed reading asset $filename: ${e.message}")
            null
        }
    }

    fun isSurahAvailable(surahId: Int): Boolean {
        return surahMap.containsKey(surahId) || QuranOfflineSurahs.offlineSurahs.containsKey(surahId) || rootJson?.has(surahId.toString()) == true
    }

    fun getSurah(context: Context?, surahId: Int): List<Ayah> {
        // 1. Return in-memory cached surah if loaded
        surahMap[surahId]?.let { return it }

        // 2. Immediate on-demand parse from root JSON
        val ctx = context ?: appContext
        if (ctx != null) {
            try {
                val root = getRootJsonObject(ctx)
                if (root != null && root.has(surahId.toString())) {
                    val ayahsArray = root.getJSONArray(surahId.toString())
                    val list = parseSurahJsonArray(surahId, ayahsArray)
                    surahMap[surahId] = list
                    return list
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching surah $surahId from asset: ${e.message}", e)
            }
        }

        // 3. Fallback to hardcoded QuranOfflineSurahs if present
        QuranOfflineSurahs.offlineSurahs[surahId]?.let { return it }

        return emptyList()
    }

    /**
     * Retrieves all verses with their authentic text and Tafsir Al-Muyassar for a specific physical page (1-604).
     */
    fun getAyahsForPage(context: Context?, page: Int): List<Ayah> {
        val clampedPage = page.coerceIn(1, QuranPagesManager.TOTAL_PAGES)
        val mainSurah = QuranPagesManager.getPageSurah(clampedPage)
        val startSurah = (mainSurah.id - 1).coerceAtLeast(1)
        val endSurah = (mainSurah.id + 3).coerceAtMost(114)

        val result = mutableListOf<Ayah>()
        for (sId in startSurah..endSurah) {
            val list = getSurah(context, sId)
            val matching = list.filter { it.pageNumber == clampedPage }
            result.addAll(matching)
        }
        return result
    }
}
