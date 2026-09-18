package com.example.ui.screens

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight

object TajweedHelper {
    val COLOR_MADD = Color(0xFFD32F2F)      // أحمر: المدود
    val COLOR_GHUNNAH = Color(0xFF2E7D32)   // أخضر: الغنة والإخفاء والإدغام
    val COLOR_QALQALAH = Color(0xFF1976D2)  // أزرق: القلقلة
    val COLOR_IQLAB = Color(0xFFF57C00)     // برتقالي: الإقلاب

    private val QALQALAH_LETTERS = setOf('ق', 'ط', 'ب', 'ج', 'د')
    private val IKHFA_LETTERS = setOf('ص', 'ذ', 'ث', 'ك', 'ج', 'ش', 'ق', 'س', 'د', 'ط', 'ز', 'ف', 'ت', 'ض', 'ظ')
    private val MADDAH_CHAR = '\u0653'
    private val SMALL_MEEM = '\u06E2'
    private val SHADDAH = '\u0651'

    fun annotateTajweed(text: String, defaultColor: Color, enabled: Boolean): AnnotatedString {
        if (!enabled) {
            return AnnotatedString(text)
        }

        return buildAnnotatedString {
            append(text)
            var i = 0
            val len = text.length

            while (i < len) {
                val ch = text[i]

                // 1. Check for Madd (Maddah above or ~)
                if (ch == MADDAH_CHAR || ch == '~' || ch == 'ٓ') {
                    val start = (i - 1).coerceAtLeast(0)
                    val end = (i + 1).coerceAtMost(len)
                    addStyle(SpanStyle(color = COLOR_MADD, fontWeight = FontWeight.Bold), start, end)
                }

                // 2. Check for Iqlab (Small Meem فوق النون أو التنوين)
                else if (ch == SMALL_MEEM || ch == 'ۭ') {
                    val start = (i - 1).coerceAtLeast(0)
                    val end = (i + 1).coerceAtMost(len)
                    addStyle(SpanStyle(color = COLOR_IQLAB, fontWeight = FontWeight.Bold), start, end)
                }

                // 3. Check for Shaddah on Nun or Meem (Ghunnah Mushaddadah)
                else if ((ch == 'ن' || ch == 'م') && i + 1 < len && text[i + 1] == SHADDAH) {
                    addStyle(SpanStyle(color = COLOR_GHUNNAH, fontWeight = FontWeight.Bold), i, i + 2)
                    i++
                }

                // 4. Check for Qalqalah (قطب جد)
                else if (ch in QALQALAH_LETTERS) {
                    val isNextSukun = i + 1 < len && (text[i + 1] == '\u0652' || text[i + 1] == '\u06E1')
                    val isWordEnd = i + 1 == len || text[i + 1] == ' ' || text[i + 1] == '۝'
                    if (isNextSukun || isWordEnd) {
                        val end = if (isNextSukun) i + 2 else i + 1
                        addStyle(SpanStyle(color = COLOR_QALQALAH, fontWeight = FontWeight.Bold), i, end)
                    }
                }

                // 5. Ayah marker symbol styling (۝)
                else if (ch == '۝') {
                    val endMarker = (i + 5).coerceAtMost(len)
                    addStyle(SpanStyle(color = Color(0xFFD4AF37), fontWeight = FontWeight.Bold), i, endMarker)
                }

                i++
            }
        }
    }
}
