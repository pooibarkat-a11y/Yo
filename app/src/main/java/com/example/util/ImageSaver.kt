package com.example.util

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import androidx.core.content.FileProvider
import com.example.data.GalleryItem
import java.io.File
import java.io.FileOutputStream

object ImageSaver {

    fun saveDrawableToGallery(context: Context, drawableResId: Int): Boolean {
        return try {
            val bitmap = BitmapFactory.decodeResource(context.resources, drawableResId) ?: return false
            saveBitmapToGallery(context, bitmap, "Islamic_Wallpaper_${System.currentTimeMillis()}")
        } catch (_: Exception) {
            false
        }
    }

    fun saveGalleryCardToGallery(context: Context, item: GalleryItem): Boolean {
        return try {
            val bitmap = createCardBitmap(context, item)
            saveBitmapToGallery(context, bitmap, "Islamic_Card_${item.id}_${System.currentTimeMillis()}")
        } catch (_: Exception) {
            false
        }
    }

    private fun saveBitmapToGallery(context: Context, bitmap: Bitmap, baseName: String): Boolean {
        val fileName = "$baseName.jpg"
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val values = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/IslamicQuranApp")
                put(MediaStore.Images.Media.IS_PENDING, 1)
            }
            val resolver = context.contentResolver
            val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values) ?: return false
            resolver.openOutputStream(uri)?.use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 95, out)
            }
            values.clear()
            values.put(MediaStore.Images.Media.IS_PENDING, 0)
            resolver.update(uri, values, null, null)
            true
        } else {
            val dir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "IslamicQuranApp")
            if (!dir.exists()) dir.mkdirs()
            val file = File(dir, fileName)
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 95, out)
            }
            val values = ContentValues().apply {
                put(MediaStore.Images.Media.DATA, file.absolutePath)
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            }
            context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            true
        }
    }

    fun shareDrawableImage(context: Context, drawableResId: Int, caption: String) {
        try {
            val bitmap = BitmapFactory.decodeResource(context.resources, drawableResId) ?: return
            shareBitmap(context, bitmap, caption)
        } catch (_: Exception) {
            shareFallbackText(context, caption)
        }
    }

    fun shareGalleryCard(context: Context, item: GalleryItem) {
        try {
            val bitmap = createCardBitmap(context, item)
            val caption = "${item.title}\n\n${item.calligraphyText}\n\n${item.subText}"
            shareBitmap(context, bitmap, caption)
        } catch (_: Exception) {
            val caption = "${item.title}\n\n${item.calligraphyText}\n\n${item.subText}"
            shareFallbackText(context, caption)
        }
    }

    private fun shareBitmap(context: Context, bitmap: Bitmap, caption: String) {
        val cachePath = File(context.cacheDir, "images")
        cachePath.mkdirs()
        val file = File(cachePath, "shared_islamic_card.jpg")
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 95, out)
        }
        val contentUri: Uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/jpeg"
            putExtra(Intent.EXTRA_STREAM, contentUri)
            putExtra(Intent.EXTRA_TEXT, caption)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(shareIntent, "مشاركة البطاقة الإسلامية"))
    }

    private fun shareFallbackText(context: Context, caption: String) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, caption)
            type = "text/plain"
        }
        context.startActivity(Intent.createChooser(sendIntent, "مشاركة البطاقة الإسلامية"))
    }

    /**
     * تحويل أي بطاقة أو لوحة من الـ ٥٠٠ بطاقة إسلامية إلى صورة عالية الدقة (خلفية موبايل وبطاقة دعوية جاهزة للحفظ).
     */
    fun createCardBitmap(context: Context, item: GalleryItem): Bitmap {
        val width = 1080
        val height = 1920
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        // 1. إذا كانت البطاقة تحتوي صورة حقيقية مخصصة كخلفية (مثل صور المعالم والخلفيات الجديدة)
        if (item.drawableRes != null) {
            val bgBitmap = BitmapFactory.decodeResource(context.resources, item.drawableRes)
            if (bgBitmap != null) {
                val srcRect = android.graphics.Rect(0, 0, bgBitmap.width, bgBitmap.height)
                val dstRect = android.graphics.Rect(0, 0, width, height)
                canvas.drawBitmap(bgBitmap, srcRect, dstRect, null)

                // Dark vignette overlay so calligraphy stands out
                val dimPaint = Paint().apply {
                    color = android.graphics.Color.argb(140, 0, 0, 0)
                }
                canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), dimPaint)
            }
        } else {
            // رسم تدرج لوني ملكي مخصص وفاخر
            val startColor = item.gradientColors.getOrNull(0)?.toInt() ?: android.graphics.Color.parseColor("#064E3B")
            val endColor = item.gradientColors.getOrNull(1)?.toInt() ?: android.graphics.Color.parseColor("#022C22")
            val bgPaint = Paint().apply {
                shader = LinearGradient(
                    0f, 0f, 0f, height.toFloat(),
                    startColor, endColor,
                    Shader.TileMode.CLAMP
                )
            }
            canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), bgPaint)

            // إطار زخرفي إسلامي هندسي ذهبي
            val borderPaint = Paint().apply {
                color = android.graphics.Color.parseColor("#EAB308")
                style = Paint.Style.STROKE
                strokeWidth = 6f
                isAntiAlias = true
            }
            val inset = 40f
            val rect = RectF(inset, inset, width - inset, height - inset)
            canvas.drawRoundRect(rect, 40f, 40f, borderPaint)

            val innerBorderPaint = Paint().apply {
                color = android.graphics.Color.argb(120, 234, 179, 8)
                style = Paint.Style.STROKE
                strokeWidth = 2f
                isAntiAlias = true
            }
            val innerRect = RectF(inset + 20f, inset + 20f, width - inset - 20f, height - inset - 20f)
            canvas.drawRoundRect(innerRect, 30f, 30f, innerBorderPaint)
        }

        // Header Category & App Title
        val headerPaint = TextPaint().apply {
            color = android.graphics.Color.parseColor("#FDE047")
            textSize = 44f
            isAntiAlias = true
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            textAlign = Paint.Align.CENTER
        }
        canvas.drawText("﴿ ${item.category} ﴾", (width / 2).toFloat(), 220f, headerPaint)

        val titlePaint = TextPaint().apply {
            color = android.graphics.Color.WHITE
            textSize = 52f
            isAntiAlias = true
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            textAlign = Paint.Align.CENTER
        }
        canvas.drawText(item.title, (width / 2).toFloat(), 320f, titlePaint)

        // Main Calligraphy Box in Center
        val calligraphyBoxPaint = Paint().apply {
            color = android.graphics.Color.argb(100, 0, 0, 0)
            isAntiAlias = true
        }
        val calligRect = RectF(80f, 500f, (width - 80).toFloat(), 1300f)
        canvas.drawRoundRect(calligRect, 32f, 32f, calligraphyBoxPaint)

        val calligBorder = Paint().apply {
            color = android.graphics.Color.argb(180, 234, 179, 8)
            style = Paint.Style.STROKE
            strokeWidth = 3f
            isAntiAlias = true
        }
        canvas.drawRoundRect(calligRect, 32f, 32f, calligBorder)

        // Draw Calligraphy Text
        val calligTextPaint = TextPaint().apply {
            color = android.graphics.Color.parseColor("#FEF08A")
            textSize = 58f
            isAntiAlias = true
            typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
        }
        val calligLayout = StaticLayout.Builder.obtain(
            item.calligraphyText,
            0,
            item.calligraphyText.length,
            calligTextPaint,
            width - 240
        ).setAlignment(Layout.Alignment.ALIGN_CENTER)
            .setLineSpacing(20f, 1.2f)
            .build()

        canvas.save()
        val textY = 540f + ((760f - calligLayout.height) / 2f).coerceAtLeast(20f)
        canvas.translate(120f, textY)
        calligLayout.draw(canvas)
        canvas.restore()

        // Subtext / Explanation at Bottom
        val subTextPaint = TextPaint().apply {
            color = android.graphics.Color.argb(230, 255, 255, 255)
            textSize = 36f
            isAntiAlias = true
        }
        val subLayout = StaticLayout.Builder.obtain(
            item.subText,
            0,
            item.subText.length,
            subTextPaint,
            width - 200
        ).setAlignment(Layout.Alignment.ALIGN_CENTER)
            .setLineSpacing(12f, 1.15f)
            .build()

        canvas.save()
        canvas.translate(100f, 1380f)
        subLayout.draw(canvas)
        canvas.restore()

        // Footer Brand
        val footerPaint = TextPaint().apply {
            color = android.graphics.Color.argb(160, 250, 204, 21)
            textSize = 32f
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
        }
        canvas.drawText("تطبيق نور الهدى والقرآن الكريم • بطاقة إسلامية دعوية", (width / 2).toFloat(), 1800f, footerPaint)

        return bitmap
    }
}
