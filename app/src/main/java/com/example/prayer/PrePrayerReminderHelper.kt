package com.example.prayer

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.RingtoneManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.MainActivity
import com.example.R
import java.util.Locale

/**
 * خدمة التنبيه المسبق قبل موعد الصلاة («اقتربت موعد الصلاة»)
 * تقدم تذكيراً روحانياً خاشعاً ونطقاً صوتياً شجياً قبل حلول وقت الأذان
 */
object PrePrayerReminderHelper {

    private const val TAG = "PrePrayerReminder"
    private const val PREFS_NAME = "pre_prayer_prefs"
    private const val KEY_ENABLED = "pre_prayer_enabled"
    private const val KEY_MINUTES_BEFORE = "pre_prayer_minutes_before"
    private const val KEY_VOICE_ENABLED = "pre_prayer_voice_enabled"
    private const val CHANNEL_ID = "channel_pre_prayer_reminder"

    private var tts: TextToSpeech? = null
    private var isTtsReady = false

    fun isPrePrayerEnabled(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_ENABLED, true)
    }

    fun setPrePrayerEnabled(context: Context, enabled: Boolean) {
        getPrefs(context).edit().putBoolean(KEY_ENABLED, enabled).apply()
        AdhanAlarmReceiver.scheduleAllPrayers(context)
    }

    fun getMinutesBefore(context: Context): Int {
        return getPrefs(context).getInt(KEY_MINUTES_BEFORE, 15)
    }

    fun setMinutesBefore(context: Context, minutes: Int) {
        getPrefs(context).edit().putInt(KEY_MINUTES_BEFORE, minutes).apply()
        AdhanAlarmReceiver.scheduleAllPrayers(context)
    }

    fun isVoiceEnabled(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_VOICE_ENABLED, true)
    }

    fun setVoiceEnabled(context: Context, enabled: Boolean) {
        getPrefs(context).edit().putBoolean(KEY_VOICE_ENABLED, enabled).apply()
    }

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    /**
     * عبارات إيمانية رقيقة خاشعة لكل صلاة
     */
    fun getSpiritualWords(prayerName: String): String {
        return when {
            prayerName.contains("فجر") ->
                "«اقترب موعد صلاة الفجر.. قم وتوضأ وأقبل على ربك بقلبٍ حاضر خاشع، فركعتا الفجر خيرٌ من الدنيا وما فيها ✨»"
            prayerName.contains("ظهر") ->
                "«اقترب موعد صلاة الظهر.. دع مشاغل الدنيا جانباً وأرح فؤادك، فالصلاة قرة عين المؤمنين وراحة للقلوب 🌸»"
            prayerName.contains("عصر") ->
                "«اقترب موعد صلاة العصر.. حافظوا على الصلوات والصلاة الوسطى، حان وقت استراحة النفس ولقاء الرحمن 🌿»"
            prayerName.contains("مغرب") ->
                "«اقترب موعد صلاة المغرب.. أقبلت نسائم المساء وطابت الأوقات بذكر الله، ردد الأذكار وأجب نداء المنادي 🌅»"
            prayerName.contains("عشاء") ->
                "«اقترب موعد صلاة العشاء.. اختم يومك بسجدة خاشعة بين يدي أرحم الراحمين في هدوء الليل وسكينته 🌙»"
            else ->
                "«اقترب موعد الصلاة.. استعد للوقوف بين يدي الله وأحسن الوضوء، تقبل الله طاعتكم ورفع درجاتكم 🤲»"
        }
    }

    /**
     * النص المنطوق صوتياً
     */
    fun getSpokenPhrase(prayerName: String): String {
        val cleanName = prayerName.replace(" (غداً)", "")
        return "اقترب موعد صلاة $cleanName، استعد للوقوف بين يدي الله وتوضأ، أثابكم الله وتقبل طاعتكم"
    }

    /**
     * إرسال الإشعار والتنبيه الصوتي
     */
    fun triggerPrePrayerReminder(context: Context, prayerName: String) {
        val spiritual = getSpiritualWords(prayerName)
        val minutes = getMinutesBefore(context)

        createNotificationChannel(context)

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("navigate_to", "prayer")
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            9111,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("اقتربت موعد صلاة $prayerName 🕌 (متبقي $minutes دقيقة)")
            .setContentText(spiritual)
            .setStyle(NotificationCompat.BigTextStyle().bigText(spiritual))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setSound(soundUri)
            .build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
        notificationManager?.notify(7000 + prayerName.hashCode(), notification)

        // النطق الصوتي إذا كان مفعلاً
        if (isVoiceEnabled(context)) {
            speakArabicText(context, getSpokenPhrase(prayerName))
        }
    }

    /**
     * نطق العبارة بالصوت عبر محرك النصوص العربي
     */
    fun speakArabicText(context: Context, text: String, onDone: (() -> Unit)? = null) {
        val appContext = context.applicationContext
        if (tts == null) {
            tts = TextToSpeech(appContext) { status ->
                if (status == TextToSpeech.SUCCESS) {
                    val result = tts?.setLanguage(Locale("ar"))
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        tts?.setLanguage(Locale.getDefault())
                    }
                    tts?.setPitch(1.0f)
                    tts?.setSpeechRate(0.88f) // سرعة هادئة رصينة
                    isTtsReady = true
                    speakInternal(text, onDone)
                } else {
                    onDone?.invoke()
                }
            }
        } else {
            speakInternal(text, onDone)
        }
    }

    private fun speakInternal(text: String, onDone: (() -> Unit)?) {
        try {
            tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {}
                override fun onDone(utteranceId: String?) {
                    Handler(Looper.getMainLooper()).post { onDone?.invoke() }
                }
                @Deprecated("Deprecated in Java")
                override fun onError(utteranceId: String?) {
                    Handler(Looper.getMainLooper()).post { onDone?.invoke() }
                }
            })
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "pre_prayer_tts_id")
        } catch (e: Exception) {
            Log.e(TAG, "Error speaking text", e)
            onDone?.invoke()
        }
    }

    fun stopSpeaking() {
        try {
            tts?.stop()
        } catch (_: Exception) {}
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "تنبيه اقتراب موعد الصلاة"
            val desc = "إشعارات تذكير بالاستعداد والوضوء قبل دخول وقت الصلاة"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = desc
                enableLights(true)
                enableVibration(true)
            }
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
            manager?.createNotificationChannel(channel)
        }
    }
}
