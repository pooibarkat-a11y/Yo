package com.example.ui.components

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.FileProvider
import com.example.model.Ayah
import com.example.model.Surah
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.GoldAccent
import java.io.File
import java.io.FileOutputStream

enum class CardDesignTheme(
    val title: String,
    val bgColors: List<androidx.compose.ui.graphics.Color>,
    val textColor: androidx.compose.ui.graphics.Color,
    val accentColor: androidx.compose.ui.graphics.Color,
    val nativeBgColor: Int,
    val nativeTextColor: Int,
    val nativeAccentColor: Int
) {
    EMERALD_GOLD(
        title = "الزمرد والذهب الملكي",
        bgColors = listOf(
            androidx.compose.ui.graphics.Color(0xFF042F24),
            androidx.compose.ui.graphics.Color(0xFF021A14)
        ),
        textColor = androidx.compose.ui.graphics.Color.White,
        accentColor = androidx.compose.ui.graphics.Color(0xFFF59E0B),
        nativeBgColor = Color.parseColor("#042F24"),
        nativeTextColor = Color.WHITE,
        nativeAccentColor = Color.parseColor("#F59E0B")
    ),
    MIDNIGHT_KAABA(
        title = "كسوة الكعبة المشرفة",
        bgColors = listOf(
            androidx.compose.ui.graphics.Color(0xFF0B0F19),
            androidx.compose.ui.graphics.Color(0xFF020617)
        ),
        textColor = androidx.compose.ui.graphics.Color(0xFFF8FAFC),
        accentColor = androidx.compose.ui.graphics.Color(0xFFFBBF24),
        nativeBgColor = Color.parseColor("#0B0F19"),
        nativeTextColor = Color.parseColor("#F8FAFC"),
        nativeAccentColor = Color.parseColor("#FBBF24")
    ),
    MADINAH_PARCHMENT(
        title = "ورق المصحف النبوي",
        bgColors = listOf(
            androidx.compose.ui.graphics.Color(0xFFFFFBEB),
            androidx.compose.ui.graphics.Color(0xFFFEF3C7)
        ),
        textColor = androidx.compose.ui.graphics.Color(0xFF451A03),
        accentColor = androidx.compose.ui.graphics.Color(0xFFB45309),
        nativeBgColor = Color.parseColor("#FFFBEB"),
        nativeTextColor = Color.parseColor("#451A03"),
        nativeAccentColor = Color.parseColor("#B45309")
    ),
    ROYAL_SAPPHIRE(
        title = "الأزرق الملكي والذهب",
        bgColors = listOf(
            androidx.compose.ui.graphics.Color(0xFF0C1E3C),
            androidx.compose.ui.graphics.Color(0xFF030A18)
        ),
        textColor = androidx.compose.ui.graphics.Color(0xFFFFFFFF),
        accentColor = androidx.compose.ui.graphics.Color(0xFFF59E0B),
        nativeBgColor = Color.parseColor("#0C1E3C"),
        nativeTextColor = Color.WHITE,
        nativeAccentColor = Color.parseColor("#F59E0B")
    ),
    IMPERIAL_PURPLE(
        title = "الأندلسي الملكي الفاخر",
        bgColors = listOf(
            androidx.compose.ui.graphics.Color(0xFF2A0845),
            androidx.compose.ui.graphics.Color(0xFF160326)
        ),
        textColor = androidx.compose.ui.graphics.Color(0xFFFAF5FF),
        accentColor = androidx.compose.ui.graphics.Color(0xFFFBBF24),
        nativeBgColor = Color.parseColor("#2A0845"),
        nativeTextColor = Color.parseColor("#FAF5FF"),
        nativeAccentColor = Color.parseColor("#FBBF24")
    )
}

object AyahImageShareHelper {

    const val DEFAULT_DEVELOPER_NAME = "المهندس يوسف محمود فوزي «الكينج 👑»"

    fun generateAyahCardBitmap(
        surahName: String,
        ayahNumber: Int,
        ayahText: String,
        theme: CardDesignTheme = CardDesignTheme.EMERALD_GOLD,
        developerName: String = DEFAULT_DEVELOPER_NAME,
        includeDeveloperCredit: Boolean = true,
        tafsirSnippet: String? = null
    ): Bitmap {
        val width = 1080
        val height = if (!tafsirSnippet.isNullOrBlank()) 1440 else 1350
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        // 1. Solid Canvas Background
        val bgPaint = Paint().apply {
            color = theme.nativeBgColor
            style = Paint.Style.FILL
        }
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), bgPaint)

        // 2. Ornamental Double Islamic Borders
        val outerBorderPaint = Paint().apply {
            color = theme.nativeAccentColor
            style = Paint.Style.STROKE
            strokeWidth = 7f
            isAntiAlias = true
        }
        val innerBorderPaint = Paint().apply {
            color = theme.nativeAccentColor
            style = Paint.Style.STROKE
            strokeWidth = 2.5f
            isAntiAlias = true
            alpha = 190
        }

        val margin = 50f
        canvas.drawRoundRect(RectF(margin, margin, width - margin, height - margin), 36f, 36f, outerBorderPaint)
        canvas.drawRoundRect(RectF(margin + 18f, margin + 18f, width - margin - 18f, height - margin - 18f), 26f, 26f, innerBorderPaint)

        // 2b. Corner Islamic Flourishes (Geometric Diamonds)
        val cornerPaint = Paint().apply {
            color = theme.nativeAccentColor
            style = Paint.Style.FILL
            isAntiAlias = true
        }
        val corners = listOf(
            Pair(margin + 18f, margin + 18f),
            Pair(width - margin - 18f, margin + 18f),
            Pair(margin + 18f, height - margin - 18f),
            Pair(width - margin - 18f, height - margin - 18f)
        )
        corners.forEach { (cx, cy) ->
            drawIslamicDiamond(canvas, cx, cy, 14f, cornerPaint)
        }

        // 3. Top Header: Bismillah & Surah Badge
        val bismillahPaint = Paint().apply {
            color = theme.nativeAccentColor
            textSize = 44f
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
            typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
        }
        canvas.drawText("✦ بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ ✦", width / 2f, 155f, bismillahPaint)

        val surahBadgeText = "سورة $surahName • الآية ${toArabicIndicDigits(ayahNumber)}"
        val badgePaint = Paint().apply {
            color = theme.nativeTextColor
            textSize = 34f
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
            alpha = 230
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }
        canvas.drawText(surahBadgeText, width / 2f, 225f, badgePaint)

        // Top Ornamental Divider Line with center diamond
        val dividerPaint = Paint().apply {
            color = theme.nativeAccentColor
            strokeWidth = 3f
            alpha = 180
            isAntiAlias = true
        }
        canvas.drawLine(180f, 265f, (width / 2f) - 24f, 265f, dividerPaint)
        drawIslamicDiamond(canvas, width / 2f, 265f, 10f, cornerPaint)
        canvas.drawLine((width / 2f) + 24f, 265f, width - 180f, 265f, dividerPaint)

        // 4. Ayah Text using StaticLayout
        val textPaint = TextPaint().apply {
            color = theme.nativeTextColor
            textSize = when {
                ayahText.length > 400 -> 34f
                ayahText.length > 250 -> 40f
                ayahText.length > 150 -> 46f
                else -> 52f
            }
            isAntiAlias = true
            typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
        }

        val textWidth = (width - 240)
        val textToDraw = "﴿ $ayahText ﴾"

        val ayahLayout = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StaticLayout.Builder.obtain(textToDraw, 0, textToDraw.length, textPaint, textWidth)
                .setAlignment(Layout.Alignment.ALIGN_CENTER)
                .setLineSpacing(16f, 1.25f)
                .build()
        } else {
            @Suppress("DEPRECATION")
            StaticLayout(
                textToDraw,
                textPaint,
                textWidth,
                Layout.Alignment.ALIGN_CENTER,
                1.25f,
                16f,
                false
            )
        }

        // Tafsir layout if enabled
        val tafsirLayout = if (!tafsirSnippet.isNullOrBlank()) {
            val tafsirPaint = TextPaint().apply {
                color = theme.nativeTextColor
                textSize = 28f
                isAntiAlias = true
                alpha = 220
                typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
            }
            val cleanTafsir = "التفسير الميسر: $tafsirSnippet"
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                StaticLayout.Builder.obtain(cleanTafsir, 0, cleanTafsir.length, tafsirPaint, textWidth - 60)
                    .setAlignment(Layout.Alignment.ALIGN_CENTER)
                    .setLineSpacing(10f, 1.15f)
                    .build()
            } else {
                @Suppress("DEPRECATION")
                StaticLayout(
                    cleanTafsir,
                    tafsirPaint,
                    textWidth - 60,
                    Layout.Alignment.ALIGN_CENTER,
                    1.15f,
                    10f,
                    false
                )
            }
        } else null

        // Calculate positions
        val bottomReserved = if (includeDeveloperCredit) 270f else 180f
        val availableContentHeight = height - 280f - bottomReserved
        val totalTextHeight = ayahLayout.height + (if (tafsirLayout != null) tafsirLayout.height + 60f else 0f)

        val startY = 280f + ((availableContentHeight - totalTextHeight) / 2f).coerceAtLeast(20f)

        // Draw Ayah Text
        canvas.save()
        canvas.translate(120f, startY)
        ayahLayout.draw(canvas)
        canvas.restore()

        // Draw Tafsir Box if available
        if (tafsirLayout != null) {
            val tafsirBoxY = startY + ayahLayout.height + 30f
            val tafsirBoxRect = RectF(100f, tafsirBoxY, width - 100f, tafsirBoxY + tafsirLayout.height + 40f)

            val boxPaint = Paint().apply {
                color = theme.nativeAccentColor
                style = Paint.Style.STROKE
                strokeWidth = 2f
                alpha = 110
                isAntiAlias = true
            }
            val boxFillPaint = Paint().apply {
                color = theme.nativeAccentColor
                style = Paint.Style.FILL
                alpha = 25
            }
            canvas.drawRoundRect(tafsirBoxRect, 20f, 20f, boxFillPaint)
            canvas.drawRoundRect(tafsirBoxRect, 20f, 20f, boxPaint)

            canvas.save()
            canvas.translate(130f, tafsirBoxY + 20f)
            tafsirLayout.draw(canvas)
            canvas.restore()
        }

        // 5. Bottom Decorative Footer & Developer Watermark
        val bottomDividerY = height - (if (includeDeveloperCredit) 240f else 170f)
        canvas.drawLine(180f, bottomDividerY, (width / 2f) - 24f, bottomDividerY, dividerPaint)
        drawIslamicDiamond(canvas, width / 2f, bottomDividerY, 10f, cornerPaint)
        canvas.drawLine((width / 2f) + 24f, bottomDividerY, width - 180f, bottomDividerY, dividerPaint)

        // App Name
        val appNamePaint = Paint().apply {
            color = theme.nativeAccentColor
            textSize = 30f
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }
        canvas.drawText("🕌 تطبيق ترتيل • القرآن الكريم", width / 2f, bottomDividerY + 48f, appNamePaint)

        // Developer Credit (Royal developer tribute)
        if (includeDeveloperCredit) {
            val devPaint = Paint().apply {
                color = theme.nativeAccentColor
                textSize = 30f
                isAntiAlias = true
                textAlign = Paint.Align.CENTER
                typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            }
            canvas.drawText("👑 برمجة وتطوير: $developerName", width / 2f, bottomDividerY + 98f, devPaint)

            val subFooterPaint = Paint().apply {
                color = theme.nativeTextColor
                textSize = 23f
                isAntiAlias = true
                textAlign = Paint.Align.CENTER
                alpha = 210
            }
            canvas.drawText("صدقة جارية • نسألكم الدعاء للمطور ولوالديه بالرحمة والمغفرة 🤲", width / 2f, bottomDividerY + 144f, subFooterPaint)
        } else {
            val subFooterPaint = Paint().apply {
                color = theme.nativeTextColor
                textSize = 24f
                isAntiAlias = true
                textAlign = Paint.Align.CENTER
                alpha = 190
            }
            canvas.drawText("صدقة جارية • ترتيل وتدبر وحفظ", width / 2f, bottomDividerY + 95f, subFooterPaint)
        }

        return bitmap
    }

    private fun drawIslamicDiamond(canvas: Canvas, cx: Float, cy: Float, size: Float, paint: Paint) {
        val path = Path().apply {
            moveTo(cx, cy - size)
            lineTo(cx + size, cy)
            lineTo(cx, cy + size)
            lineTo(cx - size, cy)
            close()
        }
        canvas.drawPath(path, paint)
    }

    fun saveAyahImageToGallery(
        context: Context,
        surahName: String,
        ayahNumber: Int,
        ayahText: String,
        theme: CardDesignTheme = CardDesignTheme.EMERALD_GOLD,
        developerName: String = DEFAULT_DEVELOPER_NAME,
        includeDeveloperCredit: Boolean = true,
        tafsirSnippet: String? = null
    ): Boolean {
        return try {
            val bitmap = generateAyahCardBitmap(
                surahName = surahName,
                ayahNumber = ayahNumber,
                ayahText = ayahText,
                theme = theme,
                developerName = developerName,
                includeDeveloperCredit = includeDeveloperCredit,
                tafsirSnippet = tafsirSnippet
            )

            val fileName = "Tartil_Ayah_${surahName}_$ayahNumber" + "_${System.currentTimeMillis()}.png"
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val values = ContentValues().apply {
                    put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                    put(MediaStore.Images.Media.MIME_TYPE, "image/png")
                    put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/TartilQuran")
                    put(MediaStore.Images.Media.IS_PENDING, 1)
                }
                val resolver = context.contentResolver
                val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values) ?: return false
                resolver.openOutputStream(uri)?.use { out ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                }
                values.clear()
                values.put(MediaStore.Images.Media.IS_PENDING, 0)
                resolver.update(uri, values, null, null)
            } else {
                val dir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "TartilQuran")
                if (!dir.exists()) dir.mkdirs()
                val file = File(dir, fileName)
                FileOutputStream(file).use { out ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                }
                val values = ContentValues().apply {
                    put(MediaStore.Images.Media.DATA, file.absolutePath)
                    put(MediaStore.Images.Media.MIME_TYPE, "image/png")
                }
                context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            }
            Toast.makeText(context, "تم حفظ بطاقة الآية في معرض الصور بنجاح 🖼️", Toast.LENGTH_LONG).show()
            true
        } catch (e: Exception) {
            Toast.makeText(context, "تعذر حفظ الصورة: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            false
        }
    }

    fun shareAyahAsImage(
        context: Context,
        surahName: String,
        ayahNumber: Int,
        ayahText: String,
        theme: CardDesignTheme = CardDesignTheme.EMERALD_GOLD,
        developerName: String = DEFAULT_DEVELOPER_NAME,
        includeDeveloperCredit: Boolean = true,
        tafsirSnippet: String? = null
    ) {
        try {
            val bitmap = generateAyahCardBitmap(
                surahName = surahName,
                ayahNumber = ayahNumber,
                ayahText = ayahText,
                theme = theme,
                developerName = developerName,
                includeDeveloperCredit = includeDeveloperCredit,
                tafsirSnippet = tafsirSnippet
            )

            val cachePath = File(context.cacheDir, "images")
            cachePath.mkdirs()
            val file = File(cachePath, "ayah_${surahName}_$ayahNumber.png")
            val stream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            stream.close()

            val contentUri: Uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )

            val devText = if (includeDeveloperCredit) "\n👑 برمجة وتطوير: $developerName" else ""
            val tafsirText = if (!tafsirSnippet.isNullOrBlank()) "\n\n📖 التفسير الميسر:\n$tafsirSnippet" else ""

            val shareCaption = """
                |﴿ $ayahText ﴾
                |[سورة $surahName: الآية ${toArabicIndicDigits(ayahNumber)}]$tafsirText
                |
                |🕌 تطبيق ترتيل للقرآن الكريم$devText
                |🤲 صدقة جارية - نسألكم صالح الدعاء
            """.trimMargin()

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "image/png"
                putExtra(Intent.EXTRA_STREAM, contentUri)
                putExtra(Intent.EXTRA_TEXT, shareCaption)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            context.startActivity(Intent.createChooser(shareIntent, "مشاركة بطاقة الآية الكريمة"))
        } catch (e: Exception) {
            // Fallback to text share
            val devText = if (includeDeveloperCredit) "\n👑 برمجة وتطوير: $developerName" else ""
            val textIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(
                    Intent.EXTRA_TEXT,
                    "﴿ $ayahText ﴾\n[سورة $surahName: الآية ${toArabicIndicDigits(ayahNumber)}]\n\n🕌 تطبيق ترتيل للقرآن الكريم$devText\n🤲 صدقة جارية"
                )
            }
            context.startActivity(Intent.createChooser(textIntent, "مشاركة الآية الكريمة"))
        }
    }
}

@Composable
fun ShareAyahDialog(
    surah: Surah,
    ayah: Ayah,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var selectedTheme by remember { mutableStateOf(CardDesignTheme.EMERALD_GOLD) }
    var includeDeveloper by remember { mutableStateOf(true) }
    var developerName by remember { mutableStateOf(AyahImageShareHelper.DEFAULT_DEVELOPER_NAME) }
    var includeTafsir by remember { mutableStateOf(ayah.tafsir.isNotBlank()) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(26.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .testTag("share_ayah_card_dialog")
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Palette,
                            contentDescription = null,
                            tint = GoldAccent,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "مشاركة الآية بصورة مصممة جميلة",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "إغلاق")
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                // Live Preview Card
                Card(
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(2.dp, selectedTheme.accentColor, RoundedCornerShape(20.dp))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Brush.verticalGradient(selectedTheme.bgColors))
                            .padding(20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "✦ بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ ✦",
                                color = selectedTheme.accentColor,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "سورة ${surah.nameArabic} • الآية ${toArabicIndicDigits(ayah.ayahNumber)}",
                                color = selectedTheme.textColor.copy(alpha = 0.85f),
                                style = MaterialTheme.typography.labelSmall
                            )
                            Spacer(modifier = Modifier.height(14.dp))
                            Text(
                                text = "﴿ ${ayah.textUthmani} ﴾",
                                color = selectedTheme.textColor,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                lineHeight = 28.sp
                            )

                            if (includeTafsir && ayah.tafsir.isNotBlank()) {
                                Spacer(modifier = Modifier.height(12.dp))
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = selectedTheme.accentColor.copy(alpha = 0.15f),
                                    border = BorderStroke(1.dp, selectedTheme.accentColor.copy(alpha = 0.4f)),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Text(
                                            text = "التفسير الميسر:",
                                            color = selectedTheme.accentColor,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = ayah.tafsir,
                                            color = selectedTheme.textColor,
                                            fontSize = 12.sp,
                                            lineHeight = 18.sp,
                                            textAlign = TextAlign.Right
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Watermark / App and Developer Tribute Banner
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = selectedTheme.accentColor.copy(alpha = 0.2f),
                                border = BorderStroke(1.dp, selectedTheme.accentColor.copy(alpha = 0.6f))
                            ) {
                                Column(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "🕌 تطبيق ترتيل • القرآن الكريم",
                                        color = selectedTheme.accentColor,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                    if (includeDeveloper) {
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = "👑 برمجة وتطوير: $developerName",
                                            color = selectedTheme.textColor,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "صدقة جارية • نسألكم الدعاء للمطور ولوالديه 🤲",
                                            color = selectedTheme.textColor.copy(alpha = 0.8f),
                                            fontSize = 10.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Theme Selector
                Text(
                    text = "اختر نمط ولون البطاقة المصممة:",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    CardDesignTheme.values().forEach { theme ->
                        val isSelected = selectedTheme == theme
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) EmeraldPrimary.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant,
                            border = if (isSelected) BorderStroke(1.8.dp, GoldAccent) else BorderStroke(0.6.dp, MaterialTheme.colorScheme.outlineVariant),
                            modifier = Modifier
                                .weight(1f)
                                .clickable { selectedTheme = theme }
                                .testTag("theme_btn_${theme.name.lowercase()}")
                        ) {
                            Column(
                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 2.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .clip(CircleShape)
                                        .background(Brush.linearGradient(theme.bgColors))
                                        .border(1.dp, theme.accentColor, CircleShape)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = theme.title.substringBefore(" ").take(7),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontSize = 10.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Developer & Signature Options
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                    border = BorderStroke(1.dp, GoldAccent.copy(alpha = 0.3f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "تضمين اسم المطور في الصورة 👑",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "المهندس يوسف محمود فوزي «الكينج»",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = GoldAccent
                                )
                            }
                            Switch(
                                checked = includeDeveloper,
                                onCheckedChange = { includeDeveloper = it },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = GoldAccent,
                                    checkedTrackColor = EmeraldPrimary
                                )
                            )
                        }

                        if (ayah.tafsir.isNotBlank()) {
                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "تضمين التفسير الميسر في الصورة 📖",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "شرح الآية الميسر داخل البطاقة",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                Switch(
                                    checked = includeTafsir,
                                    onCheckedChange = { includeTafsir = it },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = GoldAccent,
                                        checkedTrackColor = EmeraldPrimary
                                    )
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Action Buttons: Share Image, Save to Gallery, Copy Text
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Copy text button
                    OutlinedButton(
                        onClick = {
                            val devText = if (includeDeveloper) "\n👑 برمجة وتطوير: $developerName" else ""
                            val tafsirText = if (includeTafsir && ayah.tafsir.isNotBlank()) "\n\nالتفسير الميسر: ${ayah.tafsir}" else ""
                            val fullText = "﴿ ${ayah.textUthmani} ﴾\n[سورة ${surah.nameArabic}: ${toArabicIndicDigits(ayah.ayahNumber)}]$tafsirText\n\n🕌 تطبيق ترتيل للقرآن الكريم$devText\n🤲 صدقة جارية - نسألكم صالح الدعاء"
                            val clip = android.content.ClipData.newPlainText("آية قرآنية", fullText)
                            val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                            cm.setPrimaryClip(clip)
                            Toast.makeText(context, "تم نسخ الآية واسم المطور للحافظة 📋", Toast.LENGTH_SHORT).show()
                            onDismiss()
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("copy_ayah_text_btn")
                    ) {
                        Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("نسخ", fontSize = 12.sp)
                    }

                    // Save to gallery button
                    OutlinedButton(
                        onClick = {
                            AyahImageShareHelper.saveAyahImageToGallery(
                                context = context,
                                surahName = surah.nameArabic,
                                ayahNumber = ayah.ayahNumber,
                                ayahText = ayah.textUthmani,
                                theme = selectedTheme,
                                developerName = developerName,
                                includeDeveloperCredit = includeDeveloper,
                                tafsirSnippet = if (includeTafsir) ayah.tafsir else null
                            )
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1.1f)
                            .testTag("save_ayah_image_btn")
                    ) {
                        Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(16.dp), tint = GoldAccent)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("حفظ الصورة", fontSize = 12.sp)
                    }

                    // Share Image Button (Primary Action)
                    Button(
                        onClick = {
                            AyahImageShareHelper.shareAyahAsImage(
                                context = context,
                                surahName = surah.nameArabic,
                                ayahNumber = ayah.ayahNumber,
                                ayahText = ayah.textUthmani,
                                theme = selectedTheme,
                                developerName = developerName,
                                includeDeveloperCredit = includeDeveloper,
                                tafsirSnippet = if (includeTafsir) ayah.tafsir else null
                            )
                            onDismiss()
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                        modifier = Modifier
                            .weight(1.4f)
                            .testTag("share_ayah_image_btn")
                    ) {
                        Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(18.dp), tint = GoldAccent)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("مشاركة الصورة", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            }
        }
    }
}
