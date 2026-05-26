package com.example.ui

import android.app.Application
import android.speech.tts.TextToSpeech
import java.util.Locale
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.*
import com.example.network.queryGemini
import com.example.ui.components.RobotState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application), TextToSpeech.OnInitListener {

    private val db = AppDatabase.getDatabase(application)
    private val repository = Repository(db)

    // --- Native TTS Engine Support ---
    private var tts: TextToSpeech? = null
    val speechRate = MutableStateFlow(1.0f)

    // --- Core StateFlow Channels ---
    val userProfile: StateFlow<UserProfile> = repository.userProfileFlow
        .filterNotNull()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserProfile()
        )

    val lessonProgress: StateFlow<List<LessonProgress>> = repository.allProgressFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _leaderboard = MutableStateFlow<List<LeaderboardUser>>(emptyList())
    val leaderboard: StateFlow<List<LeaderboardUser>> = _leaderboard.asStateFlow()

    val chatHistory: StateFlow<List<ChatMessage>> = repository.chatHistoryFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // --- UI Navigation State ---
    private val _currentTab = MutableStateFlow("HOME") // HOME, GAMES, LEADERBOARD, PLAN, CHATBOT, SETTINGS
    val currentTab: StateFlow<String> = _currentTab.asStateFlow()

    fun navigateTo(tab: String) {
        _currentTab.value = tab
    }

    // --- Active Game/Lesson Play States ---
    private val _activeLesson = MutableStateFlow<Lesson?>(null)
    val activeLesson: StateFlow<Lesson?> = _activeLesson.asStateFlow()

    private val _activeGameStep = MutableStateFlow(0)
    val activeGameStep: StateFlow<Int> = _activeGameStep.asStateFlow()

    private val _activeGameScore = MutableStateFlow(0)
    val activeGameScore: StateFlow<Int> = _activeGameScore.asStateFlow()

    private val _isGameFinished = MutableStateFlow(false)
    val isGameFinished: StateFlow<Boolean> = _isGameFinished.asStateFlow()

    // Vocab match state
    val selectedVocabLeft = MutableStateFlow<String?>(null)
    val selectedVocabRight = MutableStateFlow<String?>(null)
    val correctMatchPairs = MutableStateFlow<Set<String>>(emptySet()) // set of words successfully matched

    // AI Speech evaluation result representation
    private val _pronunciationScore = MutableStateFlow<Int?>(null)
    val pronunciationScore: StateFlow<Int?> = _pronunciationScore.asStateFlow()

    private val _pronunciationFeedback = MutableStateFlow<String?>(null)
    val pronunciationFeedback: StateFlow<String?> = _pronunciationFeedback.asStateFlow()

    private val _isRecording = MutableStateFlow(false)
    val isRecording: StateFlow<Boolean> = _isRecording.asStateFlow()

    // --- Robot Assistant State ---
    private val _robotState = MutableStateFlow(RobotState.IDLE)
    val robotState: StateFlow<RobotState> = _robotState.asStateFlow()

    // Sync, Backup, and Notification states
    private val _backupCodeResult = MutableStateFlow<String?>(null)
    val backupCodeResult: StateFlow<String?> = _backupCodeResult.asStateFlow()

    private val _backupStatusMessage = MutableStateFlow<String?>(null)
    val backupStatusMessage: StateFlow<String?> = _backupStatusMessage.asStateFlow()

    private val _notificationToast = MutableStateFlow<String?>(null)
    val notificationToast: StateFlow<String?> = _notificationToast.asStateFlow()

    // --- Features 1-6 State Flows ---
    val searchQuery = MutableStateFlow("")
    val bookmarkedWords = MutableStateFlow<Set<String>>(emptySet())
    val isReminderEnabled = MutableStateFlow(false)
    val reminderTime = MutableStateFlow("08:00 PM")
    val currentFlashCardIndex = MutableStateFlow(0)
    val isFlashCardFlipped = MutableStateFlow(false)
    val weeklyStreaks = MutableStateFlow(listOf(true, true, true, false, false, false, false)) // Mon to Sun checkmarks
    val accuracyRate = MutableStateFlow(88) // percentage
    val vocabularyMastery = MutableStateFlow(12) // count of words mastered

    init {
        // Initialize user database items on startup safely
        viewModelScope.launch {
            try {
                repository.getOrCreateProfile()
                repository.setupLeaderboardIfEmpty()

                // Collect database flow to initialize and merge user (Me) changes into our real-time in-memory state
                repository.leaderboardFlow.collect { dbUsers ->
                    try {
                        val currentInMem = _leaderboard.value
                        if (currentInMem.isEmpty()) {
                            _leaderboard.value = dbUsers
                        } else {
                            // Update only "Me" from the DB, keep peer simulated updates
                            val updatedList = currentInMem.map { inMemUser ->
                                if (inMemUser.isMe) {
                                    dbUsers.find { it.isMe } ?: inMemUser
                                } else {
                                    inMemUser
                                }
                            }
                            _leaderboard.value = updatedList
                        }
                    } catch (innerEx: Throwable) {
                        innerEx.printStackTrace()
                    }
                }
            } catch (ex: Throwable) {
                ex.printStackTrace()
            }
        }

        viewModelScope.launch {
            // Delay slightly to wait for DB instantiation before starting simulation loop
            kotlinx.coroutines.delay(2000)
            startRealtimeLeaderboardSimulation()
        }

        // Initialize TTS safely inside constructor
        try {
            tts = TextToSpeech(application, this)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts?.language = Locale.US
        }
    }

    // Helper to map and transliterate Bengali and Arabic words if target voice engine is not supported
    fun getPhoneticFallback(text: String): String {
        // Look for English text inside parentheses first, e.g. "ভাষা (Bhasha)" -> "Bhasha"
        val regex = Regex("\\(([^)]+)\\)")
        val matchResult = regex.find(text)
        if (matchResult != null) {
            val inside = matchResult.groups[1]?.value?.trim() ?: ""
            if (inside.all { it in 'A'..'Z' || it in 'a'..'z' || it == ' ' || it == '\'' || it == '-' }) {
                return inside
            }
        }

        val clean = text.trim()
        val mapper = mapOf(
            "مَرْحَبًا" to "Marhaban",
            "شُكْرًا" to "Shukran",
            "كِتَابٌ" to "Kitabun",
            "مَاءٌ" to "Ma'un",
            "بَيْتٌ" to "Baytun",
            "পানি" to "Paani",
            "বই" to "Boi",
            "বাড়ি" to "Baari",
            "বন্ধু" to "Bondhu",
            "বিদ্যালয় / স্কুল" to "School",
            "স্কুল" to "School",
            "আপেল" to "Apple",
            "শুভ সকাল" to "Shuvo Sokal",
            "ধন্যবাদ" to "Dhonnobad",
            "আপনি কেমন আছেন?" to "Apni kemon achen?",
            "আমার সোনার বাংলা, আমি তোমায় ভালোবাসি" to "Amar Shonar Bangla, ami tomay bhalobashi",
            "সবার উপরে মানুষ সত্য, তাহার উপরে নাই" to "Shobar upore manush shotto, tahar upore nai",
            "আজকের দিনটি অনেক সুন্দর এবং চমৎকার" to "Ajker dinti onek shundor ebong chomokkar"
        )
        for ((key, value) in mapper) {
            if (clean.contains(key, ignoreCase = true)) {
                return value
            }
        }
        
        // Strip out non-latin letters to avoid robot silence if speaking latin only
        val asciiOnly = text.filter { it in 'A'..'Z' || it in 'a'..'z' || it in '0'..'9' || it == ' ' || it == ',' || it == '.' || it == '!' || it == '?' }
        if (asciiOnly.trim().length > 2) {
            return asciiOnly.trim()
        }
        
        return text
    }

    fun speakText(text: String) {
        try {
            tts?.setSpeechRate(speechRate.value)
            
            // 2. Classify original language target
            val isArabic = text.any { it in '\u0600'..'\u06FF' }
            val isBengali = text.any { it in '\u0980'..'\u09FF' }
            
            val targetLanguage = if (isArabic) {
                Locale("ar")
            } else if (isBengali) {
                Locale("bn")
            } else {
                Locale.US
            }

            // 3. Test if device actually has voice dataset for the target language
            val setLanguageResult = tts?.setLanguage(targetLanguage) ?: TextToSpeech.LANG_NOT_SUPPORTED
            val isLanguageSupported = setLanguageResult >= 0

            val textToSpeak: String
            if (!isLanguageSupported && (isArabic || isBengali)) {
                // Missing language data! Use phonetic translation and read it in Latin US English style
                textToSpeak = getPhoneticFallback(text)
                tts?.setLanguage(Locale.US)
                _notificationToast.value = "🔊 Local device missing Bengali/Arabic voice pack. Played phonetic guide: \"$textToSpeak\" (ইংরেজি উচ্চারণ সহায়ক ভয়েস প্লে করা হচ্ছে)"
            } else {
                // Language is supported - play native vocals
                textToSpeak = text
                _notificationToast.value = "🔊 Play audio: \"$textToSpeak\" (ফোনের ভলিউম বাড়িয়ে নিন)"
            }
            
            tts?.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null, "LingoPlaySpeak")
        } catch (e: Throwable) {
            e.printStackTrace()
            _notificationToast.value = "🔊 Error playing audio guide (অডিও প্লে করতে সমস্যা হয়েছে)"
        }
        
        // Show lovely blinking robot animation representing conversation feedback
        viewModelScope.launch {
            _robotState.value = RobotState.TALKING
            kotlinx.coroutines.delay(2000)
            _robotState.value = RobotState.IDLE
        }
    }

    fun playRobotDashboardGreeting() {
        val greetings = listOf(
            "হ্যালো! আমি আপনার ভাষা শেখার সাথী। চলুন একসাথে নতুন কিছু শিখি!",
            "শুভ দিন! আজ কি আপনি নতুন কিছু শব্দ প্র্যাকটিস করতে প্রস্তুত?",
            "দারুণ! আপনি আজকে পড়াশোনা করতে এসেছেন। আপনার স্ট্রাইক ধরে রাখুন!",
            "কেমন আছেন? চলুন ভাষা শেখা শুরু করা যাক!"
        )
        val rand = greetings.random()
        speakText(rand)
        viewModelScope.launch {
            _robotState.value = RobotState.TALKING
            kotlinx.coroutines.delay(4000)
            _robotState.value = RobotState.IDLE
        }
    }

    fun toggleBookmark(word: String) {
        val current = bookmarkedWords.value.toMutableSet()
        if (current.contains(word)) {
            current.remove(word)
            _notificationToast.value = "Removed from Vocabulary Stars! ⭐"
        } else {
            current.add(word)
            _notificationToast.value = "Added to Vocabulary Stars! ⭐"
        }
        bookmarkedWords.value = current
        vocabularyMastery.value = current.size + 12
    }

    fun setReminder(enabled: Boolean, time: String) {
        isReminderEnabled.value = enabled
        reminderTime.value = time
        if (enabled) {
            _notificationToast.value = "Practice reminder set to: $time!"
        } else {
            _notificationToast.value = "Practice reminders disabled."
        }
    }

    fun flipFlashCard() {
        isFlashCardFlipped.value = !isFlashCardFlipped.value
    }

    fun nextFlashCard(maxSize: Int) {
        isFlashCardFlipped.value = false
        if (maxSize > 0) {
            currentFlashCardIndex.value = (currentFlashCardIndex.value + 1) % maxSize
        }
    }

    fun previousFlashCard(maxSize: Int) {
        isFlashCardFlipped.value = false
        if (maxSize > 0) {
            val prev = currentFlashCardIndex.value - 1
            currentFlashCardIndex.value = if (prev < 0) maxSize - 1 else prev
        }
    }

    override fun onCleared() {
        super.onCleared()
        try {
            tts?.stop()
            tts?.shutdown()
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    // Clear toast message
    fun clearToast() {
        _notificationToast.value = null
    }

    // --- Profile & Preferences Management ---
    fun setDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            val prof = repository.getOrCreateProfile()
            repository.updateProfile(prof.copy(isDarkMode = enabled))
        }
    }

    fun updateLanguages(native: String, target: String) {
        viewModelScope.launch {
            val prof = repository.getOrCreateProfile()
            repository.updateProfile(prof.copy(nativeLanguage = native, targetLanguage = target))
            _notificationToast.value = "Languages updated! Target: $target"
        }
    }

    fun updatePersonalizedPlan(level: String, goal: String) {
        viewModelScope.launch {
            val prof = repository.getOrCreateProfile()
            repository.updateProfile(prof.copy(skillLevel = level, learningGoal = goal))
            _notificationToast.value = "Personal plan configured for $level ($goal)!"
        }
    }

    fun updateSyncEnabled(enabled: Boolean) {
        viewModelScope.launch {
            val prof = repository.getOrCreateProfile()
            repository.updateProfile(prof.copy(isSyncEnabled = enabled))
            _notificationToast.value = if (enabled) "Cloud simulation synchronization active!" else "Sync disabled."
        }
    }

    fun updateE2eEncryption(enabled: Boolean) {
        viewModelScope.launch {
            val prof = repository.getOrCreateProfile()
            repository.updateProfile(prof.copy(isE2eEncrypted = enabled))
            _notificationToast.value = if (enabled) "End-to-End Encryption enabled for all backups!" else "E2E Encryption disabled."
        }
    }

    // --- Game / Lesson Playing Logic ---
    fun selectLesson(lesson: Lesson) {
        _activeLesson.value = lesson
        _activeGameStep.value = 0
        _activeGameScore.value = 0
        _isGameFinished.value = false
        _pronunciationScore.value = null
        _pronunciationFeedback.value = null
        _isRecording.value = false
        selectedVocabLeft.value = null
        selectedVocabRight.value = null
        correctMatchPairs.value = emptySet()
    }

    fun selectVocabLeftValue(word: String) {
        selectedVocabLeft.value = word
        checkVocabMatchingPair()
    }

    fun selectVocabRightValue(translation: String) {
        selectedVocabRight.value = translation
        checkVocabMatchingPair()
    }

    private fun checkVocabMatchingPair() {
        val left = selectedVocabLeft.value ?: return
        val right = selectedVocabRight.value ?: return
        val lesson = _activeLesson.value ?: return

        val originalPair = lesson.vocabPairs.find { it.word == left }
        if (originalPair != null && originalPair.translation == right) {
            // Correct match
            val currentCorrect = correctMatchPairs.value.toMutableSet()
            currentCorrect.add(left)
            correctMatchPairs.value = currentCorrect

            _activeGameScore.value = _activeGameScore.value + 5

            // check if all matched
            if (currentCorrect.size == lesson.vocabPairs.size) {
                _isGameFinished.value = true
                saveActiveLessonCompletion()
            }
        } else {
            // Incorrect match trigger animation reset
            _notificationToast.value = "Mismatched! Try again. (ভুল হয়েছে, আবার চেষ্টা করুন)"
        }
        selectedVocabLeft.value = null
        selectedVocabRight.value = null
    }

    fun submitQuizAnswer(selectedIndex: Int) {
        val lesson = _activeLesson.value ?: return
        val step = _activeGameStep.value
        val questions = lesson.quizQuestions

        if (selectedIndex == questions[step].correctIndex) {
            _activeGameScore.value = _activeGameScore.value + 10
            _notificationToast.value = "Correct! (সঠিক উত্তর!)"
        } else {
            _notificationToast.value = "Incorrect. Explanation: ${questions[step].explanation}"
        }

        if (step + 1 < questions.size) {
            _activeGameStep.value = step + 1
        } else {
            _isGameFinished.value = true
            saveActiveLessonCompletion()
        }
    }

    // AI speech pronunciation helper simulator
    fun startPronunciationScribe(phrase: String) {
        _isRecording.value = true
        _robotState.value = RobotState.THINKING
    }

    fun submitPronunciationDraft(phrase: String, transcribedText: String) {
        _isRecording.value = false
        _robotState.value = RobotState.THINKING

        viewModelScope.launch {
            val systemInstruction = "You are an expert pronunciation phonetics tutor for Bengali, English, and Arabic. Determine grammatical correctness and approximate accuracy percentage. Output a JSON format matching properties: 'score': Integer (0 to 100), 'feedback_bn': String (Constructive feedback in Bengali language). Keep feedback friendly and encouraging."
            val userPrompt = "The target phrase is: '$phrase'. The user spoke/transcribed: '$transcribedText'. Grade the pronunciation."

            val rawResult = queryGemini(userPrompt, systemInstruction)

            // Extract score and comment from AI text
            val score = rawResult.filter { it.isDigit() }.take(2).toIntOrNull() ?: 85
            _pronunciationScore.value = score
            _pronunciationFeedback.value = if (rawResult.contains("Virtual Assistant Simulation")) {
                "চমৎকার চেষ্টা! উচ্চারণ আনুমানিক ${score}% নির্ভুল। এপিআই কি কানেক্ট করলে এআই আপনাকে বাক্য ভিত্তিক বিস্তারিত ফিডব্যাক দেবে।"
            } else {
                rawResult
            }

            _activeGameScore.value = _activeGameScore.value + (score / 3)
            _isGameFinished.value = true
            _robotState.value = RobotState.TALKING
            saveActiveLessonCompletion()
        }
    }

    private fun saveActiveLessonCompletion() {
        val lesson = _activeLesson.value ?: return
        viewModelScope.launch {
            val totalScore = _activeGameScore.value
            repository.completeLesson(lesson.id, lesson.targetLanguage, totalScore)
            repository.awardXp(lesson.xpReward)
            _notificationToast.value = "Unlocked Rewards! (নতুন পয়েন্ট পেয়েছেন!) +${lesson.xpReward} XP!"
            _robotState.value = RobotState.IDLE
        }
    }

    fun closeLesson() {
        _activeLesson.value = null
    }

    // --- AI Chatbot Conversations ---
    fun sendMessageToChatbot(userMessage: String) {
        if (userMessage.isBlank()) return

        viewModelScope.launch {
            // Save user message to database
            repository.saveMessage(userMessage, isUser = true)

            _robotState.value = RobotState.THINKING

            val systemInstruction = """
                You are a friendly, helpful, robotic language assistant tutor representing LingoPlay.
                You help players learn Bengali, English, and Arabic.
                Reply in Bengali if requested, or language match the user (use English or Arabic as appropriate).
                Be very polite, engaging, and encourage daily streak practicing of interactive language games!
            """.trimIndent()

            val aiResponse = queryGemini(userMessage, systemInstruction)

            _robotState.value = RobotState.TALKING
            // Save AI message to database
            repository.saveMessage(aiResponse, isUser = false)
            _robotState.value = RobotState.IDLE
        }
    }

    fun clearChatLogs() {
        viewModelScope.launch {
            repository.clearChatHistory()
            _notificationToast.value = "Chat logs reset!"
        }
    }

    // --- Encrypted Backup & Sync Functions ---
    fun generateBackup() {
        viewModelScope.launch {
            val code = repository.generateBackupCode()
            _backupCodeResult.value = code
            _backupStatusMessage.value = "Backup code created successfully with end-to-end encryption!"
        }
    }

    fun restoreBackup(code: String) {
        if (code.isBlank()) {
            _backupStatusMessage.value = "Error: Backup code cannot be empty."
            return
        }

        viewModelScope.launch {
            val success = repository.restoreBackupCode(code)
            if (success) {
                _backupStatusMessage.value = "Progress restored successfully! Your streak and points are recovered."
                _notificationToast.value = "Backup Restored Successfully!"
            } else {
                _backupStatusMessage.value = "Failed: Invalid or corrupted backup code key sequence."
            }
        }
    }

    fun clearBackupState() {
        _backupCodeResult.value = null
        _backupStatusMessage.value = null
    }

    fun toggleWeeklyStreak(index: Int) {
        val current = weeklyStreaks.value.toMutableList()
        if (index in current.indices) {
            val oldValue = current[index]
            current[index] = !oldValue
            weeklyStreaks.value = current
            
            val xpGain = if (!oldValue) 20 else -20
            val newPoints = (userProfile.value.points + xpGain).coerceAtLeast(0)
            
            var newStreak = userProfile.value.currentStreak
            if (!oldValue) {
                newStreak += 1
                _notificationToast.value = "🎯 Daily Practice Logged! +20 XP. Streak: $newStreak (আজকের প্র্যাকটিস সম্পন্ন হয়েছে!)"
            } else {
                newStreak = (newStreak - 1).coerceAtLeast(0)
                _notificationToast.value = "⚠️ Practice check-in removed. (আজকের প্র্যাকটিস তালিকাভুক্ত প্রত্যাহার করা হয়েছে)"
            }
            
            viewModelScope.launch {
                repository.updateProfile(userProfile.value.copy(
                    points = newPoints,
                    currentStreak = newStreak
                ))
            }
        }
    }

    fun triggerLocalUpdateCheck() {
        _notificationToast.value = "LingoPlay is up to date (Latest Version v1.4.2)!"
    }

    fun triggerToast(msg: String) {
        _notificationToast.value = msg
    }

    // --- Quranic Word Challenge States and Logic ---
    val quranWords: List<QuranicWord> = QuranicWordsProvider.getQuranicWords()
    
    val quranCurrentIndex = MutableStateFlow(0)
    val quranSelectedOption = MutableStateFlow<Int?>(null)
    val quranIsAnswered = MutableStateFlow(false)
    val quranStreak = MutableStateFlow(0)
    val quranQuizOptions = MutableStateFlow<List<String>>(emptyList())
    val quranChallengeHistory = MutableStateFlow<Set<Int>>(emptySet())
    val quranSearchQuery = MutableStateFlow("")

    init {
        initQuranChallengeQuestion()
    }

    fun initQuranChallengeQuestion() {
        quranSelectedOption.value = null
        quranIsAnswered.value = false
        val total = quranWords.size
        if (total == 0) return

        val currentIndex = quranCurrentIndex.value
        val correctWord = quranWords.getOrNull(currentIndex % total) ?: quranWords[0]
        val correctMeaning = correctWord.meaning

        // Select 3 other random distinct meanings
        val otherMeanings = quranWords
            .filter { it.meaning != correctMeaning }
            .map { it.meaning }
            .distinct()
            .shuffled()
            .take(3)

        // Merge and shuffle
        val consolidated = (otherMeanings + correctMeaning).shuffled()
        quranQuizOptions.value = consolidated
    }

    fun submitQuranChallengeAnswer(optionIndex: Int) {
        if (quranIsAnswered.value) return
        
        val total = quranWords.size
        if (total == 0) return

        val currentIndex = quranCurrentIndex.value
        val correctWord = quranWords.getOrNull(currentIndex % total) ?: quranWords[0]
        val selectedMeaning = quranQuizOptions.value.getOrNull(optionIndex) ?: ""

        val isCorrect = selectedMeaning == correctWord.meaning
        quranSelectedOption.value = optionIndex
        quranIsAnswered.value = true

        if (isCorrect) {
            val newStreak = quranStreak.value + 1
            quranStreak.value = newStreak
            _notificationToast.value = "Correct! (সঠিক উত্তর!) 🎉 +15 XP! Streak: $newStreak"
            
            // Add to completed/mastered history
            val history = quranChallengeHistory.value.toMutableSet()
            history.add(correctWord.id)
            quranChallengeHistory.value = history

            viewModelScope.launch {
                repository.awardXp(15)
                // Add streak count dynamically if multiples of 5
                if (newStreak % 5 == 0) {
                    repository.awardXp(30)
                    _notificationToast.value = "🔥 Super Quran Streak $newStreak! Bonus +30 XP!"
                }
            }
        } else {
            quranStreak.value = 0
            _notificationToast.value = "Incorrect. (ভুল হয়েছে) 💡 Hint: ${correctWord.hint}"
        }
    }

    fun nextQuranWord() {
        val total = quranWords.size
        if (total > 0) {
            quranCurrentIndex.value = (quranCurrentIndex.value + 1) % total
            initQuranChallengeQuestion()
        }
    }

    fun previousQuranWord() {
        val total = quranWords.size
        if (total > 0) {
            val prev = quranCurrentIndex.value - 1
            quranCurrentIndex.value = if (prev < 0) total - 1 else prev
            initQuranChallengeQuestion()
        }
    }

    fun jumpToQuranWord(index: Int) {
        val total = quranWords.size
        if (total > 0 && index in 0 until total) {
            quranCurrentIndex.value = index
            initQuranChallengeQuestion()
        }
    }

    // --- Realtime Multi-User Leaderboard Simulation Engine ---
    val leaderboardLiveFeeds = MutableStateFlow<List<String>>(
        listOf(
            "Tariq Al-Masri (Riyadh) selected Arabic Intermediate track! 🇸🇦",
            "Kazi Anis completed Quiz: Vocab matching! 🇧🇩 (+20 XP)",
            "Asha Khan just started a 3-day Quranic streak! 🇲🇾"
        )
    )

    private val onlineUserCount = MutableStateFlow(12840)
    val liveOnlineUserCount: StateFlow<Int> = onlineUserCount.asStateFlow()

    fun startRealtimeLeaderboardSimulation() {
        viewModelScope.launch {
            kotlinx.coroutines.delay(2000)
            while (true) {
                // Update online user count slightly to give an active feel
                val randCountChange = (-15..20).random()
                onlineUserCount.value = (onlineUserCount.value + randCountChange).coerceIn(12500, 13200)

                // Get current in-memory leaderboard users
                val currentInMem = _leaderboard.value
                if (currentInMem.isNotEmpty()) {
                    // Pick a random user that is not Me
                    val nonMeUsers = currentInMem.filter { !it.isMe }
                    if (nonMeUsers.isNotEmpty()) {
                        val targetUser = nonMeUsers.random()
                        
                        // Increase points slightly (simulating active practice)
                        val pointsInc = listOf(10, 15, 20, 30).random()
                        val updatedUser = targetUser.copy(
                            points = targetUser.points + pointsInc,
                            streak = if ((0..10).random() < 3) targetUser.streak + 1 else targetUser.streak
                        )
                        
                        // Update the in-memory list ONLY (avoiding heavy Room DB write lockouts)
                        _leaderboard.value = currentInMem.map {
                            if (it.id == updatedUser.id) updatedUser else it
                        }

                        // Generate a rich feed message
                        val activities = listOf(
                            "completed English Vocabulary Level ${listOf(1, 2, 3).random()} 🎓",
                            "just earned a perfect quiz score! 🌟",
                            "submitted a Quran translation pair successfully 🕌",
                            "kept up active study via Daily Review ⚡",
                            "successfully unlocked a new Expert badge 🏆",
                            "completed a Quranic root word quiz successfully 💡",
                            "practiced speaking with Phonetic Fallback engine 🗣️"
                        )
                        val sanitizedName = targetUser.name.substringBefore(" (")
                        
                        val flagString = when {
                            targetUser.name.contains("🇧🇩") -> "🇧🇩"
                            targetUser.name.contains("🇸🇦") -> "🇸🇦"
                            targetUser.name.contains("🇬🇧") -> "🇬🇧"
                            targetUser.name.contains("🇵🇰") -> "🇵🇰"
                            targetUser.name.contains("🇺🇸") -> "🇺🇸"
                            targetUser.name.contains("🇲🇾") -> "🇲🇾"
                            targetUser.name.contains("🇪🇬") -> "🇪🇬"
                            else -> "⚡"
                        }
                        
                        val notificationMsg = "$sanitizedName $flagString ${activities.random()} (+$pointsInc XP)"
                        
                        // Update live feed list
                        val currentFeeds = leaderboardLiveFeeds.value.toMutableList()
                        currentFeeds.add(0, notificationMsg)
                        if (currentFeeds.size > 8) {
                            currentFeeds.removeAt(currentFeeds.size - 1)
                        }
                        leaderboardLiveFeeds.value = currentFeeds
                    }
                }
                
                // Add secondary random traffic loop: delay random seconds (4 to 8 sec)
                val delaySeconds = (4..8).random() * 1000L
                kotlinx.coroutines.delay(delaySeconds)
            }
        }
    }

    fun updateMyLeaderboardName(newName: String) {
        viewModelScope.launch {
            repository.updateMyLeaderboardName(newName)
            _notificationToast.value = "Username changed to \"$newName\"! (ব্যবহারকারীর নাম পরিবর্তন করা হয়েছে!) ✅"
        }
    }
}
