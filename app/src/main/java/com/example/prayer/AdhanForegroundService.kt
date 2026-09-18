package com.example.prayer

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import androidx.core.app.NotificationCompat
import com.example.MainActivity
import com.example.R

/**
 * Foreground Service to play the Adhan reliably even when the app is completely closed
 * or the phone screen is locked.
 */
class AdhanForegroundService : Service() {

    private var mediaPlayer: MediaPlayer? = null
    private var wakeLock: PowerManager.WakeLock? = null

    companion object {
        const val CHANNEL_ID = "adhan_playback_channel"
        const val NOTIFICATION_ID = 7777

        const val ACTION_PLAY_ADHAN = "com.example.prayer.action.PLAY_ADHAN"
        const val ACTION_STOP_ADHAN = "com.example.prayer.action.STOP_ADHAN"

        const val EXTRA_PRAYER_NAME = "extra_prayer_name"
        const val EXTRA_MUEZZIN_ID = "extra_muezzin_id"
        const val EXTRA_MUEZZIN_NAME = "extra_muezzin_name"
        const val EXTRA_AUDIO_URL = "extra_audio_url"

        fun startAdhan(
            context: Context,
            prayerName: String,
            muezzinName: String,
            audioUrl: String,
            muezzinId: String = ""
        ) {
            val intent = Intent(context, AdhanForegroundService::class.java).apply {
                action = ACTION_PLAY_ADHAN
                putExtra(EXTRA_PRAYER_NAME, prayerName)
                putExtra(EXTRA_MUEZZIN_NAME, muezzinName)
                putExtra(EXTRA_AUDIO_URL, audioUrl)
                putExtra(EXTRA_MUEZZIN_ID, muezzinId)
            }
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(intent)
                } else {
                    context.startService(intent)
                }
            } catch (_: Exception) {}
        }

        fun stopAdhan(context: Context) {
            val intent = Intent(context, AdhanForegroundService::class.java).apply {
                action = ACTION_STOP_ADHAN
            }
            try {
                context.startService(intent)
            } catch (_: Exception) {}
        }
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        try {
            val pm = getSystemService(Context.POWER_SERVICE) as PowerManager
            wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Tarteel:AdhanWakeLock").apply {
                acquire(10 * 60 * 1000L /* 10 minutes */)
            }
        } catch (_: Exception) {}
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.action
        if (action == ACTION_STOP_ADHAN) {
            stopAdhanAndFinish()
            return START_NOT_STICKY
        }

        val prayerName = intent?.getStringExtra(EXTRA_PRAYER_NAME) ?: "الصلاة"
        val muezzinName = intent?.getStringExtra(EXTRA_MUEZZIN_NAME) ?: "أذان الحرم المكي"
        val audioUrl = intent?.getStringExtra(EXTRA_AUDIO_URL) ?: ""

        val notification = createAdhanNotification(prayerName, muezzinName)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(
                NOTIFICATION_ID,
                notification,
                android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
            )
        } else {
            startForeground(NOTIFICATION_ID, notification)
        }

        startPlayback(prayerName, audioUrl)

        return START_NOT_STICKY
    }

    private fun startPlayback(prayerName: String, audioUrl: String) {
        releasePlayer()
        try {
            if (audioUrl.isNotBlank()) {
                val player = MediaPlayer().apply {
                    setAudioAttributes(
                        AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setUsage(AudioAttributes.USAGE_ALARM)
                            .build()
                    )
                    try {
                        setWakeMode(applicationContext, PowerManager.PARTIAL_WAKE_LOCK)
                    } catch (_: Exception) {}
                    setVolume(1.0f, 1.0f)
                    setDataSource(applicationContext, Uri.parse(audioUrl))
                    setOnPreparedListener {
                        it.start()
                    }
                    setOnCompletionListener {
                        onAdhanPlaybackFinished(prayerName)
                    }
                    setOnErrorListener { _, _, _ ->
                        playOfflineFallback(prayerName)
                        true
                    }
                }
                player.prepareAsync()
                mediaPlayer = player
            } else {
                playOfflineFallback(prayerName)
            }
        } catch (_: Exception) {
            playOfflineFallback(prayerName)
        }
    }

    private fun playOfflineFallback(prayerName: String) {
        releasePlayer()
        try {
            val fallback = MediaPlayer.create(applicationContext, R.raw.adhan_default)
            if (fallback != null) {
                fallback.setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .build()
                )
                fallback.setVolume(1.0f, 1.0f)
                fallback.setOnCompletionListener {
                    onAdhanPlaybackFinished(prayerName)
                }
                fallback.start()
                mediaPlayer = fallback
            } else {
                onAdhanPlaybackFinished(prayerName)
            }
        } catch (_: Exception) {
            stopAdhanAndFinish()
        }
    }

    private fun onAdhanPlaybackFinished(prayerName: String) {
        if (AdhanData.isDuaAfterAdhanEnabled) {
            playDuaAfterAdhan(prayerName)
        } else {
            stopAdhanAndFinish()
        }
    }

    private fun playDuaAfterAdhan(prayerName: String) {
        releasePlayer()
        try {
            val sheikh = AdhanData.duaAfterAdhanSheikhs.find { it.id == AdhanData.selectedDuaSheikhId }
                ?: AdhanData.duaAfterAdhanSheikhs.first()

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notification = createDuaNotification(prayerName, sheikh.nameArabic)
            notificationManager.notify(NOTIFICATION_ID, notification)

            val duaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .build()
                )
                try {
                    setWakeMode(applicationContext, PowerManager.PARTIAL_WAKE_LOCK)
                } catch (_: Exception) {}
                setVolume(1.0f, 1.0f)
                setDataSource(applicationContext, Uri.parse(sheikh.audioUrl))
                setOnPreparedListener { it.start() }
                setOnCompletionListener { stopAdhanAndFinish() }
                setOnErrorListener { _, _, _ ->
                    stopAdhanAndFinish()
                    true
                }
            }
            duaPlayer.prepareAsync()
            mediaPlayer = duaPlayer
        } catch (_: Exception) {
            stopAdhanAndFinish()
        }
    }

    private fun createDuaNotification(prayerName: String, sheikhName: String): Notification {
        val appIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pendingAppIntent = PendingIntent.getActivity(
            this,
            0,
            appIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val stopIntent = Intent(this, AdhanForegroundService::class.java).apply {
            action = ACTION_STOP_ADHAN
        }
        val pendingStopIntent = PendingIntent.getService(
            this,
            NOTIFICATION_ID + 2,
            stopIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("🤲 دعاء ما بعد أذان $prayerName")
            .setContentText("اللهم رب هذه الدعوة التامة والصلاة القائمة... • $sheikhName")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("اللَّهُمَّ رَبَّ هَذِهِ الدَّعْوَةِ التَّامَّةِ، وَالصَّلاَةِ الْقَائِمَةِ، آتِ مُحَمَّدًا الْوَسِيلَةَ وَالْفَضِيلَةَ، وَابْعَثْهُ مَقَامًا مَحْمُودًا الَّذِي وَعَدْتَهُ.\nبصوت: $sheikhName\nفضله: حلت له شفاعتي يوم القيامة.")
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setContentIntent(pendingAppIntent)
            .addAction(android.R.drawable.ic_media_pause, "إغلاق", pendingStopIntent)
            .setOngoing(true)
            .build()
    }

    private fun stopAdhanAndFinish() {
        releasePlayer()
        try {
            wakeLock?.let {
                if (it.isHeld) it.release()
            }
        } catch (_: Exception) {}
        wakeLock = null
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun releasePlayer() {
        try {
            mediaPlayer?.stop()
            mediaPlayer?.release()
        } catch (_: Exception) {}
        mediaPlayer = null
    }

    private fun createAdhanNotification(prayerName: String, muezzinName: String): android.app.Notification {
        val appIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pendingAppIntent = PendingIntent.getActivity(
            this,
            NOTIFICATION_ID,
            appIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val stopIntent = Intent(this, AdhanForegroundService::class.java).apply {
            action = ACTION_STOP_ADHAN
        }
        val pendingStopIntent = PendingIntent.getService(
            this,
            NOTIFICATION_ID + 1,
            stopIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("🕌 حان الآن موعد أذان $prayerName")
            .setContentText("حيّ على الصلاة، حيّ على الفلاح • $muezzinName")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("الله أكبر، الله أكبر.. حان الآن موعد أذان $prayerName.\nبصوت: $muezzinName\nالصلاة خير من النوم، حيّ على الصلاة حيّ على الفلاح.")
            )
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setContentIntent(pendingAppIntent)
            .addAction(android.R.drawable.ic_media_pause, "إيقاف الأذان", pendingStopIntent)
            .setOngoing(true)
            .setAutoCancel(false)
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(
                CHANNEL_ID,
                "صوت الأذان والنداء للصلاة",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "تشغيل صوت الأذان الشريف كاملاً عند دخول وقت الصلاة حتى لو كان التطبيق مغلقاً"
                enableVibration(true)
                lockscreenVisibility = android.app.Notification.VISIBILITY_PUBLIC
            }
            manager.createNotificationChannel(channel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopAdhanAndFinish()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
