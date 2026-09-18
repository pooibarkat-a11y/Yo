package com.example.ui.screens.prayer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.prayer.PrePrayerReminderHelper
import com.example.ui.components.SheikhAvatar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prayer.AdhanData
import com.example.prayer.Muezzin
import com.example.viewmodel.AppViewModel

@Composable
fun PrayerAdhanTab(
    isAutoPlayEnabled: Boolean,
    selectedMuezzin: Muezzin,
    enabledPrayers: Set<String>,
    isAdhanPlaying: Boolean,
    viewModel: AppViewModel,
    modifier: Modifier = Modifier
) {
    val allPrayers = listOf("الفجر", "الظهر", "العصر", "المغرب", "العشاء")
    val isDuaAfterAdhanEnabled by viewModel.isDuaAfterAdhanEnabled.collectAsState()
    val selectedDuaSheikhId by viewModel.selectedDuaSheikhId.collectAsState()
    val isPlayingDuaAfterAdhan by viewModel.isPlayingDuaAfterAdhan.collectAsState()

    val isPrePrayerEnabled by viewModel.isPrePrayerEnabled.collectAsState()
    val prePrayerMinutes by viewModel.prePrayerMinutes.collectAsState()
    val isPrePrayerVoiceEnabled by viewModel.isPrePrayerVoiceEnabled.collectAsState()
    val isSpeakingPrePrayer by viewModel.isSpeakingPrePrayer.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    var selectedCategoryFilter by remember { mutableStateOf("الكل") }

    val filteredMuezzins = remember(searchQuery, selectedCategoryFilter) {
        AdhanData.muezzins.filter { muezzin ->
            val matchesSearch = if (searchQuery.isBlank()) true else {
                muezzin.nameArabic.contains(searchQuery.trim(), ignoreCase = true) ||
                muezzin.mosque.contains(searchQuery.trim(), ignoreCase = true)
            }
            val matchesCategory = when (selectedCategoryFilter) {
                "الحرمين" -> muezzin.mosque.contains("الحرام") || muezzin.mosque.contains("النبوي") || muezzin.nameArabic.contains("مكه") || muezzin.nameArabic.contains("المدينة") || muezzin.nameArabic.contains("ملا") || muezzin.nameArabic.contains("رمل")
                "مصر والأزهر" -> muezzin.mosque.contains("مصر") || muezzin.mosque.contains("القاهرة") || muezzin.nameArabic.contains("عبد الباسط") || muezzin.nameArabic.contains("رفعت") || muezzin.nameArabic.contains("الحصري") || muezzin.nameArabic.contains("المنشاوي") || muezzin.nameArabic.contains("طوبار") || muezzin.nameArabic.contains("شعيشع") || muezzin.nameArabic.contains("نعينع") || muezzin.nameArabic.contains("البنا") || muezzin.nameArabic.contains("الطوخي")
                "الكويت والخليج" -> muezzin.mosque.contains("الكويت") || muezzin.mosque.contains("الإمارات") || muezzin.mosque.contains("دبي") || muezzin.mosque.contains("عجمان") || muezzin.mosque.contains("قطر") || muezzin.mosque.contains("عُمان") || muezzin.mosque.contains("عمان") || muezzin.mosque.contains("الرياض") || muezzin.mosque.contains("الراجحي") || muezzin.nameArabic.contains("العفاسي") || muezzin.nameArabic.contains("القطامي") || muezzin.nameArabic.contains("الدوسري")
                "الأقصى والشام" -> muezzin.mosque.contains("الأقصى") || muezzin.mosque.contains("القدس") || muezzin.mosque.contains("دمشق") || muezzin.mosque.contains("الشام") || muezzin.mosque.contains("سوريا") || muezzin.mosque.contains("فلسطين") || muezzin.mosque.contains("لبنان") || muezzin.mosque.contains("العراق") || muezzin.mosque.contains("بغداد") || muezzin.mosque.contains("الجزائر") || muezzin.mosque.contains("تونس") || muezzin.mosque.contains("المغرب")
                else -> true
            }
            matchesSearch && matchesCategory
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            // Master Background AutoPlay Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(Color(0xFF1E1B4B), Color(0xFF312E81))
                            )
                        )
                        .padding(18.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .background(Color(0xFF818CF8).copy(alpha = 0.2f), CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.NotificationsActive,
                                        contentDescription = null,
                                        tint = Color(0xFFA5B4FC),
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = "الأذان في الخلفية",
                                        color = Color.White,
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = if (isAutoPlayEnabled) "مفعل (يعمل والشاشة مقفلة)" else "متوقف مؤقتاً",
                                        color = if (isAutoPlayEnabled) Color(0xFF86EFAC) else Color(0xFFCBD5E1),
                                        fontSize = 12.sp
                                    )
                                }
                            }

                            Switch(
                                checked = isAutoPlayEnabled,
                                onCheckedChange = { viewModel.setAdhanAutoPlayEnabled(it) },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = Color(0xFF4F46E5)
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Surface(
                            color = Color.Black.copy(alpha = 0.25f),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "💡 يعمل الأذان التلقائي فور دخول وقت الصلاة عبر خدمة النظام المستمرة، ولا يلزم فتح التطبيق مطلقاً.",
                                color = Color(0xFFE0E7FF),
                                fontSize = 12.sp,
                                lineHeight = 18.sp,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                }
            }
        }

        item {
            // Select which prayers should trigger Adhan
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "الصلوات التي يرفع فيها الأذان:",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        allPrayers.forEach { prayer ->
                            val isSelected = prayer in enabledPrayers
                            FilterChip(
                                selected = isSelected,
                                onClick = { viewModel.toggleAdhanPrayer(prayer) },
                                label = { Text(prayer, fontSize = 12.sp, fontWeight = FontWeight.Bold) },
                                leadingIcon = if (isSelected) {
                                    { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(14.dp)) }
                                } else null,
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                                ),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }

        // Dedicated Dua after Adhan Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                border = BorderStroke(1.dp, Color(0xFFD97706).copy(alpha = 0.35f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .background(Color(0xFFD97706).copy(alpha = 0.15f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.VolumeUp,
                                    contentDescription = null,
                                    tint = Color(0xFFD97706),
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "دعاء ما بعد الأذان التلقائي",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Surface(
                                        color = Color(0xFFD97706).copy(alpha = 0.2f),
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Text(
                                            text = "سنة نبوية",
                                            color = Color(0xFFB45309),
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                                Text(
                                    text = "يصدح بالدعاء فور انتهاء الأذان مباشرة 🎙️",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Switch(
                            checked = isDuaAfterAdhanEnabled,
                            onCheckedChange = { viewModel.toggleDuaAfterAdhan(it) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Color(0xFFD97706)
                            )
                        )
                    }

                    AnimatedVisibility(visible = isDuaAfterAdhanEnabled) {
                        Column {
                            Spacer(modifier = Modifier.height(14.dp))

                            // Dua Card with full text
                            Surface(
                                color = Color(0xFFF59E0B).copy(alpha = 0.08f),
                                shape = RoundedCornerShape(14.dp),
                                border = BorderStroke(1.dp, Color(0xFFF59E0B).copy(alpha = 0.25f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Text(
                                        text = "«اللَّهُمَّ رَبَّ هَذِهِ الدَّعْوَةِ التَّامَّةِ، وَالصَّلاَةِ الْقَائِمَةِ، آتِ مُحَمَّدًا الْوَسِيلَةَ وَالْفَضِيلَةَ، وَابْعَثْهُ مَقَامًا مَحْمُودًا الَّذِي وَعَدْتَهُ»",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        lineHeight = 26.sp,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = null,
                                            tint = Color(0xFF16A34A),
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "«حلت له شفاعتي يوم القيامة» • صحيح البخاري",
                                            fontSize = 11.sp,
                                            color = Color(0xFF16A34A),
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            Text(
                                text = "اختر صوت الشيخ للدعاء بعد الأذان:",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                items(AdhanData.duaAfterAdhanSheikhs) { sheikh ->
                                    val isSelected = selectedDuaSheikhId == sheikh.id
                                    FilterChip(
                                        selected = isSelected,
                                        onClick = {
                                            viewModel.selectDuaSheikh(sheikh.id)
                                            viewModel.playDuaAfterAdhanSound(sheikh.id)
                                        },
                                        label = {
                                            Text(
                                                text = sheikh.nameArabic.replace("الشيخ ", ""),
                                                fontSize = 12.sp,
                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                            )
                                        },
                                        leadingIcon = if (isSelected && isPlayingDuaAfterAdhan) {
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
                                            selectedContainerColor = Color(0xFFD97706),
                                            selectedLabelColor = Color.White
                                        )
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Direct Preview and Listen Button
                            Button(
                                onClick = {
                                    if (isPlayingDuaAfterAdhan) {
                                        viewModel.stopAdhanAudio()
                                    } else {
                                        viewModel.playDuaAfterAdhanSound(selectedDuaSheikhId)
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isPlayingDuaAfterAdhan) Color(0xFFEF4444) else Color(0xFFD97706)
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(
                                    imageVector = if (isPlayingDuaAfterAdhan) Icons.Default.Stop else Icons.Default.PlayArrow,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                val currentSheikh = AdhanData.duaAfterAdhanSheikhs.find { it.id == selectedDuaSheikhId }
                                Text(
                                    text = if (isPlayingDuaAfterAdhan) "إيقاف استماع الدعاء" else "تجربة واستماع للدعاء بصوت ${currentSheikh?.nameArabic ?: ""}",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }

        item {
            // Pre-Prayer Announcement Card (اقتربت موعد الصلاة)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                border = BorderStroke(1.dp, Color(0xFF0D9488).copy(alpha = 0.35f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .background(Color(0xFF0D9488).copy(alpha = 0.15f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Campaign,
                                    contentDescription = null,
                                    tint = Color(0xFF0D9488),
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "تنبيه اقتراب موعد الصلاة",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Surface(
                                        color = Color(0xFF0D9488).copy(alpha = 0.2f),
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Text(
                                            text = "استعداد وخشوع",
                                            color = Color(0xFF0F766E),
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                                Text(
                                    text = "تذكير إيماني ونطق صوتي قبل الأذان للاستعداد والوضوء 🌸",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Switch(
                            checked = isPrePrayerEnabled,
                            onCheckedChange = { viewModel.setPrePrayerEnabled(it) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Color(0xFF0D9488)
                            )
                        )
                    }

                    AnimatedVisibility(visible = isPrePrayerEnabled) {
                        Column {
                            Spacer(modifier = Modifier.height(14.dp))

                            Text(
                                text = "توقيت التنبيه قبل دخول الوقت:",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                listOf(5, 10, 15, 20).forEach { mins ->
                                    val isSelected = prePrayerMinutes == mins
                                    FilterChip(
                                        selected = isSelected,
                                        onClick = { viewModel.setPrePrayerMinutes(mins) },
                                        label = { Text("$mins دقيقة", fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = Color(0xFF0D9488),
                                            selectedLabelColor = Color.White
                                        ),
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "النداء الصوتي المسموع",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "«اقترب موعد صلاة.. استعد للوقوف بين يدي الله وتوضأ»",
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                Switch(
                                    checked = isPrePrayerVoiceEnabled,
                                    onCheckedChange = { viewModel.setPrePrayerVoiceEnabled(it) },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = Color.White,
                                        checkedTrackColor = Color(0xFF0D9488)
                                    )
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Surface(
                                color = Color(0xFF0D9488).copy(alpha = 0.08f),
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(1.dp, Color(0xFF0D9488).copy(alpha = 0.2f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(
                                        text = "نموذج من الكلمات الطيبة المصاحبة للتنبيه:",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF0F766E)
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = PrePrayerReminderHelper.getSpiritualWords("الفجر"),
                                        fontSize = 12.sp,
                                        lineHeight = 18.sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Button(
                                onClick = {
                                    if (isSpeakingPrePrayer) {
                                        viewModel.stopPrePrayerSpeech()
                                    } else {
                                        viewModel.previewPrePrayerSpeech("الفجر")
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isSpeakingPrePrayer) Color(0xFFEF4444) else Color(0xFF0D9488)
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(
                                    imageVector = if (isSpeakingPrePrayer) Icons.Default.Stop else Icons.Default.VolumeUp,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (isSpeakingPrePrayer) "إيقاف التنبيه الصوتي" else "تجربة سماع التنبيه الصوتي الآن 🔊",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }

        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "أصوات المؤذنين",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = "أصوات الخشوع (${AdhanData.muezzins.size}) 🎙️",
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Text(
                            text = "أصوات عذبة وهادئة خاشعة لأشهر مؤذني العالم الإسلامي",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    if (isAdhanPlaying) {
                        Button(
                            onClick = { viewModel.stopAdhanAudio() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.Stop, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("إيقاف الصوت", fontSize = 12.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Search field
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = {
                        Text(
                            text = "ابحث بالاسم أو المسجد أو الدولة...",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "بحث",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "مسح",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Category chips
                val categories = listOf("الكل", "الحرمين", "مصر والأزهر", "الكويت والخليج", "الأقصى والشام")
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(categories) { cat ->
                        val isCatSelected = selectedCategoryFilter == cat
                        val label = when (cat) {
                            "الكل" -> "الكل (${AdhanData.muezzins.size})"
                            else -> cat
                        }
                        FilterChip(
                            selected = isCatSelected,
                            onClick = { selectedCategoryFilter = cat },
                            label = { Text(text = label, fontSize = 12.sp, fontWeight = if (isCatSelected) FontWeight.Bold else FontWeight.Normal) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }
                }
            }
        }

        if (filteredMuezzins.isEmpty()) {
            item {
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "🔍 لم يتم العثور على مؤذن يطابق «$searchQuery»",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                searchQuery = ""
                                selectedCategoryFilter = "الكل"
                            },
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("عرض كافة الـ 151 مؤذناً", fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        items(filteredMuezzins, key = { it.id }) { muezzin ->
            val isSelected = selectedMuezzin.id == muezzin.id
            val isPreviewPlaying = isAdhanPlaying && isSelected

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        viewModel.selectMuezzin(muezzin)
                    },
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(
                    width = if (isSelected) 2.dp else 1.dp,
                    color = if (isSelected) MaterialTheme.colorScheme.primary else Color.White.copy(alpha = 0.08f)
                ),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected)
                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
                    else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                        SheikhAvatar(
                            nameArabic = muezzin.nameArabic,
                            imageUrl = muezzin.imageUrl,
                            sheikhId = muezzin.id,
                            isSelected = isSelected,
                            size = 48.dp
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = muezzin.nameArabic,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                if (isSelected) {
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Surface(
                                        color = MaterialTheme.colorScheme.primary,
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Text(
                                            text = "المعتمد للأذان ✓",
                                            color = MaterialTheme.colorScheme.onPrimary,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                            }
                            Text(
                                text = muezzin.mosque,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    // Preview Button: plays this muezzin's adhan directly!
                    IconButton(
                        onClick = {
                            if (isPreviewPlaying) {
                                viewModel.stopAdhanAudio()
                            } else {
                                viewModel.selectMuezzin(muezzin)
                                viewModel.playAdhan("الأذان", muezzin)
                            }
                        },
                        modifier = Modifier
                            .size(38.dp)
                            .background(
                                if (isPreviewPlaying) Color(0xFFEF4444).copy(alpha = 0.2f)
                                else MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                                CircleShape
                            )
                    ) {
                        Icon(
                            imageVector = if (isPreviewPlaying) Icons.Default.Stop else Icons.Default.PlayArrow,
                            contentDescription = if (isPreviewPlaying) "إيقاف" else "استماع",
                            tint = if (isPreviewPlaying) Color(0xFFEF4444) else MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
