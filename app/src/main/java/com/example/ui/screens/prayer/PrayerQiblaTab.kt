package com.example.ui.screens.prayer

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.CompassCalibration
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Mosque
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prayer.PrayerTimes
import com.example.prayer.rememberQiblaCompass
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun PrayerQiblaTab(
    prayerTimes: PrayerTimes,
    modifier: Modifier = Modifier
) {
    val haptic = LocalHapticFeedback.current
    val targetQiblaDeg = prayerTimes.qiblaDirectionDeg
    val compassState = rememberQiblaCompass(targetQiblaDeg)

    var manualHeadingOffset by remember { mutableFloatStateOf(0f) }
    val activeHeading = if (compassState.hasSensor && compassState.deviceAzimuthDeg > 0f) {
        compassState.deviceAzimuthDeg
    } else {
        manualHeadingOffset
    }

    val relativeQibla = ((targetQiblaDeg - activeHeading) + 360f) % 360f
    val isAligned = relativeQibla < 4.0f || relativeQibla > 356.0f

    LaunchedEffect(isAligned) {
        if (isAligned) {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            // Status Alignment Card
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isAligned) Color(0xFF065F46) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                ),
                border = if (isAligned) BorderStroke(2.dp, Color(0xFFD4AF37)) else null,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (isAligned) {
                        Text(
                            text = "🕋 مبارك! أنت الآن في اتجاه القبلة الشريفة تماماً",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 15.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "وجهك مباشرة نحو الكعبة المشرفة بمكة المكرمة",
                            fontSize = 12.sp,
                            color = Color(0xFFFDE047),
                            textAlign = TextAlign.Center
                        )
                    } else {
                        val diff = if (relativeQibla <= 180f) {
                            "أدر هاتفك يميناً بمقدار ${relativeQibla.toInt()}°"
                        } else {
                            "أدر هاتفك يساراً بمقدار ${(360f - relativeQibla).toInt()}°"
                        }
                        Text(
                            text = "وجّه هاتفك نحو الكعبة المشرفة",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = diff,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFFD97706)
                        )
                    }
                }
            }
        }

        item {
            val animatedDialAngle by animateFloatAsState(
                targetValue = -activeHeading,
                animationSpec = tween(durationMillis = 200),
                label = "compassDial"
            )

            val animatedPointerAngle by animateFloatAsState(
                targetValue = relativeQibla,
                animationSpec = tween(durationMillis = 200),
                label = "compassPointer"
            )

            Box(
                modifier = Modifier
                    .size(270.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)),
                contentAlignment = Alignment.Center
            ) {
                // Outer rotating compass rose with Cardinal Marks (N, E, S, W)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .rotate(animatedDialAngle),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val center = Offset(size.width / 2, size.height / 2)
                        val radius = size.minDimension / 2 - 16.dp.toPx()

                        // Outer gold ring
                        drawCircle(
                            color = if (isAligned) Color(0xFFD4AF37) else Color.Gray.copy(alpha = 0.4f),
                            radius = radius,
                            center = center,
                            style = Stroke(width = if (isAligned) 4.dp.toPx() else 2.dp.toPx())
                        )

                        // 36 tick marks
                        for (i in 0 until 36) {
                            val angleDeg = i * 10
                            val angleRad = Math.toRadians(angleDeg.toDouble())
                            val isMajor = angleDeg % 90 == 0
                            val isMedium = angleDeg % 30 == 0
                            val lineLen = if (isMajor) 18.dp.toPx() else if (isMedium) 12.dp.toPx() else 6.dp.toPx()

                            val startX = center.x + (radius - lineLen) * sin(angleRad).toFloat()
                            val startY = center.y - (radius - lineLen) * cos(angleRad).toFloat()
                            val endX = center.x + radius * sin(angleRad).toFloat()
                            val endY = center.y - radius * cos(angleRad).toFloat()

                            val tickColor = when {
                                angleDeg == 0 -> Color(0xFFEF4444)
                                isMajor -> Color.White
                                else -> Color.Gray.copy(alpha = 0.5f)
                            }

                            drawLine(
                                color = tickColor,
                                start = Offset(startX, startY),
                                end = Offset(endX, endY),
                                strokeWidth = if (isMajor) 3.dp.toPx() else 1.5.dp.toPx()
                            )
                        }
                    }

                    Text(
                        text = "ش",
                        fontWeight = FontWeight.Black,
                        fontSize = 16.sp,
                        color = Color(0xFFEF4444),
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 20.dp)
                    )
                    Text(
                        text = "ق",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.LightGray,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 20.dp)
                    )
                    Text(
                        text = "ج",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.LightGray,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 20.dp)
                    )
                    Text(
                        text = "غ",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.LightGray,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 20.dp)
                    )
                }

                // Qibla Pointer Needle
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .rotate(animatedPointerAngle),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val center = Offset(size.width / 2, size.height / 2)
                        val pointerLen = size.minDimension / 2 - 36.dp.toPx()

                        val needlePath = Path().apply {
                            moveTo(center.x, center.y - pointerLen)
                            lineTo(center.x - 14.dp.toPx(), center.y)
                            lineTo(center.x, center.y - 8.dp.toPx())
                            close()
                        }
                        drawPath(needlePath, color = if (isAligned) Color(0xFF10B981) else Color(0xFF059669))

                        val needlePathRight = Path().apply {
                            moveTo(center.x, center.y - pointerLen)
                            lineTo(center.x + 14.dp.toPx(), center.y)
                            lineTo(center.x, center.y - 8.dp.toPx())
                            close()
                        }
                        drawPath(needlePathRight, color = if (isAligned) Color(0xFFD4AF37) else Color(0xFFB45309))

                        val southTail = Path().apply {
                            moveTo(center.x, center.y + pointerLen * 0.7f)
                            lineTo(center.x - 10.dp.toPx(), center.y)
                            lineTo(center.x + 10.dp.toPx(), center.y)
                            close()
                        }
                        drawPath(southTail, color = Color.Gray.copy(alpha = 0.5f))

                        drawCircle(color = Color(0xFFD4AF37), radius = 10.dp.toPx(), center = center)
                        drawCircle(color = Color.White, radius = 5.dp.toPx(), center = center)
                    }

                    Box(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 40.dp)
                            .size(28.dp)
                            .background(Color(0xFFD4AF37), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Mosque,
                            contentDescription = "الكعبة",
                            tint = Color.Black,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }

        item {
            // Distance and Degree summary card
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${prayerTimes.qiblaDirectionDeg.toInt()}°",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "درجة اتجاه القبلة",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Box(
                        modifier = Modifier
                            .height(36.dp)
                            .width(1.dp)
                            .background(Color.White.copy(alpha = 0.1f))
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${prayerTimes.distanceToKaabaKm} كم",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 20.sp,
                            color = Color(0xFFD4AF37)
                        )
                        Text(
                            text = "المسافة إلى مكة المكرمة",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
