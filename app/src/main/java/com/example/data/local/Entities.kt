package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val surahId: Int,
    val ayahNumber: Int,
    val surahName: String,
    val ayahText: String,
    val note: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "favorites")
data class FavoriteAyahEntity(
    @PrimaryKey val key: String, // "surah_ayah"
    val surahId: Int,
    val ayahNumber: Int,
    val surahName: String,
    val ayahText: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "ayah_notes")
data class AyahNoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val surahId: Int,
    val ayahNumber: Int,
    val surahName: String,
    val noteText: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "khatmah_plans")
data class KhatmahPlanEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val targetDays: Int,
    val totalPages: Int = 604,
    val pagesRead: Int = 0,
    val dailyGoalPages: Int = 20,
    val startDate: Long = System.currentTimeMillis(),
    val isCompleted: Boolean = false,
    val reminderHour: Int = 8,
    val reminderMinute: Int = 0
)

@Entity(tableName = "mistake_ayahs")
data class MistakeAyahEntity(
    @PrimaryKey val key: String, // "surah_ayah"
    val surahId: Int,
    val ayahNumber: Int,
    val surahName: String,
    val ayahText: String,
    val mistakeCount: Int = 1,
    val srsLevel: Int = 1, // 1 to 5
    val lastReviewed: Long = System.currentTimeMillis(),
    val nextReviewDue: Long = System.currentTimeMillis() + (24 * 60 * 60 * 1000L) // +1 day
)

@Entity(tableName = "user_progress")
data class UserProgressEntity(
    @PrimaryKey val id: Int = 1,
    val lastReadSurahId: Int = 1,
    val lastReadAyahNumber: Int = 1,
    val lastReadSurahName: String = "الفاتحة",
    val lastAudioSurahId: Int = 1,
    val lastAudioAyahNumber: Int = 1,
    val lastAudioSurahName: String = "الفاتحة",
    val streakDays: Int = 1,
    val lastActiveDate: String = "",
    val pagesReadToday: Int = 4,
    val ayahsReadToday: Int = 28,
    val minutesListenedToday: Int = 15,
    val totalXP: Int = 150,
    val userLevel: Int = 1,
    val userLevelTitle: String = "قارئ مواظب",
    val hifzTestsCompleted: Int = 1
)

@Entity(tableName = "hifz_history")
data class HifzHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val surahId: Int,
    val surahName: String,
    val startAyah: Int,
    val endAyah: Int,
    val accuracyPercentage: Int,
    val testMode: String,
    val timestamp: Long = System.currentTimeMillis()
)
