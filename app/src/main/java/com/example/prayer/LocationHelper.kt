package com.example.prayer

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat
import java.util.Locale
import java.util.TimeZone

object LocationHelper {

    private const val TAG = "LocationHelper"
    private const val PREFS_NAME = "location_helper_prefs"
    private const val KEY_LAST_GPS_CITY = "last_gps_city_json"

    fun hasLocationPermission(context: Context): Boolean {
        val fine = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val coarse = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        return fine || coarse
    }

    @SuppressLint("MissingPermission")
    fun detectLocation(context: Context, onComplete: (PrayerCity?) -> Unit) {
        if (!hasLocationPermission(context)) {
            Log.w(TAG, "Location permission not granted")
            onComplete(null)
            return
        }

        val lm = context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
        if (lm == null) {
            onComplete(null)
            return
        }

        // Try getting last known location first from GPS or Network
        var bestLocation: Location? = null
        try {
            val gpsLoc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            val netLoc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            val passiveLoc = lm.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)

            bestLocation = when {
                gpsLoc != null && netLoc != null -> if (gpsLoc.time > netLoc.time) gpsLoc else netLoc
                gpsLoc != null -> gpsLoc
                netLoc != null -> netLoc
                else -> passiveLoc
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching last known location", e)
        }

        if (bestLocation != null) {
            val city = buildPrayerCityFromLocation(context, bestLocation)
            onComplete(city)
            return
        }

        // If no last known location, request a single update with timeout
        try {
            val handler = Handler(Looper.getMainLooper())
            var hasResponded = false

            val listener = object : LocationListener {
                override fun onLocationChanged(loc: Location) {
                    if (!hasResponded) {
                        hasResponded = true
                        try {
                            lm.removeUpdates(this)
                        } catch (_: Exception) {}
                        val city = buildPrayerCityFromLocation(context, loc)
                        onComplete(city)
                    }
                }
                override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
                override fun onProviderEnabled(provider: String) {}
                override fun onProviderDisabled(provider: String) {}
            }

            // Timeout after 7 seconds
            handler.postDelayed({
                if (!hasResponded) {
                    hasResponded = true
                    try {
                        lm.removeUpdates(listener)
                    } catch (_: Exception) {}
                    // Fallback to closest city or default
                    onComplete(PrayerCalculator.DEFAULT_CITY)
                }
            }, 7000L)

            val provider = when {
                lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER) -> LocationManager.NETWORK_PROVIDER
                lm.isProviderEnabled(LocationManager.GPS_PROVIDER) -> LocationManager.GPS_PROVIDER
                else -> LocationManager.PASSIVE_PROVIDER
            }

            lm.requestSingleUpdate(provider, listener, Looper.getMainLooper())
        } catch (e: Exception) {
            Log.e(TAG, "Error requesting single location update", e)
            onComplete(null)
        }
    }

    fun buildPrayerCityFromLocation(context: Context, location: Location): PrayerCity {
        val lat = location.latitude
        val lng = location.longitude

        var cityNameArabic = ""
        var countryNameArabic = ""

        try {
            val geocoder = Geocoder(context, Locale("ar"))
            @Suppress("DEPRECATION")
            val addresses = geocoder.getFromLocation(lat, lng, 1)
            if (!addresses.isNullOrEmpty()) {
                val addr = addresses[0]
                cityNameArabic = addr.locality ?: addr.subAdminArea ?: addr.adminArea ?: ""
                countryNameArabic = addr.countryName ?: ""
            }
        } catch (e: Exception) {
            Log.w(TAG, "Geocoder failed or offline: ${e.message}")
        }

        // If geocoding was empty or offline, find the nearest pre-configured city
        val nearestCity = findNearestCity(lat, lng)
        if (cityNameArabic.isBlank()) {
            cityNameArabic = nearestCity.nameArabic
        }
        if (countryNameArabic.isBlank()) {
            countryNameArabic = nearestCity.countryArabic
        }

        // Current device timezone
        val tz = TimeZone.getDefault()
        val tzOffset = tz.getOffset(System.currentTimeMillis()) / 3600000.0

        // Determine recommended prayer method based on coordinates / country
        val method = determineMethod(lat, lng, countryNameArabic)

        return PrayerCity(
            id = "gps_${String.format(Locale.US, "%.3f", lat)}_${String.format(Locale.US, "%.3f", lng)}",
            nameArabic = cityNameArabic,
            countryArabic = countryNameArabic,
            latitude = lat,
            longitude = lng,
            defaultTimeZone = tzOffset,
            recommendedMethod = method,
            isGpsDetected = true,
            timeZoneId = nearestCity.timeZoneId ?: tz.id
        )
    }

    private fun findNearestCity(lat: Double, lng: Double): PrayerCity {
        var closest = PrayerCalculator.DEFAULT_CITY
        var minDistance = Double.MAX_VALUE
        for (city in PrayerCalculator.CITIES) {
            val dLat = Math.toRadians(city.latitude - lat)
            val dLng = Math.toRadians(city.longitude - lng)
            val a = kotlin.math.sin(dLat / 2) * kotlin.math.sin(dLat / 2) +
                    kotlin.math.cos(Math.toRadians(lat)) * kotlin.math.cos(Math.toRadians(city.latitude)) *
                    kotlin.math.sin(dLng / 2) * kotlin.math.sin(dLng / 2)
            val dist = 2 * kotlin.math.atan2(kotlin.math.sqrt(a), kotlin.math.sqrt(1 - a))
            if (dist < minDistance) {
                minDistance = dist
                closest = city
            }
        }
        return closest
    }

    private fun determineMethod(lat: Double, lng: Double, countryArabic: String): PrayerMethod {
        return when {
            countryArabic.contains("مصر") || (lat in 22.0..31.8 && lng in 25.0..36.0) ->
                PrayerMethod.EGYPTIAN
            countryArabic.contains("السعودية") || countryArabic.contains("الإمارات") ||
            countryArabic.contains("الكويت") || countryArabic.contains("قطر") ||
            countryArabic.contains("عمان") || countryArabic.contains("البحرين") ||
            countryArabic.contains("اليمن") ->
                PrayerMethod.UMM_AL_QURA
            countryArabic.contains("أمريكا") || countryArabic.contains("كندا") ->
                PrayerMethod.ISNA
            countryArabic.contains("باكستان") || countryArabic.contains("الهند") ||
            countryArabic.contains("بنغلاديش") ->
                PrayerMethod.KARACHI
            else ->
                PrayerMethod.MUSLIM_WORLD_LEAGUE
        }
    }
}
