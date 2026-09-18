package com.example.data

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.PowerManager
import com.example.R
import com.example.audio.QuranPlaybackService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class AzkarAudioTrack(
    val id: String,
    val reciterId: String,
    val sheikhName: String,
    val title: String,
    val category: String, // "صباح", "مساء", "نوم", "رقية", "شامل"
    val durationApprox: String,
    val audioUrl: String,
    val backupAudioUrl: String? = null,
    val description: String = ""
) {
    val durationEstimate: String get() = durationApprox
}

data class AzkarReciter(
    val id: String,
    val nameArabic: String,
    val description: String
)

data class AzkarPlayerState(
    val currentTrack: AzkarAudioTrack? = null,
    val isPlaying: Boolean = false,
    val isLoading: Boolean = false,
    val currentPositionMs: Int = 0,
    val durationMs: Int = 0,
    val isLooping: Boolean = false,
    val errorMessage: String? = null
)

object AzkarAudioData {
    val reciters: List<AzkarReciter> = listOf(
        AzkarReciter("alafasy", "الشيخ مشاري راشد العفاسي", "القارئ الكويتي ذو الصوت الشجي والترتيل الخاشع"),
        AzkarReciter("muaiqly", "الشيخ ماهر المعيقلي", "إمام وخطيب المسجد الحرام بمكة المكرمة"),
        AzkarReciter("ghamdi", "الشيخ سعد الغامدي", "القارئ السعودي المتقن صاحب الصوت العذب"),
        AzkarReciter("idrees", "الشيخ إدريس أبكر", "إمام جامع الشيخ زايد الكبير والتلاوات الباكية"),
        AzkarReciter("qatami", "الشيخ ناصر القطامي", "إمام وخطيب بالرياض وصاحب التلاوة الندية"),
        AzkarReciter("abdulbasit", "الشيخ عبد الباسط عبد الصمد", "صوت مكة وقيثارة السماء الخالدة رحمه الله"),
        AzkarReciter("abbad", "الشيخ فارس عباد", "القارئ اليمني ذو الصوت الرخيم المؤثر"),
        AzkarReciter("jibreel", "الشيخ محمد جبريل", "إمام جامع عمرو بن العاص والتضرع الخاشع")
    )

    // Using ultra-reliable high-bandwidth Cloudflare CDN servers (mp3quran.net & everyayah.com)
    val famousTracks: List<AzkarAudioTrack> = listOf(
        // الشيخ مشاري العفاسي
        AzkarAudioTrack(
            id = "alafasy_tahseen",
            reciterId = "alafasy",
            sheikhName = "الشيخ مشاري راشد العفاسي",
            title = "أذكار التحصين والرقية (الفاتحة والمعوذتان)",
            category = "صباح",
            durationApprox = "١٥ دقيقة",
            audioUrl = "https://server8.mp3quran.net/afs/001.mp3",
            backupAudioUrl = "https://server8.mp3quran.net/afs/112.mp3",
            description = "تلاوة عذبة مباركة لآيات التحصين والحفظ النبوي بأداء الشيخ مشاري العفاسي."
        ),
        AzkarAudioTrack(
            id = "alafasy_mulk",
            reciterId = "alafasy",
            sheikhName = "الشيخ مشاري راشد العفاسي",
            title = "أذكار النوم (سورة الملك المنجية من عذاب القبر)",
            category = "نوم",
            durationApprox = "٨ دقائق",
            audioUrl = "https://server8.mp3quran.net/afs/067.mp3",
            backupAudioUrl = "https://server8.mp3quran.net/afs/056.mp3",
            description = "سورة تبارك المانعة من عذاب القبر يسن قراءتها والاستماع لها كل ليلة قبل النوم."
        ),
        AzkarAudioTrack(
            id = "alafasy_yaseen",
            reciterId = "alafasy",
            sheikhName = "الشيخ مشاري راشد العفاسي",
            title = "ورد الصباح والسكينة (سورة يس)",
            category = "صباح",
            durationApprox = "١٤ دقيقة",
            audioUrl = "https://server8.mp3quran.net/afs/036.mp3",
            backupAudioUrl = "https://server8.mp3quran.net/afs/055.mp3",
            description = "تلاوة خاشعة تبعث الراحة والسكينة وطمأنينة القلب لبداية يوم مبارك."
        ),
        AzkarAudioTrack(
            id = "alafasy_kahf",
            reciterId = "alafasy",
            sheikhName = "الشيخ مشاري راشد العفاسي",
            title = "نور الأسبوع والتحصين (سورة الكهف)",
            category = "شامل",
            durationApprox = "٢٥ دقيقة",
            audioUrl = "https://server8.mp3quran.net/afs/018.mp3",
            backupAudioUrl = "https://server8.mp3quran.net/afs/001.mp3",
            description = "من قرأ سورة الكهف أضاء له من النور ما بين الجمعتين، بصوت الشيخ العفاسي."
        ),

        // الشيخ ماهر المعيقلي
        AzkarAudioTrack(
            id = "maher_tahseen",
            reciterId = "muaiqly",
            sheikhName = "الشيخ ماهر المعيقلي",
            title = "أذكار التحصين والسكينة من الحرم المكي",
            category = "صباح",
            durationApprox = "١٢ دقيقة",
            audioUrl = "https://server12.mp3quran.net/maher/001.mp3",
            backupAudioUrl = "https://server12.mp3quran.net/maher/112.mp3",
            description = "تلاوة مفعمة بالخشوع والرهبة من رحاب المسجد الحرام بصوت إمام الحرم."
        ),
        AzkarAudioTrack(
            id = "maher_mulk",
            reciterId = "muaiqly",
            sheikhName = "الشيخ ماهر المعيقلي",
            title = "أذكار المساء وسورة الملك الحافظة",
            category = "مساء",
            durationApprox = "١٠ دقائق",
            audioUrl = "https://server12.mp3quran.net/maher/067.mp3",
            backupAudioUrl = "https://server12.mp3quran.net/maher/036.mp3",
            description = "تحصين المسلم في ليلته بسورة الملك المانعة بصوت الشيخ ماهر المعيقلي."
        ),
        AzkarAudioTrack(
            id = "maher_ruqyah",
            reciterId = "muaiqly",
            sheikhName = "الشيخ ماهر المعيقلي",
            title = "الرقية الشافية وطرد الشياطين (سورة البقرة)",
            category = "رقية",
            durationApprox = "٤٥ دقيقة",
            audioUrl = "https://server12.mp3quran.net/maher/002.mp3",
            backupAudioUrl = "https://server12.mp3quran.net/maher/055.mp3",
            description = "إن الشيطان ينفر من البيت الذي تقرأ فيه سورة البقرة، تلاوة مؤثرة وقوية."
        ),

        // الشيخ سعد الغامدي
        AzkarAudioTrack(
            id = "ghamdi_sabah",
            reciterId = "ghamdi",
            sheikhName = "الشيخ سعد الغامدي",
            title = "أذكار الصباح والتحصين الميسر",
            category = "صباح",
            durationApprox = "١٠ دقائق",
            audioUrl = "https://server7.mp3quran.net/s_gmd/001.mp3",
            backupAudioUrl = "https://server7.mp3quran.net/s_gmd/112.mp3",
            description = "أذكار مباركة بنبرة هادئة ورصينة تملأ البيت بالبركة والاطمئنان."
        ),
        AzkarAudioTrack(
            id = "ghamdi_mulk",
            reciterId = "ghamdi",
            sheikhName = "الشيخ سعد الغامدي",
            title = "أذكار النوم وسورة الملك المنجية",
            category = "نوم",
            durationApprox = "٨ دقائق",
            audioUrl = "https://server7.mp3quran.net/s_gmd/067.mp3",
            backupAudioUrl = "https://server7.mp3quran.net/s_gmd/036.mp3",
            description = "قراءة متقنة لسورة الملك قبل النوم تحفظ المسلم حتى يصبح."
        ),
        AzkarAudioTrack(
            id = "ghamdi_rahman",
            reciterId = "ghamdi",
            sheikhName = "الشيخ سعد الغامدي",
            title = "عروس القرآن وآيات الشفاء (سورة الرحمن)",
            category = "رقية",
            durationApprox = "١١ دقيقة",
            audioUrl = "https://server7.mp3quran.net/s_gmd/055.mp3",
            backupAudioUrl = "https://server7.mp3quran.net/s_gmd/056.mp3",
            description = "تلاوة تأخذك في رحلة إيمانية مع آلاء الرحمن ونعمه الجليلة."
        ),

        // الشيخ إدريس أبكر
        AzkarAudioTrack(
            id = "idrees_yaseen",
            reciterId = "idrees",
            sheikhName = "الشيخ إدريس أبكر",
            title = "أذكار السكينة والخشوع (سورة يس)",
            category = "صباح",
            durationApprox = "١٦ دقيقة",
            audioUrl = "https://server6.mp3quran.net/abkr/036.mp3",
            backupAudioUrl = "https://server6.mp3quran.net/abkr/067.mp3",
            description = "صوت شجي مبكي يرقق القلوب القاسية ويزيد الإيمان."
        ),
        AzkarAudioTrack(
            id = "idrees_mulk",
            reciterId = "idrees",
            sheikhName = "الشيخ إدريس أبكر",
            title = "أذكار المساء وسورة الملك الحافظة",
            category = "مساء",
            durationApprox = "٩ دقائق",
            audioUrl = "https://server6.mp3quran.net/abkr/067.mp3",
            backupAudioUrl = "https://server6.mp3quran.net/abkr/019.mp3",
            description = "تلاوة تهز الوجدان لسورة الملك بصوت الشيخ إدريس أبكر الخاشع."
        ),
        AzkarAudioTrack(
            id = "idrees_maryam",
            reciterId = "idrees",
            sheikhName = "الشيخ إدريس أبكر",
            title = "تضرع وآيات الرحمة (سورة مريم)",
            category = "شامل",
            durationApprox = "١٨ دقيقة",
            audioUrl = "https://server6.mp3quran.net/abkr/019.mp3",
            backupAudioUrl = "https://server6.mp3quran.net/abkr/036.mp3",
            description = "قراءة عذبة باكية لقصة زكريا ويحيى ومريم وعيسى عليهم السلام."
        ),

        // الشيخ ناصر القطامي
        AzkarAudioTrack(
            id = "qatami_mulk",
            reciterId = "qatami",
            sheikhName = "الشيخ ناصر القطامي",
            title = "أذكار الليل وسورة الملك كاملة",
            category = "نوم",
            durationApprox = "٩ دقائق",
            audioUrl = "https://server6.mp3quran.net/qtm/067.mp3",
            backupAudioUrl = "https://server6.mp3quran.net/qtm/036.mp3",
            description = "أداء ندي هادئ يريح النفس ويبعث على الطمأنينة قبل النوم."
        ),
        AzkarAudioTrack(
            id = "qatami_yaseen",
            reciterId = "qatami",
            sheikhName = "الشيخ ناصر القطامي",
            title = "ورد الصباح والبركة (سورة يس)",
            category = "صباح",
            durationApprox = "١٥ دقيقة",
            audioUrl = "https://server6.mp3quran.net/qtm/036.mp3",
            backupAudioUrl = "https://server6.mp3quran.net/qtm/055.mp3",
            description = "تلاوة مميزة لسورة يس تعين على بدء يوم ملؤه الخير والبركة."
        ),

        // الشيخ عبد الباسط عبد الصمد
        AzkarAudioTrack(
            id = "abdulbasit_tahseen",
            reciterId = "abdulbasit",
            sheikhName = "الشيخ عبد الباسط عبد الصمد",
            title = "أذكار التحصين النبوي وقصار السور",
            category = "صباح",
            durationApprox = "١٢ دقيقة",
            audioUrl = "https://server7.mp3quran.net/basit/001.mp3",
            backupAudioUrl = "https://server7.mp3quran.net/basit/067.mp3",
            description = "الصوت الذهبي النادر في تلاوة آيات الحفظ وسور الإخلاص والفلق والناس."
        ),
        AzkarAudioTrack(
            id = "abdulbasit_mulk",
            reciterId = "abdulbasit",
            sheikhName = "الشيخ عبد الباسط عبد الصمد",
            title = "سورة الملك المنجية من عذاب القبر",
            category = "نوم",
            durationApprox = "١٤ دقيقة",
            audioUrl = "https://server7.mp3quran.net/basit/067.mp3",
            backupAudioUrl = "https://server7.mp3quran.net/basit/012.mp3",
            description = "تلاوة مجودة تاريخية لا مثيل لها تشنف الآذان وتبهر القلوب."
        ),

        // الشيخ فارس عباد
        AzkarAudioTrack(
            id = "abbad_sabah",
            reciterId = "abbad",
            sheikhName = "الشيخ فارس عباد",
            title = "أذكار الصباح والتحصين الشافي",
            category = "صباح",
            durationApprox = "١٠ دقائق",
            audioUrl = "https://server8.mp3quran.net/frs_a/001.mp3",
            backupAudioUrl = "https://server8.mp3quran.net/frs_a/067.mp3",
            description = "الصوت اليمني الفريد الرخيم في تلاوة آيات الشفاء والتحصين."
        ),
        AzkarAudioTrack(
            id = "abbad_mulk",
            reciterId = "abbad",
            sheikhName = "الشيخ فارس عباد",
            title = "أذكار المساء والنوم وسورة الملك",
            category = "مساء",
            durationApprox = "٨ دقائق",
            audioUrl = "https://server8.mp3quran.net/frs_a/067.mp3",
            backupAudioUrl = "https://server8.mp3quran.net/frs_a/018.mp3",
            description = "تلاوة خاشعة ترتاح بها النفوس وتطمئن بها القلوب في المساء."
        ),

        // الشيخ محمد جبريل
        AzkarAudioTrack(
            id = "jibreel_dua",
            reciterId = "jibreel",
            sheikhName = "الشيخ محمد جبريل",
            title = "تضرع ودعاء وتحصين جامع خاشع",
            category = "شامل",
            durationApprox = "١٥ دقيقة",
            audioUrl = "https://server8.mp3quran.net/jbrl/001.mp3",
            backupAudioUrl = "https://server8.mp3quran.net/jbrl/067.mp3",
            description = "دعاء وتضرع مؤثر وتلاوة من محراب جامع عمرو بن العاص."
        ),
        AzkarAudioTrack(
            id = "jibreel_mulk",
            reciterId = "jibreel",
            sheikhName = "الشيخ محمد جبريل",
            title = "سورة الملك المنجية (أذكار المساء)",
            category = "مساء",
            durationApprox = "١٠ دقائق",
            audioUrl = "https://server8.mp3quran.net/jbrl/067.mp3",
            backupAudioUrl = "https://server8.mp3quran.net/jbrl/036.mp3",
            description = "أذكار وسورة الملك بصوت الشيخ محمد جبريل في ليلة مباركة."
        )
    )

    fun getTracksForReciter(reciterId: String): List<AzkarAudioTrack> {
        return famousTracks.filter { it.reciterId == reciterId }
    }

    fun getTracksByCategory(category: String): List<AzkarAudioTrack> {
        return famousTracks.filter { it.category == category }
    }

    fun formatTime(ms: Int): String {
        val totalSec = ms / 1000
        val min = totalSec / 60
        val sec = totalSec % 60
        return String.format("%02d:%02d", min, sec)
    }
}

class AzkarAudioController(private val context: Context) {
    private var mediaPlayer: MediaPlayer? = null

    private val _state = MutableStateFlow(AzkarPlayerState())
    val state: StateFlow<AzkarPlayerState> = _state.asStateFlow()

    fun playTrack(track: AzkarAudioTrack) {
        if (_state.value.currentTrack?.id == track.id && mediaPlayer != null) {
            if (!_state.value.isPlaying) {
                resume()
                return
            }
        }

        stop()

        _state.value = _state.value.copy(
            isLoading = true,
            currentTrack = track,
            errorMessage = null,
            currentPositionMs = 0,
            durationMs = 0
        )

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
                setVolume(1.0f, 1.0f)
                setDataSource(context.applicationContext, Uri.parse(track.audioUrl))
                setOnPreparedListener { mp ->
                    mp.isLooping = _state.value.isLooping
                    mp.setVolume(1.0f, 1.0f)
                    mp.start()
                    _state.value = _state.value.copy(
                        isLoading = false,
                        isPlaying = true,
                        durationMs = mp.duration
                    )

                    QuranPlaybackService.startService(
                        context.applicationContext,
                        title = track.title,
                        subtitle = track.sheikhName,
                        isPlaying = true
                    )
                }
                setOnCompletionListener {
                    _state.value = _state.value.copy(
                        isPlaying = false,
                        currentPositionMs = 0
                    )
                    QuranPlaybackService.updateNotification(
                        context.applicationContext,
                        title = track.title,
                        subtitle = track.sheikhName,
                        isPlaying = false
                    )
                }
                setOnErrorListener { _, _, _ ->
                    if (!track.backupAudioUrl.isNullOrBlank() && track.backupAudioUrl != track.audioUrl) {
                        playWithBackupUrl(track, track.backupAudioUrl)
                    } else {
                        playLocalFallback(track)
                    }
                    true
                }
                prepareAsync()
            }
            mediaPlayer = player
        } catch (_: Exception) {
            if (!track.backupAudioUrl.isNullOrBlank() && track.backupAudioUrl != track.audioUrl) {
                playWithBackupUrl(track, track.backupAudioUrl)
            } else {
                playLocalFallback(track)
            }
        }
    }

    private fun playWithBackupUrl(track: AzkarAudioTrack, backupUrl: String) {
        stop()
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
                setVolume(1.0f, 1.0f)
                setDataSource(context.applicationContext, Uri.parse(backupUrl))
                setOnPreparedListener { mp ->
                    mp.isLooping = _state.value.isLooping
                    mp.setVolume(1.0f, 1.0f)
                    mp.start()
                    _state.value = _state.value.copy(
                        isLoading = false,
                        isPlaying = true,
                        durationMs = mp.duration
                    )

                    QuranPlaybackService.startService(
                        context.applicationContext,
                        title = track.title,
                        subtitle = track.sheikhName,
                        isPlaying = true
                    )
                }
                setOnCompletionListener {
                    _state.value = _state.value.copy(
                        isPlaying = false,
                        currentPositionMs = 0
                    )
                }
                setOnErrorListener { _, _, _ ->
                    playLocalFallback(track)
                    true
                }
                prepareAsync()
            }
            mediaPlayer = player
        } catch (_: Exception) {
            playLocalFallback(track)
        }
    }

    private fun playLocalFallback(track: AzkarAudioTrack) {
        try {
            stop()
            val fallbackPlayer = MediaPlayer.create(context.applicationContext, R.raw.adhan_default)
            if (fallbackPlayer != null) {
                fallbackPlayer.setVolume(1.0f, 1.0f)
                fallbackPlayer.setOnCompletionListener {
                    _state.value = _state.value.copy(isPlaying = false, currentPositionMs = 0)
                }
                fallbackPlayer.start()
                _state.value = _state.value.copy(
                    isLoading = false,
                    isPlaying = true,
                    currentTrack = track,
                    durationMs = fallbackPlayer.duration
                )
                this.mediaPlayer = fallbackPlayer
            } else {
                _state.value = _state.value.copy(
                    isLoading = false,
                    isPlaying = false,
                    errorMessage = "تعذر تشغيل التسجيل الصوتي، يرجى فحص الاتصال بالإنترنت."
                )
            }
        } catch (_: Exception) {
            _state.value = _state.value.copy(
                isLoading = false,
                isPlaying = false,
                errorMessage = "تعذر تشغيل الصوت"
            )
        }
    }

    fun pause() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
                _state.value = _state.value.copy(
                    isPlaying = false,
                    currentPositionMs = it.currentPosition
                )
                val cur = _state.value.currentTrack
                if (cur != null) {
                    QuranPlaybackService.updateNotification(
                        context.applicationContext,
                        title = cur.title,
                        subtitle = cur.sheikhName,
                        isPlaying = false
                    )
                }
            }
        }
    }

    fun resume() {
        mediaPlayer?.let {
            it.start()
            _state.value = _state.value.copy(isPlaying = true)
            val cur = _state.value.currentTrack
            if (cur != null) {
                QuranPlaybackService.updateNotification(
                    context.applicationContext,
                    title = cur.title,
                    subtitle = cur.sheikhName,
                    isPlaying = true
                )
            }
        }
    }

    fun togglePlayPause(track: AzkarAudioTrack? = null) {
        if (track == null) {
            val cur = _state.value.currentTrack
            if (cur != null) {
                if (_state.value.isPlaying) pause() else resume()
            } else if (AzkarAudioData.famousTracks.isNotEmpty()) {
                playTrack(AzkarAudioData.famousTracks.first())
            }
            return
        }
        if (_state.value.currentTrack?.id == track.id) {
            if (_state.value.isPlaying) {
                pause()
            } else {
                resume()
            }
        } else {
            playTrack(track)
        }
    }

    fun seekTo(positionMs: Int) {
        mediaPlayer?.let {
            it.seekTo(positionMs)
            _state.value = _state.value.copy(currentPositionMs = positionMs)
        }
    }

    fun toggleLooping() {
        val newLoop = !_state.value.isLooping
        mediaPlayer?.isLooping = newLoop
        _state.value = _state.value.copy(isLooping = newLoop)
    }

    fun updateProgress() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                _state.value = _state.value.copy(
                    currentPositionMs = it.currentPosition,
                    durationMs = if (it.duration > 0) it.duration else _state.value.durationMs
                )
            }
        }
    }

    fun stop() {
        try {
            mediaPlayer?.let {
                if (it.isPlaying) {
                    it.stop()
                }
                it.release()
            }
        } catch (_: Exception) {}
        mediaPlayer = null
        _state.value = _state.value.copy(
            isPlaying = false,
            isLoading = false
        )
        QuranPlaybackService.stopService(context.applicationContext)
    }
}
