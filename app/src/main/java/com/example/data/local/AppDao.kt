package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    // Bookmarks
    @Query("SELECT * FROM bookmarks ORDER BY timestamp DESC")
    fun getAllBookmarks(): Flow<List<BookmarkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: BookmarkEntity)

    @Query("DELETE FROM bookmarks WHERE id = :id")
    suspend fun deleteBookmark(id: Int)

    // Favorites
    @Query("SELECT * FROM favorites ORDER BY timestamp DESC")
    fun getAllFavorites(): Flow<List<FavoriteAyahEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteAyahEntity)

    @Query("DELETE FROM favorites WHERE `key` = :key")
    suspend fun deleteFavorite(key: String)

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE `key` = :key)")
    fun isFavorite(key: String): Flow<Boolean>

    // Notes
    @Query("SELECT * FROM ayah_notes WHERE surahId = :surahId AND ayahNumber = :ayahNumber")
    fun getNotesForAyah(surahId: Int, ayahNumber: Int): Flow<List<AyahNoteEntity>>

    @Query("SELECT * FROM ayah_notes ORDER BY timestamp DESC")
    fun getAllNotes(): Flow<List<AyahNoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: AyahNoteEntity)

    @Query("DELETE FROM ayah_notes WHERE id = :id")
    suspend fun deleteNote(id: Int)

    // Khatmah Plans
    @Query("SELECT * FROM khatmah_plans ORDER BY id DESC")
    fun getAllKhatmahs(): Flow<List<KhatmahPlanEntity>>

    @Query("SELECT * FROM khatmah_plans WHERE isCompleted = 0 LIMIT 1")
    fun getActiveKhatmah(): Flow<KhatmahPlanEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKhatmah(plan: KhatmahPlanEntity)

    @Update
    suspend fun updateKhatmah(plan: KhatmahPlanEntity)

    @Query("DELETE FROM khatmah_plans WHERE id = :id")
    suspend fun deleteKhatmah(id: Int)

    // Spaced Repetition / Mistakes
    @Query("SELECT * FROM mistake_ayahs ORDER BY nextReviewDue ASC")
    fun getAllMistakeAyahs(): Flow<List<MistakeAyahEntity>>

    @Query("SELECT * FROM mistake_ayahs WHERE nextReviewDue <= :currentTime ORDER BY mistakeCount DESC")
    fun getDueRevisionAyahs(currentTime: Long): Flow<List<MistakeAyahEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateMistake(mistake: MistakeAyahEntity)

    @Query("DELETE FROM mistake_ayahs WHERE `key` = :key")
    suspend fun deleteMistake(key: String)

    // User Progress
    @Query("SELECT * FROM user_progress WHERE id = 1")
    fun getUserProgress(): Flow<UserProgressEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserProgress(progress: UserProgressEntity)

    // Hifz Test History
    @Query("SELECT * FROM hifz_history ORDER BY timestamp DESC")
    fun getAllHifzHistory(): Flow<List<HifzHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHifzHistory(record: HifzHistoryEntity)
}
