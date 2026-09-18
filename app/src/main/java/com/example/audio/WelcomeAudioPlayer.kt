package com.example.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.widget.Toast
import com.example.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object WelcomeAudioPlayer {

    private const val PREFS_NAME = "welcome_audio_prefs"
    private const val KEY_WELCOME_ENABLED = "key_welcome_audio_enabled"

    private var mediaPlayer: MediaPlayer? = null
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    fun isEnabled(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_WELCOME_ENABLED, true)
    }

    fun setEnabled(context: Context, enabled: Boolean) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(KEY_WELCOME_ENABLED, enabled).apply()
    }

    // Play Salawat on Prophet Muhammad ﷺ upon app startup
    fun playWelcomeRecitation(context: Context, onCompletion: () -> Unit = {}) {
        playSalawat(context, onCompletion)
    }

    fun playSalawat(context: Context, onCompletion: () -> Unit = {}) {
        if (!isEnabled(context)) return

        stop()
        try {
            val player = MediaPlayer.create(context.applicationContext, R.raw.salawat) ?: run {
                val afd = context.resources.openRawResourceFd(R.raw.salawat)
                val mp = MediaPlayer()
                mp.setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                mp.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                afd.close()
                mp.prepare()
                mp
            }
            player.setVolume(1.0f, 1.0f)
            player.setOnCompletionListener {
                stop()
                onCompletion()
            }
            player.setOnErrorListener { _, _, _ ->
                stop()
                false
            }
            mediaPlayer = player
            player.start()
            _isPlaying.value = true
        } catch (_: Exception) {
            stop()
        }
    }

    fun stop() {
        try {
            mediaPlayer?.stop()
            mediaPlayer?.release()
        } catch (_: Exception) {}
        mediaPlayer = null
        _isPlaying.value = false
    }
}
