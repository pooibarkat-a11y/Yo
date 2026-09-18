package com.example.data

data class DailyWorshipTask(
    val id: String,
    val title: String,
    val description: String,
    val virtue: String,
    val category: String, // صلاة، قرآن، أذكار، إحسان
    var isCompleted: Boolean = false
)

data class DailyAyahReflection(
    val surahName: String,
    val ayahNumber: Int,
    val ayahText: String,
    val reflection: String,
    val practicalStep: String
)

data class DailyDua(
    val title: String,
    val duaText: String,
    val source: String,
    val virtue: String,
    val bestTime: String
)

object DailyTasksData {
    val defaultTasks: List<DailyWorshipTask> = listOf(
        DailyWorshipTask(
            id = "fajr_prayer",
            title = "صلاة الفجر في وقتها",
            description = "أداء صلاة الفجر في وقتها ومع الجماعة للرجال.",
            virtue = "من صلى الصبح فهو في ذمة الله.",
            category = "صلاة"
        ),
        DailyWorshipTask(
            id = "fajr_sunnah",
            title = "ركعتا سنة الفجر",
            description = "ركعتان خفيفتان قبل فريضة الصبح.",
            virtue = "ركعتا الفجر خير من الدنيا وما فيها.",
            category = "صلاة"
        ),
        DailyWorshipTask(
            id = "morning_azkar",
            title = "أذكار الصباح كاملة",
            description = "قراءة الحصن اليومي من بعد صلاة الفجر حتى الشروق.",
            virtue = "حفظ ورعاية وبركة طوال اليوم ودفع للشياطين.",
            category = "أذكار"
        ),
        DailyWorshipTask(
            id = "duha_prayer",
            title = "صلاة الضحى (ركعتان أو أربع)",
            description = "صلاة الأوابين بعد شروق الشمس بثلث ساعة حتى قبل الظهر.",
            virtue = "تجزئ عن ٣٦٠ صدقة عن كل مفصل في جسدك.",
            category = "صلاة"
        ),
        DailyWorshipTask(
            id = "quran_wird",
            title = "ورد القرآن اليومي (حزب أو جزء)",
            description = "قراءة وتدبر وردك اليومي الثابت من المصحف.",
            virtue = "يقال لقارئ القرآن: اقرأ وارتق ورتل كما كنت ترتل.",
            category = "قرآن"
        ),
        DailyWorshipTask(
            id = "dhuhr_prayer",
            title = "صلاة الظهر وسننها الرواتب",
            description = "أداء الظهر مع ٤ ركعات قبلها وركعتين بعدها.",
            virtue = "من صلى اثنتي عشرة ركعة تطوعاً بُني له بيت في الجنة.",
            category = "صلاة"
        ),
        DailyWorshipTask(
            id = "asr_prayer",
            title = "صلاة العصر في أول وقتها",
            description = "المحافظة على الصلاة الوسطى دون تأخير.",
            virtue = "من صلى البردين (الصبح والعصر) دخل الجنة.",
            category = "صلاة"
        ),
        DailyWorshipTask(
            id = "evening_azkar",
            title = "أذكار المساء كاملة",
            description = "قراءة أذكار المساء بعد العصر حتى غروب الشمس.",
            virtue = "حرز وحماية تامة للعبد في ليلته حتى يصبح.",
            category = "أذكار"
        ),
        DailyWorshipTask(
            id = "maghrib_prayer",
            title = "صلاة المغرب وركعتا السنة",
            description = "أداء المغرب في وقتها مع ركعتين بعدها.",
            virtue = "استجابة الدعاء بين الأذان والإقامة وقضاء الحوائج.",
            category = "صلاة"
        ),
        DailyWorshipTask(
            id = "isha_witr",
            title = "صلاة العشاء وسنتها وصلاة الوتر",
            description = "أداء العشاء وسنتها ثم ختم اليوم بصلاة الوتر.",
            virtue = "إن الله وتر يحب الوتر، فأوتروا يا أهل القرآن.",
            category = "صلاة"
        ),
        DailyWorshipTask(
            id = "tasbih_istighfar",
            title = "الاستغفار والتسبيح ١٠٠ مرة",
            description = "أستغفر الله وأتوب إليه (١٠٠) وسبحان الله وبحمده (١٠٠).",
            virtue = "حُطت خطاياه وإن كانت مثل زبد البحر ويفتح أبواب الرزق.",
            category = "أذكار"
        ),
        DailyWorshipTask(
            id = "salat_ala_nabi",
            title = "الصلاة على النبي ﷺ ١٠٠ مرة",
            description = "اللهم صل وسلم وبارك على سيدنا محمد.",
            virtue = "من صلى عليَّ صلاة واحدة صلى الله عليه بها عشراً وكُفي همه.",
            category = "أذكار"
        ),
        DailyWorshipTask(
            id = "daily_sadaqah",
            title = "صدقة اليوم أو تفريج كربة",
            description = "التصدق بمال، إطعام طعام، أو تبسم وإعانة محتاج.",
            virtue = "صنائع المعروف تقي مصارع السوء، والصدقة تطفئ غضب الرب.",
            category = "إحسان"
        ),
        DailyWorshipTask(
            id = "qiyam_layl",
            title = "قيام الليل ولو بركعتين",
            description = "الوقوف بين يدي الله في الثلث الأخير من الليل.",
            virtue = "شرف المؤمن قيام الليل، ودعاء الثلث الأخير مستجاب.",
            category = "صلاة"
        )
    )

    val todayAyah: DailyAyahReflection = DailyAyahReflection(
        surahName = "سورة الشرح",
        ayahNumber = 6,
        ayahText = "إِنَّ مَعَ الْعُسْرِ يُسْرًا",
        reflection = "كرر الله تعالى اليسر معرفاً والعسر منكراً، ليدل على أن العسر الواحد يصحبه يسران عظيمان من فضل الله ورحمته، فلا تيأس مهما اشتدت الكروب.",
        practicalStep = "إذا أحسست بضيق اليوم، فاستحضر أن اليسر قادم حتماً، وأكثر من قول: 'لا حول ولا قوة إلا بالله'."
    )

    val todayDua: DailyDua = DailyDua(
        title = "دعوة اليوم: تفريج الهموم وجلب الرزق والتيسير",
        duaText = "اللَّهُمَّ إِنِّي أَعُوذُ بِكَ مِنَ الْهَمِّ وَالْحَزَنِ، وَأَعُوذُ بِكَ مِنَ الْعَجْزِ وَالْكَسَلِ، وَأَعُوذُ بِكَ مِنَ الْجُبْنِ وَالْبُخْلِ، وَأَعُوذُ بِكَ مِنْ غَلَبَةِ الدَّيْنِ وَقَهْرِ الرِّجَالِ.",
        source = "صحيح البخاري • عن أنس بن مالك رضي الله عنه",
        virtue = "دعاء شامل كان يكثر منه النبي ﷺ، يزيل الله به الكرب ويقضي به الديون مهما عظمت.",
        bestTime = "دبر كل صلاة، وبين الأذان والإقامة، وفي سجودك."
    )

    fun getCompletedTaskIds(context: android.content.Context): Set<String> {
        val prefs = context.getSharedPreferences("daily_worship_tasks_prefs", android.content.Context.MODE_PRIVATE)
        val csv = prefs.getString("completed_task_ids_csv", null)
        if (csv != null) {
            return if (csv.isBlank()) emptySet() else csv.split("|").filter { it.isNotBlank() }.toSet()
        }
        return prefs.getStringSet("completed_task_ids", emptySet()) ?: emptySet()
    }

    fun setTaskCompleted(context: android.content.Context, taskId: String, completed: Boolean) {
        val prefs = context.getSharedPreferences("daily_worship_tasks_prefs", android.content.Context.MODE_PRIVATE)
        val current = getCompletedTaskIds(context).toMutableSet()
        if (completed) {
            current.add(taskId)
        } else {
            current.remove(taskId)
        }
        val csv = current.joinToString("|")
        prefs.edit()
            .putString("completed_task_ids_csv", csv)
            .putStringSet("completed_task_ids", java.util.HashSet(current))
            .commit()
    }

    fun clearAllCompletedTasks(context: android.content.Context) {
        val prefs = context.getSharedPreferences("daily_worship_tasks_prefs", android.content.Context.MODE_PRIVATE)
        prefs.edit()
            .remove("completed_task_ids_csv")
            .remove("completed_task_ids")
            .commit()
    }
}
