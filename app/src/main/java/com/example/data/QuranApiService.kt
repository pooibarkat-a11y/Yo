package com.example.data

import android.util.Log
import com.example.model.Ayah
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

object QuranApiService {
    private const val TAG = "QuranApiService"
    private const val BASE_URL = "https://api.alquran.cloud/v1/surah"

    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .build()

    // In-memory cache for fetched surahs
    private val surahCache = ConcurrentHashMap<Int, List<Ayah>>()

    fun getCachedSurah(surahId: Int): List<Ayah>? = surahCache[surahId]

    fun putInCache(surahId: Int, ayahs: List<Ayah>) {
        surahCache[surahId] = ayahs
    }

    fun clearCache() {
        surahCache.clear()
    }

    suspend fun fetchSurahWithTafsir(surahId: Int): Result<List<Ayah>> = withContext(Dispatchers.IO) {
        // Return from cache if already loaded
        surahCache[surahId]?.let {
            return@withContext Result.success(it)
        }

        try {
            val url = "$BASE_URL/$surahId/editions/quran-uthmani,ar.muyassar"
            val request = Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .build()

            val response = client.newCall(request).execute()
            if (!response.isSuccessful) {
                return@withContext Result.failure(Exception("HTTP ${response.code}: ${response.message}"))
            }

            val bodyString = response.body?.string() ?: ""
            val json = JSONObject(bodyString)
            val code = json.optInt("code", 0)
            if (code != 200) {
                return@withContext Result.failure(Exception("API returned code $code"))
            }

            val dataArray = json.getJSONArray("data")
            if (dataArray.length() < 2) {
                return@withContext Result.failure(Exception("Incomplete editions in response"))
            }

            // Find quran-uthmani edition and ar.muyassar edition
            var uthmaniObj: JSONObject? = null
            var tafsirObj: JSONObject? = null

            for (i in 0 until dataArray.length()) {
                val edObj = dataArray.getJSONObject(i)
                val editionMeta = edObj.optJSONObject("edition")
                val id = editionMeta?.optString("identifier", "") ?: ""
                if (id == "quran-uthmani") {
                    uthmaniObj = edObj
                } else if (id == "ar.muyassar") {
                    tafsirObj = edObj
                }
            }

            // Fallback by index if identifiers not matched
            if (uthmaniObj == null) uthmaniObj = dataArray.getJSONObject(0)
            if (tafsirObj == null) tafsirObj = dataArray.getJSONObject(1)

            val uthmaniAyahs = uthmaniObj.getJSONArray("ayahs")
            val tafsirAyahs = tafsirObj.getJSONArray("ayahs")

            val tafsirMap = mutableMapOf<Int, String>()
            for (i in 0 until tafsirAyahs.length()) {
                val tAyah = tafsirAyahs.getJSONObject(i)
                val numInSurah = tAyah.optInt("numberInSurah", i + 1)
                val text = tAyah.optString("text", "")
                tafsirMap[numInSurah] = text
            }

            val resultList = mutableListOf<Ayah>()
            for (i in 0 until uthmaniAyahs.length()) {
                val uAyah = uthmaniAyahs.getJSONObject(i)
                val ayahNumber = uAyah.optInt("numberInSurah", i + 1)
                var uthmaniText = uAyah.optString("text", "")
                val page = uAyah.optInt("page", 1)
                val juz = uAyah.optInt("juz", 1)

                // Clean UTF BOM and trim
                uthmaniText = uthmaniText.replace("\uFEFF", "").trim()

                // Add traditional Quranic end-of-ayah marker symbol if not present
                val formattedText = if (!uthmaniText.contains("۝")) {
                    "$uthmaniText ۝$ayahNumber"
                } else {
                    uthmaniText
                }

                val tafsirText = tafsirMap[ayahNumber] ?: "تفسير الآية الكريمة من التفسير الميسر"

                resultList.add(
                    Ayah(
                        surahId = surahId,
                        ayahNumber = ayahNumber,
                        textUthmani = formattedText,
                        translation = "Verse $ayahNumber",
                        tafsir = tafsirText,
                        pageNumber = page,
                        juzNumber = juz
                    )
                )
            }

            if (resultList.isNotEmpty()) {
                surahCache[surahId] = resultList
            }
            Result.success(resultList)
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching surah $surahId: ${e.message}", e)
            Result.failure(e)
        }
    }
}
