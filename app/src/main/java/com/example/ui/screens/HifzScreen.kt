package com.example.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Hearing
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.core.content.ContextCompat
import com.example.data.QuranData
import com.example.data.local.MistakeAyahEntity
import com.example.model.Surah
import com.example.model.WordDiff
import com.example.ui.theme.CorrectGreen
import com.example.ui.theme.EmeraldDark
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.GoldAccent
import com.example.viewmodel.AppViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HifzScreen(
    viewModel: AppViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val hifzSurah by viewModel.hifzSurah.collectAsState()
    val hifzAyahNum by viewModel.hifzAyahNumber.collectAsState()
    val testMode by viewModel.hifzTestMode.collectAsState()
    val isRecording by viewModel.isRecordingVoice.collectAsState()
    val spokenText by viewModel.spokenText.collectAsState()
    val testResult by viewModel.testResult.collectAsState()
    val mistakes by viewModel.mistakeAyahs.collectAsState()

    var showSurahPicker by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableStateOf(0) } // 0: Test, 1: Spaced Repetition Review
    var manualTextRecitation by remember { mutableStateOf("") }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.startVoiceRecording()
        } else {
            Toast.makeText(context, "يلزم إذن الميكروفون للاستماع للتلاوة", Toast.LENGTH_LONG).show()
        }
    }

    val surahAyahs = QuranData.getAyahsForSurah(hifzSurah.id)
    val targetAyah = surahAyahs.find { it.ayahNumber == hifzAyahNum } ?: surahAyahs.first()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Tab Row: Test Mode vs SRS Revision
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Mic, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("اختبار التسميع", fontWeight = FontWeight.Bold)
                    }
                }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Psychology, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("المراجعة الذكية (${mistakes.size})", fontWeight = FontWeight.Bold)
                    }
                }
            )
        }

        if (selectedTab == 0) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(10.dp))

                    // Surah & Ayah Chooser Bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier
                                .clickable { showSurahPicker = true }
                                .testTag("hifz_surah_picker_button")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "سورة ${hifzSurah.nameArabic}",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("▼", fontSize = 10.sp)
                            }
                        }

                        // Ayah Stepper
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "الآية: ",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            IconButton(
                                onClick = {
                                    if (hifzAyahNum > 1) viewModel.setHifzSurah(hifzSurah, hifzAyahNum - 1)
                                }
                            ) {
                                Text("◀", fontWeight = FontWeight.Bold)
                            }
                            Text(
                                text = "$hifzAyahNum",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = GoldAccent
                            )
                            IconButton(
                                onClick = {
                                    if (hifzAyahNum < hifzSurah.versesCount) viewModel.setHifzSurah(hifzSurah, hifzAyahNum + 1)
                                }
                            ) {
                                Text("▶", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                // Testing Mode Chips
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("تسميع بصوتي", "أكمل الآية", "تسميع خفي").forEach { mode ->
                            FilterChip(
                                selected = testMode == mode,
                                onClick = { viewModel.setHifzTestMode(mode) },
                                label = { Text(mode) }
                            )
                        }
                    }
                }

                // Question Prompt Card according to Mode
                item {
                    Card(
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(18.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            when (testMode) {
                                "أكمل الآية" -> {
                                    val words = targetAyah.textUthmani.split(" ")
                                    val hintWords = words.take(3).joinToString(" ")
                                    Text(
                                        text = "أكمل الآية الكريمة بعد:",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "﴿ $hintWords ... ﴾",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = GoldAccent,
                                        textAlign = TextAlign.Center
                                    )
                                }
                                "تسميع خفي" -> {
                                    Text(
                                        text = "اقرأ الآية كاملة غيباً من ذاكرتك",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "سورة ${hifzSurah.nameArabic} - الآية $hifzAyahNum",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    OutlinedButton(
                                        onClick = { viewModel.audioPlayer.playAyah(hifzSurah, hifzAyahNum) },
                                        shape = RoundedCornerShape(10.dp)
                                    ) {
                                        Icon(Icons.Default.VolumeUp, contentDescription = null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("استمع لتلميح صوتي")
                                    }
                                }
                                else -> {
                                    Text(
                                        text = "سمّع الآية الكريمة بصوتك:",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = targetAyah.textUthmani,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Medium,
                                        textAlign = TextAlign.Center,
                                        lineHeight = 32.sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                }

                // Microphone Recitation Button & Status
                item {
                    val micBgColor by animateColorAsState(
                        targetValue = if (isRecording) ErrorRed else EmeraldPrimary,
                        label = "micColor"
                    )

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(90.dp)
                                .clip(CircleShape)
                                .background(micBgColor)
                                .clickable {
                                    if (isRecording) {
                                        viewModel.stopVoiceRecording()
                                    } else {
                                        val hasPermission = ContextCompat.checkSelfPermission(
                                            context,
                                            Manifest.permission.RECORD_AUDIO
                                        ) == PackageManager.PERMISSION_GRANTED

                                        if (hasPermission) {
                                            viewModel.startVoiceRecording()
                                        } else {
                                            permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                                        }
                                    }
                                }
                                .testTag("hifz_mic_button"),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (isRecording) Icons.Default.Stop else Icons.Default.Mic,
                                contentDescription = if (isRecording) "إيقاف التسجيل" else "ابدأ التسميع بصوتك",
                                tint = Color.White,
                                modifier = Modifier.size(46.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = if (isRecording) "جارِ الاستماع لتلاوتك... (اضغط للإيقاف والتقييم)"
                            else "اضغط على الميكروفون وابدأ التلاوة بصوتك",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            color = if (isRecording) ErrorRed else MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        // Manual recitation fallback input
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = manualTextRecitation,
                                onValueChange = { manualTextRecitation = it },
                                placeholder = { Text("أو اكتب تلاوتك هنا للتقييم...") },
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                onClick = {
                                    if (manualTextRecitation.isNotBlank()) {
                                        viewModel.evaluateRecitation(manualTextRecitation)
                                    }
                                }
                            ) {
                                Text("قيّم")
                            }
                        }
                    }
                }

                // Live Spoken Transcription Display
                if (spokenText.isNotBlank()) {
                    item {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = "ما تم التقاطه صوتياً:",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = spokenText,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }

                // Test Result & Word-by-Word Diff Card
                testResult?.let { res ->
                    item {
                        Card(
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (res.accuracyPercentage >= 85) EmeraldDark.copy(alpha = 0.2f)
                                else Color(0xFFEF4444).copy(alpha = 0.15f)
                            ),
                            border = BorderStroke(
                                1.5.dp,
                                if (res.accuracyPercentage >= 85) CorrectGreen else ErrorRed
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = if (res.accuracyPercentage >= 85) Icons.Default.CheckCircle else Icons.Default.Warning,
                                            contentDescription = null,
                                            tint = if (res.accuracyPercentage >= 85) CorrectGreen else ErrorRed
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = if (res.accuracyPercentage >= 90) "ممتاز! حفظ متقن ومبارك"
                                            else if (res.accuracyPercentage >= 75) "جيد جداً، مع بعض الملاحظات"
                                            else "تحتاج لمزيد من التكرار والتثبيت",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }

                                    Text(
                                        text = "${res.accuracyPercentage}%",
                                        style = MaterialTheme.typography.headlineSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = if (res.accuracyPercentage >= 85) CorrectGreen else ErrorRed
                                    )
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                Text(
                                    text = "التحليل التفصيلي للكلمات (كلمة بكلمة):",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                // FlowRow for Word diffs
                                FlowRow(
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    verticalArrangement = Arrangement.spacedBy(6.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    res.wordDiffs.forEach { diff ->
                                        WordDiffBadge(diff)
                                    }
                                }

                                Spacer(modifier = Modifier.height(14.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "الكلمات الصحيحة: ${res.correctWords} من ${res.totalWords}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = CorrectGreen
                                    )
                                    if (res.accuracyPercentage < 85) {
                                        Text(
                                            text = "تمت إضافتها للمراجعة الذكية ✓",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = GoldAccent
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
        } else {
            // Spaced Repetition (SRS) Review List
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "الآيات التي تحتاج إلى تثبيت ومراجعة",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "نظام التكرار المتباعد يذكرك بالآيات ذات الأخطاء السابقة لتثبيتها في صدرك",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                if (mistakes.isEmpty()) {
                    item {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 20.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = CorrectGreen,
                                    modifier = Modifier.size(48.dp)
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "ما شاء الله! لا توجد آيات في قائمة الأخطاء حالياً",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "ابدأ اختبار تسميع في السور التي تحفظها لاكتشاف مواضع الضعف وتثبيتها.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                } else {
                    items(mistakes) { item ->
                        MistakeReviewCard(
                            mistake = item,
                            onMastered = { viewModel.reviewMistake(item, success = true) },
                            onNeedsPractice = { viewModel.reviewMistake(item, success = false) },
                            onListen = {
                                val s = QuranData.surahs.find { it.id == item.surahId } ?: QuranData.surahs.first()
                                viewModel.audioPlayer.playAyah(s, item.ayahNumber)
                            }
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
        }
    }

    // Surah Picker Dialog
    if (showSurahPicker) {
        SurahChooserDialog(
            currentSurah = hifzSurah,
            onSurahSelected = { s ->
                viewModel.setHifzSurah(s, 1)
                showSurahPicker = false
            },
            onDismiss = { showSurahPicker = false }
        )
    }
}

@Composable
fun WordDiffBadge(diff: WordDiff) {
    val bgColor = when {
        diff.isCorrect -> CorrectGreen.copy(alpha = 0.2f)
        diff.isMissing -> Color(0xFFEF4444).copy(alpha = 0.2f)
        else -> Color(0xFFF97316).copy(alpha = 0.2f)
    }

    val textColor = when {
        diff.isCorrect -> CorrectGreen
        diff.isMissing -> ErrorRed
        else -> Color(0xFFF97316)
    }

    Surface(
        shape = RoundedCornerShape(8.dp),
        color = bgColor,
        border = BorderStroke(1.dp, textColor.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (diff.isMissing) "${diff.originalWord} [منسية]" else diff.originalWord,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
        }
    }
}

@Composable
fun MistakeReviewCard(
    mistake: MistakeAyahEntity,
    onMastered: () -> Unit,
    onNeedsPractice: () -> Unit,
    onListen: () -> Unit
) {
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
                Text(
                    text = "سورة ${mistake.surahName} • آية ${mistake.ayahNumber}",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                IconButton(onClick = onListen, modifier = Modifier.size(32.dp)) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "استمع للآية",
                        tint = GoldAccent,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = mistake.ayahText,
                style = MaterialTheme.typography.bodyMedium,
                lineHeight = 26.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onNeedsPractice) {
                    Text("أحتاج مراجعة", color = ErrorRed)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = onMastered,
                    colors = ButtonDefaults.buttonColors(containerColor = CorrectGreen),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("أتقنتها ✓", color = Color.White)
                }
            }
        }
    }
}
