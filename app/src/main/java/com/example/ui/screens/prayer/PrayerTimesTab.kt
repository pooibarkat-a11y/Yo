package com.example.ui.screens.prayer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.Brightness5
import androidx.compose.material.icons.filled.Brightness6
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.WbTwilight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prayer.PrayerTimes
import com.example.viewmodel.AppViewModel

enum class PrayerPeriodTheme(
    val title: String,
    val atmosphereBadge: String,
    val atmosphereDescription: String
) {
    FAJR("الفجر", "🌙 فجر وليل", "غلس الليل وبزوغ الفجر وسكون السحر"),
    SUNRISE("الشروق", "🌅 إشراق الصباح", "طلوع قرص الشمس وابتداء النهار"),
    DHUHR("الظهر", "☀️ نهار مشرق (الظهيرة)", "زوال الشمس في كبد السماء وعز النهار"),
    ASR("العصر", "🌤️ آخر النهار (الأصيل)", "ميل الشمس نحو الغروب وبداية آخر النهار"),
    MAGHRIB("المغرب", "🌇 شفق الغروب والغسق", "مغيب قرص الشمس وبداية الشفق الأحمر"),
    ISHA("العشاء", "🌌 سكون الليل البهيم", "مغيب الشفق ودخول ظلمة وسكون الليل")
}

@Composable
private fun PrayerAtmosphereEmblem(
    period: PrayerPeriodTheme,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(50.dp)
            .clip(CircleShape)
            .background(
                when (period) {
                    PrayerPeriodTheme.FAJR -> Brush.verticalGradient(listOf(Color(0xFF0F172A), Color(0xFF1E293B), Color(0xFF0284C7)))
                    PrayerPeriodTheme.SUNRISE -> Brush.verticalGradient(listOf(Color(0xFF431407), Color(0xFF7C2D12), Color(0xFFD97706)))
                    PrayerPeriodTheme.DHUHR -> Brush.verticalGradient(listOf(Color(0xFF0284C7), Color(0xFF38BDF8), Color(0xFFFDE047)))
                    PrayerPeriodTheme.ASR -> Brush.verticalGradient(listOf(Color(0xFF78350F), Color(0xFFB45309), Color(0xFFF59E0B)))
                    PrayerPeriodTheme.MAGHRIB -> Brush.verticalGradient(listOf(Color(0xFF4C0519), Color(0xFF881337), Color(0xFFF43F5E)))
                    PrayerPeriodTheme.ISHA -> Brush.verticalGradient(listOf(Color(0xFF020617), Color(0xFF0F172A), Color(0xFF1E1B4B)))
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            when (period) {
                PrayerPeriodTheme.FAJR -> {
                    // Star dots
                    drawCircle(Color.White.copy(alpha = 0.9f), radius = 1.5.dp.toPx(), center = Offset(w * 0.25f, h * 0.25f))
                    drawCircle(Color.White.copy(alpha = 0.7f), radius = 1.2.dp.toPx(), center = Offset(w * 0.75f, h * 0.35f))
                    // Dawn horizon band
                    drawRect(
                        brush = Brush.verticalGradient(
                            listOf(Color.Transparent, Color(0xFF38BDF8).copy(alpha = 0.6f)),
                            startY = h * 0.6f,
                            endY = h
                        )
                    )
                    // Crescent moon
                    drawCircle(Color(0xFFE0F2FE), radius = 7.dp.toPx(), center = Offset(w * 0.5f, h * 0.45f))
                    drawCircle(Color(0xFF0F172A), radius = 5.8.dp.toPx(), center = Offset(w * 0.56f, h * 0.42f))
                }
                PrayerPeriodTheme.SUNRISE -> {
                    // Horizon ground line
                    drawLine(Color(0xFFFDE68A), Offset(0f, h * 0.68f), Offset(w, h * 0.68f), strokeWidth = 2.dp.toPx())
                    // Rising sun half above horizon
                    drawCircle(Color(0xFFF59E0B), radius = 9.dp.toPx(), center = Offset(w * 0.5f, h * 0.68f))
                    // Upward radiating rays
                    for (angle in listOf(-60f, -30f, 0f, 30f, 60f)) {
                        val rad = Math.toRadians((angle - 90).toDouble())
                        val startX = (w * 0.5f + Math.cos(rad) * 11.dp.toPx()).toFloat()
                        val startY = (h * 0.68f + Math.sin(rad) * 11.dp.toPx()).toFloat()
                        val endX = (w * 0.5f + Math.cos(rad) * 17.dp.toPx()).toFloat()
                        val endY = (h * 0.68f + Math.sin(rad) * 17.dp.toPx()).toFloat()
                        drawLine(Color(0xFFFEF08A), Offset(startX, startY), Offset(endX, endY), strokeWidth = 1.8.dp.toPx())
                    }
                }
                PrayerPeriodTheme.DHUHR -> {
                    // Bright daylight high-noon sun in center with 360 degree rays
                    drawCircle(Color(0xFFFEF08A), radius = 7.dp.toPx(), center = Offset(w * 0.5f, h * 0.5f))
                    for (i in 0 until 8) {
                        val angle = i * 45.0
                        val rad = Math.toRadians(angle)
                        val startX = (w * 0.5f + Math.cos(rad) * 9.dp.toPx()).toFloat()
                        val startY = (h * 0.5f + Math.sin(rad) * 9.dp.toPx()).toFloat()
                        val endX = (w * 0.5f + Math.cos(rad) * 14.dp.toPx()).toFloat()
                        val endY = (h * 0.5f + Math.sin(rad) * 14.dp.toPx()).toFloat()
                        drawLine(Color(0xFFFFFFFF), Offset(startX, startY), Offset(endX, endY), strokeWidth = 1.8.dp.toPx())
                    }
                }
                PrayerPeriodTheme.ASR -> {
                    // "آخر النهار والأصيل" - Warm sloping sun rays across the sky
                    val sunCenter = Offset(w * 0.35f, h * 0.38f)
                    drawCircle(Color(0xFFFBBF24), radius = 6.5.dp.toPx(), center = sunCenter)
                    // Slanted late afternoon rays heading down right
                    drawLine(Color(0xFFFDE68A), Offset(sunCenter.x + 8.dp.toPx(), sunCenter.y + 4.dp.toPx()), Offset(w * 0.85f, h * 0.75f), strokeWidth = 2.dp.toPx())
                    drawLine(Color(0xFFFDE68A).copy(alpha = 0.7f), Offset(sunCenter.x + 4.dp.toPx(), sunCenter.y + 8.dp.toPx()), Offset(w * 0.7f, h * 0.88f), strokeWidth = 1.8.dp.toPx())
                    drawLine(Color(0xFFFDE68A).copy(alpha = 0.5f), Offset(sunCenter.x + 9.dp.toPx(), sunCenter.y), Offset(w * 0.95f, h * 0.58f), strokeWidth = 1.5.dp.toPx())
                }
                PrayerPeriodTheme.MAGHRIB -> {
                    // Horizon line
                    drawLine(Color(0xFFFDA4AF), Offset(0f, h * 0.7f), Offset(w, h * 0.7f), strokeWidth = 2.dp.toPx())
                    // Sunset dipping below horizon
                    drawCircle(Color(0xFFFB7185), radius = 8.dp.toPx(), center = Offset(w * 0.5f, h * 0.72f))
                    // Dusk glow
                    drawCircle(Color(0xFFE11D48).copy(alpha = 0.35f), radius = 13.dp.toPx(), center = Offset(w * 0.5f, h * 0.72f))
                }
                PrayerPeriodTheme.ISHA -> {
                    // Night sky with stars and tranquil moon
                    drawCircle(Color.White, radius = 1.2.dp.toPx(), center = Offset(w * 0.25f, h * 0.3f))
                    drawCircle(Color.White, radius = 1.4.dp.toPx(), center = Offset(w * 0.78f, h * 0.25f))
                    drawCircle(Color.White, radius = 1.1.dp.toPx(), center = Offset(w * 0.3f, h * 0.75f))
                    drawCircle(Color.White, radius = 1.3.dp.toPx(), center = Offset(w * 0.7f, h * 0.7f))
                    // Crescent moon
                    drawCircle(Color(0xFFE0E7FF), radius = 7.5.dp.toPx(), center = Offset(w * 0.5f, h * 0.48f))
                    drawCircle(Color(0xFF0F172A), radius = 6.2.dp.toPx(), center = Offset(w * 0.56f, h * 0.45f))
                }
            }
        }
    }
}

private data class PrayerItemInfo(
    val name: String,
    val time: String,
    val icon: ImageVector,
    val periodTheme: PrayerPeriodTheme,
    val bgGradient: List<Color>,
    val accentColor: Color,
    val isSalat: Boolean = true
)

@Composable
fun PrayerTimesTab(
    prayerTimes: PrayerTimes,
    isAdhanPlaying: Boolean,
    currentAdhanPrayer: String?,
    enabledPrayers: Set<String>,
    viewModel: AppViewModel,
    onNavigateToLocation: () -> Unit,
    modifier: Modifier = Modifier
) {
    val prayerList = listOf(
        PrayerItemInfo(
            name = "الفجر",
            time = prayerTimes.fajr,
            icon = Icons.Default.Bedtime,
            periodTheme = PrayerPeriodTheme.FAJR,
            bgGradient = listOf(Color(0xFF1E293B), Color(0xFF0F172A)),
            accentColor = Color(0xFF38BDF8)
        ),
        PrayerItemInfo(
            name = "الشروق",
            time = prayerTimes.sunrise,
            icon = Icons.Default.WbTwilight,
            periodTheme = PrayerPeriodTheme.SUNRISE,
            bgGradient = listOf(Color(0xFF2E1A0C), Color(0xFF1C0F05)),
            accentColor = Color(0xFFFBBF24),
            isSalat = false
        ),
        PrayerItemInfo(
            name = "الظهر",
            time = prayerTimes.dhuhr,
            icon = Icons.Default.WbSunny,
            periodTheme = PrayerPeriodTheme.DHUHR,
            bgGradient = listOf(Color(0xFF2A2006), Color(0xFF171203)),
            accentColor = Color(0xFFFDE047)
        ),
        PrayerItemInfo(
            name = "العصر",
            time = prayerTimes.asr,
            icon = Icons.Default.Brightness5,
            periodTheme = PrayerPeriodTheme.ASR,
            bgGradient = listOf(Color(0xFF251A05), Color(0xFF140D02)),
            accentColor = Color(0xFFF59E0B)
        ),
        PrayerItemInfo(
            name = "المغرب",
            time = prayerTimes.maghrib,
            icon = Icons.Default.Brightness6,
            periodTheme = PrayerPeriodTheme.MAGHRIB,
            bgGradient = listOf(Color(0xFF2E0F17), Color(0xFF17060A)),
            accentColor = Color(0xFFFB7185)
        ),
        PrayerItemInfo(
            name = "العشاء",
            time = prayerTimes.isha,
            icon = Icons.Default.NightsStay,
            periodTheme = PrayerPeriodTheme.ISHA,
            bgGradient = listOf(Color(0xFF1E1B4B), Color(0xFF0F0E2A)),
            accentColor = Color(0xFFA5B4FC)
        )
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            // Hero Card: Next Prayer & Countdown
            NextPrayerHeroCard(
                prayerTimes = prayerTimes,
                onLocationClick = onNavigateToLocation
            )
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "مواقيت اليوم الخمسة",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                    modifier = Modifier.clickable { onNavigateToLocation() }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (prayerTimes.isGpsDetected) "${prayerTimes.cityName} (GPS)" else prayerTimes.cityName,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }

        items(prayerList.size) { index ->
            val prayer = prayerList[index]
            val isNext = prayerTimes.nextPrayerName.contains(prayer.name)
            val isCurrentlyPlaying = isAdhanPlaying && currentAdhanPrayer == prayer.name
            val isEnabled = prayer.name in enabledPrayers

            PrayerCardItem(
                prayer = prayer,
                isNext = isNext,
                isCurrentlyPlaying = isCurrentlyPlaying,
                isEnabled = isEnabled,
                onPlayClick = {
                    if (isCurrentlyPlaying) {
                        viewModel.stopAdhanAudio()
                    } else {
                        viewModel.playAdhanForPrayer(prayer.name)
                    }
                },
                onToggleEnabled = {
                    if (prayer.isSalat) {
                        viewModel.toggleAdhanPrayer(prayer.name)
                    }
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun NextPrayerHeroCard(
    prayerTimes: PrayerTimes,
    onLocationClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF064E3B),
                            Color(0xFF047857),
                            Color(0xFF0F172A)
                        )
                    )
                )
                .padding(20.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = Color(0xFF10B981).copy(alpha = 0.25f),
                        shape = RoundedCornerShape(20.dp),
                        border = BorderStroke(1.dp, Color(0xFF34D399).copy(alpha = 0.4f))
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(Color(0xFF34D399), CircleShape)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "الصلاة القادمة",
                                color = Color(0xFFE2E8F0),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Surface(
                        color = Color.White.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.clickable { onLocationClick() }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = Color(0xFFFDE047),
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${prayerTimes.cityName} • ضبط",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        Text(
                            text = prayerTimes.nextPrayerName,
                            color = Color.White,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Text(
                            text = "حان موعدها في خلال",
                            color = Color(0xFFA7F3D0),
                            fontSize = 13.sp
                        )
                    }

                    Surface(
                        color = Color.Black.copy(alpha = 0.35f),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, Color(0xFF34D399).copy(alpha = 0.3f))
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Timer,
                                contentDescription = null,
                                tint = Color(0xFFFDE047),
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = prayerTimes.timeRemainingNextPrayer,
                                color = Color(0xFFFDE047),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PrayerCardItem(
    prayer: PrayerItemInfo,
    isNext: Boolean,
    isCurrentlyPlaying: Boolean,
    isEnabled: Boolean,
    onPlayClick: () -> Unit,
    onToggleEnabled: () -> Unit
) {
    val borderColor by animateColorAsState(
        targetValue = when {
            isCurrentlyPlaying -> Color(0xFF34D399)
            isNext -> prayer.accentColor
            else -> Color.White.copy(alpha = 0.08f)
        },
        label = "border"
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        border = BorderStroke(if (isNext || isCurrentlyPlaying) 2.dp else 1.dp, borderColor),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isNext) 4.dp else 1.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.horizontalGradient(prayer.bgGradient))
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Atmosphere Emblem and Name
                Row(
                    modifier = Modifier.weight(1f, fill = false),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PrayerAtmosphereEmblem(
                        period = prayer.periodTheme,
                        accentColor = prayer.accentColor
                    )

                    Spacer(modifier = Modifier.width(14.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = prayer.name,
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                color = prayer.accentColor.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(6.dp),
                                border = BorderStroke(0.6.dp, prayer.accentColor.copy(alpha = 0.4f))
                            ) {
                                Text(
                                    text = prayer.periodTheme.atmosphereBadge,
                                    color = prayer.accentColor,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                            if (isNext) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Surface(
                                    color = Color(0xFF34D399),
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        text = "التالية",
                                        color = Color(0xFF064E3B),
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }

                        Text(
                            text = prayer.periodTheme.atmosphereDescription,
                            color = Color(0xFFCBD5E1).copy(alpha = 0.85f),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(top = 2.dp)
                        )

                        if (prayer.isSalat) {
                            Text(
                                text = if (isEnabled) "الأذان مفعل ✓" else "الأذان صامت",
                                color = if (isEnabled) Color(0xFF86EFAC) else Color(0xFF94A3B8),
                                fontSize = 11.sp,
                                modifier = Modifier
                                    .clickable { onToggleEnabled() }
                                    .padding(top = 2.dp)
                            )
                        } else {
                            Text(
                                text = "وقت شروق الشمس",
                                color = Color(0xFFCBD5E1),
                                fontSize = 11.sp,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }
                }

                // Time and Play Button
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = prayer.time,
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 0.5.sp
                    )

                    if (prayer.isSalat) {
                        Spacer(modifier = Modifier.width(12.dp))
                        IconButton(
                            onClick = onPlayClick,
                            modifier = Modifier
                                .size(38.dp)
                                .background(
                                    if (isCurrentlyPlaying) Color(0xFFEF4444).copy(alpha = 0.3f)
                                    else prayer.accentColor.copy(alpha = 0.2f),
                                    CircleShape
                                )
                        ) {
                            Icon(
                                imageVector = if (isCurrentlyPlaying) Icons.Default.Stop else Icons.Default.PlayArrow,
                                contentDescription = "أذان ${prayer.name}",
                                tint = if (isCurrentlyPlaying) Color(0xFFF87171) else prayer.accentColor,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
