package com.example.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object NasheedAudioPlayer {
    private var mediaPlayer: MediaPlayer? = null

    private val _currentNasheedId = MutableStateFlow<String?>(null)
    val currentNasheedId: StateFlow<String?> = _currentNasheedId.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun togglePlay(id: String, audioUrl: String, context: Context? = null) {
        if (_currentNasheedId.value == id && _isPlaying.value) {
            pause()
            return
        }
        if (_currentNasheedId.value == id && !_isPlaying.value && mediaPlayer != null) {
            resume()
            return
        }

        stop()
        try {
            AzkarAudioPlayer.stop()
        } catch (_: Exception) {}
        _currentNasheedId.value = id
        _isLoading.value = true

        try {
            mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                val localFile = if (context != null) NasheedDownloader.getLocalFile(context, id) else null
                if (localFile != null && localFile.exists()) {
                    setDataSource(localFile.absolutePath)
                } else {
                    val headers = mapOf("User-Agent" to "Mozilla/5.0 (Android; Mobile)")
                    if (context != null) {
                        setDataSource(context.applicationContext, Uri.parse(audioUrl), headers)
                    } else {
                        setDataSource(audioUrl)
                    }
                }
                setOnPreparedListener { mp ->
                    _isLoading.value = false
                    _isPlaying.value = true
                    mp.start()
                }
                setOnCompletionListener {
                    _isPlaying.value = false
                    _currentNasheedId.value = null
                    _isLoading.value = false
                }
                setOnErrorListener { _, _, _ ->
                    _isLoading.value = false
                    _isPlaying.value = false
                    _currentNasheedId.value = null
                    true
                }
                prepareAsync()
            }
        } catch (_: Exception) {
            _isLoading.value = false
            _isPlaying.value = false
            _currentNasheedId.value = null
        }
    }

    fun pause() {
        try {
            mediaPlayer?.pause()
            _isPlaying.value = false
        } catch (_: Exception) {}
    }

    fun resume() {
        try {
            mediaPlayer?.start()
            _isPlaying.value = true
        } catch (_: Exception) {}
    }

    fun stop() {
        try {
            mediaPlayer?.stop()
            mediaPlayer?.release()
        } catch (_: Exception) {}
        mediaPlayer = null
        _isPlaying.value = false
        _isLoading.value = false
        _currentNasheedId.value = null
    }
}
