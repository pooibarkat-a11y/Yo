package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Downloading
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.QuranData
import com.example.data.QuranIndexData
import com.example.model.RevelationType
import com.example.model.Surah
import com.example.ui.theme.EmeraldDark
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.GoldAccent
import com.example.viewmodel.AppViewModel

@Composable
fun SurahIndexView(
    viewModel: AppViewModel,
    onSurahSelected: (Surah) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("السور (١١٤)", "الأجزاء (٣٠)", "سور الفضائل", "العلامات")

    var searchQuery by remember { mutableStateOf("") }
    var selectedCategoryFilter by remember { mutableStateOf("all") } // all, makki, madani, tiwal, virtues, amma
    var sortByLength by remember { mutableStateOf(false) }

    val userProgress by viewModel.userProgress.collectAsState()
    val lastReadSurahId = userProgress?.lastReadSurahId ?: 1
    val lastReadSurah = remember(lastReadSurahId) {
        QuranData.surahs.find { it.id == lastReadSurahId } ?: QuranData.surahs.first()
    }
    val bookmarks by viewModel.bookmarks.collectAsState(initial = emptyList())

    val filteredSurahs = remember(searchQuery, selectedCategoryFilter, sortByLength) {
        val list = QuranData.surahs.filter { surah ->
            val matchesCategory = when (selectedCategoryFilter) {
                "makki" -> surah.revelationType == RevelationType.MAKKI
                "madani" -> surah.revelationType == RevelationType.MADANI
                "tiwal" -> QuranIndexData.longSurahsIds.contains(surah.id)
                "virtues" -> QuranIndexData.virtuesList.any { it.surahId == surah.id }
                "amma" -> QuranIndexData.juzAmmaSurahsIds.contains(surah.id)
                else -> true
            }
            val matchesQuery = if (searchQuery.isBlank()) true else {
                val q = searchQuery.trim()
                surah.nameArabic.contains(q, ignoreCase = true) ||
                        surah.nameEnglish.contains(q, ignoreCase = true) ||
                        surah.id.toString() == q ||
                        surah.startPage.toString() == q ||
                        surah.juzNumber.toString() == q
            }
            matchesCategory && matchesQuery
        }

        if (sortByLength) {
            list.sortedByDescending { it.versesCount }
        } else {
            list.sortedBy { it.id }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        // Hero Banner: Last Read Progress with direct continue reading
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            EmeraldDark,
                            EmeraldPrimary,
                            Color(0xFF0F766E)
                        )
                    )
                )
                .clickable { onSurahSelected(lastReadSurah) }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Bookmark,
                            contentDescription = null,
                            tint = GoldAccent,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "متابعة التلاوة من حيث توقفت",
                            style = MaterialTheme.typography.labelMedium,
                            color = GoldAccent,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "سورة ${lastReadSurah.nameArabic}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "صفحة ${lastReadSurah.startPage} • الجزء ${lastReadSurah.juzNumber} • ${lastReadSurah.versesCount} آية",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.85f)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = { viewModel.audioPlayer.playSurah(lastReadSurah) },
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.18f))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Headphones,
                            contentDescription = "استماع مباشر",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = { onSurahSelected(lastReadSurah) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GoldAccent,
                            contentColor = EmeraldDark
                        ),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Text("متابعة", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Main 4 Categorization Tabs: Surahs, Juzs, Virtues, Bookmarks
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = EmeraldPrimary,
            modifier = Modifier.clip(RoundedCornerShape(14.dp))
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            fontSize = 12.sp,
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        when (selectedTabIndex) {
            0 -> {
                // TAB 0: ALL 114 SURAHS WITH FILTER CHIPS & SEARCH
                // Search field
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("ابحث عن سورة بالاسم، الرقم، الصفحة، أو الجزء...", fontSize = 12.sp) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "بحث",
                            tint = EmeraldPrimary
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(Icons.Default.Clear, contentDescription = "مسح")
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = EmeraldPrimary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("surah_index_search")
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Categorization Filter Chips
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    item {
                        FilterChip(
                            selected = selectedCategoryFilter == "all",
                            onClick = { selectedCategoryFilter = "all" },
                            label = { Text("الكل (١١٤)") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = EmeraldPrimary,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                    item {
                        FilterChip(
                            selected = selectedCategoryFilter == "makki",
                            onClick = { selectedCategoryFilter = "makki" },
                            label = { Text("🕋 مكية (٨٦)") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFFD97706),
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                    item {
                        FilterChip(
                            selected = selectedCategoryFilter == "madani",
                            onClick = { selectedCategoryFilter = "madani" },
                            label = { Text("🕌 مدنية (٢٨)") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFF059669),
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                    item {
                        FilterChip(
                            selected = selectedCategoryFilter == "tiwal",
                            onClick = { selectedCategoryFilter = "tiwal" },
                            label = { Text("📜 السبع الطوال") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFF4F46E5),
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                    item {
                        FilterChip(
                            selected = selectedCategoryFilter == "virtues",
                            onClick = { selectedCategoryFilter = "virtues" },
                            label = { Text("💎 سور الفضائل") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFF9333EA),
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                    item {
                        FilterChip(
                            selected = selectedCategoryFilter == "amma",
                            onClick = { selectedCategoryFilter = "amma" },
                            label = { Text("🌿 جزء عم") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFF0891B2),
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }

                // Row for sorting & result count
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "عرض ${filteredSurahs.size} سورة",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (sortByLength) EmeraldPrimary.copy(alpha = 0.15f) else Color.Transparent,
                        modifier = Modifier.clickable { sortByLength = !sortByLength }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Sort,
                                contentDescription = null,
                                tint = if (sortByLength) EmeraldPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (sortByLength) "مرتب بالأطول" else "ترتيب المصحف",
                                style = MaterialTheme.typography.labelSmall,
                                color = if (sortByLength) EmeraldPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                                fontWeight = if (sortByLength) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }

                // Surahs LazyColumn
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(bottom = 120.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(filteredSurahs, key = { it.id }) { surah ->
                        val isLastRead = surah.id == lastReadSurahId

                        SurahItemCard(
                            surah = surah,
                            isLastRead = isLastRead,
                            viewModel = viewModel,
                            onReadClick = { onSurahSelected(surah) },
                            onListenClick = { viewModel.audioPlayer.playSurah(surah) }
                        )
                    }
                }
            }

            1 -> {
                // TAB 1: 30 JUZ INDEX
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(bottom = 120.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(QuranIndexData.allJuzList) { juz ->
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            ),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    val surah = QuranData.surahs.find { it.id == juz.startSurahId } ?: QuranData.surahs.first()
                                    onSurahSelected(surah)
                                }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    // Juz Number Badge
                                    Surface(
                                        shape = RoundedCornerShape(14.dp),
                                        color = EmeraldPrimary.copy(alpha = 0.15f),
                                        border = BorderStroke(1.5.dp, EmeraldPrimary),
                                        modifier = Modifier.size(46.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Text(
                                                text = "${juz.number}",
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.Bold,
                                                color = EmeraldPrimary
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.width(14.dp))

                                    Column {
                                        Text(
                                            text = juz.titleArabic,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = "«${juz.startAyahText}»",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = EmeraldPrimary,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = "${juz.includedSurahs} • صفحة ${juz.startPage}",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                                Button(
                                    onClick = {
                                        val surah = QuranData.surahs.find { it.id == juz.startSurahId } ?: QuranData.surahs.first()
                                        onSurahSelected(surah)
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                                    shape = RoundedCornerShape(10.dp),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Icon(Icons.Default.MenuBook, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("ص ${juz.startPage}", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }

            2 -> {
                // TAB 2: VIRTUOUS SURAHS WITH REWARDS & AUTHENTIC HADITHS
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 120.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(QuranIndexData.virtuesList) { virtue ->
                        val surah = QuranData.surahs.find { it.id == virtue.surahId } ?: QuranData.surahs.first()
                        val badgeColor = Color(virtue.badgeColorHex)

                        Card(
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            ),
                            border = BorderStroke(1.dp, badgeColor.copy(alpha = 0.4f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Surface(
                                            shape = RoundedCornerShape(10.dp),
                                            color = badgeColor.copy(alpha = 0.15f),
                                            border = BorderStroke(1.dp, badgeColor)
                                        ) {
                                            Text(
                                                text = "سورة ${virtue.nameArabic}",
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.Bold,
                                                color = badgeColor,
                                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                            )
                                        }

                                        Spacer(modifier = Modifier.width(8.dp))

                                        Text(
                                            text = "${surah.versesCount} آية • صفحة ${surah.startPage}",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }

                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        val reciter = viewModel.audioPlayer.state.collectAsState().value.selectedReciter
                                        val isVirtueSurahDownloaded = viewModel.isSurahDownloaded(surah.id, reciter.id)
                                        val isVirtueDownloading = viewModel.activeAudioDownloads.collectAsState().value.contains("${reciter.id}_${surah.id}")

                                        IconButton(
                                            onClick = {
                                                if (isVirtueSurahDownloaded) {
                                                    Toast.makeText(context, "سورة ${surah.nameArabic} محملة بالكامل أوفلاين ✓", Toast.LENGTH_SHORT).show()
                                                } else if (isVirtueDownloading) {
                                                    Toast.makeText(context, "جاري تنزيل سورة ${surah.nameArabic}...", Toast.LENGTH_SHORT).show()
                                                } else {
                                                    viewModel.downloadSurah(surah, reciter)
                                                    Toast.makeText(context, "بدأ تنزيل سورة ${surah.nameArabic} للاستماع بدون نت 📥", Toast.LENGTH_SHORT).show()
                                                }
                                            },
                                            modifier = Modifier
                                                .size(34.dp)
                                                .clip(CircleShape)
                                                .background(
                                                    if (isVirtueSurahDownloaded) EmeraldPrimary.copy(alpha = 0.15f)
                                                    else if (isVirtueDownloading) GoldAccent.copy(alpha = 0.2f)
                                                    else badgeColor.copy(alpha = 0.1f)
                                                )
                                        ) {
                                            if (isVirtueDownloading) {
                                                CircularProgressIndicator(
                                                    modifier = Modifier.size(16.dp),
                                                    strokeWidth = 2.dp,
                                                    color = GoldAccent
                                                )
                                            } else {
                                                Icon(
                                                    imageVector = if (isVirtueSurahDownloaded) Icons.Default.CheckCircle else Icons.Default.Download,
                                                    contentDescription = "تنزيل السورة",
                                                    tint = if (isVirtueSurahDownloaded) EmeraldPrimary else badgeColor,
                                                    modifier = Modifier.size(17.dp)
                                                )
                                            }
                                        }

                                        Spacer(modifier = Modifier.width(6.dp))

                                        IconButton(
                                            onClick = { viewModel.audioPlayer.playSurah(surah) },
                                            modifier = Modifier
                                                .size(36.dp)
                                                .clip(CircleShape)
                                                .background(badgeColor.copy(alpha = 0.15f))
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.PlayArrow,
                                                contentDescription = "استماع",
                                                tint = badgeColor,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }

                                        Spacer(modifier = Modifier.width(6.dp))

                                        Button(
                                            onClick = { onSurahSelected(surah) },
                                            colors = ButtonDefaults.buttonColors(containerColor = badgeColor),
                                            shape = RoundedCornerShape(10.dp),
                                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                                        ) {
                                            Text("قراءة", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                Text(
                                    text = virtue.virtueTitle,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = virtue.virtueDescription,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = MaterialTheme.colorScheme.surface,
                                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = virtue.hadithText,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.padding(10.dp),
                                        lineHeight = 18.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            3 -> {
                // TAB 3: BOOKMARKS & SAVED AYAT
                if (bookmarks.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 100.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.BookmarkBorder,
                                contentDescription = null,
                                tint = EmeraldPrimary,
                                modifier = Modifier.size(64.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "لا توجد علامات مرجعية محفوظة بعد",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "أثناء قراءة أي سورة في المصحف، اضغط على أيقونة العلامة المرجعية بجانب الآية لحفظها والرجوع إليها هنا في أي وقت.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(bottom = 120.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(bookmarks) { bookmark ->
                            val surah = QuranData.surahs.find { it.id == bookmark.surahId } ?: QuranData.surahs.first()

                            Card(
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onSurahSelected(surah) }
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(14.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = "سورة ${bookmark.surahName} • آية ${bookmark.ayahNumber}",
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = EmeraldPrimary
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = bookmark.ayahText,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }

                                    Button(
                                        onClick = { onSurahSelected(surah) },
                                        shape = RoundedCornerShape(10.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                                    ) {
                                        Text("انتقال", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SurahItemCard(
    surah: Surah,
    isLastRead: Boolean,
    viewModel: AppViewModel,
    onReadClick: () -> Unit,
    onListenClick: () -> Unit
) {
    val context = LocalContext.current
    val isMakki = surah.revelationType == RevelationType.MAKKI
    val revelationColor = if (isMakki) Color(0xFFD97706) else Color(0xFF059669)

    val audioState by viewModel.audioPlayer.state.collectAsState()
    val reciter = audioState.selectedReciter
    val isDownloaded = viewModel.isSurahDownloaded(surah.id, reciter.id)
    val activeDownloads by viewModel.activeAudioDownloads.collectAsState()
    val isDownloading = activeDownloads.contains("${reciter.id}_${surah.id}")

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isLastRead) EmeraldPrimary.copy(alpha = 0.1f)
            else MaterialTheme.colorScheme.surfaceVariant
        ),
        border = if (isLastRead) BorderStroke(1.5.dp, GoldAccent)
        else BorderStroke(0.5.dp, MaterialTheme.colorScheme.outlineVariant),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onReadClick() }
            .testTag("surah_item_${surah.id}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Number badge and details
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                // Surah Number inside geometric badge
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (isLastRead) GoldAccent.copy(alpha = 0.25f)
                    else MaterialTheme.colorScheme.surface,
                    border = BorderStroke(
                        1.dp,
                        if (isLastRead) GoldAccent else MaterialTheme.colorScheme.outlineVariant
                    ),
                    modifier = Modifier.size(46.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "${surah.id}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = if (isLastRead) GoldAccent else EmeraldPrimary
                        )
                    }
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "سورة ${surah.nameArabic}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        if (isLastRead) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = GoldAccent.copy(alpha = 0.2f)
                            ) {
                                Text(
                                    text = "آخر قراءة",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = GoldAccent,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(3.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Revelation Tag with distinctive styling
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = revelationColor.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = if (isMakki) "🕋 مكية" else "🕌 مدنية",
                                style = MaterialTheme.typography.labelSmall,
                                color = revelationColor,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }

                        Text(text = "•", color = MaterialTheme.colorScheme.onSurfaceVariant)

                        Text(
                            text = "${surah.versesCount} آية",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Text(text = "•", color = MaterialTheme.colorScheme.onSurfaceVariant)

                        Text(
                            text = "ص ${surah.startPage}",
                            style = MaterialTheme.typography.bodySmall,
                            color = EmeraldPrimary,
                            fontWeight = FontWeight.Bold
                        )

                        Text(text = "•", color = MaterialTheme.colorScheme.onSurfaceVariant)

                        Text(
                            text = "ج ${surah.juzNumber}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Action Buttons: Download, Listen & Read
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Quick Download Button
                IconButton(
                    onClick = {
                        if (isDownloaded) {
                            Toast.makeText(context, "سورة ${surah.nameArabic} محملة بالكامل أوفلاين بصوت ${reciter.nameArabic} ✓", Toast.LENGTH_SHORT).show()
                        } else if (isDownloading) {
                            Toast.makeText(context, "جاري تنزيل سورة ${surah.nameArabic} حالياً...", Toast.LENGTH_SHORT).show()
                        } else {
                            viewModel.downloadSurah(surah, reciter)
                            Toast.makeText(context, "بدأ تنزيل سورة ${surah.nameArabic} بصوت ${reciter.nameArabic} للاستماع بدون نت 📥", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(
                            if (isDownloaded) EmeraldPrimary.copy(alpha = 0.15f)
                            else if (isDownloading) GoldAccent.copy(alpha = 0.2f)
                            else MaterialTheme.colorScheme.surface
                        )
                ) {
                    if (isDownloading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(18.dp),
                            strokeWidth = 2.dp,
                            color = GoldAccent
                        )
                    } else {
                        Icon(
                            imageVector = if (isDownloaded) Icons.Default.CheckCircle else Icons.Default.Download,
                            contentDescription = if (isDownloaded) "محملة" else "تنزيل السورة",
                            tint = if (isDownloaded) EmeraldPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(6.dp))

                IconButton(
                    onClick = onListenClick,
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(EmeraldPrimary.copy(alpha = 0.12f))
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "استماع للسورة",
                        tint = EmeraldPrimary,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.width(6.dp))

                Button(
                    onClick = onReadClick,
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = EmeraldPrimary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.MenuBook,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "قراءة", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
