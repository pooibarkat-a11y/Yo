package com.example.data

import com.example.model.Ayah
import com.example.model.Reciter
import com.example.model.RevelationType
import com.example.model.Surah

object QuranData {

    val reciters: List<Reciter> = RecitersData.allReciters

    fun getAudioUrl(reciter: Reciter, surahId: Int, ayahNumber: Int = 1): String {
        val s = surahId.toString().padStart(3, '0')
        return if (reciter.isVerseByVerse) {
            val a = ayahNumber.toString().padStart(3, '0')
            "${reciter.serverFolder}$s$a.mp3"
        } else {
            "${reciter.serverFolder}$s.mp3"
        }
    }

    fun getFallbackAudioUrl(surahId: Int, ayahNumber: Int): String {
        val s = surahId.toString().padStart(3, '0')
        val a = ayahNumber.toString().padStart(3, '0')
        return "https://everyayah.com/data/Alafasy_128kbps/$s$a.mp3"
    }

    val surahs: List<Surah> = listOf(
        Surah(1, "الفاتحة", "Al-Fatihah", "The Opener", RevelationType.MAKKI, 7, 1, 1),
        Surah(2, "البقرة", "Al-Baqarah", "The Cow", RevelationType.MADANI, 286, 2, 1),
        Surah(3, "آل عمران", "Ali 'Imran", "Family of Imran", RevelationType.MADANI, 200, 50, 3),
        Surah(4, "النساء", "An-Nisa", "The Women", RevelationType.MADANI, 176, 77, 4),
        Surah(5, "المائدة", "Al-Ma'idah", "The Table Spread", RevelationType.MADANI, 120, 106, 6),
        Surah(6, "الأنعام", "Al-An'am", "The Cattle", RevelationType.MAKKI, 165, 128, 7),
        Surah(7, "الأعراف", "Al-A'raf", "The Heights", RevelationType.MAKKI, 206, 151, 8),
        Surah(8, "الأنفال", "Al-Anfal", "The Spoils of War", RevelationType.MADANI, 75, 177, 9),
        Surah(9, "التوبة", "At-Tawbah", "The Repentance", RevelationType.MADANI, 129, 187, 10),
        Surah(10, "يونس", "Yunus", "Jonah", RevelationType.MAKKI, 109, 208, 11),
        Surah(11, "هود", "Hud", "Hud", RevelationType.MAKKI, 123, 221, 11),
        Surah(12, "يوسف", "Yusuf", "Joseph", RevelationType.MAKKI, 111, 235, 12),
        Surah(13, "الرعد", "Ar-Ra'd", "The Thunder", RevelationType.MADANI, 43, 249, 13),
        Surah(14, "إبراهيم", "Ibrahim", "Abraham", RevelationType.MAKKI, 52, 255, 13),
        Surah(15, "الحجر", "Al-Hijr", "The Rocky Tract", RevelationType.MAKKI, 99, 262, 14),
        Surah(16, "النحل", "An-Nahl", "The Bee", RevelationType.MAKKI, 128, 267, 14),
        Surah(17, "الإسراء", "Al-Isra", "The Night Journey", RevelationType.MAKKI, 111, 282, 15),
        Surah(18, "الكهف", "Al-Kahf", "The Cave", RevelationType.MAKKI, 110, 293, 15),
        Surah(19, "مريم", "Maryam", "Mary", RevelationType.MAKKI, 98, 305, 16),
        Surah(20, "طه", "Taha", "Ta-Ha", RevelationType.MAKKI, 135, 312, 16),
        Surah(21, "الأنبياء", "Al-Anbiya", "The Prophets", RevelationType.MAKKI, 112, 322, 17),
        Surah(22, "الحج", "Al-Hajj", "The Pilgrimage", RevelationType.MADANI, 78, 332, 17),
        Surah(23, "المؤمنون", "Al-Mu'minun", "The Believers", RevelationType.MAKKI, 118, 342, 18),
        Surah(24, "النور", "An-Nur", "The Light", RevelationType.MADANI, 64, 350, 18),
        Surah(25, "الفرقان", "Al-Furqan", "The Criterion", RevelationType.MAKKI, 77, 359, 18),
        Surah(26, "الشعراء", "Ash-Shu'ara", "The Poets", RevelationType.MAKKI, 227, 367, 19),
        Surah(27, "النمل", "An-Naml", "The Ant", RevelationType.MAKKI, 93, 377, 19),
        Surah(28, "القصص", "Al-Qasas", "The Stories", RevelationType.MAKKI, 88, 385, 20),
        Surah(29, "العنكبوت", "Al-'Ankabut", "The Spider", RevelationType.MAKKI, 69, 396, 20),
        Surah(30, "الروم", "Ar-Rum", "The Romans", RevelationType.MAKKI, 60, 404, 21),
        Surah(31, "لقمان", "Luqman", "Luqman", RevelationType.MAKKI, 34, 411, 21),
        Surah(32, "السجدة", "As-Sajdah", "The Prostration", RevelationType.MAKKI, 30, 415, 21),
        Surah(33, "الأحزاب", "Al-Ahzab", "The Combined Forces", RevelationType.MADANI, 73, 418, 21),
        Surah(34, "سبأ", "Saba", "Sheba", RevelationType.MAKKI, 54, 428, 22),
        Surah(35, "فاطر", "Fatir", "Originator", RevelationType.MAKKI, 45, 434, 22),
        Surah(36, "يس", "Ya-Sin", "Ya-Sin", RevelationType.MAKKI, 83, 440, 22),
        Surah(37, "الصافات", "As-Saffat", "Those who set the Ranks", RevelationType.MAKKI, 182, 446, 23),
        Surah(38, "ص", "Sad", "The Letter Sad", RevelationType.MAKKI, 88, 453, 23),
        Surah(39, "الزمر", "Az-Zumar", "The Troops", RevelationType.MAKKI, 75, 458, 23),
        Surah(40, "غافر", "Ghafir", "The Forgiver", RevelationType.MAKKI, 85, 467, 24),
        Surah(41, "فصلت", "Fussilat", "Explained in Detail", RevelationType.MAKKI, 54, 477, 24),
        Surah(42, "الشورى", "Ash-Shuraa", "The Consultation", RevelationType.MAKKI, 53, 483, 25),
        Surah(43, "الزخرف", "Az-Zukhruf", "The Ornaments of Gold", RevelationType.MAKKI, 89, 489, 25),
        Surah(44, "الدخان", "Ad-Dukhan", "The Smoke", RevelationType.MAKKI, 59, 496, 25),
        Surah(45, "الجاثية", "Al-Jathiyah", "The Crouching", RevelationType.MAKKI, 37, 499, 25),
        Surah(46, "الأحقاف", "Al-Ahqaf", "The Wind-Curved Sandhills", RevelationType.MAKKI, 35, 502, 26),
        Surah(47, "محمد", "Muhammad", "Muhammad", RevelationType.MADANI, 38, 507, 26),
        Surah(48, "الفتح", "Al-Fath", "The Victory", RevelationType.MADANI, 29, 511, 26),
        Surah(49, "الحجرات", "Al-Hujurat", "The Rooms", RevelationType.MADANI, 18, 515, 26),
        Surah(50, "ق", "Qaf", "The Letter Qaf", RevelationType.MAKKI, 45, 518, 26),
        Surah(51, "الذاريات", "Adh-Dhariyat", "The Winnowing Winds", RevelationType.MAKKI, 60, 520, 26),
        Surah(52, "الطور", "At-Tur", "The Mount", RevelationType.MAKKI, 49, 523, 27),
        Surah(53, "النجم", "An-Najm", "The Star", RevelationType.MAKKI, 62, 526, 27),
        Surah(54, "القمر", "Al-Qamar", "The Moon", RevelationType.MAKKI, 55, 528, 27),
        Surah(55, "الرحمن", "Ar-Rahman", "The Beneficent", RevelationType.MADANI, 78, 531, 27),
        Surah(56, "الواقعة", "Al-Waqi'ah", "The Inevitable", RevelationType.MAKKI, 96, 534, 27),
        Surah(57, "الحديد", "Al-Hadid", "The Iron", RevelationType.MADANI, 29, 537, 27),
        Surah(58, "المجادلة", "Al-Mujadila", "The Pleading Woman", RevelationType.MADANI, 22, 542, 28),
        Surah(59, "الحشر", "Al-Hashr", "The Exile", RevelationType.MADANI, 24, 545, 28),
        Surah(60, "الممتحنة", "Al-Mumtahanah", "She that is to be examined", RevelationType.MADANI, 13, 549, 28),
        Surah(61, "الصف", "As-Saff", "The Ranks", RevelationType.MADANI, 14, 551, 28),
        Surah(62, "الجمعة", "Al-Jumu'ah", "The Congregation", RevelationType.MADANI, 11, 553, 28),
        Surah(63, "المنافقون", "Al-Munafiqun", "The Hypocrites", RevelationType.MADANI, 11, 554, 28),
        Surah(64, "التغابن", "At-Taghabun", "The Mutual Disillusion", RevelationType.MADANI, 18, 556, 28),
        Surah(65, "الطلاق", "At-Talaq", "The Divorce", RevelationType.MADANI, 12, 558, 28),
        Surah(66, "التحريم", "At-Tahrim", "The Prohibition", RevelationType.MADANI, 12, 560, 28),
        Surah(67, "الملك", "Al-Mulk", "The Sovereignty", RevelationType.MAKKI, 30, 562, 29),
        Surah(68, "القلم", "Al-Qalam", "The Pen", RevelationType.MAKKI, 52, 564, 29),
        Surah(69, "الحاقة", "Al-Haqqah", "The Inevitable", RevelationType.MAKKI, 52, 566, 29),
        Surah(70, "المعارج", "Al-Ma'arij", "The Ascending Stairways", RevelationType.MAKKI, 44, 568, 29),
        Surah(71, "نوح", "Nuh", "Noah", RevelationType.MAKKI, 28, 570, 29),
        Surah(72, "الجن", "Al-Jinn", "The Jinn", RevelationType.MAKKI, 28, 572, 29),
        Surah(73, "المزمل", "Al-Muzzammil", "The Enshrouded One", RevelationType.MAKKI, 20, 574, 29),
        Surah(74, "المدثر", "Al-Muddaththir", "The Cloaked One", RevelationType.MAKKI, 56, 575, 29),
        Surah(75, "القيامة", "Al-Qiyamah", "The Resurrection", RevelationType.MAKKI, 40, 577, 29),
        Surah(76, "الإنسان", "Al-Insan", "Man", RevelationType.MADANI, 31, 578, 29),
        Surah(77, "المرسلات", "Al-Mursalat", "The Emissaries", RevelationType.MAKKI, 50, 580, 29),
        Surah(78, "النبأ", "An-Naba", "The Tidings", RevelationType.MAKKI, 40, 582, 30),
        Surah(79, "النازعات", "An-Nazi'at", "Those who drag forth", RevelationType.MAKKI, 46, 583, 30),
        Surah(80, "عبس", "'Abasa", "He Frowned", RevelationType.MAKKI, 42, 585, 30),
        Surah(81, "التكوير", "At-Takwir", "The Overthrowing", RevelationType.MAKKI, 29, 586, 30),
        Surah(82, "الانفطار", "Al-Infitar", "The Cleaving", RevelationType.MAKKI, 19, 587, 30),
        Surah(83, "المطففين", "Al-Mutaffifin", "The Defrauding", RevelationType.MAKKI, 36, 587, 30),
        Surah(84, "الانشقاق", "Al-Inshiqaq", "The Splitting Open", RevelationType.MAKKI, 25, 589, 30),
        Surah(85, "البروج", "Al-Buruj", "The Mansions of the Stars", RevelationType.MAKKI, 22, 590, 30),
        Surah(86, "الطارق", "At-Tariq", "The Morning Star", RevelationType.MAKKI, 17, 591, 30),
        Surah(87, "الأعلى", "Al-A'la", "The Most High", RevelationType.MAKKI, 19, 591, 30),
        Surah(88, "الغاشية", "Al-Ghashiyah", "The Overwhelming", RevelationType.MAKKI, 26, 592, 30),
        Surah(89, "الفجر", "Al-Fajr", "The Dawn", RevelationType.MAKKI, 30, 593, 30),
        Surah(90, "البلد", "Al-Balad", "The City", RevelationType.MAKKI, 20, 594, 30),
        Surah(91, "الشمس", "Ash-Shams", "The Sun", RevelationType.MAKKI, 15, 595, 30),
        Surah(92, "الليل", "Al-Layl", "The Night", RevelationType.MAKKI, 21, 595, 30),
        Surah(93, "الضحى", "Ad-Duhaa", "The Morning Hours", RevelationType.MAKKI, 11, 596, 30),
        Surah(94, "الشرح", "Ash-Sharh", "The Relief", RevelationType.MAKKI, 8, 596, 30),
        Surah(95, "التين", "At-Tin", "The Fig", RevelationType.MAKKI, 8, 597, 30),
        Surah(96, "العلق", "Al-'Alaq", "The Clot", RevelationType.MAKKI, 19, 597, 30),
        Surah(97, "القدر", "Al-Qadr", "The Power", RevelationType.MAKKI, 5, 598, 30),
        Surah(98, "البينة", "Al-Bayyinah", "The Clear Proof", RevelationType.MADANI, 8, 598, 30),
        Surah(99, "الزلزلة", "Az-Zalzalah", "The Earthquake", RevelationType.MADANI, 8, 599, 30),
        Surah(100, "العاديات", "Al-'Adiyat", "The Courser", RevelationType.MAKKI, 11, 599, 30),
        Surah(101, "القارعة", "Al-Qari'ah", "The Calamity", RevelationType.MAKKI, 11, 600, 30),
        Surah(102, "التكاثر", "At-Takathur", "The Rivalry in world increase", RevelationType.MAKKI, 8, 600, 30),
        Surah(103, "العصر", "Al-'Asr", "The Declining Day", RevelationType.MAKKI, 3, 601, 30),
        Surah(104, "الهمزة", "Al-Humazah", "The Traducer", RevelationType.MAKKI, 9, 601, 30),
        Surah(105, "الفيل", "Al-Fil", "The Elephant", RevelationType.MAKKI, 5, 601, 30),
        Surah(106, "قريش", "Quraysh", "Quraysh", RevelationType.MAKKI, 4, 602, 30),
        Surah(107, "الماعون", "Al-Ma'un", "The Small kindnesses", RevelationType.MAKKI, 7, 602, 30),
        Surah(108, "الكوثر", "Al-Kawthar", "The Abundance", RevelationType.MAKKI, 3, 602, 30),
        Surah(109, "الكافرون", "Al-Kafirun", "The Disbelievers", RevelationType.MAKKI, 6, 603, 30),
        Surah(110, "النصر", "An-Nasr", "The Divine Support", RevelationType.MADANI, 3, 603, 30),
        Surah(111, "المسد", "Al-Masad", "The Palm Fiber", RevelationType.MAKKI, 5, 603, 30),
        Surah(112, "الإخلاص", "Al-Ikhlas", "The Sincerity", RevelationType.MAKKI, 4, 604, 30),
        Surah(113, "الفلق", "Al-Falaq", "The Daybreak", RevelationType.MAKKI, 5, 604, 30),
        Surah(114, "الناس", "An-Nas", "Mankind", RevelationType.MAKKI, 6, 604, 30)
    )

    private val detailedAyahs: Map<Int, List<Ayah>> = mapOf(
        // Al-Fatihah
        1 to listOf(
            Ayah(1, 1, "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ ۝١", "In the name of Allah, the Entirely Merciful, the Especially Merciful.", "أبدأ قراءتي مستعيناً باسم الله الرحمن واسع الرحمة الرحيم بعباده.", "افتتاح كتاب الله العزيز."),
            Ayah(1, 2, "الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ ۝٢", "All praise is due to Allah, Lord of the worlds.", "الثناء المطلق والمدح الكامل لله وحده خالق كل شيء ومدبره.", ""),
            Ayah(1, 3, "الرَّحْمَٰنِ الرَّحِيمِ ۝٣", "The Entirely Merciful, the Especially Merciful.", "الرحمن الذي وسعت رحمته كل شيء، الرحيم بعباده المؤمنين.", ""),
            Ayah(1, 4, "مَالِكِ يَوْمِ الدِّينِ ۝٤", "Sovereign of the Day of Recompense.", "المتصرف وحده في يوم القيامة والجزاء والحساب.", ""),
            Ayah(1, 5, "إِيَّاكَ نَعْبُدُ وَإِيَّاكَ نَسْتَعِينُ ۝٥", "It is You we worship and You we ask for help.", "نخصك وحدك بالعبادة ونستعين بك وحدك في كل شؤوننا.", ""),
            Ayah(1, 6, "اهْدِنَا الصِّرَاطَ الْمُسْتَقِيمَ ۝٦", "Guide us to the straight path.", "وفقنا وأرشدنا وثبتنا على الصراط المستقيم وهو دين الإسلام.", ""),
            Ayah(1, 7, "صِرَاطَ الَّذِينَ أَنْعَمْتَ عَلَيْهِمْ غَيْرِ الْمَغْضُوبِ عَلَيْهِمْ وَلَا الضَّالِّينَ ۝٧", "The path of those upon whom You have bestowed favor, not of those who have evoked Your anger or of those who are astray.", "طريق الأنبياء والصديقين والشهداء والصالحين، غير المغضوب عليهم (اليهود) ولا الضالين (النصارى).", "")
        ),

        // Al-Ikhlas
        112 to listOf(
            Ayah(112, 1, "قُلْ هُوَ اللَّهُ أَحَدٌ ۝١", "Say, 'He is Allah, [who is] One.'", "قل أيها الرسول: الله هو الواحد الأحد المتفرد بالكمال.", "سأل المشركون عن نسب الرب فنزلت."),
            Ayah(112, 2, "اللَّهُ الصَّمَدُ ۝٢", "Allah, the Eternal Refuge.", "الله السيد الذي تصمد وتتجه إليه جميع الخلائق في حوائجها.", ""),
            Ayah(112, 3, "لَمْ يَلِدْ وَلَمْ يُولَدْ ۝٣", "He neither begets nor is born,", "تنزه سبحانه عن الولد والوالد والشبيه والمثيل.", ""),
            Ayah(112, 4, "وَلَمْ يَكُن لَّهُ كُفُوًا أَحَدٌ ۝٤", "Nor is there to Him any equivalent.", "وليس له مكافئ ولا نظير ولا مثيل من خلقه تبارك وتعالى.", "")
        ),

        // Al-Falaq
        113 to listOf(
            Ayah(113, 1, "قُلْ أَعُوذُ بِرَبِّ الْفَلَقِ ۝١", "Say, 'I seek refuge in the Lord of daybreak'", "قل ألتجئ وأتحصن برب الصبح وفالق الحَب والنوى.", "المعوذتان للحفظ والتحصين من الشرور."),
            Ayah(113, 2, "مِن شَرِّ مَا خَلَقَ ۝٢", "From the evil of that which He created", "من شر جميع المخلوقات وآذاها.", ""),
            Ayah(113, 3, "وَمِن شَرِّ غَاسِقٍ إِذَا وَقَبَ ۝٣", "And from the evil of darkness when it settles", "ومن شر الليل وظلامه إذا دخل وانتشر.", ""),
            Ayah(113, 4, "وَمِن شَرِّ النَّفَّاثَاتِ فِي الْعُقَدِ ۝٤", "And from the evil of the blowers in knots", "ومن شر الساحرات والنفث في العقد للإفساد.", ""),
            Ayah(113, 5, "وَمِن شَرِّ حَاسِدٍ إِذَا حَسَدَ ۝٥", "And from the evil of an envier when he envies.", "ومن شر كل حاسد يتمنى زوال النعمة عن غيره.", "")
        ),

        // An-Nas
        114 to listOf(
            Ayah(114, 1, "قُلْ أَعُوذُ بِرَبِّ النَّاسِ ۝١", "Say, 'I seek refuge in the Lord of mankind'", "قل أعتصم برب الناس وخالقهم ومدبر أمورهم.", "التحصين من وساوس الإنس والجن."),
            Ayah(114, 2, "مَلِكِ النَّاسِ ۝٢", "The Sovereign of mankind,", "مالكهم والمتصرف فيهم بعدله وحكمته.", ""),
            Ayah(114, 3, "إِلَٰهِ النَّاسِ ۝٣", "The God of mankind,", "معبودهم الحق الذي لا معبود سواه.", ""),
            Ayah(114, 4, "مِن شَرِّ الْوَسْوَاسِ الْخَنَّاسِ ۝٤", "From the evil of the retreating whisperer", "من شر الشيطان الذي يلقي الوسوسة ويخنس إذا ذكر الله.", ""),
            Ayah(114, 5, "الَّذِي يُوَسْوِسُ فِي صُدُورِ النَّاسِ ۝٥", "Who whispers into the breasts of mankind", "الذي ينفث الشر والشكوك في قلوب الناس.", ""),
            Ayah(114, 6, "مِنَ الْجِنَّةِ وَالنَّاسِ ۝٦", "From among the jinn and mankind.", "من شياطين الإنس والجن.", "")
        ),

        // Al-Kawthar
        108 to listOf(
            Ayah(108, 1, "إِنَّا أَعْطَيْنَاكَ الْكَوْثَرَ ۝١", "Indeed, We have granted you, [O Muhammad], al-Kawthar.", "إنا أعطيناك أيها النبي الخير العظيم ونهر الكوثر في الجنة.", "نزلت تسلية للنبي صلى الله عليه وسلم."),
            Ayah(108, 2, "فَصَلِّ لِرَبِّكَ وَانْحَرْ ۝٢", "So pray to your Lord and sacrifice [to Him alone].", "فأخلص لربك صلاتك كلها وانحر ذبيحتك له وحده.", ""),
            Ayah(108, 3, "إِنَّ شَانِئَكَ هُوَ الْأَبْتَرُ ۝٣", "Indeed, your enemy is the one cut off.", "إن مبغضك هو المنقطع ذكره وعقبه وكل خير عنه.", "")
        ),

        // Al-Asr
        103 to listOf(
            Ayah(103, 1, "وَالْعَصْرِ ۝١", "By time,", "أقسم الله تعالى بالزمان والدهر لما فيه من العبر.", ""),
            Ayah(103, 2, "إِنَّ الْإِنسَانَ لَفِي خُسْرٍ ۝٢", "Indeed, mankind is in loss,", "إن جنس الإنسان في خسران ونقصان وهلاك.", ""),
            Ayah(103, 3, "إِلَّا الَّذِينَ آمَنُوا وَعَمِلُوا الصَّالِحَاتِ وَتَوَاصَوْا بِالْحَقِّ وَتَوَاصَوْا بِالصَّبْرِ ۝٣", "Except for those who have believed and done righteous deeds and advised each other to truth and advised each other to patience.", "إلا الذين جمعوا بين الإيمان بالله والعمل الصالح والتواصي بالحق والصبر عليه.", "")
        ),

        // Al-Mulk (first 5 ayahs)
        67 to listOf(
            Ayah(67, 1, "تَبَارَكَ الَّذِي بِيَدِهِ الْمُلْكُ وَهُوَ عَلَىٰ كُلِّ شَيْءٍ قَدِيرٌ ۝١", "Blessed is He in whose hand is dominion, and He is over all things competent -", "تكاثر خير الله وتعالى شأنه، بيده ملك السموات والأرض والقدرة المطلقة.", "المانعة والمنجية من عذاب القبر."),
            Ayah(67, 2, "الَّذِي خَلَقَ الْمَوْتَ وَالْحَيَاةَ لِيَبْلُوَكُمْ أَيُّكُمْ أَحْسَنُ عَمَلًا ۚ وَهُوَ الْعَزِيزُ الْغَفُورُ ۝٢", "[He] who created death and life to test you [as to] which of you is best in deed - and He is the Exalted in Might, the Forgiving -", "خلق الحياة والموت امتحاناً للعباد ليظهر أيهم أخلص عملاً وأصوبه.", ""),
            Ayah(67, 3, "الَّذِي خَلَقَ سَبْعَ سَمَاوَاتٍ طِبَاقًا ۖ مَّا تَرَىٰ فِي خَلْقِ الرَّحْمَٰنِ مِن تَفَاوُتٍ ۖ فَارْجِعِ الْبَصَرَ هَلْ تَرَىٰ مِن فُطُورٍ ۝٣", "[And] who created seven heavens in layers. You see no flaw in the creation of the Most Merciful. So look again; do you see any flaw?", "خلق سبع سماوات بعضها فوق بعض بإتقان تام، لا نقص ولا خلل.", ""),
            Ayah(67, 4, "ثُمَّ ارْجِعِ الْبَصَرَ كَرَّتَيْنِ يَنقَلِبْ إِلَيْكَ الْبَصَرُ خَاسِئًا وَهُوَ حَسِيرٌ ۝٤", "Then return [your] vision twice again. [Your] vision will return to you humbled while it is fatigued.", "كرر النظر مراراً وتأمّل، سيرجع بصرك كليلاً عاجزاً عن التماس أي عيب.", ""),
            Ayah(67, 5, "وَلَقَدْ زَيَّنَّا السَّمَاءَ الدُّنْيَا بِمَصَابِيحَ وَجَعَلْنَاهَا رُجُومًا لِّلشَّيَاطِينِ ۖ وَأَعْتَدْنَا لَهُمْ عَذَابَ السَّعِيرِ ۝٥", "And We have certainly beautified the nearest heaven with stars and have made [from] them projectiles for the devils.", "زينا السماء الدنيا بنجوم وكواكب منيرة، ورجوماً للشياطين المسترقين للسمع.", "")
        ),

        // Ya-Sin (first 6 ayahs)
        36 to listOf(
            Ayah(36, 1, "يس ۝١", "Ya-Sin.", "حروف مقطعة لبيان إعجاز القرآن العظيم.", "قلب القرآن الكريم."),
            Ayah(36, 2, "وَالْقُرْآنِ الْحَكِيمِ ۝٢", "By the Quran, full of wisdom,", "قسَمٌ بالقرآن المحكم المشتمل على الحكمة والهدى.", ""),
            Ayah(36, 3, "إِنَّكَ لَمِنَ الْمُرْسَلِينَ ۝٣", "Indeed you, [O Muhammad], are from among the messengers,", "إنك يا محمد لمن الرسل الصادقين المبعوثين بالحق.", ""),
            Ayah(36, 4, "عَلَىٰ صِرَاطٍ مُّسْتَقِيمٍ ۝٤", "On a straight path.", "على دين قويم ومنهج سليم هو الإسلام.", ""),
            Ayah(36, 5, "تَنزِيلَ الْعَزِيزِ الرَّحِيمِ ۝٥", "[This is] a revelation of the Exalted in Might, the Merciful,", "هذا القرآن تنزيل من الله العزيز في انتقامه، الرحيم بعباده.", ""),
            Ayah(36, 6, "لِتُنذِرَ قَوْمًا مَّا أُنذِرَ آبَاؤُهُمْ فَهُمْ غَافِلُونَ ۝٦", "That you may warn a people whose forefathers were not warned, so they are unaware.", "لتخوف قوماً انقطعت عنهم النذارة مدة حتى غلب عليهم الجهل.", "")
        ),

        // Ayat Al-Kursi (Al-Baqarah 255)
        2 to listOf(
            Ayah(2, 1, "الم ۝١", "Alif, Lam, Meem.", "حروف مقطعة للإعجاز والتحدي بأن القرآن مركب من حروف لغتكم.", ""),
            Ayah(2, 2, "ذَٰلِكَ الْكِتَابُ لَا رَيْبَ ۛ فِيهِ ۛ هُدًى لِّلْمُتَّقِينَ ۝٢", "This is the Book about which there is no doubt, a guidance for those conscious of Allah -", "هذا القرآن لا شك في صدقه ولا ارتياب، هادٍ للمتقين الذين يخشون ربهم.", ""),
            Ayah(2, 3, "الَّذِينَ يُؤْمِنُونَ بِالْغَيْبِ وَيُقِيمُونَ الصَّلَاةَ وَمِمَّا رَزَقْنَاهُمْ يُنفِقُونَ ۝٣", "Who believe in the unseen, establish prayer, and spend out of what We have provided for them,", "يصدقون بما غاب عن حواسهم من البعث والجنة والنار، ويؤدون الصلاة بأركانها.", ""),
            Ayah(2, 255, "اللَّهُ لَا إِلَٰهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ ۚ لَا تَأْخُذُهُ سِنَةٌ وَلَا نَوْمٌ ۚ لَّهُ مَا فِي السَّمَاوَاتِ وَمَا فِي الْأَرْضِ ۗ مَن ذَا الَّذِي يَشْفَعُ عِندَهُ إِلَّا بِإِذْنِهِ ۚ يَعْلَمُ مَا بَيْنَ أَيْدِيهِمْ وَمَا خَلْفَهُمْ ۖ وَلَا يُحِيطُونَ بِشَيْءٍ مِّنْ عِلْمِهِ إِلَّا بِمَا شَاءَ ۚ وَسِعَ كُرْسِيُّهُ السَّمَاوَاتِ وَالْأَرْضَ ۖ وَلَا يَئُودُهُ حِفْظُهُمَا ۚ وَهُوَ الْعَلِيُّ الْعَظِيمُ ۝٢٥٥", "Allah - there is no deity except Him, the Ever-Living, the Sustainer of all existence. Neither drowsiness overtakes Him nor sleep...", "آية الكرسي: أعظم آية في كتاب الله تعالى، متضمنة لتوحيد الأسماء والصفات وكمال القيومية والعظمة.", "سيد آي القرآن.")
        )
    )

    fun getAyahsForSurah(surahId: Int): List<Ayah> {
        // 1. Check in-memory fetched cache from API
        val cached = QuranApiService.getCachedSurah(surahId)
        if (cached != null && cached.isNotEmpty()) {
            return cached
        }

        // 2. Check complete offline assets provider (authentic Uthmani text for all 114 Surahs)
        val offlineAsset = QuranOfflineProvider.getSurah(null, surahId)
        if (offlineAsset.isNotEmpty()) {
            return offlineAsset
        }

        // 3. Check offline preloaded authentic surahs
        val offline = QuranOfflineSurahs.offlineSurahs[surahId]
        if (offline != null && offline.isNotEmpty()) {
            return offline
        }

        // 4. Check detailedAyahs map
        val existing = detailedAyahs[surahId]
        if (existing != null && existing.isNotEmpty()) {
            return existing
        }

        return emptyList()
    }


    val verseOfTheDay: Ayah = detailedAyahs[2]?.find { it.ayahNumber == 255 } ?: detailedAyahs[1]!![1]
}
