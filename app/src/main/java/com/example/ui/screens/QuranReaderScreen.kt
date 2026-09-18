package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import com.example.ui.components.BismillahRibbon
import com.example.ui.components.RoyalPageFrame
import com.example.ui.components.SurahHeaderCartouche
import com.example.ui.components.toArabicIndicDigits
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.QuranData
import com.example.model.Ayah
import com.example.model.MushafDisplayMode
import com.example.model.MushafPaperColor
import com.example.model.QuranTab
import com.example.model.Surah
import com.example.ui.components.SheikhAvatar
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.LocalIsTablet
import com.example.viewmodel.AppViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuranReaderScreen(
    viewModel: AppViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    val quranTab by viewModel.quranTab.collectAsState()
    val selectedSurah by viewModel.selectedSurah.collectAsState()
    val ayahs by viewModel.currentAyahs.collectAsState()
    val targetAyahNum by viewModel.targetAyahNumber.collectAsState()
    val fontSizeSp by viewModel.fontSizeSp.collectAsState()
    val isTablet = LocalIsTablet.current
    val effectiveFontSize = if (isTablet) (fontSizeSp * 1.25f).toInt() else fontSizeSp
    val mushafMode by viewModel.mushafDisplayMode.collectAsState()
    val mushafPaper by viewModel.mushafPaperColor.collectAsState()
    val isLoadingOnline by viewModel.isLoadingSurahOnline.collectAsState()
    val audioState by viewModel.audioPlayer.state.collectAsState()
    val bookmarks by viewModel.bookmarks.collectAsState()
    val favorites by viewModel.favorites.collectAsState()

    var showSurahChooser by remember { mutableStateOf(false) }
    var showMushafModeDialog by remember { mutableStateOf(false) }
    var showReciterDialog by remember { mutableStateOf(false) }
    var showFontControls by remember { mutableStateOf(false) }
    var showSearchDialog by remember { mutableStateOf(false) }
    var selectedAyahForTafsir by remember { mutableStateOf<Ayah?>(null) }
    var selectedAyahForNote by remember { mutableStateOf<Ayah?>(null) }
    var selectedAyahForCardShare by remember { mutableStateOf<Ayah?>(null) }
    var noteInputText by remember { mutableStateOf("") }

    // Scroll to target ayah when opened
    LaunchedEffect(targetAyahNum, selectedSurah) {
        if (targetAyahNum > 1 && ayahs.isNotEmpty()) {
            val index = ayahs.indexOfFirst { it.ayahNumber == targetAyahNum }
            if (index >= 0) {
                listState.animateScrollToItem(index)
            }
        } else {
            listState.scrollToItem(0)
        }
    }

    // Follow the reciter's voice in real-time: keep Surah and Ayah synchronized with audio playback
    LaunchedEffect(audioState.currentSurah?.id, audioState.currentAyahNumber, audioState.isPlaying) {
        val playingSurah = audioState.currentSurah
        if (playingSurah != null && audioState.isPlaying) {
            if (playingSurah.id != selectedSurah.id) {
                // If reciter transitions to next surah, follow to that surah automatically
                viewModel.openSurah(playingSurah, audioState.currentAyahNumber)
            } else if (ayahs.isNotEmpty()) {
                val index = ayahs.indexOfFirst { it.ayahNumber == audioState.currentAyahNumber }
                if (index >= 0) {
                    listState.animateScrollToItem(index)
                }
            }
        }
    }

    // Paper color background and text colors
    val paperBgColor = when (mushafPaper) {
        MushafPaperColor.PARCHMENT -> Color(0xFFFBF6E9)
        MushafPaperColor.MADINAH_GREEN -> Color(0xFF092317)
        MushafPaperColor.DARK -> Color(0xFF13161A)
        MushafPaperColor.PURE_WHITE -> Color(0xFFFFFFFF)
    }

    val paperTextColor = when (mushafPaper) {
        MushafPaperColor.PARCHMENT -> Color(0xFF221A0F)
        MushafPaperColor.MADINAH_GREEN -> Color(0xFFF1EADF)
        MushafPaperColor.DARK -> Color(0xFFE8EEF5)
        MushafPaperColor.PURE_WHITE -> Color(0xFF1A1A1A)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(paperBgColor)
    ) {
        // Top 2 Primary Tabs: "فهرس السور" vs "المصحف الشريف"
        TabRow(
            selectedTabIndex = if (quranTab == QuranTab.INDEX) 0 else 1,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth()
        ) {
            Tab(
                selected = quranTab == QuranTab.INDEX,
                onClick = { viewModel.setQuranTab(QuranTab.INDEX) },
                text = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.List,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "فهرس السور (١١٤)",
                            fontWeight = if (quranTab == QuranTab.INDEX) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                },
                modifier = Modifier.testTag("tab_quran_index")
            )
            Tab(
                selected = quranTab == QuranTab.READER,
                onClick = { viewModel.setQuranTab(QuranTab.READER) },
                text = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.MenuBook,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "المصحف الشريف",
                            fontWeight = if (quranTab == QuranTab.READER) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                },
                modifier = Modifier.testTag("tab_quran_reader")
            )
        }

        // View 1: 114 Surah Index View
        if (quranTab == QuranTab.INDEX) {
            SurahIndexView(
                viewModel = viewModel,
                onSurahSelected = { surah ->
                    viewModel.openSurah(surah)
                }
            )
        } else if (mushafMode == MushafDisplayMode.PHYSICAL_PAGES) {
            // View 2: Authentic 604 Physical Pages Viewer (King Fahd Madinah Mushaf with RTL Page Flipping)
            MushafPhysicalPagesViewer(
                viewModel = viewModel,
                modifier = Modifier.fillMaxSize(),
                onOpenSurahChooser = { showSurahChooser = true },
                onOpenReciterPicker = { showReciterDialog = true },
                onOpenMushafModes = { showMushafModeDialog = true }
            )
        } else {
            // View 3: Alternative Study Modes (Continuous text, Tajweed colors, Inline Tafsir, Hifz, Classic cards)
            // Top Reader Navigation Toolbar
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                tonalElevation = 3.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Surah Navigation: [Prev] [Surah Name Dropdown] [Next]
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = { viewModel.previousSurah() },
                                enabled = selectedSurah.id > 1,
                                modifier = Modifier.size(34.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "السورة السابقة",
                                    tint = if (selectedSurah.id > 1) MaterialTheme.colorScheme.primary else Color.Gray.copy(alpha = 0.3f),
                                    modifier = Modifier.size(18.dp)
                                )
                            }

                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = MaterialTheme.colorScheme.surface,
                                modifier = Modifier
                                    .clickable { showSurahChooser = true }
                                    .testTag("surah_selector_button")
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "سورة ${selectedSurah.nameArabic}",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(text = "▼", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }

                            IconButton(
                                onClick = { viewModel.nextSurah() },
                                enabled = selectedSurah.id < 114,
                                modifier = Modifier.size(34.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = "السورة التالية",
                                    tint = if (selectedSurah.id < 114) MaterialTheme.colorScheme.primary else Color.Gray.copy(alpha = 0.3f),
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        // Toolbar actions: Download, Search, Mushaf Styles, Reciter, Font Size
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // Download Surah Audio Button
                            val reciter = audioState.selectedReciter
                            val isDownloaded = viewModel.isSurahDownloaded(selectedSurah.id, reciter.id)
                            val downloadMap by viewModel.audioDownloadsProgress.collectAsState()
                            val key = "${reciter.id}_${selectedSurah.id}"
                            val isDownloading = viewModel.activeAudioDownloads.collectAsState().value.contains(key)

                            IconButton(
                                onClick = {
                                    if (!isDownloaded && !isDownloading) {
                                        viewModel.downloadSurah(selectedSurah, reciter)
                                        Toast.makeText(context, "جاري تنزيل سورة ${selectedSurah.nameArabic} بصوت ${reciter.nameArabic} للاستماع أوفلاين...", Toast.LENGTH_SHORT).show()
                                    } else if (isDownloaded) {
                                        Toast.makeText(context, "سورة ${selectedSurah.nameArabic} مُحمَّلة بالكامل أوفلاين بصوت ${reciter.nameArabic} ✓", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                modifier = Modifier.testTag("download_surah_audio_button")
                            ) {
                                Icon(
                                    imageVector = if (isDownloaded) Icons.Default.CheckCircle else Icons.Default.Download,
                                    contentDescription = "تنزيل السورة صوتياً",
                                    tint = if (isDownloaded) EmeraldPrimary else if (isDownloading) GoldAccent else MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            // Mushaf Styles (شكل المصحف)
                            IconButton(
                                onClick = { showMushafModeDialog = true },
                                modifier = Modifier.testTag("mushaf_style_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AutoStories,
                                    contentDescription = "أشكال المصحف",
                                    tint = GoldAccent,
                                    modifier = Modifier.size(22.dp)
                                )
                            }

                            // Famous Reciters (شيوخ وقراء) with Real Portrait Photo
                            Surface(
                                shape = CircleShape,
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .clickable { showReciterDialog = true }
                                    .testTag("reciter_dialog_button"),
                                color = Color.Transparent
                            ) {
                                SheikhAvatar(
                                    nameArabic = reciter.nameArabic,
                                    imageUrl = reciter.imageUrl,
                                    sheikhId = reciter.id,
                                    isSelected = audioState.isPlaying,
                                    size = 30.dp
                                )
                            }

                            // Font size
                            IconButton(
                                onClick = { showFontControls = !showFontControls },
                                modifier = Modifier.testTag("font_controls_toggle")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.FormatSize,
                                    contentDescription = "حجم الخط",
                                    tint = if (showFontControls) EmeraldLight else MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.size(22.dp)
                                )
                            }

                            // Search
                            IconButton(
                                onClick = { showSearchDialog = true },
                                modifier = Modifier.testTag("quran_search_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "بحث في القرآن",
                                    tint = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                    }

                    // Direct Mushaf Forms Bar (أشكال قراءة المصحف الشريف)
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        items(MushafDisplayMode.values()) { mode ->
                            val isSelected = mushafMode == mode
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = if (isSelected) GoldAccent else MaterialTheme.colorScheme.surface,
                                border = BorderStroke(
                                    1.dp,
                                    if (isSelected) GoldAccent else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                                ),
                                modifier = Modifier
                                    .clickable { viewModel.setMushafDisplayMode(mode) }
                                    .testTag("mushaf_mode_chip_${mode.name}")
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = when (mode) {
                                            MushafDisplayMode.PHYSICAL_PAGES -> "📖 "
                                            MushafDisplayMode.CONTINUOUS_PAGE -> "📜 "
                                            MushafDisplayMode.TAJWEED -> "🎨 "
                                            MushafDisplayMode.TAFSIR_INLINE -> "💡 "
                                            MushafDisplayMode.HIFZ_MODE -> "🧠 "
                                            MushafDisplayMode.LARGE_FONT -> "🔍 "
                                            MushafDisplayMode.CLASSIC -> "📋 "
                                        },
                                        fontSize = 12.sp
                                    )
                                    Text(
                                        text = mode.titleArabic,
                                        fontSize = 11.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSelected) Color.Black else MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }

                    // Paper Colors Bar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 2.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "نوع الورق:",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        MushafPaperColor.values().forEach { paper ->
                            val isSelected = mushafPaper == paper
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = if (isSelected) EmeraldPrimary else MaterialTheme.colorScheme.surface,
                                border = if (isSelected) null else BorderStroke(0.5.dp, MaterialTheme.colorScheme.outlineVariant),
                                modifier = Modifier.clickable { viewModel.setMushafPaperColor(paper) }
                            ) {
                                Text(
                                    text = paper.titleArabic,
                                    fontSize = 10.sp,
                                    color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }
                    }

                    // Online loading indicator for verified Uthmani script & Tafsir Al-Muyassar
                    if (isLoadingOnline) {
                        LinearProgressIndicator(
                            modifier = Modifier.fillMaxWidth(),
                            color = GoldAccent
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 2.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "جاري جلب نص السورة كاملاً برواية حفص والتفسير الميسر...",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }

            // Font Size Adjustment Slider (Adaptive Mobile & Tablet)
            AnimatedVisibility(visible = showFontControls) {
                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = 2.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isTablet) "خط التابلت (${fontSizeSp}sp 💻)" else "خط الهاتف (${fontSizeSp}sp 📱)",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        IconButton(onClick = { viewModel.setFontSize((fontSizeSp - 2).coerceAtLeast(14)) }) {
                            Icon(Icons.Default.Remove, contentDescription = "تصغير", modifier = Modifier.size(18.dp))
                        }
                        Slider(
                            value = fontSizeSp.toFloat(),
                            onValueChange = { viewModel.setFontSize(it.toInt()) },
                            valueRange = if (isTablet) 20f..56f else 16f..42f,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = { viewModel.setFontSize((fontSizeSp + 2).coerceAtMost(56)) }) {
                            Icon(Icons.Default.Add, contentDescription = "تكبير", modifier = Modifier.size(18.dp))
                        }
                    }
                }
            }

            // Tajweed Legend Bar (when in Tajweed mode)
            if (mushafMode == MushafDisplayMode.TAJWEED) {
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        LegendChip(color = TajweedHelper.COLOR_MADD, label = "مدود")
                        LegendChip(color = TajweedHelper.COLOR_GHUNNAH, label = "غنة وإخفاء")
                        LegendChip(color = TajweedHelper.COLOR_QALQALAH, label = "قلقلة")
                        LegendChip(color = TajweedHelper.COLOR_IQLAB, label = "إقلاب")
                    }
                }
            }

            // Active Mushaf Style Ribbon
            Surface(
                color = when (mushafPaper) {
                    MushafPaperColor.PARCHMENT -> Color(0xFFF1E6D0)
                    MushafPaperColor.MADINAH_GREEN -> Color(0xFF0F3823)
                    MushafPaperColor.DARK -> Color(0xFF1E2128)
                    MushafPaperColor.PURE_WHITE -> Color(0xFFF5F5F5)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 6.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = GoldAccent.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = mushafMode.titleArabic,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = GoldAccent,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${selectedSurah.revelationType.arabicName} • ${selectedSurah.versesCount} آيات",
                            style = MaterialTheme.typography.labelSmall,
                            color = paperTextColor.copy(alpha = 0.8f)
                        )
                    }

                    Text(
                        text = "الجزء ${selectedSurah.juzNumber} • صفحة ${selectedSurah.startPage}",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = GoldAccent
                    )
                }
            }

            val isContinuousStyle = mushafMode == MushafDisplayMode.CONTINUOUS_PAGE ||
                    mushafMode == MushafDisplayMode.LARGE_FONT ||
                    mushafMode == MushafDisplayMode.TAJWEED

            // Bismillah Header for Card Mode (Continuous mode has BismillahRibbon inside RoyalPageFrame)
            if (!isContinuousStyle && selectedSurah.id != 9) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = GoldAccent
                    )
                }
            }

            // Continuous Flowing Page Layout Mode
            if (isContinuousStyle) {
                ContinuousMushafPageView(
                    ayahs = ayahs,
                    fontSizeSp = effectiveFontSize,
                    paperTextColor = paperTextColor,
                    audioState = audioState,
                    selectedSurah = selectedSurah,
                    mushafMode = mushafMode,
                    onAyahClick = { ayah ->
                        selectedAyahForTafsir = ayah
                    },
                    modifier = Modifier.weight(1f)
                )
            } else {
                // Ayahs Cards List (Classic, Tajweed, Inline Tafsir, or Hifz Mode)
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    contentPadding = PaddingValues(bottom = 120.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(ayahs, key = { "${it.surahId}_${it.ayahNumber}" }) { ayah ->
                        val isPlayingCurrent = audioState.currentSurah?.id == ayah.surahId && audioState.currentAyahNumber == ayah.ayahNumber
                        val isBookmarked = bookmarks.any { it.surahId == ayah.surahId && it.ayahNumber == ayah.ayahNumber }
                        val isFav = favorites.any { it.surahId == ayah.surahId && it.ayahNumber == ayah.ayahNumber }

                        AyahCardEnhanced(
                            ayah = ayah,
                            surah = selectedSurah,
                            fontSizeSp = effectiveFontSize,
                            mushafMode = mushafMode,
                            paperTextColor = paperTextColor,
                            isPlaying = isPlayingCurrent,
                            isBookmarked = isBookmarked,
                            isFavorite = isFav,
                            onPlay = { viewModel.audioPlayer.playAyah(selectedSurah, ayah.ayahNumber) },
                            onBookmark = { viewModel.toggleBookmark(ayah, selectedSurah.nameArabic) },
                            onFavorite = { viewModel.toggleFavorite(ayah, selectedSurah.nameArabic) },
                            onShowTafsir = { selectedAyahForTafsir = ayah },
                            onAddNote = {
                                selectedAyahForNote = ayah
                                noteInputText = ""
                            },
                            onCopy = {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText("Quran Ayah", "${ayah.textUthmani}\n[سورة ${selectedSurah.nameArabic}: ${ayah.ayahNumber}]")
                                clipboard.setPrimaryClip(clip)
                                Toast.makeText(context, "تم نسخ الآية الكريمة", Toast.LENGTH_SHORT).show()
                            },
                            onShare = {
                                selectedAyahForCardShare = ayah
                            }
                        )
                    }
                }
            }
        }
    }

    // Reciter Picker Dialog (22 famous reciters)
    if (showReciterDialog) {
        ReciterPickerDialog(
            currentReciter = audioState.selectedReciter,
            onReciterSelected = { reciter ->
                viewModel.selectReciter(reciter)
                Toast.makeText(context, "تم اختيار القارئ: ${reciter.nameArabic}", Toast.LENGTH_SHORT).show()
            },
            onDismiss = { showReciterDialog = false }
        )
    }

    // Mushaf Modes & Paper Dialog
    if (showMushafModeDialog) {
        MushafModeDialog(
            currentMode = mushafMode,
            currentPaper = mushafPaper,
            onModeSelected = { mode ->
                viewModel.setMushafDisplayMode(mode)
            },
            onPaperSelected = { paper ->
                viewModel.setMushafPaperColor(paper)
            },
            onDismiss = { showMushafModeDialog = false }
        )
    }

    // Surah Chooser Dialog
    if (showSurahChooser) {
        SurahChooserDialog(
            currentSurah = selectedSurah,
            onSurahSelected = { surah ->
                viewModel.openSurah(surah)
                showSurahChooser = false
            },
            onDismiss = { showSurahChooser = false }
        )
    }

    // Search Dialog
    if (showSearchDialog) {
        QuranSearchDialog(
            viewModel = viewModel,
            onSelectAyah = { surahId, ayahNum ->
                val surah = QuranData.surahs.find { it.id == surahId }
                if (surah != null) {
                    viewModel.openSurah(surah, ayahNum)
                }
                showSearchDialog = false
            },
            onDismiss = { showSearchDialog = false }
        )
    }

    // Tafsir Al-Muyassar Bottom Sheet
    selectedAyahForTafsir?.let { ayah ->
        ModalBottomSheet(
            onDismissRequest = { selectedAyahForTafsir = null },
            sheetState = rememberModalBottomSheetState()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.MenuBook, contentDescription = null, tint = GoldAccent)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "التفسير الميسر • سورة ${selectedSurah.nameArabic} (${ayah.ayahNumber})",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(onClick = { selectedAyahForTafsir = null }) {
                        Icon(Icons.Default.Close, contentDescription = "إغلاق")
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Ayah Calligraphy Text
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = ayah.textUthmani,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        lineHeight = 32.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        color = GoldAccent
                    )
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                Text(
                    text = "التفسير الميسر المعتمد:",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = ayah.tafsir.ifBlank { "التفسير الميسر للآية الكريمة." },
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = 26.sp
                )

                if (ayah.asbabNuzul.isNotBlank()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "أسباب النزول:",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = ayah.asbabNuzul,
                        style = MaterialTheme.typography.bodySmall,
                        lineHeight = 22.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "English Translation (Sahih International):",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = ayah.translation,
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Verse Navigation Row (الآية السابقة / التالية)
                val prevAyah = ayahs.find { it.ayahNumber == ayah.ayahNumber - 1 }
                val nextAyah = ayahs.find { it.ayahNumber == ayah.ayahNumber + 1 }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = { if (prevAyah != null) selectedAyahForTafsir = prevAyah },
                        enabled = prevAyah != null
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("الآية السابقة (${toArabicIndicDigits(ayah.ayahNumber - 1)})")
                    }

                    TextButton(
                        onClick = { if (nextAyah != null) selectedAyahForTafsir = nextAyah },
                        enabled = nextAyah != null
                    ) {
                        Text("الآية التالية (${toArabicIndicDigits(ayah.ayahNumber + 1)})")
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(16.dp))
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Bottom actions: Listen, Copy, Share
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = {
                            viewModel.audioPlayer.playAyah(selectedSurah, ayah.ayahNumber)
                            selectedAyahForTafsir = null
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("استماع للآية")
                    }

                    OutlinedButton(
                        onClick = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("Tafsir", "${ayah.textUthmani}\nالتفسير: ${ayah.tafsir}")
                            clipboard.setPrimaryClip(clip)
                            Toast.makeText(context, "تم نسخ التفسير", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("نسخ التفسير")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        val target = selectedAyahForTafsir
                        selectedAyahForTafsir = null
                        selectedAyahForCardShare = target
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("مشاركة الآية كبطاقة مصممة جميلة")
                }

                Spacer(modifier = Modifier.height(26.dp))
            }
        }
    }

    // Add Note Dialog
    selectedAyahForNote?.let { ayah ->
        AlertDialog(
            onDismissRequest = { selectedAyahForNote = null },
            title = { Text("إضافة ملاحظة وتدبر للآية ${ayah.ayahNumber}") },
            text = {
                Column {
                    Text(
                        text = ayah.textUthmani,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = noteInputText,
                        onValueChange = { noteInputText = it },
                        label = { Text("اكتب تدبرك الشخصي...") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (noteInputText.isNotBlank()) {
                            viewModel.addNote(ayah, selectedSurah.nameArabic, noteInputText)
                            Toast.makeText(context, "تم حفظ الملاحظة", Toast.LENGTH_SHORT).show()
                        }
                        selectedAyahForNote = null
                    }
                ) {
                    Text("حفظ")
                }
            },
            dismissButton = {
                TextButton(onClick = { selectedAyahForNote = null }) {
                    Text("إلغاء")
                }
            }
        )
    }

    // Share Ayah as Beautiful Designed Card Dialog
    selectedAyahForCardShare?.let { ayah ->
        com.example.ui.components.ShareAyahDialog(
            surah = selectedSurah,
            ayah = ayah,
            onDismiss = { selectedAyahForCardShare = null }
        )
    }
}

@Composable
fun LegendChip(color: Color, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(color)
        )
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun AyahCardEnhanced(
    ayah: Ayah,
    surah: Surah,
    fontSizeSp: Int,
    mushafMode: MushafDisplayMode,
    paperTextColor: Color,
    isPlaying: Boolean,
    isBookmarked: Boolean,
    isFavorite: Boolean,
    onPlay: () -> Unit,
    onBookmark: () -> Unit,
    onFavorite: () -> Unit,
    onShowTafsir: () -> Unit,
    onAddNote: () -> Unit,
    onCopy: () -> Unit,
    onShare: () -> Unit
) {
    var isHifzRevealed by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isPlaying) EmeraldPrimary.copy(alpha = 0.15f)
            else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
        ),
        border = if (isPlaying) BorderStroke(2.dp, GoldAccent) else null,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onShowTafsir() }
            .testTag("ayah_card_${ayah.surahId}_${ayah.ayahNumber}")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Live Recitation Marker Ribbon following the reciter
            if (isPlaying) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = EmeraldPrimary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.GraphicEq,
                            contentDescription = null,
                            tint = GoldAccent,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "القارئ يرتل هذه الآية الآن • متابعة التلاوة الحية",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = GoldAccent
                        ) {
                            Text(
                                text = "آية ${toArabicIndicDigits(ayah.ayahNumber)}",
                                color = Color.Black,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }
            // Verse Text Display
            if (mushafMode == MushafDisplayMode.HIFZ_MODE && !isHifzRevealed) {
                // Concealed text for Hifz self-testing
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .clickable { isHifzRevealed = true }
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Visibility,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "آية ${ayah.ayahNumber} (مخفية للاختبار - انقر للكشف)",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            } else {
                // Render text: Tajweed Annotated or Standard
                val isTajweed = mushafMode == MushafDisplayMode.TAJWEED
                val textToDisplay = TajweedHelper.annotateTajweed(
                    text = ayah.textUthmani,
                    defaultColor = if (isPlaying) MaterialTheme.colorScheme.primary else paperTextColor,
                    enabled = isTajweed
                )

                Text(
                    text = textToDisplay,
                    fontSize = fontSizeSp.sp,
                    lineHeight = (fontSizeSp * 1.75).sp,
                    textAlign = TextAlign.Right,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.fillMaxWidth(),
                    color = if (isPlaying) MaterialTheme.colorScheme.primary else paperTextColor
                )
            }

            // Inline Tafsir Card (when in TAFSIR_INLINE mode)
            if (mushafMode == MushafDisplayMode.TAFSIR_INLINE) {
                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = MaterialTheme.colorScheme.surface,
                    border = BorderStroke(1.dp, GoldAccent.copy(alpha = 0.3f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.MenuBook,
                                contentDescription = null,
                                tint = GoldAccent,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "التفسير الميسر:",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = GoldAccent
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = ayah.tafsir,
                            style = MaterialTheme.typography.bodySmall,
                            lineHeight = 22.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            // Quick hint to view Tafsir
            if (mushafMode != MushafDisplayMode.TAFSIR_INLINE) {
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable { onShowTafsir() }
                        .padding(vertical = 2.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.MenuBook,
                        contentDescription = null,
                        tint = GoldAccent,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "انقر لعرض التفسير الميسر المعتمد",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = GoldAccent
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Action Row: Ayah badge, Audio, Bookmark, Favorite, Tafsir, Note, Copy, Share
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Ayah badge
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Text(
                        text = "آية ${ayah.ayahNumber}",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onPlay, modifier = Modifier.size(34.dp)) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "استماع",
                            tint = if (isPlaying) GoldAccent else MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    IconButton(onClick = onBookmark, modifier = Modifier.size(34.dp)) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "علامة مرجعية",
                            tint = if (isBookmarked) GoldAccent else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    IconButton(onClick = onFavorite, modifier = Modifier.size(34.dp)) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "المفضلة",
                            tint = if (isFavorite) Color(0xFFEF4444) else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    IconButton(onClick = onShowTafsir, modifier = Modifier.size(34.dp)) {
                        Icon(
                            imageVector = Icons.Default.Visibility,
                            contentDescription = "التفسير",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    IconButton(onClick = onAddNote, modifier = Modifier.size(34.dp)) {
                        Icon(
                            imageVector = Icons.Default.Notes,
                            contentDescription = "ملاحظة",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    IconButton(onClick = onCopy, modifier = Modifier.size(34.dp)) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "نسخ",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    IconButton(onClick = onShare, modifier = Modifier.size(34.dp)) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "مشاركة",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ContinuousMushafPageView(
    ayahs: List<Ayah>,
    fontSizeSp: Int,
    paperTextColor: Color,
    audioState: com.example.audio.AudioPlayerState,
    selectedSurah: Surah,
    mushafMode: MushafDisplayMode,
    onAyahClick: (Ayah) -> Unit,
    modifier: Modifier = Modifier
) {
    val effectiveFontSize = if (mushafMode == MushafDisplayMode.LARGE_FONT) (fontSizeSp + 8) else fontSizeSp
    val effectiveLineHeight = if (mushafMode == MushafDisplayMode.LARGE_FONT) (effectiveFontSize * 2.2).sp else (effectiveFontSize * 1.95).sp
    val isTajweed = mushafMode == MushafDisplayMode.TAJWEED

    val continuousListState = rememberLazyListState()

    // Follow reciter smoothly in Continuous Mushaf View
    LaunchedEffect(audioState.currentAyahNumber, audioState.isPlaying, audioState.currentSurah?.id) {
        if (audioState.isPlaying && audioState.currentSurah?.id == selectedSurah.id && ayahs.isNotEmpty()) {
            val index = ayahs.indexOfFirst { it.ayahNumber == audioState.currentAyahNumber }
            if (index >= 0) {
                val headerOffset = if (selectedSurah.id != 9) 2 else 1
                continuousListState.animateScrollToItem((index + headerOffset).coerceAtLeast(0))
            }
        }
    }

    RoyalPageFrame(
        surah = selectedSurah,
        currentPage = selectedSurah.startPage,
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            state = continuousListState,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 2.dp),
            contentPadding = PaddingValues(bottom = 120.dp)
        ) {
            // Royal Surah Header Cartouche (الترويسة المذهبة الملكية كما في طبعة المدينة)
            item {
                SurahHeaderCartouche(surah = selectedSurah)
            }

            // Bismillah Ribbon (except Surah At-Tawbah #9)
            if (selectedSurah.id != 9) {
                item {
                    BismillahRibbon()
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            // Lightweight, lazy rendered Uthmani Verses with auto-scroll and highlight
            items(ayahs, key = { "continuous_${it.surahId}_${it.ayahNumber}" }) { ayah ->
                val isPlaying = audioState.currentSurah?.id == ayah.surahId && audioState.currentAyahNumber == ayah.ayahNumber
                val baseColor = if (isPlaying) GoldAccent else paperTextColor

                val ayahAnnotated = if (isTajweed) {
                    TajweedHelper.annotateTajweed(ayah.textUthmani, defaultColor = baseColor, enabled = true)
                } else {
                    androidx.compose.ui.text.AnnotatedString(ayah.textUthmani)
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (isPlaying) GoldAccent.copy(alpha = 0.28f) else Color.Transparent,
                    border = if (isPlaying) BorderStroke(2.dp, GoldAccent) else null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onAyahClick(ayah) }
                        .padding(vertical = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 6.dp)
                    ) {
                        // Live Recitation Marker Ribbon following the reciter
                        if (isPlaying) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = EmeraldPrimary,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 6.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.GraphicEq,
                                        contentDescription = null,
                                        tint = GoldAccent,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "القارئ يرتل هذه الآية الآن (انقر للتفسير الميسر)",
                                        color = Color.White,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    Surface(
                                        shape = RoundedCornerShape(4.dp),
                                        color = GoldAccent
                                    ) {
                                        Text(
                                            text = "آية ${toArabicIndicDigits(ayah.ayahNumber)}",
                                            color = Color.Black,
                                            fontWeight = FontWeight.ExtraBold,
                                            fontSize = 10.sp,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 1.dp)
                                        )
                                    }
                                }
                            }
                        }

                        Text(
                            text = androidx.compose.ui.text.buildAnnotatedString {
                                append(ayahAnnotated)
                                append("  ")
                                pushStyle(
                                    androidx.compose.ui.text.SpanStyle(
                                        color = GoldAccent,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                append("۝${toArabicIndicDigits(ayah.ayahNumber)}")
                                pop()
                                append(" ")
                            },
                            fontSize = effectiveFontSize.sp,
                            lineHeight = effectiveLineHeight,
                            textAlign = TextAlign.Right,
                            color = baseColor,
                            fontWeight = if (isPlaying) FontWeight.Bold else FontWeight.Medium,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SurahChooserDialog(
    currentSurah: Surah,
    onSurahSelected: (Surah) -> Unit,
    onDismiss: () -> Unit
) {
    var searchFilter by remember { mutableStateOf("") }
    var filterMakkiMadani by remember { mutableStateOf<String?>(null) }

    val filteredSurahs = QuranData.surahs.filter { surah ->
        val matchesName = surah.nameArabic.contains(searchFilter) || surah.nameEnglish.contains(searchFilter, ignoreCase = true)
        val matchesType = when (filterMakkiMadani) {
            "مكية" -> surah.revelationType == com.example.model.RevelationType.MAKKI
            "مدنية" -> surah.revelationType == com.example.model.RevelationType.MADANI
            else -> true
        }
        matchesName && matchesType
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("إغلاق") }
        },
        title = {
            Column {
                Text("فهرس سور القرآن الكريم (١١٤ سورة)")
                Spacer(modifier = Modifier.height(6.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = EmeraldPrimary.copy(alpha = 0.15f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.CloudDone, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "المصحف كامل 114 سورة متاح أوفلاين بدون نت ✓",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldPrimary
                        )
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = searchFilter,
                    onValueChange = { searchFilter = it },
                    placeholder = { Text("ابحث باسم السورة...") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = filterMakkiMadani == null,
                        onClick = { filterMakkiMadani = null },
                        label = { Text("الكل") }
                    )
                    FilterChip(
                        selected = filterMakkiMadani == "مكية",
                        onClick = { filterMakkiMadani = "مكية" },
                        label = { Text("مكية") }
                    )
                    FilterChip(
                        selected = filterMakkiMadani == "مدنية",
                        onClick = { filterMakkiMadani = "مدنية" },
                        label = { Text("مدنية") }
                    )
                }
            }
        },
        text = {
            LazyColumn(modifier = Modifier.height(350.dp)) {
                items(filteredSurahs) { surah ->
                    Surface(
                        color = if (surah.id == currentSurah.id) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSurahSelected(surah) }
                            .padding(vertical = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    shape = CircleShape,
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = "${surah.id}",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = "سورة ${surah.nameArabic}",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "${surah.nameEnglish} • ${surah.revelationType.arabicName}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            Text(
                                text = "${surah.versesCount} آية",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun QuranSearchDialog(
    viewModel: AppViewModel,
    onSelectAyah: (surahId: Int, ayahNum: Int) -> Unit,
    onDismiss: () -> Unit
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("إغلاق") }
        },
        title = {
            Column {
                Text("البحث المتقدم في القرآن الكريم")
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { viewModel.searchQuran(it) },
                    placeholder = { Text("ابحث بكلمة أو آية أو تفسير...") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
        },
        text = {
            if (searchResults.isEmpty() && searchQuery.length >= 2) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "لا توجد نتائج مطابقة لـ \"$searchQuery\"",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(modifier = Modifier.height(350.dp)) {
                    items(searchResults) { ayah ->
                        val surah = QuranData.surahs.find { it.id == ayah.surahId }
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSelectAyah(ayah.surahId, ayah.ayahNumber) }
                                .padding(vertical = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "سورة ${surah?.nameArabic ?: ""} - آية ${ayah.ayahNumber}",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Text(
                                        text = "صفحة ${ayah.pageNumber}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = ayah.textUthmani,
                                    style = MaterialTheme.typography.bodySmall,
                                    maxLines = 2,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}
