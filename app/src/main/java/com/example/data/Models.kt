package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity(tableName = "user_profile")
@JsonClass(generateAdapter = true)
data class UserProfile(
    @PrimaryKey val id: Int = 1,
    val points: Int = 100,
    val currentStreak: Int = 0,
    val lastActiveDate: String = "", // YYYY-MM-DD
    val nativeLanguage: String = "BN", // BN, EN, AR
    val targetLanguage: String = "EN", // BN, EN, AR
    val skillLevel: String = "Beginner", // Beginner, Intermediate, Advanced
    val learningGoal: String = "Regular", // Casual (10 XP), Regular (20 XP), Serious (50 XP)
    val isDarkMode: Boolean = false,
    val isSyncEnabled: Boolean = false,
    val isE2eEncrypted: Boolean = false,
    val securityCode: String = "SECURE-1234",
    val multiDeviceCode: String = "DEV-8899"
)

@Entity(tableName = "lesson_progress")
@JsonClass(generateAdapter = true)
data class LessonProgress(
    @PrimaryKey val lessonId: String,
    val targetLanguage: String,
    val isCompleted: Boolean = false,
    val score: Int = 0,
    val lastAttempt: Long = 0
)

@Entity(tableName = "chat_message")
@JsonClass(generateAdapter = true)
data class ChatMessage(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val text: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis(),
    val translatedText: String? = null
)

@Entity(tableName = "leaderboard_user")
@JsonClass(generateAdapter = true)
data class LeaderboardUser(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val points: Int,
    val streak: Int,
    val isMe: Boolean = false
)
