package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.prayer.PrayerCalculator
import com.example.speech.QuranSpeechComparator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun readStringFromContext() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("ترتيل", appName)
    }

    @Test
    fun testArabicSpeechNormalizationAndDiff() {
        val original = "قُلْ هُوَ اللَّهُ أَحَدٌ"
        val userRecitation = "قل هو الله احد"
        val result = QuranSpeechComparator.compareRecitation(
            surahId = 112,
            surahName = "الإخلاص",
            startAyah = 1,
            endAyah = 1,
            originalAyahText = original,
            userSpokenText = userRecitation
        )
        assertEquals(100, result.accuracyPercentage)
        assertEquals(4, result.correctWords)
    }

    @Test
    fun testPrayerCalculationAndQibla() {
        val times = PrayerCalculator.calculatePrayerTimes()
        assertTrue(times.fajr.isNotBlank())
        assertTrue(times.dhuhr.isNotBlank())
        assertTrue(times.asr.isNotBlank())
        assertTrue(times.maghrib.isNotBlank())
        assertTrue(times.isha.isNotBlank())
        assertTrue(times.qiblaDirectionDeg > 0f)
    }
}
