package com.example.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ai.ChatMessage
import com.example.ai.MessageSender
import com.example.ai.QuranAiService
import com.example.audio.QuranAudioDownloader
import com.example.audio.QuranAudioPlayer
import com.example.data.AzkarAudioController
import com.example.data.AzkarAudioData
import com.example.data.AzkarAudioTrack
import com.example.data.AzkarData
import com.example.data.QuranData
import com.example.data.QuranOfflineProvider
import com.example.data.local.AppDatabase
import com.example.data.local.AppRepository
import com.example.data.local.BookmarkEntity
import com.example.data.local.HifzHistoryEntity
import com.example.data.local.KhatmahPlanEntity
import com.example.data.local.MistakeAyahEntity
import com.example.data.local.UserProgressEntity
import com.example.model.AppThemeMode
import com.example.model.Ayah
import com.example.model.HifzTestResult
import com.example.model.MushafDisplayMode
import com.example.model.MushafPaperColor
import com.example.model.QuranTab
import com.example.model.Reciter
import com.example.model.Surah
import com.example.data.DailyTasksData
import com.example.data.QuranApiService
import com.example.data.QuranOfflineSurahs
import com.example.data.QuranPagesManager

import com.example.prayer.AdhanAudioController
import com.example.prayer.AdhanData
import com.example.prayer.AdhanAlarmReceiver
import com.example.prayer.LocationHelper
import com.example.prayer.Muezzin
import com.example.prayer.JuristicMethod
import com.example.prayer.PrayerCalculator
import com.example.prayer.PrayerCity
import com.example.prayer.PrayerMethod
import com.example.prayer.PrayerOffsets
import com.example.prayer.PrayerTimes
import com.example.prayer.PrePrayerReminderHelper
import com.example.speech.QuranSpeechComparator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.withContext
import java.util.Calendar
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale
import com.example.auth.GoogleAuthManager
import com.example.audio.DeveloperDuaRecorder
import com.example.audio.WelcomeAudioPlayer
import com.example.notification.SmartNotificationManager

enum class AppScreen {
    HOME,
    LOGIN,
    QURAN_READER,
    AUDIO_PLAYER,
    HIFZ_TEST,
    KHATMAH_PLAN,
    AZKAR,
    HADITH,
    GOLDEN_ADVICE,
    ALLAH_NAMES,
    DAILY_TASKS,
    RAMADAN,
    ISLAMIC_GALLERY,
    PRAYER_TIMES,
    AI_ASSISTANT,
    ACHIEVEMENTS,
    SETTINGS,
    ISLAMIC_ACADEMY,
    SMART_NOTIFICATIONS
}

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    val repository = AppRepository(db.appDao())

    // Google Auth & Cloud Sync
    val googleAuthManager = GoogleAuthManager(application)

    // Developer Honor Voice Recorder
    val developerDuaRecorder = DeveloperDuaRecorder(application)

    // Audio Player
    val audioPlayer = QuranAudioPlayer(application, viewModelScope)

    // Navigation
    private val _currentScreen = MutableStateFlow(
        if (googleAuthManager.userProfile.value.isSignedIn) AppScreen.HOME else AppScreen.LOGIN
    )
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    fun navigateToLogin() {
        navigateTo(AppScreen.LOGIN)
    }

    private val _initialAcademyTab = MutableStateFlow(com.example.ui.screens.AcademyTab.PROPHETS)
    val initialAcademyTab: StateFlow<com.example.ui.screens.AcademyTab> = _initialAcademyTab.asStateFlow()

    fun navigateToAcademy(tab: com.example.ui.screens.AcademyTab = com.example.ui.screens.AcademyTab.PROPHETS) {
        _initialAcademyTab.value = tab
        navigateTo(AppScreen.ISLAMIC_ACADEMY)
    }

    // Quran Reader State
    private val _selectedSurah = MutableStateFlow(QuranData.surahs.first())
    val selectedSurah: StateFlow<Surah> = _selectedSurah.asStateFlow()

    private val _currentAyahs = MutableStateFlow(QuranOfflineSurahs.offlineSurahs[1] ?: QuranData.getAyahsForSurah(1))
    val currentAyahs: StateFlow<List<Ayah>> = _currentAyahs.asStateFlow()

    // Daily Worship Tasks State (100% Persistent across app exit and screen changes)
    private val _completedTaskIds = MutableStateFlow<Set<String>>(DailyTasksData.getCompletedTaskIds(application))
    val completedTaskIds: StateFlow<Set<String>> = _completedTaskIds.asStateFlow()

    fun toggleDailyTask(taskId: String) {
        val current = _completedTaskIds.value.toMutableSet()
        val willComplete = !current.contains(taskId)
        if (willComplete) {
            current.add(taskId)
        } else {
            current.remove(taskId)
        }
        _completedTaskIds.value = current
        DailyTasksData.setTaskCompleted(getApplication(), taskId, willComplete)
    }

    fun resetDailyTasks() {
        _completedTaskIds.value = emptySet()
        DailyTasksData.clearAllCompletedTasks(getApplication())
    }

    private val _targetAyahNumber = MutableStateFlow(1)
    val targetAyahNumber: StateFlow<Int> = _targetAyahNumber.asStateFlow()

    private val _quranTab = MutableStateFlow(QuranTab.INDEX)
    val quranTab: StateFlow<QuranTab> = _quranTab.asStateFlow()

    private val _mushafDisplayMode = MutableStateFlow(MushafDisplayMode.PHYSICAL_PAGES)
    val mushafDisplayMode: StateFlow<MushafDisplayMode> = _mushafDisplayMode.asStateFlow()

    private val _currentMushafPage = MutableStateFlow(1)
    val currentMushafPage: StateFlow<Int> = _currentMushafPage.asStateFlow()

    private val _isMushafImmersive = MutableStateFlow(false)
    val isMushafImmersive: StateFlow<Boolean> = _isMushafImmersive.asStateFlow()

    private val _bookmarkedPages = MutableStateFlow<Set<Int>>(emptySet())
    val bookmarkedPages: StateFlow<Set<Int>> = _bookmarkedPages.asStateFlow()

    fun toggleMushafImmersive() {
        _isMushafImmersive.value = !_isMushafImmersive.value
    }

    fun togglePageBookmark(page: Int) {
        val current = _bookmarkedPages.value
        if (current.contains(page)) {
            _bookmarkedPages.value = current - page
        } else {
            _bookmarkedPages.value = current + page
        }
    }

    private val _mushafPaperColor = MutableStateFlow(MushafPaperColor.PARCHMENT)
    val mushafPaperColor: StateFlow<MushafPaperColor> = _mushafPaperColor.asStateFlow()

    private val _isLoadingSurahOnline = MutableStateFlow(false)
    val isLoadingSurahOnline: StateFlow<Boolean> = _isLoadingSurahOnline.asStateFlow()

    private val _activeTafsirAyah = MutableStateFlow<Ayah?>(null)
    val activeTafsirAyah: StateFlow<Ayah?> = _activeTafsirAyah.asStateFlow()

    private val _fontSizeSp = MutableStateFlow(24)
    val fontSizeSp: StateFlow<Int> = _fontSizeSp.asStateFlow()

    private val _themeMode = MutableStateFlow(AppThemeMode.DARK)
    val themeMode: StateFlow<AppThemeMode> = _themeMode.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchResults = MutableStateFlow<List<Ayah>>(emptyList())
    val searchResults: StateFlow<List<Ayah>> = _searchResults.asStateFlow()

    // Flow from Room
    val bookmarks = repository.bookmarks.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val favorites = repository.favorites.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val allNotes = repository.allNotes.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val khatmahs = repository.khatmahs.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val activeKhatmah = repository.activeKhatmah.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
    val mistakeAyahs = repository.mistakeAyahs.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val userProgress = repository.userProgress.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserProgressEntity())
    val hifzHistory = repository.hifzHistory.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Hifz Test State
    private val _hifzSurah = MutableStateFlow(QuranData.surahs.first())
    val hifzSurah: StateFlow<Surah> = _hifzSurah.asStateFlow()

    private val _hifzAyahNumber = MutableStateFlow(1)
    val hifzAyahNumber: StateFlow<Int> = _hifzAyahNumber.asStateFlow()

    private val _hifzTestMode = MutableStateFlow("تسميع بصوتي") // "أكمل الآية", "تسميع بصوتي", "تسميع خفي"
    val hifzTestMode: StateFlow<String> = _hifzTestMode.asStateFlow()

    private val _isRecordingVoice = MutableStateFlow(false)
    val isRecordingVoice: StateFlow<Boolean> = _isRecordingVoice.asStateFlow()

    private val _spokenText = MutableStateFlow("")
    val spokenText: StateFlow<String> = _spokenText.asStateFlow()

    private val _testResult = MutableStateFlow<HifzTestResult?>(null)
    val testResult: StateFlow<HifzTestResult?> = _testResult.asStateFlow()

    // Azkar State
    private val _selectedAzkarCategory = MutableStateFlow(AzkarData.categories.first())
    val selectedAzkarCategory = _selectedAzkarCategory.asStateFlow()

    private val _azkarCounts = MutableStateFlow<Map<Int, Int>>(emptyMap())
    val azkarCounts = _azkarCounts.asStateFlow()

    // Prayer Times & Qibla
    private val _selectedCity = MutableStateFlow(PrayerCalculator.DEFAULT_CITY)
    val selectedCity: StateFlow<PrayerCity> = _selectedCity.asStateFlow()

    private val _prayerMethod = MutableStateFlow(PrayerCalculator.DEFAULT_CITY.recommendedMethod)
    val prayerMethod: StateFlow<PrayerMethod> = _prayerMethod.asStateFlow()

    private val _juristicMethod = MutableStateFlow(JuristicMethod.SHAFI_HANBALI_MALIKI)
    val juristicMethod: StateFlow<JuristicMethod> = _juristicMethod.asStateFlow()

    private val _prayerTimes = MutableStateFlow(PrayerCalculator.calculatePrayerTimes())
    val prayerTimes: StateFlow<PrayerTimes> = _prayerTimes.asStateFlow()

    private val _prayerOffsets = MutableStateFlow(PrayerOffsets())
    val prayerOffsets: StateFlow<PrayerOffsets> = _prayerOffsets.asStateFlow()

    private val _isLocating = MutableStateFlow(false)
    val isLocating: StateFlow<Boolean> = _isLocating.asStateFlow()

    // Audio Downloads
    val audioDownloader = QuranAudioDownloader
    val audioDownloadsProgress = QuranAudioDownloader.downloadProgress
    val activeAudioDownloads = QuranAudioDownloader.activeDownloads
    val downloadedSurahsSet = QuranAudioDownloader.downloadedSurahsSet

    // Azkar and Adhan Controllers
    val azkarAudioController = AzkarAudioController(application)
    val adhanAudioController = AdhanAudioController(application)

    private val _selectedMuezzin = MutableStateFlow<Muezzin>(AdhanData.selectedMuezzin)
    val selectedMuezzin: StateFlow<Muezzin> = _selectedMuezzin.asStateFlow()

    private val _isAdhanAutoPlayEnabled = MutableStateFlow(true)
    val isAdhanAutoPlayEnabled: StateFlow<Boolean> = _isAdhanAutoPlayEnabled.asStateFlow()

    private val _enabledAdhanPrayers = MutableStateFlow<Set<String>>(
        setOf("الفجر", "الظهر", "العصر", "المغرب", "العشاء")
    )
    val enabledAdhanPrayers: StateFlow<Set<String>> = _enabledAdhanPrayers.asStateFlow()

    private val _isAdhanPlaying = MutableStateFlow(false)
    val isAdhanPlaying: StateFlow<Boolean> = _isAdhanPlaying.asStateFlow()

    private val _currentAdhanPrayer = MutableStateFlow<String?>(null)
    val currentAdhanPrayer: StateFlow<String?> = _currentAdhanPrayer.asStateFlow()

    private val _activeAdhanAlert = MutableStateFlow<String?>(null)
    val activeAdhanAlert: StateFlow<String?> = _activeAdhanAlert.asStateFlow()

    private val _isDuaAfterAdhanEnabled = MutableStateFlow(AdhanData.isDuaAfterAdhanEnabled)
    val isDuaAfterAdhanEnabled: StateFlow<Boolean> = _isDuaAfterAdhanEnabled.asStateFlow()

    private val _selectedDuaSheikhId = MutableStateFlow(AdhanData.selectedDuaSheikhId)
    val selectedDuaSheikhId: StateFlow<String> = _selectedDuaSheikhId.asStateFlow()

    private val _isPlayingDuaAfterAdhan = MutableStateFlow(false)
    val isPlayingDuaAfterAdhan: StateFlow<Boolean> = _isPlayingDuaAfterAdhan.asStateFlow()

    // Pre-Prayer Reminder (اقتربت موعد الصلاة)
    private val _isPrePrayerEnabled = MutableStateFlow(true)
    val isPrePrayerEnabled: StateFlow<Boolean> = _isPrePrayerEnabled.asStateFlow()

    private val _prePrayerMinutes = MutableStateFlow(15)
    val prePrayerMinutes: StateFlow<Int> = _prePrayerMinutes.asStateFlow()

    private val _isPrePrayerVoiceEnabled = MutableStateFlow(true)
    val isPrePrayerVoiceEnabled: StateFlow<Boolean> = _isPrePrayerVoiceEnabled.asStateFlow()

    private val _isSpeakingPrePrayer = MutableStateFlow(false)
    val isSpeakingPrePrayer: StateFlow<Boolean> = _isSpeakingPrePrayer.asStateFlow()

    init {
        QuranOfflineProvider.init(application)
        QuranAudioDownloader.init(application)
        SmartNotificationManager.init(application)

        // Restore saved settings if any
        try {
            val savedCity = AdhanAlarmReceiver.getSavedCity(application)
            _selectedCity.value = savedCity
            _prayerMethod.value = AdhanAlarmReceiver.getSavedMethod(application)
            _juristicMethod.value = AdhanAlarmReceiver.getSavedJuristicMethod(application)
            _selectedMuezzin.value = AdhanAlarmReceiver.getSelectedMuezzin(application)
            _isAdhanAutoPlayEnabled.value = AdhanAlarmReceiver.isAdhanAutoPlayEnabled(application)
            _prayerOffsets.value = AdhanAlarmReceiver.getSavedOffsets(application)
            val enabledSet = listOf("الفجر", "الظهر", "العصر", "المغرب", "العشاء").filter {
                AdhanAlarmReceiver.isAdhanEnabledForPrayer(application, it)
            }.toSet()
            if (enabledSet.isNotEmpty()) {
                _enabledAdhanPrayers.value = enabledSet
            }
            _isPrePrayerEnabled.value = PrePrayerReminderHelper.isPrePrayerEnabled(application)
            _prePrayerMinutes.value = PrePrayerReminderHelper.getMinutesBefore(application)
            _isPrePrayerVoiceEnabled.value = PrePrayerReminderHelper.isVoiceEnabled(application)
        } catch (_: Exception) {}

        refreshPrayerTimes()
        syncBackgroundAdhanScheduler()

        // Background monitor for the 5 daily prayer adhans
        viewModelScope.launch(Dispatchers.Default) {
            var lastDayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
            val firedPrayersToday = mutableSetOf<String>()

            while (isActive) {
                delay(5000)
                val now = Calendar.getInstance()
                val currentDay = now.get(Calendar.DAY_OF_YEAR)
                if (currentDay != lastDayOfYear) {
                    firedPrayersToday.clear()
                    lastDayOfYear = currentDay
                }

                val h = now.get(Calendar.HOUR_OF_DAY).toString().padStart(2, '0')
                val m = now.get(Calendar.MINUTE).toString().padStart(2, '0')
                val currentTime = "$h:$m"

                val times = _prayerTimes.value
                val prayersToCheck = listOf(
                    "الفجر" to times.fajr,
                    "الظهر" to times.dhuhr,
                    "العصر" to times.asr,
                    "المغرب" to times.maghrib,
                    "العشاء" to times.isha
                )

                if (_isAdhanAutoPlayEnabled.value) {
                    for ((name, pTime) in prayersToCheck) {
                        if (pTime == currentTime && name in _enabledAdhanPrayers.value && !firedPrayersToday.contains(name)) {
                            firedPrayersToday.add(name)
                            withContext(Dispatchers.Main) {
                                playAdhan(name)
                            }
                            break
                        }
                    }
                }
            }
        }

        // Periodic update for Azkar player progress
        viewModelScope.launch(Dispatchers.Main) {
            while (isActive) {
                delay(500)
                azkarAudioController.updateProgress()
            }
        }
    }

    // AI Assistant Chat
    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(
        listOf(
            ChatMessage(
                sender = MessageSender.ASSISTANT,
                text = "السلام عليكم ورحمة الله وبركاته! أنا مساعدك القرآني الذكي في ترتيل. كيف يمكنني إعانتك اليوم في تدبر القرآن أو مراجعته؟"
            )
        )
    )
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _isAiThinking = MutableStateFlow(false)
    val isAiThinking: StateFlow<Boolean> = _isAiThinking.asStateFlow()

    // Settings & Preferences
    private val _isKeepScreenOn = MutableStateFlow(true)
    val isKeepScreenOn: StateFlow<Boolean> = _isKeepScreenOn.asStateFlow()

    private val _isVibrationEnabled = MutableStateFlow(true)
    val isVibrationEnabled: StateFlow<Boolean> = _isVibrationEnabled.asStateFlow()

    private val _isAutoScrollEnabled = MutableStateFlow(false)
    val isAutoScrollEnabled: StateFlow<Boolean> = _isAutoScrollEnabled.asStateFlow()

    private val _autoAdvanceAyah = MutableStateFlow(true)
    val autoAdvanceAyah: StateFlow<Boolean> = _autoAdvanceAyah.asStateFlow()

    // Speech Recognizer instance (lazily initialized on first mic tap to ensure ultra-fast app launch)
    private var speechRecognizer: SpeechRecognizer? = null

    init {
        // Synchronize audio player ayah callback
        audioPlayer.onAyahChanged = { surahId, ayahNumber ->
            viewModelScope.launch {
                val surah = QuranData.surahs.find { it.id == surahId }
                if (surah != null) {
                    repository.updateLastAudio(surahId, ayahNumber, surah.nameArabic, userProgress.value)

                    // Auto-sync physical mushaf page and selected surah with the currently recited ayah
                    val ayahs = QuranOfflineProvider.getSurah(getApplication(), surahId)
                    val targetAyah = ayahs.find { it.ayahNumber == ayahNumber }
                    if (targetAyah != null && targetAyah.pageNumber > 0) {
                        _currentMushafPage.value = targetAyah.pageNumber
                    }
                    if (_selectedSurah.value.id != surahId) {
                        _selectedSurah.value = surah
                        if (ayahs.isNotEmpty()) {
                            _currentAyahs.value = ayahs
                        }
                    }
                }
            }
        }
        // Lightweight non-blocking prayer times calculation
        refreshPrayerTimes()
    }

    private fun ensureSpeechRecognizer() {
        if (speechRecognizer != null) return
        try {
            if (SpeechRecognizer.isRecognitionAvailable(getApplication())) {
                speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getApplication()).apply {
                    setRecognitionListener(object : RecognitionListener {
                        override fun onReadyForSpeech(params: Bundle?) {}
                        override fun onBeginningOfSpeech() {}
                        override fun onRmsChanged(rmsdB: Float) {}
                        override fun onBufferReceived(buffer: ByteArray?) {}
                        override fun onEndOfSpeech() {
                            _isRecordingVoice.value = false
                        }
                        override fun onError(error: Int) {
                            _isRecordingVoice.value = false
                        }
                        override fun onResults(results: Bundle?) {
                            _isRecordingVoice.value = false
                            val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                            val text = matches?.firstOrNull() ?: ""
                            if (text.isNotBlank()) {
                                _spokenText.value = text
                                evaluateRecitation(text)
                            }
                        }
                        override fun onPartialResults(partialResults: Bundle?) {
                            val matches = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                            val text = matches?.firstOrNull()
                            if (!text.isNullOrBlank()) {
                                _spokenText.value = text
                            }
                        }
                        override fun onEvent(eventType: Int, params: Bundle?) {}
                    })
                }
            }
        } catch (e: Exception) {
            // SpeechRecognizer initialization failed or unavailable
        }
    }

    fun navigateTo(screen: AppScreen) {
        _currentScreen.value = screen
    }

    fun setQuranTab(tab: QuranTab) {
        _quranTab.value = tab
    }

    fun setMushafDisplayMode(mode: MushafDisplayMode) {
        _mushafDisplayMode.value = mode
    }

    fun setMushafPaperColor(color: MushafPaperColor) {
        _mushafPaperColor.value = color
    }

    fun showTafsirDialog(ayah: Ayah?) {
        _activeTafsirAyah.value = ayah
    }

    fun selectReciter(reciter: Reciter) {
        audioPlayer.selectReciter(reciter)
    }

    fun openSurah(surah: Surah, targetAyah: Int = 1) {
        _selectedSurah.value = surah
        _targetAyahNumber.value = targetAyah
        _currentMushafPage.value = surah.startPage
        _quranTab.value = QuranTab.READER
        _currentScreen.value = AppScreen.QURAN_READER

        // 1. Instantly show authentic local/offline verses
        val offline = QuranOfflineProvider.getSurah(getApplication(), surah.id)
        if (offline.isNotEmpty()) {
            _currentAyahs.value = offline
        } else {
            val initial = QuranData.getAyahsForSurah(surah.id)
            _currentAyahs.value = initial
        }

        viewModelScope.launch {
            repository.updateLastRead(surah.id, targetAyah, surah.nameArabic, userProgress.value)
        }

        // 2. Asynchronously fetch 100% authentic Uthmani text + Tafsir Al-Muyassar online if needed
        loadFullSurah(surah.id)
    }

    fun openPage(page: Int) {
        val clamped = page.coerceIn(1, QuranPagesManager.TOTAL_PAGES)
        _currentMushafPage.value = clamped
        val surah = QuranPagesManager.getPageSurah(clamped)
        _selectedSurah.value = surah

        val offline = QuranOfflineProvider.getSurah(getApplication(), surah.id)
        if (offline.isNotEmpty()) {
            _currentAyahs.value = offline
        } else {
            val initial = QuranData.getAyahsForSurah(surah.id)
            _currentAyahs.value = initial
        }

        viewModelScope.launch {
            repository.updateLastRead(surah.id, 1, surah.nameArabic, userProgress.value)
        }
    }

    fun nextPage() {
        val current = _currentMushafPage.value
        if (current < QuranPagesManager.TOTAL_PAGES) {
            openPage(current + 1)
        }
    }

    fun previousPage() {
        val current = _currentMushafPage.value
        if (current > 1) {
            openPage(current - 1)
        }
    }

    fun loadFullSurah(surahId: Int) {
        viewModelScope.launch {
            val offline = QuranOfflineProvider.getSurah(getApplication(), surahId)
            if (offline.isNotEmpty() && _selectedSurah.value.id == surahId) {
                _currentAyahs.value = offline
            }

            // Only show spinner if not available in memory, offline, or hardcoded
            if (QuranApiService.getCachedSurah(surahId) == null && offline.isEmpty() && QuranOfflineSurahs.offlineSurahs[surahId] == null) {
                _isLoadingSurahOnline.value = true
            }
            val result = QuranApiService.fetchSurahWithTafsir(surahId)
            _isLoadingSurahOnline.value = false
            result.onSuccess { list ->
                if (_selectedSurah.value.id == surahId && list.isNotEmpty()) {
                    _currentAyahs.value = list
                }
            }
        }
    }

    fun nextSurah() {
        val currentId = _selectedSurah.value.id
        if (currentId < 114) {
            val next = QuranData.surahs.find { it.id == currentId + 1 }
            if (next != null) openSurah(next, 1)
        }
    }

    fun previousSurah() {
        val currentId = _selectedSurah.value.id
        if (currentId > 1) {
            val prev = QuranData.surahs.find { it.id == currentId - 1 }
            if (prev != null) openSurah(prev, 1)
        }
    }


    fun setFontSize(size: Int) {
        _fontSizeSp.value = size.coerceIn(16, 42)
    }

    fun setThemeMode(mode: AppThemeMode) {
        _themeMode.value = mode
    }

    fun setKeepScreenOn(enabled: Boolean) {
        _isKeepScreenOn.value = enabled
    }

    fun setVibration(enabled: Boolean) {
        _isVibrationEnabled.value = enabled
    }

    fun setAutoScroll(enabled: Boolean) {
        _isAutoScrollEnabled.value = enabled
    }

    fun setAutoAdvanceAyah(enabled: Boolean) {
        _autoAdvanceAyah.value = enabled
    }

    fun clearAppCache(onComplete: (String) -> Unit) {
        viewModelScope.launch {
            try {
                QuranApiService.clearCache()
                val context = getApplication<Application>()
                context.cacheDir.deleteRecursively()
                onComplete("تم تفريغ الذاكرة المؤقتة بنجاح وتسريع استجابة التطبيق! ⚡")
            } catch (e: Exception) {
                onComplete("تم تحسين الذاكرة وتسريع استجابة التطبيق ⚡")
            }
        }
    }

    fun searchQuran(query: String) {
        _searchQuery.value = query
        if (query.trim().length < 2) {
            _searchResults.value = emptyList()
            return
        }
        val normQuery = QuranSpeechComparator.normalizeArabic(query)
        val results = mutableListOf<Ayah>()
        for (surah in QuranData.surahs) {
            val ayahs = QuranData.getAyahsForSurah(surah.id)
            for (ayah in ayahs) {
                val normAyah = QuranSpeechComparator.normalizeArabic(ayah.textUthmani)
                val normTafsir = QuranSpeechComparator.normalizeArabic(ayah.tafsir)
                if (normAyah.contains(normQuery) || normTafsir.contains(normQuery) || ayah.translation.contains(query, ignoreCase = true)) {
                    results.add(ayah)
                }
                if (results.size >= 50) break
            }
            if (results.size >= 50) break
        }
        _searchResults.value = results
    }

    // Bookmark & Favorites
    fun toggleBookmark(ayah: Ayah, surahName: String) {
        viewModelScope.launch {
            val existing = bookmarks.value.find { it.surahId == ayah.surahId && it.ayahNumber == ayah.ayahNumber }
            if (existing != null) {
                repository.removeBookmark(existing.id)
            } else {
                repository.addBookmark(ayah.surahId, ayah.ayahNumber, surahName, ayah.textUthmani)
            }
        }
    }

    fun toggleFavorite(ayah: Ayah, surahName: String) {
        viewModelScope.launch {
            val key = "${ayah.surahId}_${ayah.ayahNumber}"
            val isFav = favorites.value.any { it.key == key }
            repository.toggleFavorite(ayah.surahId, ayah.ayahNumber, surahName, ayah.textUthmani, isFav)
        }
    }

    fun addNote(ayah: Ayah, surahName: String, text: String) {
        viewModelScope.launch {
            repository.addNote(ayah.surahId, ayah.ayahNumber, surahName, text)
        }
    }

    // Hifz Testing logic
    fun setHifzSurah(surah: Surah, ayahNumber: Int = 1) {
        _hifzSurah.value = surah
        _hifzAyahNumber.value = ayahNumber
        _testResult.value = null
        _spokenText.value = ""
    }

    fun setHifzTestMode(mode: String) {
        _hifzTestMode.value = mode
        _testResult.value = null
        _spokenText.value = ""
    }

    fun startVoiceRecording() {
        _testResult.value = null
        _spokenText.value = ""
        ensureSpeechRecognizer()
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ar-SA")
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "ar-SA")
            putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, "ar-SA")
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
            putExtra(RecognizerIntent.EXTRA_PROMPT, "اقرأ الآية بصوتك المرتل...")
        }
        try {
            _isRecordingVoice.value = true
            speechRecognizer?.startListening(intent)
        } catch (e: Exception) {
            _isRecordingVoice.value = false
        }
    }

    fun stopVoiceRecording() {
        try {
            speechRecognizer?.stopListening()
        } catch (e: Exception) {
            // Ignored
        }
        _isRecordingVoice.value = false
    }

    fun evaluateRecitation(spoken: String) {
        val surah = _hifzSurah.value
        val ayahNum = _hifzAyahNumber.value
        val ayahs = QuranData.getAyahsForSurah(surah.id)
        val targetAyah = ayahs.find { it.ayahNumber == ayahNum } ?: ayahs.first()

        val result = QuranSpeechComparator.compareRecitation(
            surahId = surah.id,
            surahName = surah.nameArabic,
            startAyah = ayahNum,
            endAyah = ayahNum,
            originalAyahText = targetAyah.textUthmani,
            userSpokenText = spoken
        )
        _testResult.value = result

        // If accuracy < 85%, record as mistake into Spaced Repetition queue
        viewModelScope.launch {
            if (result.accuracyPercentage < 85) {
                repository.recordMistake(surah.id, ayahNum, surah.nameArabic, targetAyah.textUthmani)
            }
            repository.addHifzRecord(
                HifzHistoryEntity(
                    surahId = surah.id,
                    surahName = surah.nameArabic,
                    startAyah = ayahNum,
                    endAyah = ayahNum,
                    accuracyPercentage = result.accuracyPercentage,
                    testMode = _hifzTestMode.value
                ),
                userProgress.value
            )
        }
    }

    // Khatmah Actions
    fun createKhatmah(days: Int, title: String) {
        viewModelScope.launch {
            val daily = (604 / days).coerceAtLeast(1)
            val plan = KhatmahPlanEntity(
                title = title,
                targetDays = days,
                totalPages = 604,
                pagesRead = 0,
                dailyGoalPages = daily
            )
            repository.saveKhatmah(plan)
        }
    }

    fun updateKhatmahPages(plan: KhatmahPlanEntity, addedPages: Int) {
        viewModelScope.launch {
            val newCount = (plan.pagesRead + addedPages).coerceIn(0, plan.totalPages)
            repository.updateKhatmahProgress(plan, newCount)
        }
    }

    // Azkar actions
    fun selectAzkarCategory(cat: com.example.model.AzkarCategory) {
        _selectedAzkarCategory.value = cat
    }

    fun incrementDhikr(id: Int, target: Int) {
        val current = _azkarCounts.value[id] ?: 0
        if (current < target) {
            val newMap = _azkarCounts.value.toMutableMap()
            newMap[id] = current + 1
            _azkarCounts.value = newMap
            if (current + 1 == target) {
                // Award XP
                viewModelScope.launch {
                    val p = userProgress.value ?: UserProgressEntity()
                    repository.saveUserProgress(p.copy(totalXP = p.totalXP + 10))
                }
            }
        }
    }

    fun resetDhikr(id: Int) {
        val newMap = _azkarCounts.value.toMutableMap()
        newMap[id] = 0
        _azkarCounts.value = newMap
    }

    // Prayer & Location
    fun selectCity(city: PrayerCity) {
        _selectedCity.value = city
        _prayerMethod.value = city.recommendedMethod
        refreshPrayerTimes()
        syncBackgroundAdhanScheduler()
    }

    fun setPrayerMethod(method: PrayerMethod) {
        _prayerMethod.value = method
        refreshPrayerTimes()
        syncBackgroundAdhanScheduler()
    }

    fun setJuristicMethod(method: JuristicMethod) {
        _juristicMethod.value = method
        refreshPrayerTimes()
        syncBackgroundAdhanScheduler()
    }

    fun detectCurrentLocation(context: Context, onResult: (Boolean, String) -> Unit) {
        _isLocating.value = true
        LocationHelper.detectLocation(context) { detectedCity ->
            _isLocating.value = false
            if (detectedCity != null) {
                _selectedCity.value = detectedCity
                _prayerMethod.value = detectedCity.recommendedMethod
                refreshPrayerTimes()
                syncBackgroundAdhanScheduler()
                onResult(true, "تم تحديد موقعك الحالي بدقة: ${detectedCity.nameArabic}، ${detectedCity.countryArabic}")
            } else {
                onResult(false, "تعذر تحديد الموقع تلقائياً. تأكد من تفعيل الـ GPS وصلاحية الموقع.")
            }
        }
    }

    fun updatePrayerOffset(prayerName: String, deltaMinutes: Int) {
        val current = _prayerOffsets.value
        val updated = when (prayerName) {
            "الفجر" -> current.copy(fajr = (current.fajr + deltaMinutes).coerceIn(-30, 30))
            "الشروق" -> current.copy(sunrise = (current.sunrise + deltaMinutes).coerceIn(-30, 30))
            "الظهر" -> current.copy(dhuhr = (current.dhuhr + deltaMinutes).coerceIn(-30, 30))
            "العصر" -> current.copy(asr = (current.asr + deltaMinutes).coerceIn(-30, 30))
            "المغرب" -> current.copy(maghrib = (current.maghrib + deltaMinutes).coerceIn(-30, 30))
            "العشاء" -> current.copy(isha = (current.isha + deltaMinutes).coerceIn(-30, 30))
            else -> current
        }
        _prayerOffsets.value = updated
        refreshPrayerTimes()
        syncBackgroundAdhanScheduler()
    }

    fun resetPrayerOffsets() {
        _prayerOffsets.value = PrayerOffsets()
        refreshPrayerTimes()
        syncBackgroundAdhanScheduler()
    }

    fun refreshPrayerTimes() {
        _prayerTimes.value = PrayerCalculator.calculatePrayerTimes(
            city = _selectedCity.value,
            method = _prayerMethod.value,
            juristicMethod = _juristicMethod.value,
            offsets = _prayerOffsets.value
        )
    }

    private fun syncBackgroundAdhanScheduler() {
        try {
            val app: Application = getApplication()
            AdhanAlarmReceiver.savePrayerPreferences(
                context = app,
                city = _selectedCity.value,
                methodName = _prayerMethod.value.name,
                juristicName = _juristicMethod.value.name,
                muezzinId = _selectedMuezzin.value.id,
                autoPlayEnabled = _isAdhanAutoPlayEnabled.value,
                enabledPrayers = _enabledAdhanPrayers.value,
                offsets = _prayerOffsets.value
            )
            AdhanAlarmReceiver.scheduleAllPrayers(app)
        } catch (_: Exception) {}
    }

    // Audio Downloads
    fun downloadSurah(surah: Surah, reciter: Reciter = audioPlayer.state.value.selectedReciter) {
        QuranAudioDownloader.downloadSurah(reciter, surah)
    }

    fun cancelAudioDownload(surahId: Int, reciterId: String) {
        QuranAudioDownloader.cancelDownload(reciterId, surahId)
    }

    fun deleteDownloadedSurah(surahId: Int, reciterId: String) {
        QuranAudioDownloader.deleteSurah(reciterId, surahId)
    }

    fun isSurahDownloaded(surahId: Int, reciterId: String = audioPlayer.state.value.selectedReciter.id): Boolean {
        return QuranAudioDownloader.isSurahDownloaded(reciterId, surahId)
    }

    // AI Assistant
    fun sendChatMessage(text: String) {
        if (text.isBlank()) return
        val userMsg = ChatMessage(sender = MessageSender.USER, text = text)
        val updatedList = _chatMessages.value + userMsg
        _chatMessages.value = updatedList
        _isAiThinking.value = true

        viewModelScope.launch {
            val reply = QuranAiService.askAssistant(text, updatedList)
            val assistantMsg = ChatMessage(sender = MessageSender.ASSISTANT, text = reply)
            _chatMessages.value = _chatMessages.value + assistantMsg
            _isAiThinking.value = false
        }
    }

    // Spaced repetition review
    fun reviewMistake(mistake: MistakeAyahEntity, success: Boolean) {
        viewModelScope.launch {
            repository.advanceSrsLevel(mistake.key, mistake, success)
        }
    }

    // Adhan & Azkar Controls
    fun selectMuezzin(muezzin: Muezzin) {
        AdhanData.selectedMuezzin = muezzin
        _selectedMuezzin.value = muezzin
        syncBackgroundAdhanScheduler()
    }

    fun setAdhanAutoPlayEnabled(enabled: Boolean) {
        _isAdhanAutoPlayEnabled.value = enabled
        syncBackgroundAdhanScheduler()
    }

    fun toggleAdhanAutoPlay() {
        _isAdhanAutoPlayEnabled.value = !_isAdhanAutoPlayEnabled.value
        syncBackgroundAdhanScheduler()
    }

    fun toggleAdhanForPrayer(prayerName: String) {
        val current = _enabledAdhanPrayers.value.toMutableSet()
        if (current.contains(prayerName)) {
            current.remove(prayerName)
        } else {
            current.add(prayerName)
        }
        _enabledAdhanPrayers.value = current
        syncBackgroundAdhanScheduler()
    }

    fun toggleAdhanPrayer(prayerName: String) {
        toggleAdhanForPrayer(prayerName)
    }

    fun playAdhanForPrayer(prayerName: String) {
        playAdhan(prayerName)
    }

    fun testAdhanSound() {
        playAdhan("الفجر", _selectedMuezzin.value)
    }

    fun stopAdhanAudio() {
        stopAdhan()
    }

    fun toggleDuaAfterAdhan(enabled: Boolean) {
        AdhanData.isDuaAfterAdhanEnabled = enabled
        _isDuaAfterAdhanEnabled.value = enabled
    }

    fun selectDuaSheikh(sheikhId: String) {
        AdhanData.selectedDuaSheikhId = sheikhId
        _selectedDuaSheikhId.value = sheikhId
    }

    fun playDuaAfterAdhanSound(sheikhId: String = _selectedDuaSheikhId.value) {
        _isAdhanPlaying.value = true
        _isPlayingDuaAfterAdhan.value = true
        _activeAdhanAlert.value = "دعاء ما بعد الأذان • اللهم رب هذه الدعوة التامة"
        adhanAudioController.playDuaDirectly(sheikhId) {
            _isAdhanPlaying.value = false
            _isPlayingDuaAfterAdhan.value = false
            _activeAdhanAlert.value = null
        }
    }

    fun playAdhan(prayerName: String, muezzin: Muezzin = _selectedMuezzin.value) {
        _isAdhanPlaying.value = true
        _isPlayingDuaAfterAdhan.value = false
        _currentAdhanPrayer.value = prayerName
        _activeAdhanAlert.value = "حان الآن موعد أذان $prayerName بصوت ${muezzin.nameArabic}"
        adhanAudioController.playAdhan(
            muezzin = muezzin,
            prayerName = prayerName,
            onAdhanFinished = {
                if (AdhanData.isDuaAfterAdhanEnabled) {
                    _isPlayingDuaAfterAdhan.value = true
                    _activeAdhanAlert.value = "🤲 دعاء ما بعد الأذان • اللهم رب هذه الدعوة التامة"
                }
            },
            onCompletion = {
                _isAdhanPlaying.value = false
                _isPlayingDuaAfterAdhan.value = false
                _currentAdhanPrayer.value = null
                _activeAdhanAlert.value = null
            }
        )
    }

    fun stopAdhan() {
        adhanAudioController.stopAdhan()
        _isAdhanPlaying.value = false
        _isPlayingDuaAfterAdhan.value = false
        _currentAdhanPrayer.value = null
        _activeAdhanAlert.value = null
    }

    fun dismissAdhanAlert() {
        _activeAdhanAlert.value = null
    }

    // Pre-Prayer Reminder Controls (اقتربت موعد الصلاة)
    fun setPrePrayerEnabled(enabled: Boolean) {
        _isPrePrayerEnabled.value = enabled
        PrePrayerReminderHelper.setPrePrayerEnabled(getApplication(), enabled)
    }

    fun setPrePrayerMinutes(minutes: Int) {
        _prePrayerMinutes.value = minutes
        PrePrayerReminderHelper.setMinutesBefore(getApplication(), minutes)
    }

    fun setPrePrayerVoiceEnabled(enabled: Boolean) {
        _isPrePrayerVoiceEnabled.value = enabled
        PrePrayerReminderHelper.setVoiceEnabled(getApplication(), enabled)
    }

    fun previewPrePrayerSpeech(prayerName: String) {
        _isSpeakingPrePrayer.value = true
        val phrase = PrePrayerReminderHelper.getSpokenPhrase(prayerName)
        PrePrayerReminderHelper.speakArabicText(getApplication(), phrase) {
            _isSpeakingPrePrayer.value = false
        }
    }

    fun stopPrePrayerSpeech() {
        PrePrayerReminderHelper.stopSpeaking()
        _isSpeakingPrePrayer.value = false
    }

    override fun onCleared() {
        super.onCleared()
        audioPlayer.onDestroy()
        speechRecognizer?.destroy()
        adhanAudioController.stopAdhan()
        azkarAudioController.stop()
    }
}
