package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Azkar132DoorsData
import com.example.data.AzkarAudioData
import com.example.data.AzkarAudioTrack
import com.example.data.AzkarData
import com.example.data.AzkarReciter
import com.example.data.GoldenAdviceData
import com.example.data.GoldenAdviceItem
import com.example.data.HadithCategory
import com.example.data.HadithData
import com.example.data.HadithItem
import com.example.model.AzkarCategory
import com.example.model.DhikrItem
import com.example.ui.theme.CorrectGreen
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.GoldAccent
import com.example.viewmodel.AppViewModel

@Composable
fun AzkarScreen(
    viewModel: AppViewModel,
    initialTab: Int = 0,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val selectedCategory by viewModel.selectedAzkarCategory.collectAsState()
    val countsMap by viewModel.azkarCounts.collectAsState()

    var activeMainTab by remember { mutableIntStateOf(initialTab.coerceIn(0, 4)) }
    LaunchedEffect(initialTab) {
        activeMainTab = initialTab.coerceIn(0, 4)
    }

    val azkarAudioState by viewModel.azkarAudioController.state.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var isDoorIndexDialogOpen by remember { mutableStateOf(false) }

    // Digital Subha local state
    var subhaCount by remember { mutableIntStateOf(0) }
    var subhaLaps by remember { mutableIntStateOf(0) }
    var subhaTarget by remember { mutableIntStateOf(33) }
    var selectedSubhaPhrase by remember { mutableStateOf("سُبْحَانَ اللَّهِ وَبِحَمْدِهِ") }

    val subhaPhrases = listOf(
        "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
        "سُبْحَانَ اللَّهِ الْعَظِيمِ",
        "الْحَمْدُ لِلَّهِ",
        "لاَ إِلَهَ إِلاَّ اللَّهُ",
        "اللَّهُ أَكْبَرُ",
        "أَسْتَغْفِرُ اللَّهَ وَأَتُوبُ إِلَيْهِ",
        "لاَ حَوْلَ وَلاَ قُوَّةَ إِلاَّ بِاللَّهِ",
        "اللَّهُمَّ صَلِّ عَلَى نَبِيِّنَا مُحَمَّدٍ"
    )

    fun performHaptic() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
                vibratorManager?.defaultVibrator?.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_TICK))
            } else {
                @Suppress("DEPRECATION")
                val v = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
                @Suppress("DEPRECATION")
                v?.vibrate(30)
            }
        } catch (_: Exception) {}
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Main Section Tabs: 132 Doors Azkar, Audio Recitations, Hadiths, Golden Advice, Subha
        ScrollableTabRow(
            selectedTabIndex = activeMainTab,
            edgePadding = 8.dp,
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = EmeraldPrimary
        ) {
            Tab(
                selected = activeMainTab == 0,
                onClick = { activeMainTab = 0; searchQuery = "" },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.MenuBook, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("الأذكار (١٣٢)", fontSize = 12.sp, fontWeight = if (activeMainTab == 0) FontWeight.Bold else FontWeight.Normal)
                    }
                }
            )
            Tab(
                selected = activeMainTab == 1,
                onClick = { activeMainTab = 1; searchQuery = "" },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Headphones, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("أصوات الشيوخ 🎧", fontSize = 12.sp, fontWeight = if (activeMainTab == 1) FontWeight.Bold else FontWeight.Normal)
                    }
                }
            )
            Tab(
                selected = activeMainTab == 2,
                onClick = { activeMainTab = 2; searchQuery = "" },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.AutoStories, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("الأحاديث", fontSize = 12.sp, fontWeight = if (activeMainTab == 2) FontWeight.Bold else FontWeight.Normal)
                    }
                }
            )
            Tab(
                selected = activeMainTab == 3,
                onClick = { activeMainTab = 3; searchQuery = "" },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Lightbulb, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("النصائح الذهبية", fontSize = 12.sp, fontWeight = if (activeMainTab == 3) FontWeight.Bold else FontWeight.Normal)
                    }
                }
            )
            Tab(
                selected = activeMainTab == 4,
                onClick = { activeMainTab = 4; searchQuery = "" },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.TouchApp, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("السبحة", fontSize = 12.sp, fontWeight = if (activeMainTab == 4) FontWeight.Bold else FontWeight.Normal)
                    }
                }
            )
        }

        // Mini Sticky Player Bar when an Azkar track is active
        if (azkarAudioState.currentTrack != null) {
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 6.dp)
                    .clickable { activeMainTab = 1 }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Headphones,
                        contentDescription = null,
                        tint = EmeraldPrimary,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = azkarAudioState.currentTrack?.title ?: "",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            maxLines = 1
                        )
                        Text(
                            text = "${azkarAudioState.currentTrack?.sheikhName} • ${AzkarAudioData.formatTime(azkarAudioState.currentPositionMs)} / ${AzkarAudioData.formatTime(azkarAudioState.durationMs)}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                        )
                    }
                    IconButton(
                        onClick = { viewModel.azkarAudioController.togglePlayPause() },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = if (azkarAudioState.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (azkarAudioState.isPlaying) "إيقاف مؤقت" else "تشغيل",
                            tint = EmeraldPrimary
                        )
                    }
                    IconButton(
                        onClick = { viewModel.azkarAudioController.stop() },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Stop,
                            contentDescription = "إيقاف",
                            tint = Color(0xFFEF4444)
                        )
                    }
                }
            }
        }

        when (activeMainTab) {
            0 -> {
                // === TAB 0: 132 DOORS OF AZKAR & DUA ===
                Azkar132DoorsView(
                    viewModel = viewModel,
                    searchQuery = searchQuery,
                    onSearchQueryChange = { searchQuery = it },
                    selectedCategory = selectedCategory,
                    countsMap = countsMap,
                    onOpenDoorIndex = { isDoorIndexDialogOpen = true },
                    onPerformHaptic = { performHaptic() }
                )
            }
            1 -> {
                // === TAB 1: AUDIO RECITATIONS BY FAMOUS SHEIKHS ===
                AzkarAudioRecitationsView(viewModel = viewModel)
            }
            2 -> {
                // === TAB 2: HADITH ENCYCLOPEDIA ===
                HadithLibraryView(
                    searchQuery = searchQuery,
                    onSearchQueryChange = { searchQuery = it }
                )
            }
            3 -> {
                // === TAB 3: DAILY GOLDEN ADVICE ===
                GoldenAdviceView(
                    searchQuery = searchQuery,
                    onSearchQueryChange = { searchQuery = it }
                )
            }
            4 -> {
                // === TAB 4: INTERACTIVE DIGITAL SUBHA ===
                DigitalSubhaView(
                    subhaCount = subhaCount,
                    subhaLaps = subhaLaps,
                    subhaTarget = subhaTarget,
                    selectedSubhaPhrase = selectedSubhaPhrase,
                    subhaPhrases = subhaPhrases,
                    onCountChange = { subhaCount = it },
                    onLapsChange = { subhaLaps = it },
                    onTargetChange = { subhaTarget = it },
                    onPhraseChange = { selectedSubhaPhrase = it },
                    onPerformHaptic = { performHaptic() }
                )
            }
        }
    }

    // Modal Dialog: Full 132 Doors Index with fast search
    if (isDoorIndexDialogOpen) {
        DoorIndexDialog(
            categories = Azkar132DoorsData.allDoors,
            selectedId = selectedCategory.id,
            onSelectDoor = { cat ->
                viewModel.selectAzkarCategory(cat)
                isDoorIndexDialogOpen = false
            },
            onDismiss = { isDoorIndexDialogOpen = false }
        )
    }
}

// -------------------------------------------------------------------------------------------------
// Sub-View: 132 Doors of Azkar
// -------------------------------------------------------------------------------------------------
@Composable
private fun Azkar132DoorsView(
    viewModel: AppViewModel,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    selectedCategory: AzkarCategory,
    countsMap: Map<Int, Int>,
    onOpenDoorIndex: () -> Unit,
    onPerformHaptic: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Search bar & 132 Doors index button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                modifier = Modifier
                    .weight(1f)
                    .testTag("azkar_search_field"),
                placeholder = { Text("بحث في ١٣٢ باباً وجميع الأذكار...", fontSize = 12.sp) },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "بحث", tint = EmeraldPrimary)
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { onSearchQueryChange("") }) {
                            Icon(Icons.Default.Clear, contentDescription = "مسح")
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = EmeraldPrimary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                )
            )

            // Index Dialog Trigger Button
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = EmeraldPrimary,
                modifier = Modifier
                    .clickable { onOpenDoorIndex() }
                    .testTag("door_index_trigger_button")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.FormatListNumbered,
                        contentDescription = "فهرس الأبواب",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "١٣٢ باباً",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }
        }

        // All 132 doors
        val allDoors = Azkar132DoorsData.allDoors

        // Quick Daily Essential Azkar Shortcuts
        if (searchQuery.isBlank()) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    val morningDoor = allDoors.find { it.nameArabic.contains("الصباح") }
                    val isSelected = morningDoor?.id == selectedCategory.id
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color.Transparent,
                        modifier = Modifier
                            .width(180.dp)
                            .clickable {
                                morningDoor?.let {
                                    viewModel.selectAzkarCategory(it)
                                    onSearchQueryChange("")
                                }
                            }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Brush.horizontalGradient(
                                        listOf(Color(0xFFD97706), Color(0xFFF59E0B))
                                    ),
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .border(
                                    width = if (isSelected) 2.dp else 0.dp,
                                    color = if (isSelected) Color.White else Color.Transparent,
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .padding(12.dp)
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.WbSunny,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(22.dp)
                                    )
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = Color.White.copy(alpha = 0.25f)
                                    ) {
                                        Text(
                                            text = "الباب ٢٧",
                                            color = Color.White,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "أذكار الصباح",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                                Text(
                                    text = "بركة اليوم وحفظه",
                                    color = Color.White.copy(alpha = 0.85f),
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }
                }

                item {
                    val eveningDoor = allDoors.find { it.nameArabic.contains("المساء") }
                    val isSelected = eveningDoor?.id == selectedCategory.id
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color.Transparent,
                        modifier = Modifier
                            .width(180.dp)
                            .clickable {
                                eveningDoor?.let {
                                    viewModel.selectAzkarCategory(it)
                                    onSearchQueryChange("")
                                }
                            }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Brush.horizontalGradient(
                                        listOf(Color(0xFF4338CA), Color(0xFF6366F1))
                                    ),
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .border(
                                    width = if (isSelected) 2.dp else 0.dp,
                                    color = if (isSelected) Color.White else Color.Transparent,
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .padding(12.dp)
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AutoAwesome,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(22.dp)
                                    )
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = Color.White.copy(alpha = 0.25f)
                                    ) {
                                        Text(
                                            text = "الباب ٢٨",
                                            color = Color.White,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "أذكار المساء",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                                Text(
                                    text = "سكينة النفس والتحصين",
                                    color = Color.White.copy(alpha = 0.85f),
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }
                }

                item {
                    val prayerDoor = allDoors.find { it.nameArabic.contains("بعد السلام") }
                    val isSelected = prayerDoor?.id == selectedCategory.id
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color.Transparent,
                        modifier = Modifier
                            .width(180.dp)
                            .clickable {
                                prayerDoor?.let {
                                    viewModel.selectAzkarCategory(it)
                                    onSearchQueryChange("")
                                }
                            }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Brush.horizontalGradient(
                                        listOf(Color(0xFF047857), Color(0xFF10B981))
                                    ),
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .border(
                                    width = if (isSelected) 2.dp else 0.dp,
                                    color = if (isSelected) Color.White else Color.Transparent,
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .padding(12.dp)
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.MenuBook,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(22.dp)
                                    )
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = Color.White.copy(alpha = 0.25f)
                                    ) {
                                        Text(
                                            text = "الباب ٢٥",
                                            color = Color.White,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "أذكار بعد الصلاة",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                                Text(
                                    text = "الاستغفار والتسبيح",
                                    color = Color.White.copy(alpha = 0.85f),
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }
                }

                item {
                    val tahiyyatDoor = allDoors.find { it.nameArabic.contains("التشهد") || it.nameArabic.contains("التحيات") }
                    val isSelected = tahiyyatDoor?.id == selectedCategory.id
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color.Transparent,
                        modifier = Modifier
                            .width(195.dp)
                            .clickable {
                                tahiyyatDoor?.let {
                                    viewModel.selectAzkarCategory(it)
                                    onSearchQueryChange("")
                                }
                            }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Brush.horizontalGradient(
                                        listOf(Color(0xFF0D9488), Color(0xFF14B8A6))
                                    ),
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .border(
                                    width = if (isSelected) 2.dp else 0.dp,
                                    color = if (isSelected) Color.White else Color.Transparent,
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .padding(12.dp)
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AutoStories,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(22.dp)
                                    )
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = Color.White.copy(alpha = 0.25f)
                                    ) {
                                        Text(
                                            text = "الباب ٢٢",
                                            color = Color.White,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "التحيات لله والصلوات",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                                Text(
                                    text = "دعاء ختام الصلاة بأشهر الأصوات 🎙️",
                                    color = Color.White.copy(alpha = 0.85f),
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }
                }

                item {
                    val sleepDoor = allDoors.find { it.nameArabic.contains("أذكار النوم") }
                    val isSelected = sleepDoor?.id == selectedCategory.id
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color.Transparent,
                        modifier = Modifier
                            .width(180.dp)
                            .clickable {
                                sleepDoor?.let {
                                    viewModel.selectAzkarCategory(it)
                                    onSearchQueryChange("")
                                }
                            }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Brush.horizontalGradient(
                                        listOf(Color(0xFF1E293B), Color(0xFF334155))
                                    ),
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .border(
                                    width = if (isSelected) 2.dp else 0.dp,
                                    color = if (isSelected) Color.White else Color.Transparent,
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .padding(12.dp)
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Favorite,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(22.dp)
                                    )
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = Color.White.copy(alpha = 0.25f)
                                    ) {
                                        Text(
                                            text = "الباب ٢٩",
                                            color = Color.White,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "أذكار النوم",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                                Text(
                                    text = "آية الكرسي والمعوذات",
                                    color = Color.White.copy(alpha = 0.85f),
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))
        }

        // Thematic Classification Pillars
        var selectedPillarIndex by remember { mutableIntStateOf(0) }
        val doorPillars = listOf(
            "🌟 الكل (١٣٢)",
            "🌅 اليوم والليلة",
            "🕌 الصلاة والمساجد",
            "🤲 تفريج الكرب",
            "🩺 الرقية والشفاء",
            "🧳 السفر والتنقل",
            "🍽️ الطعام والشراب",
            "💍 المناسبات والآداب"
        )

        fun checkPillar(cat: AzkarCategory, pillar: Int): Boolean {
            if (pillar == 0) return true
            val name = cat.nameArabic
            return when (pillar) {
                1 -> name.contains("الصباح") || name.contains("المساء") || name.contains("الاستيقاظ") || name.contains("النوم") || name.contains("المنزل") || name.contains("الخلاء") || name.contains("الوضوء") || name.contains("الثوب") || name.contains("الرؤيا")
                2 -> name.contains("الصلاة") || name.contains("المسجد") || name.contains("الأذان") || name.contains("الاستفتاح") || name.contains("الركوع") || name.contains("السجود") || name.contains("التشهد") || name.contains("الوتر") || name.contains("الجمعة") || name.contains("السلام")
                3 -> name.contains("الكرب") || name.contains("الهم") || name.contains("الحزن") || name.contains("الدين") || name.contains("العدو") || name.contains("الخوف") || name.contains("الغضب") || name.contains("المصيبة") || name.contains("الريح") || name.contains("الرعد") || name.contains("المطر") || name.contains("الاستخارة") || name.contains("الوسوسة")
                4 -> name.contains("المريض") || name.contains("الرقية") || name.contains("المرض") || name.contains("الوجع") || name.contains("الموت") || name.contains("الجنازة") || name.contains("القبور")
                5 -> name.contains("السفر") || name.contains("الدابة") || name.contains("الركوب") || name.contains("القرية") || name.contains("البلدة") || name.contains("الرجوع")
                6 -> name.contains("الطعام") || name.contains("الشراب") || name.contains("الضيف") || name.contains("الصائم") || name.contains("الفراغ") || name.contains("الفاكهة")
                7 -> !checkPillar(cat, 1) && !checkPillar(cat, 2) && !checkPillar(cat, 3) && !checkPillar(cat, 4) && !checkPillar(cat, 5) && !checkPillar(cat, 6)
                else -> true
            }
        }

        val filteredDoors = remember(selectedPillarIndex) {
            allDoors.filter { checkPillar(it, selectedPillarIndex) }
        }

        // Horizontal Pillars Chips
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(doorPillars.indices.toList()) { index ->
                val title = doorPillars[index]
                val isSelected = selectedPillarIndex == index
                FilterChip(
                    selected = isSelected,
                    onClick = {
                        selectedPillarIndex = index
                        val firstInPillar = allDoors.firstOrNull { checkPillar(it, index) }
                        if (firstInPillar != null && !checkPillar(selectedCategory, index)) {
                            viewModel.selectAzkarCategory(firstInPillar)
                        }
                    },
                    label = {
                        Text(
                            text = title,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = EmeraldPrimary,
                        selectedLabelColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
            }
        }

        // Horizontal Quick Scroll Tabs for the doors in selected pillar
        val currentDoorIndex = filteredDoors.indexOfFirst { it.id == selectedCategory.id }.coerceAtLeast(0)

        ScrollableTabRow(
            selectedTabIndex = currentDoorIndex,
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            edgePadding = 16.dp
        ) {
            filteredDoors.forEachIndexed { index, cat ->
                val isSelected = cat.id == selectedCategory.id
                val globalIndex = allDoors.indexOf(cat) + 1
                Tab(
                    selected = isSelected,
                    onClick = {
                        viewModel.selectAzkarCategory(cat)
                        onSearchQueryChange("")
                    },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "$globalIndex. ${cat.nameArabic.replace(Regex("^[٠-٩0-9]+[.\\s-]*"), "")}",
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) EmeraldPrimary else MaterialTheme.colorScheme.onSurface,
                                fontSize = 12.sp
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Surface(
                                shape = CircleShape,
                                color = if (isSelected) EmeraldPrimary.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surface
                            ) {
                                Text(
                                    text = "${cat.items.size}",
                                    fontSize = 10.sp,
                                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp),
                                    color = if (isSelected) EmeraldPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                )
            }
        }

        // Items List
        val displayedItems = if (searchQuery.isNotBlank()) {
            allDoors.flatMap { it.items }.filter {
                it.text.contains(searchQuery, ignoreCase = true) ||
                it.virtue.contains(searchQuery, ignoreCase = true) ||
                it.reference.contains(searchQuery, ignoreCase = true)
            }.distinctBy { it.id }
        } else {
            selectedCategory.items
        }

        val completedCount = selectedCategory.items.count { item ->
            val c = countsMap[item.id] ?: 0
            c >= item.targetCount
        }
        val totalItems = selectedCategory.items.size
        val progressFloat = if (totalItems > 0) (completedCount.toFloat() / totalItems).coerceIn(0f, 1f) else 0f

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(10.dp))
                if (searchQuery.isBlank()) {
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.MenuBook,
                                        contentDescription = null,
                                        tint = EmeraldPrimary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = selectedCategory.nameArabic,
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Text(
                                    text = "$completedCount من $totalItems أذكار",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = EmeraldPrimary,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            LinearProgressIndicator(
                                progress = { progressFloat },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(3.dp)),
                                color = EmeraldLight,
                                trackColor = MaterialTheme.colorScheme.surface
                            )
                        }
                    }
                } else {
                    Text(
                        text = "نتائج البحث في الأبواب (${displayedItems.size} ذكر/دعاء)",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldPrimary
                    )
                }
            }

            // اختيار القارئ المعتمد للأذكار (تسجيلات حقيقية بدون ذكاء اصطناعي)
            item {
                val selectedSheikhId by com.example.audio.AzkarAudioPlayer.selectedSheikhId.collectAsState()
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    border = BorderStroke(1.dp, EmeraldPrimary.copy(alpha = 0.25f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp, bottom = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Headphones,
                                    contentDescription = null,
                                    tint = EmeraldPrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "صوت القارئ المعتمد للأذكار:",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            Text(
                                text = "تسجيلات بشرية حقيقية 🎙️",
                                style = MaterialTheme.typography.labelSmall,
                                color = GoldAccent,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            com.example.audio.AzkarAudioPlayer.sheikhOptions.forEach { sheikh ->
                                val isSelected = sheikh.id == selectedSheikhId
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { com.example.audio.AzkarAudioPlayer.setReciter(sheikh.id) },
                                    label = {
                                        Text(
                                            text = sheikh.name.replace("الشيخ ", ""),
                                            fontSize = 11.sp,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                        )
                                    },
                                    leadingIcon = if (isSelected) {
                                        {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = null,
                                                modifier = Modifier.size(12.dp)
                                            )
                                        }
                                    } else null,
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = EmeraldPrimary,
                                        selectedLabelColor = Color.White,
                                        selectedLeadingIconColor = Color.White
                                    ),
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }

            items(displayedItems, key = { it.id }) { dhikr ->
                val currentCount = countsMap[dhikr.id] ?: 0
                EnhancedDhikrCard(
                    dhikr = dhikr,
                    count = currentCount,
                    onIncrement = {
                        onPerformHaptic()
                        viewModel.incrementDhikr(dhikr.id, dhikr.targetCount)
                    },
                    onReset = { viewModel.resetDhikr(dhikr.id) }
                )
            }

            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

// -------------------------------------------------------------------------------------------------
// Dialog: 132 Doors Index
// -------------------------------------------------------------------------------------------------
@Composable
private fun DoorIndexDialog(
    categories: List<AzkarCategory>,
    selectedId: String,
    onSelectDoor: (AzkarCategory) -> Unit,
    onDismiss: () -> Unit
) {
    var dialogSearch by remember { mutableStateOf("") }
    val filtered = if (dialogSearch.isBlank()) categories else categories.filter {
        it.nameArabic.contains(dialogSearch, ignoreCase = true)
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "فهرس أبواب الأذكار (١٣٢ باباً)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = EmeraldPrimary
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Clear, contentDescription = "إغلاق")
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = dialogSearch,
                    onValueChange = { dialogSearch = it },
                    placeholder = { Text("ابحث في اسم الباب...", fontSize = 12.sp) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
            }
        },
        text = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(380.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(filtered) { cat ->
                    val index = categories.indexOf(cat) + 1
                    val isSelected = cat.id == selectedId

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = if (isSelected) EmeraldPrimary.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSelectDoor(cat) }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "$index.",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = EmeraldPrimary,
                                    modifier = Modifier.width(32.dp)
                                )
                                Text(
                                    text = cat.nameArabic,
                                    fontSize = 13.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            Surface(
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.surface
                            ) {
                                Text(
                                    text = "${cat.items.size}",
                                    fontSize = 10.sp,
                                    color = EmeraldPrimary,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("إغلاق", fontWeight = FontWeight.Bold)
            }
        }
    )
}

// -------------------------------------------------------------------------------------------------
// Sub-View: Hadith Encyclopedia
// -------------------------------------------------------------------------------------------------
@Composable
private fun HadithLibraryView(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
) {
    val context = LocalContext.current
    var selectedCategoryId by remember { mutableStateOf("nawawi") }

    val filteredHadiths = if (searchQuery.isNotBlank()) {
        HadithData.hadiths.filter {
            it.title.contains(searchQuery, ignoreCase = true) ||
            it.text.contains(searchQuery, ignoreCase = true) ||
            it.narrator.contains(searchQuery, ignoreCase = true) ||
            it.explanation.contains(searchQuery, ignoreCase = true)
        }
    } else {
        HadithData.hadiths.filter { it.categoryId == selectedCategoryId }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .testTag("hadith_search_field"),
            placeholder = { Text("بحث في متون وشروح الأحاديث النبوية...", fontSize = 12.sp) },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "بحث", tint = EmeraldPrimary)
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { onSearchQueryChange("") }) {
                        Icon(Icons.Default.Clear, contentDescription = "مسح")
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = EmeraldPrimary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
            )
        )

        // Category Chips
        if (searchQuery.isBlank()) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(HadithData.categories) { cat ->
                    FilterChip(
                        selected = selectedCategoryId == cat.id,
                        onClick = { selectedCategoryId = cat.id },
                        label = { Text(cat.titleArabic, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = EmeraldPrimary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(4.dp))
                val currentCategory = HadithData.categories.find { it.id == selectedCategoryId }
                if (searchQuery.isBlank() && currentCategory != null) {
                    Text(
                        text = "${currentCategory.titleArabic} • ${currentCategory.subtitle}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium
                    )
                } else if (searchQuery.isNotBlank()) {
                    Text(
                        text = "نتائج البحث في الأحاديث: ${filteredHadiths.size} حديث",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldPrimary
                    )
                }
            }

            items(filteredHadiths, key = { it.id }) { hadith ->
                HadithCard(hadith = hadith)
            }

            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

@Composable
private fun HadithCard(hadith: HadithItem) {
    val context = LocalContext.current

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = EmeraldPrimary.copy(alpha = 0.12f)
                ) {
                    Text(
                        text = hadith.grade,
                        style = MaterialTheme.typography.labelSmall,
                        color = EmeraldPrimary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                Row {
                    IconButton(
                        onClick = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("حديث شريف", "${hadith.title}\n${hadith.narrator}\n${hadith.text}\n[${hadith.source}]")
                            clipboard.setPrimaryClip(clip)
                            Toast.makeText(context, "تم نسخ الحديث الشريف", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(Icons.Default.ContentCopy, contentDescription = "نسخ", tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(16.dp))
                    }

                    IconButton(
                        onClick = {
                            val sendIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, "${hadith.title}\n\n${hadith.narrator}\n${hadith.text}\n\nالشرح والفائدة:\n${hadith.explanation}\n[المصدر: ${hadith.source}]")
                                type = "text/plain"
                            }
                            context.startActivity(Intent.createChooser(sendIntent, "مشاركة الحديث الشريف"))
                        },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(Icons.Default.Share, contentDescription = "مشاركة", tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(16.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = hadith.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = hadith.narrator,
                style = MaterialTheme.typography.bodySmall,
                color = GoldAccent,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Matn
            Text(
                text = hadith.text,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                lineHeight = 30.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Explanation / lessons
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = "💡 الشرح والفقه المستفاد:",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = hadith.explanation,
                        style = MaterialTheme.typography.bodySmall,
                        lineHeight = 20.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "المصدر: ${hadith.source}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// -------------------------------------------------------------------------------------------------
// Sub-View: Daily Golden Advice
// -------------------------------------------------------------------------------------------------
@Composable
private fun GoldenAdviceView(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
) {
    val context = LocalContext.current
    val adviceOfTheDay = GoldenAdviceData.getAdviceOfTheDay()

    val filtered = if (searchQuery.isNotBlank()) {
        GoldenAdviceData.advices.filter {
            it.title.contains(searchQuery, ignoreCase = true) ||
            it.quote.contains(searchQuery, ignoreCase = true) ||
            it.author.contains(searchQuery, ignoreCase = true) ||
            it.category.contains(searchQuery, ignoreCase = true) ||
            it.practicalTakeaway.contains(searchQuery, ignoreCase = true)
        }
    } else {
        GoldenAdviceData.advices
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .testTag("golden_advice_search_field"),
            placeholder = { Text("بحث في النصائح والوصايا الذهبية...", fontSize = 12.sp) },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "بحث", tint = GoldAccent)
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { onSearchQueryChange("") }) {
                        Icon(Icons.Default.Clear, contentDescription = "مسح")
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = GoldAccent,
                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
            )
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            if (searchQuery.isBlank()) {
                // Advice of the Day Hero Banner
                item {
                    Card(
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = EmeraldPrimary),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(20.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "النصيحة الذهبية لليوم",
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        fontSize = 15.sp
                                    )
                                }
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color.White.copy(alpha = 0.2f)
                                ) {
                                    Text(
                                        text = adviceOfTheDay.author,
                                        fontSize = 11.sp,
                                        color = Color.White,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = adviceOfTheDay.quote,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                lineHeight = 28.sp
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color.Black.copy(alpha = 0.25f),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Text("🎯 ", fontSize = 14.sp)
                                    Text(
                                        text = adviceOfTheDay.practicalTakeaway,
                                        fontSize = 12.sp,
                                        color = Color.White.copy(alpha = 0.95f),
                                        lineHeight = 18.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            items(filtered, key = { it.id }) { item ->
                GoldenAdviceCard(item = item)
            }

            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

@Composable
private fun GoldenAdviceCard(item: GoldenAdviceItem) {
    val context = LocalContext.current

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = GoldAccent.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = item.category,
                        style = MaterialTheme.typography.labelSmall,
                        color = GoldAccent,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                Row {
                    IconButton(
                        onClick = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("نصيحة ذهبية", "${item.title}\n${item.quote}\n- ${item.author}")
                            clipboard.setPrimaryClip(clip)
                            Toast.makeText(context, "تم نسخ النصيحة الذهبية", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(Icons.Default.ContentCopy, contentDescription = "نسخ", tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(16.dp))
                    }

                    IconButton(
                        onClick = {
                            val sendIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, "«${item.title}»\n\n${item.quote}\n\n— قائلها: ${item.author}\n\n💡 التطبيق العملي اليوم:\n${item.practicalTakeaway}")
                                type = "text/plain"
                            }
                            context.startActivity(Intent.createChooser(sendIntent, "مشاركة النصيحة الذهبية"))
                        },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(Icons.Default.Share, contentDescription = "مشاركة", tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(16.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "— ${item.author}",
                style = MaterialTheme.typography.bodySmall,
                color = EmeraldPrimary,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = item.quote,
                style = MaterialTheme.typography.bodyMedium,
                lineHeight = 26.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(10.dp))

            Surface(
                shape = RoundedCornerShape(10.dp),
                color = EmeraldPrimary.copy(alpha = 0.08f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("💡 ", fontSize = 14.sp)
                    Text(
                        text = "التطبيق العملي: ${item.practicalTakeaway}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 19.sp
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------------------------------------------
// Sub-View: Digital Subha
// -------------------------------------------------------------------------------------------------
@Composable
private fun DigitalSubhaView(
    subhaCount: Int,
    subhaLaps: Int,
    subhaTarget: Int,
    selectedSubhaPhrase: String,
    subhaPhrases: List<String>,
    onCountChange: (Int) -> Unit,
    onLapsChange: (Int) -> Unit,
    onTargetChange: (Int) -> Unit,
    onPhraseChange: (String) -> Unit,
    onPerformHaptic: () -> Unit
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "السبحة الإلكترونية التفاعلية",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = EmeraldPrimary
            )
            Text(
                text = "اختر صيغة الذكر واضغط على الدائرة الكبرى للتسبيح",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Phrases Quick Selector Chips
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 4.dp)
            ) {
                items(subhaPhrases) { phrase ->
                    FilterChip(
                        selected = selectedSubhaPhrase == phrase,
                        onClick = {
                            onPhraseChange(phrase)
                            onCountChange(0)
                        },
                        label = { Text(phrase, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = EmeraldPrimary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
        }

        // Target counter selector (33, 100, 1000)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                listOf(33, 100, 1000).forEach { target ->
                    FilterChip(
                        selected = subhaTarget == target,
                        onClick = {
                            onTargetChange(target)
                            onCountChange(0)
                        },
                        label = { Text("الهدف: $target", fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = GoldAccent,
                            selectedLabelColor = Color.Black
                        )
                    )
                }
            }
        }

        // Selected Phrase Banner
        item {
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = selectedSubhaPhrase,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldPrimary,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "الدورات المكتملة: $subhaLaps دورة",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // The Big Interactive Subha Clicker
        item {
            Box(
                modifier = Modifier
                    .size(240.dp)
                    .clip(CircleShape)
                    .background(EmeraldPrimary)
                    .border(8.dp, GoldAccent, CircleShape)
                    .clickable {
                        onPerformHaptic()
                        val nextCount = subhaCount + 1
                        if (nextCount >= subhaTarget) {
                            onCountChange(0)
                            onLapsChange(subhaLaps + 1)
                            Toast.makeText(context, "ما شاء الله! اكتملت الدورة ${subhaLaps + 1} بنجاح", Toast.LENGTH_SHORT).show()
                        } else {
                            onCountChange(nextCount)
                        }
                    }
                    .testTag("interactive_subha_big_button"),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "$subhaCount",
                        fontSize = 58.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                    Text(
                        text = "من $subhaTarget",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White.copy(alpha = 0.85f)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "اضغط للتسبيح",
                        fontSize = 12.sp,
                        color = GoldAccent
                    )
                }
            }
        }

        // Reset Controls
        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        onCountChange(0)
                        onLapsChange(0)
                    }
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = "تصفير السبحة", tint = MaterialTheme.colorScheme.primary)
                }
                Text("تصفير العداد والدورات", style = MaterialTheme.typography.labelMedium)
            }
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

// -------------------------------------------------------------------------------------------------
// Common: Enhanced Dhikr Card
// -------------------------------------------------------------------------------------------------
@Composable
fun EnhancedDhikrCard(
    dhikr: DhikrItem,
    count: Int,
    onIncrement: () -> Unit,
    onReset: () -> Unit
) {
    val context = LocalContext.current
    val isCompleted = count >= dhikr.targetCount
    val btnColor by animateColorAsState(
        targetValue = if (isCompleted) CorrectGreen else EmeraldPrimary,
        label = "btnColor"
    )

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isCompleted) CorrectGreen.copy(alpha = 0.08f) else MaterialTheme.colorScheme.surfaceVariant
        ),
        border = BorderStroke(
            1.5.dp,
            if (isCompleted) CorrectGreen else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.35f)
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            if (isCompleted) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = CorrectGreen.copy(alpha = 0.15f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = CorrectGreen,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "اكتمل هذا الذكر بحمد الله وتوفيقه ✓",
                            color = CorrectGreen,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            // Header: Reference & Actions (Copy, Share, Reset)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Text(
                        text = dhikr.reference,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("الذكر", "${dhikr.text}\n[${dhikr.reference}]")
                            clipboard.setPrimaryClip(clip)
                            Toast.makeText(context, "تم نسخ الذكر إلى الحافظة", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "نسخ الذكر",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    IconButton(
                        onClick = {
                            val sendIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, "${dhikr.text}\n\nالفضل: ${dhikr.virtue}\n[${dhikr.reference}]")
                                type = "text/plain"
                            }
                            context.startActivity(Intent.createChooser(sendIntent, "مشاركة الذكر"))
                        },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "مشاركة الذكر",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    if (count > 0) {
                        IconButton(onClick = onReset, modifier = Modifier.size(32.dp)) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "إعادة ضبط العداد",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Dhikr text
            Text(
                text = dhikr.text,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                lineHeight = 32.sp,
                textAlign = TextAlign.Right,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth()
            )

            if (dhikr.virtue.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = EmeraldPrimary.copy(alpha = 0.08f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "✨ الفضل: ${dhikr.virtue}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 20.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // 🎙️ Listen to Sheikh Recitation for this Dhikr
            val playingId by com.example.audio.AzkarAudioPlayer.currentPlayingId.collectAsState()
            val isAudioPlaying by com.example.audio.AzkarAudioPlayer.isPlaying.collectAsState()
            val isAudioLoading by com.example.audio.AzkarAudioPlayer.isLoading.collectAsState()
            val isThisDhikrPlaying = playingId == dhikr.id && isAudioPlaying
            val isThisDhikrLoading = playingId == dhikr.id && isAudioLoading

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = if (isThisDhikrPlaying) GoldAccent.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surface,
                border = BorderStroke(1.dp, if (isThisDhikrPlaying) GoldAccent else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        com.example.audio.AzkarAudioPlayer.togglePlay(context, dhikr)
                    }
                    .testTag("dhikr_audio_btn_${dhikr.id}")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = if (isThisDhikrPlaying) GoldAccent else EmeraldPrimary,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                if (isThisDhikrLoading) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(16.dp),
                                        color = Color.White,
                                        strokeWidth = 2.dp
                                    )
                                } else {
                                    Icon(
                                        imageVector = if (isThisDhikrPlaying) Icons.Default.Stop else Icons.Default.PlayArrow,
                                        contentDescription = null,
                                        tint = if (isThisDhikrPlaying) Color.Black else Color.White,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        val currentSheikhName by com.example.audio.AzkarAudioPlayer.currentSheikhName.collectAsState()
                        Column {
                            Text(
                                text = if (isThisDhikrPlaying) "جارٍ الاستماع بصوت: $currentSheikhName 🔊" else "استمع بصوت كبار الشيوخ (العفاسي، الغامدي، المعيقلي) 🎙️",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                color = if (isThisDhikrPlaying) GoldAccent else MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = if (isThisDhikrPlaying) "تسجيل صوتي بشري نقي معتمد • بدون ذكاء اصطناعي" else "تسجيلات حقيقية نقية بصوت الشيخ مشاري العفاسي وكبار القراء",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    if (isThisDhikrPlaying) {
                        Text(
                            text = "🔊 بصوت الشيخ",
                            fontSize = 11.sp,
                            color = GoldAccent,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            val isTahiyyatDhikr = dhikr.text.contains("التحيات") || dhikr.text.contains("التشهد") || dhikr.id / 1000 == 22 || dhikr.id == 25005
            val isDuaAfterAdhan = dhikr.text.contains("الدعوة التامة") || dhikr.text.contains("الوسيلة والفضيلة") || dhikr.id == 15004
            if (isTahiyyatDhikr || isDuaAfterAdhan) {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surface,
                    border = BorderStroke(1.dp, GoldAccent.copy(alpha = 0.4f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Headphones,
                                contentDescription = null,
                                tint = GoldAccent,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = if (isDuaAfterAdhan) "أشهر الشيوخ لتلاوة دعاء ما بعد الأذان مباشرة:" else "أشهر الشيوخ لتلاوة التحيات لله بعد الصلاة:",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        val currentReciterId by com.example.audio.AzkarAudioPlayer.selectedSheikhId.collectAsState()
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            items(com.example.audio.AzkarAudioPlayer.sheikhOptions) { sheikh ->
                                val isSelected = currentReciterId == sheikh.id
                                FilterChip(
                                    selected = isSelected,
                                    onClick = {
                                        com.example.audio.AzkarAudioPlayer.setReciter(sheikh.id)
                                        com.example.audio.AzkarAudioPlayer.play(context, dhikr)
                                    },
                                    label = {
                                        Text(
                                            text = sheikh.name.replace("الشيخ ", ""),
                                            fontSize = 11.sp,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                        )
                                    },
                                    leadingIcon = if (isSelected && isThisDhikrPlaying) {
                                        {
                                            Icon(
                                                imageVector = Icons.Default.VolumeUp,
                                                contentDescription = null,
                                                modifier = Modifier.size(14.dp),
                                                tint = Color.White
                                            )
                                        }
                                    } else null,
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = EmeraldPrimary,
                                        selectedLabelColor = Color.White
                                    ),
                                    shape = RoundedCornerShape(8.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Bottom interactive tap counter
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "التكرار المطلوب: ${dhikr.targetCount} مرات",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = btnColor,
                    modifier = Modifier
                        .clickable { onIncrement() }
                        .testTag("dhikr_tap_counter_${dhikr.id}")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 18.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (isCompleted) {
                            Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("تم ✓", fontWeight = FontWeight.Bold, color = Color.White)
                        } else {
                            Text(
                                text = "$count / ${dhikr.targetCount}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AzkarAudioRecitationsView(
    viewModel: AppViewModel,
    modifier: Modifier = Modifier
) {
    val azkarAudioController = viewModel.azkarAudioController
    val audioState by azkarAudioController.state.collectAsState()
    var selectedReciterId by remember { mutableStateOf(AzkarAudioData.reciters.first().id) }
    val selectedReciter = AzkarAudioData.reciters.find { it.id == selectedReciterId } ?: AzkarAudioData.reciters.first()
    val availableTracks = AzkarAudioData.getTracksForReciter(selectedReciter.id)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 90.dp)
    ) {
        // Hero Card
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                border = androidx.compose.foundation.BorderStroke(1.dp, EmeraldPrimary.copy(alpha = 0.3f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = EmeraldPrimary.copy(alpha = 0.15f),
                            modifier = Modifier.size(44.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Headphones,
                                    contentDescription = null,
                                    tint = EmeraldPrimary,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        Column {
                            Text(
                                text = "أذكار وأدعية بأصوات كبار المشايخ",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = EmeraldPrimary
                            )
                            Text(
                                text = "استمع إلى أذكار الصباح والمساء والرقية الشرعية بأصوات خاشعة",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        // Active Player Card
        if (audioState.currentTrack != null) {
            item {
                val currentTrack = audioState.currentTrack!!
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, EmeraldPrimary),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = currentTrack.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = currentTrack.sheikhName,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = EmeraldPrimary,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = EmeraldPrimary.copy(alpha = 0.12f)
                            ) {
                                Text(
                                    text = currentTrack.category,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = EmeraldPrimary,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Progress Slider
                        val progressFraction = if (audioState.durationMs > 0) {
                            (audioState.currentPositionMs.toFloat() / audioState.durationMs.toFloat()).coerceIn(0f, 1f)
                        } else 0f

                        Slider(
                            value = progressFraction,
                            onValueChange = { frac ->
                                if (audioState.durationMs > 0) {
                                    val newPos = (frac * audioState.durationMs).toInt()
                                    azkarAudioController.seekTo(newPos)
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = AzkarAudioData.formatTime(audioState.currentPositionMs),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = AzkarAudioData.formatTime(audioState.durationMs),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Controls
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = {
                                    val newPos = (audioState.currentPositionMs - 10000).coerceAtLeast(0)
                                    azkarAudioController.seekTo(newPos)
                                }
                            ) {
                                Text("١٠-", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = EmeraldPrimary)
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Surface(
                                shape = CircleShape,
                                color = EmeraldPrimary,
                                modifier = Modifier
                                    .size(54.dp)
                                    .clickable { azkarAudioController.togglePlayPause() }
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = if (audioState.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                        contentDescription = if (audioState.isPlaying) "إيقاف مؤقت" else "تشغيل",
                                        tint = Color.White,
                                        modifier = Modifier.size(30.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            IconButton(
                                onClick = {
                                    val newPos = (audioState.currentPositionMs + 10000).coerceAtMost(audioState.durationMs)
                                    azkarAudioController.seekTo(newPos)
                                }
                            ) {
                                Text("١٠+", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = EmeraldPrimary)
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            IconButton(
                                onClick = { azkarAudioController.stop() },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Stop,
                                    contentDescription = "إيقاف",
                                    tint = Color(0xFFEF4444)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Sheikh Selector Header
        item {
            Text(
                text = "اختر الشيخ القارئ:",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        // Reciters LazyRow
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(AzkarAudioData.reciters) { reciter ->
                    val isSelected = reciter.id == selectedReciter.id
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedReciterId = reciter.id },
                        label = {
                            Text(
                                text = reciter.nameArabic,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 12.sp
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Headphones,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = if (isSelected) EmeraldPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    )
                }
            }
        }

        // Available Tracks Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "تسجيلات ${selectedReciter.nameArabic}",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = EmeraldPrimary
                )
                Text(
                    text = "${availableTracks.size} تسجيلات",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Track items
        items(availableTracks) { track ->
            val isCurrentTrack = audioState.currentTrack?.id == track.id
            val isThisPlaying = isCurrentTrack && audioState.isPlaying

            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isCurrentTrack) EmeraldPrimary.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surfaceVariant
                ),
                border = if (isCurrentTrack) androidx.compose.foundation.BorderStroke(1.5.dp, EmeraldPrimary) else null,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        azkarAudioController.playTrack(track)
                    }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = if (isThisPlaying) EmeraldPrimary else MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.size(42.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = if (isThisPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                    contentDescription = null,
                                    tint = if (isThisPlaying) Color.White else EmeraldPrimary,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }

                        Column {
                            Text(
                                text = track.title,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (isCurrentTrack) EmeraldPrimary else MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "${track.category} • ${track.durationEstimate}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (isCurrentTrack) EmeraldPrimary else MaterialTheme.colorScheme.surface,
                        modifier = Modifier.clickable {
                            azkarAudioController.playTrack(track)
                        }
                    ) {
                        Text(
                            text = if (isThisPlaying) "جاري الاستماع" else "تشغيل",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = if (isThisPlaying) Color.White else EmeraldPrimary,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }
    }
}
