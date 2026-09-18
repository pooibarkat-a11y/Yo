package com.example.data.local

import kotlinx.coroutines.flow.Flow

class AppRepository(private val dao: AppDao) {
    val bookmarks: Flow<List<BookmarkEntity>> = dao.getAllBookmarks()
    val favorites: Flow<List<FavoriteAyahEntity>> = dao.getAllFavorites()
    val allNotes: Flow<List<AyahNoteEntity>> = dao.getAllNotes()
    val khatmahs: Flow<List<KhatmahPlanEntity>> = dao.getAllKhatmahs()
    val activeKhatmah: Flow<KhatmahPlanEntity?> = dao.getActiveKhatmah()
    val mistakeAyahs: Flow<List<MistakeAyahEntity>> = dao.getAllMistakeAyahs()
    val userProgress: Flow<UserProgressEntity?> = dao.getUserProgress()
    val hifzHistory: Flow<List<HifzHistoryEntity>> = dao.getAllHifzHistory()

    fun isFavorite(surahId: Int, ayahNumber: Int): Flow<Boolean> =
        dao.isFavorite("${surahId}_${ayahNumber}")

    suspend fun toggleFavorite(surahId: Int, ayahNumber: Int, surahName: String, ayahText: String, currentFav: Boolean) {
        val key = "${surahId}_${ayahNumber}"
        if (currentFav) {
            dao.deleteFavorite(key)
        } else {
            dao.insertFavorite(
                FavoriteAyahEntity(
                    key = key,
                    surahId = surahId,
                    ayahNumber = ayahNumber,
                    surahName = surahName,
                    ayahText = ayahText
                )
            )
        }
    }

    suspend fun addBookmark(surahId: Int, ayahNumber: Int, surahName: String, ayahText: String, note: String = "") {
        dao.insertBookmark(
            BookmarkEntity(
                surahId = surahId,
                ayahNumber = ayahNumber,
                surahName = surahName,
                ayahText = ayahText,
                note = note
            )
        )
    }

    suspend fun removeBookmark(id: Int) = dao.deleteBookmark(id)

    suspend fun addNote(surahId: Int, ayahNumber: Int, surahName: String, text: String) {
        dao.insertNote(
            AyahNoteEntity(
                surahId = surahId,
                ayahNumber = ayahNumber,
                surahName = surahName,
                noteText = text
            )
        )
    }

    suspend fun removeNote(id: Int) = dao.deleteNote(id)

    suspend fun saveKhatmah(plan: KhatmahPlanEntity) = dao.insertKhatmah(plan)

    suspend fun updateKhatmahProgress(plan: KhatmahPlanEntity, newPages: Int) {
        val completed = newPages >= plan.totalPages
        dao.updateKhatmah(plan.copy(pagesRead = newPages.coerceAtMost(plan.totalPages), isCompleted = completed))
    }

    suspend fun deleteKhatmah(id: Int) = dao.deleteKhatmah(id)

    suspend fun recordMistake(surahId: Int, ayahNumber: Int, surahName: String, ayahText: String) {
        val key = "${surahId}_${ayahNumber}"
        val now = System.currentTimeMillis()
        val nextDue = now + (24 * 60 * 60 * 1000L) // review next day
        dao.insertOrUpdateMistake(
            MistakeAyahEntity(
                key = key,
                surahId = surahId,
                ayahNumber = ayahNumber,
                surahName = surahName,
                ayahText = ayahText,
                mistakeCount = 1,
                srsLevel = 1,
                lastReviewed = now,
                nextReviewDue = nextDue
            )
        )
    }

    suspend fun advanceSrsLevel(key: String, mistake: MistakeAyahEntity, success: Boolean) {
        val now = System.currentTimeMillis()
        if (success) {
            val newLevel = (mistake.srsLevel + 1).coerceAtMost(5)
            // Intervals: L1: 1 day, L2: 3 days, L3: 7 days, L4: 14 days, L5: 30 days
            val intervalDays = when (newLevel) {
                1 -> 1L
                2 -> 3L
                3 -> 7L
                4 -> 14L
                else -> 30L
            }
            dao.insertOrUpdateMistake(
                mistake.copy(
                    srsLevel = newLevel,
                    lastReviewed = now,
                    nextReviewDue = now + (intervalDays * 24 * 60 * 60 * 1000L)
                )
            )
        } else {
            // Reset to level 1 on failure
            dao.insertOrUpdateMistake(
                mistake.copy(
                    srsLevel = 1,
                    mistakeCount = mistake.mistakeCount + 1,
                    lastReviewed = now,
                    nextReviewDue = now + (24 * 60 * 60 * 1000L)
                )
            )
        }
    }

    suspend fun updateLastRead(surahId: Int, ayahNumber: Int, surahName: String, currentProgress: UserProgressEntity?) {
        val base = currentProgress ?: UserProgressEntity()
        dao.saveUserProgress(
            base.copy(
                lastReadSurahId = surahId,
                lastReadAyahNumber = ayahNumber,
                lastReadSurahName = surahName,
                ayahsReadToday = base.ayahsReadToday + 1,
                totalXP = base.totalXP + 5
            )
        )
    }

    suspend fun updateLastAudio(surahId: Int, ayahNumber: Int, surahName: String, currentProgress: UserProgressEntity?) {
        val base = currentProgress ?: UserProgressEntity()
        dao.saveUserProgress(
            base.copy(
                lastAudioSurahId = surahId,
                lastAudioAyahNumber = ayahNumber,
                lastAudioSurahName = surahName,
                minutesListenedToday = base.minutesListenedToday + 1,
                totalXP = base.totalXP + 3
            )
        )
    }

    suspend fun addHifzRecord(record: HifzHistoryEntity, currentProgress: UserProgressEntity?) {
        dao.insertHifzHistory(record)
        val base = currentProgress ?: UserProgressEntity()
        val xpGain = (record.accuracyPercentage / 2) + 20
        dao.saveUserProgress(
            base.copy(
                totalXP = base.totalXP + xpGain,
                hifzTestsCompleted = base.hifzTestsCompleted + 1
            )
        )
    }

    suspend fun saveUserProgress(progress: UserProgressEntity) {
        dao.saveUserProgress(progress)
    }
}
