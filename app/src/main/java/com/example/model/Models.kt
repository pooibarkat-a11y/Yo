package com.example.model

enum class RevelationType(val arabicName: String) {
    MAKKI("مكية"),
    MADANI("مدنية")
}

data class Surah(
    val id: Int,
    val nameArabic: String,
    val nameEnglish: String,
    val englishTranslation: String,
    val revelationType: RevelationType,
    val versesCount: Int,
    val startPage: Int,
    val juzNumber: Int
)

data class Ayah(
    val surahId: Int,
    val ayahNumber: Int,
    val textUthmani: String,
    val translation: String,
    val tafsir: String = "",
    val asbabNuzul: String = "",
    val pageNumber: Int = 1,
    val juzNumber: Int = 1
)

data class Reciter(
    val id: String,
    val nameArabic: String,
    val nameEnglish: String,
    val style: String,
    val serverFolder: String,
    val isVerseByVerse: Boolean = true,
    val imageUrl: String? = null
) {
    val nameAr: String get() = nameArabic
}

data class AzkarCategory(
    val id: String,
    val nameArabic: String,
    val iconName: String,
    val items: List<DhikrItem>
)

data class DhikrItem(
    val id: Int,
    val text: String,
    val virtue: String,
    val targetCount: Int,
    var currentCount: Int = 0,
    val reference: String = ""
)

enum class AppThemeMode {
    LIGHT,
    DARK,
    SEPIA // Comfort Eye Mode
}

data class WordDiff(
    val originalWord: String,
    val spokenWord: String?,
    val isCorrect: Boolean,
    val isMissing: Boolean = false,
    val isExtra: Boolean = false
)

data class HifzTestResult(
    val surahId: Int,
    val surahName: String,
    val startAyah: Int,
    val endAyah: Int,
    val totalWords: Int,
    val correctWords: Int,
    val accuracyPercentage: Int,
    val wordDiffs: List<WordDiff>,
    val timestamp: Long = System.currentTimeMillis()
)

data class Badge(
    val id: String,
    val title: String,
    val description: String,
    val iconName: String,
    val isUnlocked: Boolean = false
)

enum class MushafDisplayMode(val titleArabic: String, val subtitleArabic: String) {
    PHYSICAL_PAGES("مصحف المدينة الورقي (٦٠٤ صفحة)", "تقليب صفحات المصحف الأصيل تماماً كالمصحف الشريف بين يديك"),
    CONTINUOUS_PAGE("المصحف النصي المتصل", "نصوص متتالية بالرسم العثماني"),
    TAJWEED("مصحف التجويد الملون", "تلوين أحكام المدود والغنن والقلقلة والإقلاب"),
    TAFSIR_INLINE("المصحف المفسر", "عرض التفسير الميسر المعتمد مباشرة تحت كل آية"),
    HIFZ_MODE("مصحف الحفظ والتسميع", "إخفاء الآيات واختبار الذاكرة مع عداد المتقن"),
    CLASSIC("مصحف الآيات المستقلة", "بطاقات آيات مستقلة مع أدوات الحفظ والاستماع"),
    LARGE_FONT("مصحف القراءة الكبرى", "خط عثماني ضخم جداً لكبار السن وراحة العين")
}

enum class MushafPaperColor(val titleArabic: String) {
    PARCHMENT("ورق قديم بيج"),
    MADINAH_GREEN("أخضر نبوي"),
    DARK("ليلي هادئ"),
    PURE_WHITE("أبيض صافي")
}

enum class QuranTab(val titleArabic: String) {
    INDEX("فهرس السور"),
    READER("عرض المصحف")
}

