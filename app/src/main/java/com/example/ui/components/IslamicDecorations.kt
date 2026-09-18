package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Surah
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.GoldAccent

// Helper to convert Western digits (1, 2, 3) to Arabic-Indic numerals (١, ٢, ٣)
fun toArabicIndicDigits(number: Int): String {
    val arabicDigits = charArrayOf('٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩')
    return number.toString().map { arabicDigits[it - '0'] }.joinToString("")
}

/**
 * Traditional Royal Surah Header Cartouche (ترويسة السورة المذهبة كما في مصحف المدينة)
 */
@Composable
fun SurahHeaderCartouche(
    surah: Surah,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        // Decorative Ornate Background Box
        Surface(
            shape = RoundedCornerShape(14.dp),
            color = Color(0xFF1E3A2B), // Deep Islamic Forest Green
            border = androidx.compose.foundation.BorderStroke(2.5.dp, GoldAccent),
            shadowElevation = 4.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                // Background subtle Islamic geometric canvas lines
                Canvas(modifier = Modifier.matchParentSize()) {
                    val stroke = Stroke(width = 1.dp.toPx())
                    // Draw decorative corner diagonals
                    drawLine(
                        color = GoldAccent.copy(alpha = 0.35f),
                        start = Offset(0f, 0f),
                        end = Offset(24.dp.toPx(), 24.dp.toPx()),
                        strokeWidth = stroke.width
                    )
                    drawLine(
                        color = GoldAccent.copy(alpha = 0.35f),
                        start = Offset(size.width, 0f),
                        end = Offset(size.width - 24.dp.toPx(), 24.dp.toPx()),
                        strokeWidth = stroke.width
                    )
                    drawLine(
                        color = GoldAccent.copy(alpha = 0.35f),
                        start = Offset(0f, size.height),
                        end = Offset(24.dp.toPx(), size.height - 24.dp.toPx()),
                        strokeWidth = stroke.width
                    )
                    drawLine(
                        color = GoldAccent.copy(alpha = 0.35f),
                        start = Offset(size.width, size.height),
                        end = Offset(size.width - 24.dp.toPx(), size.height - 24.dp.toPx()),
                        strokeWidth = stroke.width
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Right Pill: Revelation Type (مكية / مدنية)
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = Color(0xFF28503B),
                        border = androidx.compose.foundation.BorderStroke(1.dp, GoldAccent.copy(alpha = 0.7f))
                    ) {
                        Text(
                            text = surah.revelationType.arabicName,
                            color = GoldAccent,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }

                    // Center: Surah Title in Calligraphic Style
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "سُورَةُ ${surah.nameArabic}",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = GoldAccent,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "نُزُولُهَا بَعْدَ ${surah.nameEnglish} • رَقْمُهَا ${surah.id}",
                            fontSize = 10.sp,
                            color = Color(0xFFE2D6B5),
                            textAlign = TextAlign.Center
                        )
                    }

                    // Left Pill: Verses Count (آياتها كذا)
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = Color(0xFF28503B),
                        border = androidx.compose.foundation.BorderStroke(1.dp, GoldAccent.copy(alpha = 0.7f))
                    ) {
                        Text(
                            text = "آيَاتُهَا ${toArabicIndicDigits(surah.versesCount)}",
                            color = GoldAccent,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Traditional Basmalah Ribbon (شريطة البسملة الشريفة)
 */
@Composable
fun BismillahRibbon(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color(0xFFF7F1E1),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, GoldAccent.copy(alpha = 0.8f)),
            shadowElevation = 2.dp
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "۞",
                    color = GoldAccent,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E3A2B),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "۞",
                    color = GoldAccent,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

/**
 * Islamic Page Frame (برواز مصحف المدينة المنورة المذهب)
 */
@Composable
fun RoyalPageFrame(
    surah: Surah,
    currentPage: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp, vertical = 4.dp)
    ) {
        // Page Container
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(2.5.dp, GoldAccent, RoundedCornerShape(12.dp))
                .padding(2.5.dp)
                .border(1.dp, GoldAccent.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
                .padding(8.dp)
        ) {
            // Page Header (اسم السورة والجزء)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "الجزء ${toArabicIndicDigits(surah.juzNumber)}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = GoldAccent
                )
                Text(
                    text = "سورة ${surah.nameArabic}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = EmeraldPrimary
                )
                Text(
                    text = "الحزب ${toArabicIndicDigits(((surah.juzNumber - 1) * 2) + 1)}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = GoldAccent
                )
            }

            // Outer decorative line below header
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .padding(horizontal = 6.dp)
            ) {
                drawLine(
                    color = GoldAccent.copy(alpha = 0.6f),
                    start = Offset(0f, 1f),
                    end = Offset(size.width, 1f),
                    strokeWidth = 1.5.dp.toPx()
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Main Verses Content
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = false)
            ) {
                content()
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Outer decorative line above footer
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .padding(horizontal = 6.dp)
            ) {
                drawLine(
                    color = GoldAccent.copy(alpha = 0.6f),
                    start = Offset(0f, 1f),
                    end = Offset(size.width, 1f),
                    strokeWidth = 1.5.dp.toPx()
                )
            }

            // Page Footer (رقم الصفحة)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = GoldAccent.copy(alpha = 0.15f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, GoldAccent.copy(alpha = 0.5f))
                ) {
                    Text(
                        text = "صفحة ${toArabicIndicDigits(currentPage)}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = GoldAccent,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}
