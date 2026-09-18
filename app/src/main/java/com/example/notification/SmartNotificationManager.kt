package com.example.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.MainActivity
import com.example.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class SmartNotificationItem(
    val id: String,
    val title: String,
    val description: String,
    val category: String,
    val iconEmoji: String,
    val defaultHour: Int,
    val defaultMinute: Int,
    var isEnabled: Boolean = true,
    val sampleBody: String
) {
    val timeFormatted: String
        get() = String.format("%02d:%02d", defaultHour, defaultMinute)
}

object SmartNotificationManager {

    private const val PREFS_NAME = "smart_notifications_prefs"
    private const val KEY_ENABLED_PREFIX = "notif_enabled_"

    const val CHANNEL_PRAYER = "channel_prayer_reminders"
    const val CHANNEL_AZKAR = "channel_azkar_reminders"
    const val CHANNEL_QURAN = "channel_quran_reminders"
    const val CHANNEL_SUNAN = "channel_sunan_reminders"

    val defaultItems = listOf(
        SmartNotificationItem(
            id = "prayer_entry",
            title = "إشعار دخول وقت الصلاة",
            description = "تنبيه فوري عذب عند حلول وقت كل صلاة من الصلوات الخمس",
            category = "الصلاة والأذان",
            iconEmoji = "🕌",
            defaultHour = 12,
            defaultMinute = 0,
            isEnabled = true,
            sampleBody = "حان الآن موعد صلاة الظهر.. حيّ على الصلاة، حيّ على الفلاح."
        ),
        SmartNotificationItem(
            id = "pre_fajr",
            title = "إشعار قبل الفجر (السحور والاستغفار)",
            description = "تنبيه مبارك بالأسحار لقيام الليل والاستغفار وتناول السحور",
            category = "الصلاة والأذان",
            iconEmoji = "🌙",
            defaultHour = 4,
            defaultMinute = 0,
            isEnabled = true,
            sampleBody = "والمستغفرين بالأسحار.. هنيئاً لمن قام لربه راكعاً وساجداً ومستغفراً بالأسحار."
        ),
        SmartNotificationItem(
            id = "prayer_reminder",
            title = "تذكير الصلاة والسنن الرواتب",
            description = "تذكير بالمحافظة على الجماعة وصلاة السنن الرواتب",
            category = "الصلاة والأذان",
            iconEmoji = "🤲",
            defaultHour = 12,
            defaultMinute = 30,
            isEnabled = true,
            sampleBody = "أول ما يحاسب عليه العبد يوم القيامة الصلاة.. لا تفوت صلاة الجماعة وفضل السنن الرواتب."
        ),
        SmartNotificationItem(
            id = "azkar_morning_evening",
            title = "تذكير أذكار الصباح والمساء",
            description = "حصنك اليومي المنيع من كل سوء وأذكار الاستيقاظ والنوم",
            category = "الأذكار",
            iconEmoji = "☀️",
            defaultHour = 6,
            defaultMinute = 30,
            isEnabled = true,
            sampleBody = "أصبحنا وأصبح الملك لله، والحمد لله.. عطر يومك الآن بأذكار الصباح."
        ),
        SmartNotificationItem(
            id = "quran_daily",
            title = "تذكير قراءة القرآن اليومي",
            description = "تنبيه لمداومة تلاوة آيات الذكر الحكيم وتدبر معانيه",
            category = "القرآن الكريم",
            iconEmoji = "📖",
            defaultHour = 14,
            defaultMinute = 0,
            isEnabled = true,
            sampleBody = "﴿إِنَّ هَٰذَا الْقُرْآنَ يَهْدِي لِلَّتِي هِيَ أَقْوَمُ﴾.. لا تجعل يومك يمر دون ورد قرآني يبارك حياتك."
        ),
        SmartNotificationItem(
            id = "daily_wird",
            title = "إشعار يومي: وردك اليوم",
            description = "تنبيه ذكي لمتابعة ورد اليوم والصفحات المحددة لك",
            category = "القرآن الكريم",
            iconEmoji = "✨",
            defaultHour = 18,
            defaultMinute = 0,
            isEnabled = true,
            sampleBody = "حان موعد وردك اليومي من القرآن الكريم.. افتح المصحف واقرأ صفحاتك المقررة الآن."
        ),
        SmartNotificationItem(
            id = "khatmah_reminder",
            title = "تذكير ختمة القرآن ومتابعة الخطة",
            description = "متابعة إنجازك في جدول الختمة الشهرية وتشجيعك على إتمامها",
            category = "القرآن الكريم",
            iconEmoji = "📑",
            defaultHour = 20,
            defaultMinute = 30,
            isEnabled = true,
            sampleBody = "ما زلت على طريق ختم كتاب الله.. راجع ما قرأته اليوم واقترب خطوة جديدة من الختمة."
        ),
        SmartNotificationItem(
            id = "friday_reminder",
            title = "تذكير يوم الجمعة (سورة الكهف والصلاة على النبي)",
            description = "تنبيه أسبوعي صباح الجمعة بقراءة سورة الكهف وساعة الإجابة",
            category = "السنن والأيام الفاضلة",
            iconEmoji = "📿",
            defaultHour = 9,
            defaultMinute = 0,
            isEnabled = true,
            sampleBody = "جمعة مباركة طيبة.. من قرأ سورة الكهف في يوم الجمعة أضاء له من النور ما بين الجمعتين. أكثر من الصلاة على الحبيب ﷺ."
        ),
        SmartNotificationItem(
            id = "fasting_reminder",
            title = "تذكير الصيام (الإثنين والخميس والأيام البيض)",
            description = "تنبيه عشية أيام الصيام المستحب لنيل الأجر العظيم وتبييت النية",
            category = "السنن والأيام الفاضلة",
            iconEmoji = "🥛",
            defaultHour = 20,
            defaultMinute = 0,
            isEnabled = true,
            sampleBody = "تذكير بسنة الصيام المستحب غداً.. من صام يوماً في سبيل الله باعد الله وجهه عن النار سبعين خريفاً."
        ),
        SmartNotificationItem(
            id = "qiyam_layl",
            title = "تذكير قيام الليل والوتر",
            description = "دعوة لمناجاة الله قبل النوم أو في جوف الليل الأخير",
            category = "السنن والأيام الفاضلة",
            iconEmoji = "🌟",
            defaultHour = 23,
            defaultMinute = 0,
            isEnabled = true,
            sampleBody = "اجعلوا آخر صلاتكم بالليل وتراً.. ركعة في جوف الليل خير من الدنيا وما فيها، صلِّ الوتر ونم على طهارة."
        )
    )

    private val _notificationsState = MutableStateFlow<List<SmartNotificationItem>>(emptyList())
    val notificationsState: StateFlow<List<SmartNotificationItem>> = _notificationsState.asStateFlow()

    fun init(context: Context) {
        createNotificationChannels(context)
        loadSettings(context)
    }

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    private fun loadSettings(context: Context) {
        val prefs = getPrefs(context)
        val list = defaultItems.map { item ->
            val isEnabled = prefs.getBoolean(KEY_ENABLED_PREFIX + item.id, true)
            item.copy(isEnabled = isEnabled)
        }
        _notificationsState.value = list
    }

    fun setNotificationEnabled(context: Context, id: String, enabled: Boolean) {
        val prefs = getPrefs(context)
        prefs.edit().putBoolean(KEY_ENABLED_PREFIX + id, enabled).apply()

        val updated = _notificationsState.value.map { item ->
            if (item.id == id) item.copy(isEnabled = enabled) else item
        }
        _notificationsState.value = updated
    }

    fun toggleAll(context: Context, enabled: Boolean) {
        val editor = getPrefs(context).edit()
        val updated = _notificationsState.value.map { item ->
            editor.putBoolean(KEY_ENABLED_PREFIX + item.id, enabled)
            item.copy(isEnabled = enabled)
        }
        editor.apply()
        _notificationsState.value = updated
    }

    private fun createNotificationChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val prayerChannel = NotificationChannel(
                CHANNEL_PRAYER,
                "مواقيت الصلاة والأذان",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "تنبيهات بدخول أوقات الصلوات المفروضة والتهجد"
                enableVibration(true)
            }

            val azkarChannel = NotificationChannel(
                CHANNEL_AZKAR,
                "أذكار الصباح والمساء",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "أوراد الحصن اليومي وأذكار اليوم والليلة"
                enableVibration(true)
            }

            val quranChannel = NotificationChannel(
                CHANNEL_QURAN,
                "تلاوة القرآن والورد اليومي",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "تذكير الورد القرآني وختمة كتاب الله"
                enableVibration(true)
            }

            val sunanChannel = NotificationChannel(
                CHANNEL_SUNAN,
                "السنن وصيام التطوع ويوم الجمعة",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "تنبيهات صيام الاثنين والخميس والأيام البيض وسورة الكهف وقيام الليل"
                enableVibration(true)
            }

            manager.createNotificationChannels(listOf(prayerChannel, azkarChannel, quranChannel, sunanChannel))
        }
    }

    fun areNotificationsEnabled(context: Context): Boolean {
        return NotificationManagerCompat.from(context).areNotificationsEnabled()
    }

    fun sendPrayerTimeNotification(context: Context, prayerName: String, muezzinName: String): Boolean {
        createNotificationChannels(context)
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            prayerName.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_PRAYER)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("🕌 حان الآن موعد أذان $prayerName")
            .setContentText("حيّ على الصلاة، حيّ على الفلاح • $muezzinName")
            .setStyle(
                NotificationCompat.BigTextStyle().bigText(
                    "الله أكبر، الله أكبر.. حان الآن موعد دخول صلاة $prayerName المباركة.\n" +
                    "بصوت: $muezzinName\n" +
                    "﴿إِنَّ الصَّلَاةَ كَانَتْ عَلَى الْمُؤْمِنِينَ كِتَابًا مَوْقُوتًا﴾\n" +
                    "حيّ على الصلاة، حيّ على الفلاح."
                )
            )
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .build()

        return try {
            val manager = NotificationManagerCompat.from(context)
            manager.notify(prayerName.hashCode(), notification)
            true
        } catch (_: Exception) {
            false
        }
    }

    fun scheduleAllEnabledNotifications(context: Context) {
        init(context)
        _notificationsState.value.filter { it.isEnabled }.forEach { item ->
            scheduleSingleNotification(context, item.id)
        }
    }

    fun scheduleSingleNotification(context: Context, notificationId: String) {
        val item = _notificationsState.value.find { it.id == notificationId }
            ?: defaultItems.find { it.id == notificationId }
            ?: return

        if (!item.isEnabled) return

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? android.app.AlarmManager ?: return
        val intent = Intent(context, SmartNotificationReceiver::class.java).apply {
            action = "com.example.ACTION_FIRE_NOTIFICATION"
            putExtra("notification_id", item.id)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            item.id.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = java.util.Calendar.getInstance().apply {
            set(java.util.Calendar.HOUR_OF_DAY, item.defaultHour)
            set(java.util.Calendar.MINUTE, item.defaultMinute)
            set(java.util.Calendar.SECOND, 0)
            set(java.util.Calendar.MILLISECOND, 0)
            if (timeInMillis <= System.currentTimeMillis()) {
                add(java.util.Calendar.DAY_OF_YEAR, 1)
            }
        }

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    android.app.AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            } else {
                alarmManager.setExact(
                    android.app.AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            }
        } catch (_: SecurityException) {
            alarmManager.set(
                android.app.AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        } catch (_: Exception) {}
    }

    fun sendInstantTestNotification(context: Context): Boolean {
        createNotificationChannels(context)
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            9999,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_PRAYER)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("🕌 إشعار تجريبي ناجح من تطبيق القرآن الشامل")
            .setContentText("﴿أَلَا بِذِكْرِ اللَّهِ تَطْمَئِنُّ الْقُلُوبُ﴾ - الإشعارات والتنبيهات الإسلامية تعمل الآن بنجاح تام!")
            .setStyle(NotificationCompat.BigTextStyle().bigText("﴿أَلَا بِذِكْرِ اللَّهِ تَطْمَئِنُّ الْقُلُوبُ﴾\n\nتم تفعيل الإشعارات بنجاح. ستصلك تذكيرات الصلوات الخمس، أذكار الصباح والمساء، والورد القرآني في مواقيتها المباركة إن شاء الله."))
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .build()

        return try {
            val manager = NotificationManagerCompat.from(context)
            manager.notify(9999, notification)
            true
        } catch (_: Exception) {
            false
        }
    }

    fun sendTestNotification(context: Context, notificationId: String): Boolean {
        createNotificationChannels(context)
        val item = _notificationsState.value.find { it.id == notificationId }
            ?: defaultItems.find { it.id == notificationId }
            ?: return false

        val channelId = when (item.id) {
            "prayer_entry", "pre_fajr", "prayer_reminder" -> CHANNEL_PRAYER
            "azkar_morning_evening" -> CHANNEL_AZKAR
            "quran_daily", "daily_wird", "khatmah_reminder" -> CHANNEL_QURAN
            else -> CHANNEL_SUNAN
        }

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            notificationId.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("${item.iconEmoji} ${item.title}")
            .setContentText(item.sampleBody)
            .setStyle(NotificationCompat.BigTextStyle().bigText(item.sampleBody))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .build()

        return try {
            val manager = NotificationManagerCompat.from(context)
            manager.notify(notificationId.hashCode(), notification)
            true
        } catch (_: SecurityException) {
            false
        } catch (_: Exception) {
            false
        }
    }
}

class SmartNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action ?: return
        when (action) {
            Intent.ACTION_BOOT_COMPLETED,
            Intent.ACTION_MY_PACKAGE_REPLACED -> {
                SmartNotificationManager.init(context)
                SmartNotificationManager.scheduleAllEnabledNotifications(context)
                com.example.prayer.AdhanAlarmReceiver.scheduleAllPrayers(context)
            }
            "com.example.ACTION_FIRE_NOTIFICATION" -> {
                val notificationId = intent.getStringExtra("notification_id") ?: return
                SmartNotificationManager.init(context)
                SmartNotificationManager.sendTestNotification(context, notificationId)
                SmartNotificationManager.scheduleSingleNotification(context, notificationId)
            }
        }
    }
}
