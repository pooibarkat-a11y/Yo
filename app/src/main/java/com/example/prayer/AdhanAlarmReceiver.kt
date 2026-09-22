package com.example.prayer

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import com.example.notification.SmartNotificationManager
import java.util.Calendar

/**
 * Handles scheduling and triggering of exact adhans and prayer alarms even when the app is killed.
 */
class AdhanAlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action ?: return
        Log.d("AdhanAlarmReceiver", "Received action: $action")

        when (action) {
            Intent.ACTION_BOOT_COMPLETED,
            Intent.ACTION_MY_PACKAGE_REPLACED,
            ACTION_RESCHEDULE -> {
                scheduleAllPrayers(context)
                SmartNotificationManager.scheduleAllEnabledNotifications(context)
            }
            ACTION_PRE_PRAYER_ALARM -> {
                val prayerName = intent.getStringExtra(EXTRA_PRAYER_NAME) ?: "الصلاة"
                PrePrayerReminderHelper.triggerPrePrayerReminder(context, prayerName)
            }
            ACTION_PRAYER_ALARM -> {
                val prayerName = intent.getStringExtra(EXTRA_PRAYER_NAME) ?: "الصلاة"
                val muezzin = getSelectedMuezzin(context)

                val audioUrl = if (prayerName.contains("الفجر") && !muezzin.fajrAudioUrl.isNullOrBlank()) {
                    muezzin.fajrAudioUrl
                } else {
                    muezzin.audioUrl
                }

                // Fire Foreground Service to play full Adhan voice out loud
                AdhanForegroundService.startAdhan(
                    context = context,
                    prayerName = prayerName,
                    muezzinName = muezzin.nameArabic,
                    audioUrl = audioUrl,
                    muezzinId = muezzin.id
                )

                // Speak voice reminder: "حان وقت الصلاة..." if pre-prayer/voice reminder is active
                if (PrePrayerReminderHelper.isVoiceEnabled(context)) {
                    PrePrayerReminderHelper.speakArabicText(
                        context,
                        PrePrayerReminderHelper.getPrayerTimeSpokenPhrase(prayerName)
                    )
                }

                // Also send a high-priority system heads-up notification with exact time
                SmartNotificationManager.sendPrayerTimeNotification(
                    context = context,
                    prayerName = prayerName,
                    muezzinName = muezzin.nameArabic
                )

                // Reschedule for next day
                scheduleAllPrayers(context)
            }
        }
    }

    companion object {
        const val ACTION_PRAYER_ALARM = "com.example.prayer.ACTION_PRAYER_ALARM"
        const val ACTION_PRE_PRAYER_ALARM = "com.example.prayer.ACTION_PRE_PRAYER_ALARM"
        const val ACTION_RESCHEDULE = "com.example.prayer.ACTION_RESCHEDULE_ADHANS"

        const val EXTRA_PRAYER_NAME = "extra_prayer_name"
        const val EXTRA_MUEZZIN_ID = "extra_muezzin_id"

        private const val PREFS_NAME = "adhan_scheduler_prefs"
        private const val KEY_SELECTED_CITY = "adhan_city_id"
        private const val KEY_PRAYER_METHOD = "adhan_prayer_method"
        private const val KEY_JURISTIC_METHOD = "adhan_juristic_method"
        private const val KEY_SELECTED_MUEZZIN = "adhan_muezzin_id"
        private const val KEY_AUTOPLAY_ENABLED = "adhan_autoplay_enabled"
        private const val KEY_ENABLED_PRAYERS_PREFIX = "adhan_prayer_enabled_"

        private fun getPrefs(context: Context): SharedPreferences {
            return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        }

        private const val KEY_CITY_LAT = "adhan_city_lat"
        private const val KEY_CITY_LNG = "adhan_city_lng"
        private const val KEY_CITY_NAME = "adhan_city_name"
        private const val KEY_CITY_COUNTRY = "adhan_city_country"
        private const val KEY_CITY_TZ = "adhan_city_tz"
        private const val KEY_CITY_IS_GPS = "adhan_city_is_gps"

        private const val KEY_OFFSET_FAJR = "offset_fajr"
        private const val KEY_OFFSET_SUNRISE = "offset_sunrise"
        private const val KEY_OFFSET_DHUHR = "offset_dhuhr"
        private const val KEY_OFFSET_ASR = "offset_asr"
        private const val KEY_OFFSET_MAGHRIB = "offset_maghrib"
        private const val KEY_OFFSET_ISHA = "offset_isha"

        fun savePrayerPreferences(
            context: Context,
            city: PrayerCity,
            methodName: String,
            juristicName: String,
            muezzinId: String,
            autoPlayEnabled: Boolean,
            enabledPrayers: Set<String>,
            offsets: PrayerOffsets = PrayerOffsets()
        ) {
            val editor = getPrefs(context).edit()
            editor.putString(KEY_SELECTED_CITY, city.id)
            editor.putString(KEY_CITY_NAME, city.nameArabic)
            editor.putString(KEY_CITY_COUNTRY, city.countryArabic)
            editor.putString(KEY_CITY_LAT, city.latitude.toString())
            editor.putString(KEY_CITY_LNG, city.longitude.toString())
            editor.putString(KEY_CITY_TZ, city.defaultTimeZone.toString())
            editor.putBoolean(KEY_CITY_IS_GPS, city.isGpsDetected)

            editor.putString(KEY_PRAYER_METHOD, methodName)
            editor.putString(KEY_JURISTIC_METHOD, juristicName)
            editor.putString(KEY_SELECTED_MUEZZIN, muezzinId)
            editor.putBoolean(KEY_AUTOPLAY_ENABLED, autoPlayEnabled)

            editor.putInt(KEY_OFFSET_FAJR, offsets.fajr)
            editor.putInt(KEY_OFFSET_SUNRISE, offsets.sunrise)
            editor.putInt(KEY_OFFSET_DHUHR, offsets.dhuhr)
            editor.putInt(KEY_OFFSET_ASR, offsets.asr)
            editor.putInt(KEY_OFFSET_MAGHRIB, offsets.maghrib)
            editor.putInt(KEY_OFFSET_ISHA, offsets.isha)

            listOf("الفجر", "الظهر", "العصر", "المغرب", "العشاء").forEach { prayer ->
                editor.putBoolean(KEY_ENABLED_PRAYERS_PREFIX + prayer, prayer in enabledPrayers)
            }
            editor.apply()
        }

        fun getSavedOffsets(context: Context): PrayerOffsets {
            val prefs = getPrefs(context)
            return PrayerOffsets(
                fajr = prefs.getInt(KEY_OFFSET_FAJR, 0),
                sunrise = prefs.getInt(KEY_OFFSET_SUNRISE, 0),
                dhuhr = prefs.getInt(KEY_OFFSET_DHUHR, 0),
                asr = prefs.getInt(KEY_OFFSET_ASR, 0),
                maghrib = prefs.getInt(KEY_OFFSET_MAGHRIB, 0),
                isha = prefs.getInt(KEY_OFFSET_ISHA, 0)
            )
        }

        fun isAdhanEnabledForPrayer(context: Context, prayerName: String): Boolean {
            val prefs = getPrefs(context)
            return prefs.getBoolean(KEY_ENABLED_PRAYERS_PREFIX + prayerName, true)
        }

        fun isAdhanAutoPlayEnabled(context: Context): Boolean {
            val prefs = getPrefs(context)
            return prefs.getBoolean(KEY_AUTOPLAY_ENABLED, true)
        }

        fun getSelectedMuezzin(context: Context): Muezzin {
            val prefs = getPrefs(context)
            val id = prefs.getString(KEY_SELECTED_MUEZZIN, null)
            return AdhanData.muezzins.find { it.id == id } ?: AdhanData.muezzins.first()
        }

        fun getSavedCity(context: Context): PrayerCity {
            val prefs = getPrefs(context)
            val cityId = prefs.getString(KEY_SELECTED_CITY, null)
            val matched = PrayerCalculator.CITIES.find { it.id == cityId }
            if (matched != null) return matched

            // If not found in standard list, restore from saved custom/GPS data
            val name = prefs.getString(KEY_CITY_NAME, null)
            val country = prefs.getString(KEY_CITY_COUNTRY, null)
            val latStr = prefs.getString(KEY_CITY_LAT, null)
            val lngStr = prefs.getString(KEY_CITY_LNG, null)
            val tzStr = prefs.getString(KEY_CITY_TZ, null)
            val isGps = prefs.getBoolean(KEY_CITY_IS_GPS, false)

            if (name != null && latStr != null && lngStr != null) {
                val lat = latStr.toDoubleOrNull() ?: PrayerCalculator.DEFAULT_CITY.latitude
                val lng = lngStr.toDoubleOrNull() ?: PrayerCalculator.DEFAULT_CITY.longitude
                val tz = tzStr?.toDoubleOrNull() ?: PrayerCalculator.DEFAULT_CITY.defaultTimeZone
                return PrayerCity(
                    id = cityId ?: "saved_custom_city",
                    nameArabic = name,
                    countryArabic = country ?: "",
                    latitude = lat,
                    longitude = lng,
                    defaultTimeZone = tz,
                    recommendedMethod = PrayerCalculator.DEFAULT_CITY.recommendedMethod,
                    isGpsDetected = isGps
                )
            }
            return PrayerCalculator.DEFAULT_CITY
        }

        fun getSavedMethod(context: Context): PrayerMethod {
            val prefs = getPrefs(context)
            val methodName = prefs.getString(KEY_PRAYER_METHOD, null)
            return try {
                PrayerMethod.valueOf(methodName ?: "")
            } catch (_: Exception) {
                getSavedCity(context).recommendedMethod
            }
        }

        fun getSavedJuristicMethod(context: Context): JuristicMethod {
            val prefs = getPrefs(context)
            val juristicName = prefs.getString(KEY_JURISTIC_METHOD, null)
            return try {
                JuristicMethod.valueOf(juristicName ?: "")
            } catch (_: Exception) {
                JuristicMethod.SHAFI_HANBALI_MALIKI
            }
        }

        fun scheduleAllPrayers(context: Context) {
            val prefs = getPrefs(context)
            val autoPlay = prefs.getBoolean(KEY_AUTOPLAY_ENABLED, true)
            if (!autoPlay) return

            val city = getSavedCity(context)
            val method = getSavedMethod(context)
            val juristic = getSavedJuristicMethod(context)
            val muezzin = getSelectedMuezzin(context)

            val offsets = getSavedOffsets(context)
            val prayerTimes = PrayerCalculator.calculatePrayerTimes(city, method, juristic, offsets = offsets)

            val prayerList = listOf(
                "الفجر" to prayerTimes.fajr,
                "الظهر" to prayerTimes.dhuhr,
                "العصر" to prayerTimes.asr,
                "المغرب" to prayerTimes.maghrib,
                "العشاء" to prayerTimes.isha
            )

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager ?: return

            prayerList.forEachIndexed { index, (name, timeStr) ->
                val isPrayerEnabled = prefs.getBoolean(KEY_ENABLED_PRAYERS_PREFIX + name, true)
                if (!isPrayerEnabled) return@forEachIndexed

                val parts = timeStr.split(":")
                if (parts.size != 2) return@forEachIndexed
                val hour = parts[0].toIntOrNull() ?: return@forEachIndexed
                val minute = parts[1].toIntOrNull() ?: return@forEachIndexed

                val targetCal = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, hour)
                    set(Calendar.MINUTE, minute)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                    // If prayer time passed for today, schedule for tomorrow
                    if (timeInMillis <= System.currentTimeMillis()) {
                        add(Calendar.DAY_OF_YEAR, 1)
                    }
                }

                val intent = Intent(context, AdhanAlarmReceiver::class.java).apply {
                    action = ACTION_PRAYER_ALARM
                    putExtra(EXTRA_PRAYER_NAME, name)
                    putExtra(EXTRA_MUEZZIN_ID, muezzin.id)
                }

                val requestCode = 5000 + index
                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    requestCode,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )

                try {
                    val showIntent = Intent(context, com.example.MainActivity::class.java)
                    val showPendingIntent = PendingIntent.getActivity(
                        context,
                        6000 + index,
                        showIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        alarmManager.setAlarmClock(
                            AlarmManager.AlarmClockInfo(targetCal.timeInMillis, showPendingIntent),
                            pendingIntent
                        )
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        alarmManager.setExactAndAllowWhileIdle(
                            AlarmManager.RTC_WAKEUP,
                            targetCal.timeInMillis,
                            pendingIntent
                        )
                    } else {
                        alarmManager.setExact(
                            AlarmManager.RTC_WAKEUP,
                            targetCal.timeInMillis,
                            pendingIntent
                        )
                    }
                    Log.d("AdhanAlarmReceiver", "Scheduled exact Adhan for $name at ${targetCal.time}")
                } catch (_: SecurityException) {
                    alarmManager.set(
                        AlarmManager.RTC_WAKEUP,
                        targetCal.timeInMillis,
                        pendingIntent
                    )
                } catch (e: Exception) {
                    Log.e("AdhanAlarmReceiver", "Error scheduling adhan for $name", e)
                }

                // Schedule Pre-Prayer Reminder (اقتربت موعد الصلاة)
                if (PrePrayerReminderHelper.isPrePrayerEnabled(context)) {
                    val minutesBefore = PrePrayerReminderHelper.getMinutesBefore(context)
                    val preCalTime = targetCal.timeInMillis - (minutesBefore * 60 * 1000L)
                    if (preCalTime > System.currentTimeMillis()) {
                        val preIntent = Intent(context, AdhanAlarmReceiver::class.java).apply {
                            action = ACTION_PRE_PRAYER_ALARM
                            putExtra(EXTRA_PRAYER_NAME, name)
                        }
                        val prePendingIntent = PendingIntent.getBroadcast(
                            context,
                            5500 + index,
                            preIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                        )
                        try {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                alarmManager.setExactAndAllowWhileIdle(
                                    AlarmManager.RTC_WAKEUP,
                                    preCalTime,
                                    prePendingIntent
                                )
                            } else {
                                alarmManager.set(
                                    AlarmManager.RTC_WAKEUP,
                                    preCalTime,
                                    prePendingIntent
                                )
                            }
                            Log.d("AdhanAlarmReceiver", "Scheduled pre-prayer reminder for $name at ${Calendar.getInstance().apply { timeInMillis = preCalTime }.time}")
                        } catch (e: Exception) {
                            Log.e("AdhanAlarmReceiver", "Error scheduling pre-prayer reminder for $name", e)
                        }
                    }
                }
            }
        }
    }
}
