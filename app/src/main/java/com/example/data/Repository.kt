package com.example.data

import android.util.Base64
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import java.text.SimpleDateFormat
import java.util.*

class Repository(private val db: AppDatabase) {

    private val userDao = db.userDao()
    private val progressDao = db.lessonProgressDao()
    private val chatDao = db.chatDao()
    private val leaderboardDao = db.leaderboardDao()

    private val moshi = Moshi.Builder().build()

    // --- User Profile Flows & Functions ---
    val userProfileFlow: Flow<UserProfile?> = userDao.getUserProfileFlow()

    suspend fun getOrCreateProfile(): UserProfile {
        val profile = userDao.getUserProfile()
        if (profile == null) {
            val newProfile = UserProfile()
            userDao.insertUserProfile(newProfile)
            return newProfile
        }
        return profile
    }

    suspend fun updateProfile(profile: UserProfile) {
        userDao.insertUserProfile(profile)
    }

    // Award XP and manage daily streak calculations
    suspend fun awardXp(xp: Int) {
        val currentProfile = getOrCreateProfile()
        val todayStr = getCurrentDateString()

        val lastActive = currentProfile.lastActiveDate
        val newStreak = when {
            lastActive.isEmpty() -> {
                1
            }
            lastActive == todayStr -> {
                // Already practiced today, keep streak
                currentProfile.currentStreak
            }
            isYesterday(lastActive) -> {
                // Practice on consecutive day, increment streak
                currentProfile.currentStreak + 1
            }
            else -> {
                // Broke streak, reset to 1
                1
            }
        }

        val updatedProfile = currentProfile.copy(
            points = currentProfile.points + xp,
            currentStreak = newStreak,
            lastActiveDate = todayStr
        )
        userDao.insertUserProfile(updatedProfile)

        // Also update user's score in the leaderboard!
        updateMyLeaderboardScore(updatedProfile.points, newStreak)
    }

    // --- Lesson Progress Flows & Functions ---
    val allProgressFlow: Flow<List<LessonProgress>> = progressDao.getAllProgressFlow()

    suspend fun completeLesson(lessonId: String, targetLang: String, score: Int) {
        val progress = LessonProgress(
            lessonId = lessonId,
            targetLanguage = targetLang,
            isCompleted = true,
            score = score,
            lastAttempt = System.currentTimeMillis()
        )
        progressDao.insertProgress(progress)
    }

    suspend fun clearProgress() {
        progressDao.clearAllProgress()
    }

    // --- Chat Flows & Functions ---
    val chatHistoryFlow: Flow<List<ChatMessage>> = chatDao.getChatHistoryFlow()

    suspend fun saveMessage(text: String, isUser: Boolean, translatedText: String? = null) {
        val msg = ChatMessage(text = text, isUser = isUser, translatedText = translatedText)
        chatDao.insertMessage(msg)
    }

    suspend fun clearChatHistory() {
        chatDao.clearHistory()
    }

    // --- Leaderboard Flows & Functions ---
    val leaderboardFlow: Flow<List<LeaderboardUser>> = leaderboardDao.getLeaderboardFlow()

    suspend fun setupLeaderboardIfEmpty() {
        val currentList = leaderboardDao.getLeaderboardOnce()
        if (currentList.isEmpty()) {
            val simulatedUsers = listOf(
                LeaderboardUser(id = 1, name = "Kazi Anis (Dhaka, BD) 🇧🇩", points = 1250, streak = 18, isMe = false),
                LeaderboardUser(id = 2, name = "Fatima Al-Harbi (Riyadh, SA) 🇸🇦", points = 1120, streak = 15, isMe = false),
                LeaderboardUser(id = 3, name = "Zayn Malik (London, UK) 🇬🇧", points = 940, streak = 11, isMe = false),
                LeaderboardUser(id = 4, name = "Yasmin Begum (Sylhet, BD) 🇧🇩", points = 830, streak = 9, isMe = false),
                LeaderboardUser(id = 5, name = "Sajid Afridi (Karachi, PK) 🇵🇰", points = 720, streak = 7, isMe = false),
                LeaderboardUser(id = 6, name = "You (Me)", points = 100, streak = 0, isMe = true),
                LeaderboardUser(id = 7, name = "Sarah Jenkins (New York, US) 🇺🇸", points = 640, streak = 6, isMe = false),
                LeaderboardUser(id = 8, name = "Omar Farooq (Cairo, EG) 🇪🇬", points = 510, streak = 5, isMe = false),
                LeaderboardUser(id = 9, name = "Farid Uddin (Chittagong, BD) 🇧🇩", points = 410, streak = 4, isMe = false),
                LeaderboardUser(id = 10, name = "Amina Shah (London, UK) 🇬🇧", points = 320, streak = 3, isMe = false),
                LeaderboardUser(id = 11, name = "Rafi Al-Hasan (Rajshahi, BD) 🇧🇩", points = 230, streak = 2, isMe = false),
                LeaderboardUser(id = 12, name = "Aisha Khan (Kuala Lumpur, MY) 🇲🇾", points = 150, streak = 1, isMe = false)
            )
            leaderboardDao.insertUsers(simulatedUsers)
        }
    }

    suspend fun getLeaderboardOnce(): List<LeaderboardUser> {
        return leaderboardDao.getLeaderboardOnce()
    }

    suspend fun insertUser(user: LeaderboardUser) {
        leaderboardDao.insertUser(user)
    }

    suspend fun updateMyLeaderboardName(newName: String) {
        val currentLeaderboard = leaderboardDao.getLeaderboardOnce()
        val me = currentLeaderboard.find { it.isMe }
        if (me != null) {
            leaderboardDao.insertUser(me.copy(name = newName))
        } else {
            leaderboardDao.insertUser(LeaderboardUser(name = newName, points = 100, streak = 0, isMe = true))
        }
    }

    private suspend fun updateMyLeaderboardScore(points: Int, streak: Int) {
        val currentLeaderboard = leaderboardDao.getLeaderboardOnce()
        val me = currentLeaderboard.find { it.isMe }
        if (me != null) {
            leaderboardDao.insertUser(me.copy(points = points, streak = streak))
        } else {
            leaderboardDao.insertUser(LeaderboardUser(name = "You (Me)", points = points, streak = streak, isMe = true))
        }
    }

    // --- Date Helpers ---
    private fun getCurrentDateString(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }

    private fun isYesterday(dateStr: String): Boolean {
        return try {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = sdf.parse(dateStr) ?: return false

            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, -1)

            val yesterdayStr = sdf.format(calendar.time)
            yesterdayStr == dateStr
        } catch (e: Exception) {
            false
        }
    }

    // --- E2E Encrypted Backup & Restore Utilities ---
    // Serializes local progress & profile into an encrypted string (Base64 + simple XOR for E2E-like lock representation)
    suspend fun generateBackupCode(): String {
        val profile = getOrCreateProfile()
        val progressList = progressDao.getAllProgressOnce()

        val adapterProfile = moshi.adapter(UserProfile::class.java)
        val adapterProgress = moshi.adapter(Array<LessonProgress>::class.java)

        val profileJson = adapterProfile.toJson(profile)
        val progressJson = adapterProgress.toJson(progressList.toTypedArray())

        val rawBackupData = "$profileJson|||$progressJson"
        
        // XOR Encryption Simulation using Key
        val key = "LINGOPLAYSECUREKEY"
        val encryptedBytes = xorEncryptDecrypt(rawBackupData.toByteArray(), key.toByteArray())
        return Base64.encodeToString(encryptedBytes, Base64.NO_WRAP)
    }

    suspend fun restoreBackupCode(base64Code: String): Boolean {
        return try {
            val decodedBytes = Base64.decode(base64Code, Base64.NO_WRAP)
            val key = "LINGOPLAYSECUREKEY"
            val decryptedBytes = xorEncryptDecrypt(decodedBytes, key.toByteArray())
            val rawBackupData = String(decryptedBytes)

            val splits = rawBackupData.split("|||")
            if (splits.size == 2) {
                val profileJson = splits[0]
                val progressJson = splits[1]

                val profile = moshi.adapter(UserProfile::class.java).fromJson(profileJson)
                val progressList = moshi.adapter(Array<LessonProgress>::class.java).fromJson(progressJson)

                if (profile != null) {
                    userDao.insertUserProfile(profile.copy(id = 1)) // keep ID 1
                    updateMyLeaderboardScore(profile.points, profile.currentStreak)
                }

                if (progressList != null) {
                    progressDao.clearAllProgress()
                    progressList.forEach { p ->
                        progressDao.insertProgress(p)
                    }
                }
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    private fun xorEncryptDecrypt(input: ByteArray, key: ByteArray): ByteArray {
        val output = ByteArray(input.size)
        for (i in input.indices) {
            output[i] = (input[i].toInt() xor key[i % key.size].toInt()).toByte()
        }
        return output
    }
}
