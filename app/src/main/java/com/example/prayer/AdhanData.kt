package com.example.prayer

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.PowerManager
import com.example.R

data class Muezzin(
    val id: String,
    val nameArabic: String,
    val mosque: String,
    val audioUrl: String,
    val fajrAudioUrl: String? = null,
    val imageUrl: String? = null,
    val category: String = "عام"
)

object AdhanData {
    val muezzins: List<Muezzin> = ALL_MUEZZINS

    var selectedMuezzin: Muezzin = ALL_MUEZZINS.first()

    data class DuaAfterAdhanSheikh(
        val id: String,
        val nameArabic: String,
        val description: String,
        val audioUrl: String
    )

    val duaAfterAdhanSheikhs = listOf(
        DuaAfterAdhanSheikh(
            id = "mashary",
            nameArabic = "الشيخ مشاري راشد العفاسي",
            description = "التسجيل الشجي الأشهر لدعاء الوسيلة والفضيلة بعد الأذان",
            audioUrl = "https://archive.org/download/athan_sound/after_azan_haram_mashary.mp3"
        ),
        DuaAfterAdhanSheikh(
            id = "shaarawy",
            nameArabic = "فضيلة الشيخ محمد متولي الشعراوي",
            description = "الدعاء بصوت إمام الدعاة رحمه الله بخشوع ووقار وإيمان",
            audioUrl = "https://archive.org/download/athan_sound/alshrawy_cut.mp3"
        ),
        DuaAfterAdhanSheikh(
            id = "haram_makkah",
            nameArabic = "تسجيل الحرم المكي الشريف",
            description = "دعاء ما بعد الأذان من رحاب المسجد الحرام بمكة المكرمة",
            audioUrl = "https://archive.org/download/athan_sound/after_azan_haram.mp3"
        )
    )

    var isDuaAfterAdhanEnabled: Boolean = true
    var selectedDuaSheikhId: String = "mashary"
}

class AdhanAudioController(private val context: Context) {
    private var mediaPlayer: MediaPlayer? = null
    var isPlaying: Boolean = false
        private set
    var currentlyPlayingPrayer: String? = null
        private set

    fun playAdhan(
        muezzin: Muezzin = AdhanData.selectedMuezzin,
        prayerName: String = "الصلاة",
        onAdhanFinished: () -> Unit = {},
        onCompletion: () -> Unit = {}
    ) {
        stopAdhan()
        try {
            val player = MediaPlayer()
            player.setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            try {
                player.setWakeMode(context.applicationContext, PowerManager.PARTIAL_WAKE_LOCK)
            } catch (_: Exception) {}
            player.setVolume(1.0f, 1.0f)

            val urlToPlay = if (prayerName.contains("الفجر") && !muezzin.fajrAudioUrl.isNullOrBlank()) {
                muezzin.fajrAudioUrl
            } else {
                muezzin.audioUrl
            }

            player.setDataSource(context.applicationContext, Uri.parse(urlToPlay))
            player.setOnPreparedListener {
                it.setVolume(1.0f, 1.0f)
                it.start()
                this.isPlaying = true
                this.currentlyPlayingPrayer = prayerName
            }
            player.setOnCompletionListener {
                this.isPlaying = false
                this.currentlyPlayingPrayer = null
                onAdhanFinished()
                if (AdhanData.isDuaAfterAdhanEnabled) {
                    playDuaDirectly(AdhanData.selectedDuaSheikhId, onCompletion)
                } else {
                    onCompletion()
                }
            }
            player.setOnErrorListener { _, _, _ ->
                playOfflineFallback(prayerName, onAdhanFinished, onCompletion)
                true
            }
            player.prepareAsync()
            mediaPlayer = player
        } catch (_: Exception) {
            playOfflineFallback(prayerName, onAdhanFinished, onCompletion)
        }
    }

    fun playDuaDirectly(
        sheikhId: String = AdhanData.selectedDuaSheikhId,
        onCompletion: () -> Unit = {}
    ) {
        stopAdhan()
        val sheikh = AdhanData.duaAfterAdhanSheikhs.find { it.id == sheikhId }
            ?: AdhanData.duaAfterAdhanSheikhs.first()
        try {
            val player = MediaPlayer()
            player.setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            try {
                player.setWakeMode(context.applicationContext, PowerManager.PARTIAL_WAKE_LOCK)
            } catch (_: Exception) {}
            player.setVolume(1.0f, 1.0f)
            player.setDataSource(context.applicationContext, Uri.parse(sheikh.audioUrl))
            player.setOnPreparedListener {
                it.start()
                this.isPlaying = true
                this.currentlyPlayingPrayer = "دعاء ما بعد الأذان"
            }
            player.setOnCompletionListener {
                this.isPlaying = false
                this.currentlyPlayingPrayer = null
                onCompletion()
            }
            player.setOnErrorListener { _, _, _ ->
                this.isPlaying = false
                this.currentlyPlayingPrayer = null
                onCompletion()
                true
            }
            player.prepareAsync()
            mediaPlayer = player
        } catch (_: Exception) {
            this.isPlaying = false
            this.currentlyPlayingPrayer = null
            onCompletion()
        }
    }

    private fun playOfflineFallback(
        prayerName: String,
        onAdhanFinished: () -> Unit = {},
        onCompletion: () -> Unit = {}
    ) {
        try {
            stopAdhan()
            val fallbackPlayer = MediaPlayer.create(context.applicationContext, R.raw.adhan_default)
            if (fallbackPlayer != null) {
                fallbackPlayer.setVolume(1.0f, 1.0f)
                fallbackPlayer.setOnCompletionListener {
                    this.isPlaying = false
                    this.currentlyPlayingPrayer = null
                    onAdhanFinished()
                    if (AdhanData.isDuaAfterAdhanEnabled) {
                        playDuaDirectly(AdhanData.selectedDuaSheikhId, onCompletion)
                    } else {
                        onCompletion()
                    }
                }
                fallbackPlayer.start()
                this.isPlaying = true
                this.currentlyPlayingPrayer = prayerName
                this.mediaPlayer = fallbackPlayer
            }
        } catch (_: Exception) {
            isPlaying = false
            currentlyPlayingPrayer = null
        }
    }

    fun stopAdhan() {
        try {
            mediaPlayer?.stop()
            mediaPlayer?.release()
        } catch (_: Exception) {}
        mediaPlayer = null
        isPlaying = false
        currentlyPlayingPrayer = null
    }
}
