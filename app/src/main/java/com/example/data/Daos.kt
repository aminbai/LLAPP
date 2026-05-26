package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user_profile WHERE id = 1 LIMIT 1")
    fun getUserProfileFlow(): Flow<UserProfile?>

    @Query("SELECT * FROM user_profile WHERE id = 1 LIMIT 1")
    suspend fun getUserProfile(): UserProfile?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserProfile(profile: UserProfile)
}

@Dao
interface LessonProgressDao {
    @Query("SELECT * FROM lesson_progress")
    fun getAllProgressFlow(): Flow<List<LessonProgress>>

    @Query("SELECT * FROM lesson_progress")
    suspend fun getAllProgressOnce(): List<LessonProgress>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgress(progress: LessonProgress)

    @Query("SELECT * FROM lesson_progress WHERE lessonId = :lessonId LIMIT 1")
    suspend fun getProgressForLesson(lessonId: String): LessonProgress?

    @Query("DELETE FROM lesson_progress")
    suspend fun clearAllProgress()
}

@Dao
interface ChatDao {
    @Query("SELECT * FROM chat_message ORDER BY timestamp ASC")
    fun getChatHistoryFlow(): Flow<List<ChatMessage>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: ChatMessage)

    @Query("DELETE FROM chat_message")
    suspend fun clearHistory()
}

@Dao
interface LeaderboardDao {
    @Query("SELECT * FROM leaderboard_user ORDER BY points DESC")
    fun getLeaderboardFlow(): Flow<List<LeaderboardUser>>

    @Query("SELECT * FROM leaderboard_user ORDER BY points DESC")
    suspend fun getLeaderboardOnce(): List<LeaderboardUser>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<LeaderboardUser>)

    @Query("DELETE FROM leaderboard_user")
    suspend fun clearLeaderboard()
}
