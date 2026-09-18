package com.example.prayer

import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.math.PI
import kotlin.math.acos
import kotlin.math.asin
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

enum class PrayerMethod(val arabicName: String, val fajrAngle: Double, val ishaAngle: Double) {
    EGYPTIAN("الهيئة المصرية العامة للمساحة", 19.5, 17.5),
    UMM_AL_QURA("جامعة أم القرى - مكة المكرمة", 18.5, 0.0), // Isha is 90 mins after Maghrib
    MUSLIM_WORLD_LEAGUE("رابطة العالم الإسلامي", 18.0, 17.0),
    ISNA("الجمعية الإسلامية لأمريكا الشمالية (ISNA)", 15.0, 15.0),
    KARACHI("جامعة العلوم الإسلامية بكراتشي", 18.0, 18.0)
}

enum class JuristicMethod(val arabicName: String, val shadowFactor: Double) {
    SHAFI_HANBALI_MALIKI("الجمهور: الشافعي والمالكي والحنبلي", 1.0),
    HANAFI("المذهب الحنفي", 2.0)
}

data class PrayerOffsets(
    val fajr: Int = 0,
    val sunrise: Int = 0,
    val dhuhr: Int = 0,
    val asr: Int = 0,
    val maghrib: Int = 0,
    val isha: Int = 0
)

data class PrayerCity(
    val id: String,
    val nameArabic: String,
    val countryArabic: String,
    val latitude: Double,
    val longitude: Double,
    val defaultTimeZone: Double,
    val recommendedMethod: PrayerMethod,
    val isGpsDetected: Boolean = false,
    val timeZoneId: String? = null
)

data class PrayerTimes(
    val cityName: String,
    val countryName: String,
    val fajr: String,
    val sunrise: String,
    val dhuhr: String,
    val asr: String,
    val maghrib: String,
    val isha: String,
    val nextPrayerName: String,
    val timeRemainingNextPrayer: String,
    val qiblaDirectionDeg: Float,
    val distanceToKaabaKm: Int,
    val isGpsDetected: Boolean = false,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val nextPrayerTime: String = "",
    val fajr12: String = "",
    val sunrise12: String = "",
    val dhuhr12: String = "",
    val asr12: String = "",
    val maghrib12: String = "",
    val isha12: String = ""
)

object PrayerCalculator {

    const val KAABA_LAT = 21.4225
    const val KAABA_LNG = 39.8262

    val CITIES = listOf(
        // Egypt (كافة محافظات ومدن جمهورية مصر العربية بتوقيت دقيق)
        PrayerCity("cairo", "القاهرة", "مصر", 30.0444, 31.2357, 2.0, PrayerMethod.EGYPTIAN, timeZoneId = "Africa/Cairo"),
        PrayerCity("alexandria", "الإسكندرية", "مصر", 31.2001, 29.9187, 2.0, PrayerMethod.EGYPTIAN, timeZoneId = "Africa/Cairo"),
        PrayerCity("giza", "الجيزة", "مصر", 30.0131, 31.2089, 2.0, PrayerMethod.EGYPTIAN, timeZoneId = "Africa/Cairo"),
        PrayerCity("mansoura", "المنصورة (الدقهلية)", "مصر", 31.0409, 31.3785, 2.0, PrayerMethod.EGYPTIAN, timeZoneId = "Africa/Cairo"),
        PrayerCity("tanta", "طنطا (الغربية)", "مصر", 30.7865, 31.0004, 2.0, PrayerMethod.EGYPTIAN, timeZoneId = "Africa/Cairo"),
        PrayerCity("mahalla", "المحلة الكبرى", "مصر", 30.9706, 31.1669, 2.0, PrayerMethod.EGYPTIAN, timeZoneId = "Africa/Cairo"),
        PrayerCity("zagazig", "الزقازيق (الشرقية)", "مصر", 30.5877, 31.5020, 2.0, PrayerMethod.EGYPTIAN, timeZoneId = "Africa/Cairo"),
        PrayerCity("ismailia", "الإسماعيلية", "مصر", 30.5965, 32.2715, 2.0, PrayerMethod.EGYPTIAN, timeZoneId = "Africa/Cairo"),
        PrayerCity("port_said", "بورسعيد", "مصر", 31.2653, 32.3019, 2.0, PrayerMethod.EGYPTIAN, timeZoneId = "Africa/Cairo"),
        PrayerCity("suez", "السويس", "مصر", 29.9668, 32.5498, 2.0, PrayerMethod.EGYPTIAN, timeZoneId = "Africa/Cairo"),
        PrayerCity("damietta", "دمياط", "مصر", 31.4175, 31.8144, 2.0, PrayerMethod.EGYPTIAN, timeZoneId = "Africa/Cairo"),
        PrayerCity("kafr_el_sheikh", "كفر الشيخ", "مصر", 31.1107, 30.9388, 2.0, PrayerMethod.EGYPTIAN, timeZoneId = "Africa/Cairo"),
        PrayerCity("damanhour", "دمنهور (البحيرة)", "مصر", 31.0409, 30.4700, 2.0, PrayerMethod.EGYPTIAN, timeZoneId = "Africa/Cairo"),
        PrayerCity("shebin_el_kom", "شبين الكوم (المنوفية)", "مصر", 30.5526, 31.0090, 2.0, PrayerMethod.EGYPTIAN, timeZoneId = "Africa/Cairo"),
        PrayerCity("banha", "بنها (القليوبية)", "مصر", 30.4660, 31.1853, 2.0, PrayerMethod.EGYPTIAN, timeZoneId = "Africa/Cairo"),
        PrayerCity("fayoum", "الفيوم", "مصر", 29.3084, 30.8428, 2.0, PrayerMethod.EGYPTIAN, timeZoneId = "Africa/Cairo"),
        PrayerCity("beni_suef", "بني سويف", "مصر", 29.0661, 31.0994, 2.0, PrayerMethod.EGYPTIAN, timeZoneId = "Africa/Cairo"),
        PrayerCity("minya", "المنيا", "مصر", 28.1099, 30.7503, 2.0, PrayerMethod.EGYPTIAN, timeZoneId = "Africa/Cairo"),
        PrayerCity("asyut", "أسيوط", "مصر", 27.1783, 31.1859, 2.0, PrayerMethod.EGYPTIAN, timeZoneId = "Africa/Cairo"),
        PrayerCity("sohag", "سوهاج", "مصر", 26.5569, 31.6948, 2.0, PrayerMethod.EGYPTIAN, timeZoneId = "Africa/Cairo"),
        PrayerCity("qena", "قنا", "مصر", 26.1551, 32.7160, 2.0, PrayerMethod.EGYPTIAN, timeZoneId = "Africa/Cairo"),
        PrayerCity("luxor", "الأقصر", "مصر", 25.6872, 32.6396, 2.0, PrayerMethod.EGYPTIAN, timeZoneId = "Africa/Cairo"),
        PrayerCity("aswan", "أسوان", "مصر", 24.0889, 32.8998, 2.0, PrayerMethod.EGYPTIAN, timeZoneId = "Africa/Cairo"),
        PrayerCity("hurghada", "الغردقة (البحر الأحمر)", "مصر", 27.2579, 33.8116, 2.0, PrayerMethod.EGYPTIAN, timeZoneId = "Africa/Cairo"),
        PrayerCity("sharm_el_sheikh", "شرم الشيخ (جنوب سيناء)", "مصر", 27.9158, 34.3299, 2.0, PrayerMethod.EGYPTIAN, timeZoneId = "Africa/Cairo"),
        PrayerCity("arish", "العريش (شمال سيناء)", "مصر", 31.1321, 33.7984, 2.0, PrayerMethod.EGYPTIAN, timeZoneId = "Africa/Cairo"),
        PrayerCity("matrouh", "مرسى مطروح", "مصر", 31.3543, 27.2373, 2.0, PrayerMethod.EGYPTIAN, timeZoneId = "Africa/Cairo"),
        PrayerCity("new_valley", "الخارجة (الوادي الجديد)", "مصر", 25.4514, 30.5464, 2.0, PrayerMethod.EGYPTIAN, timeZoneId = "Africa/Cairo"),

        // Saudi Arabia (المملكة العربية السعودية)
        PrayerCity("makkah", "مكة المكرمة", "السعودية", 21.4225, 39.8262, 3.0, PrayerMethod.UMM_AL_QURA, timeZoneId = "Asia/Riyadh"),
        PrayerCity("madinah", "المدينة المنورة", "السعودية", 24.5247, 39.5692, 3.0, PrayerMethod.UMM_AL_QURA, timeZoneId = "Asia/Riyadh"),
        PrayerCity("riyadh", "الرياض", "السعودية", 24.7136, 46.6753, 3.0, PrayerMethod.UMM_AL_QURA, timeZoneId = "Asia/Riyadh"),
        PrayerCity("jeddah", "جدة", "السعودية", 21.4858, 39.1925, 3.0, PrayerMethod.UMM_AL_QURA, timeZoneId = "Asia/Riyadh"),
        PrayerCity("dammam", "الدمام", "السعودية", 26.4207, 50.0888, 3.0, PrayerMethod.UMM_AL_QURA, timeZoneId = "Asia/Riyadh"),
        PrayerCity("abha", "أبها", "السعودية", 18.2164, 42.5053, 3.0, PrayerMethod.UMM_AL_QURA, timeZoneId = "Asia/Riyadh"),
        PrayerCity("tabuk", "تبوك", "السعودية", 28.3835, 36.5662, 3.0, PrayerMethod.UMM_AL_QURA, timeZoneId = "Asia/Riyadh"),
        PrayerCity("taif", "الطائف", "السعودية", 21.2854, 40.4222, 3.0, PrayerMethod.UMM_AL_QURA, timeZoneId = "Asia/Riyadh"),

        // UAE (الإمارات)
        PrayerCity("abu_dhabi", "أبو ظبي", "الإمارات", 24.4539, 54.3773, 4.0, PrayerMethod.UMM_AL_QURA, timeZoneId = "Asia/Dubai"),
        PrayerCity("dubai", "دبي", "الإمارات", 25.2048, 55.2708, 4.0, PrayerMethod.UMM_AL_QURA, timeZoneId = "Asia/Dubai"),
        PrayerCity("sharjah", "الشارقة", "الإمارات", 25.3573, 55.4033, 4.0, PrayerMethod.UMM_AL_QURA, timeZoneId = "Asia/Dubai"),

        // Levant & Iraq (بلاد الشام والعراق)
        PrayerCity("jerusalem", "القدس الشريف", "فلسطين", 31.7683, 35.2137, 2.0, PrayerMethod.MUSLIM_WORLD_LEAGUE, timeZoneId = "Asia/Jerusalem"),
        PrayerCity("gaza", "غزة", "فلسطين", 31.5017, 34.4668, 2.0, PrayerMethod.MUSLIM_WORLD_LEAGUE, timeZoneId = "Asia/Gaza"),
        PrayerCity("amman", "عمّان", "الأردن", 31.9454, 35.9284, 3.0, PrayerMethod.MUSLIM_WORLD_LEAGUE, timeZoneId = "Asia/Amman"),
        PrayerCity("damascus", "دمشق", "سوريا", 33.5138, 36.2765, 3.0, PrayerMethod.MUSLIM_WORLD_LEAGUE, timeZoneId = "Asia/Damascus"),
        PrayerCity("beirut", "بيروت", "لبنان", 33.8938, 35.5018, 2.0, PrayerMethod.MUSLIM_WORLD_LEAGUE, timeZoneId = "Asia/Beirut"),
        PrayerCity("baghdad", "بغداد", "العراق", 33.3152, 44.3661, 3.0, PrayerMethod.MUSLIM_WORLD_LEAGUE, timeZoneId = "Asia/Baghdad"),
        PrayerCity("basra", "البصرة", "العراق", 30.5085, 47.7804, 3.0, PrayerMethod.MUSLIM_WORLD_LEAGUE, timeZoneId = "Asia/Baghdad"),
        PrayerCity("erbil", "أربيل", "العراق", 36.1901, 44.0091, 3.0, PrayerMethod.MUSLIM_WORLD_LEAGUE, timeZoneId = "Asia/Baghdad"),

        // Gulf (الخليج العربي)
        PrayerCity("kuwait", "مدينة الكويت", "الكويت", 29.3759, 47.9774, 3.0, PrayerMethod.UMM_AL_QURA, timeZoneId = "Asia/Kuwait"),
        PrayerCity("doha", "الدوحة", "قطر", 25.2854, 51.5310, 3.0, PrayerMethod.UMM_AL_QURA, timeZoneId = "Asia/Qatar"),
        PrayerCity("manama", "المنامة", "البحرين", 26.2285, 50.5860, 3.0, PrayerMethod.UMM_AL_QURA, timeZoneId = "Asia/Bahrain"),
        PrayerCity("muscat", "مسقط", "عمان", 23.5880, 58.3829, 4.0, PrayerMethod.UMM_AL_QURA, timeZoneId = "Asia/Muscat"),
        PrayerCity("sanaa", "صنعاء", "اليمن", 15.3694, 44.1910, 3.0, PrayerMethod.UMM_AL_QURA, timeZoneId = "Asia/Aden"),

        // North Africa & Sudan (شمال أفريقيا والسودان)
        PrayerCity("khartoum", "الخرطوم", "السودان", 15.5007, 32.5599, 2.0, PrayerMethod.EGYPTIAN, timeZoneId = "Africa/Khartoum"),
        PrayerCity("tripoli", "طرابلس", "ليبيا", 32.8872, 13.1913, 2.0, PrayerMethod.MUSLIM_WORLD_LEAGUE, timeZoneId = "Africa/Tripoli"),
        PrayerCity("tunis", "تونس العاصمة", "تونس", 36.8065, 10.1815, 1.0, PrayerMethod.MUSLIM_WORLD_LEAGUE, timeZoneId = "Africa/Tunis"),
        PrayerCity("algiers", "الجزائر العاصمة", "الجزائر", 36.7538, 3.0588, 1.0, PrayerMethod.MUSLIM_WORLD_LEAGUE, timeZoneId = "Africa/Algiers"),
        PrayerCity("rabat", "الرباط", "المغرب", 34.0209, -6.8416, 1.0, PrayerMethod.MUSLIM_WORLD_LEAGUE, timeZoneId = "Africa/Casablanca"),
        PrayerCity("casablanca", "الدار البيضاء", "المغرب", 33.5731, -7.5898, 1.0, PrayerMethod.MUSLIM_WORLD_LEAGUE, timeZoneId = "Africa/Casablanca"),

        // International (عالمياً)
        PrayerCity("istanbul", "إسطنبول", "تركيا", 41.0082, 28.9784, 3.0, PrayerMethod.MUSLIM_WORLD_LEAGUE, timeZoneId = "Europe/Istanbul"),
        PrayerCity("london", "لندن", "المملكة المتحدة", 51.5074, -0.1278, 0.0, PrayerMethod.MUSLIM_WORLD_LEAGUE, timeZoneId = "Europe/London"),
        PrayerCity("paris", "باريس", "فرنسا", 48.8566, 2.3522, 1.0, PrayerMethod.MUSLIM_WORLD_LEAGUE, timeZoneId = "Europe/Paris"),
        PrayerCity("berlin", "برلين", "ألمانيا", 52.5200, 13.4050, 1.0, PrayerMethod.MUSLIM_WORLD_LEAGUE, timeZoneId = "Europe/Berlin"),
        PrayerCity("new_york", "نيويورك", "الولايات المتحدة", 40.7128, -74.0060, -5.0, PrayerMethod.ISNA, timeZoneId = "America/New_York")
    )

    val DEFAULT_CITY = CITIES.first() // Cairo (القاهرة)

    /**
     * Calculates True Qibla direction in degrees clockwise from True North (0..360)
     */
    fun calculateQiblaDirection(userLat: Double, userLng: Double): Float {
        val phi1 = Math.toRadians(userLat)
        val phi2 = Math.toRadians(KAABA_LAT)
        val deltaLambda = Math.toRadians(KAABA_LNG - userLng)

        val y = sin(deltaLambda) * cos(phi2)
        val x = cos(phi1) * sin(phi2) - sin(phi1) * cos(phi2) * cos(deltaLambda)

        var qibla = Math.toDegrees(atan2(y, x))
        qibla = (qibla + 360.0) % 360.0
        return qibla.toFloat()
    }

    /**
     * Calculates Great Circle distance to the Kaaba in kilometers.
     */
    fun calculateDistanceToKaaba(userLat: Double, userLng: Double): Int {
        val earthRadiusKm = 6371.0
        val dLat = Math.toRadians(KAABA_LAT - userLat)
        val dLng = Math.toRadians(KAABA_LNG - userLng)
        val a = sin(dLat / 2).pow(2) +
                cos(Math.toRadians(userLat)) * cos(Math.toRadians(KAABA_LAT)) * sin(dLng / 2).pow(2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return (earthRadiusKm * c).toInt()
    }

    /**
     * Calculates exact Prayer times for given city/location, date, and methods.
     */
    fun calculatePrayerTimes(
        city: PrayerCity = DEFAULT_CITY,
        method: PrayerMethod? = null,
        juristicMethod: JuristicMethod = JuristicMethod.SHAFI_HANBALI_MALIKI,
        date: Date = Date(),
        offsets: PrayerOffsets = PrayerOffsets()
    ): PrayerTimes {
        val activeMethod = method ?: city.recommendedMethod

        // Accurate Timezone Offset handling
        val tzOffset: Double = when {
            city.isGpsDetected -> {
                TimeZone.getDefault().getOffset(date.time) / 3600000.0
            }
            !city.timeZoneId.isNullOrBlank() -> {
                try {
                    val cityTz = TimeZone.getTimeZone(city.timeZoneId)
                    cityTz.getOffset(date.time) / 3600000.0
                } catch (_: Exception) {
                    city.defaultTimeZone
                }
            }
            else -> {
                val devOffset = TimeZone.getDefault().getOffset(date.time) / 3600000.0
                if (kotlin.math.abs(devOffset - city.defaultTimeZone) <= 1.0) devOffset else city.defaultTimeZone
            }
        }

        val cal = Calendar.getInstance().apply { time = date }
        val dayOfYear = cal.get(Calendar.DAY_OF_YEAR)

        // Solar declination approximation
        val b = 2 * PI * (dayOfYear - 81) / 365.0
        val declination = Math.toRadians(23.45 * sin(b))

        // Equation of time in minutes
        val eot = 9.87 * sin(2 * b) - 7.53 * cos(b) - 1.5 * sin(b)

        // Solar transit (astronomical solar noon) in local hours
        val transit = 12.0 + tzOffset - (city.longitude / 15.0) - (eot / 60.0)

        val latRad = Math.toRadians(city.latitude)

        // Hour angle helper
        fun hourAngle(altitudeDeg: Double): Double {
            val altRad = Math.toRadians(altitudeDeg)
            val cosH = (sin(altRad) - sin(latRad) * sin(declination)) / (cos(latRad) * cos(declination))
            return if (cosH < -1.0 || cosH > 1.0) 0.0 else Math.toDegrees(acos(cosH)) / 15.0
        }

        // Sunrise & Sunset
        val sunriseH = hourAngle(-0.833)
        val sunriseHours = transit - sunriseH + (offsets.sunrise / 60.0)
        val sunsetHours = transit + sunriseH

        // Dhuhr (+ 2 minutes precaution for Zawal)
        val dhuhrHours = transit + (2.0 / 60.0) + (offsets.dhuhr / 60.0)

        // Fajr
        val fajrH = hourAngle(-activeMethod.fajrAngle)
        val fajrHours = transit - fajrH + (offsets.fajr / 60.0)

        // Asr calculation
        val shadowRatio = juristicMethod.shadowFactor
        val asrAlt = Math.toDegrees(atan2(1.0, shadowRatio + tan(kotlin.math.abs(latRad - declination))))
        val asrH = hourAngle(asrAlt)
        val asrHours = transit + asrH + (offsets.asr / 60.0)

        // Maghrib
        val maghribHours = sunsetHours + (offsets.maghrib / 60.0)

        // Isha
        val ishaHours = if (activeMethod == PrayerMethod.UMM_AL_QURA) {
            maghribHours + 1.5 + (offsets.isha / 60.0)
        } else {
            val ishaH = hourAngle(-activeMethod.ishaAngle)
            transit + ishaH + (offsets.isha / 60.0)
        }

        val qibla = calculateQiblaDirection(city.latitude, city.longitude)
        val distance = calculateDistanceToKaaba(city.latitude, city.longitude)

        val fajrStr = formatTime(fajrHours)
        val sunriseStr = formatTime(sunriseHours)
        val dhuhrStr = formatTime(dhuhrHours)
        val asrStr = formatTime(asrHours)
        val maghribStr = formatTime(maghribHours)
        val ishaStr = formatTime(ishaHours)

        val fajr12 = formatTime12(fajrHours)
        val sunrise12 = formatTime12(sunriseHours)
        val dhuhr12 = formatTime12(dhuhrHours)
        val asr12 = formatTime12(asrHours)
        val maghrib12 = formatTime12(maghribHours)
        val isha12 = formatTime12(ishaHours)

        // Determine next prayer and time remaining
        val currentHourDecimal = cal.get(Calendar.HOUR_OF_DAY) +
                cal.get(Calendar.MINUTE) / 60.0 +
                cal.get(Calendar.SECOND) / 3600.0

        val (nextName, nextTimeHours) = when {
            currentHourDecimal < fajrHours -> "الفجر" to fajrHours
            currentHourDecimal < sunriseHours -> "الشروق" to sunriseHours
            currentHourDecimal < dhuhrHours -> "الظهر" to dhuhrHours
            currentHourDecimal < asrHours -> "العصر" to asrHours
            currentHourDecimal < maghribHours -> "المغرب" to maghribHours
            currentHourDecimal < ishaHours -> "العشاء" to ishaHours
            else -> "الفجر (غداً)" to (fajrHours + 24.0)
        }

        val diffHours = nextTimeHours - currentHourDecimal
        val diffMinutesTotal = kotlin.math.max(0, (diffHours * 60).toInt())
        val h = diffMinutesTotal / 60
        val m = diffMinutesTotal % 60
        val remainingStr = "${h.toString().padStart(2, '0')}:${m.toString().padStart(2, '0')}"

        val nextTime12Str = formatTime12(nextTimeHours % 24.0)

        return PrayerTimes(
            cityName = city.nameArabic,
            countryName = city.countryArabic,
            fajr = fajrStr,
            sunrise = sunriseStr,
            dhuhr = dhuhrStr,
            asr = asrStr,
            maghrib = maghribStr,
            isha = ishaStr,
            nextPrayerName = nextName,
            timeRemainingNextPrayer = remainingStr,
            qiblaDirectionDeg = qibla,
            distanceToKaabaKm = distance,
            isGpsDetected = city.isGpsDetected,
            latitude = city.latitude,
            longitude = city.longitude,
            nextPrayerTime = nextTime12Str,
            fajr12 = fajr12,
            sunrise12 = sunrise12,
            dhuhr12 = dhuhr12,
            asr12 = asr12,
            maghrib12 = maghrib12,
            isha12 = isha12
        )
    }

    private fun formatTime(hoursDecimal: Double): String {
        val totalMinutes = Math.round(hoursDecimal * 60).toInt()
        val normalizedMinutes = ((totalMinutes % 1440) + 1440) % 1440
        val h = normalizedMinutes / 60
        val m = normalizedMinutes % 60
        return "${h.toString().padStart(2, '0')}:${m.toString().padStart(2, '0')}"
    }

    fun formatTime12(hoursDecimal: Double): String {
        val totalMinutes = Math.round(hoursDecimal * 60).toInt()
        val normalizedMinutes = ((totalMinutes % 1440) + 1440) % 1440
        val h24 = normalizedMinutes / 60
        val m = normalizedMinutes % 60
        val period = if (h24 < 12) "ص" else "م"
        val h12 = when {
            h24 == 0 -> 12
            h24 > 12 -> h24 - 12
            else -> h24
        }
        return "${h12.toString().padStart(2, '0')}:${m.toString().padStart(2, '0')} $period"
    }
}
