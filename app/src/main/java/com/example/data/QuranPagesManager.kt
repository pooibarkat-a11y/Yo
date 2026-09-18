package com.example.data

import com.example.model.Surah
import java.util.Locale

object QuranPagesManager {

    const val TOTAL_PAGES = 604

    /**
     * Primary high-definition Madani Mushaf page URL (BunnyCDN / Cloudflare)
     * e.g. https://files.quran.app/hafs/madani/width_1024/page001.png
     */
    fun getPageImageUrl(page: Int): String {
        val clamped = page.coerceIn(1, TOTAL_PAGES)
        return String.format(Locale.US, "https://files.quran.app/hafs/madani/width_1024/page%03d.png", clamped)
    }

    /**
     * High-res fallback page URL from King Saud University Ayat Project
     * e.g. https://quran.ksu.edu.sa/ayat/safahat1/1.png
     */
    fun getFallbackPageImageUrl(page: Int): String {
        val clamped = page.coerceIn(1, TOTAL_PAGES)
        return "https://quran.ksu.edu.sa/ayat/safahat1/$clamped.png"
    }

    /**
     * Determines which Surah primarily resides on the given page
     */
    fun getPageSurah(page: Int): Surah {
        val clamped = page.coerceIn(1, TOTAL_PAGES)
        return QuranData.surahs.lastOrNull { it.startPage <= clamped } ?: QuranData.surahs.first()
    }

    /**
     * Determines which Juz the page belongs to
     */
    fun getPageJuz(page: Int): JuzInfo {
        val clamped = page.coerceIn(1, TOTAL_PAGES)
        return QuranIndexData.allJuzList.lastOrNull { it.startPage <= clamped } ?: QuranIndexData.allJuzList.first()
    }

    /**
     * Computes the Hizb number (1 to 60) for a given page
     */
    fun getPageHizb(page: Int): Int {
        val clamped = page.coerceIn(1, TOTAL_PAGES)
        val juz = getPageJuz(clamped)
        val nextJuzStart = QuranIndexData.allJuzList.getOrNull(juz.number)?.startPage ?: 605
        val midJuz = juz.startPage + (nextJuzStart - juz.startPage) / 2
        return (juz.number - 1) * 2 + (if (clamped >= midJuz) 2 else 1)
    }

    /**
     * Cleans trailing ayah symbols and repeated numbers so they don't appear twice
     */
    private val trailingAyahRegex = Regex("""[\s۝۞۩\(\)\[\]﴿﴾\d\u0660-\u0669]+$""")

    fun cleanAyahText(raw: String): String {
        return trailingAyahRegex.replace(raw, "").trim()
    }
}
