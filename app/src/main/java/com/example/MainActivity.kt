package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mosque
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.MiniAudioPlayer
import com.example.ui.components.TarteelBottomNav
import com.example.ui.screens.AchievementsScreen
import com.example.ui.screens.AllahNamesScreen
import com.example.ui.screens.AssistantScreen
import com.example.ui.screens.AudioPlayerScreen
import com.example.ui.screens.AzkarScreen
import com.example.ui.screens.DailyTasksScreen
import com.example.ui.screens.HifzScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.IslamicAcademyScreen
import com.example.ui.screens.IslamicGalleryScreen
import com.example.ui.screens.KhatmahScreen
import com.example.ui.screens.LoginScreen
import com.example.ui.screens.PrayerScreen
import com.example.ui.screens.QuranReaderScreen
import com.example.ui.screens.RamadanScreen
import com.example.ui.screens.SettingsScreen
import androidx.activity.result.contract.ActivityResultContracts
import android.os.Build
import android.content.pm.PackageManager
import com.example.notification.SmartNotificationManager
import com.example.ui.screens.SmartNotificationsScreen
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.TarteelTheme
import com.example.viewmodel.AppScreen
import com.example.viewmodel.AppViewModel

class MainActivity : ComponentActivity() {

    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            SmartNotificationManager.scheduleAllEnabledNotifications(this)
            com.example.prayer.AdhanAlarmReceiver.scheduleAllPrayers(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        com.example.data.QuranOfflineProvider.init(this)
        com.example.audio.WelcomeAudioPlayer.playSalawat(this)

        // Initialize smart notifications and background adhan alarms
        SmartNotificationManager.init(this)
        com.example.prayer.AdhanAlarmReceiver.scheduleAllPrayers(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            } else {
                SmartNotificationManager.scheduleAllEnabledNotifications(this)
            }
        } else {
            SmartNotificationManager.scheduleAllEnabledNotifications(this)
        }

        enableEdgeToEdge()
        setContent {
            val appViewModel: AppViewModel = viewModel()
            val themeMode by appViewModel.themeMode.collectAsState()
            val currentScreen by appViewModel.currentScreen.collectAsState()
            val audioState by appViewModel.audioPlayer.state.collectAsState()
            val isAdhanPlaying by appViewModel.isAdhanPlaying.collectAsState()
            val currentAdhanPrayer by appViewModel.currentAdhanPrayer.collectAsState()
            val selectedMuezzin by appViewModel.selectedMuezzin.collectAsState()

            TarteelTheme(themeMode = themeMode) {
                // Ensure natural RTL Arabic layout direction
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    BackHandler(enabled = currentScreen != AppScreen.HOME) {
                        appViewModel.navigateTo(AppScreen.HOME)
                    }

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = {
                            if (currentScreen != AppScreen.LOGIN) {
                                Column {
                                    MiniAudioPlayer(
                                        state = audioState,
                                        onTogglePlay = { appViewModel.audioPlayer.togglePlayPause() },
                                        onNext = { appViewModel.audioPlayer.nextAyah() },
                                        onClickPlayer = { appViewModel.navigateTo(AppScreen.AUDIO_PLAYER) }
                                    )
                                    TarteelBottomNav(
                                        currentScreen = currentScreen,
                                        onNavigate = { screen -> appViewModel.navigateTo(screen) }
                                    )
                                }
                            }
                        }
                    ) { innerPadding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                        ) {
                            // Islamic decorative background wallpaper
                            Image(
                                painter = painterResource(id = R.drawable.img_islamic_bg),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop,
                                alpha = 0.08f
                            )

                            when (currentScreen) {
                                AppScreen.HOME -> HomeScreen(viewModel = appViewModel)
                                AppScreen.LOGIN -> LoginScreen(
                                    viewModel = appViewModel,
                                    onDismiss = { appViewModel.navigateTo(AppScreen.HOME) }
                                )
                                AppScreen.QURAN_READER -> QuranReaderScreen(viewModel = appViewModel)
                                AppScreen.AUDIO_PLAYER -> AudioPlayerScreen(viewModel = appViewModel)
                                AppScreen.HIFZ_TEST -> HifzScreen(viewModel = appViewModel)
                                AppScreen.KHATMAH_PLAN -> KhatmahScreen(viewModel = appViewModel)
                                AppScreen.AZKAR -> AzkarScreen(viewModel = appViewModel, initialTab = 0)
                                AppScreen.HADITH -> AzkarScreen(viewModel = appViewModel, initialTab = 1)
                                AppScreen.GOLDEN_ADVICE -> AzkarScreen(viewModel = appViewModel, initialTab = 2)
                                AppScreen.ALLAH_NAMES -> AllahNamesScreen(viewModel = appViewModel)
                                AppScreen.DAILY_TASKS -> DailyTasksScreen(viewModel = appViewModel)
                                AppScreen.RAMADAN -> RamadanScreen(viewModel = appViewModel)
                                AppScreen.ISLAMIC_GALLERY -> IslamicGalleryScreen(viewModel = appViewModel)
                                AppScreen.PRAYER_TIMES -> PrayerScreen(viewModel = appViewModel)
                                AppScreen.AI_ASSISTANT -> AssistantScreen(viewModel = appViewModel)
                                AppScreen.ACHIEVEMENTS -> AchievementsScreen(viewModel = appViewModel)
                                AppScreen.SETTINGS -> SettingsScreen(viewModel = appViewModel)
                                AppScreen.ISLAMIC_ACADEMY -> IslamicAcademyScreen(
                                    viewModel = appViewModel,
                                    initialTab = appViewModel.initialAcademyTab.collectAsState().value
                                )
                                AppScreen.SMART_NOTIFICATIONS -> SmartNotificationsScreen(viewModel = appViewModel)
                            }

                            // Floating Active Adhan Alert Banner
                            AnimatedVisibility(
                                visible = isAdhanPlaying,
                                enter = slideInVertically(initialOffsetY = { -it }),
                                exit = slideOutVertically(targetOffsetY = { -it }),
                                modifier = Modifier
                                    .align(Alignment.TopCenter)
                                    .padding(horizontal = 16.dp, vertical = 10.dp)
                            ) {
                                Card(
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surface
                                    ),
                                    border = androidx.compose.foundation.BorderStroke(1.5.dp, GoldAccent),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { appViewModel.navigateTo(AppScreen.PRAYER_TIMES) }
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
                                            Icon(
                                                imageVector = Icons.Default.Mosque,
                                                contentDescription = null,
                                                tint = EmeraldPrimary,
                                                modifier = Modifier.size(28.dp)
                                            )
                                            Spacer(modifier = Modifier.width(10.dp))
                                            Column {
                                                Text(
                                                    text = "حيّ على الصلاة • أذان ${currentAdhanPrayer ?: "الصلاة"}",
                                                    style = MaterialTheme.typography.titleSmall,
                                                    fontWeight = FontWeight.Bold,
                                                    color = EmeraldPrimary
                                                )
                                                Text(
                                                    text = "${selectedMuezzin.nameArabic} • ${selectedMuezzin.mosque}",
                                                    style = MaterialTheme.typography.bodySmall,
                                                    fontSize = 11.sp,
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                                )
                                            }
                                        }

                                        FilledTonalButton(
                                            onClick = { appViewModel.stopAdhan() },
                                            shape = RoundedCornerShape(10.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Stop,
                                                contentDescription = "إيقاف",
                                                modifier = Modifier.size(16.dp)
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("إيقاف", fontSize = 12.sp)
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
}
