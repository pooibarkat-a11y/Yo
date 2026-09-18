package com.example.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.io.File
import com.example.R

class DeveloperDuaRecorder(private val context: Context) {

    private var mediaRecorder: MediaRecorder? = null
    private var mediaPlayer: MediaPlayer? = null
    private var timerJob: Job? = null

    private val _isRecording = MutableStateFlow(false)
    val isRecording: StateFlow<Boolean> = _isRecording.asStateFlow()

    private val _recordingSeconds = MutableStateFlow(0)
    val recordingSeconds: StateFlow<Int> = _recordingSeconds.asStateFlow()

    private val _isPlayingUserDua = MutableStateFlow(false)
    val isPlayingUserDua: StateFlow<Boolean> = _isPlayingUserDua.asStateFlow()

    private val _isPlayingBuiltinDua = MutableStateFlow(false)
    val isPlayingBuiltinDua: StateFlow<Boolean> = _isPlayingBuiltinDua.asStateFlow()

    private val outputFile: File by lazy {
        File(context.filesDir, "user_dua_for_youssef.m4a")
    }

    private val _hasCustomDua = MutableStateFlow(false)
    val hasCustomDua: StateFlow<Boolean> = _hasCustomDua.asStateFlow()

    init {
        _hasCustomDua.value = outputFile.exists() && outputFile.length() > 0
    }

    fun startRecording(coroutineScope: CoroutineScope): Boolean {
        stopPlayback()
        return try {
            val recorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                MediaRecorder(context)
            } else {
                @Suppress("DEPRECATION")
                MediaRecorder()
            }

            recorder.setAudioSource(MediaRecorder.AudioSource.MIC)
            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            recorder.setAudioEncodingBitRate(128000)
            recorder.setAudioSamplingRate(44100)
            recorder.setOutputFile(outputFile.absolutePath)

            recorder.prepare()
            recorder.start()

            mediaRecorder = recorder
            _isRecording.value = true
            _recordingSeconds.value = 0

            timerJob = coroutineScope.launch(Dispatchers.Main) {
                while (isActive && _isRecording.value) {
                    delay(1000)
                    _recordingSeconds.value += 1
                }
            }
            true
        } catch (e: Exception) {
            _isRecording.value = false
            Toast.makeText(context, "يرجى منح إذن الميكروفون لتسجيل الدعاء", Toast.LENGTH_SHORT).show()
            false
        }
    }

    fun stopRecording(): Boolean {
        timerJob?.cancel()
        timerJob = null

        return try {
            mediaRecorder?.stop()
            mediaRecorder?.release()
            mediaRecorder = null
            _isRecording.value = false
            _hasCustomDua.value = outputFile.exists() && outputFile.length() > 0
            Toast.makeText(context, "تم حفظ دعائك الصالح للمطور يوسف بنجاح! جزاك الله خيراً", Toast.LENGTH_LONG).show()
            true
        } catch (e: Exception) {
            mediaRecorder = null
            _isRecording.value = false
            false
        }
    }

    fun playUserRecordedDua() {
        if (!outputFile.exists()) {
            Toast.makeText(context, "لم يتم تسجيل دعاء بعد", Toast.LENGTH_SHORT).show()
            return
        }

        stopPlayback()
        try {
            val player = MediaPlayer()
            player.setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            player.setDataSource(context, Uri.fromFile(outputFile))
            player.setOnPreparedListener { mp ->
                mp.start()
                _isPlayingUserDua.value = true
            }
            player.setOnCompletionListener {
                stopPlayback()
            }
            player.setOnErrorListener { _, _, _ ->
                stopPlayback()
                false
            }
            mediaPlayer = player
            player.prepareAsync()
        } catch (e: Exception) {
            stopPlayback()
        }
    }

    // Play dedicated recorded Dua for developer Eng. Youssef Mahmoud Fawzy
    fun playBuiltinHonoringDua() {
        if (_isPlayingBuiltinDua.value) {
            stopPlayback()
            return
        }

        stopPlayback()
        try {
            val player = MediaPlayer.create(context.applicationContext, R.raw.dua_developer) ?: run {
                val afd = context.resources.openRawResourceFd(R.raw.dua_developer)
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
                stopPlayback()
            }
            player.setOnErrorListener { _, _, _ ->
                stopPlayback()
                false
            }
            mediaPlayer = player
            player.start()
            _isPlayingBuiltinDua.value = true
            Toast.makeText(context, "اللهم بارك في الباشمهندس يوسف محمود فوزي 🤲", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            stopPlayback()
            Toast.makeText(context, "تعذر تشغيل الصوت: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }

    fun togglePlayBuiltinDua() {
        if (_isPlayingBuiltinDua.value) {
            stopPlayback()
        } else {
            playBuiltinHonoringDua()
        }
    }

    fun togglePlayUserDua() {
        if (_isPlayingUserDua.value) {
            stopPlayback()
        } else {
            playUserRecordedDua()
        }
    }

    fun stopPlayback() {
        try {
            mediaPlayer?.stop()
            mediaPlayer?.release()
        } catch (_: Exception) {}
        mediaPlayer = null
        _isPlayingUserDua.value = false
        _isPlayingBuiltinDua.value = false
    }

    fun deleteUserDua() {
        stopPlayback()
        if (outputFile.exists()) {
            outputFile.delete()
        }
        _hasCustomDua.value = false
        Toast.makeText(context, "تم حذف التسجيل الصوتي", Toast.LENGTH_SHORT).show()
    }
}
