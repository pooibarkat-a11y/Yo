package com.example

import com.example.audio.QuranAudioDownloader
import com.example.prayer.PrayerCalculator
import com.example.prayer.PrayerCity
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun testQiblaDirection() {
    // Cairo to Mecca Qibla should be approximately 136 degrees (South-East)
    val cairo = PrayerCalculator.CITIES.find { it.id == "cairo" } ?: PrayerCalculator.DEFAULT_CITY
    val qibla = PrayerCalculator.calculateQiblaDirection(cairo.latitude, cairo.longitude)
    assertTrue("Cairo Qibla should be between 130 and 140 degrees", qibla in 130f..140f)

    // Distance Cairo to Mecca is around 1280-1300 km
    val distance = PrayerCalculator.calculateDistanceToKaaba(cairo.latitude, cairo.longitude)
    assertTrue("Distance should be around 1280 km", distance in 1200..1400)
  }

  @Test
  fun testPrayerTimesCalculation() {
    val times = PrayerCalculator.calculatePrayerTimes()
    assertNotNull(times.fajr)
    assertNotNull(times.dhuhr)
    assertNotNull(times.asr)
    assertNotNull(times.maghrib)
    assertNotNull(times.isha)
  }

  @Test
  fun testAudioDownloaderKey() {
    val key = QuranAudioDownloader.makeKey("alafasy", 1)
    assertEquals("alafasy_1", key)
  }
}

