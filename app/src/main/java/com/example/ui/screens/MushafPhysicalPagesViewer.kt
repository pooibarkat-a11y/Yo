package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.FullscreenExit
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.PauseCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.data.QuranData
import com.example.data.QuranOfflineProvider
import com.example.data.QuranPagesManager
import com.example.model.Ayah
import com.example.model.MushafPaperColor
import com.example.model.Surah
import com.example.ui.components.SheikhAvatar
import com.example.ui.components.toArabicIndicDigits
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.GoldAccent
import com.example.viewmodel.AppViewModel
import kotlinx.coroutines.launch

/**
 * Authentic Holy Quran Real Pages Viewer (مصحف المدينة النبوية الورقي 1-604 صفحة)
 * Offers a true page-flipping physical book experience with high-definition Madinah pages,
 * RTL horizontal swiping, jump-to-page dialog, paper styling, bookmarking, and immersion.
 */
@Composable
fun MushafPhysicalPagesViewer(
    viewModel: AppViewModel,
    modifier: Modifier = Modifier,
    onOpenSurahChooser: () -> Unit,
    onOpenReciterPicker: (() -> Unit)? = null,
    onOpenMushafModes: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val currentMushafPage by viewModel.currentMushafPage.collectAsState()
    val mushafPaper by viewModel.mushafPaperColor.collectAsState()
    val isImmersive by viewModel.isMushafImmersive.collectAsState()
    val bookmarkedPages by viewModel.bookmarkedPages.collectAsState()
    val audioState by viewModel.audioPlayer.state.collectAsState()

    var showJumpDialog by remember { mutableStateOf(false) }
    var showPaperMenu by remember { mutableStateOf(false) }
    var showPageTafsirSheet by remember { mutableStateOf(false) }
    var selectedAyahForPageTafsir by remember { mutableStateOf<Ayah?>(null) }
    var ayahForCardShare by remember { mutableStateOf<Ayah?>(null) }

    // Zoom state
    var zoomScale by remember { mutableFloatStateOf(1f) }

    // Pager from 0 to 603 (representing pages 1 to 604)
    val pagerState = rememberPagerState(
        initialPage = (currentMushafPage - 1).coerceIn(0, QuranPagesManager.TOTAL_PAGES - 1),
        pageCount = { QuranPagesManager.TOTAL_PAGES }
    )

    // Synchronize external page changes (e.g. from Surah index, Search, Khatmah) to the Pager
    LaunchedEffect(currentMushafPage) {
        val targetIndex = (currentMushafPage - 1).coerceIn(0, QuranPagesManager.TOTAL_PAGES - 1)
        if (pagerState.currentPage != targetIndex) {
            pagerState.scrollToPage(targetIndex)
        }
    }

    // Synchronize user swipe/flip to ViewModel when page settles
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.settledPage }.collect { settledIndex ->
            val pageNum = settledIndex + 1
            if (pageNum != currentMushafPage) {
                viewModel.openPage(pageNum)
            }
        }
    }

    // Colors matching genuine Mushaf paper
    val (pageBgColor, pageBorderColor) = when (mushafPaper) {
        MushafPaperColor.PARCHMENT -> Color(0xFFFBF6EB) to GoldAccent.copy(alpha = 0.55f)
        MushafPaperColor.MADINAH_GREEN -> Color(0xFF092317) to EmeraldPrimary.copy(alpha = 0.6f)
        MushafPaperColor.DARK -> Color(0xFF121518) to Color(0xFF334155)
        MushafPaperColor.PURE_WHITE -> Color(0xFFFFFFFF) to Color(0xFFE2E8F0)
    }

    val pageNumber = pagerState.currentPage + 1
    val currentSurah = remember(pageNumber) { QuranPagesManager.getPageSurah(pageNumber) }
    val currentJuz = remember(pageNumber) { QuranPagesManager.getPageJuz(pageNumber) }
    val currentHizb = remember(pageNumber) { QuranPagesManager.getPageHizb(pageNumber) }
    val isPageBookmarked = bookmarkedPages.contains(pageNumber)
    val pageAyahs = remember(pageNumber) { QuranOfflineProvider.getAyahsForPage(context, pageNumber) }

    // Follow reciter smoothly: automatically flip pages when the Sheikh advances to an Ayah on the next page
    LaunchedEffect(audioState.isPlaying, audioState.currentSurah?.id, audioState.currentAyahNumber) {
        if (audioState.isPlaying && audioState.currentSurah != null) {
            val surahAyahs = QuranOfflineProvider.getSurah(context, audioState.currentSurah!!.id)
            val activeAyah = surahAyahs.find { it.ayahNumber == audioState.currentAyahNumber }
            if (activeAyah != null && activeAyah.pageNumber in 1..604 && activeAyah.pageNumber != pageNumber) {
                pagerState.animateScrollToPage(activeAyah.pageNumber - 1)
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(pageBgColor)
    ) {
        // Quran uses Right-To-Left page flipping: Swiping right-to-left turns to next page (1 -> 2 -> 3)
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("mushaf_horizontal_pager")
            ) { pageIndex ->
                val displayPage = pageIndex + 1

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onTap = {
                                    viewModel.toggleMushafImmersive()
                                },
                                onDoubleTap = {
                                    zoomScale = if (zoomScale > 1.1f) 1f else 1.45f
                                }
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    SingleMushafPageDisplay(
                        page = displayPage,
                        paperColor = mushafPaper,
                        zoomScale = zoomScale,
                        surah = currentSurah,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                top = if (isImmersive) 8.dp else 48.dp,
                                bottom = if (isImmersive) 8.dp else 70.dp,
                                start = 4.dp,
                                end = 4.dp
                            )
                    )

                    // Authentic Golden Bookmark Ribbon on page top-left
                    if (isPageBookmarked) {
                        Surface(
                            shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp),
                            color = GoldAccent,
                            shadowElevation = 4.dp,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(end = 24.dp)
                                .width(22.dp)
                                .height(44.dp)
                        ) {
                            Box(contentAlignment = Alignment.BottomCenter) {
                                Icon(
                                    imageVector = Icons.Default.Bookmark,
                                    contentDescription = "علامة محفوظة",
                                    tint = Color.White,
                                    modifier = Modifier
                                        .size(16.dp)
                                        .padding(bottom = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // FLOATING TOP BAR (Surah, Juz, Hizb, Bookmark, Paper Tint)
        AnimatedVisibility(
            visible = !isImmersive,
            enter = fadeIn() + slideInVertically { -it },
            exit = fadeOut() + slideOutVertically { -it },
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Surface(
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.94f),
                tonalElevation = 6.dp,
                shadowElevation = 8.dp,
                shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp),
                border = BorderStroke(1.dp, GoldAccent.copy(alpha = 0.35f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Surah selector badge
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        border = BorderStroke(1.dp, EmeraldPrimary.copy(alpha = 0.4f)),
                        modifier = Modifier
                            .clickable { onOpenSurahChooser() }
                            .testTag("mushaf_top_surah_badge")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "سورة ${currentSurah.nameArabic}",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = EmeraldPrimary
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "▼", fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }

                    // Juz & Hizb info
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "الجزء ${toArabicIndicDigits(currentJuz.number)}",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = GoldAccent
                        )
                        Text(
                            text = "الحزب ${toArabicIndicDigits(currentHizb)}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // Actions: Reciter Avatar, Audio, Modes, Bookmark, Paper Style, Fullscreen
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Reciter Photo Avatar (Direct click to pick reciter photo)
                        Surface(
                            shape = CircleShape,
                            color = Color.Transparent,
                            modifier = Modifier
                                .padding(end = 4.dp)
                                .clickable { onOpenReciterPicker?.invoke() }
                                .testTag("mushaf_sheikh_avatar_button")
                        ) {
                            SheikhAvatar(
                                nameArabic = audioState.selectedReciter.nameArabic,
                                imageUrl = audioState.selectedReciter.imageUrl,
                                sheikhId = audioState.selectedReciter.id,
                                isSelected = audioState.isPlaying,
                                size = 30.dp
                            )
                        }

                        // Audio Play / Pause for Reciter
                        IconButton(
                            onClick = {
                                if (audioState.isPlaying) {
                                    viewModel.audioPlayer.pause()
                                } else {
                                    viewModel.audioPlayer.playSurah(currentSurah, 1)
                                }
                            },
                            modifier = Modifier.size(32.dp).testTag("mushaf_play_reciter_button")
                        ) {
                            Icon(
                                imageVector = if (audioState.isPlaying) Icons.Default.PauseCircle else Icons.Default.PlayCircle,
                                contentDescription = if (audioState.isPlaying) "إيقاف مؤقت" else "استماع للتلاوة",
                                tint = GoldAccent,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        // Mushaf Display Modes Dialog Button
                        if (onOpenMushafModes != null) {
                            IconButton(
                                onClick = { onOpenMushafModes() },
                                modifier = Modifier.size(32.dp).testTag("mushaf_modes_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AutoStories,
                                    contentDescription = "أنماط المصحف والتفسير",
                                    tint = EmeraldPrimary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        // Page Tafsir Al-Muyassar Button
                        IconButton(
                            onClick = { showPageTafsirSheet = true },
                            modifier = Modifier.size(32.dp).testTag("mushaf_page_tafsir_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.MenuBook,
                                contentDescription = "التفسير الميسر لآيات الصفحة",
                                tint = GoldAccent,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        // Bookmark button
                        IconButton(
                            onClick = {
                                viewModel.togglePageBookmark(pageNumber)
                                val msg = if (isPageBookmarked) {
                                    "تمت إزالة علامة صفحة $pageNumber"
                                } else {
                                    "تم حفظ العلامة عند صفحة $pageNumber (سورة ${currentSurah.nameArabic}) ✓"
                                }
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.size(34.dp).testTag("mushaf_bookmark_button")
                        ) {
                            Icon(
                                imageVector = if (isPageBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = "حفظ الصفحة",
                                tint = GoldAccent,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        // Paper Theme Menu
                        Box {
                            IconButton(
                                onClick = { showPaperMenu = true },
                                modifier = Modifier.size(34.dp).testTag("mushaf_paper_theme_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ColorLens,
                                    contentDescription = "لون ورق المصحف",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            DropdownMenu(
                                expanded = showPaperMenu,
                                onDismissRequest = { showPaperMenu = false }
                            ) {
                                MushafPaperColor.values().forEach { paper ->
                                    DropdownMenuItem(
                                        text = {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Box(
                                                    modifier = Modifier
                                                        .size(16.dp)
                                                        .clip(CircleShape)
                                                        .background(
                                                            when (paper) {
                                                                MushafPaperColor.PARCHMENT -> Color(0xFFFBF6EB)
                                                                MushafPaperColor.MADINAH_GREEN -> Color(0xFF092317)
                                                                MushafPaperColor.DARK -> Color(0xFF121518)
                                                                MushafPaperColor.PURE_WHITE -> Color(0xFFFFFFFF)
                                                            }
                                                        )
                                                        .border(1.dp, Color.Gray, CircleShape)
                                                )
                                                Spacer(modifier = Modifier.width(8.dp))
                                                Text(
                                                    text = paper.titleArabic,
                                                    fontWeight = if (mushafPaper == paper) FontWeight.Bold else FontWeight.Normal
                                                )
                                                if (mushafPaper == paper) {
                                                    Spacer(modifier = Modifier.width(6.dp))
                                                    Icon(Icons.Default.Check, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(16.dp))
                                                }
                                            }
                                        },
                                        onClick = {
                                            viewModel.setMushafPaperColor(paper)
                                            showPaperMenu = false
                                        }
                                    )
                                }
                            }
                        }

                        // Immersive Toggle
                        IconButton(
                            onClick = { viewModel.toggleMushafImmersive() },
                            modifier = Modifier.size(34.dp).testTag("mushaf_immersive_button")
                        ) {
                            Icon(
                                imageVector = if (isImmersive) Icons.Default.FullscreenExit else Icons.Default.Fullscreen,
                                contentDescription = "قراءة غامرة",
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }

        // FLOATING BOTTOM NAVIGATION BAR (Page Flip, Slider, Jump)
        AnimatedVisibility(
            visible = !isImmersive,
            enter = fadeIn() + slideInVertically { it },
            exit = fadeOut() + slideOutVertically { it },
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Surface(
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                tonalElevation = 8.dp,
                shadowElevation = 10.dp,
                shape = RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp),
                border = BorderStroke(1.dp, GoldAccent.copy(alpha = 0.35f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Active Audio Playback Ribbon with Live Ayah Recitation Follower & Tafsir button
                    if (audioState.isPlaying || audioState.currentSurah != null) {
                        val activeSurah = audioState.currentSurah ?: currentSurah
                        val activeAyahNum = audioState.currentAyahNumber
                        val activeAyah = pageAyahs.find { it.surahId == activeSurah.id && it.ayahNumber == activeAyahNum }
                            ?: QuranOfflineProvider.getSurah(context, activeSurah.id).find { it.ayahNumber == activeAyahNum }

                        Surface(
                            color = if (audioState.isPlaying) EmeraldPrimary.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(14.dp),
                            border = BorderStroke(if (audioState.isPlaying) 1.8.dp else 1.dp, if (audioState.isPlaying) GoldAccent else GoldAccent.copy(alpha = 0.5f)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 6.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .weight(1f)
                                            .clickable { onOpenReciterPicker?.invoke() }
                                    ) {
                                        SheikhAvatar(
                                            nameArabic = audioState.selectedReciter.nameArabic,
                                            imageUrl = audioState.selectedReciter.imageUrl,
                                            sheikhId = audioState.selectedReciter.id,
                                            isSelected = audioState.isPlaying,
                                            size = 36.dp
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Column {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                if (audioState.isPlaying) {
                                                    Icon(
                                                        Icons.Default.GraphicEq,
                                                        contentDescription = null,
                                                        tint = EmeraldPrimary,
                                                        modifier = Modifier.size(14.dp)
                                                    )
                                                    Spacer(modifier = Modifier.width(4.dp))
                                                }
                                                Text(
                                                    text = audioState.selectedReciter.nameArabic,
                                                    fontSize = 11.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = MaterialTheme.colorScheme.onSurface
                                                )
                                            }
                                            Text(
                                                text = if (audioState.isPlaying) "تلاوة سورة ${activeSurah.nameArabic} • آية ${toArabicIndicDigits(activeAyahNum)}" else "التلاوة متوقفة مؤقتاً",
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Medium,
                                                color = if (audioState.isPlaying) EmeraldPrimary else Color.Gray
                                            )
                                        }
                                    }

                                    // Quick Tafsir Button for currently recited Ayah
                                    if (activeAyah != null) {
                                        Surface(
                                            shape = RoundedCornerShape(10.dp),
                                            color = GoldAccent.copy(alpha = 0.22f),
                                            border = BorderStroke(1.dp, GoldAccent),
                                            modifier = Modifier
                                                .clickable {
                                                    selectedAyahForPageTafsir = activeAyah
                                                }
                                        ) {
                                            Row(
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Icon(
                                                    Icons.Default.MenuBook,
                                                    contentDescription = null,
                                                    tint = GoldAccent,
                                                    modifier = Modifier.size(13.dp)
                                                )
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text(
                                                    text = "تفسير الآية",
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = MaterialTheme.colorScheme.onSurface
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.width(6.dp))
                                    }

                                    // Controls: Prev Ayah, Play/Pause, Next Ayah, Stop
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        IconButton(
                                            onClick = { viewModel.audioPlayer.previousAyah() },
                                            modifier = Modifier.size(28.dp)
                                        ) {
                                            Icon(
                                                Icons.AutoMirrored.Filled.ArrowForward,
                                                contentDescription = "الآية السابقة",
                                                tint = MaterialTheme.colorScheme.onSurface,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                        IconButton(
                                            onClick = {
                                                if (audioState.isPlaying) {
                                                    viewModel.audioPlayer.pause()
                                                } else {
                                                    viewModel.audioPlayer.playSurah(currentSurah, 1)
                                                }
                                            },
                                            modifier = Modifier.size(32.dp)
                                        ) {
                                            Icon(
                                                imageVector = if (audioState.isPlaying) Icons.Default.PauseCircle else Icons.Default.PlayCircle,
                                                contentDescription = "تشغيل / إيقاف التلاوة",
                                                tint = GoldAccent,
                                                modifier = Modifier.size(24.dp)
                                            )
                                        }
                                        IconButton(
                                            onClick = { viewModel.audioPlayer.nextAyah() },
                                            modifier = Modifier.size(28.dp)
                                        ) {
                                            Icon(
                                                Icons.AutoMirrored.Filled.ArrowBack,
                                                contentDescription = "الآية التالية",
                                                tint = MaterialTheme.colorScheme.onSurface,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                        IconButton(
                                            onClick = { viewModel.audioPlayer.stop() },
                                            modifier = Modifier.size(28.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Stop,
                                                contentDescription = "إيقاف",
                                                tint = MaterialTheme.colorScheme.error,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                    }
                                }

                                // Display text of currently recited Ayah with gold styling
                                if (audioState.isPlaying && activeAyah != null && activeAyah.textUthmani.isNotBlank()) {
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = activeAyah.textUthmani,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = GoldAccent,
                                        maxLines = 1,
                                        textAlign = TextAlign.Right,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }
                    }

                    // Page Flipping Buttons and Clickable Page Number Badge
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Previous Page (السابقة ➡️)
                        Button(
                            onClick = {
                                if (pagerState.currentPage > 0) {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                                    }
                                }
                            },
                            enabled = pagerState.currentPage > 0,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                            modifier = Modifier.testTag("mushaf_prev_page_button")
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("السابقة", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                            }
                        }

                        // Clickable Page Number Badge -> Opens Direct Jump Dialog
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = GoldAccent.copy(alpha = 0.18f),
                            border = BorderStroke(1.dp, GoldAccent),
                            modifier = Modifier
                                .clickable { showJumpDialog = true }
                                .testTag("mushaf_jump_page_badge")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.MenuBook, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(15.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "صفحة ${toArabicIndicDigits(pageNumber)} من ٦٠٤",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = "✎", fontSize = 10.sp, color = GoldAccent)
                            }
                        }

                        // Prominent Tafsir Al-Muyassar button for this page
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = EmeraldPrimary,
                            modifier = Modifier
                                .clickable { showPageTafsirSheet = true }
                                .testTag("mushaf_page_tafsir_pill")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.MenuBook, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("التفسير الميسر 📖", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        // Next Page (التالية ⬅️)
                        Button(
                            onClick = {
                                if (pagerState.currentPage < QuranPagesManager.TOTAL_PAGES - 1) {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                    }
                                }
                            },
                            enabled = pagerState.currentPage < QuranPagesManager.TOTAL_PAGES - 1,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = EmeraldPrimary,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                            modifier = Modifier.testTag("mushaf_next_page_button")
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("التالية", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(16.dp))
                            }
                        }
                    }

                    // Page Navigation Slider
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "١",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Slider(
                            value = pageNumber.toFloat(),
                            onValueChange = { targetVal ->
                                val targetPage = targetVal.toInt().coerceIn(1, QuranPagesManager.TOTAL_PAGES)
                                coroutineScope.launch {
                                    pagerState.scrollToPage(targetPage - 1)
                                }
                            },
                            valueRange = 1f..604f,
                            colors = SliderDefaults.colors(
                                thumbColor = GoldAccent,
                                activeTrackColor = EmeraldPrimary,
                                inactiveTrackColor = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("mushaf_page_slider")
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "٦٠٤",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }

    // JUMP TO PAGE DIALOG
    if (showJumpDialog) {
        JumpToPageDialog(
            currentPage = pageNumber,
            onPageSelected = { target ->
                coroutineScope.launch {
                    pagerState.scrollToPage((target - 1).coerceIn(0, QuranPagesManager.TOTAL_PAGES - 1))
                }
                showJumpDialog = false
            },
            onDismiss = { showJumpDialog = false }
        )
    }

    // PAGE TAFSIR AL-MUYASSAR BOTTOM SHEET
    if (showPageTafsirSheet) {
        PageAyahsTafsirBottomSheet(
            pageNumber = pageNumber,
            ayahs = pageAyahs,
            audioState = audioState,
            onPlayAyah = { ayah ->
                val surahObj = QuranData.surahs.find { it.id == ayah.surahId } ?: currentSurah
                viewModel.audioPlayer.playAyah(surahObj, ayah.ayahNumber)
            },
            onShareAyah = { ayah ->
                ayahForCardShare = ayah
            },
            onDismiss = { showPageTafsirSheet = false }
        )
    }

    // SINGLE AYAH TAFSIR BOTTOM SHEET
    selectedAyahForPageTafsir?.let { ayah ->
        SingleAyahTafsirBottomSheet(
            ayah = ayah,
            audioState = audioState,
            onPlayAyah = {
                val surahObj = QuranData.surahs.find { it.id == ayah.surahId } ?: currentSurah
                viewModel.audioPlayer.playAyah(surahObj, ayah.ayahNumber)
            },
            onShareAyah = {
                selectedAyahForPageTafsir = null
                ayahForCardShare = ayah
            },
            onDismiss = { selectedAyahForPageTafsir = null }
        )
    }

    // SHARE AYAH AS BEAUTIFUL DESIGNED IMAGE WITH DEVELOPER NAME
    ayahForCardShare?.let { ayah ->
        val surahObj = QuranData.surahs.find { it.id == ayah.surahId } ?: currentSurah
        com.example.ui.components.ShareAyahDialog(
            surah = surahObj,
            ayah = ayah,
            onDismiss = { ayahForCardShare = null }
        )
    }
}

/**
 * Renders a single authentic King Fahd Madinah Mushaf Page with Coil image loading and graceful offline fallback.
 */
@Composable
fun SingleMushafPageDisplay(
    page: Int,
    paperColor: MushafPaperColor,
    zoomScale: Float,
    surah: Surah,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val primaryUrl = remember(page) { QuranPagesManager.getPageImageUrl(page) }
    val fallbackUrl = remember(page) { QuranPagesManager.getFallbackPageImageUrl(page) }

    var useFallback by remember(page) { mutableStateOf(false) }

    val imageUrl = if (useFallback) fallbackUrl else primaryUrl

    val imageRequest = remember(imageUrl) {
        ImageRequest.Builder(context)
            .data(imageUrl)
            .crossfade(true)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .build()
    }

    Box(
        modifier = modifier
            .graphicsLayer {
                scaleX = zoomScale
                scaleY = zoomScale
            },
        contentAlignment = Alignment.Center
    ) {
        SubcomposeAsyncImage(
            model = imageRequest,
            contentDescription = "صفحة $page من المصحف الشريف",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 2.dp),
            loading = {
                // Shimmer / Ornate Loading Frame
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                        .border(2.dp, GoldAccent.copy(alpha = 0.4f), RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            color = GoldAccent,
                            strokeWidth = 3.dp,
                            modifier = Modifier.size(38.dp)
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "سورة ${surah.nameArabic}",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldPrimary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "فتح صفحة ${toArabicIndicDigits(page)} من مصحف المدينة...",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            },
            error = {
                if (!useFallback) {
                    LaunchedEffect(Unit) {
                        useFallback = true
                    }
                }
                // Offline Beautiful Fallback
                OfflinePageFallback(
                    page = page,
                    surah = surah,
                    modifier = Modifier.fillMaxSize()
                )
            }
        )
    }
}

/**
 * Elegant fallback card rendered if network is unavailable before pages are cached
 */
@Composable
fun OfflinePageFallback(
    page: Int,
    surah: Surah,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val localAyahs = remember(surah.id) {
        QuranOfflineProvider.getSurah(context, surah.id)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
            .border(2.5.dp, GoldAccent, RoundedCornerShape(14.dp))
            .padding(4.dp)
            .border(1.dp, GoldAccent.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "الجزء ${toArabicIndicDigits(surah.juzNumber)}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = GoldAccent
                )
                Text(
                    text = "سورة ${surah.nameArabic}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = EmeraldPrimary
                )
                Text(
                    text = "صفحة ${toArabicIndicDigits(page)}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = GoldAccent
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Body
            if (localAyahs.isNotEmpty()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    if (surah.id != 9 && surah.id != 1) {
                        Text(
                            text = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = GoldAccent,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                    Text(
                        text = localAyahs.take(8).joinToString(" ") { ayah ->
                            "${ayah.textUthmani} \u06DD${toArabicIndicDigits(ayah.ayahNumber)}"
                        },
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 16.sp,
                        lineHeight = 28.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.MenuBook,
                        contentDescription = null,
                        tint = GoldAccent,
                        modifier = Modifier.size(54.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "صفحة رقم $page",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldPrimary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "مصحف المدينة النبوية الشريف",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = GoldAccent
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "يتطلب فتح الصفحة لأول مرة اتصالاً بالإنترنت لتحميل صفحات مصحف مجمع الملك فهد الأصلية وحفظها في ذاكرة الهاتف للاستخدام بدون نت.",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                }
            }

            // Footer
            Text(
                text = "— صفحة ${toArabicIndicDigits(page)} —",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = GoldAccent
            )
        }
    }
}

/**
 * Jump to Page Dialog allowing instant typing or shortcuts to important pages
 */
@Composable
fun JumpToPageDialog(
    currentPage: Int,
    onPageSelected: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    var input by remember { mutableStateOf(currentPage.toString()) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.MenuBook, contentDescription = null, tint = GoldAccent)
                Spacer(modifier = Modifier.width(8.dp))
                Text("الانتقال إلى صفحة في المصحف", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }
        },
        text = {
            Column {
                Text(
                    text = "أدخل رقم الصفحة من ١ إلى ٦٠٤:",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = input,
                    onValueChange = {
                        input = it.filter { char -> char.isDigit() }
                        errorMessage = null
                    },
                    isError = errorMessage != null,
                    supportingText = {
                        if (errorMessage != null) {
                            Text(text = errorMessage!!, color = MaterialTheme.colorScheme.error)
                        }
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            val p = input.toIntOrNull()
                            if (p != null && p in 1..604) {
                                onPageSelected(p)
                            } else {
                                errorMessage = "الرجاء إدخال رقم بين ١ و ٦٠٤"
                            }
                        }
                    ),
                    modifier = Modifier.fillMaxWidth().testTag("jump_page_input")
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "وجهات سريعة:",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = EmeraldPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    SuggestionChipQuick("الفاتحة (١)", 1) { onPageSelected(1) }
                    SuggestionChipQuick("البقرة (٢)", 2) { onPageSelected(2) }
                    SuggestionChipQuick("الكهف (٢٩٣)", 293) { onPageSelected(293) }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    SuggestionChipQuick("يس (٤٤٠)", 440) { onPageSelected(440) }
                    SuggestionChipQuick("الملك (٥٦٢)", 562) { onPageSelected(562) }
                    SuggestionChipQuick("جزء عم (٥٨٢)", 582) { onPageSelected(582) }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val p = input.toIntOrNull()
                    if (p != null && p in 1..604) {
                        onPageSelected(p)
                    } else {
                        errorMessage = "الرجاء إدخال رقم بين ١ و ٦٠٤"
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
            ) {
                Text("انتقال")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("إلغاء")
            }
        }
    )
}

@Composable
private fun SuggestionChipQuick(
    label: String,
    page: Int,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        border = BorderStroke(0.6.dp, EmeraldPrimary.copy(alpha = 0.5f)),
        modifier = Modifier.clickable { onClick() }
    ) {
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PageAyahsTafsirBottomSheet(
    pageNumber: Int,
    ayahs: List<Ayah>,
    audioState: com.example.audio.AudioPlayerState,
    onPlayAyah: (Ayah) -> Unit,
    onShareAyah: (Ayah) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val sheetListState = rememberLazyListState()

    // Auto-scroll to currently playing ayah if playing on this page
    LaunchedEffect(audioState.currentAyahNumber, audioState.isPlaying) {
        if (audioState.isPlaying && ayahs.isNotEmpty()) {
            val idx = ayahs.indexOfFirst { it.surahId == audioState.currentSurah?.id && it.ayahNumber == audioState.currentAyahNumber }
            if (idx >= 0) {
                sheetListState.animateScrollToItem(idx)
            }
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.MenuBook,
                        contentDescription = null,
                        tint = GoldAccent,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "التفسير الميسر • صفحة ${toArabicIndicDigits(pageNumber)}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "تفسير ميسر معتمد لجميع آيات الصفحة (${toArabicIndicDigits(ayahs.size)} آيات)",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "إغلاق")
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))

            if (ayahs.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "جاري تحميل آيات الصفحة وتفسيرها الميسر...",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    state = sheetListState,
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 32.dp)
                ) {
                    items(ayahs, key = { "page_${pageNumber}_${it.surahId}_${it.ayahNumber}" }) { ayah ->
                        val isPlayingThisAyah = audioState.isPlaying &&
                                audioState.currentSurah?.id == ayah.surahId &&
                                audioState.currentAyahNumber == ayah.ayahNumber
                        val surahObj = QuranData.surahs.find { it.id == ayah.surahId }

                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = if (isPlayingThisAyah) GoldAccent.copy(alpha = 0.18f)
                            else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                            border = if (isPlayingThisAyah) BorderStroke(1.8.dp, GoldAccent)
                            else BorderStroke(0.6.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                // Live reciter badge when this verse is currently being recited
                                if (isPlayingThisAyah) {
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = EmeraldPrimary,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 8.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                Icons.Default.GraphicEq,
                                                contentDescription = null,
                                                tint = GoldAccent,
                                                modifier = Modifier.size(16.dp)
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(
                                                text = "القارئ ${audioState.selectedReciter.nameArabic} يرتل هذه الآية الآن",
                                                color = Color.White,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                }

                                // Surah & Ayah Number + Play/Copy actions
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = GoldAccent.copy(alpha = 0.2f),
                                        border = BorderStroke(0.6.dp, GoldAccent)
                                    ) {
                                        Text(
                                            text = "سورة ${surahObj?.nameArabic ?: ""} • آية ${toArabicIndicDigits(ayah.ayahNumber)}",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                        )
                                    }

                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        IconButton(
                                            onClick = { onPlayAyah(ayah) },
                                            modifier = Modifier.size(30.dp)
                                        ) {
                                            Icon(
                                                imageVector = if (isPlayingThisAyah) Icons.Default.PauseCircle else Icons.Default.PlayCircle,
                                                contentDescription = "استماع للآية",
                                                tint = GoldAccent,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                        IconButton(
                                            onClick = {
                                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                                val clip = ClipData.newPlainText("Ayah Tafsir", "${ayah.textUthmani}\nالتفسير الميسر: ${ayah.tafsir}")
                                                clipboard.setPrimaryClip(clip)
                                                Toast.makeText(context, "تم نسخ الآية وتفسيرها الميسر", Toast.LENGTH_SHORT).show()
                                            },
                                            modifier = Modifier.size(30.dp)
                                        ) {
                                            Icon(
                                                Icons.Default.ContentCopy,
                                                contentDescription = "نسخ",
                                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                        IconButton(
                                            onClick = { onShareAyah(ayah) },
                                            modifier = Modifier.size(30.dp)
                                        ) {
                                            Icon(
                                                Icons.Default.Palette,
                                                contentDescription = "مشاركة كصورة مصممة",
                                                tint = GoldAccent,
                                                modifier = Modifier.size(17.dp)
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                // Ayah Uthmani Text
                                Text(
                                    text = ayah.textUthmani,
                                    style = MaterialTheme.typography.titleMedium,
                                    textAlign = TextAlign.Right,
                                    lineHeight = 32.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isPlayingThisAyah) EmeraldPrimary else MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.fillMaxWidth()
                                )

                                HorizontalDivider(
                                    modifier = Modifier.padding(vertical = 8.dp),
                                    color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
                                )

                                // Tafsir Al-Muyassar Text
                                Text(
                                    text = "التفسير الميسر المعتمد:",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = GoldAccent
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = ayah.tafsir.ifBlank { "التفسير الميسر للآية الكريمة." },
                                    style = MaterialTheme.typography.bodyMedium,
                                    lineHeight = 24.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleAyahTafsirBottomSheet(
    ayah: Ayah,
    audioState: com.example.audio.AudioPlayerState,
    onPlayAyah: () -> Unit,
    onShareAyah: () -> Unit = {},
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val surahObj = QuranData.surahs.find { it.id == ayah.surahId }
    val isPlayingThis = audioState.isPlaying &&
            audioState.currentSurah?.id == ayah.surahId &&
            audioState.currentAyahNumber == ayah.ayahNumber

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.MenuBook, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "التفسير الميسر",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "سورة ${surahObj?.nameArabic ?: ""} • آية ${toArabicIndicDigits(ayah.ayahNumber)}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onShareAyah) {
                        Icon(
                            Icons.Default.Palette,
                            contentDescription = "مشاركة كصورة مصممة",
                            tint = GoldAccent,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "إغلاق")
                    }
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))

            // Uthmani Verse
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                border = BorderStroke(1.dp, GoldAccent.copy(alpha = 0.4f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = ayah.textUthmani,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Right,
                    lineHeight = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(14.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Tafsir text
            Text(
                text = "التفسير الميسر المعتمد:",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = GoldAccent
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = ayah.tafsir.ifBlank { "التفسير الميسر للآية الكريمة." },
                style = MaterialTheme.typography.bodyMedium,
                lineHeight = 24.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            if (ayah.asbabNuzul.isNotBlank()) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "سبب النزول:",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = EmeraldPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = ayah.asbabNuzul,
                    style = MaterialTheme.typography.bodySmall,
                    lineHeight = 20.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Action buttons: Listen & Copy
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onPlayAyah,
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = if (isPlayingThis) Icons.Default.PauseCircle else Icons.Default.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(if (isPlayingThis) "إيقاف التلاوة" else "استماع للآية")
                }

                OutlinedButton(
                    onClick = {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("Ayah Tafsir", "${ayah.textUthmani}\nالتفسير الميسر: ${ayah.tafsir}")
                        clipboard.setPrimaryClip(clip)
                        Toast.makeText(context, "تم نسخ الآية وتفسيرها الميسر", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("نسخ التفسير")
                }
            }

            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}
