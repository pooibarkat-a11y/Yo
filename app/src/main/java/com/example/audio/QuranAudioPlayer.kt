package com.example.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.PowerManager
import com.example.data.QuranData
import com.example.model.Ayah
import com.example.model.Reciter
import com.example.model.Surah
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class RepeatMode {
    NONE,
    REPEAT_AYAH,
    REPEAT_SURAH
}

data class AudioPlayerState(
    val isPlaying: Boolean = false,
    val isLoading: Boolean = false,
    val currentSurah: Surah? = null,
    val currentAyahNumber: Int = 1,
    val currentAyahText: String = "",
    val selectedReciter: Reciter = QuranData.reciters.first(),
    val playbackSpeed: Float = 1.0f,
    val vocalPitch: Float = 1.0f, // 100% pure, natural, original authentic vocal tone of the reciter
    val repeatMode: RepeatMode = RepeatMode.NONE,
    val sleepTimerMinutesRemaining: Int = 0,
    val durationMs: Int = 0,
    val currentPositionMs: Int = 0,
    val errorMessage: String? = null,
    val isSoundBoosterEnabled: Boolean = true,
    val isCurrentAyahOffline: Boolean = false,
    val isCurrentSurahFullyDownloaded: Boolean = false
)

class QuranAudioPlayer(
    private val context: Context,
    private val scope: CoroutineScope
) {
    private var mediaPlayer: MediaPlayer? = null
    private var progressJob: Job? = null
    private var sleepTimerJob: Job? = null
    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as? android.media.AudioManager

    private val _state = MutableStateFlow(AudioPlayerState())
    val state: StateFlow<AudioPlayerState> = _state.asStateFlow()

    var onAyahChanged: ((surahId: Int, ayahNumber: Int) -> Unit)? = null

    fun toggleSoundBooster() {
        val newState = !_state.value.isSoundBoosterEnabled
        _state.value = _state.value.copy(isSoundBoosterEnabled = newState)
        applySoundBoost(newState)
    }

    private fun applySoundBoost(enabled: Boolean) {
        try {
            val volume = if (enabled) 1.0f else 0.85f
            mediaPlayer?.setVolume(volume, volume)
        } catch (e: Exception) {
            // Ignored
        }
    }

    fun selectReciter(reciter: Reciter) {
        _state.value = _state.value.copy(selectedReciter = reciter)
        if (_state.value.isPlaying) {
            val s = _state.value.currentSurah
            if (s != null) {
                playAyah(s, _state.value.currentAyahNumber)
            }
        }
    }

    fun setPlaybackSpeed(speed: Float) {
        _state.value = _state.value.copy(playbackSpeed = speed)
        applyPlaybackParams()
    }

    fun setVocalPitch(pitch: Float) {
        _state.value = _state.value.copy(vocalPitch = pitch)
        applyPlaybackParams()
    }

    private fun applyPlaybackParams() {
        try {
            val speed = _state.value.playbackSpeed
            val pitch = _state.value.vocalPitch
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                // If both are exact standard 1.0, don't mangle audio unless explicitly requested
                if (speed == 1.0f && pitch == 1.0f) {
                    mediaPlayer?.playbackParams = android.media.PlaybackParams().setSpeed(1.0f).setPitch(1.0f)
                } else {
                    val params = android.media.PlaybackParams()
                        .setSpeed(speed)
                        .setPitch(pitch)
                        .setAudioFallbackMode(android.media.PlaybackParams.AUDIO_FALLBACK_MODE_DEFAULT)
                    mediaPlayer?.playbackParams = params
                }
            }
        } catch (e: Exception) {
            // Speed/pitch change might not be supported on older devices
        }
    }

    fun setRepeatMode(mode: RepeatMode) {
        _state.value = _state.value.copy(repeatMode = mode)
    }

    fun startSleepTimer(minutes: Int) {
        sleepTimerJob?.cancel()
        if (minutes <= 0) {
            _state.value = _state.value.copy(sleepTimerMinutesRemaining = 0)
            return
        }
        _state.value = _state.value.copy(sleepTimerMinutesRemaining = minutes)
        sleepTimerJob = scope.launch(Dispatchers.Main) {
            var remaining = minutes
            while (remaining > 0) {
                delay(60000L)
                remaining--
                _state.value = _state.value.copy(sleepTimerMinutesRemaining = remaining)
            }
            pause()
        }
    }

    fun playSurah(surah: Surah, startAyah: Int = 1) {
        playAyah(surah, startAyah)
    }

    fun playAyah(surah: Surah, ayahNumber: Int) {
        playAyahInternal(surah, ayahNumber)
    }

    private fun playAyahInternal(surah: Surah, ayahNumber: Int) {
        val reciter = _state.value.selectedReciter
        val ayahs = QuranData.getAyahsForSurah(surah.id)
        val targetAyah = ayahs.find { it.ayahNumber == ayahNumber } ?: ayahs.firstOrNull()
        val text = if (reciter.isVerseByVerse) {
            targetAyah?.textUthmani ?: "الآية $ayahNumber"
        } else {
            "تلاوة كاملة لسورة ${surah.nameArabic} بصوت القارئ ${reciter.nameAr}"
        }

        val localFile = QuranAudioDownloader.getLocalAyahFile(reciter.id, surah.id, ayahNumber)
        val isOffline = localFile != null && localFile.exists() && localFile.length() > 1024
        val isFullyDownloaded = QuranAudioDownloader.isSurahDownloaded(reciter.id, surah.id)

        _state.value = _state.value.copy(
            isLoading = true,
            currentSurah = surah,
            currentAyahNumber = if (reciter.isVerseByVerse) ayahNumber else 1,
            currentAyahText = text,
            isCurrentAyahOffline = isOffline,
            isCurrentSurahFullyDownloaded = isFullyDownloaded,
            errorMessage = null
        )

        val audioSource = if (isOffline) {
            localFile!!.absolutePath
        } else {
            QuranData.getAudioUrl(reciter, surah.id, ayahNumber)
        }

        releasePlayer()

        try {
            val player = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                try {
                    setWakeMode(context.applicationContext, PowerManager.PARTIAL_WAKE_LOCK)
                } catch (_: Exception) {}
                setDataSource(audioSource)
                setOnPreparedListener { mp ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        isPlaying = true,
                        durationMs = mp.duration
                    )
                    applySoundBoost(_state.value.isSoundBoosterEnabled)
                    applyPlaybackParams()
                    mp.start()
                    startProgressTracker()
                    onAyahChanged?.invoke(surah.id, ayahNumber)

                    QuranPlaybackService.startService(
                        context.applicationContext,
                        title = "سورة ${surah.nameArabic} - الآية $ayahNumber",
                        subtitle = "بصوت القارئ ${reciter.nameAr}",
                        isPlaying = true
                    )
                }
                setOnCompletionListener {
                    handleAyahCompletion(surah, ayahNumber)
                }
                setOnErrorListener { _, _, _ ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        isPlaying = false,
                        errorMessage = "تعذر تشغيل تلاوة القارئ ${reciter.nameAr} (تحقق من الاتصال بالإنترنت)"
                    )
                    QuranPlaybackService.stopService(context.applicationContext)
                    true
                }
                prepareAsync()
            }
            mediaPlayer = player
        } catch (e: Exception) {
            _state.value = _state.value.copy(
                isLoading = false,
                isPlaying = false,
                errorMessage = "تعذر تشغيل تلاوة ${reciter.nameAr}: ${e.localizedMessage ?: "خطأ في الشبكة"}"
            )
        }
    }

    private fun handleAyahCompletion(surah: Surah, finishedAyahNumber: Int) {
        val reciter = _state.value.selectedReciter
        if (!reciter.isVerseByVerse) {
            when (_state.value.repeatMode) {
                RepeatMode.REPEAT_AYAH, RepeatMode.REPEAT_SURAH -> {
                    playAyah(surah, 1)
                }
                RepeatMode.NONE -> {
                    val nextSurah = QuranData.surahs.find { it.id == surah.id + 1 }
                    if (nextSurah != null) {
                        playAyah(nextSurah, 1)
                    } else {
                        pause()
                    }
                }
            }
            return
        }

        when (_state.value.repeatMode) {
            RepeatMode.REPEAT_AYAH -> {
                playAyah(surah, finishedAyahNumber)
            }
            RepeatMode.REPEAT_SURAH -> {
                if (finishedAyahNumber >= surah.versesCount) {
                    playAyah(surah, 1)
                } else {
                    playAyah(surah, finishedAyahNumber + 1)
                }
            }
            RepeatMode.NONE -> {
                if (finishedAyahNumber < surah.versesCount) {
                    playAyah(surah, finishedAyahNumber + 1)
                } else {
                    // Next surah or stop
                    val nextSurah = QuranData.surahs.find { it.id == surah.id + 1 }
                    if (nextSurah != null) {
                        playAyah(nextSurah, 1)
                    } else {
                        pause()
                    }
                }
            }
        }
    }

    fun togglePlayPause() {
        val player = mediaPlayer
        if (player != null) {
            if (player.isPlaying) {
                player.pause()
                _state.value = _state.value.copy(isPlaying = false)
                QuranPlaybackService.updateNotification(
                    context.applicationContext,
                    title = "سورة ${_state.value.currentSurah?.nameArabic ?: ""} - الآية ${_state.value.currentAyahNumber}",
                    subtitle = "بصوت القارئ ${_state.value.selectedReciter.nameAr}",
                    isPlaying = false
                )
            } else {
                player.start()
                _state.value = _state.value.copy(isPlaying = true)
                startProgressTracker()
                QuranPlaybackService.updateNotification(
                    context.applicationContext,
                    title = "سورة ${_state.value.currentSurah?.nameArabic ?: ""} - الآية ${_state.value.currentAyahNumber}",
                    subtitle = "بصوت القارئ ${_state.value.selectedReciter.nameAr}",
                    isPlaying = true
                )
            }
        } else {
            val s = _state.value.currentSurah ?: QuranData.surahs.first()
            playAyah(s, _state.value.currentAyahNumber)
        }
    }

    fun pause() {
        mediaPlayer?.pause()
        _state.value = _state.value.copy(isPlaying = false)
        QuranPlaybackService.updateNotification(
            context.applicationContext,
            title = "سورة ${_state.value.currentSurah?.nameArabic ?: ""} - الآية ${_state.value.currentAyahNumber}",
            subtitle = "بصوت القارئ ${_state.value.selectedReciter.nameAr}",
            isPlaying = false
        )
    }

    fun nextAyah() {
        val s = _state.value.currentSurah ?: return
        val reciter = _state.value.selectedReciter
        if (!reciter.isVerseByVerse) {
            val nextSurah = QuranData.surahs.find { it.id == s.id + 1 }
            if (nextSurah != null) {
                playAyah(nextSurah, 1)
            }
            return
        }
        if (_state.value.currentAyahNumber < s.versesCount) {
            playAyah(s, _state.value.currentAyahNumber + 1)
        } else {
            val nextSurah = QuranData.surahs.find { it.id == s.id + 1 }
            if (nextSurah != null) {
                playAyah(nextSurah, 1)
            }
        }
    }

    fun previousAyah() {
        val s = _state.value.currentSurah ?: return
        val reciter = _state.value.selectedReciter
        if (!reciter.isVerseByVerse) {
            val prevSurah = QuranData.surahs.find { it.id == s.id - 1 }
            if (prevSurah != null) {
                playAyah(prevSurah, 1)
            }
            return
        }
        if (_state.value.currentAyahNumber > 1) {
            playAyah(s, _state.value.currentAyahNumber - 1)
        } else {
            val prevSurah = QuranData.surahs.find { it.id == s.id - 1 }
            if (prevSurah != null) {
                playAyah(prevSurah, prevSurah.versesCount)
            }
        }
    }

    fun seekTo(positionMs: Int) {
        mediaPlayer?.seekTo(positionMs)
        _state.value = _state.value.copy(currentPositionMs = positionMs)
    }

    private fun startProgressTracker() {
        progressJob?.cancel()
        progressJob = scope.launch(Dispatchers.Main) {
            while (_state.value.isPlaying && mediaPlayer != null) {
                try {
                    val pos = mediaPlayer?.currentPosition ?: 0
                    val dur = mediaPlayer?.duration ?: 0
                    _state.value = _state.value.copy(currentPositionMs = pos, durationMs = dur)
                } catch (e: Exception) {
                    // Ignore state errors during release
                }
                delay(500L)
            }
        }
    }

    private fun releasePlayer() {
        progressJob?.cancel()
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        QuranPlaybackService.stopService(context.applicationContext)
    }

    fun stop() {
        releasePlayer()
        _state.value = _state.value.copy(isPlaying = false, currentPositionMs = 0)
    }

    fun onDestroy() {
        sleepTimerJob?.cancel()
        releasePlayer()
    }
}
