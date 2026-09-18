package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode as AnimRepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.filled.AvTimer
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Downloading
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FastRewind
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ui.components.SheikhAvatar
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.RepeatOne
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.RepeatMode
import com.example.data.QuranData
import com.example.model.Reciter
import com.example.model.Surah
import com.example.ui.theme.EmeraldDark
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.GoldAccent
import com.example.viewmodel.AppViewModel

@Composable
fun AudioPlayerScreen(
    viewModel: AppViewModel,
    modifier: Modifier = Modifier
) {
    val audioState by viewModel.audioPlayer.state.collectAsState()
    val currentSurah = audioState.currentSurah ?: QuranData.surahs.first()
    var selectedPlayerTab by remember { mutableIntStateOf(0) } // 0: Player, 1: Reciters Library, 2: Surah Playlist
    var showSleepTimerDialog by remember { mutableStateOf(false) }
    var hideAyahText by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        // Top Navigation Tabs: Player, Reciters, Surahs
        TabRow(
            selectedTabIndex = selectedPlayerTab,
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = EmeraldPrimary,
            modifier = Modifier.clip(RoundedCornerShape(14.dp))
        ) {
            Tab(
                selected = selectedPlayerTab == 0,
                onClick = { selectedPlayerTab = 0 },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Headphones, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("المشغل", fontSize = 12.sp, fontWeight = if (selectedPlayerTab == 0) FontWeight.Bold else FontWeight.Medium)
                    }
                }
            )
            Tab(
                selected = selectedPlayerTab == 1,
                onClick = { selectedPlayerTab = 1 },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("الشيوخ (${QuranData.reciters.size})", fontSize = 12.sp, fontWeight = if (selectedPlayerTab == 1) FontWeight.Bold else FontWeight.Medium)
                    }
                }
            )
            Tab(
                selected = selectedPlayerTab == 2,
                onClick = { selectedPlayerTab = 2 },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.MenuBook, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("السور (١١٤)", fontSize = 12.sp, fontWeight = if (selectedPlayerTab == 2) FontWeight.Bold else FontWeight.Medium)
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        when (selectedPlayerTab) {
            0 -> {
                // TAB 0: NOW PLAYING SCREEN
                AudioPlayerActiveTab(
                    viewModel = viewModel,
                    audioState = audioState,
                    currentSurah = currentSurah,
                    hideAyahText = hideAyahText,
                    onToggleHideAyah = { hideAyahText = !hideAyahText },
                    onOpenRecitersTab = { selectedPlayerTab = 1 },
                    onOpenSurahsTab = { selectedPlayerTab = 2 },
                    onShowSleepTimer = { showSleepTimerDialog = true }
                )
            }
            1 -> {
                // TAB 1: RECITERS LIBRARY & VOICES
                AudioRecitersLibraryTab(
                    currentReciter = audioState.selectedReciter,
                    onSelectReciter = { reciter ->
                        viewModel.audioPlayer.selectReciter(reciter)
                        selectedPlayerTab = 0
                    }
                )
            }
            2 -> {
                // TAB 2: SURAHS PLAYLIST
                AudioSurahPlaylistTab(
                    viewModel = viewModel,
                    currentSurahId = currentSurah.id,
                    isPlaying = audioState.isPlaying,
                    onSelectSurah = { surah ->
                        viewModel.audioPlayer.playSurah(surah)
                        selectedPlayerTab = 0
                    }
                )
            }
        }
    }

    // Sleep Timer Dialog
    if (showSleepTimerDialog) {
        AlertDialog(
            onDismissRequest = { showSleepTimerDialog = false },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showSleepTimerDialog = false }) { Text("إلغاء") }
            },
            title = { Text("مؤقت النوم لإيقاف التلاوة", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(
                        0 to "إيقاف المؤقت",
                        15 to "بعد ١٥ دقيقة",
                        30 to "بعد ٣٠ دقيقة",
                        45 to "بعد ٤٥ دقيقة",
                        60 to "بعد ٦٠ دقيقة"
                    ).forEach { (mins, label) ->
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.audioPlayer.startSleepTimer(mins)
                                    showSleepTimerDialog = false
                                }
                        ) {
                            Text(
                                text = label,
                                modifier = Modifier.padding(14.dp),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        )
    }
}

// -------------------------------------------------------------------------------------------------
// Sub-View: Active Player Controls Tab
// -------------------------------------------------------------------------------------------------
@Composable
private fun AudioPlayerActiveTab(
    viewModel: AppViewModel,
    audioState: com.example.audio.AudioPlayerState,
    currentSurah: Surah,
    hideAyahText: Boolean,
    onToggleHideAyah: () -> Unit,
    onOpenRecitersTab: () -> Unit,
    onOpenSurahsTab: () -> Unit,
    onShowSleepTimer: () -> Unit
) {
    val reciter = audioState.selectedReciter
    val downloadKey = "${reciter.id}_${currentSurah.id}"
    val isDownloaded = viewModel.isSurahDownloaded(currentSurah.id, reciter.id)
    val downloadProgress by viewModel.audioDownloadsProgress.collectAsState()
    val isDownloading = downloadProgress.containsKey(downloadKey)
    val progressPercent = downloadProgress[downloadKey] ?: 0

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = AnimRepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            // Header Info Card: Reciter Name & Surah Name with Direct Selector Buttons
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Reciter Info & Trigger
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = EmeraldPrimary.copy(alpha = 0.15f),
                        modifier = Modifier
                            .clickable { onOpenRecitersTab() }
                            .weight(1f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            SheikhAvatar(
                                nameArabic = reciter.nameArabic,
                                imageUrl = reciter.imageUrl,
                                isSelected = true,
                                size = 26.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = reciter.nameArabic,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = "تغيير القارئ",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = EmeraldPrimary
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Surah Info & Trigger
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = GoldAccent.copy(alpha = 0.18f),
                        modifier = Modifier
                            .clickable { onOpenSurahsTab() }
                            .weight(1f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.MenuBook, contentDescription = null, tint = Color(0xFFB45309), modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Column {
                                Text(
                                    text = "سورة ${currentSurah.nameArabic}",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    maxLines = 1
                                )
                                Text(
                                    text = "اختيار سورة أخرى",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color(0xFFB45309)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))
        }

        // Circular Disc Art with Animated Waves
        item {
            Box(
                modifier = Modifier
                    .size(if (audioState.isPlaying) (190 * pulseScale).dp else 190.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                GoldAccent.copy(alpha = 0.45f),
                                EmeraldDark,
                                Color(0xFF064E3B)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.GraphicEq,
                        contentDescription = "تلاوة",
                        tint = GoldAccent,
                        modifier = Modifier.size(44.dp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "سورة ${currentSurah.nameArabic}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                    Text(
                        text = "آية ${audioState.currentAyahNumber} من ${currentSurah.versesCount}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = GoldAccent,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Current Ayah Text Card
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                border = BorderStroke(1.dp, EmeraldPrimary.copy(alpha = 0.3f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "نص الآية الكريمة (آية ${audioState.currentAyahNumber})",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldPrimary
                        )

                        IconButton(onClick = onToggleHideAyah, modifier = Modifier.size(32.dp)) {
                            Icon(
                                imageVector = if (hideAyahText) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = "إخفاء/إظهار الآية للتسميع",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    if (hideAyahText) {
                        Text(
                            text = "النص مخفي لمساعدتك على المراجعة والتسميع من الذاكرة 🧠",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    } else {
                        Text(
                            text = audioState.currentAyahText.ifBlank { "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ" },
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center,
                            lineHeight = 32.sp,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
        }

        // Download offline button / progress
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isDownloaded) EmeraldPrimary.copy(alpha = 0.1f)
                    else MaterialTheme.colorScheme.surfaceVariant
                ),
                border = BorderStroke(1.dp, if (isDownloaded) EmeraldPrimary.copy(alpha = 0.4f) else MaterialTheme.colorScheme.outlineVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                            Surface(
                                shape = CircleShape,
                                color = if (isDownloaded) EmeraldPrimary.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surface,
                                modifier = Modifier.size(36.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = if (isDownloaded) Icons.Default.CheckCircle else if (isDownloading) Icons.Default.Downloading else Icons.Default.Download,
                                        contentDescription = null,
                                        tint = if (isDownloaded) EmeraldPrimary else if (isDownloading) GoldAccent else EmeraldPrimary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = if (isDownloaded) "سورة ${currentSurah.nameArabic} محفوظة بدون إنترنت ✓"
                                    else if (isDownloading) "جاري التحميل ($progressPercent%)"
                                    else "تنزيل سورة ${currentSurah.nameArabic} بصوت ${reciter.nameArabic}",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isDownloaded) EmeraldPrimary else MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = if (isDownloaded) "استمع في أي وقت بدون استهلاك باقة البيانات"
                                    else "حفظ السورة بالكامل للاستماع أوفلاين",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        if (isDownloading) {
                            IconButton(onClick = { viewModel.cancelAudioDownload(currentSurah.id, reciter.id) }) {
                                Icon(Icons.Default.Close, contentDescription = "إلغاء", tint = Color.Red)
                            }
                        } else if (isDownloaded) {
                            IconButton(onClick = { viewModel.deleteDownloadedSurah(currentSurah.id, reciter.id) }) {
                                Icon(Icons.Default.DeleteOutline, contentDescription = "حذف", tint = MaterialTheme.colorScheme.error)
                            }
                        } else {
                            FilledTonalButton(
                                onClick = { viewModel.downloadSurah(currentSurah, reciter) },
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("تنزيل", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    if (isDownloading) {
                        Spacer(modifier = Modifier.height(8.dp))
                        LinearProgressIndicator(
                            progress = { progressPercent / 100f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = GoldAccent
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
        }

        // Progress Slider & Timers
        item {
            val progress = if (audioState.durationMs > 0) {
                (audioState.currentPositionMs.toFloat() / audioState.durationMs.toFloat()).coerceIn(0f, 1f)
            } else 0f

            Slider(
                value = progress,
                onValueChange = { newProgress ->
                    val newMs = (newProgress * audioState.durationMs).toInt()
                    viewModel.audioPlayer.seekTo(newMs)
                },
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = formatMs(audioState.currentPositionMs),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = formatMs(audioState.durationMs),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
        }

        // Playback Controls Row: FastRewind, Previous, Play/Pause, Next, FastForward, Repeat
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Repeat Mode Button
                IconButton(
                    onClick = {
                        val nextMode = when (audioState.repeatMode) {
                            RepeatMode.NONE -> RepeatMode.REPEAT_AYAH
                            RepeatMode.REPEAT_AYAH -> RepeatMode.REPEAT_SURAH
                            RepeatMode.REPEAT_SURAH -> RepeatMode.NONE
                        }
                        viewModel.audioPlayer.setRepeatMode(nextMode)
                    }
                ) {
                    Icon(
                        imageVector = when (audioState.repeatMode) {
                            RepeatMode.NONE -> Icons.Default.Repeat
                            RepeatMode.REPEAT_AYAH -> Icons.Default.RepeatOne
                            RepeatMode.REPEAT_SURAH -> Icons.Default.Repeat
                        },
                        contentDescription = "التكرار",
                        tint = if (audioState.repeatMode != RepeatMode.NONE) GoldAccent else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Rewind 10 seconds
                IconButton(
                    onClick = {
                        val target = (audioState.currentPositionMs - 10000).coerceAtLeast(0)
                        viewModel.audioPlayer.seekTo(target)
                    }
                ) {
                    Icon(Icons.Default.FastRewind, contentDescription = "تأخير ١٠ ثوان", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                }

                // Previous Ayah
                IconButton(
                    onClick = { viewModel.audioPlayer.previousAyah() },
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Icon(Icons.Default.SkipPrevious, contentDescription = "الآية السابقة", tint = MaterialTheme.colorScheme.onSurface)
                }

                // Play / Pause glowing button
                Surface(
                    shape = CircleShape,
                    color = EmeraldPrimary,
                    border = BorderStroke(2.dp, GoldAccent),
                    modifier = Modifier
                        .size(64.dp)
                        .clickable { viewModel.audioPlayer.togglePlayPause() }
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = if (audioState.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (audioState.isPlaying) "إيقاف مؤقت" else "تشغيل",
                            tint = Color.White,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }

                // Next Ayah
                IconButton(
                    onClick = { viewModel.audioPlayer.nextAyah() },
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Icon(Icons.Default.SkipNext, contentDescription = "الآية التالية", tint = MaterialTheme.colorScheme.onSurface)
                }

                // Fast Forward 10 seconds
                IconButton(
                    onClick = {
                        val target = (audioState.currentPositionMs + 10000).coerceAtMost(audioState.durationMs)
                        viewModel.audioPlayer.seekTo(target)
                    }
                ) {
                    Icon(Icons.Default.FastForward, contentDescription = "تقديم ١٠ ثوان", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                }

                // Sleep Timer Trigger
                IconButton(onClick = onShowSleepTimer) {
                    Icon(
                        imageVector = Icons.Default.Timer,
                        contentDescription = "مؤقت النوم",
                        tint = if (audioState.sleepTimerMinutesRemaining > 0) EmeraldLight else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
        }

        // Sound Booster & Speed Selector
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.GraphicEq, contentDescription = null, tint = EmeraldPrimary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "مُحسّن نقاء وجودة الصوت (HQ Booster)",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = if (audioState.isSoundBoosterEnabled) "استريو نقي فائق الوضوح مفعّل" else "الصوت الطبيعي القياسي",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (audioState.isSoundBoosterEnabled) EmeraldPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Switch(
                            checked = audioState.isSoundBoosterEnabled,
                            onCheckedChange = { viewModel.audioPlayer.toggleSoundBooster() },
                            colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = EmeraldPrimary)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "سرعة القراءة: ",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(end = 6.dp)
                        )
                        listOf(0.75f, 1.0f, 1.25f, 1.5f).forEach { speed ->
                            FilterChip(
                                selected = audioState.playbackSpeed == speed,
                                onClick = { viewModel.audioPlayer.setPlaybackSpeed(speed) },
                                label = { Text("${speed}x", fontSize = 11.sp) },
                                modifier = Modifier.padding(horizontal = 3.dp)
                            )
                        }
                    }
                }
            }

            if (audioState.sleepTimerMinutesRemaining > 0) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "مؤقت النوم مفعّل: سيتوقف الصوت بعد ${audioState.sleepTimerMinutesRemaining} دقيقة",
                    style = MaterialTheme.typography.labelSmall,
                    color = EmeraldPrimary,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

// -------------------------------------------------------------------------------------------------
// Sub-View: Reciters Library Tab
// -------------------------------------------------------------------------------------------------
@Composable
private fun AudioRecitersLibraryTab(
    currentReciter: Reciter,
    onSelectReciter: (Reciter) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategoryIndex by remember { mutableIntStateOf(0) }
    val categories = listOf(
        "الكل (${QuranData.reciters.size})",
        "👑 أئمة الحرمين",
        "🏛️ عمالقة التلاوة المصرية",
        "🎙️ مشاهير القراء",
        "📜 ورش وقالون",
        "🎓 المصاحف المعلمة"
    )

    val filteredReciters = remember(searchQuery, selectedCategoryIndex) {
        QuranData.reciters.filter { reciter ->
            val matchesCategory = when (selectedCategoryIndex) {
                1 -> reciter.style.contains("الحرم") || reciter.nameArabic.contains("السديس") || reciter.nameArabic.contains("المعيقلي") || reciter.nameArabic.contains("الشريم") || reciter.nameArabic.contains("الدوسري") || reciter.nameArabic.contains("الجهني") || reciter.nameArabic.contains("البدير") || reciter.nameArabic.contains("الحذيفي") || reciter.nameArabic.contains("أيوب")
                2 -> reciter.nameArabic.contains("المنشاوي") || reciter.nameArabic.contains("عبد الباسط") || reciter.nameArabic.contains("الحصري") || reciter.nameArabic.contains("مصطفى إسماعيل") || reciter.nameArabic.contains("البنا") || reciter.nameArabic.contains("الطبلاوي")
                3 -> reciter.nameArabic.contains("العفاسي") || reciter.nameArabic.contains("الغامدي") || reciter.nameArabic.contains("العجمي") || reciter.nameArabic.contains("القطامي") || reciter.nameArabic.contains("الشاطري") || reciter.nameArabic.contains("عباد") || reciter.nameArabic.contains("الرفاعي")
                4 -> reciter.nameArabic.contains("ورش") || reciter.style.contains("ورش") || reciter.style.contains("قالون") || reciter.nameArabic.contains("قالون")
                5 -> reciter.style.contains("معلم") || reciter.nameArabic.contains("المعلم") || reciter.style.contains("مجود") || reciter.nameArabic.contains("مجود")
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

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("ابحث عن قارئ أو شيخ بالاسم أو الرواية...", fontSize = 12.sp) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = EmeraldPrimary) },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(Icons.Default.Close, contentDescription = null)
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = EmeraldPrimary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(categories.size) { index ->
                FilterChip(
                    selected = selectedCategoryIndex == index,
                    onClick = { selectedCategoryIndex = index },
                    label = { Text(categories[index], fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = EmeraldPrimary,
                        selectedLabelColor = Color.White
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "عرض ${filteredReciters.size} قارئاً معتمداً",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(6.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 120.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredReciters, key = { it.id }) { reciter ->
                val isSelected = reciter.id == currentReciter.id

                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) EmeraldPrimary.copy(alpha = 0.15f)
                        else MaterialTheme.colorScheme.surfaceVariant
                    ),
                    border = if (isSelected) BorderStroke(1.5.dp, GoldAccent)
                    else BorderStroke(0.5.dp, MaterialTheme.colorScheme.outlineVariant),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelectReciter(reciter) }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                            // Avatar Icon with Reciter Photo or Initial
                            SheikhAvatar(
                                nameArabic = reciter.nameArabic,
                                imageUrl = reciter.imageUrl,
                                isSelected = isSelected,
                                size = 50.dp
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = reciter.nameArabic,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    if (isSelected) {
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Surface(
                                            shape = RoundedCornerShape(6.dp),
                                            color = GoldAccent.copy(alpha = 0.2f)
                                        ) {
                                            Text(
                                                text = "القارئ الحالي",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = GoldAccent,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(2.dp))

                                Text(
                                    text = reciter.style,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }

                        Button(
                            onClick = { onSelectReciter(reciter) },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isSelected) GoldAccent else EmeraldPrimary,
                                contentColor = if (isSelected) EmeraldDark else Color.White
                            ),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            if (isSelected) {
                                Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("المختار", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            } else {
                                Text("اختيار", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------------------------------------------
// Sub-View: Surahs Playlist for Instant Listening
// -------------------------------------------------------------------------------------------------
@Composable
private fun AudioSurahPlaylistTab(
    viewModel: AppViewModel,
    currentSurahId: Int,
    isPlaying: Boolean,
    onSelectSurah: (Surah) -> Unit
) {
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }
    val audioState by viewModel.audioPlayer.state.collectAsState()
    val reciter = audioState.selectedReciter
    val activeDownloads by viewModel.activeAudioDownloads.collectAsState()

    val filteredSurahs = remember(searchQuery) {
        if (searchQuery.isBlank()) QuranData.surahs
        else {
            val q = searchQuery.trim()
            QuranData.surahs.filter {
                it.nameArabic.contains(q, ignoreCase = true) ||
                        it.nameEnglish.contains(q, ignoreCase = true) ||
                        it.id.toString() == q
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("ابحث عن سورة للاستماع الفوري...", fontSize = 12.sp) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = EmeraldPrimary) },
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = EmeraldPrimary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 120.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredSurahs, key = { it.id }) { surah ->
                val isCurrent = surah.id == currentSurahId
                val isDownloaded = viewModel.isSurahDownloaded(surah.id, reciter.id)
                val isDownloading = activeDownloads.contains("${reciter.id}_${surah.id}")

                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isCurrent) EmeraldPrimary.copy(alpha = 0.15f)
                        else MaterialTheme.colorScheme.surfaceVariant
                    ),
                    border = if (isCurrent) BorderStroke(1.dp, GoldAccent) else null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelectSurah(surah) }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = if (isCurrent) GoldAccent else EmeraldPrimary.copy(alpha = 0.15f),
                                modifier = Modifier.size(38.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = "${surah.id}",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isCurrent) EmeraldDark else EmeraldPrimary
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {
                                Text(
                                    text = "سورة ${surah.nameArabic}",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "${surah.revelationType.arabicName} • ${surah.versesCount} آية • صفحة ${surah.startPage}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // Download Surah Button
                            IconButton(
                                onClick = {
                                    if (isDownloaded) {
                                        android.widget.Toast.makeText(context, "سورة ${surah.nameArabic} محملة بالكامل أوفلاين ✓", android.widget.Toast.LENGTH_SHORT).show()
                                    } else if (isDownloading) {
                                        android.widget.Toast.makeText(context, "جاري التنزيل حالياً...", android.widget.Toast.LENGTH_SHORT).show()
                                    } else {
                                        viewModel.downloadSurah(surah, reciter)
                                        android.widget.Toast.makeText(context, "بدأ تنزيل سورة ${surah.nameArabic} للاستماع بدون نت 📥", android.widget.Toast.LENGTH_SHORT).show()
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
                                        modifier = Modifier.size(16.dp),
                                        strokeWidth = 2.dp,
                                        color = GoldAccent
                                    )
                                } else {
                                    Icon(
                                        imageVector = if (isDownloaded) Icons.Default.CheckCircle else Icons.Default.Download,
                                        contentDescription = "تنزيل السورة",
                                        tint = if (isDownloaded) EmeraldPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(6.dp))

                            IconButton(
                                onClick = { onSelectSurah(surah) },
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(if (isCurrent && isPlaying) GoldAccent else EmeraldPrimary)
                            ) {
                                Icon(
                                    imageVector = if (isCurrent && isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                    contentDescription = "تشغيل",
                                    tint = if (isCurrent && isPlaying) EmeraldDark else Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun formatMs(ms: Int): String {
    val totalSec = ms / 1000
    val m = totalSec / 60
    val s = totalSec % 60
    return "${m.toString().padStart(2, '0')}:${s.toString().padStart(2, '0')}"
}
