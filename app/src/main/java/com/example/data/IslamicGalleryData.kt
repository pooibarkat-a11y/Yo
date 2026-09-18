package com.example.data

import androidx.annotation.DrawableRes
import com.example.R

data class GalleryItem(
    val id: String,
    val title: String,
    val calligraphyText: String,
    val subText: String,
    val category: String,
    val gradientColors: List<Long>,
    @DrawableRes val drawableRes: Int? = null
)

object IslamicGalleryData {
    val items: List<GalleryItem> = listOf(
        GalleryItem(
            id = "g_ramadan_1",
            title = "خلفية فانوس رمضان ومحراب الأنوار #1",
            calligraphyText = "﴿شَهْرُ رَمَضَانَ الَّذِي أُنزِلَ فِيهِ الْقُرْآنُ هُدًى لِّلنَّاسِ﴾",
            subText = "خلفية رمضانية أصلية بدقة عالية للموبايل • تقبل الله منا ومنكم صالح الأعمال والطاعات • لوحة وخلفية رمضانية #1",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = R.drawable.img_wallpaper_lantern_ramadan
        ),
        GalleryItem(
            id = "g_ramadan_2",
            title = "خلفية هلال وليالي رمضان المبارك #2",
            calligraphyText = "«مَنْ صَامَ رَمَضَانَ إِيمَانًا وَاحْتِسَابًا غُفِرَ لَهُ مَا تَقَدَّمَ مِنْ ذَنْبِهِ»",
            subText = "لوحة رمضانية فنية كاملة الألوان مع هلال الشهر الكريم • فضل صيام شهر رمضان المبارك #2",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = R.drawable.img_ramadan_kareem
        ),
        GalleryItem(
            id = "g_ramadan_3",
            title = "ليالي رمضان المباركة #3",
            calligraphyText = "«مَنْ قَامَ رَمَضَانَ إِيمَانًا وَاحْتِسَابًا غُفِرَ لَهُ مَا تَقَدَّمَ مِنْ ذَنْبِهِ»",
            subText = "فضل قيام ليالي رمضان وصلاة التراويح والتهجد في الأسحار • بطاقة رمضانية راقية #3",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_4",
            title = "نفحات الشهر الفضيل #4",
            calligraphyText = "«اللَّهُمَّ إِنَّكَ عَفُوٌّ كَرِيمٌ تُحِبُّ الْعَفْوَ فَاعْفُ عَنَّا»",
            subText = "دعاء ليلة القدر المباركة التي هي خير من ألف شهر • بطاقة رمضانية راقية #4",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_5",
            title = "روحانية التراويح والأسحار #5",
            calligraphyText = "﴿إِنَّا أَنزَلْنَاهُ فِي لَيْلَةِ الْقَدْرِ ۝ وَمَا أَدْرَاكَ مَا لَيْلَةُ الْقَدْرِ﴾",
            subText = "ليلة مباركة تتنزل فيها الملائكة والسكينة حتى مطلع الفجر • بطاقة رمضانية راقية #5",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_6",
            title = "مجالس الذكر وتلاوة القرآن #6",
            calligraphyText = "«ذَهَبَ الظَّمَأُ وَابْتَلَّتِ الْعُرُوقُ وَثَبَتَ الأَجْرُ إِنْ شَاءَ اللَّهُ»",
            subText = "دعاء الإفطار وسنة النبي ﷺ عند الفطر • بطاقة رمضانية راقية #6",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_7",
            title = "موائد الإفطار والبركة #7",
            calligraphyText = "«إِذَا دَخَلَ رَمَضَانُ فُتِّحَتْ أَبْوَابُ الْجَنَّةِ وَغُلِّقَتْ أَبْوَابُ جَهَنَّمَ»",
            subText = "بشارة النبي ﷺ بقدوم شهر الصيام والرحمات • بطاقة رمضانية راقية #7",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_8",
            title = "ساعة الإجابة عند الغروب #8",
            calligraphyText = "«الصِّيَامُ جُنَّةٌ فَلَا يَرْفُثْ وَلَا يَجْهَلْ»",
            subText = "حفظ الصيام بالصبر وحسن الخلق والسكينة • بطاقة رمضانية راقية #8",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_9",
            title = "ليلة القدر العظيمة #9",
            calligraphyText = "«لِلصَّائِمِ فَرْحَتَانِ: فَرْحَةٌ عِنْدَ فِطْرِهِ، وَفَرْحَةٌ عِنْدَ لِقَاءِ رَبِّهِ»",
            subText = "بشارة الصائمين بجزاء الله العظيم في الدنيا والآخرة • بطاقة رمضانية راقية #9",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF9D174DL, 0xFF4A044EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_10",
            title = "العشر الأواخر من رمضان #10",
            calligraphyText = "«تَسَحَّرُوا فَإِنَّ فِي السَّحُورِ بَرَكَةً»",
            subText = "بركة السحور وسنة الهادي المصطفى ﷺ • بطاقة رمضانية راقية #10",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFFB45309L, 0xFF451A03L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_11",
            title = "الاعتكاف والخلوة برب العالمين #11",
            calligraphyText = "﴿شَهْرُ رَمَضَانَ الَّذِي أُنزِلَ فِيهِ الْقُرْآنُ هُدًى لِّلنَّاسِ﴾",
            subText = "أهلاً بشهر الخير والرحمة والغفران • تقبل الله منا ومنكم صالح الأعمال والطاعات • بطاقة رمضانية راقية #11",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_12",
            title = "قبسات رمضانية إيمانية #12",
            calligraphyText = "«مَنْ صَامَ رَمَضَانَ إِيمَانًا وَاحْتِسَابًا غُفِرَ لَهُ مَا تَقَدَّمَ مِنْ ذَنْبِهِ»",
            subText = "فضل صيام شهر رمضان المبارك واحتساب الأجر عند الله تعالى • بطاقة رمضانية راقية #12",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_13",
            title = "بهاء ليالي الصيام #13",
            calligraphyText = "«مَنْ قَامَ رَمَضَانَ إِيمَانًا وَاحْتِسَابًا غُفِرَ لَهُ مَا تَقَدَّمَ مِنْ ذَنْبِهِ»",
            subText = "فضل قيام ليالي رمضان وصلاة التراويح والتهجد في الأسحار • بطاقة رمضانية راقية #13",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_14",
            title = "دموع الخاشعين في المحاريب #14",
            calligraphyText = "«اللَّهُمَّ إِنَّكَ عَفُوٌّ كَرِيمٌ تُحِبُّ الْعَفْوَ فَاعْفُ عَنَّا»",
            subText = "دعاء ليلة القدر المباركة التي هي خير من ألف شهر • بطاقة رمضانية راقية #14",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_15",
            title = "أنوار الشهر الكريم #15",
            calligraphyText = "﴿إِنَّا أَنزَلْنَاهُ فِي لَيْلَةِ الْقَدْرِ ۝ وَمَا أَدْرَاكَ مَا لَيْلَةُ الْقَدْرِ﴾",
            subText = "ليلة مباركة تتنزل فيها الملائكة والسكينة حتى مطلع الفجر • بطاقة رمضانية راقية #15",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_16",
            title = "طهارة القلوب في رمضان #16",
            calligraphyText = "«ذَهَبَ الظَّمَأُ وَابْتَلَّتِ الْعُرُوقُ وَثَبَتَ الأَجْرُ إِنْ شَاءَ اللَّهُ»",
            subText = "دعاء الإفطار وسنة النبي ﷺ عند الفطر • بطاقة رمضانية راقية #16",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_17",
            title = "أيام معدودات مباركات #17",
            calligraphyText = "«إِذَا دَخَلَ رَمَضَانُ فُتِّحَتْ أَبْوَابُ الْجَنَّةِ وَغُلِّقَتْ أَبْوَابُ جَهَنَّمَ»",
            subText = "بشارة النبي ﷺ بقدوم شهر الصيام والرحمات • بطاقة رمضانية راقية #17",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_18",
            title = "شمس المغفرة والرضوان #18",
            calligraphyText = "«الصِّيَامُ جُنَّةٌ فَلَا يَرْفُثْ وَلَا يَجْهَلْ»",
            subText = "حفظ الصيام بالصبر وحسن الخلق والسكينة • بطاقة رمضانية راقية #18",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_19",
            title = "رحمات تتنزل في السحر #19",
            calligraphyText = "«لِلصَّائِمِ فَرْحَتَانِ: فَرْحَةٌ عِنْدَ فِطْرِهِ، وَفَرْحَةٌ عِنْدَ لِقَاءِ رَبِّهِ»",
            subText = "بشارة الصائمين بجزاء الله العظيم في الدنيا والآخرة • بطاقة رمضانية راقية #19",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF9D174DL, 0xFF4A044EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_20",
            title = "ختمة كتاب الله في رمضان #20",
            calligraphyText = "«تَسَحَّرُوا فَإِنَّ فِي السَّحُورِ بَرَكَةً»",
            subText = "بركة السحور وسنة الهادي المصطفى ﷺ • بطاقة رمضانية راقية #20",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFFB45309L, 0xFF451A03L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_21",
            title = "هلال رمضان وبشائر الرحمة #21",
            calligraphyText = "﴿شَهْرُ رَمَضَانَ الَّذِي أُنزِلَ فِيهِ الْقُرْآنُ هُدًى لِّلنَّاسِ﴾",
            subText = "أهلاً بشهر الخير والرحمة والغفران • تقبل الله منا ومنكم صالح الأعمال والطاعات • بطاقة رمضانية راقية #21",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_22",
            title = "فانوس رمضان المضيء بالأنوار #22",
            calligraphyText = "«مَنْ صَامَ رَمَضَانَ إِيمَانًا وَاحْتِسَابًا غُفِرَ لَهُ مَا تَقَدَّمَ مِنْ ذَنْبِهِ»",
            subText = "فضل صيام شهر رمضان المبارك واحتساب الأجر عند الله تعالى • بطاقة رمضانية راقية #22",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_23",
            title = "ليالي رمضان المباركة #23",
            calligraphyText = "«مَنْ قَامَ رَمَضَانَ إِيمَانًا وَاحْتِسَابًا غُفِرَ لَهُ مَا تَقَدَّمَ مِنْ ذَنْبِهِ»",
            subText = "فضل قيام ليالي رمضان وصلاة التراويح والتهجد في الأسحار • بطاقة رمضانية راقية #23",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_24",
            title = "نفحات الشهر الفضيل #24",
            calligraphyText = "«اللَّهُمَّ إِنَّكَ عَفُوٌّ كَرِيمٌ تُحِبُّ الْعَفْوَ فَاعْفُ عَنَّا»",
            subText = "دعاء ليلة القدر المباركة التي هي خير من ألف شهر • بطاقة رمضانية راقية #24",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_25",
            title = "روحانية التراويح والأسحار #25",
            calligraphyText = "﴿إِنَّا أَنزَلْنَاهُ فِي لَيْلَةِ الْقَدْرِ ۝ وَمَا أَدْرَاكَ مَا لَيْلَةُ الْقَدْرِ﴾",
            subText = "ليلة مباركة تتنزل فيها الملائكة والسكينة حتى مطلع الفجر • بطاقة رمضانية راقية #25",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_26",
            title = "مجالس الذكر وتلاوة القرآن #26",
            calligraphyText = "«ذَهَبَ الظَّمَأُ وَابْتَلَّتِ الْعُرُوقُ وَثَبَتَ الأَجْرُ إِنْ شَاءَ اللَّهُ»",
            subText = "دعاء الإفطار وسنة النبي ﷺ عند الفطر • بطاقة رمضانية راقية #26",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_27",
            title = "موائد الإفطار والبركة #27",
            calligraphyText = "«إِذَا دَخَلَ رَمَضَانُ فُتِّحَتْ أَبْوَابُ الْجَنَّةِ وَغُلِّقَتْ أَبْوَابُ جَهَنَّمَ»",
            subText = "بشارة النبي ﷺ بقدوم شهر الصيام والرحمات • بطاقة رمضانية راقية #27",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_28",
            title = "ساعة الإجابة عند الغروب #28",
            calligraphyText = "«الصِّيَامُ جُنَّةٌ فَلَا يَرْفُثْ وَلَا يَجْهَلْ»",
            subText = "حفظ الصيام بالصبر وحسن الخلق والسكينة • بطاقة رمضانية راقية #28",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_29",
            title = "ليلة القدر العظيمة #29",
            calligraphyText = "«لِلصَّائِمِ فَرْحَتَانِ: فَرْحَةٌ عِنْدَ فِطْرِهِ، وَفَرْحَةٌ عِنْدَ لِقَاءِ رَبِّهِ»",
            subText = "بشارة الصائمين بجزاء الله العظيم في الدنيا والآخرة • بطاقة رمضانية راقية #29",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF9D174DL, 0xFF4A044EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_30",
            title = "العشر الأواخر من رمضان #30",
            calligraphyText = "«تَسَحَّرُوا فَإِنَّ فِي السَّحُورِ بَرَكَةً»",
            subText = "بركة السحور وسنة الهادي المصطفى ﷺ • بطاقة رمضانية راقية #30",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFFB45309L, 0xFF451A03L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_31",
            title = "الاعتكاف والخلوة برب العالمين #31",
            calligraphyText = "﴿شَهْرُ رَمَضَانَ الَّذِي أُنزِلَ فِيهِ الْقُرْآنُ هُدًى لِّلنَّاسِ﴾",
            subText = "أهلاً بشهر الخير والرحمة والغفران • تقبل الله منا ومنكم صالح الأعمال والطاعات • بطاقة رمضانية راقية #31",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_32",
            title = "قبسات رمضانية إيمانية #32",
            calligraphyText = "«مَنْ صَامَ رَمَضَانَ إِيمَانًا وَاحْتِسَابًا غُفِرَ لَهُ مَا تَقَدَّمَ مِنْ ذَنْبِهِ»",
            subText = "فضل صيام شهر رمضان المبارك واحتساب الأجر عند الله تعالى • بطاقة رمضانية راقية #32",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_33",
            title = "بهاء ليالي الصيام #33",
            calligraphyText = "«مَنْ قَامَ رَمَضَانَ إِيمَانًا وَاحْتِسَابًا غُفِرَ لَهُ مَا تَقَدَّمَ مِنْ ذَنْبِهِ»",
            subText = "فضل قيام ليالي رمضان وصلاة التراويح والتهجد في الأسحار • بطاقة رمضانية راقية #33",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_34",
            title = "دموع الخاشعين في المحاريب #34",
            calligraphyText = "«اللَّهُمَّ إِنَّكَ عَفُوٌّ كَرِيمٌ تُحِبُّ الْعَفْوَ فَاعْفُ عَنَّا»",
            subText = "دعاء ليلة القدر المباركة التي هي خير من ألف شهر • بطاقة رمضانية راقية #34",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_35",
            title = "أنوار الشهر الكريم #35",
            calligraphyText = "﴿إِنَّا أَنزَلْنَاهُ فِي لَيْلَةِ الْقَدْرِ ۝ وَمَا أَدْرَاكَ مَا لَيْلَةُ الْقَدْرِ﴾",
            subText = "ليلة مباركة تتنزل فيها الملائكة والسكينة حتى مطلع الفجر • بطاقة رمضانية راقية #35",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_36",
            title = "طهارة القلوب في رمضان #36",
            calligraphyText = "«ذَهَبَ الظَّمَأُ وَابْتَلَّتِ الْعُرُوقُ وَثَبَتَ الأَجْرُ إِنْ شَاءَ اللَّهُ»",
            subText = "دعاء الإفطار وسنة النبي ﷺ عند الفطر • بطاقة رمضانية راقية #36",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_37",
            title = "أيام معدودات مباركات #37",
            calligraphyText = "«إِذَا دَخَلَ رَمَضَانُ فُتِّحَتْ أَبْوَابُ الْجَنَّةِ وَغُلِّقَتْ أَبْوَابُ جَهَنَّمَ»",
            subText = "بشارة النبي ﷺ بقدوم شهر الصيام والرحمات • بطاقة رمضانية راقية #37",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_38",
            title = "شمس المغفرة والرضوان #38",
            calligraphyText = "«الصِّيَامُ جُنَّةٌ فَلَا يَرْفُثْ وَلَا يَجْهَلْ»",
            subText = "حفظ الصيام بالصبر وحسن الخلق والسكينة • بطاقة رمضانية راقية #38",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_39",
            title = "رحمات تتنزل في السحر #39",
            calligraphyText = "«لِلصَّائِمِ فَرْحَتَانِ: فَرْحَةٌ عِنْدَ فِطْرِهِ، وَفَرْحَةٌ عِنْدَ لِقَاءِ رَبِّهِ»",
            subText = "بشارة الصائمين بجزاء الله العظيم في الدنيا والآخرة • بطاقة رمضانية راقية #39",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF9D174DL, 0xFF4A044EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_40",
            title = "ختمة كتاب الله في رمضان #40",
            calligraphyText = "«تَسَحَّرُوا فَإِنَّ فِي السَّحُورِ بَرَكَةً»",
            subText = "بركة السحور وسنة الهادي المصطفى ﷺ • بطاقة رمضانية راقية #40",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFFB45309L, 0xFF451A03L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_41",
            title = "هلال رمضان وبشائر الرحمة #41",
            calligraphyText = "﴿شَهْرُ رَمَضَانَ الَّذِي أُنزِلَ فِيهِ الْقُرْآنُ هُدًى لِّلنَّاسِ﴾",
            subText = "أهلاً بشهر الخير والرحمة والغفران • تقبل الله منا ومنكم صالح الأعمال والطاعات • بطاقة رمضانية راقية #41",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_42",
            title = "فانوس رمضان المضيء بالأنوار #42",
            calligraphyText = "«مَنْ صَامَ رَمَضَانَ إِيمَانًا وَاحْتِسَابًا غُفِرَ لَهُ مَا تَقَدَّمَ مِنْ ذَنْبِهِ»",
            subText = "فضل صيام شهر رمضان المبارك واحتساب الأجر عند الله تعالى • بطاقة رمضانية راقية #42",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_43",
            title = "ليالي رمضان المباركة #43",
            calligraphyText = "«مَنْ قَامَ رَمَضَانَ إِيمَانًا وَاحْتِسَابًا غُفِرَ لَهُ مَا تَقَدَّمَ مِنْ ذَنْبِهِ»",
            subText = "فضل قيام ليالي رمضان وصلاة التراويح والتهجد في الأسحار • بطاقة رمضانية راقية #43",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_44",
            title = "نفحات الشهر الفضيل #44",
            calligraphyText = "«اللَّهُمَّ إِنَّكَ عَفُوٌّ كَرِيمٌ تُحِبُّ الْعَفْوَ فَاعْفُ عَنَّا»",
            subText = "دعاء ليلة القدر المباركة التي هي خير من ألف شهر • بطاقة رمضانية راقية #44",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_45",
            title = "روحانية التراويح والأسحار #45",
            calligraphyText = "﴿إِنَّا أَنزَلْنَاهُ فِي لَيْلَةِ الْقَدْرِ ۝ وَمَا أَدْرَاكَ مَا لَيْلَةُ الْقَدْرِ﴾",
            subText = "ليلة مباركة تتنزل فيها الملائكة والسكينة حتى مطلع الفجر • بطاقة رمضانية راقية #45",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_46",
            title = "مجالس الذكر وتلاوة القرآن #46",
            calligraphyText = "«ذَهَبَ الظَّمَأُ وَابْتَلَّتِ الْعُرُوقُ وَثَبَتَ الأَجْرُ إِنْ شَاءَ اللَّهُ»",
            subText = "دعاء الإفطار وسنة النبي ﷺ عند الفطر • بطاقة رمضانية راقية #46",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_47",
            title = "موائد الإفطار والبركة #47",
            calligraphyText = "«إِذَا دَخَلَ رَمَضَانُ فُتِّحَتْ أَبْوَابُ الْجَنَّةِ وَغُلِّقَتْ أَبْوَابُ جَهَنَّمَ»",
            subText = "بشارة النبي ﷺ بقدوم شهر الصيام والرحمات • بطاقة رمضانية راقية #47",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_48",
            title = "ساعة الإجابة عند الغروب #48",
            calligraphyText = "«الصِّيَامُ جُنَّةٌ فَلَا يَرْفُثْ وَلَا يَجْهَلْ»",
            subText = "حفظ الصيام بالصبر وحسن الخلق والسكينة • بطاقة رمضانية راقية #48",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_49",
            title = "ليلة القدر العظيمة #49",
            calligraphyText = "«لِلصَّائِمِ فَرْحَتَانِ: فَرْحَةٌ عِنْدَ فِطْرِهِ، وَفَرْحَةٌ عِنْدَ لِقَاءِ رَبِّهِ»",
            subText = "بشارة الصائمين بجزاء الله العظيم في الدنيا والآخرة • بطاقة رمضانية راقية #49",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF9D174DL, 0xFF4A044EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_50",
            title = "العشر الأواخر من رمضان #50",
            calligraphyText = "«تَسَحَّرُوا فَإِنَّ فِي السَّحُورِ بَرَكَةً»",
            subText = "بركة السحور وسنة الهادي المصطفى ﷺ • بطاقة رمضانية راقية #50",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFFB45309L, 0xFF451A03L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_51",
            title = "الاعتكاف والخلوة برب العالمين #51",
            calligraphyText = "﴿شَهْرُ رَمَضَانَ الَّذِي أُنزِلَ فِيهِ الْقُرْآنُ هُدًى لِّلنَّاسِ﴾",
            subText = "أهلاً بشهر الخير والرحمة والغفران • تقبل الله منا ومنكم صالح الأعمال والطاعات • بطاقة رمضانية راقية #51",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_52",
            title = "قبسات رمضانية إيمانية #52",
            calligraphyText = "«مَنْ صَامَ رَمَضَانَ إِيمَانًا وَاحْتِسَابًا غُفِرَ لَهُ مَا تَقَدَّمَ مِنْ ذَنْبِهِ»",
            subText = "فضل صيام شهر رمضان المبارك واحتساب الأجر عند الله تعالى • بطاقة رمضانية راقية #52",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_53",
            title = "بهاء ليالي الصيام #53",
            calligraphyText = "«مَنْ قَامَ رَمَضَانَ إِيمَانًا وَاحْتِسَابًا غُفِرَ لَهُ مَا تَقَدَّمَ مِنْ ذَنْبِهِ»",
            subText = "فضل قيام ليالي رمضان وصلاة التراويح والتهجد في الأسحار • بطاقة رمضانية راقية #53",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_54",
            title = "دموع الخاشعين في المحاريب #54",
            calligraphyText = "«اللَّهُمَّ إِنَّكَ عَفُوٌّ كَرِيمٌ تُحِبُّ الْعَفْوَ فَاعْفُ عَنَّا»",
            subText = "دعاء ليلة القدر المباركة التي هي خير من ألف شهر • بطاقة رمضانية راقية #54",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_55",
            title = "أنوار الشهر الكريم #55",
            calligraphyText = "﴿إِنَّا أَنزَلْنَاهُ فِي لَيْلَةِ الْقَدْرِ ۝ وَمَا أَدْرَاكَ مَا لَيْلَةُ الْقَدْرِ﴾",
            subText = "ليلة مباركة تتنزل فيها الملائكة والسكينة حتى مطلع الفجر • بطاقة رمضانية راقية #55",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_56",
            title = "طهارة القلوب في رمضان #56",
            calligraphyText = "«ذَهَبَ الظَّمَأُ وَابْتَلَّتِ الْعُرُوقُ وَثَبَتَ الأَجْرُ إِنْ شَاءَ اللَّهُ»",
            subText = "دعاء الإفطار وسنة النبي ﷺ عند الفطر • بطاقة رمضانية راقية #56",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_57",
            title = "أيام معدودات مباركات #57",
            calligraphyText = "«إِذَا دَخَلَ رَمَضَانُ فُتِّحَتْ أَبْوَابُ الْجَنَّةِ وَغُلِّقَتْ أَبْوَابُ جَهَنَّمَ»",
            subText = "بشارة النبي ﷺ بقدوم شهر الصيام والرحمات • بطاقة رمضانية راقية #57",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_58",
            title = "شمس المغفرة والرضوان #58",
            calligraphyText = "«الصِّيَامُ جُنَّةٌ فَلَا يَرْفُثْ وَلَا يَجْهَلْ»",
            subText = "حفظ الصيام بالصبر وحسن الخلق والسكينة • بطاقة رمضانية راقية #58",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_59",
            title = "رحمات تتنزل في السحر #59",
            calligraphyText = "«لِلصَّائِمِ فَرْحَتَانِ: فَرْحَةٌ عِنْدَ فِطْرِهِ، وَفَرْحَةٌ عِنْدَ لِقَاءِ رَبِّهِ»",
            subText = "بشارة الصائمين بجزاء الله العظيم في الدنيا والآخرة • بطاقة رمضانية راقية #59",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF9D174DL, 0xFF4A044EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_60",
            title = "ختمة كتاب الله في رمضان #60",
            calligraphyText = "«تَسَحَّرُوا فَإِنَّ فِي السَّحُورِ بَرَكَةً»",
            subText = "بركة السحور وسنة الهادي المصطفى ﷺ • بطاقة رمضانية راقية #60",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFFB45309L, 0xFF451A03L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_61",
            title = "هلال رمضان وبشائر الرحمة #61",
            calligraphyText = "﴿شَهْرُ رَمَضَانَ الَّذِي أُنزِلَ فِيهِ الْقُرْآنُ هُدًى لِّلنَّاسِ﴾",
            subText = "أهلاً بشهر الخير والرحمة والغفران • تقبل الله منا ومنكم صالح الأعمال والطاعات • بطاقة رمضانية راقية #61",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_62",
            title = "فانوس رمضان المضيء بالأنوار #62",
            calligraphyText = "«مَنْ صَامَ رَمَضَانَ إِيمَانًا وَاحْتِسَابًا غُفِرَ لَهُ مَا تَقَدَّمَ مِنْ ذَنْبِهِ»",
            subText = "فضل صيام شهر رمضان المبارك واحتساب الأجر عند الله تعالى • بطاقة رمضانية راقية #62",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_63",
            title = "ليالي رمضان المباركة #63",
            calligraphyText = "«مَنْ قَامَ رَمَضَانَ إِيمَانًا وَاحْتِسَابًا غُفِرَ لَهُ مَا تَقَدَّمَ مِنْ ذَنْبِهِ»",
            subText = "فضل قيام ليالي رمضان وصلاة التراويح والتهجد في الأسحار • بطاقة رمضانية راقية #63",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_64",
            title = "نفحات الشهر الفضيل #64",
            calligraphyText = "«اللَّهُمَّ إِنَّكَ عَفُوٌّ كَرِيمٌ تُحِبُّ الْعَفْوَ فَاعْفُ عَنَّا»",
            subText = "دعاء ليلة القدر المباركة التي هي خير من ألف شهر • بطاقة رمضانية راقية #64",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_65",
            title = "روحانية التراويح والأسحار #65",
            calligraphyText = "﴿إِنَّا أَنزَلْنَاهُ فِي لَيْلَةِ الْقَدْرِ ۝ وَمَا أَدْرَاكَ مَا لَيْلَةُ الْقَدْرِ﴾",
            subText = "ليلة مباركة تتنزل فيها الملائكة والسكينة حتى مطلع الفجر • بطاقة رمضانية راقية #65",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_66",
            title = "مجالس الذكر وتلاوة القرآن #66",
            calligraphyText = "«ذَهَبَ الظَّمَأُ وَابْتَلَّتِ الْعُرُوقُ وَثَبَتَ الأَجْرُ إِنْ شَاءَ اللَّهُ»",
            subText = "دعاء الإفطار وسنة النبي ﷺ عند الفطر • بطاقة رمضانية راقية #66",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_67",
            title = "موائد الإفطار والبركة #67",
            calligraphyText = "«إِذَا دَخَلَ رَمَضَانُ فُتِّحَتْ أَبْوَابُ الْجَنَّةِ وَغُلِّقَتْ أَبْوَابُ جَهَنَّمَ»",
            subText = "بشارة النبي ﷺ بقدوم شهر الصيام والرحمات • بطاقة رمضانية راقية #67",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_68",
            title = "ساعة الإجابة عند الغروب #68",
            calligraphyText = "«الصِّيَامُ جُنَّةٌ فَلَا يَرْفُثْ وَلَا يَجْهَلْ»",
            subText = "حفظ الصيام بالصبر وحسن الخلق والسكينة • بطاقة رمضانية راقية #68",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_69",
            title = "ليلة القدر العظيمة #69",
            calligraphyText = "«لِلصَّائِمِ فَرْحَتَانِ: فَرْحَةٌ عِنْدَ فِطْرِهِ، وَفَرْحَةٌ عِنْدَ لِقَاءِ رَبِّهِ»",
            subText = "بشارة الصائمين بجزاء الله العظيم في الدنيا والآخرة • بطاقة رمضانية راقية #69",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF9D174DL, 0xFF4A044EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_70",
            title = "العشر الأواخر من رمضان #70",
            calligraphyText = "«تَسَحَّرُوا فَإِنَّ فِي السَّحُورِ بَرَكَةً»",
            subText = "بركة السحور وسنة الهادي المصطفى ﷺ • بطاقة رمضانية راقية #70",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFFB45309L, 0xFF451A03L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_71",
            title = "الاعتكاف والخلوة برب العالمين #71",
            calligraphyText = "﴿شَهْرُ رَمَضَانَ الَّذِي أُنزِلَ فِيهِ الْقُرْآنُ هُدًى لِّلنَّاسِ﴾",
            subText = "أهلاً بشهر الخير والرحمة والغفران • تقبل الله منا ومنكم صالح الأعمال والطاعات • بطاقة رمضانية راقية #71",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_72",
            title = "قبسات رمضانية إيمانية #72",
            calligraphyText = "«مَنْ صَامَ رَمَضَانَ إِيمَانًا وَاحْتِسَابًا غُفِرَ لَهُ مَا تَقَدَّمَ مِنْ ذَنْبِهِ»",
            subText = "فضل صيام شهر رمضان المبارك واحتساب الأجر عند الله تعالى • بطاقة رمضانية راقية #72",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_73",
            title = "بهاء ليالي الصيام #73",
            calligraphyText = "«مَنْ قَامَ رَمَضَانَ إِيمَانًا وَاحْتِسَابًا غُفِرَ لَهُ مَا تَقَدَّمَ مِنْ ذَنْبِهِ»",
            subText = "فضل قيام ليالي رمضان وصلاة التراويح والتهجد في الأسحار • بطاقة رمضانية راقية #73",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_74",
            title = "دموع الخاشعين في المحاريب #74",
            calligraphyText = "«اللَّهُمَّ إِنَّكَ عَفُوٌّ كَرِيمٌ تُحِبُّ الْعَفْوَ فَاعْفُ عَنَّا»",
            subText = "دعاء ليلة القدر المباركة التي هي خير من ألف شهر • بطاقة رمضانية راقية #74",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_75",
            title = "أنوار الشهر الكريم #75",
            calligraphyText = "﴿إِنَّا أَنزَلْنَاهُ فِي لَيْلَةِ الْقَدْرِ ۝ وَمَا أَدْرَاكَ مَا لَيْلَةُ الْقَدْرِ﴾",
            subText = "ليلة مباركة تتنزل فيها الملائكة والسكينة حتى مطلع الفجر • بطاقة رمضانية راقية #75",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_76",
            title = "طهارة القلوب في رمضان #76",
            calligraphyText = "«ذَهَبَ الظَّمَأُ وَابْتَلَّتِ الْعُرُوقُ وَثَبَتَ الأَجْرُ إِنْ شَاءَ اللَّهُ»",
            subText = "دعاء الإفطار وسنة النبي ﷺ عند الفطر • بطاقة رمضانية راقية #76",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_77",
            title = "أيام معدودات مباركات #77",
            calligraphyText = "«إِذَا دَخَلَ رَمَضَانُ فُتِّحَتْ أَبْوَابُ الْجَنَّةِ وَغُلِّقَتْ أَبْوَابُ جَهَنَّمَ»",
            subText = "بشارة النبي ﷺ بقدوم شهر الصيام والرحمات • بطاقة رمضانية راقية #77",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_78",
            title = "شمس المغفرة والرضوان #78",
            calligraphyText = "«الصِّيَامُ جُنَّةٌ فَلَا يَرْفُثْ وَلَا يَجْهَلْ»",
            subText = "حفظ الصيام بالصبر وحسن الخلق والسكينة • بطاقة رمضانية راقية #78",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_79",
            title = "رحمات تتنزل في السحر #79",
            calligraphyText = "«لِلصَّائِمِ فَرْحَتَانِ: فَرْحَةٌ عِنْدَ فِطْرِهِ، وَفَرْحَةٌ عِنْدَ لِقَاءِ رَبِّهِ»",
            subText = "بشارة الصائمين بجزاء الله العظيم في الدنيا والآخرة • بطاقة رمضانية راقية #79",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF9D174DL, 0xFF4A044EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_80",
            title = "ختمة كتاب الله في رمضان #80",
            calligraphyText = "«تَسَحَّرُوا فَإِنَّ فِي السَّحُورِ بَرَكَةً»",
            subText = "بركة السحور وسنة الهادي المصطفى ﷺ • بطاقة رمضانية راقية #80",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFFB45309L, 0xFF451A03L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_81",
            title = "هلال رمضان وبشائر الرحمة #81",
            calligraphyText = "﴿شَهْرُ رَمَضَانَ الَّذِي أُنزِلَ فِيهِ الْقُرْآنُ هُدًى لِّلنَّاسِ﴾",
            subText = "أهلاً بشهر الخير والرحمة والغفران • تقبل الله منا ومنكم صالح الأعمال والطاعات • بطاقة رمضانية راقية #81",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_82",
            title = "فانوس رمضان المضيء بالأنوار #82",
            calligraphyText = "«مَنْ صَامَ رَمَضَانَ إِيمَانًا وَاحْتِسَابًا غُفِرَ لَهُ مَا تَقَدَّمَ مِنْ ذَنْبِهِ»",
            subText = "فضل صيام شهر رمضان المبارك واحتساب الأجر عند الله تعالى • بطاقة رمضانية راقية #82",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_83",
            title = "ليالي رمضان المباركة #83",
            calligraphyText = "«مَنْ قَامَ رَمَضَانَ إِيمَانًا وَاحْتِسَابًا غُفِرَ لَهُ مَا تَقَدَّمَ مِنْ ذَنْبِهِ»",
            subText = "فضل قيام ليالي رمضان وصلاة التراويح والتهجد في الأسحار • بطاقة رمضانية راقية #83",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_84",
            title = "نفحات الشهر الفضيل #84",
            calligraphyText = "«اللَّهُمَّ إِنَّكَ عَفُوٌّ كَرِيمٌ تُحِبُّ الْعَفْوَ فَاعْفُ عَنَّا»",
            subText = "دعاء ليلة القدر المباركة التي هي خير من ألف شهر • بطاقة رمضانية راقية #84",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_85",
            title = "روحانية التراويح والأسحار #85",
            calligraphyText = "﴿إِنَّا أَنزَلْنَاهُ فِي لَيْلَةِ الْقَدْرِ ۝ وَمَا أَدْرَاكَ مَا لَيْلَةُ الْقَدْرِ﴾",
            subText = "ليلة مباركة تتنزل فيها الملائكة والسكينة حتى مطلع الفجر • بطاقة رمضانية راقية #85",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_86",
            title = "مجالس الذكر وتلاوة القرآن #86",
            calligraphyText = "«ذَهَبَ الظَّمَأُ وَابْتَلَّتِ الْعُرُوقُ وَثَبَتَ الأَجْرُ إِنْ شَاءَ اللَّهُ»",
            subText = "دعاء الإفطار وسنة النبي ﷺ عند الفطر • بطاقة رمضانية راقية #86",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_87",
            title = "موائد الإفطار والبركة #87",
            calligraphyText = "«إِذَا دَخَلَ رَمَضَانُ فُتِّحَتْ أَبْوَابُ الْجَنَّةِ وَغُلِّقَتْ أَبْوَابُ جَهَنَّمَ»",
            subText = "بشارة النبي ﷺ بقدوم شهر الصيام والرحمات • بطاقة رمضانية راقية #87",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_88",
            title = "ساعة الإجابة عند الغروب #88",
            calligraphyText = "«الصِّيَامُ جُنَّةٌ فَلَا يَرْفُثْ وَلَا يَجْهَلْ»",
            subText = "حفظ الصيام بالصبر وحسن الخلق والسكينة • بطاقة رمضانية راقية #88",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_89",
            title = "ليلة القدر العظيمة #89",
            calligraphyText = "«لِلصَّائِمِ فَرْحَتَانِ: فَرْحَةٌ عِنْدَ فِطْرِهِ، وَفَرْحَةٌ عِنْدَ لِقَاءِ رَبِّهِ»",
            subText = "بشارة الصائمين بجزاء الله العظيم في الدنيا والآخرة • بطاقة رمضانية راقية #89",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF9D174DL, 0xFF4A044EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_90",
            title = "العشر الأواخر من رمضان #90",
            calligraphyText = "«تَسَحَّرُوا فَإِنَّ فِي السَّحُورِ بَرَكَةً»",
            subText = "بركة السحور وسنة الهادي المصطفى ﷺ • بطاقة رمضانية راقية #90",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFFB45309L, 0xFF451A03L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_91",
            title = "الاعتكاف والخلوة برب العالمين #91",
            calligraphyText = "﴿شَهْرُ رَمَضَانَ الَّذِي أُنزِلَ فِيهِ الْقُرْآنُ هُدًى لِّلنَّاسِ﴾",
            subText = "أهلاً بشهر الخير والرحمة والغفران • تقبل الله منا ومنكم صالح الأعمال والطاعات • بطاقة رمضانية راقية #91",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_92",
            title = "قبسات رمضانية إيمانية #92",
            calligraphyText = "«مَنْ صَامَ رَمَضَانَ إِيمَانًا وَاحْتِسَابًا غُفِرَ لَهُ مَا تَقَدَّمَ مِنْ ذَنْبِهِ»",
            subText = "فضل صيام شهر رمضان المبارك واحتساب الأجر عند الله تعالى • بطاقة رمضانية راقية #92",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_93",
            title = "بهاء ليالي الصيام #93",
            calligraphyText = "«مَنْ قَامَ رَمَضَانَ إِيمَانًا وَاحْتِسَابًا غُفِرَ لَهُ مَا تَقَدَّمَ مِنْ ذَنْبِهِ»",
            subText = "فضل قيام ليالي رمضان وصلاة التراويح والتهجد في الأسحار • بطاقة رمضانية راقية #93",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_94",
            title = "دموع الخاشعين في المحاريب #94",
            calligraphyText = "«اللَّهُمَّ إِنَّكَ عَفُوٌّ كَرِيمٌ تُحِبُّ الْعَفْوَ فَاعْفُ عَنَّا»",
            subText = "دعاء ليلة القدر المباركة التي هي خير من ألف شهر • بطاقة رمضانية راقية #94",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_95",
            title = "أنوار الشهر الكريم #95",
            calligraphyText = "﴿إِنَّا أَنزَلْنَاهُ فِي لَيْلَةِ الْقَدْرِ ۝ وَمَا أَدْرَاكَ مَا لَيْلَةُ الْقَدْرِ﴾",
            subText = "ليلة مباركة تتنزل فيها الملائكة والسكينة حتى مطلع الفجر • بطاقة رمضانية راقية #95",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_96",
            title = "طهارة القلوب في رمضان #96",
            calligraphyText = "«ذَهَبَ الظَّمَأُ وَابْتَلَّتِ الْعُرُوقُ وَثَبَتَ الأَجْرُ إِنْ شَاءَ اللَّهُ»",
            subText = "دعاء الإفطار وسنة النبي ﷺ عند الفطر • بطاقة رمضانية راقية #96",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_97",
            title = "أيام معدودات مباركات #97",
            calligraphyText = "«إِذَا دَخَلَ رَمَضَانُ فُتِّحَتْ أَبْوَابُ الْجَنَّةِ وَغُلِّقَتْ أَبْوَابُ جَهَنَّمَ»",
            subText = "بشارة النبي ﷺ بقدوم شهر الصيام والرحمات • بطاقة رمضانية راقية #97",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_98",
            title = "شمس المغفرة والرضوان #98",
            calligraphyText = "«الصِّيَامُ جُنَّةٌ فَلَا يَرْفُثْ وَلَا يَجْهَلْ»",
            subText = "حفظ الصيام بالصبر وحسن الخلق والسكينة • بطاقة رمضانية راقية #98",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_99",
            title = "رحمات تتنزل في السحر #99",
            calligraphyText = "«لِلصَّائِمِ فَرْحَتَانِ: فَرْحَةٌ عِنْدَ فِطْرِهِ، وَفَرْحَةٌ عِنْدَ لِقَاءِ رَبِّهِ»",
            subText = "بشارة الصائمين بجزاء الله العظيم في الدنيا والآخرة • بطاقة رمضانية راقية #99",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFF9D174DL, 0xFF4A044EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_ramadan_100",
            title = "ختمة كتاب الله في رمضان #100",
            calligraphyText = "«تَسَحَّرُوا فَإِنَّ فِي السَّحُورِ بَرَكَةً»",
            subText = "بركة السحور وسنة الهادي المصطفى ﷺ • بطاقة رمضانية راقية #100",
            category = "صور وخلفيات رمضانية",
            gradientColors = listOf(0xFFB45309L, 0xFF451A03L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_1",
            title = "خلفية الكعبة المشرفة وبيت الله الحرام #1",
            calligraphyText = "﴿إِنَّ أَوَّلَ بَيْتٍ وُضِعَ لِلنَّاسِ لَلَّذِي بِبَكَّةَ مُبَارَكًا وَهُدًى لِّلْعَالَمِينَ﴾",
            subText = "صورة عالية الدقة للكعبة المشرفة والمسجد الحرام تصلح خلفية للموبايل • معالم إسلامية خالدة #1",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF1F2937L, 0xFF111827L),
            drawableRes = R.drawable.img_kaaba_makkah
        ),
        GalleryItem(
            id = "g_masjid_2",
            title = "خلفية المسجد النبوي والقبة الخضراء #2",
            calligraphyText = "«صَلَاةٌ فِي مَسْجِدِي هَذَا خَيْرٌ مِنْ أَلْفِ صَلَاةٍ فِيمَا سِوَاهُ إِلَّا الْمَسْجِدَ الْحَرَامَ»",
            subText = "صورة نادرة للمسجد النبوي الشريف برحاب المدينة المنورة كخلفية للهاتف • معالم إسلامية #2",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = R.drawable.img_masjid_nabawi
        ),
        GalleryItem(
            id = "g_masjid_3",
            title = "المسجد الأقصى وقبة الصخرة #3",
            calligraphyText = "﴿سُبْحَانَ الَّذِي أَسْرَىٰ بِعَبْدِهِ لَيْلًا مِّنَ الْمَسْجِدِ الْحَرَامِ إِلَى الْمَسْجِدِ الْأَقْصَى﴾",
            subText = "المسجد الأقصى المبارك أولى القبلتين ومسرى رسول الله ﷺ • معالم إسلامية خالدة #3",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_4",
            title = "جامع السلطان أحمد والمآذن الشامخة #4",
            calligraphyText = "﴿فِي بُيُوتٍ أَذِنَ اللَّهُ أَن تُرْفَعَ وَيُذْكَرَ فِيهَا اسْمُهُ يُسَبِّحُ لَهُ فِيهَا بِالْغُدُوِّ وَالْآصَالِ﴾",
            subText = "عمارة بيوت الله بالذكر والصلاة والقرآن في كل حين • معالم إسلامية خالدة #4",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_5",
            title = "جامع الزيتونة المعمور #5",
            calligraphyText = "﴿إِنَّمَا يَعْمُرُ مَسَاجِدَ اللَّهِ مَنْ آمَنَ بِاللَّهِ وَالْيَوْمِ الْآخِرِ﴾",
            subText = "فضل المساجد وروادها المخلصين المقيمين للصلاة • معالم إسلامية خالدة #5",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_6",
            title = "جامع القرويين بفاس #6",
            calligraphyText = "«مَنْ بَنَى مَسْجِدًا لِلَّهِ بَنَى اللَّهُ لَهُ بَيْتًا فِي الْجَنَّةِ»",
            subText = "فضل بناء المساجد ورعايتها وتطييبها للمصلين • معالم إسلامية خالدة #6",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_7",
            title = "الجامع الأزهر الشريف بمصر #7",
            calligraphyText = "«أَحَبُّ الْبِلَادِ إِلَى اللَّهِ مَسَاجِدُهَا»",
            subText = "طهارة المساجد وروحانية السجود في رياض الجنان • معالم إسلامية خالدة #7",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_8",
            title = "جامع الشيخ زايد الكبير #8",
            calligraphyText = "«صَلَاةُ الرَّجُلِ فِي جَمَاعَةٍ تَزِيدُ عَلَى صَلَاتِهِ فِي بَيْتِهِ سَبْعًا وَعِشْرِينَ دَرَجَةً»",
            subText = "فضل صلاة الجماعة وشهود الفجر والعشاء في المسجد • معالم إسلامية خالدة #8",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_9",
            title = "مسجد قباء أول مسجد في الإسلام #9",
            calligraphyText = "﴿وَأَنَّ الْمَسَاجِدَ لِلَّهِ فَلَا تَدْعُوا مَعَ اللَّهِ أَحَدًا﴾",
            subText = "إخلاص التوحيد والدعاء لله رب العالمين في بيوته • معالم إسلامية خالدة #9",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_10",
            title = "مسجد القبلتين المبارك #10",
            calligraphyText = "«إِذَا رَأَيْتُمُ الرَّجُلَ يَعْتَادُ الْمَسَاجِدَ فَاشْهَدُوا لَهُ بِالإِيمَانِ»",
            subText = "شهادة الإيمان لرواد المساجد والمتعلقين ببيوت الله • معالم إسلامية خالدة #10",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_11",
            title = "جامع الفاتح بإسطنبول #11",
            calligraphyText = "﴿إِنَّ أَوَّلَ بَيْتٍ وُضِعَ لِلنَّاسِ لَلَّذِي بِبَكَّةَ مُبَارَكًا وَهُدًى لِّلْعَالَمِينَ﴾",
            subText = "الكعبة المشرفة مهوى أفئدة المؤمنين وقبلة الموحدين • معالم إسلامية خالدة #11",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF1F2937L, 0xFF111827L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_12",
            title = "مسجد بادشاهي التاريخي #12",
            calligraphyText = "«صَلَاةٌ فِي مَسْجِدِي هَذَا خَيْرٌ مِنْ أَلْفِ صَلَاةٍ فِيمَا سِوَاهُ إِلَّا الْمَسْجِدَ الْحَرَامَ»",
            subText = "المسجد النبوي الشريف والروضة الشريفة بالمدينة المنورة • معالم إسلامية خالدة #12",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_13",
            title = "جامع قرطبة والآثار الأندلسية #13",
            calligraphyText = "﴿سُبْحَانَ الَّذِي أَسْرَىٰ بِعَبْدِهِ لَيْلًا مِّنَ الْمَسْجِدِ الْحَرَامِ إِلَى الْمَسْجِدِ الْأَقْصَى﴾",
            subText = "المسجد الأقصى المبارك أولى القبلتين ومسرى رسول الله ﷺ • معالم إسلامية خالدة #13",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_14",
            title = "جامع عقبة بن نافع بالقيروان #14",
            calligraphyText = "﴿فِي بُيُوتٍ أَذِنَ اللَّهُ أَن تُرْفَعَ وَيُذْكَرَ فِيهَا اسْمُهُ يُسَبِّحُ لَهُ فِيهَا بِالْغُدُوِّ وَالْآصَالِ﴾",
            subText = "عمارة بيوت الله بالذكر والصلاة والقرآن في كل حين • معالم إسلامية خالدة #14",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_15",
            title = "مسجد الحسن الثاني على المحيط #15",
            calligraphyText = "﴿إِنَّمَا يَعْمُرُ مَسَاجِدَ اللَّهِ مَنْ آمَنَ بِاللَّهِ وَالْيَوْمِ الْآخِرِ﴾",
            subText = "فضل المساجد وروادها المخلصين المقيمين للصلاة • معالم إسلامية خالدة #15",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_16",
            title = "مسجد الكريستال الفريد #16",
            calligraphyText = "«مَنْ بَنَى مَسْجِدًا لِلَّهِ بَنَى اللَّهُ لَهُ بَيْتًا فِي الْجَنَّةِ»",
            subText = "فضل بناء المساجد ورعايتها وتطييبها للمصلين • معالم إسلامية خالدة #16",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_17",
            title = "مسجد فيصل بإسلام آباد #17",
            calligraphyText = "«أَحَبُّ الْبِلَادِ إِلَى اللَّهِ مَسَاجِدُهَا»",
            subText = "طهارة المساجد وروحانية السجود في رياض الجنان • معالم إسلامية خالدة #17",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_18",
            title = "جامع دمشق الأموي الكبير #18",
            calligraphyText = "«صَلَاةُ الرَّجُلِ فِي جَمَاعَةٍ تَزِيدُ عَلَى صَلَاتِهِ فِي بَيْتِهِ سَبْعًا وَعِشْرِينَ دَرَجَةً»",
            subText = "فضل صلاة الجماعة وشهود الفجر والعشاء في المسجد • معالم إسلامية خالدة #18",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_19",
            title = "مئذنة ملتوية سامراء #19",
            calligraphyText = "﴿وَأَنَّ الْمَسَاجِدَ لِلَّهِ فَلَا تَدْعُوا مَعَ اللَّهِ أَحَدًا﴾",
            subText = "إخلاص التوحيد والدعاء لله رب العالمين في بيوته • معالم إسلامية خالدة #19",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_20",
            title = "مسجد الكتبية بمراكش #20",
            calligraphyText = "«إِذَا رَأَيْتُمُ الرَّجُلَ يَعْتَادُ الْمَسَاجِدَ فَاشْهَدُوا لَهُ بِالإِيمَانِ»",
            subText = "شهادة الإيمان لرواد المساجد والمتعلقين ببيوت الله • معالم إسلامية خالدة #20",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_21",
            title = "الكعبة المشرفة وبيت الله الحرام #21",
            calligraphyText = "﴿إِنَّ أَوَّلَ بَيْتٍ وُضِعَ لِلنَّاسِ لَلَّذِي بِبَكَّةَ مُبَارَكًا وَهُدًى لِّلْعَالَمِينَ﴾",
            subText = "الكعبة المشرفة مهوى أفئدة المؤمنين وقبلة الموحدين • معالم إسلامية خالدة #21",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF1F2937L, 0xFF111827L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_22",
            title = "المسجد النبوي والمدينة المنورة #22",
            calligraphyText = "«صَلَاةٌ فِي مَسْجِدِي هَذَا خَيْرٌ مِنْ أَلْفِ صَلَاةٍ فِيمَا سِوَاهُ إِلَّا الْمَسْجِدَ الْحَرَامَ»",
            subText = "المسجد النبوي الشريف والروضة الشريفة بالمدينة المنورة • معالم إسلامية خالدة #22",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_23",
            title = "المسجد الأقصى وقبة الصخرة #23",
            calligraphyText = "﴿سُبْحَانَ الَّذِي أَسْرَىٰ بِعَبْدِهِ لَيْلًا مِّنَ الْمَسْجِدِ الْحَرَامِ إِلَى الْمَسْجِدِ الْأَقْصَى﴾",
            subText = "المسجد الأقصى المبارك أولى القبلتين ومسرى رسول الله ﷺ • معالم إسلامية خالدة #23",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_24",
            title = "جامع السلطان أحمد والمآذن الشامخة #24",
            calligraphyText = "﴿فِي بُيُوتٍ أَذِنَ اللَّهُ أَن تُرْفَعَ وَيُذْكَرَ فِيهَا اسْمُهُ يُسَبِّحُ لَهُ فِيهَا بِالْغُدُوِّ وَالْآصَالِ﴾",
            subText = "عمارة بيوت الله بالذكر والصلاة والقرآن في كل حين • معالم إسلامية خالدة #24",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_25",
            title = "جامع الزيتونة المعمور #25",
            calligraphyText = "﴿إِنَّمَا يَعْمُرُ مَسَاجِدَ اللَّهِ مَنْ آمَنَ بِاللَّهِ وَالْيَوْمِ الْآخِرِ﴾",
            subText = "فضل المساجد وروادها المخلصين المقيمين للصلاة • معالم إسلامية خالدة #25",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_26",
            title = "جامع القرويين بفاس #26",
            calligraphyText = "«مَنْ بَنَى مَسْجِدًا لِلَّهِ بَنَى اللَّهُ لَهُ بَيْتًا فِي الْجَنَّةِ»",
            subText = "فضل بناء المساجد ورعايتها وتطييبها للمصلين • معالم إسلامية خالدة #26",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_27",
            title = "الجامع الأزهر الشريف بمصر #27",
            calligraphyText = "«أَحَبُّ الْبِلَادِ إِلَى اللَّهِ مَسَاجِدُهَا»",
            subText = "طهارة المساجد وروحانية السجود في رياض الجنان • معالم إسلامية خالدة #27",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_28",
            title = "جامع الشيخ زايد الكبير #28",
            calligraphyText = "«صَلَاةُ الرَّجُلِ فِي جَمَاعَةٍ تَزِيدُ عَلَى صَلَاتِهِ فِي بَيْتِهِ سَبْعًا وَعِشْرِينَ دَرَجَةً»",
            subText = "فضل صلاة الجماعة وشهود الفجر والعشاء في المسجد • معالم إسلامية خالدة #28",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_29",
            title = "مسجد قباء أول مسجد في الإسلام #29",
            calligraphyText = "﴿وَأَنَّ الْمَسَاجِدَ لِلَّهِ فَلَا تَدْعُوا مَعَ اللَّهِ أَحَدًا﴾",
            subText = "إخلاص التوحيد والدعاء لله رب العالمين في بيوته • معالم إسلامية خالدة #29",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_30",
            title = "مسجد القبلتين المبارك #30",
            calligraphyText = "«إِذَا رَأَيْتُمُ الرَّجُلَ يَعْتَادُ الْمَسَاجِدَ فَاشْهَدُوا لَهُ بِالإِيمَانِ»",
            subText = "شهادة الإيمان لرواد المساجد والمتعلقين ببيوت الله • معالم إسلامية خالدة #30",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_31",
            title = "جامع الفاتح بإسطنبول #31",
            calligraphyText = "﴿إِنَّ أَوَّلَ بَيْتٍ وُضِعَ لِلنَّاسِ لَلَّذِي بِبَكَّةَ مُبَارَكًا وَهُدًى لِّلْعَالَمِينَ﴾",
            subText = "الكعبة المشرفة مهوى أفئدة المؤمنين وقبلة الموحدين • معالم إسلامية خالدة #31",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF1F2937L, 0xFF111827L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_32",
            title = "مسجد بادشاهي التاريخي #32",
            calligraphyText = "«صَلَاةٌ فِي مَسْجِدِي هَذَا خَيْرٌ مِنْ أَلْفِ صَلَاةٍ فِيمَا سِوَاهُ إِلَّا الْمَسْجِدَ الْحَرَامَ»",
            subText = "المسجد النبوي الشريف والروضة الشريفة بالمدينة المنورة • معالم إسلامية خالدة #32",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_33",
            title = "جامع قرطبة والآثار الأندلسية #33",
            calligraphyText = "﴿سُبْحَانَ الَّذِي أَسْرَىٰ بِعَبْدِهِ لَيْلًا مِّنَ الْمَسْجِدِ الْحَرَامِ إِلَى الْمَسْجِدِ الْأَقْصَى﴾",
            subText = "المسجد الأقصى المبارك أولى القبلتين ومسرى رسول الله ﷺ • معالم إسلامية خالدة #33",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_34",
            title = "جامع عقبة بن نافع بالقيروان #34",
            calligraphyText = "﴿فِي بُيُوتٍ أَذِنَ اللَّهُ أَن تُرْفَعَ وَيُذْكَرَ فِيهَا اسْمُهُ يُسَبِّحُ لَهُ فِيهَا بِالْغُدُوِّ وَالْآصَالِ﴾",
            subText = "عمارة بيوت الله بالذكر والصلاة والقرآن في كل حين • معالم إسلامية خالدة #34",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_35",
            title = "مسجد الحسن الثاني على المحيط #35",
            calligraphyText = "﴿إِنَّمَا يَعْمُرُ مَسَاجِدَ اللَّهِ مَنْ آمَنَ بِاللَّهِ وَالْيَوْمِ الْآخِرِ﴾",
            subText = "فضل المساجد وروادها المخلصين المقيمين للصلاة • معالم إسلامية خالدة #35",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_36",
            title = "مسجد الكريستال الفريد #36",
            calligraphyText = "«مَنْ بَنَى مَسْجِدًا لِلَّهِ بَنَى اللَّهُ لَهُ بَيْتًا فِي الْجَنَّةِ»",
            subText = "فضل بناء المساجد ورعايتها وتطييبها للمصلين • معالم إسلامية خالدة #36",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_37",
            title = "مسجد فيصل بإسلام آباد #37",
            calligraphyText = "«أَحَبُّ الْبِلَادِ إِلَى اللَّهِ مَسَاجِدُهَا»",
            subText = "طهارة المساجد وروحانية السجود في رياض الجنان • معالم إسلامية خالدة #37",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_38",
            title = "جامع دمشق الأموي الكبير #38",
            calligraphyText = "«صَلَاةُ الرَّجُلِ فِي جَمَاعَةٍ تَزِيدُ عَلَى صَلَاتِهِ فِي بَيْتِهِ سَبْعًا وَعِشْرِينَ دَرَجَةً»",
            subText = "فضل صلاة الجماعة وشهود الفجر والعشاء في المسجد • معالم إسلامية خالدة #38",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_39",
            title = "مئذنة ملتوية سامراء #39",
            calligraphyText = "﴿وَأَنَّ الْمَسَاجِدَ لِلَّهِ فَلَا تَدْعُوا مَعَ اللَّهِ أَحَدًا﴾",
            subText = "إخلاص التوحيد والدعاء لله رب العالمين في بيوته • معالم إسلامية خالدة #39",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_40",
            title = "مسجد الكتبية بمراكش #40",
            calligraphyText = "«إِذَا رَأَيْتُمُ الرَّجُلَ يَعْتَادُ الْمَسَاجِدَ فَاشْهَدُوا لَهُ بِالإِيمَانِ»",
            subText = "شهادة الإيمان لرواد المساجد والمتعلقين ببيوت الله • معالم إسلامية خالدة #40",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_41",
            title = "الكعبة المشرفة وبيت الله الحرام #41",
            calligraphyText = "﴿إِنَّ أَوَّلَ بَيْتٍ وُضِعَ لِلنَّاسِ لَلَّذِي بِبَكَّةَ مُبَارَكًا وَهُدًى لِّلْعَالَمِينَ﴾",
            subText = "الكعبة المشرفة مهوى أفئدة المؤمنين وقبلة الموحدين • معالم إسلامية خالدة #41",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF1F2937L, 0xFF111827L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_42",
            title = "المسجد النبوي والمدينة المنورة #42",
            calligraphyText = "«صَلَاةٌ فِي مَسْجِدِي هَذَا خَيْرٌ مِنْ أَلْفِ صَلَاةٍ فِيمَا سِوَاهُ إِلَّا الْمَسْجِدَ الْحَرَامَ»",
            subText = "المسجد النبوي الشريف والروضة الشريفة بالمدينة المنورة • معالم إسلامية خالدة #42",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_43",
            title = "المسجد الأقصى وقبة الصخرة #43",
            calligraphyText = "﴿سُبْحَانَ الَّذِي أَسْرَىٰ بِعَبْدِهِ لَيْلًا مِّنَ الْمَسْجِدِ الْحَرَامِ إِلَى الْمَسْجِدِ الْأَقْصَى﴾",
            subText = "المسجد الأقصى المبارك أولى القبلتين ومسرى رسول الله ﷺ • معالم إسلامية خالدة #43",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_44",
            title = "جامع السلطان أحمد والمآذن الشامخة #44",
            calligraphyText = "﴿فِي بُيُوتٍ أَذِنَ اللَّهُ أَن تُرْفَعَ وَيُذْكَرَ فِيهَا اسْمُهُ يُسَبِّحُ لَهُ فِيهَا بِالْغُدُوِّ وَالْآصَالِ﴾",
            subText = "عمارة بيوت الله بالذكر والصلاة والقرآن في كل حين • معالم إسلامية خالدة #44",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_45",
            title = "جامع الزيتونة المعمور #45",
            calligraphyText = "﴿إِنَّمَا يَعْمُرُ مَسَاجِدَ اللَّهِ مَنْ آمَنَ بِاللَّهِ وَالْيَوْمِ الْآخِرِ﴾",
            subText = "فضل المساجد وروادها المخلصين المقيمين للصلاة • معالم إسلامية خالدة #45",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_46",
            title = "جامع القرويين بفاس #46",
            calligraphyText = "«مَنْ بَنَى مَسْجِدًا لِلَّهِ بَنَى اللَّهُ لَهُ بَيْتًا فِي الْجَنَّةِ»",
            subText = "فضل بناء المساجد ورعايتها وتطييبها للمصلين • معالم إسلامية خالدة #46",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_47",
            title = "الجامع الأزهر الشريف بمصر #47",
            calligraphyText = "«أَحَبُّ الْبِلَادِ إِلَى اللَّهِ مَسَاجِدُهَا»",
            subText = "طهارة المساجد وروحانية السجود في رياض الجنان • معالم إسلامية خالدة #47",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_48",
            title = "جامع الشيخ زايد الكبير #48",
            calligraphyText = "«صَلَاةُ الرَّجُلِ فِي جَمَاعَةٍ تَزِيدُ عَلَى صَلَاتِهِ فِي بَيْتِهِ سَبْعًا وَعِشْرِينَ دَرَجَةً»",
            subText = "فضل صلاة الجماعة وشهود الفجر والعشاء في المسجد • معالم إسلامية خالدة #48",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_49",
            title = "مسجد قباء أول مسجد في الإسلام #49",
            calligraphyText = "﴿وَأَنَّ الْمَسَاجِدَ لِلَّهِ فَلَا تَدْعُوا مَعَ اللَّهِ أَحَدًا﴾",
            subText = "إخلاص التوحيد والدعاء لله رب العالمين في بيوته • معالم إسلامية خالدة #49",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_50",
            title = "مسجد القبلتين المبارك #50",
            calligraphyText = "«إِذَا رَأَيْتُمُ الرَّجُلَ يَعْتَادُ الْمَسَاجِدَ فَاشْهَدُوا لَهُ بِالإِيمَانِ»",
            subText = "شهادة الإيمان لرواد المساجد والمتعلقين ببيوت الله • معالم إسلامية خالدة #50",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_51",
            title = "جامع الفاتح بإسطنبول #51",
            calligraphyText = "﴿إِنَّ أَوَّلَ بَيْتٍ وُضِعَ لِلنَّاسِ لَلَّذِي بِبَكَّةَ مُبَارَكًا وَهُدًى لِّلْعَالَمِينَ﴾",
            subText = "الكعبة المشرفة مهوى أفئدة المؤمنين وقبلة الموحدين • معالم إسلامية خالدة #51",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF1F2937L, 0xFF111827L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_52",
            title = "مسجد بادشاهي التاريخي #52",
            calligraphyText = "«صَلَاةٌ فِي مَسْجِدِي هَذَا خَيْرٌ مِنْ أَلْفِ صَلَاةٍ فِيمَا سِوَاهُ إِلَّا الْمَسْجِدَ الْحَرَامَ»",
            subText = "المسجد النبوي الشريف والروضة الشريفة بالمدينة المنورة • معالم إسلامية خالدة #52",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_53",
            title = "جامع قرطبة والآثار الأندلسية #53",
            calligraphyText = "﴿سُبْحَانَ الَّذِي أَسْرَىٰ بِعَبْدِهِ لَيْلًا مِّنَ الْمَسْجِدِ الْحَرَامِ إِلَى الْمَسْجِدِ الْأَقْصَى﴾",
            subText = "المسجد الأقصى المبارك أولى القبلتين ومسرى رسول الله ﷺ • معالم إسلامية خالدة #53",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_54",
            title = "جامع عقبة بن نافع بالقيروان #54",
            calligraphyText = "﴿فِي بُيُوتٍ أَذِنَ اللَّهُ أَن تُرْفَعَ وَيُذْكَرَ فِيهَا اسْمُهُ يُسَبِّحُ لَهُ فِيهَا بِالْغُدُوِّ وَالْآصَالِ﴾",
            subText = "عمارة بيوت الله بالذكر والصلاة والقرآن في كل حين • معالم إسلامية خالدة #54",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_55",
            title = "مسجد الحسن الثاني على المحيط #55",
            calligraphyText = "﴿إِنَّمَا يَعْمُرُ مَسَاجِدَ اللَّهِ مَنْ آمَنَ بِاللَّهِ وَالْيَوْمِ الْآخِرِ﴾",
            subText = "فضل المساجد وروادها المخلصين المقيمين للصلاة • معالم إسلامية خالدة #55",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_56",
            title = "مسجد الكريستال الفريد #56",
            calligraphyText = "«مَنْ بَنَى مَسْجِدًا لِلَّهِ بَنَى اللَّهُ لَهُ بَيْتًا فِي الْجَنَّةِ»",
            subText = "فضل بناء المساجد ورعايتها وتطييبها للمصلين • معالم إسلامية خالدة #56",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_57",
            title = "مسجد فيصل بإسلام آباد #57",
            calligraphyText = "«أَحَبُّ الْبِلَادِ إِلَى اللَّهِ مَسَاجِدُهَا»",
            subText = "طهارة المساجد وروحانية السجود في رياض الجنان • معالم إسلامية خالدة #57",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_58",
            title = "جامع دمشق الأموي الكبير #58",
            calligraphyText = "«صَلَاةُ الرَّجُلِ فِي جَمَاعَةٍ تَزِيدُ عَلَى صَلَاتِهِ فِي بَيْتِهِ سَبْعًا وَعِشْرِينَ دَرَجَةً»",
            subText = "فضل صلاة الجماعة وشهود الفجر والعشاء في المسجد • معالم إسلامية خالدة #58",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_59",
            title = "مئذنة ملتوية سامراء #59",
            calligraphyText = "﴿وَأَنَّ الْمَسَاجِدَ لِلَّهِ فَلَا تَدْعُوا مَعَ اللَّهِ أَحَدًا﴾",
            subText = "إخلاص التوحيد والدعاء لله رب العالمين في بيوته • معالم إسلامية خالدة #59",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_60",
            title = "مسجد الكتبية بمراكش #60",
            calligraphyText = "«إِذَا رَأَيْتُمُ الرَّجُلَ يَعْتَادُ الْمَسَاجِدَ فَاشْهَدُوا لَهُ بِالإِيمَانِ»",
            subText = "شهادة الإيمان لرواد المساجد والمتعلقين ببيوت الله • معالم إسلامية خالدة #60",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_61",
            title = "الكعبة المشرفة وبيت الله الحرام #61",
            calligraphyText = "﴿إِنَّ أَوَّلَ بَيْتٍ وُضِعَ لِلنَّاسِ لَلَّذِي بِبَكَّةَ مُبَارَكًا وَهُدًى لِّلْعَالَمِينَ﴾",
            subText = "الكعبة المشرفة مهوى أفئدة المؤمنين وقبلة الموحدين • معالم إسلامية خالدة #61",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF1F2937L, 0xFF111827L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_62",
            title = "المسجد النبوي والمدينة المنورة #62",
            calligraphyText = "«صَلَاةٌ فِي مَسْجِدِي هَذَا خَيْرٌ مِنْ أَلْفِ صَلَاةٍ فِيمَا سِوَاهُ إِلَّا الْمَسْجِدَ الْحَرَامَ»",
            subText = "المسجد النبوي الشريف والروضة الشريفة بالمدينة المنورة • معالم إسلامية خالدة #62",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_63",
            title = "المسجد الأقصى وقبة الصخرة #63",
            calligraphyText = "﴿سُبْحَانَ الَّذِي أَسْرَىٰ بِعَبْدِهِ لَيْلًا مِّنَ الْمَسْجِدِ الْحَرَامِ إِلَى الْمَسْجِدِ الْأَقْصَى﴾",
            subText = "المسجد الأقصى المبارك أولى القبلتين ومسرى رسول الله ﷺ • معالم إسلامية خالدة #63",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_64",
            title = "جامع السلطان أحمد والمآذن الشامخة #64",
            calligraphyText = "﴿فِي بُيُوتٍ أَذِنَ اللَّهُ أَن تُرْفَعَ وَيُذْكَرَ فِيهَا اسْمُهُ يُسَبِّحُ لَهُ فِيهَا بِالْغُدُوِّ وَالْآصَالِ﴾",
            subText = "عمارة بيوت الله بالذكر والصلاة والقرآن في كل حين • معالم إسلامية خالدة #64",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_65",
            title = "جامع الزيتونة المعمور #65",
            calligraphyText = "﴿إِنَّمَا يَعْمُرُ مَسَاجِدَ اللَّهِ مَنْ آمَنَ بِاللَّهِ وَالْيَوْمِ الْآخِرِ﴾",
            subText = "فضل المساجد وروادها المخلصين المقيمين للصلاة • معالم إسلامية خالدة #65",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_66",
            title = "جامع القرويين بفاس #66",
            calligraphyText = "«مَنْ بَنَى مَسْجِدًا لِلَّهِ بَنَى اللَّهُ لَهُ بَيْتًا فِي الْجَنَّةِ»",
            subText = "فضل بناء المساجد ورعايتها وتطييبها للمصلين • معالم إسلامية خالدة #66",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_67",
            title = "الجامع الأزهر الشريف بمصر #67",
            calligraphyText = "«أَحَبُّ الْبِلَادِ إِلَى اللَّهِ مَسَاجِدُهَا»",
            subText = "طهارة المساجد وروحانية السجود في رياض الجنان • معالم إسلامية خالدة #67",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_68",
            title = "جامع الشيخ زايد الكبير #68",
            calligraphyText = "«صَلَاةُ الرَّجُلِ فِي جَمَاعَةٍ تَزِيدُ عَلَى صَلَاتِهِ فِي بَيْتِهِ سَبْعًا وَعِشْرِينَ دَرَجَةً»",
            subText = "فضل صلاة الجماعة وشهود الفجر والعشاء في المسجد • معالم إسلامية خالدة #68",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_69",
            title = "مسجد قباء أول مسجد في الإسلام #69",
            calligraphyText = "﴿وَأَنَّ الْمَسَاجِدَ لِلَّهِ فَلَا تَدْعُوا مَعَ اللَّهِ أَحَدًا﴾",
            subText = "إخلاص التوحيد والدعاء لله رب العالمين في بيوته • معالم إسلامية خالدة #69",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_70",
            title = "مسجد القبلتين المبارك #70",
            calligraphyText = "«إِذَا رَأَيْتُمُ الرَّجُلَ يَعْتَادُ الْمَسَاجِدَ فَاشْهَدُوا لَهُ بِالإِيمَانِ»",
            subText = "شهادة الإيمان لرواد المساجد والمتعلقين ببيوت الله • معالم إسلامية خالدة #70",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_71",
            title = "جامع الفاتح بإسطنبول #71",
            calligraphyText = "﴿إِنَّ أَوَّلَ بَيْتٍ وُضِعَ لِلنَّاسِ لَلَّذِي بِبَكَّةَ مُبَارَكًا وَهُدًى لِّلْعَالَمِينَ﴾",
            subText = "الكعبة المشرفة مهوى أفئدة المؤمنين وقبلة الموحدين • معالم إسلامية خالدة #71",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF1F2937L, 0xFF111827L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_72",
            title = "مسجد بادشاهي التاريخي #72",
            calligraphyText = "«صَلَاةٌ فِي مَسْجِدِي هَذَا خَيْرٌ مِنْ أَلْفِ صَلَاةٍ فِيمَا سِوَاهُ إِلَّا الْمَسْجِدَ الْحَرَامَ»",
            subText = "المسجد النبوي الشريف والروضة الشريفة بالمدينة المنورة • معالم إسلامية خالدة #72",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_73",
            title = "جامع قرطبة والآثار الأندلسية #73",
            calligraphyText = "﴿سُبْحَانَ الَّذِي أَسْرَىٰ بِعَبْدِهِ لَيْلًا مِّنَ الْمَسْجِدِ الْحَرَامِ إِلَى الْمَسْجِدِ الْأَقْصَى﴾",
            subText = "المسجد الأقصى المبارك أولى القبلتين ومسرى رسول الله ﷺ • معالم إسلامية خالدة #73",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_74",
            title = "جامع عقبة بن نافع بالقيروان #74",
            calligraphyText = "﴿فِي بُيُوتٍ أَذِنَ اللَّهُ أَن تُرْفَعَ وَيُذْكَرَ فِيهَا اسْمُهُ يُسَبِّحُ لَهُ فِيهَا بِالْغُدُوِّ وَالْآصَالِ﴾",
            subText = "عمارة بيوت الله بالذكر والصلاة والقرآن في كل حين • معالم إسلامية خالدة #74",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_75",
            title = "مسجد الحسن الثاني على المحيط #75",
            calligraphyText = "﴿إِنَّمَا يَعْمُرُ مَسَاجِدَ اللَّهِ مَنْ آمَنَ بِاللَّهِ وَالْيَوْمِ الْآخِرِ﴾",
            subText = "فضل المساجد وروادها المخلصين المقيمين للصلاة • معالم إسلامية خالدة #75",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_76",
            title = "مسجد الكريستال الفريد #76",
            calligraphyText = "«مَنْ بَنَى مَسْجِدًا لِلَّهِ بَنَى اللَّهُ لَهُ بَيْتًا فِي الْجَنَّةِ»",
            subText = "فضل بناء المساجد ورعايتها وتطييبها للمصلين • معالم إسلامية خالدة #76",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_77",
            title = "مسجد فيصل بإسلام آباد #77",
            calligraphyText = "«أَحَبُّ الْبِلَادِ إِلَى اللَّهِ مَسَاجِدُهَا»",
            subText = "طهارة المساجد وروحانية السجود في رياض الجنان • معالم إسلامية خالدة #77",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_78",
            title = "جامع دمشق الأموي الكبير #78",
            calligraphyText = "«صَلَاةُ الرَّجُلِ فِي جَمَاعَةٍ تَزِيدُ عَلَى صَلَاتِهِ فِي بَيْتِهِ سَبْعًا وَعِشْرِينَ دَرَجَةً»",
            subText = "فضل صلاة الجماعة وشهود الفجر والعشاء في المسجد • معالم إسلامية خالدة #78",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_79",
            title = "مئذنة ملتوية سامراء #79",
            calligraphyText = "﴿وَأَنَّ الْمَسَاجِدَ لِلَّهِ فَلَا تَدْعُوا مَعَ اللَّهِ أَحَدًا﴾",
            subText = "إخلاص التوحيد والدعاء لله رب العالمين في بيوته • معالم إسلامية خالدة #79",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_80",
            title = "مسجد الكتبية بمراكش #80",
            calligraphyText = "«إِذَا رَأَيْتُمُ الرَّجُلَ يَعْتَادُ الْمَسَاجِدَ فَاشْهَدُوا لَهُ بِالإِيمَانِ»",
            subText = "شهادة الإيمان لرواد المساجد والمتعلقين ببيوت الله • معالم إسلامية خالدة #80",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_81",
            title = "الكعبة المشرفة وبيت الله الحرام #81",
            calligraphyText = "﴿إِنَّ أَوَّلَ بَيْتٍ وُضِعَ لِلنَّاسِ لَلَّذِي بِبَكَّةَ مُبَارَكًا وَهُدًى لِّلْعَالَمِينَ﴾",
            subText = "الكعبة المشرفة مهوى أفئدة المؤمنين وقبلة الموحدين • معالم إسلامية خالدة #81",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF1F2937L, 0xFF111827L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_82",
            title = "المسجد النبوي والمدينة المنورة #82",
            calligraphyText = "«صَلَاةٌ فِي مَسْجِدِي هَذَا خَيْرٌ مِنْ أَلْفِ صَلَاةٍ فِيمَا سِوَاهُ إِلَّا الْمَسْجِدَ الْحَرَامَ»",
            subText = "المسجد النبوي الشريف والروضة الشريفة بالمدينة المنورة • معالم إسلامية خالدة #82",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_83",
            title = "المسجد الأقصى وقبة الصخرة #83",
            calligraphyText = "﴿سُبْحَانَ الَّذِي أَسْرَىٰ بِعَبْدِهِ لَيْلًا مِّنَ الْمَسْجِدِ الْحَرَامِ إِلَى الْمَسْجِدِ الْأَقْصَى﴾",
            subText = "المسجد الأقصى المبارك أولى القبلتين ومسرى رسول الله ﷺ • معالم إسلامية خالدة #83",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_84",
            title = "جامع السلطان أحمد والمآذن الشامخة #84",
            calligraphyText = "﴿فِي بُيُوتٍ أَذِنَ اللَّهُ أَن تُرْفَعَ وَيُذْكَرَ فِيهَا اسْمُهُ يُسَبِّحُ لَهُ فِيهَا بِالْغُدُوِّ وَالْآصَالِ﴾",
            subText = "عمارة بيوت الله بالذكر والصلاة والقرآن في كل حين • معالم إسلامية خالدة #84",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_85",
            title = "جامع الزيتونة المعمور #85",
            calligraphyText = "﴿إِنَّمَا يَعْمُرُ مَسَاجِدَ اللَّهِ مَنْ آمَنَ بِاللَّهِ وَالْيَوْمِ الْآخِرِ﴾",
            subText = "فضل المساجد وروادها المخلصين المقيمين للصلاة • معالم إسلامية خالدة #85",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_86",
            title = "جامع القرويين بفاس #86",
            calligraphyText = "«مَنْ بَنَى مَسْجِدًا لِلَّهِ بَنَى اللَّهُ لَهُ بَيْتًا فِي الْجَنَّةِ»",
            subText = "فضل بناء المساجد ورعايتها وتطييبها للمصلين • معالم إسلامية خالدة #86",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_87",
            title = "الجامع الأزهر الشريف بمصر #87",
            calligraphyText = "«أَحَبُّ الْبِلَادِ إِلَى اللَّهِ مَسَاجِدُهَا»",
            subText = "طهارة المساجد وروحانية السجود في رياض الجنان • معالم إسلامية خالدة #87",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_88",
            title = "جامع الشيخ زايد الكبير #88",
            calligraphyText = "«صَلَاةُ الرَّجُلِ فِي جَمَاعَةٍ تَزِيدُ عَلَى صَلَاتِهِ فِي بَيْتِهِ سَبْعًا وَعِشْرِينَ دَرَجَةً»",
            subText = "فضل صلاة الجماعة وشهود الفجر والعشاء في المسجد • معالم إسلامية خالدة #88",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_89",
            title = "مسجد قباء أول مسجد في الإسلام #89",
            calligraphyText = "﴿وَأَنَّ الْمَسَاجِدَ لِلَّهِ فَلَا تَدْعُوا مَعَ اللَّهِ أَحَدًا﴾",
            subText = "إخلاص التوحيد والدعاء لله رب العالمين في بيوته • معالم إسلامية خالدة #89",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_90",
            title = "مسجد القبلتين المبارك #90",
            calligraphyText = "«إِذَا رَأَيْتُمُ الرَّجُلَ يَعْتَادُ الْمَسَاجِدَ فَاشْهَدُوا لَهُ بِالإِيمَانِ»",
            subText = "شهادة الإيمان لرواد المساجد والمتعلقين ببيوت الله • معالم إسلامية خالدة #90",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_91",
            title = "جامع الفاتح بإسطنبول #91",
            calligraphyText = "﴿إِنَّ أَوَّلَ بَيْتٍ وُضِعَ لِلنَّاسِ لَلَّذِي بِبَكَّةَ مُبَارَكًا وَهُدًى لِّلْعَالَمِينَ﴾",
            subText = "الكعبة المشرفة مهوى أفئدة المؤمنين وقبلة الموحدين • معالم إسلامية خالدة #91",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF1F2937L, 0xFF111827L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_92",
            title = "مسجد بادشاهي التاريخي #92",
            calligraphyText = "«صَلَاةٌ فِي مَسْجِدِي هَذَا خَيْرٌ مِنْ أَلْفِ صَلَاةٍ فِيمَا سِوَاهُ إِلَّا الْمَسْجِدَ الْحَرَامَ»",
            subText = "المسجد النبوي الشريف والروضة الشريفة بالمدينة المنورة • معالم إسلامية خالدة #92",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_93",
            title = "جامع قرطبة والآثار الأندلسية #93",
            calligraphyText = "﴿سُبْحَانَ الَّذِي أَسْرَىٰ بِعَبْدِهِ لَيْلًا مِّنَ الْمَسْجِدِ الْحَرَامِ إِلَى الْمَسْجِدِ الْأَقْصَى﴾",
            subText = "المسجد الأقصى المبارك أولى القبلتين ومسرى رسول الله ﷺ • معالم إسلامية خالدة #93",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_94",
            title = "جامع عقبة بن نافع بالقيروان #94",
            calligraphyText = "﴿فِي بُيُوتٍ أَذِنَ اللَّهُ أَن تُرْفَعَ وَيُذْكَرَ فِيهَا اسْمُهُ يُسَبِّحُ لَهُ فِيهَا بِالْغُدُوِّ وَالْآصَالِ﴾",
            subText = "عمارة بيوت الله بالذكر والصلاة والقرآن في كل حين • معالم إسلامية خالدة #94",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_95",
            title = "مسجد الحسن الثاني على المحيط #95",
            calligraphyText = "﴿إِنَّمَا يَعْمُرُ مَسَاجِدَ اللَّهِ مَنْ آمَنَ بِاللَّهِ وَالْيَوْمِ الْآخِرِ﴾",
            subText = "فضل المساجد وروادها المخلصين المقيمين للصلاة • معالم إسلامية خالدة #95",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_96",
            title = "مسجد الكريستال الفريد #96",
            calligraphyText = "«مَنْ بَنَى مَسْجِدًا لِلَّهِ بَنَى اللَّهُ لَهُ بَيْتًا فِي الْجَنَّةِ»",
            subText = "فضل بناء المساجد ورعايتها وتطييبها للمصلين • معالم إسلامية خالدة #96",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_97",
            title = "مسجد فيصل بإسلام آباد #97",
            calligraphyText = "«أَحَبُّ الْبِلَادِ إِلَى اللَّهِ مَسَاجِدُهَا»",
            subText = "طهارة المساجد وروحانية السجود في رياض الجنان • معالم إسلامية خالدة #97",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_98",
            title = "جامع دمشق الأموي الكبير #98",
            calligraphyText = "«صَلَاةُ الرَّجُلِ فِي جَمَاعَةٍ تَزِيدُ عَلَى صَلَاتِهِ فِي بَيْتِهِ سَبْعًا وَعِشْرِينَ دَرَجَةً»",
            subText = "فضل صلاة الجماعة وشهود الفجر والعشاء في المسجد • معالم إسلامية خالدة #98",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_99",
            title = "مئذنة ملتوية سامراء #99",
            calligraphyText = "﴿وَأَنَّ الْمَسَاجِدَ لِلَّهِ فَلَا تَدْعُوا مَعَ اللَّهِ أَحَدًا﴾",
            subText = "إخلاص التوحيد والدعاء لله رب العالمين في بيوته • معالم إسلامية خالدة #99",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_masjid_100",
            title = "مسجد الكتبية بمراكش #100",
            calligraphyText = "«إِذَا رَأَيْتُمُ الرَّجُلَ يَعْتَادُ الْمَسَاجِدَ فَاشْهَدُوا لَهُ بِالإِيمَانِ»",
            subText = "شهادة الإيمان لرواد المساجد والمتعلقين ببيوت الله • معالم إسلامية خالدة #100",
            category = "معالم إسلامية",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_1",
            title = "خلفية ليلة الجمعة ومآذن المساجد والأنوار #1",
            calligraphyText = "﴿إِنَّ اللَّهَ وَمَلَائِكَتَهُ يُصَلُّونَ عَلَى النَّبِيِّ يَا أَيُّهَا الَّذِينَ آمَنُوا صَلُّوا عَلَيْهِ وَسَلِّمُوا تَسْلِيمًا﴾",
            subText = "خلفية ليلية بمآذن المساجد وهلال السماء كخلفية شاشة للموبايل • بطاقة الجمعة المباركة #1",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = R.drawable.img_wallpaper_mosque_night
        ),
        GalleryItem(
            id = "g_jummah_2",
            title = "نور سورة الكهف والسكينة #2",
            calligraphyText = "«خَيْرُ يَوْمٍ طَلَعَتْ عَلَيْهِ الشَّمْسُ يَوْمُ الْجُمُعَةِ»",
            subText = "فيه خُلق آدم وفيه أُدخل الجنة وفيه أُخرج منها وفيه تقوم الساعة • بطاقة الجمعة المباركة #2",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_3",
            title = "الصلاة على خير الأنام ﷺ #3",
            calligraphyText = "«مَنْ قَرَأَ سُورَةَ الْكَهْفِ فِي يَوْمِ الْجُمُعَةِ أَضَاءَ لَهُ مِنَ النُّورِ مَا بَيْنَ الْجُمُعَتَيْنِ»",
            subText = "نور سورة الكهف وضياؤها المستنير للمؤمنين كل أسبوع • بطاقة الجمعة المباركة #3",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_4",
            title = "ساعة الإجابة المباركة #4",
            calligraphyText = "«فِيهِ سَاعَةٌ لَا يُوَافِقُهَا عَبْدٌ مُسْلِمٌ وَهُوَ قَائِمٌ يُصَلِّي يَسْأَلُ اللَّهَ شَيْئًا إِلَّا أَعْطَاهُ إِيَّاهُ»",
            subText = "تحري ساعة الاستجابة آخر نهار الجمعة قبل الغروب • بطاقة الجمعة المباركة #4",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_5",
            title = "نفحات الجمعة الزاهرة #5",
            calligraphyText = "«مَنِ اغْتَسَلَ يَوْمَ الْجُمُعَةِ وَتَطَهَّرَ بِمَا اسْتَطَاعَ مِنْ طُهْرٍ ثُمَّ رَاحَ فَلَهُ كَفَّارَةٌ»",
            subText = "سنن يوم الجمعة: الغسل، الطيب، السواك، والتبكير إلى الصلاة • بطاقة الجمعة المباركة #5",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_6",
            title = "طيب الأنفاس بالصلاة والسلام #6",
            calligraphyText = "«أَكْثِرُوا عَلَيَّ مِنَ الصَّلَاةِ يَوْمَ الْجُمُعَةِ فَإِنَّ صَلَاتَكُمْ مَعْرُوضَةٌ عَلَيَّ»",
            subText = "محبة النبي ﷺ وصلاتنا المعروضة عليه في هذا اليوم الأزهر • بطاقة الجمعة المباركة #6",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_7",
            title = "جمعة طيبة مستبشرة #7",
            calligraphyText = "﴿يَا أَيُّهَا الَّذِينَ آمَنُوا إِذَا نُودِيَ لِلصَّلَاةِ مِن يَوْمِ الْجُمُعَةِ فَاسْعَوْا إِلَىٰ ذِكْرِ اللَّهِ﴾",
            subText = "وجوب السعي إلى صلاة الجمعة والاستماع لخطبة الإمام • بطاقة الجمعة المباركة #7",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_8",
            title = "دعاء الجمعة المستجاب #8",
            calligraphyText = "«اللَّهُمَّ بَارِكْ لَنَا فِي جُمُعَتِنَا وَاغْفِرْ لَنَا وَلِوَالِدَيْنَا»",
            subText = "دعاء الجمعة الجامعة بالبركة والمغفرة وصلاح الأحوال • بطاقة الجمعة المباركة #8",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF9D174DL, 0xFF4A044EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_9",
            title = "طهارة الجمعة وبياض القلوب #9",
            calligraphyText = "«اللَّهُمَّ اجْعَلْ هَذِهِ الْجُمُعَةَ فَرَجًا لِكُلِّ صَابِرٍ، وَشِفَاءً لِكُلِّ مَرِيضٍ»",
            subText = "مناجاة ورجاء بالفرج العاجل ورفع البلاء عن أمة الإسلام • بطاقة الجمعة المباركة #9",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_10",
            title = "أريج الجمعة العاطر #10",
            calligraphyText = "«طَيَّبَ اللَّهُ جُمُعَتَكُمْ بِذِكْرِهِ وَشَرَحَ صُدُورَكُمْ بِنُورِهِ»",
            subText = "تهنئة يوم الجمعة المباركة بالأنس والسكينة والرضوان • بطاقة الجمعة المباركة #10",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF047857L, 0xFF022C22L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_11",
            title = "سكينة خطبة الجمعة #11",
            calligraphyText = "﴿إِنَّ اللَّهَ وَمَلَائِكَتَهُ يُصَلُّونَ عَلَى النَّبِيِّ يَا أَيُّهَا الَّذِينَ آمَنُوا صَلُّوا عَلَيْهِ وَسَلِّمُوا تَسْلِيمًا﴾",
            subText = "أكثروا من الصلاة والسلام على النبي في يوم الجمعة وليلتها • بطاقة الجمعة المباركة #11",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_12",
            title = "بشائر الفرج في يوم الجمعة #12",
            calligraphyText = "«خَيْرُ يَوْمٍ طَلَعَتْ عَلَيْهِ الشَّمْسُ يَوْمُ الْجُمُعَةِ»",
            subText = "فيه خُلق آدم وفيه أُدخل الجنة وفيه أُخرج منها وفيه تقوم الساعة • بطاقة الجمعة المباركة #12",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_13",
            title = "تسابيح الجمعة الجامعة #13",
            calligraphyText = "«مَنْ قَرَأَ سُورَةَ الْكَهْفِ فِي يَوْمِ الْجُمُعَةِ أَضَاءَ لَهُ مِنَ النُّورِ مَا بَيْنَ الْجُمُعَتَيْنِ»",
            subText = "نور سورة الكهف وضياؤها المستنير للمؤمنين كل أسبوع • بطاقة الجمعة المباركة #13",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_14",
            title = "رياض الجنان في يوم الجمعة #14",
            calligraphyText = "«فِيهِ سَاعَةٌ لَا يُوَافِقُهَا عَبْدٌ مُسْلِمٌ وَهُوَ قَائِمٌ يُصَلِّي يَسْأَلُ اللَّهَ شَيْئًا إِلَّا أَعْطَاهُ إِيَّاهُ»",
            subText = "تحري ساعة الاستجابة آخر نهار الجمعة قبل الغروب • بطاقة الجمعة المباركة #14",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_15",
            title = "نور ما بين الجمعتين #15",
            calligraphyText = "«مَنِ اغْتَسَلَ يَوْمَ الْجُمُعَةِ وَتَطَهَّرَ بِمَا اسْتَطَاعَ مِنْ طُهْرٍ ثُمَّ رَاحَ فَلَهُ كَفَّارَةٌ»",
            subText = "سنن يوم الجمعة: الغسل، الطيب، السواك، والتبكير إلى الصلاة • بطاقة الجمعة المباركة #15",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_16",
            title = "جمعة الخيرات والبركات #16",
            calligraphyText = "«أَكْثِرُوا عَلَيَّ مِنَ الصَّلَاةِ يَوْمَ الْجُمُعَةِ فَإِنَّ صَلَاتَكُمْ مَعْرُوضَةٌ عَلَيَّ»",
            subText = "محبة النبي ﷺ وصلاتنا المعروضة عليه في هذا اليوم الأزهر • بطاقة الجمعة المباركة #16",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_17",
            title = "استغفار ودعاء في الجمعة #17",
            calligraphyText = "﴿يَا أَيُّهَا الَّذِينَ آمَنُوا إِذَا نُودِيَ لِلصَّلَاةِ مِن يَوْمِ الْجُمُعَةِ فَاسْعَوْا إِلَىٰ ذِكْرِ اللَّهِ﴾",
            subText = "وجوب السعي إلى صلاة الجمعة والاستماع لخطبة الإمام • بطاقة الجمعة المباركة #17",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_18",
            title = "وفاء الأحبة في يوم الجمعة #18",
            calligraphyText = "«اللَّهُمَّ بَارِكْ لَنَا فِي جُمُعَتِنَا وَاغْفِرْ لَنَا وَلِوَالِدَيْنَا»",
            subText = "دعاء الجمعة الجامعة بالبركة والمغفرة وصلاح الأحوال • بطاقة الجمعة المباركة #18",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF9D174DL, 0xFF4A044EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_19",
            title = "صلوات محمدية مباركة #19",
            calligraphyText = "«اللَّهُمَّ اجْعَلْ هَذِهِ الْجُمُعَةَ فَرَجًا لِكُلِّ صَابِرٍ، وَشِفَاءً لِكُلِّ مَرِيضٍ»",
            subText = "مناجاة ورجاء بالفرج العاجل ورفع البلاء عن أمة الإسلام • بطاقة الجمعة المباركة #19",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_20",
            title = "عطر الجمعة الفواح #20",
            calligraphyText = "«طَيَّبَ اللَّهُ جُمُعَتَكُمْ بِذِكْرِهِ وَشَرَحَ صُدُورَكُمْ بِنُورِهِ»",
            subText = "تهنئة يوم الجمعة المباركة بالأنس والسكينة والرضوان • بطاقة الجمعة المباركة #20",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF047857L, 0xFF022C22L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_21",
            title = "جمعة مباركة عامرة بالذكر #21",
            calligraphyText = "﴿إِنَّ اللَّهَ وَمَلَائِكَتَهُ يُصَلُّونَ عَلَى النَّبِيِّ يَا أَيُّهَا الَّذِينَ آمَنُوا صَلُّوا عَلَيْهِ وَسَلِّمُوا تَسْلِيمًا﴾",
            subText = "أكثروا من الصلاة والسلام على النبي في يوم الجمعة وليلتها • بطاقة الجمعة المباركة #21",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_22",
            title = "نور سورة الكهف والسكينة #22",
            calligraphyText = "«خَيْرُ يَوْمٍ طَلَعَتْ عَلَيْهِ الشَّمْسُ يَوْمُ الْجُمُعَةِ»",
            subText = "فيه خُلق آدم وفيه أُدخل الجنة وفيه أُخرج منها وفيه تقوم الساعة • بطاقة الجمعة المباركة #22",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_23",
            title = "الصلاة على خير الأنام ﷺ #23",
            calligraphyText = "«مَنْ قَرَأَ سُورَةَ الْكَهْفِ فِي يَوْمِ الْجُمُعَةِ أَضَاءَ لَهُ مِنَ النُّورِ مَا بَيْنَ الْجُمُعَتَيْنِ»",
            subText = "نور سورة الكهف وضياؤها المستنير للمؤمنين كل أسبوع • بطاقة الجمعة المباركة #23",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_24",
            title = "ساعة الإجابة المباركة #24",
            calligraphyText = "«فِيهِ سَاعَةٌ لَا يُوَافِقُهَا عَبْدٌ مُسْلِمٌ وَهُوَ قَائِمٌ يُصَلِّي يَسْأَلُ اللَّهَ شَيْئًا إِلَّا أَعْطَاهُ إِيَّاهُ»",
            subText = "تحري ساعة الاستجابة آخر نهار الجمعة قبل الغروب • بطاقة الجمعة المباركة #24",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_25",
            title = "نفحات الجمعة الزاهرة #25",
            calligraphyText = "«مَنِ اغْتَسَلَ يَوْمَ الْجُمُعَةِ وَتَطَهَّرَ بِمَا اسْتَطَاعَ مِنْ طُهْرٍ ثُمَّ رَاحَ فَلَهُ كَفَّارَةٌ»",
            subText = "سنن يوم الجمعة: الغسل، الطيب، السواك، والتبكير إلى الصلاة • بطاقة الجمعة المباركة #25",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_26",
            title = "طيب الأنفاس بالصلاة والسلام #26",
            calligraphyText = "«أَكْثِرُوا عَلَيَّ مِنَ الصَّلَاةِ يَوْمَ الْجُمُعَةِ فَإِنَّ صَلَاتَكُمْ مَعْرُوضَةٌ عَلَيَّ»",
            subText = "محبة النبي ﷺ وصلاتنا المعروضة عليه في هذا اليوم الأزهر • بطاقة الجمعة المباركة #26",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_27",
            title = "جمعة طيبة مستبشرة #27",
            calligraphyText = "﴿يَا أَيُّهَا الَّذِينَ آمَنُوا إِذَا نُودِيَ لِلصَّلَاةِ مِن يَوْمِ الْجُمُعَةِ فَاسْعَوْا إِلَىٰ ذِكْرِ اللَّهِ﴾",
            subText = "وجوب السعي إلى صلاة الجمعة والاستماع لخطبة الإمام • بطاقة الجمعة المباركة #27",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_28",
            title = "دعاء الجمعة المستجاب #28",
            calligraphyText = "«اللَّهُمَّ بَارِكْ لَنَا فِي جُمُعَتِنَا وَاغْفِرْ لَنَا وَلِوَالِدَيْنَا»",
            subText = "دعاء الجمعة الجامعة بالبركة والمغفرة وصلاح الأحوال • بطاقة الجمعة المباركة #28",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF9D174DL, 0xFF4A044EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_29",
            title = "طهارة الجمعة وبياض القلوب #29",
            calligraphyText = "«اللَّهُمَّ اجْعَلْ هَذِهِ الْجُمُعَةَ فَرَجًا لِكُلِّ صَابِرٍ، وَشِفَاءً لِكُلِّ مَرِيضٍ»",
            subText = "مناجاة ورجاء بالفرج العاجل ورفع البلاء عن أمة الإسلام • بطاقة الجمعة المباركة #29",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_30",
            title = "أريج الجمعة العاطر #30",
            calligraphyText = "«طَيَّبَ اللَّهُ جُمُعَتَكُمْ بِذِكْرِهِ وَشَرَحَ صُدُورَكُمْ بِنُورِهِ»",
            subText = "تهنئة يوم الجمعة المباركة بالأنس والسكينة والرضوان • بطاقة الجمعة المباركة #30",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF047857L, 0xFF022C22L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_31",
            title = "سكينة خطبة الجمعة #31",
            calligraphyText = "﴿إِنَّ اللَّهَ وَمَلَائِكَتَهُ يُصَلُّونَ عَلَى النَّبِيِّ يَا أَيُّهَا الَّذِينَ آمَنُوا صَلُّوا عَلَيْهِ وَسَلِّمُوا تَسْلِيمًا﴾",
            subText = "أكثروا من الصلاة والسلام على النبي في يوم الجمعة وليلتها • بطاقة الجمعة المباركة #31",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_32",
            title = "بشائر الفرج في يوم الجمعة #32",
            calligraphyText = "«خَيْرُ يَوْمٍ طَلَعَتْ عَلَيْهِ الشَّمْسُ يَوْمُ الْجُمُعَةِ»",
            subText = "فيه خُلق آدم وفيه أُدخل الجنة وفيه أُخرج منها وفيه تقوم الساعة • بطاقة الجمعة المباركة #32",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_33",
            title = "تسابيح الجمعة الجامعة #33",
            calligraphyText = "«مَنْ قَرَأَ سُورَةَ الْكَهْفِ فِي يَوْمِ الْجُمُعَةِ أَضَاءَ لَهُ مِنَ النُّورِ مَا بَيْنَ الْجُمُعَتَيْنِ»",
            subText = "نور سورة الكهف وضياؤها المستنير للمؤمنين كل أسبوع • بطاقة الجمعة المباركة #33",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_34",
            title = "رياض الجنان في يوم الجمعة #34",
            calligraphyText = "«فِيهِ سَاعَةٌ لَا يُوَافِقُهَا عَبْدٌ مُسْلِمٌ وَهُوَ قَائِمٌ يُصَلِّي يَسْأَلُ اللَّهَ شَيْئًا إِلَّا أَعْطَاهُ إِيَّاهُ»",
            subText = "تحري ساعة الاستجابة آخر نهار الجمعة قبل الغروب • بطاقة الجمعة المباركة #34",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_35",
            title = "نور ما بين الجمعتين #35",
            calligraphyText = "«مَنِ اغْتَسَلَ يَوْمَ الْجُمُعَةِ وَتَطَهَّرَ بِمَا اسْتَطَاعَ مِنْ طُهْرٍ ثُمَّ رَاحَ فَلَهُ كَفَّارَةٌ»",
            subText = "سنن يوم الجمعة: الغسل، الطيب، السواك، والتبكير إلى الصلاة • بطاقة الجمعة المباركة #35",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_36",
            title = "جمعة الخيرات والبركات #36",
            calligraphyText = "«أَكْثِرُوا عَلَيَّ مِنَ الصَّلَاةِ يَوْمَ الْجُمُعَةِ فَإِنَّ صَلَاتَكُمْ مَعْرُوضَةٌ عَلَيَّ»",
            subText = "محبة النبي ﷺ وصلاتنا المعروضة عليه في هذا اليوم الأزهر • بطاقة الجمعة المباركة #36",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_37",
            title = "استغفار ودعاء في الجمعة #37",
            calligraphyText = "﴿يَا أَيُّهَا الَّذِينَ آمَنُوا إِذَا نُودِيَ لِلصَّلَاةِ مِن يَوْمِ الْجُمُعَةِ فَاسْعَوْا إِلَىٰ ذِكْرِ اللَّهِ﴾",
            subText = "وجوب السعي إلى صلاة الجمعة والاستماع لخطبة الإمام • بطاقة الجمعة المباركة #37",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_38",
            title = "وفاء الأحبة في يوم الجمعة #38",
            calligraphyText = "«اللَّهُمَّ بَارِكْ لَنَا فِي جُمُعَتِنَا وَاغْفِرْ لَنَا وَلِوَالِدَيْنَا»",
            subText = "دعاء الجمعة الجامعة بالبركة والمغفرة وصلاح الأحوال • بطاقة الجمعة المباركة #38",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF9D174DL, 0xFF4A044EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_39",
            title = "صلوات محمدية مباركة #39",
            calligraphyText = "«اللَّهُمَّ اجْعَلْ هَذِهِ الْجُمُعَةَ فَرَجًا لِكُلِّ صَابِرٍ، وَشِفَاءً لِكُلِّ مَرِيضٍ»",
            subText = "مناجاة ورجاء بالفرج العاجل ورفع البلاء عن أمة الإسلام • بطاقة الجمعة المباركة #39",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_40",
            title = "عطر الجمعة الفواح #40",
            calligraphyText = "«طَيَّبَ اللَّهُ جُمُعَتَكُمْ بِذِكْرِهِ وَشَرَحَ صُدُورَكُمْ بِنُورِهِ»",
            subText = "تهنئة يوم الجمعة المباركة بالأنس والسكينة والرضوان • بطاقة الجمعة المباركة #40",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF047857L, 0xFF022C22L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_41",
            title = "جمعة مباركة عامرة بالذكر #41",
            calligraphyText = "﴿إِنَّ اللَّهَ وَمَلَائِكَتَهُ يُصَلُّونَ عَلَى النَّبِيِّ يَا أَيُّهَا الَّذِينَ آمَنُوا صَلُّوا عَلَيْهِ وَسَلِّمُوا تَسْلِيمًا﴾",
            subText = "أكثروا من الصلاة والسلام على النبي في يوم الجمعة وليلتها • بطاقة الجمعة المباركة #41",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_42",
            title = "نور سورة الكهف والسكينة #42",
            calligraphyText = "«خَيْرُ يَوْمٍ طَلَعَتْ عَلَيْهِ الشَّمْسُ يَوْمُ الْجُمُعَةِ»",
            subText = "فيه خُلق آدم وفيه أُدخل الجنة وفيه أُخرج منها وفيه تقوم الساعة • بطاقة الجمعة المباركة #42",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_43",
            title = "الصلاة على خير الأنام ﷺ #43",
            calligraphyText = "«مَنْ قَرَأَ سُورَةَ الْكَهْفِ فِي يَوْمِ الْجُمُعَةِ أَضَاءَ لَهُ مِنَ النُّورِ مَا بَيْنَ الْجُمُعَتَيْنِ»",
            subText = "نور سورة الكهف وضياؤها المستنير للمؤمنين كل أسبوع • بطاقة الجمعة المباركة #43",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_44",
            title = "ساعة الإجابة المباركة #44",
            calligraphyText = "«فِيهِ سَاعَةٌ لَا يُوَافِقُهَا عَبْدٌ مُسْلِمٌ وَهُوَ قَائِمٌ يُصَلِّي يَسْأَلُ اللَّهَ شَيْئًا إِلَّا أَعْطَاهُ إِيَّاهُ»",
            subText = "تحري ساعة الاستجابة آخر نهار الجمعة قبل الغروب • بطاقة الجمعة المباركة #44",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_45",
            title = "نفحات الجمعة الزاهرة #45",
            calligraphyText = "«مَنِ اغْتَسَلَ يَوْمَ الْجُمُعَةِ وَتَطَهَّرَ بِمَا اسْتَطَاعَ مِنْ طُهْرٍ ثُمَّ رَاحَ فَلَهُ كَفَّارَةٌ»",
            subText = "سنن يوم الجمعة: الغسل، الطيب، السواك، والتبكير إلى الصلاة • بطاقة الجمعة المباركة #45",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_46",
            title = "طيب الأنفاس بالصلاة والسلام #46",
            calligraphyText = "«أَكْثِرُوا عَلَيَّ مِنَ الصَّلَاةِ يَوْمَ الْجُمُعَةِ فَإِنَّ صَلَاتَكُمْ مَعْرُوضَةٌ عَلَيَّ»",
            subText = "محبة النبي ﷺ وصلاتنا المعروضة عليه في هذا اليوم الأزهر • بطاقة الجمعة المباركة #46",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_47",
            title = "جمعة طيبة مستبشرة #47",
            calligraphyText = "﴿يَا أَيُّهَا الَّذِينَ آمَنُوا إِذَا نُودِيَ لِلصَّلَاةِ مِن يَوْمِ الْجُمُعَةِ فَاسْعَوْا إِلَىٰ ذِكْرِ اللَّهِ﴾",
            subText = "وجوب السعي إلى صلاة الجمعة والاستماع لخطبة الإمام • بطاقة الجمعة المباركة #47",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_48",
            title = "دعاء الجمعة المستجاب #48",
            calligraphyText = "«اللَّهُمَّ بَارِكْ لَنَا فِي جُمُعَتِنَا وَاغْفِرْ لَنَا وَلِوَالِدَيْنَا»",
            subText = "دعاء الجمعة الجامعة بالبركة والمغفرة وصلاح الأحوال • بطاقة الجمعة المباركة #48",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF9D174DL, 0xFF4A044EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_49",
            title = "طهارة الجمعة وبياض القلوب #49",
            calligraphyText = "«اللَّهُمَّ اجْعَلْ هَذِهِ الْجُمُعَةَ فَرَجًا لِكُلِّ صَابِرٍ، وَشِفَاءً لِكُلِّ مَرِيضٍ»",
            subText = "مناجاة ورجاء بالفرج العاجل ورفع البلاء عن أمة الإسلام • بطاقة الجمعة المباركة #49",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_50",
            title = "أريج الجمعة العاطر #50",
            calligraphyText = "«طَيَّبَ اللَّهُ جُمُعَتَكُمْ بِذِكْرِهِ وَشَرَحَ صُدُورَكُمْ بِنُورِهِ»",
            subText = "تهنئة يوم الجمعة المباركة بالأنس والسكينة والرضوان • بطاقة الجمعة المباركة #50",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF047857L, 0xFF022C22L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_51",
            title = "سكينة خطبة الجمعة #51",
            calligraphyText = "﴿إِنَّ اللَّهَ وَمَلَائِكَتَهُ يُصَلُّونَ عَلَى النَّبِيِّ يَا أَيُّهَا الَّذِينَ آمَنُوا صَلُّوا عَلَيْهِ وَسَلِّمُوا تَسْلِيمًا﴾",
            subText = "أكثروا من الصلاة والسلام على النبي في يوم الجمعة وليلتها • بطاقة الجمعة المباركة #51",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_52",
            title = "بشائر الفرج في يوم الجمعة #52",
            calligraphyText = "«خَيْرُ يَوْمٍ طَلَعَتْ عَلَيْهِ الشَّمْسُ يَوْمُ الْجُمُعَةِ»",
            subText = "فيه خُلق آدم وفيه أُدخل الجنة وفيه أُخرج منها وفيه تقوم الساعة • بطاقة الجمعة المباركة #52",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_53",
            title = "تسابيح الجمعة الجامعة #53",
            calligraphyText = "«مَنْ قَرَأَ سُورَةَ الْكَهْفِ فِي يَوْمِ الْجُمُعَةِ أَضَاءَ لَهُ مِنَ النُّورِ مَا بَيْنَ الْجُمُعَتَيْنِ»",
            subText = "نور سورة الكهف وضياؤها المستنير للمؤمنين كل أسبوع • بطاقة الجمعة المباركة #53",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_54",
            title = "رياض الجنان في يوم الجمعة #54",
            calligraphyText = "«فِيهِ سَاعَةٌ لَا يُوَافِقُهَا عَبْدٌ مُسْلِمٌ وَهُوَ قَائِمٌ يُصَلِّي يَسْأَلُ اللَّهَ شَيْئًا إِلَّا أَعْطَاهُ إِيَّاهُ»",
            subText = "تحري ساعة الاستجابة آخر نهار الجمعة قبل الغروب • بطاقة الجمعة المباركة #54",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_55",
            title = "نور ما بين الجمعتين #55",
            calligraphyText = "«مَنِ اغْتَسَلَ يَوْمَ الْجُمُعَةِ وَتَطَهَّرَ بِمَا اسْتَطَاعَ مِنْ طُهْرٍ ثُمَّ رَاحَ فَلَهُ كَفَّارَةٌ»",
            subText = "سنن يوم الجمعة: الغسل، الطيب، السواك، والتبكير إلى الصلاة • بطاقة الجمعة المباركة #55",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_56",
            title = "جمعة الخيرات والبركات #56",
            calligraphyText = "«أَكْثِرُوا عَلَيَّ مِنَ الصَّلَاةِ يَوْمَ الْجُمُعَةِ فَإِنَّ صَلَاتَكُمْ مَعْرُوضَةٌ عَلَيَّ»",
            subText = "محبة النبي ﷺ وصلاتنا المعروضة عليه في هذا اليوم الأزهر • بطاقة الجمعة المباركة #56",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_57",
            title = "استغفار ودعاء في الجمعة #57",
            calligraphyText = "﴿يَا أَيُّهَا الَّذِينَ آمَنُوا إِذَا نُودِيَ لِلصَّلَاةِ مِن يَوْمِ الْجُمُعَةِ فَاسْعَوْا إِلَىٰ ذِكْرِ اللَّهِ﴾",
            subText = "وجوب السعي إلى صلاة الجمعة والاستماع لخطبة الإمام • بطاقة الجمعة المباركة #57",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_58",
            title = "وفاء الأحبة في يوم الجمعة #58",
            calligraphyText = "«اللَّهُمَّ بَارِكْ لَنَا فِي جُمُعَتِنَا وَاغْفِرْ لَنَا وَلِوَالِدَيْنَا»",
            subText = "دعاء الجمعة الجامعة بالبركة والمغفرة وصلاح الأحوال • بطاقة الجمعة المباركة #58",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF9D174DL, 0xFF4A044EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_59",
            title = "صلوات محمدية مباركة #59",
            calligraphyText = "«اللَّهُمَّ اجْعَلْ هَذِهِ الْجُمُعَةَ فَرَجًا لِكُلِّ صَابِرٍ، وَشِفَاءً لِكُلِّ مَرِيضٍ»",
            subText = "مناجاة ورجاء بالفرج العاجل ورفع البلاء عن أمة الإسلام • بطاقة الجمعة المباركة #59",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_60",
            title = "عطر الجمعة الفواح #60",
            calligraphyText = "«طَيَّبَ اللَّهُ جُمُعَتَكُمْ بِذِكْرِهِ وَشَرَحَ صُدُورَكُمْ بِنُورِهِ»",
            subText = "تهنئة يوم الجمعة المباركة بالأنس والسكينة والرضوان • بطاقة الجمعة المباركة #60",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF047857L, 0xFF022C22L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_61",
            title = "جمعة مباركة عامرة بالذكر #61",
            calligraphyText = "﴿إِنَّ اللَّهَ وَمَلَائِكَتَهُ يُصَلُّونَ عَلَى النَّبِيِّ يَا أَيُّهَا الَّذِينَ آمَنُوا صَلُّوا عَلَيْهِ وَسَلِّمُوا تَسْلِيمًا﴾",
            subText = "أكثروا من الصلاة والسلام على النبي في يوم الجمعة وليلتها • بطاقة الجمعة المباركة #61",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_62",
            title = "نور سورة الكهف والسكينة #62",
            calligraphyText = "«خَيْرُ يَوْمٍ طَلَعَتْ عَلَيْهِ الشَّمْسُ يَوْمُ الْجُمُعَةِ»",
            subText = "فيه خُلق آدم وفيه أُدخل الجنة وفيه أُخرج منها وفيه تقوم الساعة • بطاقة الجمعة المباركة #62",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_63",
            title = "الصلاة على خير الأنام ﷺ #63",
            calligraphyText = "«مَنْ قَرَأَ سُورَةَ الْكَهْفِ فِي يَوْمِ الْجُمُعَةِ أَضَاءَ لَهُ مِنَ النُّورِ مَا بَيْنَ الْجُمُعَتَيْنِ»",
            subText = "نور سورة الكهف وضياؤها المستنير للمؤمنين كل أسبوع • بطاقة الجمعة المباركة #63",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_64",
            title = "ساعة الإجابة المباركة #64",
            calligraphyText = "«فِيهِ سَاعَةٌ لَا يُوَافِقُهَا عَبْدٌ مُسْلِمٌ وَهُوَ قَائِمٌ يُصَلِّي يَسْأَلُ اللَّهَ شَيْئًا إِلَّا أَعْطَاهُ إِيَّاهُ»",
            subText = "تحري ساعة الاستجابة آخر نهار الجمعة قبل الغروب • بطاقة الجمعة المباركة #64",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_65",
            title = "نفحات الجمعة الزاهرة #65",
            calligraphyText = "«مَنِ اغْتَسَلَ يَوْمَ الْجُمُعَةِ وَتَطَهَّرَ بِمَا اسْتَطَاعَ مِنْ طُهْرٍ ثُمَّ رَاحَ فَلَهُ كَفَّارَةٌ»",
            subText = "سنن يوم الجمعة: الغسل، الطيب، السواك، والتبكير إلى الصلاة • بطاقة الجمعة المباركة #65",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_66",
            title = "طيب الأنفاس بالصلاة والسلام #66",
            calligraphyText = "«أَكْثِرُوا عَلَيَّ مِنَ الصَّلَاةِ يَوْمَ الْجُمُعَةِ فَإِنَّ صَلَاتَكُمْ مَعْرُوضَةٌ عَلَيَّ»",
            subText = "محبة النبي ﷺ وصلاتنا المعروضة عليه في هذا اليوم الأزهر • بطاقة الجمعة المباركة #66",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_67",
            title = "جمعة طيبة مستبشرة #67",
            calligraphyText = "﴿يَا أَيُّهَا الَّذِينَ آمَنُوا إِذَا نُودِيَ لِلصَّلَاةِ مِن يَوْمِ الْجُمُعَةِ فَاسْعَوْا إِلَىٰ ذِكْرِ اللَّهِ﴾",
            subText = "وجوب السعي إلى صلاة الجمعة والاستماع لخطبة الإمام • بطاقة الجمعة المباركة #67",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_68",
            title = "دعاء الجمعة المستجاب #68",
            calligraphyText = "«اللَّهُمَّ بَارِكْ لَنَا فِي جُمُعَتِنَا وَاغْفِرْ لَنَا وَلِوَالِدَيْنَا»",
            subText = "دعاء الجمعة الجامعة بالبركة والمغفرة وصلاح الأحوال • بطاقة الجمعة المباركة #68",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF9D174DL, 0xFF4A044EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_69",
            title = "طهارة الجمعة وبياض القلوب #69",
            calligraphyText = "«اللَّهُمَّ اجْعَلْ هَذِهِ الْجُمُعَةَ فَرَجًا لِكُلِّ صَابِرٍ، وَشِفَاءً لِكُلِّ مَرِيضٍ»",
            subText = "مناجاة ورجاء بالفرج العاجل ورفع البلاء عن أمة الإسلام • بطاقة الجمعة المباركة #69",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_70",
            title = "أريج الجمعة العاطر #70",
            calligraphyText = "«طَيَّبَ اللَّهُ جُمُعَتَكُمْ بِذِكْرِهِ وَشَرَحَ صُدُورَكُمْ بِنُورِهِ»",
            subText = "تهنئة يوم الجمعة المباركة بالأنس والسكينة والرضوان • بطاقة الجمعة المباركة #70",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF047857L, 0xFF022C22L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_71",
            title = "سكينة خطبة الجمعة #71",
            calligraphyText = "﴿إِنَّ اللَّهَ وَمَلَائِكَتَهُ يُصَلُّونَ عَلَى النَّبِيِّ يَا أَيُّهَا الَّذِينَ آمَنُوا صَلُّوا عَلَيْهِ وَسَلِّمُوا تَسْلِيمًا﴾",
            subText = "أكثروا من الصلاة والسلام على النبي في يوم الجمعة وليلتها • بطاقة الجمعة المباركة #71",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_72",
            title = "بشائر الفرج في يوم الجمعة #72",
            calligraphyText = "«خَيْرُ يَوْمٍ طَلَعَتْ عَلَيْهِ الشَّمْسُ يَوْمُ الْجُمُعَةِ»",
            subText = "فيه خُلق آدم وفيه أُدخل الجنة وفيه أُخرج منها وفيه تقوم الساعة • بطاقة الجمعة المباركة #72",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_73",
            title = "تسابيح الجمعة الجامعة #73",
            calligraphyText = "«مَنْ قَرَأَ سُورَةَ الْكَهْفِ فِي يَوْمِ الْجُمُعَةِ أَضَاءَ لَهُ مِنَ النُّورِ مَا بَيْنَ الْجُمُعَتَيْنِ»",
            subText = "نور سورة الكهف وضياؤها المستنير للمؤمنين كل أسبوع • بطاقة الجمعة المباركة #73",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_74",
            title = "رياض الجنان في يوم الجمعة #74",
            calligraphyText = "«فِيهِ سَاعَةٌ لَا يُوَافِقُهَا عَبْدٌ مُسْلِمٌ وَهُوَ قَائِمٌ يُصَلِّي يَسْأَلُ اللَّهَ شَيْئًا إِلَّا أَعْطَاهُ إِيَّاهُ»",
            subText = "تحري ساعة الاستجابة آخر نهار الجمعة قبل الغروب • بطاقة الجمعة المباركة #74",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_75",
            title = "نور ما بين الجمعتين #75",
            calligraphyText = "«مَنِ اغْتَسَلَ يَوْمَ الْجُمُعَةِ وَتَطَهَّرَ بِمَا اسْتَطَاعَ مِنْ طُهْرٍ ثُمَّ رَاحَ فَلَهُ كَفَّارَةٌ»",
            subText = "سنن يوم الجمعة: الغسل، الطيب، السواك، والتبكير إلى الصلاة • بطاقة الجمعة المباركة #75",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_76",
            title = "جمعة الخيرات والبركات #76",
            calligraphyText = "«أَكْثِرُوا عَلَيَّ مِنَ الصَّلَاةِ يَوْمَ الْجُمُعَةِ فَإِنَّ صَلَاتَكُمْ مَعْرُوضَةٌ عَلَيَّ»",
            subText = "محبة النبي ﷺ وصلاتنا المعروضة عليه في هذا اليوم الأزهر • بطاقة الجمعة المباركة #76",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_77",
            title = "استغفار ودعاء في الجمعة #77",
            calligraphyText = "﴿يَا أَيُّهَا الَّذِينَ آمَنُوا إِذَا نُودِيَ لِلصَّلَاةِ مِن يَوْمِ الْجُمُعَةِ فَاسْعَوْا إِلَىٰ ذِكْرِ اللَّهِ﴾",
            subText = "وجوب السعي إلى صلاة الجمعة والاستماع لخطبة الإمام • بطاقة الجمعة المباركة #77",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_78",
            title = "وفاء الأحبة في يوم الجمعة #78",
            calligraphyText = "«اللَّهُمَّ بَارِكْ لَنَا فِي جُمُعَتِنَا وَاغْفِرْ لَنَا وَلِوَالِدَيْنَا»",
            subText = "دعاء الجمعة الجامعة بالبركة والمغفرة وصلاح الأحوال • بطاقة الجمعة المباركة #78",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF9D174DL, 0xFF4A044EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_79",
            title = "صلوات محمدية مباركة #79",
            calligraphyText = "«اللَّهُمَّ اجْعَلْ هَذِهِ الْجُمُعَةَ فَرَجًا لِكُلِّ صَابِرٍ، وَشِفَاءً لِكُلِّ مَرِيضٍ»",
            subText = "مناجاة ورجاء بالفرج العاجل ورفع البلاء عن أمة الإسلام • بطاقة الجمعة المباركة #79",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_80",
            title = "عطر الجمعة الفواح #80",
            calligraphyText = "«طَيَّبَ اللَّهُ جُمُعَتَكُمْ بِذِكْرِهِ وَشَرَحَ صُدُورَكُمْ بِنُورِهِ»",
            subText = "تهنئة يوم الجمعة المباركة بالأنس والسكينة والرضوان • بطاقة الجمعة المباركة #80",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF047857L, 0xFF022C22L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_81",
            title = "جمعة مباركة عامرة بالذكر #81",
            calligraphyText = "﴿إِنَّ اللَّهَ وَمَلَائِكَتَهُ يُصَلُّونَ عَلَى النَّبِيِّ يَا أَيُّهَا الَّذِينَ آمَنُوا صَلُّوا عَلَيْهِ وَسَلِّمُوا تَسْلِيمًا﴾",
            subText = "أكثروا من الصلاة والسلام على النبي في يوم الجمعة وليلتها • بطاقة الجمعة المباركة #81",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_82",
            title = "نور سورة الكهف والسكينة #82",
            calligraphyText = "«خَيْرُ يَوْمٍ طَلَعَتْ عَلَيْهِ الشَّمْسُ يَوْمُ الْجُمُعَةِ»",
            subText = "فيه خُلق آدم وفيه أُدخل الجنة وفيه أُخرج منها وفيه تقوم الساعة • بطاقة الجمعة المباركة #82",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_83",
            title = "الصلاة على خير الأنام ﷺ #83",
            calligraphyText = "«مَنْ قَرَأَ سُورَةَ الْكَهْفِ فِي يَوْمِ الْجُمُعَةِ أَضَاءَ لَهُ مِنَ النُّورِ مَا بَيْنَ الْجُمُعَتَيْنِ»",
            subText = "نور سورة الكهف وضياؤها المستنير للمؤمنين كل أسبوع • بطاقة الجمعة المباركة #83",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_84",
            title = "ساعة الإجابة المباركة #84",
            calligraphyText = "«فِيهِ سَاعَةٌ لَا يُوَافِقُهَا عَبْدٌ مُسْلِمٌ وَهُوَ قَائِمٌ يُصَلِّي يَسْأَلُ اللَّهَ شَيْئًا إِلَّا أَعْطَاهُ إِيَّاهُ»",
            subText = "تحري ساعة الاستجابة آخر نهار الجمعة قبل الغروب • بطاقة الجمعة المباركة #84",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_85",
            title = "نفحات الجمعة الزاهرة #85",
            calligraphyText = "«مَنِ اغْتَسَلَ يَوْمَ الْجُمُعَةِ وَتَطَهَّرَ بِمَا اسْتَطَاعَ مِنْ طُهْرٍ ثُمَّ رَاحَ فَلَهُ كَفَّارَةٌ»",
            subText = "سنن يوم الجمعة: الغسل، الطيب، السواك، والتبكير إلى الصلاة • بطاقة الجمعة المباركة #85",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_86",
            title = "طيب الأنفاس بالصلاة والسلام #86",
            calligraphyText = "«أَكْثِرُوا عَلَيَّ مِنَ الصَّلَاةِ يَوْمَ الْجُمُعَةِ فَإِنَّ صَلَاتَكُمْ مَعْرُوضَةٌ عَلَيَّ»",
            subText = "محبة النبي ﷺ وصلاتنا المعروضة عليه في هذا اليوم الأزهر • بطاقة الجمعة المباركة #86",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_87",
            title = "جمعة طيبة مستبشرة #87",
            calligraphyText = "﴿يَا أَيُّهَا الَّذِينَ آمَنُوا إِذَا نُودِيَ لِلصَّلَاةِ مِن يَوْمِ الْجُمُعَةِ فَاسْعَوْا إِلَىٰ ذِكْرِ اللَّهِ﴾",
            subText = "وجوب السعي إلى صلاة الجمعة والاستماع لخطبة الإمام • بطاقة الجمعة المباركة #87",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_88",
            title = "دعاء الجمعة المستجاب #88",
            calligraphyText = "«اللَّهُمَّ بَارِكْ لَنَا فِي جُمُعَتِنَا وَاغْفِرْ لَنَا وَلِوَالِدَيْنَا»",
            subText = "دعاء الجمعة الجامعة بالبركة والمغفرة وصلاح الأحوال • بطاقة الجمعة المباركة #88",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF9D174DL, 0xFF4A044EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_89",
            title = "طهارة الجمعة وبياض القلوب #89",
            calligraphyText = "«اللَّهُمَّ اجْعَلْ هَذِهِ الْجُمُعَةَ فَرَجًا لِكُلِّ صَابِرٍ، وَشِفَاءً لِكُلِّ مَرِيضٍ»",
            subText = "مناجاة ورجاء بالفرج العاجل ورفع البلاء عن أمة الإسلام • بطاقة الجمعة المباركة #89",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_90",
            title = "أريج الجمعة العاطر #90",
            calligraphyText = "«طَيَّبَ اللَّهُ جُمُعَتَكُمْ بِذِكْرِهِ وَشَرَحَ صُدُورَكُمْ بِنُورِهِ»",
            subText = "تهنئة يوم الجمعة المباركة بالأنس والسكينة والرضوان • بطاقة الجمعة المباركة #90",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF047857L, 0xFF022C22L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_91",
            title = "سكينة خطبة الجمعة #91",
            calligraphyText = "﴿إِنَّ اللَّهَ وَمَلَائِكَتَهُ يُصَلُّونَ عَلَى النَّبِيِّ يَا أَيُّهَا الَّذِينَ آمَنُوا صَلُّوا عَلَيْهِ وَسَلِّمُوا تَسْلِيمًا﴾",
            subText = "أكثروا من الصلاة والسلام على النبي في يوم الجمعة وليلتها • بطاقة الجمعة المباركة #91",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_92",
            title = "بشائر الفرج في يوم الجمعة #92",
            calligraphyText = "«خَيْرُ يَوْمٍ طَلَعَتْ عَلَيْهِ الشَّمْسُ يَوْمُ الْجُمُعَةِ»",
            subText = "فيه خُلق آدم وفيه أُدخل الجنة وفيه أُخرج منها وفيه تقوم الساعة • بطاقة الجمعة المباركة #92",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_93",
            title = "تسابيح الجمعة الجامعة #93",
            calligraphyText = "«مَنْ قَرَأَ سُورَةَ الْكَهْفِ فِي يَوْمِ الْجُمُعَةِ أَضَاءَ لَهُ مِنَ النُّورِ مَا بَيْنَ الْجُمُعَتَيْنِ»",
            subText = "نور سورة الكهف وضياؤها المستنير للمؤمنين كل أسبوع • بطاقة الجمعة المباركة #93",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_94",
            title = "رياض الجنان في يوم الجمعة #94",
            calligraphyText = "«فِيهِ سَاعَةٌ لَا يُوَافِقُهَا عَبْدٌ مُسْلِمٌ وَهُوَ قَائِمٌ يُصَلِّي يَسْأَلُ اللَّهَ شَيْئًا إِلَّا أَعْطَاهُ إِيَّاهُ»",
            subText = "تحري ساعة الاستجابة آخر نهار الجمعة قبل الغروب • بطاقة الجمعة المباركة #94",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_95",
            title = "نور ما بين الجمعتين #95",
            calligraphyText = "«مَنِ اغْتَسَلَ يَوْمَ الْجُمُعَةِ وَتَطَهَّرَ بِمَا اسْتَطَاعَ مِنْ طُهْرٍ ثُمَّ رَاحَ فَلَهُ كَفَّارَةٌ»",
            subText = "سنن يوم الجمعة: الغسل، الطيب، السواك، والتبكير إلى الصلاة • بطاقة الجمعة المباركة #95",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_96",
            title = "جمعة الخيرات والبركات #96",
            calligraphyText = "«أَكْثِرُوا عَلَيَّ مِنَ الصَّلَاةِ يَوْمَ الْجُمُعَةِ فَإِنَّ صَلَاتَكُمْ مَعْرُوضَةٌ عَلَيَّ»",
            subText = "محبة النبي ﷺ وصلاتنا المعروضة عليه في هذا اليوم الأزهر • بطاقة الجمعة المباركة #96",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_97",
            title = "استغفار ودعاء في الجمعة #97",
            calligraphyText = "﴿يَا أَيُّهَا الَّذِينَ آمَنُوا إِذَا نُودِيَ لِلصَّلَاةِ مِن يَوْمِ الْجُمُعَةِ فَاسْعَوْا إِلَىٰ ذِكْرِ اللَّهِ﴾",
            subText = "وجوب السعي إلى صلاة الجمعة والاستماع لخطبة الإمام • بطاقة الجمعة المباركة #97",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_98",
            title = "وفاء الأحبة في يوم الجمعة #98",
            calligraphyText = "«اللَّهُمَّ بَارِكْ لَنَا فِي جُمُعَتِنَا وَاغْفِرْ لَنَا وَلِوَالِدَيْنَا»",
            subText = "دعاء الجمعة الجامعة بالبركة والمغفرة وصلاح الأحوال • بطاقة الجمعة المباركة #98",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF9D174DL, 0xFF4A044EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_99",
            title = "صلوات محمدية مباركة #99",
            calligraphyText = "«اللَّهُمَّ اجْعَلْ هَذِهِ الْجُمُعَةَ فَرَجًا لِكُلِّ صَابِرٍ، وَشِفَاءً لِكُلِّ مَرِيضٍ»",
            subText = "مناجاة ورجاء بالفرج العاجل ورفع البلاء عن أمة الإسلام • بطاقة الجمعة المباركة #99",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_jummah_100",
            title = "عطر الجمعة الفواح #100",
            calligraphyText = "«طَيَّبَ اللَّهُ جُمُعَتَكُمْ بِذِكْرِهِ وَشَرَحَ صُدُورَكُمْ بِنُورِهِ»",
            subText = "تهنئة يوم الجمعة المباركة بالأنس والسكينة والرضوان • بطاقة الجمعة المباركة #100",
            category = "بطاقات الجمعة",
            gradientColors = listOf(0xFF047857L, 0xFF022C22L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_1",
            title = "خلفية عيد مبارك والزخارف الذهبية الراقية #1",
            calligraphyText = "«تَقَبَّلَ اللَّهُ مِنَّا وَمِنْكُمُ الصَّالِحَاتِ، وَجَعَلَ أَيَّامَكُمْ كُلَّهَا أَعْيَادًا وَسُرُورًا»",
            subText = "تصميم فاخر بزخارف إسلامية ذهبية مبهجة بمناسبة العيد لحفظها كخلفية أو إرسالها • تهاني العيد #1",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF9D174DL, 0xFF4A044EL),
            drawableRes = R.drawable.img_islamic_bg
        ),
        GalleryItem(
            id = "g_eid_2",
            title = "عيد الأضحى وفرحة الحجيج #2",
            calligraphyText = "«عِيدُكُمْ مُبَارَكٌ، وَكُلُّ عَامٍ وَأَنْتُمْ إِلَى اللَّهِ أَقْرَبُ»",
            subText = "أعاده الله عليكم وعلى الأمة الإسلامية باليمن والخير والمسرات • بطاقة تهنئة العيد #2",
            category = "تهاني العيد",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_3",
            title = "تكبيرات العيد الصادحة #3",
            calligraphyText = "«اللَّهُ أَكْبَرُ، اللَّهُ أَكْبَرُ، لَا إِلَهَ إِلَّا اللَّهُ، اللَّهُ أَكْبَرُ، اللَّهُ أَكْبَرُ، وَلِلَّهِ الْحَمْدُ»",
            subText = "تكبيرات العيد الشجية التي تصدح في الآفاق فرحاً بطاعة الله • بطاقة تهنئة العيد #3",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_4",
            title = "فرحة العيد ولمة الأهل #4",
            calligraphyText = "«جَعَلَ اللَّهُ عِيدَكُمْ فَرَحًا بِأَعْمَالٍ قُبِلَتْ، وَذُنُوبٍ مُحِيَتْ، وَدَرَجَاتٍ رُفِعَتْ»",
            subText = "بشارة الفائزين في مواسم الطاعات ببهجة العيد والرضوان • بطاقة تهنئة العيد #4",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_5",
            title = "عساكم من عواده دائماً #5",
            calligraphyText = "﴿قُلْ بِفَضْلِ اللَّهِ وَبِرَحْمَتِهِ فَبِذَٰلِكَ فَلْيَفْرَحُوا هُوَ خَيْرٌ مِّمَّا يَجْمَعُونَ﴾",
            subText = "فرحة العيد شكر لله على التوفيق للصيام والقيام والحج • بطاقة تهنئة العيد #5",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_6",
            title = "تهنئة عيد مباركة للأحبة #6",
            calligraphyText = "«عَسَاكُمْ مِنْ عُوَّادِهِ، وَفَالُكُمْ الْخَيْرُ وَالسَّعَادَةُ فِي كُلِّ عَامٍ»",
            subText = "أجمل التبريكات بدوام الصحة والعافية ولم الشمل المبارك • بطاقة تهنئة العيد #6",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_7",
            title = "بهجة العيد والسرور #7",
            calligraphyText = "«أَيَّامُ التَّشْرِيقِ أَيَّامُ أَكْلٍ وَشُرْبٍ وَذِكْرٍ لِلَّهِ عَزَّ وَجَلَّ»",
            subText = "أيام عيد الأضحى المبارك وشعائر الحج وذكر الله تعالى • بطاقة تهنئة العيد #7",
            category = "تهاني العيد",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_8",
            title = "أعياد المسلمين المجيدة #8",
            calligraphyText = "«هَنِيئًا لَكُمْ بِالْعِيدِ السَّعِيدِ، أَدَامَ اللَّهُ عَلَيْكُمُ الْبَهْجَةَ وَالأَفْرَاحَ»",
            subText = "تهنئة الأهل والأحبة والأصدقاء في مشارق الأرض ومغاربها • بطاقة تهنئة العيد #8",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_9",
            title = "قبول الطاعات وفرحة العيد #9",
            calligraphyText = "«اللَّهُمَّ أَدْخِلْ عَلَى كُلِّ بَيْتٍ إِسْلَامِيٍّ فَرْحَةَ الْعِيدِ وَالْأَمَانَ»",
            subText = "دعاء العيد بالسلام والاطمئنان وزوال الكرب عن المحتاجين • بطاقة تهنئة العيد #9",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_10",
            title = "عيدكم نور وسعادة #10",
            calligraphyText = "«عِيدٌ سَعِيدٌ مُبَارَكٌ، تَقَبَّلَ اللَّهُ طَاعَتَكُمْ وَأَتَمَّ بِالْخَيْرِ فَرْحَتَكُمْ»",
            subText = "صلة الأرحام وإفشاء السلام وإدخال السرور على القلوب • بطاقة تهنئة العيد #10",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF047857L, 0xFF022C22L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_11",
            title = "تهاني العيد العطرة #11",
            calligraphyText = "«تَقَبَّلَ اللَّهُ مِنَّا وَمِنْكُمُ الصَّالِحَاتِ، وَجَعَلَ أَيَّامَكُمْ كُلَّهَا أَعْيَادًا وَسُرُورًا»",
            subText = "تهنئة عيد الفطر والأضحى المبارك بصالح العمل والقبول • بطاقة تهنئة العيد #11",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF9D174DL, 0xFF4A044EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_12",
            title = "بشائر العيد والبركة #12",
            calligraphyText = "«عِيدُكُمْ مُبَارَكٌ، وَكُلُّ عَامٍ وَأَنْتُمْ إِلَى اللَّهِ أَقْرَبُ»",
            subText = "أعاده الله عليكم وعلى الأمة الإسلامية باليمن والخير والمسرات • بطاقة تهنئة العيد #12",
            category = "تهاني العيد",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_13",
            title = "عيد المحبة والسلام #13",
            calligraphyText = "«اللَّهُ أَكْبَرُ، اللَّهُ أَكْبَرُ، لَا إِلَهَ إِلَّا اللَّهُ، اللَّهُ أَكْبَرُ، اللَّهُ أَكْبَرُ، وَلِلَّهِ الْحَمْدُ»",
            subText = "تكبيرات العيد الشجية التي تصدح في الآفاق فرحاً بطاعة الله • بطاقة تهنئة العيد #13",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_14",
            title = "عيد الأمل والأفراح #14",
            calligraphyText = "«جَعَلَ اللَّهُ عِيدَكُمْ فَرَحًا بِأَعْمَالٍ قُبِلَتْ، وَذُنُوبٍ مُحِيَتْ، وَدَرَجَاتٍ رُفِعَتْ»",
            subText = "بشارة الفائزين في مواسم الطاعات ببهجة العيد والرضوان • بطاقة تهنئة العيد #14",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_15",
            title = "أفراح العيد في كل دار #15",
            calligraphyText = "﴿قُلْ بِفَضْلِ اللَّهِ وَبِرَحْمَتِهِ فَبِذَٰلِكَ فَلْيَفْرَحُوا هُوَ خَيْرٌ مِّمَّا يَجْمَعُونَ﴾",
            subText = "فرحة العيد شكر لله على التوفيق للصيام والقيام والحج • بطاقة تهنئة العيد #15",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_16",
            title = "مسرات العيد السعيد #16",
            calligraphyText = "«عَسَاكُمْ مِنْ عُوَّادِهِ، وَفَالُكُمْ الْخَيْرُ وَالسَّعَادَةُ فِي كُلِّ عَامٍ»",
            subText = "أجمل التبريكات بدوام الصحة والعافية ولم الشمل المبارك • بطاقة تهنئة العيد #16",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_17",
            title = "أيام مباركة وأعياد هانئة #17",
            calligraphyText = "«أَيَّامُ التَّشْرِيقِ أَيَّامُ أَكْلٍ وَشُرْبٍ وَذِكْرٍ لِلَّهِ عَزَّ وَجَلَّ»",
            subText = "أيام عيد الأضحى المبارك وشعائر الحج وذكر الله تعالى • بطاقة تهنئة العيد #17",
            category = "تهاني العيد",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_18",
            title = "باقات ورد وتهاني العيد #18",
            calligraphyText = "«هَنِيئًا لَكُمْ بِالْعِيدِ السَّعِيدِ، أَدَامَ اللَّهُ عَلَيْكُمُ الْبَهْجَةَ وَالأَفْرَاحَ»",
            subText = "تهنئة الأهل والأحبة والأصدقاء في مشارق الأرض ومغاربها • بطاقة تهنئة العيد #18",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_19",
            title = "بسمة العيد وفرحة الصغار #19",
            calligraphyText = "«اللَّهُمَّ أَدْخِلْ عَلَى كُلِّ بَيْتٍ إِسْلَامِيٍّ فَرْحَةَ الْعِيدِ وَالْأَمَانَ»",
            subText = "دعاء العيد بالسلام والاطمئنان وزوال الكرب عن المحتاجين • بطاقة تهنئة العيد #19",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_20",
            title = "عيدكم مبارك وعامر بالخير #20",
            calligraphyText = "«عِيدٌ سَعِيدٌ مُبَارَكٌ، تَقَبَّلَ اللَّهُ طَاعَتَكُمْ وَأَتَمَّ بِالْخَيْرِ فَرْحَتَكُمْ»",
            subText = "صلة الأرحام وإفشاء السلام وإدخال السرور على القلوب • بطاقة تهنئة العيد #20",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF047857L, 0xFF022C22L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_21",
            title = "عيد الفطر المبارك السعيد #21",
            calligraphyText = "«تَقَبَّلَ اللَّهُ مِنَّا وَمِنْكُمُ الصَّالِحَاتِ، وَجَعَلَ أَيَّامَكُمْ كُلَّهَا أَعْيَادًا وَسُرُورًا»",
            subText = "تهنئة عيد الفطر والأضحى المبارك بصالح العمل والقبول • بطاقة تهنئة العيد #21",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF9D174DL, 0xFF4A044EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_22",
            title = "عيد الأضحى وفرحة الحجيج #22",
            calligraphyText = "«عِيدُكُمْ مُبَارَكٌ، وَكُلُّ عَامٍ وَأَنْتُمْ إِلَى اللَّهِ أَقْرَبُ»",
            subText = "أعاده الله عليكم وعلى الأمة الإسلامية باليمن والخير والمسرات • بطاقة تهنئة العيد #22",
            category = "تهاني العيد",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_23",
            title = "تكبيرات العيد الصادحة #23",
            calligraphyText = "«اللَّهُ أَكْبَرُ، اللَّهُ أَكْبَرُ، لَا إِلَهَ إِلَّا اللَّهُ، اللَّهُ أَكْبَرُ، اللَّهُ أَكْبَرُ، وَلِلَّهِ الْحَمْدُ»",
            subText = "تكبيرات العيد الشجية التي تصدح في الآفاق فرحاً بطاعة الله • بطاقة تهنئة العيد #23",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_24",
            title = "فرحة العيد ولمة الأهل #24",
            calligraphyText = "«جَعَلَ اللَّهُ عِيدَكُمْ فَرَحًا بِأَعْمَالٍ قُبِلَتْ، وَذُنُوبٍ مُحِيَتْ، وَدَرَجَاتٍ رُفِعَتْ»",
            subText = "بشارة الفائزين في مواسم الطاعات ببهجة العيد والرضوان • بطاقة تهنئة العيد #24",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_25",
            title = "عساكم من عواده دائماً #25",
            calligraphyText = "﴿قُلْ بِفَضْلِ اللَّهِ وَبِرَحْمَتِهِ فَبِذَٰلِكَ فَلْيَفْرَحُوا هُوَ خَيْرٌ مِّمَّا يَجْمَعُونَ﴾",
            subText = "فرحة العيد شكر لله على التوفيق للصيام والقيام والحج • بطاقة تهنئة العيد #25",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_26",
            title = "تهنئة عيد مباركة للأحبة #26",
            calligraphyText = "«عَسَاكُمْ مِنْ عُوَّادِهِ، وَفَالُكُمْ الْخَيْرُ وَالسَّعَادَةُ فِي كُلِّ عَامٍ»",
            subText = "أجمل التبريكات بدوام الصحة والعافية ولم الشمل المبارك • بطاقة تهنئة العيد #26",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_27",
            title = "بهجة العيد والسرور #27",
            calligraphyText = "«أَيَّامُ التَّشْرِيقِ أَيَّامُ أَكْلٍ وَشُرْبٍ وَذِكْرٍ لِلَّهِ عَزَّ وَجَلَّ»",
            subText = "أيام عيد الأضحى المبارك وشعائر الحج وذكر الله تعالى • بطاقة تهنئة العيد #27",
            category = "تهاني العيد",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_28",
            title = "أعياد المسلمين المجيدة #28",
            calligraphyText = "«هَنِيئًا لَكُمْ بِالْعِيدِ السَّعِيدِ، أَدَامَ اللَّهُ عَلَيْكُمُ الْبَهْجَةَ وَالأَفْرَاحَ»",
            subText = "تهنئة الأهل والأحبة والأصدقاء في مشارق الأرض ومغاربها • بطاقة تهنئة العيد #28",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_29",
            title = "قبول الطاعات وفرحة العيد #29",
            calligraphyText = "«اللَّهُمَّ أَدْخِلْ عَلَى كُلِّ بَيْتٍ إِسْلَامِيٍّ فَرْحَةَ الْعِيدِ وَالْأَمَانَ»",
            subText = "دعاء العيد بالسلام والاطمئنان وزوال الكرب عن المحتاجين • بطاقة تهنئة العيد #29",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_30",
            title = "عيدكم نور وسعادة #30",
            calligraphyText = "«عِيدٌ سَعِيدٌ مُبَارَكٌ، تَقَبَّلَ اللَّهُ طَاعَتَكُمْ وَأَتَمَّ بِالْخَيْرِ فَرْحَتَكُمْ»",
            subText = "صلة الأرحام وإفشاء السلام وإدخال السرور على القلوب • بطاقة تهنئة العيد #30",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF047857L, 0xFF022C22L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_31",
            title = "تهاني العيد العطرة #31",
            calligraphyText = "«تَقَبَّلَ اللَّهُ مِنَّا وَمِنْكُمُ الصَّالِحَاتِ، وَجَعَلَ أَيَّامَكُمْ كُلَّهَا أَعْيَادًا وَسُرُورًا»",
            subText = "تهنئة عيد الفطر والأضحى المبارك بصالح العمل والقبول • بطاقة تهنئة العيد #31",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF9D174DL, 0xFF4A044EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_32",
            title = "بشائر العيد والبركة #32",
            calligraphyText = "«عِيدُكُمْ مُبَارَكٌ، وَكُلُّ عَامٍ وَأَنْتُمْ إِلَى اللَّهِ أَقْرَبُ»",
            subText = "أعاده الله عليكم وعلى الأمة الإسلامية باليمن والخير والمسرات • بطاقة تهنئة العيد #32",
            category = "تهاني العيد",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_33",
            title = "عيد المحبة والسلام #33",
            calligraphyText = "«اللَّهُ أَكْبَرُ، اللَّهُ أَكْبَرُ، لَا إِلَهَ إِلَّا اللَّهُ، اللَّهُ أَكْبَرُ، اللَّهُ أَكْبَرُ، وَلِلَّهِ الْحَمْدُ»",
            subText = "تكبيرات العيد الشجية التي تصدح في الآفاق فرحاً بطاعة الله • بطاقة تهنئة العيد #33",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_34",
            title = "عيد الأمل والأفراح #34",
            calligraphyText = "«جَعَلَ اللَّهُ عِيدَكُمْ فَرَحًا بِأَعْمَالٍ قُبِلَتْ، وَذُنُوبٍ مُحِيَتْ، وَدَرَجَاتٍ رُفِعَتْ»",
            subText = "بشارة الفائزين في مواسم الطاعات ببهجة العيد والرضوان • بطاقة تهنئة العيد #34",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_35",
            title = "أفراح العيد في كل دار #35",
            calligraphyText = "﴿قُلْ بِفَضْلِ اللَّهِ وَبِرَحْمَتِهِ فَبِذَٰلِكَ فَلْيَفْرَحُوا هُوَ خَيْرٌ مِّمَّا يَجْمَعُونَ﴾",
            subText = "فرحة العيد شكر لله على التوفيق للصيام والقيام والحج • بطاقة تهنئة العيد #35",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_36",
            title = "مسرات العيد السعيد #36",
            calligraphyText = "«عَسَاكُمْ مِنْ عُوَّادِهِ، وَفَالُكُمْ الْخَيْرُ وَالسَّعَادَةُ فِي كُلِّ عَامٍ»",
            subText = "أجمل التبريكات بدوام الصحة والعافية ولم الشمل المبارك • بطاقة تهنئة العيد #36",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_37",
            title = "أيام مباركة وأعياد هانئة #37",
            calligraphyText = "«أَيَّامُ التَّشْرِيقِ أَيَّامُ أَكْلٍ وَشُرْبٍ وَذِكْرٍ لِلَّهِ عَزَّ وَجَلَّ»",
            subText = "أيام عيد الأضحى المبارك وشعائر الحج وذكر الله تعالى • بطاقة تهنئة العيد #37",
            category = "تهاني العيد",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_38",
            title = "باقات ورد وتهاني العيد #38",
            calligraphyText = "«هَنِيئًا لَكُمْ بِالْعِيدِ السَّعِيدِ، أَدَامَ اللَّهُ عَلَيْكُمُ الْبَهْجَةَ وَالأَفْرَاحَ»",
            subText = "تهنئة الأهل والأحبة والأصدقاء في مشارق الأرض ومغاربها • بطاقة تهنئة العيد #38",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_39",
            title = "بسمة العيد وفرحة الصغار #39",
            calligraphyText = "«اللَّهُمَّ أَدْخِلْ عَلَى كُلِّ بَيْتٍ إِسْلَامِيٍّ فَرْحَةَ الْعِيدِ وَالْأَمَانَ»",
            subText = "دعاء العيد بالسلام والاطمئنان وزوال الكرب عن المحتاجين • بطاقة تهنئة العيد #39",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_40",
            title = "عيدكم مبارك وعامر بالخير #40",
            calligraphyText = "«عِيدٌ سَعِيدٌ مُبَارَكٌ، تَقَبَّلَ اللَّهُ طَاعَتَكُمْ وَأَتَمَّ بِالْخَيْرِ فَرْحَتَكُمْ»",
            subText = "صلة الأرحام وإفشاء السلام وإدخال السرور على القلوب • بطاقة تهنئة العيد #40",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF047857L, 0xFF022C22L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_41",
            title = "عيد الفطر المبارك السعيد #41",
            calligraphyText = "«تَقَبَّلَ اللَّهُ مِنَّا وَمِنْكُمُ الصَّالِحَاتِ، وَجَعَلَ أَيَّامَكُمْ كُلَّهَا أَعْيَادًا وَسُرُورًا»",
            subText = "تهنئة عيد الفطر والأضحى المبارك بصالح العمل والقبول • بطاقة تهنئة العيد #41",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF9D174DL, 0xFF4A044EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_42",
            title = "عيد الأضحى وفرحة الحجيج #42",
            calligraphyText = "«عِيدُكُمْ مُبَارَكٌ، وَكُلُّ عَامٍ وَأَنْتُمْ إِلَى اللَّهِ أَقْرَبُ»",
            subText = "أعاده الله عليكم وعلى الأمة الإسلامية باليمن والخير والمسرات • بطاقة تهنئة العيد #42",
            category = "تهاني العيد",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_43",
            title = "تكبيرات العيد الصادحة #43",
            calligraphyText = "«اللَّهُ أَكْبَرُ، اللَّهُ أَكْبَرُ، لَا إِلَهَ إِلَّا اللَّهُ، اللَّهُ أَكْبَرُ، اللَّهُ أَكْبَرُ، وَلِلَّهِ الْحَمْدُ»",
            subText = "تكبيرات العيد الشجية التي تصدح في الآفاق فرحاً بطاعة الله • بطاقة تهنئة العيد #43",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_44",
            title = "فرحة العيد ولمة الأهل #44",
            calligraphyText = "«جَعَلَ اللَّهُ عِيدَكُمْ فَرَحًا بِأَعْمَالٍ قُبِلَتْ، وَذُنُوبٍ مُحِيَتْ، وَدَرَجَاتٍ رُفِعَتْ»",
            subText = "بشارة الفائزين في مواسم الطاعات ببهجة العيد والرضوان • بطاقة تهنئة العيد #44",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_45",
            title = "عساكم من عواده دائماً #45",
            calligraphyText = "﴿قُلْ بِفَضْلِ اللَّهِ وَبِرَحْمَتِهِ فَبِذَٰلِكَ فَلْيَفْرَحُوا هُوَ خَيْرٌ مِّمَّا يَجْمَعُونَ﴾",
            subText = "فرحة العيد شكر لله على التوفيق للصيام والقيام والحج • بطاقة تهنئة العيد #45",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_46",
            title = "تهنئة عيد مباركة للأحبة #46",
            calligraphyText = "«عَسَاكُمْ مِنْ عُوَّادِهِ، وَفَالُكُمْ الْخَيْرُ وَالسَّعَادَةُ فِي كُلِّ عَامٍ»",
            subText = "أجمل التبريكات بدوام الصحة والعافية ولم الشمل المبارك • بطاقة تهنئة العيد #46",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_47",
            title = "بهجة العيد والسرور #47",
            calligraphyText = "«أَيَّامُ التَّشْرِيقِ أَيَّامُ أَكْلٍ وَشُرْبٍ وَذِكْرٍ لِلَّهِ عَزَّ وَجَلَّ»",
            subText = "أيام عيد الأضحى المبارك وشعائر الحج وذكر الله تعالى • بطاقة تهنئة العيد #47",
            category = "تهاني العيد",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_48",
            title = "أعياد المسلمين المجيدة #48",
            calligraphyText = "«هَنِيئًا لَكُمْ بِالْعِيدِ السَّعِيدِ، أَدَامَ اللَّهُ عَلَيْكُمُ الْبَهْجَةَ وَالأَفْرَاحَ»",
            subText = "تهنئة الأهل والأحبة والأصدقاء في مشارق الأرض ومغاربها • بطاقة تهنئة العيد #48",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_49",
            title = "قبول الطاعات وفرحة العيد #49",
            calligraphyText = "«اللَّهُمَّ أَدْخِلْ عَلَى كُلِّ بَيْتٍ إِسْلَامِيٍّ فَرْحَةَ الْعِيدِ وَالْأَمَانَ»",
            subText = "دعاء العيد بالسلام والاطمئنان وزوال الكرب عن المحتاجين • بطاقة تهنئة العيد #49",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_50",
            title = "عيدكم نور وسعادة #50",
            calligraphyText = "«عِيدٌ سَعِيدٌ مُبَارَكٌ، تَقَبَّلَ اللَّهُ طَاعَتَكُمْ وَأَتَمَّ بِالْخَيْرِ فَرْحَتَكُمْ»",
            subText = "صلة الأرحام وإفشاء السلام وإدخال السرور على القلوب • بطاقة تهنئة العيد #50",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF047857L, 0xFF022C22L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_51",
            title = "تهاني العيد العطرة #51",
            calligraphyText = "«تَقَبَّلَ اللَّهُ مِنَّا وَمِنْكُمُ الصَّالِحَاتِ، وَجَعَلَ أَيَّامَكُمْ كُلَّهَا أَعْيَادًا وَسُرُورًا»",
            subText = "تهنئة عيد الفطر والأضحى المبارك بصالح العمل والقبول • بطاقة تهنئة العيد #51",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF9D174DL, 0xFF4A044EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_52",
            title = "بشائر العيد والبركة #52",
            calligraphyText = "«عِيدُكُمْ مُبَارَكٌ، وَكُلُّ عَامٍ وَأَنْتُمْ إِلَى اللَّهِ أَقْرَبُ»",
            subText = "أعاده الله عليكم وعلى الأمة الإسلامية باليمن والخير والمسرات • بطاقة تهنئة العيد #52",
            category = "تهاني العيد",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_53",
            title = "عيد المحبة والسلام #53",
            calligraphyText = "«اللَّهُ أَكْبَرُ، اللَّهُ أَكْبَرُ، لَا إِلَهَ إِلَّا اللَّهُ، اللَّهُ أَكْبَرُ، اللَّهُ أَكْبَرُ، وَلِلَّهِ الْحَمْدُ»",
            subText = "تكبيرات العيد الشجية التي تصدح في الآفاق فرحاً بطاعة الله • بطاقة تهنئة العيد #53",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_54",
            title = "عيد الأمل والأفراح #54",
            calligraphyText = "«جَعَلَ اللَّهُ عِيدَكُمْ فَرَحًا بِأَعْمَالٍ قُبِلَتْ، وَذُنُوبٍ مُحِيَتْ، وَدَرَجَاتٍ رُفِعَتْ»",
            subText = "بشارة الفائزين في مواسم الطاعات ببهجة العيد والرضوان • بطاقة تهنئة العيد #54",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_55",
            title = "أفراح العيد في كل دار #55",
            calligraphyText = "﴿قُلْ بِفَضْلِ اللَّهِ وَبِرَحْمَتِهِ فَبِذَٰلِكَ فَلْيَفْرَحُوا هُوَ خَيْرٌ مِّمَّا يَجْمَعُونَ﴾",
            subText = "فرحة العيد شكر لله على التوفيق للصيام والقيام والحج • بطاقة تهنئة العيد #55",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_56",
            title = "مسرات العيد السعيد #56",
            calligraphyText = "«عَسَاكُمْ مِنْ عُوَّادِهِ، وَفَالُكُمْ الْخَيْرُ وَالسَّعَادَةُ فِي كُلِّ عَامٍ»",
            subText = "أجمل التبريكات بدوام الصحة والعافية ولم الشمل المبارك • بطاقة تهنئة العيد #56",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_57",
            title = "أيام مباركة وأعياد هانئة #57",
            calligraphyText = "«أَيَّامُ التَّشْرِيقِ أَيَّامُ أَكْلٍ وَشُرْبٍ وَذِكْرٍ لِلَّهِ عَزَّ وَجَلَّ»",
            subText = "أيام عيد الأضحى المبارك وشعائر الحج وذكر الله تعالى • بطاقة تهنئة العيد #57",
            category = "تهاني العيد",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_58",
            title = "باقات ورد وتهاني العيد #58",
            calligraphyText = "«هَنِيئًا لَكُمْ بِالْعِيدِ السَّعِيدِ، أَدَامَ اللَّهُ عَلَيْكُمُ الْبَهْجَةَ وَالأَفْرَاحَ»",
            subText = "تهنئة الأهل والأحبة والأصدقاء في مشارق الأرض ومغاربها • بطاقة تهنئة العيد #58",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_59",
            title = "بسمة العيد وفرحة الصغار #59",
            calligraphyText = "«اللَّهُمَّ أَدْخِلْ عَلَى كُلِّ بَيْتٍ إِسْلَامِيٍّ فَرْحَةَ الْعِيدِ وَالْأَمَانَ»",
            subText = "دعاء العيد بالسلام والاطمئنان وزوال الكرب عن المحتاجين • بطاقة تهنئة العيد #59",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_60",
            title = "عيدكم مبارك وعامر بالخير #60",
            calligraphyText = "«عِيدٌ سَعِيدٌ مُبَارَكٌ، تَقَبَّلَ اللَّهُ طَاعَتَكُمْ وَأَتَمَّ بِالْخَيْرِ فَرْحَتَكُمْ»",
            subText = "صلة الأرحام وإفشاء السلام وإدخال السرور على القلوب • بطاقة تهنئة العيد #60",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF047857L, 0xFF022C22L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_61",
            title = "عيد الفطر المبارك السعيد #61",
            calligraphyText = "«تَقَبَّلَ اللَّهُ مِنَّا وَمِنْكُمُ الصَّالِحَاتِ، وَجَعَلَ أَيَّامَكُمْ كُلَّهَا أَعْيَادًا وَسُرُورًا»",
            subText = "تهنئة عيد الفطر والأضحى المبارك بصالح العمل والقبول • بطاقة تهنئة العيد #61",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF9D174DL, 0xFF4A044EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_62",
            title = "عيد الأضحى وفرحة الحجيج #62",
            calligraphyText = "«عِيدُكُمْ مُبَارَكٌ، وَكُلُّ عَامٍ وَأَنْتُمْ إِلَى اللَّهِ أَقْرَبُ»",
            subText = "أعاده الله عليكم وعلى الأمة الإسلامية باليمن والخير والمسرات • بطاقة تهنئة العيد #62",
            category = "تهاني العيد",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_63",
            title = "تكبيرات العيد الصادحة #63",
            calligraphyText = "«اللَّهُ أَكْبَرُ، اللَّهُ أَكْبَرُ، لَا إِلَهَ إِلَّا اللَّهُ، اللَّهُ أَكْبَرُ، اللَّهُ أَكْبَرُ، وَلِلَّهِ الْحَمْدُ»",
            subText = "تكبيرات العيد الشجية التي تصدح في الآفاق فرحاً بطاعة الله • بطاقة تهنئة العيد #63",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_64",
            title = "فرحة العيد ولمة الأهل #64",
            calligraphyText = "«جَعَلَ اللَّهُ عِيدَكُمْ فَرَحًا بِأَعْمَالٍ قُبِلَتْ، وَذُنُوبٍ مُحِيَتْ، وَدَرَجَاتٍ رُفِعَتْ»",
            subText = "بشارة الفائزين في مواسم الطاعات ببهجة العيد والرضوان • بطاقة تهنئة العيد #64",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_65",
            title = "عساكم من عواده دائماً #65",
            calligraphyText = "﴿قُلْ بِفَضْلِ اللَّهِ وَبِرَحْمَتِهِ فَبِذَٰلِكَ فَلْيَفْرَحُوا هُوَ خَيْرٌ مِّمَّا يَجْمَعُونَ﴾",
            subText = "فرحة العيد شكر لله على التوفيق للصيام والقيام والحج • بطاقة تهنئة العيد #65",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_66",
            title = "تهنئة عيد مباركة للأحبة #66",
            calligraphyText = "«عَسَاكُمْ مِنْ عُوَّادِهِ، وَفَالُكُمْ الْخَيْرُ وَالسَّعَادَةُ فِي كُلِّ عَامٍ»",
            subText = "أجمل التبريكات بدوام الصحة والعافية ولم الشمل المبارك • بطاقة تهنئة العيد #66",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_67",
            title = "بهجة العيد والسرور #67",
            calligraphyText = "«أَيَّامُ التَّشْرِيقِ أَيَّامُ أَكْلٍ وَشُرْبٍ وَذِكْرٍ لِلَّهِ عَزَّ وَجَلَّ»",
            subText = "أيام عيد الأضحى المبارك وشعائر الحج وذكر الله تعالى • بطاقة تهنئة العيد #67",
            category = "تهاني العيد",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_68",
            title = "أعياد المسلمين المجيدة #68",
            calligraphyText = "«هَنِيئًا لَكُمْ بِالْعِيدِ السَّعِيدِ، أَدَامَ اللَّهُ عَلَيْكُمُ الْبَهْجَةَ وَالأَفْرَاحَ»",
            subText = "تهنئة الأهل والأحبة والأصدقاء في مشارق الأرض ومغاربها • بطاقة تهنئة العيد #68",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_69",
            title = "قبول الطاعات وفرحة العيد #69",
            calligraphyText = "«اللَّهُمَّ أَدْخِلْ عَلَى كُلِّ بَيْتٍ إِسْلَامِيٍّ فَرْحَةَ الْعِيدِ وَالْأَمَانَ»",
            subText = "دعاء العيد بالسلام والاطمئنان وزوال الكرب عن المحتاجين • بطاقة تهنئة العيد #69",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_70",
            title = "عيدكم نور وسعادة #70",
            calligraphyText = "«عِيدٌ سَعِيدٌ مُبَارَكٌ، تَقَبَّلَ اللَّهُ طَاعَتَكُمْ وَأَتَمَّ بِالْخَيْرِ فَرْحَتَكُمْ»",
            subText = "صلة الأرحام وإفشاء السلام وإدخال السرور على القلوب • بطاقة تهنئة العيد #70",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF047857L, 0xFF022C22L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_71",
            title = "تهاني العيد العطرة #71",
            calligraphyText = "«تَقَبَّلَ اللَّهُ مِنَّا وَمِنْكُمُ الصَّالِحَاتِ، وَجَعَلَ أَيَّامَكُمْ كُلَّهَا أَعْيَادًا وَسُرُورًا»",
            subText = "تهنئة عيد الفطر والأضحى المبارك بصالح العمل والقبول • بطاقة تهنئة العيد #71",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF9D174DL, 0xFF4A044EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_72",
            title = "بشائر العيد والبركة #72",
            calligraphyText = "«عِيدُكُمْ مُبَارَكٌ، وَكُلُّ عَامٍ وَأَنْتُمْ إِلَى اللَّهِ أَقْرَبُ»",
            subText = "أعاده الله عليكم وعلى الأمة الإسلامية باليمن والخير والمسرات • بطاقة تهنئة العيد #72",
            category = "تهاني العيد",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_73",
            title = "عيد المحبة والسلام #73",
            calligraphyText = "«اللَّهُ أَكْبَرُ، اللَّهُ أَكْبَرُ، لَا إِلَهَ إِلَّا اللَّهُ، اللَّهُ أَكْبَرُ، اللَّهُ أَكْبَرُ، وَلِلَّهِ الْحَمْدُ»",
            subText = "تكبيرات العيد الشجية التي تصدح في الآفاق فرحاً بطاعة الله • بطاقة تهنئة العيد #73",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_74",
            title = "عيد الأمل والأفراح #74",
            calligraphyText = "«جَعَلَ اللَّهُ عِيدَكُمْ فَرَحًا بِأَعْمَالٍ قُبِلَتْ، وَذُنُوبٍ مُحِيَتْ، وَدَرَجَاتٍ رُفِعَتْ»",
            subText = "بشارة الفائزين في مواسم الطاعات ببهجة العيد والرضوان • بطاقة تهنئة العيد #74",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_75",
            title = "أفراح العيد في كل دار #75",
            calligraphyText = "﴿قُلْ بِفَضْلِ اللَّهِ وَبِرَحْمَتِهِ فَبِذَٰلِكَ فَلْيَفْرَحُوا هُوَ خَيْرٌ مِّمَّا يَجْمَعُونَ﴾",
            subText = "فرحة العيد شكر لله على التوفيق للصيام والقيام والحج • بطاقة تهنئة العيد #75",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_76",
            title = "مسرات العيد السعيد #76",
            calligraphyText = "«عَسَاكُمْ مِنْ عُوَّادِهِ، وَفَالُكُمْ الْخَيْرُ وَالسَّعَادَةُ فِي كُلِّ عَامٍ»",
            subText = "أجمل التبريكات بدوام الصحة والعافية ولم الشمل المبارك • بطاقة تهنئة العيد #76",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_77",
            title = "أيام مباركة وأعياد هانئة #77",
            calligraphyText = "«أَيَّامُ التَّشْرِيقِ أَيَّامُ أَكْلٍ وَشُرْبٍ وَذِكْرٍ لِلَّهِ عَزَّ وَجَلَّ»",
            subText = "أيام عيد الأضحى المبارك وشعائر الحج وذكر الله تعالى • بطاقة تهنئة العيد #77",
            category = "تهاني العيد",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_78",
            title = "باقات ورد وتهاني العيد #78",
            calligraphyText = "«هَنِيئًا لَكُمْ بِالْعِيدِ السَّعِيدِ، أَدَامَ اللَّهُ عَلَيْكُمُ الْبَهْجَةَ وَالأَفْرَاحَ»",
            subText = "تهنئة الأهل والأحبة والأصدقاء في مشارق الأرض ومغاربها • بطاقة تهنئة العيد #78",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_79",
            title = "بسمة العيد وفرحة الصغار #79",
            calligraphyText = "«اللَّهُمَّ أَدْخِلْ عَلَى كُلِّ بَيْتٍ إِسْلَامِيٍّ فَرْحَةَ الْعِيدِ وَالْأَمَانَ»",
            subText = "دعاء العيد بالسلام والاطمئنان وزوال الكرب عن المحتاجين • بطاقة تهنئة العيد #79",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_80",
            title = "عيدكم مبارك وعامر بالخير #80",
            calligraphyText = "«عِيدٌ سَعِيدٌ مُبَارَكٌ، تَقَبَّلَ اللَّهُ طَاعَتَكُمْ وَأَتَمَّ بِالْخَيْرِ فَرْحَتَكُمْ»",
            subText = "صلة الأرحام وإفشاء السلام وإدخال السرور على القلوب • بطاقة تهنئة العيد #80",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF047857L, 0xFF022C22L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_81",
            title = "عيد الفطر المبارك السعيد #81",
            calligraphyText = "«تَقَبَّلَ اللَّهُ مِنَّا وَمِنْكُمُ الصَّالِحَاتِ، وَجَعَلَ أَيَّامَكُمْ كُلَّهَا أَعْيَادًا وَسُرُورًا»",
            subText = "تهنئة عيد الفطر والأضحى المبارك بصالح العمل والقبول • بطاقة تهنئة العيد #81",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF9D174DL, 0xFF4A044EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_82",
            title = "عيد الأضحى وفرحة الحجيج #82",
            calligraphyText = "«عِيدُكُمْ مُبَارَكٌ، وَكُلُّ عَامٍ وَأَنْتُمْ إِلَى اللَّهِ أَقْرَبُ»",
            subText = "أعاده الله عليكم وعلى الأمة الإسلامية باليمن والخير والمسرات • بطاقة تهنئة العيد #82",
            category = "تهاني العيد",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_83",
            title = "تكبيرات العيد الصادحة #83",
            calligraphyText = "«اللَّهُ أَكْبَرُ، اللَّهُ أَكْبَرُ، لَا إِلَهَ إِلَّا اللَّهُ، اللَّهُ أَكْبَرُ، اللَّهُ أَكْبَرُ، وَلِلَّهِ الْحَمْدُ»",
            subText = "تكبيرات العيد الشجية التي تصدح في الآفاق فرحاً بطاعة الله • بطاقة تهنئة العيد #83",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_84",
            title = "فرحة العيد ولمة الأهل #84",
            calligraphyText = "«جَعَلَ اللَّهُ عِيدَكُمْ فَرَحًا بِأَعْمَالٍ قُبِلَتْ، وَذُنُوبٍ مُحِيَتْ، وَدَرَجَاتٍ رُفِعَتْ»",
            subText = "بشارة الفائزين في مواسم الطاعات ببهجة العيد والرضوان • بطاقة تهنئة العيد #84",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_85",
            title = "عساكم من عواده دائماً #85",
            calligraphyText = "﴿قُلْ بِفَضْلِ اللَّهِ وَبِرَحْمَتِهِ فَبِذَٰلِكَ فَلْيَفْرَحُوا هُوَ خَيْرٌ مِّمَّا يَجْمَعُونَ﴾",
            subText = "فرحة العيد شكر لله على التوفيق للصيام والقيام والحج • بطاقة تهنئة العيد #85",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_86",
            title = "تهنئة عيد مباركة للأحبة #86",
            calligraphyText = "«عَسَاكُمْ مِنْ عُوَّادِهِ، وَفَالُكُمْ الْخَيْرُ وَالسَّعَادَةُ فِي كُلِّ عَامٍ»",
            subText = "أجمل التبريكات بدوام الصحة والعافية ولم الشمل المبارك • بطاقة تهنئة العيد #86",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_87",
            title = "بهجة العيد والسرور #87",
            calligraphyText = "«أَيَّامُ التَّشْرِيقِ أَيَّامُ أَكْلٍ وَشُرْبٍ وَذِكْرٍ لِلَّهِ عَزَّ وَجَلَّ»",
            subText = "أيام عيد الأضحى المبارك وشعائر الحج وذكر الله تعالى • بطاقة تهنئة العيد #87",
            category = "تهاني العيد",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_88",
            title = "أعياد المسلمين المجيدة #88",
            calligraphyText = "«هَنِيئًا لَكُمْ بِالْعِيدِ السَّعِيدِ، أَدَامَ اللَّهُ عَلَيْكُمُ الْبَهْجَةَ وَالأَفْرَاحَ»",
            subText = "تهنئة الأهل والأحبة والأصدقاء في مشارق الأرض ومغاربها • بطاقة تهنئة العيد #88",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_89",
            title = "قبول الطاعات وفرحة العيد #89",
            calligraphyText = "«اللَّهُمَّ أَدْخِلْ عَلَى كُلِّ بَيْتٍ إِسْلَامِيٍّ فَرْحَةَ الْعِيدِ وَالْأَمَانَ»",
            subText = "دعاء العيد بالسلام والاطمئنان وزوال الكرب عن المحتاجين • بطاقة تهنئة العيد #89",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_90",
            title = "عيدكم نور وسعادة #90",
            calligraphyText = "«عِيدٌ سَعِيدٌ مُبَارَكٌ، تَقَبَّلَ اللَّهُ طَاعَتَكُمْ وَأَتَمَّ بِالْخَيْرِ فَرْحَتَكُمْ»",
            subText = "صلة الأرحام وإفشاء السلام وإدخال السرور على القلوب • بطاقة تهنئة العيد #90",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF047857L, 0xFF022C22L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_91",
            title = "تهاني العيد العطرة #91",
            calligraphyText = "«تَقَبَّلَ اللَّهُ مِنَّا وَمِنْكُمُ الصَّالِحَاتِ، وَجَعَلَ أَيَّامَكُمْ كُلَّهَا أَعْيَادًا وَسُرُورًا»",
            subText = "تهنئة عيد الفطر والأضحى المبارك بصالح العمل والقبول • بطاقة تهنئة العيد #91",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF9D174DL, 0xFF4A044EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_92",
            title = "بشائر العيد والبركة #92",
            calligraphyText = "«عِيدُكُمْ مُبَارَكٌ، وَكُلُّ عَامٍ وَأَنْتُمْ إِلَى اللَّهِ أَقْرَبُ»",
            subText = "أعاده الله عليكم وعلى الأمة الإسلامية باليمن والخير والمسرات • بطاقة تهنئة العيد #92",
            category = "تهاني العيد",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_93",
            title = "عيد المحبة والسلام #93",
            calligraphyText = "«اللَّهُ أَكْبَرُ، اللَّهُ أَكْبَرُ، لَا إِلَهَ إِلَّا اللَّهُ، اللَّهُ أَكْبَرُ، اللَّهُ أَكْبَرُ، وَلِلَّهِ الْحَمْدُ»",
            subText = "تكبيرات العيد الشجية التي تصدح في الآفاق فرحاً بطاعة الله • بطاقة تهنئة العيد #93",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_94",
            title = "عيد الأمل والأفراح #94",
            calligraphyText = "«جَعَلَ اللَّهُ عِيدَكُمْ فَرَحًا بِأَعْمَالٍ قُبِلَتْ، وَذُنُوبٍ مُحِيَتْ، وَدَرَجَاتٍ رُفِعَتْ»",
            subText = "بشارة الفائزين في مواسم الطاعات ببهجة العيد والرضوان • بطاقة تهنئة العيد #94",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_95",
            title = "أفراح العيد في كل دار #95",
            calligraphyText = "﴿قُلْ بِفَضْلِ اللَّهِ وَبِرَحْمَتِهِ فَبِذَٰلِكَ فَلْيَفْرَحُوا هُوَ خَيْرٌ مِّمَّا يَجْمَعُونَ﴾",
            subText = "فرحة العيد شكر لله على التوفيق للصيام والقيام والحج • بطاقة تهنئة العيد #95",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_96",
            title = "مسرات العيد السعيد #96",
            calligraphyText = "«عَسَاكُمْ مِنْ عُوَّادِهِ، وَفَالُكُمْ الْخَيْرُ وَالسَّعَادَةُ فِي كُلِّ عَامٍ»",
            subText = "أجمل التبريكات بدوام الصحة والعافية ولم الشمل المبارك • بطاقة تهنئة العيد #96",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_97",
            title = "أيام مباركة وأعياد هانئة #97",
            calligraphyText = "«أَيَّامُ التَّشْرِيقِ أَيَّامُ أَكْلٍ وَشُرْبٍ وَذِكْرٍ لِلَّهِ عَزَّ وَجَلَّ»",
            subText = "أيام عيد الأضحى المبارك وشعائر الحج وذكر الله تعالى • بطاقة تهنئة العيد #97",
            category = "تهاني العيد",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_98",
            title = "باقات ورد وتهاني العيد #98",
            calligraphyText = "«هَنِيئًا لَكُمْ بِالْعِيدِ السَّعِيدِ، أَدَامَ اللَّهُ عَلَيْكُمُ الْبَهْجَةَ وَالأَفْرَاحَ»",
            subText = "تهنئة الأهل والأحبة والأصدقاء في مشارق الأرض ومغاربها • بطاقة تهنئة العيد #98",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_99",
            title = "بسمة العيد وفرحة الصغار #99",
            calligraphyText = "«اللَّهُمَّ أَدْخِلْ عَلَى كُلِّ بَيْتٍ إِسْلَامِيٍّ فَرْحَةَ الْعِيدِ وَالْأَمَانَ»",
            subText = "دعاء العيد بالسلام والاطمئنان وزوال الكرب عن المحتاجين • بطاقة تهنئة العيد #99",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_eid_100",
            title = "عيدكم مبارك وعامر بالخير #100",
            calligraphyText = "«عِيدٌ سَعِيدٌ مُبَارَكٌ، تَقَبَّلَ اللَّهُ طَاعَتَكُمْ وَأَتَمَّ بِالْخَيْرِ فَرْحَتَكُمْ»",
            subText = "صلة الأرحام وإفشاء السلام وإدخال السرور على القلوب • بطاقة تهنئة العيد #100",
            category = "تهاني العيد",
            gradientColors = listOf(0xFF047857L, 0xFF022C22L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_1",
            title = "آية الكرسي وحفظ الرحمن #1",
            calligraphyText = "﴿اللَّهُ لَا إِلَٰهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ ۚ لَا تَأْخُذُهُ سِنَةٌ وَلَا نَوْمٌ﴾",
            subText = "آية الكرسي سيدة آي القرآن حرز وأمان وحفظ من كل سوء • بطاقة أدعية قرآنية #1",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_2",
            title = "دعاء الفرج وتفريج الكروب #2",
            calligraphyText = "﴿رَبَّنَا آتِنَا فِي الدُّنْيَا حَسَنَةً وَفِي الْآخِرَةِ حَسَنَةً وَقِنَا عَذَابَ النَّارِ﴾",
            subText = "أجمع دعاء للخير كله في المعاش والمعاد وسنة النبي ﷺ • بطاقة أدعية قرآنية #2",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_3",
            title = "سيد الاستغفار والتوبة #3",
            calligraphyText = "﴿لَا إِلَٰهَ إِلَّا أَنتَ سُبْحَانَكَ إِنِّي كُنتُ مِنَ الظَّالِمِينَ﴾",
            subText = "دعاء ذي النون في بطن الحوت ما دعا به مكروب إلا فرج الله عنه • بطاقة أدعية قرآنية #3",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_4",
            title = "دعاء تيسير الأمور وشرح الصدر #4",
            calligraphyText = "﴿حَسْبُنَا اللَّهُ وَنِعْمَ الْوَكِيلُ﴾",
            subText = "قالها إبراهيم حين أُلقي في النار وقالها محمد حين قالوا إن الناس قد جمعوا لكم • بطاقة أدعية قرآنية #4",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_5",
            title = "حسبنا الله ونعم الوكيل #5",
            calligraphyText = "﴿وَمَن يَتَوَكَّلْ عَلَى اللَّهِ فَهُوَ حَسْبُهُ ۚ إِنَّ اللَّهَ بَالِغُ أَمْرِهِ﴾",
            subText = "كفاية المتوكلين وتفويض الأمر إلى الرؤوف الرحيم • بطاقة أدعية قرآنية #5",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_6",
            title = "كنز الحوقلة والاستعانة #6",
            calligraphyText = "﴿لَا تَدْرِي لَعَلَّ اللَّهَ يُحْدِثُ بَعْدَ ذَٰلِكَ أَمْرًا﴾",
            subText = "حسن الظن بالله وانتظار الفرج القريب بعد الشدة والضيق • بطاقة أدعية قرآنية #6",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_7",
            title = "دعاء الرزق والبركة في المال #7",
            calligraphyText = "«اللَّهُمَّ أَنْتَ رَبِّي لَا إِلَهَ إِلَّا أَنْتَ خَلَقْتَنِي وَأَنَا عَبْدُكَ»",
            subText = "سيد الاستغفار من قاله موقناً به ومات دخل الجنة • بطاقة أدعية قرآنية #7",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_8",
            title = "دعاء الشفاء ورفع البأس #8",
            calligraphyText = "«يَا حَيُّ يَا قَيُّومُ بِرَحْمَتِكَ أَسْتَغِيثُ أَصْلِحْ لِي شَأْنِي كُلَّهُ»",
            subText = "دعاء الكرب وتفويض التدبير إلى قيوم السماوات والأرض • بطاقة أدعية قرآنية #8",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF9D174DL, 0xFF4A044EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_9",
            title = "دعاء الهداية والثبات على الدين #9",
            calligraphyText = "﴿رَبِّ اشْرَحْ لِي صَدْرِي ۝ وَيَسِّرْ لِي أَمْرِي﴾",
            subText = "دعاء موسى عليه السلام لطلب انشراح الصدر وتيسير العسير • بطاقة أدعية قرآنية #9",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_10",
            title = "دعاء الوالدين وبرهما #10",
            calligraphyText = "«لَا حَوْلَ وَلَا قُوَّةَ إِلَّا بِاللَّهِ الْعَلِيِّ الْعَظِيمِ»",
            subText = "كنز من كنوز الجنة ودواء لتسعة وتسعين داء أيسرها الهم • بطاقة أدعية قرآنية #10",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF047857L, 0xFF022C22L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_11",
            title = "دعاء تفريج الهم والحزن #11",
            calligraphyText = "﴿اللَّهُ لَا إِلَٰهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ ۚ لَا تَأْخُذُهُ سِنَةٌ وَلَا نَوْمٌ﴾",
            subText = "آية الكرسي سيدة آي القرآن حرز وأمان وحفظ من كل سوء • بطاقة أدعية قرآنية #11",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_12",
            title = "دعاء السكينة وطمأنينة القلب #12",
            calligraphyText = "﴿رَبَّنَا آتِنَا فِي الدُّنْيَا حَسَنَةً وَفِي الْآخِرَةِ حَسَنَةً وَقِنَا عَذَابَ النَّارِ﴾",
            subText = "أجمع دعاء للخير كله في المعاش والمعاد وسنة النبي ﷺ • بطاقة أدعية قرآنية #12",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_13",
            title = "دعاء طلب العلم النافع #13",
            calligraphyText = "﴿لَا إِلَٰهَ إِلَّا أَنتَ سُبْحَانَكَ إِنِّي كُنتُ مِنَ الظَّالِمِينَ﴾",
            subText = "دعاء ذي النون في بطن الحوت ما دعا به مكروب إلا فرج الله عنه • بطاقة أدعية قرآنية #13",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_14",
            title = "دعاء التوكل على الحي الذي لا يموت #14",
            calligraphyText = "﴿حَسْبُنَا اللَّهُ وَنِعْمَ الْوَكِيلُ﴾",
            subText = "قالها إبراهيم حين أُلقي في النار وقالها محمد حين قالوا إن الناس قد جمعوا لكم • بطاقة أدعية قرآنية #14",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_15",
            title = "دعاء النجاة من الفتن #15",
            calligraphyText = "﴿وَمَن يَتَوَكَّلْ عَلَى اللَّهِ فَهُوَ حَسْبُهُ ۚ إِنَّ اللَّهَ بَالِغُ أَمْرِهِ﴾",
            subText = "كفاية المتوكلين وتفويض الأمر إلى الرؤوف الرحيم • بطاقة أدعية قرآنية #15",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_16",
            title = "دعاء الصباح واستفتاح الرزق #16",
            calligraphyText = "﴿لَا تَدْرِي لَعَلَّ اللَّهَ يُحْدِثُ بَعْدَ ذَٰلِكَ أَمْرًا﴾",
            subText = "حسن الظن بالله وانتظار الفرج القريب بعد الشدة والضيق • بطاقة أدعية قرآنية #16",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_17",
            title = "دعاء المساء والحصن الحصين #17",
            calligraphyText = "«اللَّهُمَّ أَنْتَ رَبِّي لَا إِلَهَ إِلَّا أَنْتَ خَلَقْتَنِي وَأَنَا عَبْدُكَ»",
            subText = "سيد الاستغفار من قاله موقناً به ومات دخل الجنة • بطاقة أدعية قرآنية #17",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_18",
            title = "دعاء الاستخارة وتفويض الأمر #18",
            calligraphyText = "«يَا حَيُّ يَا قَيُّومُ بِرَحْمَتِكَ أَسْتَغِيثُ أَصْلِحْ لِي شَأْنِي كُلَّهُ»",
            subText = "دعاء الكرب وتفويض التدبير إلى قيوم السماوات والأرض • بطاقة أدعية قرآنية #18",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF9D174DL, 0xFF4A044EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_19",
            title = "دعاء ختم القرآن المبارك #19",
            calligraphyText = "﴿رَبِّ اشْرَحْ لِي صَدْرِي ۝ وَيَسِّرْ لِي أَمْرِي﴾",
            subText = "دعاء موسى عليه السلام لطلب انشراح الصدر وتيسير العسير • بطاقة أدعية قرآنية #19",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_20",
            title = "دعاء النصر والتمكين للحق #20",
            calligraphyText = "«لَا حَوْلَ وَلَا قُوَّةَ إِلَّا بِاللَّهِ الْعَلِيِّ الْعَظِيمِ»",
            subText = "كنز من كنوز الجنة ودواء لتسعة وتسعين داء أيسرها الهم • بطاقة أدعية قرآنية #20",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF047857L, 0xFF022C22L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_21",
            title = "آية الكرسي وحفظ الرحمن #21",
            calligraphyText = "﴿اللَّهُ لَا إِلَٰهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ ۚ لَا تَأْخُذُهُ سِنَةٌ وَلَا نَوْمٌ﴾",
            subText = "آية الكرسي سيدة آي القرآن حرز وأمان وحفظ من كل سوء • بطاقة أدعية قرآنية #21",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_22",
            title = "دعاء الفرج وتفريج الكروب #22",
            calligraphyText = "﴿رَبَّنَا آتِنَا فِي الدُّنْيَا حَسَنَةً وَفِي الْآخِرَةِ حَسَنَةً وَقِنَا عَذَابَ النَّارِ﴾",
            subText = "أجمع دعاء للخير كله في المعاش والمعاد وسنة النبي ﷺ • بطاقة أدعية قرآنية #22",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_23",
            title = "سيد الاستغفار والتوبة #23",
            calligraphyText = "﴿لَا إِلَٰهَ إِلَّا أَنتَ سُبْحَانَكَ إِنِّي كُنتُ مِنَ الظَّالِمِينَ﴾",
            subText = "دعاء ذي النون في بطن الحوت ما دعا به مكروب إلا فرج الله عنه • بطاقة أدعية قرآنية #23",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_24",
            title = "دعاء تيسير الأمور وشرح الصدر #24",
            calligraphyText = "﴿حَسْبُنَا اللَّهُ وَنِعْمَ الْوَكِيلُ﴾",
            subText = "قالها إبراهيم حين أُلقي في النار وقالها محمد حين قالوا إن الناس قد جمعوا لكم • بطاقة أدعية قرآنية #24",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_25",
            title = "حسبنا الله ونعم الوكيل #25",
            calligraphyText = "﴿وَمَن يَتَوَكَّلْ عَلَى اللَّهِ فَهُوَ حَسْبُهُ ۚ إِنَّ اللَّهَ بَالِغُ أَمْرِهِ﴾",
            subText = "كفاية المتوكلين وتفويض الأمر إلى الرؤوف الرحيم • بطاقة أدعية قرآنية #25",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_26",
            title = "كنز الحوقلة والاستعانة #26",
            calligraphyText = "﴿لَا تَدْرِي لَعَلَّ اللَّهَ يُحْدِثُ بَعْدَ ذَٰلِكَ أَمْرًا﴾",
            subText = "حسن الظن بالله وانتظار الفرج القريب بعد الشدة والضيق • بطاقة أدعية قرآنية #26",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_27",
            title = "دعاء الرزق والبركة في المال #27",
            calligraphyText = "«اللَّهُمَّ أَنْتَ رَبِّي لَا إِلَهَ إِلَّا أَنْتَ خَلَقْتَنِي وَأَنَا عَبْدُكَ»",
            subText = "سيد الاستغفار من قاله موقناً به ومات دخل الجنة • بطاقة أدعية قرآنية #27",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_28",
            title = "دعاء الشفاء ورفع البأس #28",
            calligraphyText = "«يَا حَيُّ يَا قَيُّومُ بِرَحْمَتِكَ أَسْتَغِيثُ أَصْلِحْ لِي شَأْنِي كُلَّهُ»",
            subText = "دعاء الكرب وتفويض التدبير إلى قيوم السماوات والأرض • بطاقة أدعية قرآنية #28",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF9D174DL, 0xFF4A044EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_29",
            title = "دعاء الهداية والثبات على الدين #29",
            calligraphyText = "﴿رَبِّ اشْرَحْ لِي صَدْرِي ۝ وَيَسِّرْ لِي أَمْرِي﴾",
            subText = "دعاء موسى عليه السلام لطلب انشراح الصدر وتيسير العسير • بطاقة أدعية قرآنية #29",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_30",
            title = "دعاء الوالدين وبرهما #30",
            calligraphyText = "«لَا حَوْلَ وَلَا قُوَّةَ إِلَّا بِاللَّهِ الْعَلِيِّ الْعَظِيمِ»",
            subText = "كنز من كنوز الجنة ودواء لتسعة وتسعين داء أيسرها الهم • بطاقة أدعية قرآنية #30",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF047857L, 0xFF022C22L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_31",
            title = "دعاء تفريج الهم والحزن #31",
            calligraphyText = "﴿اللَّهُ لَا إِلَٰهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ ۚ لَا تَأْخُذُهُ سِنَةٌ وَلَا نَوْمٌ﴾",
            subText = "آية الكرسي سيدة آي القرآن حرز وأمان وحفظ من كل سوء • بطاقة أدعية قرآنية #31",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_32",
            title = "دعاء السكينة وطمأنينة القلب #32",
            calligraphyText = "﴿رَبَّنَا آتِنَا فِي الدُّنْيَا حَسَنَةً وَفِي الْآخِرَةِ حَسَنَةً وَقِنَا عَذَابَ النَّارِ﴾",
            subText = "أجمع دعاء للخير كله في المعاش والمعاد وسنة النبي ﷺ • بطاقة أدعية قرآنية #32",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_33",
            title = "دعاء طلب العلم النافع #33",
            calligraphyText = "﴿لَا إِلَٰهَ إِلَّا أَنتَ سُبْحَانَكَ إِنِّي كُنتُ مِنَ الظَّالِمِينَ﴾",
            subText = "دعاء ذي النون في بطن الحوت ما دعا به مكروب إلا فرج الله عنه • بطاقة أدعية قرآنية #33",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_34",
            title = "دعاء التوكل على الحي الذي لا يموت #34",
            calligraphyText = "﴿حَسْبُنَا اللَّهُ وَنِعْمَ الْوَكِيلُ﴾",
            subText = "قالها إبراهيم حين أُلقي في النار وقالها محمد حين قالوا إن الناس قد جمعوا لكم • بطاقة أدعية قرآنية #34",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_35",
            title = "دعاء النجاة من الفتن #35",
            calligraphyText = "﴿وَمَن يَتَوَكَّلْ عَلَى اللَّهِ فَهُوَ حَسْبُهُ ۚ إِنَّ اللَّهَ بَالِغُ أَمْرِهِ﴾",
            subText = "كفاية المتوكلين وتفويض الأمر إلى الرؤوف الرحيم • بطاقة أدعية قرآنية #35",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_36",
            title = "دعاء الصباح واستفتاح الرزق #36",
            calligraphyText = "﴿لَا تَدْرِي لَعَلَّ اللَّهَ يُحْدِثُ بَعْدَ ذَٰلِكَ أَمْرًا﴾",
            subText = "حسن الظن بالله وانتظار الفرج القريب بعد الشدة والضيق • بطاقة أدعية قرآنية #36",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_37",
            title = "دعاء المساء والحصن الحصين #37",
            calligraphyText = "«اللَّهُمَّ أَنْتَ رَبِّي لَا إِلَهَ إِلَّا أَنْتَ خَلَقْتَنِي وَأَنَا عَبْدُكَ»",
            subText = "سيد الاستغفار من قاله موقناً به ومات دخل الجنة • بطاقة أدعية قرآنية #37",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_38",
            title = "دعاء الاستخارة وتفويض الأمر #38",
            calligraphyText = "«يَا حَيُّ يَا قَيُّومُ بِرَحْمَتِكَ أَسْتَغِيثُ أَصْلِحْ لِي شَأْنِي كُلَّهُ»",
            subText = "دعاء الكرب وتفويض التدبير إلى قيوم السماوات والأرض • بطاقة أدعية قرآنية #38",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF9D174DL, 0xFF4A044EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_39",
            title = "دعاء ختم القرآن المبارك #39",
            calligraphyText = "﴿رَبِّ اشْرَحْ لِي صَدْرِي ۝ وَيَسِّرْ لِي أَمْرِي﴾",
            subText = "دعاء موسى عليه السلام لطلب انشراح الصدر وتيسير العسير • بطاقة أدعية قرآنية #39",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_40",
            title = "دعاء النصر والتمكين للحق #40",
            calligraphyText = "«لَا حَوْلَ وَلَا قُوَّةَ إِلَّا بِاللَّهِ الْعَلِيِّ الْعَظِيمِ»",
            subText = "كنز من كنوز الجنة ودواء لتسعة وتسعين داء أيسرها الهم • بطاقة أدعية قرآنية #40",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF047857L, 0xFF022C22L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_41",
            title = "آية الكرسي وحفظ الرحمن #41",
            calligraphyText = "﴿اللَّهُ لَا إِلَٰهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ ۚ لَا تَأْخُذُهُ سِنَةٌ وَلَا نَوْمٌ﴾",
            subText = "آية الكرسي سيدة آي القرآن حرز وأمان وحفظ من كل سوء • بطاقة أدعية قرآنية #41",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_42",
            title = "دعاء الفرج وتفريج الكروب #42",
            calligraphyText = "﴿رَبَّنَا آتِنَا فِي الدُّنْيَا حَسَنَةً وَفِي الْآخِرَةِ حَسَنَةً وَقِنَا عَذَابَ النَّارِ﴾",
            subText = "أجمع دعاء للخير كله في المعاش والمعاد وسنة النبي ﷺ • بطاقة أدعية قرآنية #42",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_43",
            title = "سيد الاستغفار والتوبة #43",
            calligraphyText = "﴿لَا إِلَٰهَ إِلَّا أَنتَ سُبْحَانَكَ إِنِّي كُنتُ مِنَ الظَّالِمِينَ﴾",
            subText = "دعاء ذي النون في بطن الحوت ما دعا به مكروب إلا فرج الله عنه • بطاقة أدعية قرآنية #43",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_44",
            title = "دعاء تيسير الأمور وشرح الصدر #44",
            calligraphyText = "﴿حَسْبُنَا اللَّهُ وَنِعْمَ الْوَكِيلُ﴾",
            subText = "قالها إبراهيم حين أُلقي في النار وقالها محمد حين قالوا إن الناس قد جمعوا لكم • بطاقة أدعية قرآنية #44",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_45",
            title = "حسبنا الله ونعم الوكيل #45",
            calligraphyText = "﴿وَمَن يَتَوَكَّلْ عَلَى اللَّهِ فَهُوَ حَسْبُهُ ۚ إِنَّ اللَّهَ بَالِغُ أَمْرِهِ﴾",
            subText = "كفاية المتوكلين وتفويض الأمر إلى الرؤوف الرحيم • بطاقة أدعية قرآنية #45",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_46",
            title = "كنز الحوقلة والاستعانة #46",
            calligraphyText = "﴿لَا تَدْرِي لَعَلَّ اللَّهَ يُحْدِثُ بَعْدَ ذَٰلِكَ أَمْرًا﴾",
            subText = "حسن الظن بالله وانتظار الفرج القريب بعد الشدة والضيق • بطاقة أدعية قرآنية #46",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_47",
            title = "دعاء الرزق والبركة في المال #47",
            calligraphyText = "«اللَّهُمَّ أَنْتَ رَبِّي لَا إِلَهَ إِلَّا أَنْتَ خَلَقْتَنِي وَأَنَا عَبْدُكَ»",
            subText = "سيد الاستغفار من قاله موقناً به ومات دخل الجنة • بطاقة أدعية قرآنية #47",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_48",
            title = "دعاء الشفاء ورفع البأس #48",
            calligraphyText = "«يَا حَيُّ يَا قَيُّومُ بِرَحْمَتِكَ أَسْتَغِيثُ أَصْلِحْ لِي شَأْنِي كُلَّهُ»",
            subText = "دعاء الكرب وتفويض التدبير إلى قيوم السماوات والأرض • بطاقة أدعية قرآنية #48",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF9D174DL, 0xFF4A044EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_49",
            title = "دعاء الهداية والثبات على الدين #49",
            calligraphyText = "﴿رَبِّ اشْرَحْ لِي صَدْرِي ۝ وَيَسِّرْ لِي أَمْرِي﴾",
            subText = "دعاء موسى عليه السلام لطلب انشراح الصدر وتيسير العسير • بطاقة أدعية قرآنية #49",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_50",
            title = "دعاء الوالدين وبرهما #50",
            calligraphyText = "«لَا حَوْلَ وَلَا قُوَّةَ إِلَّا بِاللَّهِ الْعَلِيِّ الْعَظِيمِ»",
            subText = "كنز من كنوز الجنة ودواء لتسعة وتسعين داء أيسرها الهم • بطاقة أدعية قرآنية #50",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF047857L, 0xFF022C22L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_51",
            title = "دعاء تفريج الهم والحزن #51",
            calligraphyText = "﴿اللَّهُ لَا إِلَٰهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ ۚ لَا تَأْخُذُهُ سِنَةٌ وَلَا نَوْمٌ﴾",
            subText = "آية الكرسي سيدة آي القرآن حرز وأمان وحفظ من كل سوء • بطاقة أدعية قرآنية #51",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_52",
            title = "دعاء السكينة وطمأنينة القلب #52",
            calligraphyText = "﴿رَبَّنَا آتِنَا فِي الدُّنْيَا حَسَنَةً وَفِي الْآخِرَةِ حَسَنَةً وَقِنَا عَذَابَ النَّارِ﴾",
            subText = "أجمع دعاء للخير كله في المعاش والمعاد وسنة النبي ﷺ • بطاقة أدعية قرآنية #52",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_53",
            title = "دعاء طلب العلم النافع #53",
            calligraphyText = "﴿لَا إِلَٰهَ إِلَّا أَنتَ سُبْحَانَكَ إِنِّي كُنتُ مِنَ الظَّالِمِينَ﴾",
            subText = "دعاء ذي النون في بطن الحوت ما دعا به مكروب إلا فرج الله عنه • بطاقة أدعية قرآنية #53",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_54",
            title = "دعاء التوكل على الحي الذي لا يموت #54",
            calligraphyText = "﴿حَسْبُنَا اللَّهُ وَنِعْمَ الْوَكِيلُ﴾",
            subText = "قالها إبراهيم حين أُلقي في النار وقالها محمد حين قالوا إن الناس قد جمعوا لكم • بطاقة أدعية قرآنية #54",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_55",
            title = "دعاء النجاة من الفتن #55",
            calligraphyText = "﴿وَمَن يَتَوَكَّلْ عَلَى اللَّهِ فَهُوَ حَسْبُهُ ۚ إِنَّ اللَّهَ بَالِغُ أَمْرِهِ﴾",
            subText = "كفاية المتوكلين وتفويض الأمر إلى الرؤوف الرحيم • بطاقة أدعية قرآنية #55",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_56",
            title = "دعاء الصباح واستفتاح الرزق #56",
            calligraphyText = "﴿لَا تَدْرِي لَعَلَّ اللَّهَ يُحْدِثُ بَعْدَ ذَٰلِكَ أَمْرًا﴾",
            subText = "حسن الظن بالله وانتظار الفرج القريب بعد الشدة والضيق • بطاقة أدعية قرآنية #56",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_57",
            title = "دعاء المساء والحصن الحصين #57",
            calligraphyText = "«اللَّهُمَّ أَنْتَ رَبِّي لَا إِلَهَ إِلَّا أَنْتَ خَلَقْتَنِي وَأَنَا عَبْدُكَ»",
            subText = "سيد الاستغفار من قاله موقناً به ومات دخل الجنة • بطاقة أدعية قرآنية #57",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_58",
            title = "دعاء الاستخارة وتفويض الأمر #58",
            calligraphyText = "«يَا حَيُّ يَا قَيُّومُ بِرَحْمَتِكَ أَسْتَغِيثُ أَصْلِحْ لِي شَأْنِي كُلَّهُ»",
            subText = "دعاء الكرب وتفويض التدبير إلى قيوم السماوات والأرض • بطاقة أدعية قرآنية #58",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF9D174DL, 0xFF4A044EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_59",
            title = "دعاء ختم القرآن المبارك #59",
            calligraphyText = "﴿رَبِّ اشْرَحْ لِي صَدْرِي ۝ وَيَسِّرْ لِي أَمْرِي﴾",
            subText = "دعاء موسى عليه السلام لطلب انشراح الصدر وتيسير العسير • بطاقة أدعية قرآنية #59",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_60",
            title = "دعاء النصر والتمكين للحق #60",
            calligraphyText = "«لَا حَوْلَ وَلَا قُوَّةَ إِلَّا بِاللَّهِ الْعَلِيِّ الْعَظِيمِ»",
            subText = "كنز من كنوز الجنة ودواء لتسعة وتسعين داء أيسرها الهم • بطاقة أدعية قرآنية #60",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF047857L, 0xFF022C22L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_61",
            title = "آية الكرسي وحفظ الرحمن #61",
            calligraphyText = "﴿اللَّهُ لَا إِلَٰهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ ۚ لَا تَأْخُذُهُ سِنَةٌ وَلَا نَوْمٌ﴾",
            subText = "آية الكرسي سيدة آي القرآن حرز وأمان وحفظ من كل سوء • بطاقة أدعية قرآنية #61",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_62",
            title = "دعاء الفرج وتفريج الكروب #62",
            calligraphyText = "﴿رَبَّنَا آتِنَا فِي الدُّنْيَا حَسَنَةً وَفِي الْآخِرَةِ حَسَنَةً وَقِنَا عَذَابَ النَّارِ﴾",
            subText = "أجمع دعاء للخير كله في المعاش والمعاد وسنة النبي ﷺ • بطاقة أدعية قرآنية #62",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_63",
            title = "سيد الاستغفار والتوبة #63",
            calligraphyText = "﴿لَا إِلَٰهَ إِلَّا أَنتَ سُبْحَانَكَ إِنِّي كُنتُ مِنَ الظَّالِمِينَ﴾",
            subText = "دعاء ذي النون في بطن الحوت ما دعا به مكروب إلا فرج الله عنه • بطاقة أدعية قرآنية #63",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_64",
            title = "دعاء تيسير الأمور وشرح الصدر #64",
            calligraphyText = "﴿حَسْبُنَا اللَّهُ وَنِعْمَ الْوَكِيلُ﴾",
            subText = "قالها إبراهيم حين أُلقي في النار وقالها محمد حين قالوا إن الناس قد جمعوا لكم • بطاقة أدعية قرآنية #64",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_65",
            title = "حسبنا الله ونعم الوكيل #65",
            calligraphyText = "﴿وَمَن يَتَوَكَّلْ عَلَى اللَّهِ فَهُوَ حَسْبُهُ ۚ إِنَّ اللَّهَ بَالِغُ أَمْرِهِ﴾",
            subText = "كفاية المتوكلين وتفويض الأمر إلى الرؤوف الرحيم • بطاقة أدعية قرآنية #65",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_66",
            title = "كنز الحوقلة والاستعانة #66",
            calligraphyText = "﴿لَا تَدْرِي لَعَلَّ اللَّهَ يُحْدِثُ بَعْدَ ذَٰلِكَ أَمْرًا﴾",
            subText = "حسن الظن بالله وانتظار الفرج القريب بعد الشدة والضيق • بطاقة أدعية قرآنية #66",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_67",
            title = "دعاء الرزق والبركة في المال #67",
            calligraphyText = "«اللَّهُمَّ أَنْتَ رَبِّي لَا إِلَهَ إِلَّا أَنْتَ خَلَقْتَنِي وَأَنَا عَبْدُكَ»",
            subText = "سيد الاستغفار من قاله موقناً به ومات دخل الجنة • بطاقة أدعية قرآنية #67",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_68",
            title = "دعاء الشفاء ورفع البأس #68",
            calligraphyText = "«يَا حَيُّ يَا قَيُّومُ بِرَحْمَتِكَ أَسْتَغِيثُ أَصْلِحْ لِي شَأْنِي كُلَّهُ»",
            subText = "دعاء الكرب وتفويض التدبير إلى قيوم السماوات والأرض • بطاقة أدعية قرآنية #68",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF9D174DL, 0xFF4A044EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_69",
            title = "دعاء الهداية والثبات على الدين #69",
            calligraphyText = "﴿رَبِّ اشْرَحْ لِي صَدْرِي ۝ وَيَسِّرْ لِي أَمْرِي﴾",
            subText = "دعاء موسى عليه السلام لطلب انشراح الصدر وتيسير العسير • بطاقة أدعية قرآنية #69",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_70",
            title = "دعاء الوالدين وبرهما #70",
            calligraphyText = "«لَا حَوْلَ وَلَا قُوَّةَ إِلَّا بِاللَّهِ الْعَلِيِّ الْعَظِيمِ»",
            subText = "كنز من كنوز الجنة ودواء لتسعة وتسعين داء أيسرها الهم • بطاقة أدعية قرآنية #70",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF047857L, 0xFF022C22L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_71",
            title = "دعاء تفريج الهم والحزن #71",
            calligraphyText = "﴿اللَّهُ لَا إِلَٰهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ ۚ لَا تَأْخُذُهُ سِنَةٌ وَلَا نَوْمٌ﴾",
            subText = "آية الكرسي سيدة آي القرآن حرز وأمان وحفظ من كل سوء • بطاقة أدعية قرآنية #71",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_72",
            title = "دعاء السكينة وطمأنينة القلب #72",
            calligraphyText = "﴿رَبَّنَا آتِنَا فِي الدُّنْيَا حَسَنَةً وَفِي الْآخِرَةِ حَسَنَةً وَقِنَا عَذَابَ النَّارِ﴾",
            subText = "أجمع دعاء للخير كله في المعاش والمعاد وسنة النبي ﷺ • بطاقة أدعية قرآنية #72",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_73",
            title = "دعاء طلب العلم النافع #73",
            calligraphyText = "﴿لَا إِلَٰهَ إِلَّا أَنتَ سُبْحَانَكَ إِنِّي كُنتُ مِنَ الظَّالِمِينَ﴾",
            subText = "دعاء ذي النون في بطن الحوت ما دعا به مكروب إلا فرج الله عنه • بطاقة أدعية قرآنية #73",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_74",
            title = "دعاء التوكل على الحي الذي لا يموت #74",
            calligraphyText = "﴿حَسْبُنَا اللَّهُ وَنِعْمَ الْوَكِيلُ﴾",
            subText = "قالها إبراهيم حين أُلقي في النار وقالها محمد حين قالوا إن الناس قد جمعوا لكم • بطاقة أدعية قرآنية #74",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_75",
            title = "دعاء النجاة من الفتن #75",
            calligraphyText = "﴿وَمَن يَتَوَكَّلْ عَلَى اللَّهِ فَهُوَ حَسْبُهُ ۚ إِنَّ اللَّهَ بَالِغُ أَمْرِهِ﴾",
            subText = "كفاية المتوكلين وتفويض الأمر إلى الرؤوف الرحيم • بطاقة أدعية قرآنية #75",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_76",
            title = "دعاء الصباح واستفتاح الرزق #76",
            calligraphyText = "﴿لَا تَدْرِي لَعَلَّ اللَّهَ يُحْدِثُ بَعْدَ ذَٰلِكَ أَمْرًا﴾",
            subText = "حسن الظن بالله وانتظار الفرج القريب بعد الشدة والضيق • بطاقة أدعية قرآنية #76",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_77",
            title = "دعاء المساء والحصن الحصين #77",
            calligraphyText = "«اللَّهُمَّ أَنْتَ رَبِّي لَا إِلَهَ إِلَّا أَنْتَ خَلَقْتَنِي وَأَنَا عَبْدُكَ»",
            subText = "سيد الاستغفار من قاله موقناً به ومات دخل الجنة • بطاقة أدعية قرآنية #77",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_78",
            title = "دعاء الاستخارة وتفويض الأمر #78",
            calligraphyText = "«يَا حَيُّ يَا قَيُّومُ بِرَحْمَتِكَ أَسْتَغِيثُ أَصْلِحْ لِي شَأْنِي كُلَّهُ»",
            subText = "دعاء الكرب وتفويض التدبير إلى قيوم السماوات والأرض • بطاقة أدعية قرآنية #78",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF9D174DL, 0xFF4A044EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_79",
            title = "دعاء ختم القرآن المبارك #79",
            calligraphyText = "﴿رَبِّ اشْرَحْ لِي صَدْرِي ۝ وَيَسِّرْ لِي أَمْرِي﴾",
            subText = "دعاء موسى عليه السلام لطلب انشراح الصدر وتيسير العسير • بطاقة أدعية قرآنية #79",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_80",
            title = "دعاء النصر والتمكين للحق #80",
            calligraphyText = "«لَا حَوْلَ وَلَا قُوَّةَ إِلَّا بِاللَّهِ الْعَلِيِّ الْعَظِيمِ»",
            subText = "كنز من كنوز الجنة ودواء لتسعة وتسعين داء أيسرها الهم • بطاقة أدعية قرآنية #80",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF047857L, 0xFF022C22L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_81",
            title = "آية الكرسي وحفظ الرحمن #81",
            calligraphyText = "﴿اللَّهُ لَا إِلَٰهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ ۚ لَا تَأْخُذُهُ سِنَةٌ وَلَا نَوْمٌ﴾",
            subText = "آية الكرسي سيدة آي القرآن حرز وأمان وحفظ من كل سوء • بطاقة أدعية قرآنية #81",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_82",
            title = "دعاء الفرج وتفريج الكروب #82",
            calligraphyText = "﴿رَبَّنَا آتِنَا فِي الدُّنْيَا حَسَنَةً وَفِي الْآخِرَةِ حَسَنَةً وَقِنَا عَذَابَ النَّارِ﴾",
            subText = "أجمع دعاء للخير كله في المعاش والمعاد وسنة النبي ﷺ • بطاقة أدعية قرآنية #82",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_83",
            title = "سيد الاستغفار والتوبة #83",
            calligraphyText = "﴿لَا إِلَٰهَ إِلَّا أَنتَ سُبْحَانَكَ إِنِّي كُنتُ مِنَ الظَّالِمِينَ﴾",
            subText = "دعاء ذي النون في بطن الحوت ما دعا به مكروب إلا فرج الله عنه • بطاقة أدعية قرآنية #83",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_84",
            title = "دعاء تيسير الأمور وشرح الصدر #84",
            calligraphyText = "﴿حَسْبُنَا اللَّهُ وَنِعْمَ الْوَكِيلُ﴾",
            subText = "قالها إبراهيم حين أُلقي في النار وقالها محمد حين قالوا إن الناس قد جمعوا لكم • بطاقة أدعية قرآنية #84",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_85",
            title = "حسبنا الله ونعم الوكيل #85",
            calligraphyText = "﴿وَمَن يَتَوَكَّلْ عَلَى اللَّهِ فَهُوَ حَسْبُهُ ۚ إِنَّ اللَّهَ بَالِغُ أَمْرِهِ﴾",
            subText = "كفاية المتوكلين وتفويض الأمر إلى الرؤوف الرحيم • بطاقة أدعية قرآنية #85",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_86",
            title = "كنز الحوقلة والاستعانة #86",
            calligraphyText = "﴿لَا تَدْرِي لَعَلَّ اللَّهَ يُحْدِثُ بَعْدَ ذَٰلِكَ أَمْرًا﴾",
            subText = "حسن الظن بالله وانتظار الفرج القريب بعد الشدة والضيق • بطاقة أدعية قرآنية #86",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_87",
            title = "دعاء الرزق والبركة في المال #87",
            calligraphyText = "«اللَّهُمَّ أَنْتَ رَبِّي لَا إِلَهَ إِلَّا أَنْتَ خَلَقْتَنِي وَأَنَا عَبْدُكَ»",
            subText = "سيد الاستغفار من قاله موقناً به ومات دخل الجنة • بطاقة أدعية قرآنية #87",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_88",
            title = "دعاء الشفاء ورفع البأس #88",
            calligraphyText = "«يَا حَيُّ يَا قَيُّومُ بِرَحْمَتِكَ أَسْتَغِيثُ أَصْلِحْ لِي شَأْنِي كُلَّهُ»",
            subText = "دعاء الكرب وتفويض التدبير إلى قيوم السماوات والأرض • بطاقة أدعية قرآنية #88",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF9D174DL, 0xFF4A044EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_89",
            title = "دعاء الهداية والثبات على الدين #89",
            calligraphyText = "﴿رَبِّ اشْرَحْ لِي صَدْرِي ۝ وَيَسِّرْ لِي أَمْرِي﴾",
            subText = "دعاء موسى عليه السلام لطلب انشراح الصدر وتيسير العسير • بطاقة أدعية قرآنية #89",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_90",
            title = "دعاء الوالدين وبرهما #90",
            calligraphyText = "«لَا حَوْلَ وَلَا قُوَّةَ إِلَّا بِاللَّهِ الْعَلِيِّ الْعَظِيمِ»",
            subText = "كنز من كنوز الجنة ودواء لتسعة وتسعين داء أيسرها الهم • بطاقة أدعية قرآنية #90",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF047857L, 0xFF022C22L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_91",
            title = "دعاء تفريج الهم والحزن #91",
            calligraphyText = "﴿اللَّهُ لَا إِلَٰهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ ۚ لَا تَأْخُذُهُ سِنَةٌ وَلَا نَوْمٌ﴾",
            subText = "آية الكرسي سيدة آي القرآن حرز وأمان وحفظ من كل سوء • بطاقة أدعية قرآنية #91",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF1E3A8AL, 0xFF0F172AL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_92",
            title = "دعاء السكينة وطمأنينة القلب #92",
            calligraphyText = "﴿رَبَّنَا آتِنَا فِي الدُّنْيَا حَسَنَةً وَفِي الْآخِرَةِ حَسَنَةً وَقِنَا عَذَابَ النَّارِ﴾",
            subText = "أجمع دعاء للخير كله في المعاش والمعاد وسنة النبي ﷺ • بطاقة أدعية قرآنية #92",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF047857L, 0xFF064E3BL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_93",
            title = "دعاء طلب العلم النافع #93",
            calligraphyText = "﴿لَا إِلَٰهَ إِلَّا أَنتَ سُبْحَانَكَ إِنِّي كُنتُ مِنَ الظَّالِمِينَ﴾",
            subText = "دعاء ذي النون في بطن الحوت ما دعا به مكروب إلا فرج الله عنه • بطاقة أدعية قرآنية #93",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF0F766EL, 0xFF042F2EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_94",
            title = "دعاء التوكل على الحي الذي لا يموت #94",
            calligraphyText = "﴿حَسْبُنَا اللَّهُ وَنِعْمَ الْوَكِيلُ﴾",
            subText = "قالها إبراهيم حين أُلقي في النار وقالها محمد حين قالوا إن الناس قد جمعوا لكم • بطاقة أدعية قرآنية #94",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFFB45309L, 0xFF78350FL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_95",
            title = "دعاء النجاة من الفتن #95",
            calligraphyText = "﴿وَمَن يَتَوَكَّلْ عَلَى اللَّهِ فَهُوَ حَسْبُهُ ۚ إِنَّ اللَّهَ بَالِغُ أَمْرِهِ﴾",
            subText = "كفاية المتوكلين وتفويض الأمر إلى الرؤوف الرحيم • بطاقة أدعية قرآنية #95",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF0D9488L, 0xFF115E59L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_96",
            title = "دعاء الصباح واستفتاح الرزق #96",
            calligraphyText = "﴿لَا تَدْرِي لَعَلَّ اللَّهَ يُحْدِثُ بَعْدَ ذَٰلِكَ أَمْرًا﴾",
            subText = "حسن الظن بالله وانتظار الفرج القريب بعد الشدة والضيق • بطاقة أدعية قرآنية #96",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF4338CAL, 0xFF312E81L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_97",
            title = "دعاء المساء والحصن الحصين #97",
            calligraphyText = "«اللَّهُمَّ أَنْتَ رَبِّي لَا إِلَهَ إِلَّا أَنْتَ خَلَقْتَنِي وَأَنَا عَبْدُكَ»",
            subText = "سيد الاستغفار من قاله موقناً به ومات دخل الجنة • بطاقة أدعية قرآنية #97",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF6B21A8L, 0xFF3B0764L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_98",
            title = "دعاء الاستخارة وتفويض الأمر #98",
            calligraphyText = "«يَا حَيُّ يَا قَيُّومُ بِرَحْمَتِكَ أَسْتَغِيثُ أَصْلِحْ لِي شَأْنِي كُلَّهُ»",
            subText = "دعاء الكرب وتفويض التدبير إلى قيوم السماوات والأرض • بطاقة أدعية قرآنية #98",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF9D174DL, 0xFF4A044EL),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_99",
            title = "دعاء ختم القرآن المبارك #99",
            calligraphyText = "﴿رَبِّ اشْرَحْ لِي صَدْرِي ۝ وَيَسِّرْ لِي أَمْرِي﴾",
            subText = "دعاء موسى عليه السلام لطلب انشراح الصدر وتيسير العسير • بطاقة أدعية قرآنية #99",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFFC2410CL, 0xFF7C2D12L),
            drawableRes = null
        ),
        GalleryItem(
            id = "g_dua_100",
            title = "دعاء النصر والتمكين للحق #100",
            calligraphyText = "«لَا حَوْلَ وَلَا قُوَّةَ إِلَّا بِاللَّهِ الْعَلِيِّ الْعَظِيمِ»",
            subText = "كنز من كنوز الجنة ودواء لتسعة وتسعين داء أيسرها الهم • بطاقة أدعية قرآنية #100",
            category = "أدعية قرآنية",
            gradientColors = listOf(0xFF047857L, 0xFF022C22L),
            drawableRes = null
        ),
    )
}
