package com.example.speech

import com.example.model.HifzTestResult
import com.example.model.WordDiff

object QuranSpeechComparator {

    /**
     * Normalizes Arabic text by removing tashkeel (diacritics), unifying alef, yaa, and taa marbuta
     */
    fun normalizeArabic(text: String): String {
        return text
            // Remove diacritics / Tashkeel
            .replace(Regex("[\u064B-\u065F\u0670\u06D6-\u06ED]"), "")
            // Normalize Alef
            .replace(Regex("[أإآٱ]"), "ا")
            // Normalize Taa Marbuta
            .replace("ة", "ه")
            // Normalize Yaa / Alef Maqsura
            .replace("ى", "ي")
            // Normalize Waw with Hamza
            .replace("ؤ", "و")
            .replace("ئ", "ي")
            // Remove Quranic stop signs and symbols (۝, brackets, numbers)
            .replace(Regex("[۝0-9٠-٩()،.؛:؟!–—\\[\\]{}]"), "")
            .trim()
    }

    /**
     * Compare recited text against original Ayah text word by word
     */
    fun compareRecitation(
        surahId: Int,
        surahName: String,
        startAyah: Int,
        endAyah: Int,
        originalAyahText: String,
        userSpokenText: String
    ): HifzTestResult {
        val originalRawWords = originalAyahText
            .replace(Regex("[۝0-9٠-٩()،.؛:؟!–—\\[\\]{}]"), "")
            .split(Regex("\\s+"))
            .filter { it.isNotBlank() }

        val spokenNormalizedWords = normalizeArabic(userSpokenText)
            .split(Regex("\\s+"))
            .filter { it.isNotBlank() }

        val diffs = mutableListOf<WordDiff>()
        var correctCount = 0

        for (i in originalRawWords.indices) {
            val originalRaw = originalRawWords[i]
            val originalNorm = normalizeArabic(originalRaw)
            val spoken = spokenNormalizedWords.getOrNull(i)

            if (spoken != null) {
                // Check if spoken matches or is highly similar (edit distance <= 1)
                val isMatch = originalNorm == spoken || isCloseMatch(originalNorm, spoken)
                if (isMatch) {
                    correctCount++
                    diffs.add(WordDiff(originalWord = originalRaw, spokenWord = spoken, isCorrect = true))
                } else {
                    diffs.add(WordDiff(originalWord = originalRaw, spokenWord = spoken, isCorrect = false))
                }
            } else {
                // Missing word (user skipped or stopped early)
                diffs.add(WordDiff(originalWord = originalRaw, spokenWord = null, isCorrect = false, isMissing = true))
            }
        }

        // Check for extra words beyond the original text
        if (spokenNormalizedWords.size > originalRawWords.size) {
            for (j in originalRawWords.size until spokenNormalizedWords.size) {
                diffs.add(
                    WordDiff(
                        originalWord = "",
                        spokenWord = spokenNormalizedWords[j],
                        isCorrect = false,
                        isExtra = true
                    )
                )
            }
        }

        val totalWords = originalRawWords.size.coerceAtLeast(1)
        val accuracy = ((correctCount.toDouble() / totalWords) * 100).toInt().coerceIn(0, 100)

        return HifzTestResult(
            surahId = surahId,
            surahName = surahName,
            startAyah = startAyah,
            endAyah = endAyah,
            totalWords = totalWords,
            correctWords = correctCount,
            accuracyPercentage = accuracy,
            wordDiffs = diffs
        )
    }

    private fun isCloseMatch(s1: String, s2: String): Boolean {
        if (s1.isEmpty() || s2.isEmpty()) return false
        if (s1 == s2) return true
        // Allow minor single letter discrepancy or common prefixes (e.g. و / ف)
        if (s1.removePrefix("و") == s2.removePrefix("و")) return true
        if (s1.removePrefix("ف") == s2.removePrefix("ف")) return true
        if (s1.removePrefix("ال") == s2.removePrefix("ال")) return true

        val m = s1.length
        val n = s2.length
        if (kotlin.math.abs(m - n) > 1) return false

        var diff = 0
        var i = 0
        var j = 0
        while (i < m && j < n) {
            if (s1[i] != s2[j]) {
                diff++
                if (diff > 1) return false
                if (m > n) i++
                else if (n > m) j++
                else { i++; j++ }
            } else {
                i++; j++
            }
        }
        return true
    }
}
