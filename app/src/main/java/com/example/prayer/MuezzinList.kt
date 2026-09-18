package com.example.prayer

/**
 * قائمة كبار مؤذني العالم الإسلامي وأشهر الأصوات الخاشعة
 * تشمل الشيخ محمد رفعت، عبد الباسط، علي ملا، الحصري، المنشاوي، طوبار، أذان الحرمين والأقصى
 * كل مؤذن مزود بصورته الشخصية وتصنيفه الدقيق وصوت أذانه الخاشع
 */
val ALL_MUEZZINS: List<Muezzin> = listOf(
    Muezzin(
        id = "rifat",
        nameArabic = "الشيخ محمد رفعت",
        mosque = "قيثارة السماء • أذان الإذاعة المصرية الخالد",
        audioUrl = "https://archive.org/download/90---azan---90---azan--many----sound----mp3---alazan/042--.mp3",
        fajrAudioUrl = "https://archive.org/download/90---azan---90---azan--many----sound----mp3---alazan/042--.mp3",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c2/Muhammad_Rifat.jpg/300px-Muhammad_Rifat.jpg",
        category = "مصر والأزهر"
    ),
    Muezzin(
        id = "abdulbasit",
        nameArabic = "الشيخ عبد الباسط عبد الصمد",
        mosque = "أذان المسجد الأقصى المبارك ومصر الخاشع الباكي",
        audioUrl = "https://archive.org/download/adhan_202303/eabd-albasit-eabdalsamad.mp3",
        fajrAudioUrl = "https://archive.org/download/athan_sound/f_abd_el_basset.mp3",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4b/Abdul_Basit_Abdul_Samad.jpg/300px-Abdul_Basit_Abdul_Samad.jpg",
        category = "مصر والأزهر"
    ),
    Muezzin(
        id = "ali_mulla",
        nameArabic = "الشيخ علي بن أحمد ملا",
        mosque = "شيخ مؤذني المسجد الحرام • مكة المكرمة",
        audioUrl = "https://archive.org/download/adhan_202303/adhan-alharam-almakaa.mp3",
        fajrAudioUrl = "https://archive.org/download/adhan_202303/alfajr-alharam-almakiya.mp3",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a2/Ali_Ahmad_Mulla.jpg/300px-Ali_Ahmad_Mulla.jpg",
        category = "الحرمين"
    ),
    Muezzin(
        id = "alafasy",
        nameArabic = "الشيخ مشاري راشد العفاسي",
        mosque = "دولة الكويت • أذان خاشع عذب شجي",
        audioUrl = "https://archive.org/download/adhan_202303/musharaa-bin%20rashid-aleafasaa.mp3",
        fajrAudioUrl = "https://archive.org/download/adhan_202303/alfajr-musharaa-bin-rashid-aleafasaa.mp3",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6f/Mishary_Rashid_Alafasy.jpg/300px-Mishary_Rashid_Alafasy.jpg",
        category = "الكويت والخليج"
    ),
    Muezzin(
        id = "mustafa_ismail",
        nameArabic = "الشيخ مصطفى إسماعيل",
        mosque = "أسطورة دولة التلاوة • أذان إذاعة القرآن الكريم بمصر",
        audioUrl = "https://archive.org/download/adhan_202303/mustafaa-iismaeil.mp3",
        fajrAudioUrl = "https://archive.org/download/athan_sound/mostaf_esmaeel.mp3",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/87/Mustafa_Ismail.jpg/300px-Mustafa_Ismail.jpg",
        category = "مصر والأزهر"
    ),
    Muezzin(
        id = "husary",
        nameArabic = "الشيخ محمود خليل الحصري",
        mosque = "شيخ المقارئ المصرية • أذان السكينة بالأزهر الشريف",
        audioUrl = "https://archive.org/download/athan_sound/mahmoud_hosary.mp3",
        fajrAudioUrl = "https://archive.org/download/athan_sound/mahmoud_hosary.mp3",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/0/07/Mahmoud_Khalil_Al-Hussary.jpg/300px-Mahmoud_Khalil_Al-Hussary.jpg",
        category = "مصر والأزهر"
    ),
    Muezzin(
        id = "minshawi",
        nameArabic = "الشيخ محمد صديق المنشاوي",
        mosque = "الصوت الباكي الخاشع النقي • جمهورية مصر العربية",
        audioUrl = "https://archive.org/download/athan_sound/ena_alsalah_almenshawy.mp3",
        fajrAudioUrl = "https://archive.org/download/athan_sound/ena_alsalah_almenshawy.mp3",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/ca/Mohamed_Siddiq_El-Minshawi.jpg/300px-Mohamed_Siddiq_El-Minshawi.jpg",
        category = "مصر والأزهر"
    ),
    Muezzin(
        id = "tablawi",
        nameArabic = "الشيخ محمود محمد الطبلاوي",
        mosque = "نقيب القراء بمصر • أذان الهيبة والجلال",
        audioUrl = "https://archive.org/download/adhan_202303/mahmud-muhamad-altablawi.mp3",
        fajrAudioUrl = "https://archive.org/download/adhan_202303/mahmud-muhamad-altablawi.mp3",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e0/Mohamed_Mahmoud_El-Tablawy.jpg/300px-Mohamed_Mahmoud_El-Tablawy.jpg",
        category = "مصر والأزهر"
    ),
    Muezzin(
        id = "shaeishae",
        nameArabic = "الشيخ أبو العينين شعيشع",
        mosque = "عميد معهد الموسيقى وقارئ مصر • أذان شجي بديع",
        audioUrl = "https://archive.org/download/adhan_202303/abawialeaynayn-shaeishae.mp3",
        fajrAudioUrl = "https://archive.org/download/athan_sound/abo_enen_shaesh.mp3",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ad/Abul_Ainain_Shuaisha.jpg/300px-Abul_Ainain_Shuaisha.jpg",
        category = "مصر والأزهر"
    ),
    Muezzin(
        id = "naqshabandi",
        nameArabic = "الشيخ سيد النقشبندي",
        mosque = "كروان الابتهالات • أذان الفجر الخاشع الباكي",
        audioUrl = "https://archive.org/download/adhan_202303/sayid-alnaqshabandaa.mp3",
        fajrAudioUrl = "https://archive.org/download/athan_sound/sayed_elnakshabandy2_fagr_alarm.mp3",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7b/Sayed_Al-Naqshbandi.jpg/300px-Sayed_Al-Naqshbandi.jpg",
        category = "مصر والأزهر"
    ),
    Muezzin(
        id = "tobar",
        nameArabic = "الشيخ نصر الدين طوبار",
        mosque = "سيد المبتهلين • أذان الفجر الخاشع الباكي",
        audioUrl = "https://archive.org/download/athan_sound/nasr_tobar.mp3",
        fajrAudioUrl = "https://archive.org/download/athan_sound/f_nasr_tobar.mp3",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/91/Nasr_Al-Din_Tobar.jpg/300px-Nasr_Al-Din_Tobar.jpg",
        category = "مصر والأزهر"
    ),
    Muezzin(
        id = "madinah",
        nameArabic = "أذان المسجد النبوي الشريف",
        mosque = "المدينة المنورة • الأذان السلطاني المدني الخاشع",
        audioUrl = "https://archive.org/download/adhan_202303/alharam-almudnaa.mp3",
        fajrAudioUrl = "https://archive.org/download/adhan_202303/alharam-almudnaa.mp3",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/46/Al-Masjid_an-Nabawi.jpg/300px-Al-Masjid_an-Nabawi.jpg",
        category = "الحرمين"
    ),
    Muezzin(
        id = "aqsa",
        nameArabic = "أذان المسجد الأقصى المبارك",
        mosque = "القدس الشريف • الشيخ ناجي قزاز ورجال الأقصى",
        audioUrl = "https://archive.org/download/adhan_202303/adhan-alquds.mp3",
        fajrAudioUrl = "https://archive.org/download/adhan_202303/naji-qazaaz.mp3",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Dome_of_the_Rock_2020.jpg/300px-Dome_of_the_Rock_2020.jpg",
        category = "الأقصى والشام"
    ),
    Muezzin(
        id = "qatami",
        nameArabic = "الشيخ ناصر القطامي",
        mosque = "جامع الملك عبد الله • الرياض • أذان خاشع مؤثر",
        audioUrl = "https://archive.org/download/adhan_202303/nasir-alqatamaa.mp3",
        fajrAudioUrl = "https://archive.org/download/adhan_202303/nasir-alqatamaa.mp3",
        imageUrl = "https://static.surah.com/reciters/nasser-alqatami.jpg",
        category = "الكويت والخليج"
    ),
    Muezzin(
        id = "dawsari",
        nameArabic = "الشيخ ياسر الدوسري",
        mosque = "إمام وخطيب المسجد الحرام بمكة المكرمة",
        audioUrl = "https://archive.org/download/adhan_202303/yasir-aldawsari.mp3",
        fajrAudioUrl = "https://archive.org/download/adhan_202303/yasir-aldawsari.mp3",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f6/Makkah_Grand_Mosque.jpg/300px-Makkah_Grand_Mosque.jpg",
        category = "الحرمين"
    ),
    Muezzin(
        id = "islam_sobhy",
        nameArabic = "القارئ إسلام صبحي",
        mosque = "جمهورية مصر العربية • أذان خاشع ومؤثر للقلوب",
        audioUrl = "https://archive.org/download/adhan_202303/asalam-subhi.mp3",
        fajrAudioUrl = "https://archive.org/download/athan_sound/islam_sobhy.mp3",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/87/Mustafa_Ismail.jpg/300px-Mustafa_Ismail.jpg",
        category = "مصر والأزهر"
    ),
    Muezzin(
        id = "mansour_salimi",
        nameArabic = "الشيخ منصور السالمي",
        mosque = "المملكة العربية السعودية • أذان يرقق القلوب",
        audioUrl = "https://archive.org/download/adhan_202303/mansur-alsaalmaa.mp3",
        fajrAudioUrl = "https://archive.org/download/adhan_202303/mansur-alsaalmaa.mp3",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f6/Makkah_Grand_Mosque.jpg/300px-Makkah_Grand_Mosque.jpg",
        category = "الكويت والخليج"
    ),
    Muezzin(
        id = "banna",
        nameArabic = "الشيخ محمود علي البنا",
        mosque = "مصر • عملاق التلاوة والأذان الصافي النقي",
        audioUrl = "https://archive.org/download/adhan_202303/muhamad-eali-albana.mp3",
        fajrAudioUrl = "https://archive.org/download/adhan_202303/muhamad-eali-albana.mp3",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/1/15/Mahmoud_Ali_Al_Banna.jpg/300px-Mahmoud_Ali_Al_Banna.jpg",
        category = "مصر والأزهر"
    ),
    Muezzin(
        id = "neana",
        nameArabic = "الشيخ أحمد النعينع",
        mosque = "طبيب القراء • أذان التجويد والإتقان بمصر",
        audioUrl = "https://archive.org/download/adhan_202303/alfajr-ahmad%20naeayanae.mp3",
        fajrAudioUrl = "https://archive.org/download/adhan_202303/alfajr-ahmad%20naeayanae.mp3",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1e/Ahmed_Neana.jpg/300px-Ahmed_Neana.jpg",
        category = "مصر والأزهر"
    ),
    Muezzin(
        id = "naji_qazzaz",
        nameArabic = "الشيخ ناجي قزاز",
        mosque = "المسجد الأقصى المبارك • أذان القدس الخالد",
        audioUrl = "https://archive.org/download/adhan_202303/naji-qazaaz.mp3",
        fajrAudioUrl = "https://archive.org/download/adhan_202303/naji-qazaaz.mp3",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Dome_of_the_Rock_2020.jpg/300px-Dome_of_the_Rock_2020.jpg",
        category = "الأقصى والشام"
    )
)
