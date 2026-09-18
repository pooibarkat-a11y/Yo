package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.QuranData
import com.example.model.MushafDisplayMode
import com.example.model.MushafPaperColor
import com.example.model.Reciter
import com.example.ui.components.SheikhAvatar
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.GoldAccent

@Composable
fun ReciterPickerDialog(
    currentReciter: Reciter,
    onReciterSelected: (Reciter) -> Unit,
    onDismiss: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(0) }
    val categories = listOf("الكل (${QuranData.reciters.size})", "👑 الحرمين الشريفين", "🏛️ عمالقة التلاوة", "🎙️ مشاهير القراء", "📜 ورش وقالون", "آية بآية")

    val filteredReciters = remember(searchQuery, selectedCategory) {
        QuranData.reciters.filter { reciter ->
            val matchesCategory = when (selectedCategory) {
                1 -> reciter.style.contains("الحرم") || reciter.nameArabic.contains("السديس") || reciter.nameArabic.contains("المعيقلي") || reciter.nameArabic.contains("الشريم") || reciter.nameArabic.contains("الدوسري") || reciter.nameArabic.contains("الجهني") || reciter.nameArabic.contains("البدير") || reciter.nameArabic.contains("الحذيفي") || reciter.nameArabic.contains("أيوب")
                2 -> reciter.nameArabic.contains("المنشاوي") || reciter.nameArabic.contains("عبد الباسط") || reciter.nameArabic.contains("الحصري") || reciter.nameArabic.contains("مصطفى إسماعيل") || reciter.nameArabic.contains("البنا") || reciter.nameArabic.contains("الطبلاوي")
                3 -> reciter.nameArabic.contains("العفاسي") || reciter.nameArabic.contains("الغامدي") || reciter.nameArabic.contains("العجمي") || reciter.nameArabic.contains("القطامي") || reciter.nameArabic.contains("الشاطري") || reciter.nameArabic.contains("عباد") || reciter.nameArabic.contains("الرفاعي")
                4 -> reciter.nameArabic.contains("ورش") || reciter.style.contains("ورش") || reciter.style.contains("قالون") || reciter.nameArabic.contains("قالون")
                5 -> reciter.isVerseByVerse
                else -> true
            }
            val matchesSearch = if (searchQuery.isBlank()) true else {
                reciter.nameArabic.contains(searchQuery, ignoreCase = true) ||
                        reciter.style.contains(searchQuery, ignoreCase = true) ||
                        reciter.nameEnglish.contains(searchQuery, ignoreCase = true)
            }
            matchesCategory && matchesSearch
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("إغلاق")
            }
        },
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "موسوعة كبار القراء",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "${QuranData.reciters.size} شيخاً وقارئاً معتمداً • استمع فوراً",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "إغلاق")
                }
            }
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("ابحث باسم الشيخ أو الرواية...") },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = "بحث")
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .testTag("reciter_search_input")
                )

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    itemsIndexed(categories) { index, catName ->
                        FilterChip(
                            selected = selectedCategory == index,
                            onClick = { selectedCategory = index },
                            label = { Text(catName, style = MaterialTheme.typography.labelSmall) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = EmeraldPrimary.copy(alpha = 0.2f),
                                selectedLabelColor = EmeraldPrimary
                            )
                        )
                    }
                }

                Text(
                    text = "عرض ${filteredReciters.size} قارئاً",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 6.dp)
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 420.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredReciters, key = { it.id }) { reciter ->
                        val isSelected = reciter.id == currentReciter.id
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f)
                            else MaterialTheme.colorScheme.surfaceVariant,
                            border = if (isSelected) BorderStroke(1.5.dp, GoldAccent) else null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onReciterSelected(reciter)
                                    onDismiss()
                                }
                                .testTag("reciter_item_${reciter.id}")
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 14.dp, vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    SheikhAvatar(
                                        nameArabic = reciter.nameArabic,
                                        imageUrl = reciter.imageUrl,
                                        sheikhId = reciter.id,
                                        isSelected = isSelected,
                                        size = 44.dp
                                    )

                                    Spacer(modifier = Modifier.width(12.dp))

                                    Column {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = reciter.nameArabic,
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Surface(
                                                shape = RoundedCornerShape(4.dp),
                                                color = if (reciter.isVerseByVerse) EmeraldPrimary.copy(alpha = 0.15f) else GoldAccent.copy(alpha = 0.15f)
                                            ) {
                                                Text(
                                                    text = if (reciter.isVerseByVerse) "آية بآية" else "مصحف كامل",
                                                    style = MaterialTheme.typography.labelSmall,
                                                    fontWeight = FontWeight.Bold,
                                                    color = if (reciter.isVerseByVerse) EmeraldPrimary else GoldAccent,
                                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                                )
                                            }
                                        }
                                        Text(
                                            text = reciter.style,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                                if (isSelected) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "تم الاختيار",
                                        tint = GoldAccent,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun MushafModeDialog(
    currentMode: MushafDisplayMode,
    currentPaper: MushafPaperColor,
    onModeSelected: (MushafDisplayMode) -> Unit,
    onPaperSelected: (MushafPaperColor) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("تم")
            }
        },
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AutoStories,
                    contentDescription = null,
                    tint = GoldAccent
                )
                Text(
                    text = "أشكال وأنماط المصحف الشريف",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        text = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item {
                    Text(
                        text = "طريقة عرض المصحف:",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                items(MushafDisplayMode.values()) { mode ->
                    val isSelected = mode == currentMode
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                        else MaterialTheme.colorScheme.surfaceVariant,
                        border = if (isSelected) BorderStroke(1.5.dp, GoldAccent) else null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onModeSelected(mode) }
                            .testTag("mushaf_mode_${mode.name}")
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = mode.titleArabic,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = mode.subtitleArabic,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = GoldAccent,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }

                item {
                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                    Text(
                        text = "لون وخلفية ورق المصحف:",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        MushafPaperColor.values().forEach { paper ->
                            val isPaperSelected = paper == currentPaper
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = when (paper) {
                                    MushafPaperColor.PARCHMENT -> Color(0xFFFBF6E9)
                                    MushafPaperColor.MADINAH_GREEN -> Color(0xFF0F3823)
                                    MushafPaperColor.DARK -> Color(0xFF1E2128)
                                    MushafPaperColor.PURE_WHITE -> Color(0xFFFFFFFF)
                                },
                                border = BorderStroke(
                                    if (isPaperSelected) 2.dp else 1.dp,
                                    if (isPaperSelected) GoldAccent else Color.Gray.copy(alpha = 0.4f)
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(68.dp)
                                    .clickable { onPaperSelected(paper) }
                                    .testTag("paper_color_${paper.name}")
                            ) {
                                Box(
                                    modifier = Modifier.padding(6.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = paper.titleArabic,
                                            fontSize = 10.sp,
                                            fontWeight = if (isPaperSelected) FontWeight.Bold else FontWeight.Normal,
                                            color = when (paper) {
                                                MushafPaperColor.PARCHMENT -> Color(0xFF2A2218)
                                                MushafPaperColor.MADINAH_GREEN -> Color(0xFFE8F5E9)
                                                MushafPaperColor.DARK -> Color(0xFFEDE8DF)
                                                MushafPaperColor.PURE_WHITE -> Color(0xFF1C1B1F)
                                            }
                                        )
                                        if (isPaperSelected) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = null,
                                                tint = GoldAccent,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}
