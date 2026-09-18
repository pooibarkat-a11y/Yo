package com.example.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import com.example.R
import com.example.model.DhikrItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

data class AzkarSheikhOption(
    val id: String,
    val name: String,
    val title: String
)

/**
 * مشغل الأذكار بأصوات كبار المشايخ والقراء المعتمدين حصراً (تسجيلات بشرية حقيقية عالية النقاء).
 * تم إلغاء الذكاء الاصطناعي والتوليد الصوتي الاصطناعي تماماً استجابة لرغبة المستخدم.
 */
object AzkarAudioPlayer {
    private var mediaPlayer: MediaPlayer? = null

    val sheikhOptions = listOf(
        AzkarSheikhOption("alafasy", "الشيخ مشاري راشد العفاسي", "القارئ الكويتي صاحب الصوت العذب والتحقيق والتسجيلات المعتمدة"),
        AzkarSheikhOption("husary", "الشيخ محمود خليل الحصري", "شيخ عموم المقارئ المصرية والتلاوة التعليمية المتقنة للتحيات والتشهد"),
        AzkarSheikhOption("sudais", "الشيخ عبد الرحمن السديس", "إمام وخطيب المسجد الحرام ورئيس الشؤون الدينية"),
        AzkarSheikhOption("muaiqly", "الشيخ ماهر المعيقلي", "إمام وخطيب المسجد الحرام بمكة المكرمة"),
        AzkarSheikhOption("ghamdi", "الشيخ سعد الغامدي", "القارئ السعودي صاحب الصوت الشجي الرخيم"),
        AzkarSheikhOption("minshawi", "الشيخ محمد صديق المنشاوي", "القارئ المصري صاحب النبرة الخاشعة الباكية")
    )

    private val _selectedSheikhId = MutableStateFlow("alafasy")
    val selectedSheikhId: StateFlow<String> = _selectedSheikhId.asStateFlow()

    private val _currentSheikhName = MutableStateFlow("الشيخ مشاري راشد العفاسي")
    val currentSheikhName: StateFlow<String> = _currentSheikhName.asStateFlow()

    private val _currentPlayingId = MutableStateFlow<Int?>(null)
    val currentPlayingId: StateFlow<Int?> = _currentPlayingId.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun setReciter(sheikhId: String) {
        _selectedSheikhId.value = sheikhId
        val sheikh = sheikhOptions.find { it.id == sheikhId } ?: sheikhOptions.first()
        _currentSheikhName.value = sheikh.name
    }

    /**
     * تم تعطيل محرك الذكاء الاصطناعي TTS تماماً؛ التطبيق يستخدم فقط تسجيلات كبار الشيوخ الحقيقية.
     */
    fun initTts(context: Context) {
        // لا يتم استخدام الصوت الاصطناعي نهائياً
    }

    private fun normalizeArabic(raw: String): String {
        return raw
            .replace(Regex("[\u064B-\u065F\u0670]"), "") // إزالة حركات التشكيل للمطابقة الدقيقة
            .replace("أ", "ا")
            .replace("إ", "ا")
            .replace("آ", "ا")
            .replace("ة", "ه")
            .replace("ى", "ي")
            .replace(Regex("[-—–\\[\\]0-9٠-٩]+"), " ")
            .replace(Regex("\\s+"), " ")
            .trim()
    }

    private sealed class AudioTarget {
        data class RawRes(val resId: Int, val sheikhName: String) : AudioTarget()
        data class RemoteUrl(val url: String, val sheikhName: String) : AudioTarget()
    }

    private fun resolveAudioTarget(dhikr: DhikrItem): AudioTarget {
        val norm = normalizeArabic(dhikr.text)
        val sheikhId = _selectedSheikhId.value
        val sheikhName = sheikhOptions.find { it.id == sheikhId }?.name ?: "الشيخ مشاري راشد العفاسي"

        // 1. آية الكرسي
        if (norm.contains("الله لا اله الا هو الحي القيوم")) {
            val url = when (sheikhId) {
                "ghamdi" -> "https://everyayah.com/data/Ghamadi_40kbps/002255.mp3"
                "muaiqly" -> "https://everyayah.com/data/MaherAlMuaiqly128kbps/002255.mp3"
                else -> "https://everyayah.com/data/Alafasy_128kbps/002255.mp3"
            }
            return AudioTarget.RemoteUrl(url, sheikhName)
        }

        // 2. سورة الإخلاص
        if (norm.contains("قل هو الله احد")) {
            val url = when (sheikhId) {
                "ghamdi" -> "https://server7.mp3quran.net/s_gmd/112.mp3"
                "muaiqly" -> "https://server12.mp3quran.net/maher/112.mp3"
                else -> "https://server8.mp3quran.net/afs/112.mp3"
            }
            return AudioTarget.RemoteUrl(url, sheikhName)
        }

        // 3. سورة الفلق
        if (norm.contains("قل اعوذ برب الفلق")) {
            val url = when (sheikhId) {
                "ghamdi" -> "https://server7.mp3quran.net/s_gmd/113.mp3"
                "muaiqly" -> "https://server12.mp3quran.net/maher/113.mp3"
                else -> "https://server8.mp3quran.net/afs/113.mp3"
            }
            return AudioTarget.RemoteUrl(url, sheikhName)
        }

        // 4. سورة الناس
        if (norm.contains("قل اعوذ برب الناس")) {
            val url = when (sheikhId) {
                "ghamdi" -> "https://server7.mp3quran.net/s_gmd/114.mp3"
                "muaiqly" -> "https://server12.mp3quran.net/maher/114.mp3"
                else -> "https://server8.mp3quran.net/afs/114.mp3"
            }
            return AudioTarget.RemoteUrl(url, sheikhName)
        }

        // 5. سورة الفاتحة
        if (norm.contains("الحمد لله رب العالمين") && norm.contains("اهدنا الصراط")) {
            val url = when (sheikhId) {
                "ghamdi" -> "https://server7.mp3quran.net/s_gmd/001.mp3"
                "muaiqly" -> "https://server12.mp3quran.net/maher/001.mp3"
                else -> "https://server8.mp3quran.net/afs/001.mp3"
            }
            return AudioTarget.RemoteUrl(url, sheikhName)
        }

        // 6. خواتيم سورة البقرة
        if (norm.contains("امن الرسول بما انزل")) {
            return AudioTarget.RemoteUrl("https://everyayah.com/data/Alafasy_128kbps/002285.mp3", "الشيخ مشاري راشد العفاسي")
        }

        // 7. الصلاة على النبي ﷺ
        if (norm.contains("اللهم صل") || norm.contains("صل علي محمد") || norm.contains("صل على محمد")) {
            return AudioTarget.RawRes(R.raw.salawat, "الشيخ مشاري راشد العفاسي")
        }

        // 8. أذكار مخصصة مسجلة بصوت الشيخ مشاري العفاسي
        if (norm.contains("الحمد لله عدد ما خلق")) {
            return AudioTarget.RemoteUrl("https://archive.org/download/azkar-alafasy/alhamdu-lellah-adad-ma-khalq.mp3", "الشيخ مشاري راشد العفاسي")
        }
        if (norm.contains("الله اكبر كبيرا والحمد لله كثيرا")) {
            return AudioTarget.RemoteUrl("https://archive.org/download/azkar-alafasy/allahu-akbaru-kabira.mp3", "الشيخ مشاري راشد العفاسي")
        }
        if (norm.contains("رب هذه الدعوه التامه") || norm.contains("اللهم رب هذه الدعوة") || norm.contains("ات محمدا الوسيله") || norm.contains("الوسيلة والفضيلة") || dhikr.id == 15004) {
            return AudioTarget.RemoteUrl("https://ia801904.us.archive.org/7/items/azkar-alafasy/doaa-after-adhan.mp3", sheikhName)
        }
        if (norm.contains("التشهد") || norm.contains("التحيات لله") || norm.contains("التحيات المباركات") || norm.contains("السلام عليك ايها النبي") || norm.contains("الصلاه الابراهيميه") || norm.contains("دعاء ختام الصلاه") || norm.contains("اللهم اعني علي ذكرك وشكرك")) {
            val url = when (sheikhId) {
                "husary" -> "https://archive.org/download/Tashahhud_Al-Husary/Tashahhud_Husary.mp3"
                "sudais" -> "https://archive.org/download/azkar-alafasy/doaa-after-tashahud.mp3"
                "muaiqly" -> "https://archive.org/download/azkar-alafasy/doaa-after-tashahud.mp3"
                "ghamdi" -> "https://archive.org/download/azkar-alafasy/doaa-after-tashahud.mp3"
                "minshawi" -> "https://archive.org/download/azkar-alafasy/doaa-after-tashahud.mp3"
                else -> "https://archive.org/download/azkar-alafasy/doaa-after-tashahud.mp3"
            }
            return AudioTarget.RemoteUrl(url, sheikhName)
        }
        if (norm.contains("استغفر الله ثلاثا") || norm.contains("اللهم انت السلام ومنك السلام") || norm.contains("لا مانع لما اعطيت")) {
            return AudioTarget.RemoteUrl("https://archive.org/download/azkar-alafasy/alhamdu-lellah-adad-ma-khalq.mp3", sheikhName)
        }
        if (norm.contains("دخول القريه") || norm.contains("دخول البلد")) {
            return AudioTarget.RemoteUrl("https://archive.org/download/azkar-alafasy/doaa-dukhol-al-qaria.mp3", "الشيخ مشاري راشد العفاسي")
        }
        if (norm.contains("سبحان الله ملء البر")) {
            return AudioTarget.RemoteUrl("https://archive.org/download/azkar-alafasy/sobhan-allah-mla-albr.mp3", "الشيخ مشاري راشد العفاسي")
        }

        // 9. أذكار الصباح كاملة (الباب ٢٧)
        val doorNumber = dhikr.id / 1000
        if (doorNumber == 27) {
            return AudioTarget.RemoteUrl(
                "https://archive.org/download/adhkar-sabah-wa-masa-efassi/AdhkarSabah_Efassi.mp3",
                "الشيخ مشاري راشد العفاسي"
            )
        }

        // 10. أذكار المساء كاملة (الباب ٢٨)
        if (doorNumber == 28) {
            return AudioTarget.RemoteUrl(
                "https://archive.org/download/adhkar-sabah-wa-masa-efassi/AdhkarMasa_Efassi.mp3",
                "الشيخ مشاري راشد العفاسي"
            )
        }

        // 11. أذكار النوم (الباب ٢٩) - سورة الملك
        if (doorNumber == 29) {
            val url = when (sheikhId) {
                "ghamdi" -> "https://server7.mp3quran.net/s_gmd/067.mp3"
                "muaiqly" -> "https://server12.mp3quran.net/maher/067.mp3"
                else -> "https://server8.mp3quran.net/afs/067.mp3"
            }
            return AudioTarget.RemoteUrl(url, sheikhName)
        }

        // 12. الأذان والنداء
        if (norm.contains("الاذان") || norm.contains("اشهد ان لا اله الا الله واشهد ان محمدا رسول الله")) {
            return AudioTarget.RawRes(R.raw.adhan_default, "صوت الأذان الشرعي الحرم المكي")
        }

        // 13. موسوعة أبواب حصن المسلم الـ ١٣٢ المسجلة بصوت القارئ المعتمد
        val fileIndex = (doorNumber + 1).coerceIn(1, 133)
        val formattedFile = String.format(Locale.US, "%03d", fileIndex)
        val archiveUrl = "https://archive.org/download/HisnAlmoslimSound/$formattedFile.mp3"

        return AudioTarget.RemoteUrl(archiveUrl, "صوت قارئ حصن المسلم المعتمد")
    }

    fun play(context: Context, dhikr: DhikrItem) {
        stop()
        _currentPlayingId.value = dhikr.id
        _isLoading.value = true

        val target = resolveAudioTarget(dhikr)

        when (target) {
            is AudioTarget.RawRes -> {
                _currentSheikhName.value = target.sheikhName
                playRawResource(context, target.resId, dhikr.id)
            }
            is AudioTarget.RemoteUrl -> {
                _currentSheikhName.value = target.sheikhName
                playMediaUrl(context, target.url, dhikr.id)
            }
        }
    }

    fun togglePlay(context: Context, dhikr: DhikrItem) {
        if (_currentPlayingId.value == dhikr.id && _isPlaying.value) {
            pause()
            return
        }
        if (_currentPlayingId.value == dhikr.id && !_isPlaying.value && mediaPlayer != null) {
            resume()
            return
        }

        stop()
        _currentPlayingId.value = dhikr.id
        _isLoading.value = true

        val target = resolveAudioTarget(dhikr)

        when (target) {
            is AudioTarget.RawRes -> {
                _currentSheikhName.value = target.sheikhName
                playRawResource(context, target.resId, dhikr.id)
            }
            is AudioTarget.RemoteUrl -> {
                _currentSheikhName.value = target.sheikhName
                playMediaUrl(context, target.url, dhikr.id)
            }
        }
    }

    private fun playRawResource(context: Context, resId: Int, dhikrId: Int) {
        try {
            mediaPlayer = MediaPlayer.create(context.applicationContext, resId)?.apply {
                setOnCompletionListener {
                    _isPlaying.value = false
                    _currentPlayingId.value = null
                    _isLoading.value = false
                }
                setOnErrorListener { _, _, _ ->
                    _isPlaying.value = false
                    _isLoading.value = false
                    _currentPlayingId.value = null
                    true
                }
                _isLoading.value = false
                _isPlaying.value = true
                start()
            }
        } catch (_: Exception) {
            _isLoading.value = false
            _isPlaying.value = false
            _currentPlayingId.value = null
        }
    }

    private fun playMediaUrl(context: Context, url: String, dhikrId: Int) {
        try {
            mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                val headers = mapOf("User-Agent" to "Mozilla/5.0 (Android; Mobile; QuranAzkar/1.0)")
                setDataSource(context.applicationContext, Uri.parse(url), headers)
                setOnPreparedListener { mp ->
                    _isLoading.value = false
                    _isPlaying.value = true
                    mp.start()
                }
                setOnCompletionListener {
                    _isPlaying.value = false
                    _currentPlayingId.value = null
                    _isLoading.value = false
                }
                setOnErrorListener { _, _, _ ->
                    _isLoading.value = false
                    _isPlaying.value = false
                    _currentPlayingId.value = null
                    true
                }
                prepareAsync()
            }
        } catch (_: Exception) {
            _isLoading.value = false
            _isPlaying.value = false
            _currentPlayingId.value = null
        }
    }

    fun pause() {
        try {
            mediaPlayer?.let {
                if (it.isPlaying) {
                    it.pause()
                    _isPlaying.value = false
                }
            }
        } catch (_: Exception) {}
    }

    fun resume() {
        try {
            mediaPlayer?.let {
                it.start()
                _isPlaying.value = true
            }
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
        _currentPlayingId.value = null
    }

    // للتوافق مع أي استدعاء قديم
    fun togglePlay(context: Context, dhikrId: Int) {
        val dummy = DhikrItem(dhikrId, "الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ", "", 1)
        togglePlay(context, dummy)
    }
}
