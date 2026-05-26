package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.animation.core.*
import androidx.compose.ui.graphics.graphicsLayer
import com.example.data.*
import com.example.ui.MainViewModel
import com.example.ui.theme.*

// --- Reactive Localization Translator Objects ---
object Translator {
    private val translations = mapOf(
        "welcome" to mapOf("BN" to "স্বাগতম!", "EN" to "Welcome!", "AR" to "أهلاً بك!"),
        "streak" to mapOf("BN" to "ডেইলি স্ট্রিক", "EN" to "Daily Streak", "AR" to "الخط اليومي"),
        "points" to mapOf("BN" to "মোট পয়েন্ট", "EN" to "Total Points", "AR" to "مجموع النقاط"),
        "games" to mapOf("BN" to "শিক্ষামূলক গেম", "EN" to "Learning Games", "AR" to "الألعاب والمرح"),
        "chatbot" to mapOf("BN" to "এআই টিউটর চ্যাট", "EN" to "AI Chatbot Tutor", "AR" to "مساعد الذكاء الاصطناعي"),
        "leaderboard" to mapOf("BN" to "লিডারবোর্ড", "EN" to "Leaderboard", "AR" to "لوحة الصدارة"),
        "plan" to mapOf("BN" to "পার্সোনাল প্ল্যান", "EN" to "Personalized Plan", "AR" to "خطة التعلم"),
        "settings" to mapOf("BN" to "কন্ট্রোল প্যানেল", "EN" to "Control Panel", "AR" to "لوحة التحكم"),
        "native_lang" to mapOf("BN" to "আমার মাতৃভাষা", "EN" to "My Native Language", "AR" to "لغتي الأم"),
        "target_lang" to mapOf("BN" to "আমি শিখতে চাই", "EN" to "Language to Learn", "AR" to "أريد أن أتعلم"),
        "encryption" to mapOf("BN" to "এন্ড-টু-এন্ড এনক্রিপশন (E2EE)", "EN" to "End-to-End Encryption", "AR" to "التشفير التام (E2EE)"),
        "cloud_sync" to mapOf("BN" to "অফলাইন-ক্লাউড সিনক্রোনাইজেশন", "EN" to "Offline Cloud Synchronization", "AR" to "مزامنة السحاب"),
        "backup_export" to mapOf("BN" to "সিকিউর ডাটা ব্যাকআপ", "EN" to "Secure Data Backup", "AR" to "نسخة احتياطية آمنة"),
        "restore_import" to mapOf("BN" to "ডাটা রিকভারি/রিস্টোর", "EN" to "Restore Progress Key", "AR" to "استعادة البيانات"),
        "play" to mapOf("BN" to "গেম খেলুন", "EN" to "Play Game", "AR" to "ابدأ اللعب"),
        "level" to mapOf("BN" to "দক্ষতার লেভেল", "EN" to "Skill Level", "AR" to "مستوى المهارة"),
        "goal" to mapOf("BN" to "দৈনিক লক্ষ্য", "EN" to "Study Goal", "AR" to "الهدف اليومي"),
        "tap_robot" to mapOf("BN" to "রোবটের সাথে কথা বলুন!", "EN" to "Tap the Robot to converse!", "AR" to "اضغط على الروبوت للتحدث!"),
        "practice" to mapOf("BN" to "নিয়মিত অনুশীলন করুন এবং লিডারবোর্ডে এগিয়ে যান!", "EN" to "Practice regularly and gain points!", "AR" to "تدرب بانتظام للتصدر وحصد النقاط!")
    )

    fun get(key: String, lang: String): String {
        return translations[key]?.get(lang) ?: key
    }
}

@Composable
fun DashboardScreen(viewModel: MainViewModel) {
    val profile by viewModel.userProfile.collectAsState()
    val rState by viewModel.robotState.collectAsState()
    val progressList by viewModel.lessonProgress.collectAsState()

    val currentLang = profile.nativeLanguage
    val targetLang = profile.targetLanguage

    val completedCount = progressList.filter { it.isCompleted }.size

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Core Robotic Virtual Assistant Header Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(1.dp, Brush.linearGradient(listOf(Color.White.copy(alpha = 0.22f), Color.White.copy(alpha = 0.04f)))),
                modifier = Modifier.fillMaxWidth().testTag("robot_advisor_card")
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Robot Assistant Custom drawing with blinking and talking state
                    RobotAssistant(
                        state = rState,
                        modifier = Modifier.size(170.dp),
                        onClick = {
                            viewModel.navigateTo("CHATBOT")
                        }
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = Translator.get("welcome", currentLang) + " I'm your language companion bot!",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )

                    Text(
                        text = Translator.get("tap_robot", currentLang),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // Streak & XP Dashboard Metrics Cards Row
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Streak Card
                Card(
                    modifier = Modifier.weight(1f).testTag("streak_count_card"),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    border = BorderStroke(1.dp, Brush.linearGradient(listOf(Color.White.copy(alpha = 0.18f), Color.White.copy(alpha = 0.02f)))),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Streak FireIcon",
                            tint = Color(0xFFFF5722),
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "${profile.currentStreak} 🔥",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = Translator.get("streak", currentLang),
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                    }
                }

                // Points Card
                Card(
                    modifier = Modifier.weight(1f).testTag("points_rewards_card"),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    border = BorderStroke(1.dp, Brush.linearGradient(listOf(Color.White.copy(alpha = 0.18f), Color.White.copy(alpha = 0.02f)))),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Points Medal Icon",
                            tint = Color(0xFFFFCC00),
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "${profile.points} XP",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = Translator.get("points", currentLang),
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }

        // Interactive Stats and 7-Day Streaks Calendar Card (Feature 5)
        item {
            Card(
                modifier = Modifier.fillMaxWidth().testTag("interactive_stats_card"),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, Brush.linearGradient(listOf(Color.White.copy(alpha = 0.18f), Color.White.copy(alpha = 0.02f)))),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "📊 Weekly Practice Streak & Stats Calendar",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    
                    // Stats grid Row
                    val masteryCount by viewModel.vocabularyMastery.collectAsState()
                    val accRate by viewModel.accuracyRate.collectAsState()
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Accuracy Rate", fontSize = 11.sp, color = Color.Gray)
                            Text("$accRate%", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF00FFCC))
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Phrases Mastered", fontSize = 11.sp, color = Color.Gray)
                            Text("$masteryCount Words", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFFFFCC00))
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("Daily Activity", fontSize = 11.sp, color = Color.Gray)
                            Text("100% Active", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF00FFFF))
                        }
                    }
                    Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    // 7 days checklist horizontal grid
                    Text("Check-In Practice Calendar:", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 8.dp))
                    val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
                    val hasPracticedList by viewModel.weeklyStreaks.collectAsState()
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        days.forEachIndexed { index, day ->
                            val done = hasPracticedList.getOrElse(index) { false }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(day, fontSize = 11.sp, color = Color.Gray)
                                Spacer(modifier = Modifier.height(4.dp))
                                Box(
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(CircleShape)
                                        .background(if (done) Color(0xFF10B981) else Color.Transparent)
                                        .border(1.dp, if (done) Color.Transparent else Color.Gray, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (done) {
                                        Icon(imageVector = Icons.Default.Check, contentDescription = "", tint = Color.White, modifier = Modifier.size(16.dp))
                                    } else {
                                        Text("${index+1}", fontSize = 10.sp, color = Color.Gray)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Active target language, level, and goals progress indicators
        item {
            Card(
                modifier = Modifier.fillMaxWidth().testTag("plan_overview_card"),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, Brush.linearGradient(listOf(Color.White.copy(alpha = 0.18f), Color.White.copy(alpha = 0.02f)))),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "🎯 Learning Track Details",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Divider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.outlineVariant)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Current Language Goal:", fontSize = 14.sp)
                        AssistChip(
                            onClick = {},
                            label = { Text("${profile.nativeLanguage} ➔ ${profile.targetLanguage}") },
                            leadingIcon = { Icon(Icons.Default.Info, contentDescription = "", modifier = Modifier.size(16.dp)) }
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Difficulty level:", fontSize = 14.sp)
                        Badge {
                            Text(profile.skillLevel, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp), fontSize = 12.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Daily Commitment Goal:", fontSize = 14.sp)
                        Text(profile.learningGoal, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.secondary)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Progress chart simulation bar representation
                    Text("Target Completed Games checklist count:", fontSize = 13.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(6.dp))
                    LinearProgressIndicator(
                        progress = { if (completedCount > 0) completedCount.toFloat() / 9f else 0f },
                        modifier = Modifier.fillMaxWidth().height(10.dp).clip(CircleShape),
                        color = Color(0xFF00FFCC),
                        trackColor = MaterialTheme.colorScheme.outlineVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "$completedCount of 9 offline courses unlocked completed.",
                        fontSize = 11.sp,
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.Gray
                    )
                }
            }
        }

        // Security / Offline Sync visual prompt
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Brush.linearGradient(listOf(Color.White.copy(alpha = 0.18f), Color.White.copy(alpha = 0.02f)))),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.2f))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (profile.isE2eEncrypted) Icons.Default.Lock else Icons.Default.Lock,
                        contentDescription = "Security Status",
                        tint = if (profile.isE2eEncrypted) Color(0xFF00FFCC) else Color.Gray,
                        modifier = Modifier.size(36.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = if (profile.isE2eEncrypted) "E2E Encryption Protection Active" else "Local Database Mode Enabled",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Text(
                            text = "Secure local backup can be synchronized to other devices.",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                }
            }
        }
    }
}

// --- Games Screen layout implementation ---
@Composable
fun GamesScreen(viewModel: MainViewModel) {
    val profile by viewModel.userProfile.collectAsState()
    val activeLesson by viewModel.activeLesson.collectAsState()
    val currentLang = profile.nativeLanguage

    if (activeLesson != null) {
        ActiveGamePlayArea(viewModel = viewModel, lesson = activeLesson!!)
    } else {
        var selectedSubTab by remember { mutableStateOf("LESSONS") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Screen Header title
            Text(
                text = Translator.get("games", currentLang),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 6.dp)
            )

            Text(
                text = "Select interactive lessons, practice with custom flashcards, or look up terms in the searchable bilingual glossary below:",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Pristine segment selection row styled in Frosted glass
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(
                    "LESSONS" to "🎓 Lessons (" + Translator.get("games", currentLang).split(" ")[0] + ")",
                    "FLASHCARDS" to "🎴 Flashcards",
                    "DICTIONARY" to "📚 Dictionary"
                ).forEach { (tabCode, label) ->
                    val isSelected = selectedSubTab == tabCode
                    Button(
                        onClick = { selectedSubTab = tabCode },
                        modifier = Modifier.weight(1f).height(40.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                            contentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(label, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Render selected Sub-Section
            when (selectedSubTab) {
                "LESSONS" -> {
                    val targetsList = StaticLessons.lessonsList.filter { it.targetLanguage == profile.targetLanguage }

                    if (targetsList.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(
                                text = "No offline games configured for learning ${profile.targetLanguage} currently!\nGo to Control Panel and change target language.",
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Medium,
                                color = Color.Gray
                            )
                        }
                    } else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxSize().testTag("lessons_scrollview")
                        ) {
                            items(targetsList) { lesson ->
                                GameLessonCard(lesson = lesson, onPlay = { viewModel.selectLesson(lesson) })
                            }
                        }
                    }
                }
                "FLASHCARDS" -> {
                    // Feature 4: Interactive Flashcards Training Deck Screen
                    val flashcardPool = StaticLessons.lessonsList
                        .filter { it.targetLanguage == profile.targetLanguage && it.type == LessonType.VOCABULARY }
                        .flatMap { it.vocabPairs }

                    val deck = if (flashcardPool.isNotEmpty()) flashcardPool else listOf(
                        VocabPair("Water", "পানি", "ওয়াটার"),
                        VocabPair("Book", "বই", "বুক"),
                        VocabPair("House", "বাড়ি", "হাউজ"),
                        VocabPair("Friend", "বন্ধু", "ফ্রেন্ড"),
                        VocabPair("School", "স্কুল", "স্কুল")
                    )

                    val currentIndex by viewModel.currentFlashCardIndex.collectAsState()
                    val flipped by viewModel.isFlashCardFlipped.collectAsState()

                    val currentWord = deck.getOrNull(currentIndex % deck.size) ?: deck[0]

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .testTag("flashcards_game_arena"),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Card ${currentIndex + 1} of ${deck.size} • Memorization Mode",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        // 3D-like flip card layout
                        Card(
                            onClick = { viewModel.flipFlashCard() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp)
                                .testTag("flashcard_item_flipper"),
                            shape = RoundedCornerShape(24.dp),
                            border = BorderStroke(1.dp, Brush.linearGradient(listOf(Color.White.copy(alpha = 0.2f), Color.White.copy(alpha = 0.02f)))),
                            colors = CardDefaults.cardColors(
                                containerColor = if (flipped) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f) else MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
                            )
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(24.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    if (!flipped) {
                                        Text(
                                            text = "Target Term (শিখুন):",
                                            fontSize = 11.sp,
                                            color = Color.Gray,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                        Spacer(modifier = Modifier.height(16.dp))
                                        Text(
                                            text = currentWord.word,
                                            fontSize = 28.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Spacer(modifier = Modifier.height(16.dp))
                                        Text(
                                            text = "👆 Tap to see translation (অর্থ দেখুন)",
                                            fontSize = 11.sp,
                                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                                        )
                                    } else {
                                        Text(
                                            text = "Translation & Phonetics (বাংলা অর্থ):",
                                            fontSize = 11.sp,
                                            color = Color.Gray,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                        Spacer(modifier = Modifier.height(16.dp))
                                        Text(
                                            text = currentWord.translation,
                                            fontSize = 26.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF00FFCC)
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = "Pronounce: ${currentWord.pronunciation}",
                                            fontSize = 14.sp,
                                            fontStyle = FontStyle.Italic,
                                            color = Color.White.copy(alpha = 0.8f)
                                        )
                                        Spacer(modifier = Modifier.height(16.dp))
                                        IconButton(onClick = { viewModel.speakText(currentWord.word) }) {
                                            Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Hear audio", tint = Color.Yellow)
                                        }
                                    }
                                }
                            }
                        }

                        // Playback and step controls
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = { viewModel.previousFlashCard(deck.size) },
                                modifier = Modifier.size(48.dp).background(MaterialTheme.colorScheme.surfaceVariant, CircleShape).testTag("flashcard_prev_btn")
                            ) {
                                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Previous Card", tint = MaterialTheme.colorScheme.primary)
                            }

                            Button(
                                onClick = { viewModel.flipFlashCard() },
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                                modifier = Modifier.height(48.dp).testTag("flashcard_flip_btn")
                            ) {
                                Text(if (flipped) "Hide Translation" else "View Translation", fontWeight = FontWeight.Bold)
                            }

                            IconButton(
                                onClick = { viewModel.nextFlashCard(deck.size) },
                                modifier = Modifier.size(48.dp).background(MaterialTheme.colorScheme.surfaceVariant, CircleShape).testTag("flashcard_next_btn")
                            ) {
                                Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Next Card", tint = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                }
                "DICTIONARY" -> {
                    // Feature 2: Searchable & Favoritable Vocabulary Dictionary & bookmark stars
                    var query by remember { mutableStateOf("") }
                    val starList by viewModel.bookmarkedWords.collectAsState()
                    var showOnlyStarred by remember { mutableStateOf(false) }

                    val dictionaryGlossary = listOf(
                        VocabPair("Apple", "আপেল", "Ap-ul"),
                        VocabPair("Water", "পানি / জল", "Wa-ter"),
                        VocabPair("Book", "বই / কিতাব", "Boi"),
                        VocabPair("House", "ঘর / বাড়ি", "Hou-se"),
                        VocabPair("Friend", "বন্ধু", "Frend"),
                        VocabPair("School", "বিদ্যালয় / স্কুল", "Skul"),
                        VocabPair("Good Morning", "শুভ সকাল", "Gud Mor-ning"),
                        VocabPair("Thank you", "ধন্যবাদ", "Thenk yu"),
                        VocabPair("مَرْحَبًا", "হ্যালো / স্বাগতম (Marhaban)", "Marhaban"),
                        VocabPair("شُكْرًا", "ধন্যবাদ (Shukran)", "Shukran"),
                        VocabPair("كِتَابٌ", "বই / কিতাবুন", "Kitabun"),
                        VocabPair("مَاءٌ", "পানি (Ma'un)", "Ma'un"),
                        VocabPair("بَيْتٌ", "ঘর / বাড়ি (Baytun)", "Baytun"),
                        VocabPair("Language", "ভাষা (Bhasha)", "Bha-sha"),
                        VocabPair("Sun", "সূর্য (Shurjo)", "Shur-jo"),
                        VocabPair("Moon", "চন্দ্র / চাঁদ (Chondro)", "Chon-dro"),
                        VocabPair("Bird", "পাখি (Pakhi)", "Pa-khi"),
                        VocabPair("Fruit", "ফল (Fol)", "Fol")
                    )

                    val filteredList = dictionaryGlossary.filter { pair ->
                        val matchesQuery = pair.word.contains(query, ignoreCase = true) ||
                                pair.translation.contains(query, ignoreCase = true)
                        val matchesStars = !showOnlyStarred || starList.contains(pair.word)
                        matchesQuery && matchesStars
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .testTag("searchable_dictionary_view"),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Search text box
                        OutlinedTextField(
                            value = query,
                            onValueChange = { query = it },
                            modifier = Modifier.fillMaxWidth().testTag("dictionary_search_textfield"),
                            label = { Text("Search 100+ glossary terms... (অনুসন্ধান করুন)") },
                            shape = RoundedCornerShape(12.dp),
                            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Search icon") },
                            trailingIcon = {
                                if (query.isNotBlank()) {
                                    IconButton(onClick = { query = "" }) {
                                        Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear search query")
                                    }
                                }
                            }
                        )

                        // Stars toggle row
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Matching results: ${filteredList.size} nouns",
                                fontSize = 11.sp,
                                color = Color.Gray,
                                fontWeight = FontWeight.Bold
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable { showOnlyStarred = !showOnlyStarred }
                            ) {
                                Text("Only Stars ⭐", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                                Spacer(modifier = Modifier.width(6.dp))
                                Checkbox(
                                    checked = showOnlyStarred,
                                    onCheckedChange = { showOnlyStarred = it },
                                    modifier = Modifier.testTag("dictionary_stars_checkbox")
                                )
                            }
                        }

                        // Dictionary List
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.weight(1f).fillMaxWidth().testTag("dictionary_scrollview")
                        ) {
                            if (filteredList.isEmpty()) {
                                item {
                                    Box(
                                        modifier = Modifier.fillMaxWidth().padding(40.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            "No glossary records matched standard filters!\n(কোনো शब्द খুঁজে পাওয়া যায়নি)",
                                            textAlign = TextAlign.Center,
                                            fontSize = 12.sp,
                                            color = Color.Gray
                                        )
                                    }
                                }
                            } else {
                                items(filteredList) { item ->
                                    val isStarred = starList.contains(item.word)
                                    Card(
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(12.dp),
                                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)),
                                        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.08f))
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(12.dp).fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Column(modifier = Modifier.weight(1f)) {
                                                Text(
                                                    text = item.word,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 15.sp,
                                                    color = MaterialTheme.colorScheme.onSurface
                                                )
                                                Text(
                                                    text = "Bengali meaning: ${item.translation}",
                                                    fontSize = 12.sp,
                                                    color = Color(0xFF00FFCC)
                                                )
                                                Text(
                                                    text = "Pronounce guide: ${item.pronunciation}",
                                                    fontSize = 11.sp,
                                                    color = Color.LightGray.copy(alpha = 0.7f),
                                                    fontStyle = FontStyle.Italic
                                                )
                                            }

                                            // Speak pronunciation (Integrated Audio Player)
                                            IconButton(onClick = { viewModel.speakText(item.word) }) {
                                                Icon(
                                                    imageVector = Icons.Default.PlayArrow,
                                                    contentDescription = "Speak term out loud",
                                                    tint = MaterialTheme.colorScheme.primary
                                                )
                                            }

                                            // Bookmark Star
                                            IconButton(onClick = { viewModel.toggleBookmark(item.word) }) {
                                                Icon(
                                                    imageVector = Icons.Default.Star,
                                                    contentDescription = "Bookmark",
                                                    tint = if (isStarred) Color(0xFFFFCC00) else Color.Gray.copy(alpha = 0.4f),
                                                    modifier = Modifier.testTag("star_${item.word}")
                                                )
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
}

@Composable
fun GameLessonCard(lesson: Lesson, onPlay: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onPlay() }
            .testTag("lesson_card_${lesson.id}"),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)),
        border = BorderStroke(1.dp, Brush.linearGradient(listOf(Color.White.copy(alpha = 0.16f), Color.White.copy(alpha = 0.02f)))),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon representing lesson type
            val (icon, tint) = when (lesson.type) {
                LessonType.VOCABULARY -> Pair(Icons.Default.Info, Color(0xFF00FFCC))
                LessonType.QUIZ -> Pair(Icons.Default.Star, Color(0xFFFFCC00))
                LessonType.PRONUNCIATION -> Pair(Icons.Default.PlayArrow, Color(0xFF00FFFF))
            }

            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(tint.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = "", tint = tint, modifier = Modifier.size(28.dp))
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = lesson.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = lesson.description,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Badge(containerColor = MaterialTheme.colorScheme.primaryContainer) {
                        Text(lesson.difficulty, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 4.dp))
                    }
                    Badge(containerColor = Color(0xFFFFCC00).copy(alpha = 0.2f), contentColor = Color(0xFFFFB300)) {
                        Text("+${lesson.xpReward} XP", fontSize = 10.sp, modifier = Modifier.padding(horizontal = 4.dp))
                    }
                }
            }

            IconButton(onClick = onPlay) {
                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Play Lesson Button", tint = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

// --- Active Game Playing Canvas Area ---
@Composable
fun ActiveGamePlayArea(viewModel: MainViewModel, lesson: Lesson) {
    val step by viewModel.activeGameStep.collectAsState()
    val score by viewModel.activeGameScore.collectAsState()
    val isFinished by viewModel.isGameFinished.collectAsState()

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("active_game_canvas"),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, Brush.linearGradient(listOf(Color.White.copy(alpha = 0.20f), Color.White.copy(alpha = 0.03f)))),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header: Lesson Title + Close button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = lesson.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                IconButton(onClick = { viewModel.closeLesson() }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close Game Play area")
                }
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // Running Score bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Earned: $score pts", fontWeight = FontWeight.SemiBold, color = Color(0xFFFFCC00))
                Text("Lesson Type: ${lesson.type.name}", fontSize = 12.sp, color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isFinished) {
                // Celebration Area
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Trophy Winner",
                        tint = Color(0xFFFFD700),
                        modifier = Modifier.size(100.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "অভিনন্দন! গেম শেষ হয়েছে।",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF00FFCC)
                    )
                    Text(
                        text = "Congratulations! Game completed successfully.",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Score Earned: $score Points\nRewards Redeemed: +${lesson.xpReward} Streak XP",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        lineHeight = 22.sp
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    Button(
                        onClick = { viewModel.closeLesson() },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("ড্যাশবোর্ডে ফিরে যান (Back to Dashboard)")
                    }
                }
            } else {
                // Interactive play screens depending on game types
                Box(modifier = Modifier.weight(1f)) {
                    when (lesson.type) {
                        LessonType.VOCABULARY -> MatchCardGameLayout(viewModel, lesson)
                        LessonType.QUIZ -> QuizGameLayout(viewModel, lesson, step)
                        LessonType.PRONUNCIATION -> PronounceGameLayout(viewModel, lesson)
                    }
                }
            }
        }
    }
}

// 1. Matching Pairs layout structure
@Composable
fun MatchCardGameLayout(viewModel: MainViewModel, lesson: Lesson) {
    val leftSelected by viewModel.selectedVocabLeft.collectAsState()
    val rightSelected by viewModel.selectedVocabRight.collectAsState()
    val correctMatches by viewModel.correctMatchPairs.collectAsState()

    // Scramble lists internally
    val leftWords = remember(lesson) { lesson.vocabPairs.map { it.word }.shuffled() }
    val rightWords = remember(lesson) { lesson.vocabPairs.map { it.translation }.shuffled() }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "শব্দ মিল করুন! বাম প্যানেল থেকে শব্দ বেছে নিন এবং ডানে তার বঙ্গানুবাদ/অনুবাদ সিলেক্ট করুন:",
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
        )

        Row(
            modifier = Modifier.fillMaxWidth().weight(1f),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Left words column
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                leftWords.forEach { word ->
                    val isMatched = correctMatches.contains(word)
                    val isSelected = leftSelected == word

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                when {
                                    isMatched -> Color(0xFF00FFCC).copy(alpha = 0.2f)
                                    isSelected -> MaterialTheme.colorScheme.primaryContainer
                                    else -> MaterialTheme.colorScheme.surfaceVariant
                                }
                            )
                            .border(
                                width = 2.dp,
                                color = when {
                                    isMatched -> Color(0xFF00FFCC)
                                    isSelected -> MaterialTheme.colorScheme.primary
                                    else -> Color.Transparent
                                },
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable(enabled = !isMatched) { viewModel.selectVocabLeftValue(word) }
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = word,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = if (isMatched) Color.Gray else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            // Right translations column
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                rightWords.forEach { trans ->
                    // Find corresponding target word to check matching
                    val matchItem = lesson.vocabPairs.find { it.translation == trans }
                    val isMatched = matchItem != null && correctMatches.contains(matchItem.word)
                    val isSelected = rightSelected == trans

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                when {
                                    isMatched -> Color(0xFF00FFCC).copy(alpha = 0.2f)
                                    isSelected -> MaterialTheme.colorScheme.primaryContainer
                                    else -> MaterialTheme.colorScheme.surfaceVariant
                                }
                            )
                            .border(
                                width = 2.dp,
                                color = when {
                                    isMatched -> Color(0xFF00FFCC)
                                    isSelected -> MaterialTheme.colorScheme.primary
                                    else -> Color.Transparent
                                },
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable(enabled = !isMatched) { viewModel.selectVocabRightValue(trans) }
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = trans,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = if (isMatched) Color.Gray else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}

// 2. Multiple choice quiz layout structure
@Composable
fun QuizGameLayout(viewModel: MainViewModel, lesson: Lesson, step: Int) {
    val question = lesson.quizQuestions.getOrNull(step) ?: return

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Step progress tracker
        Text(
            text = "Question ${step + 1} of ${lesson.quizQuestions.size}",
            fontSize = 12.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Bold
        )

        // Question display card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = question.text,
                modifier = Modifier.padding(16.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Multiple choice list buttons
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            question.options.forEachIndexed { index, option ->
                Button(
                    onClick = { viewModel.submitQuizAnswer(index) },
                    modifier = Modifier.fillMaxWidth().height(54.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f), contentColor = MaterialTheme.colorScheme.onSurface)
                ) {
                    Text(option, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                }
            }
        }
    }
}

// 3. AI based pronunciation practice layout
@Composable
fun PronounceGameLayout(viewModel: MainViewModel, lesson: Lesson) {
    val phrases = lesson.pronunciationPhrases
    val step by viewModel.activeGameStep.collectAsState()
    val isRecording by viewModel.isRecording.collectAsState()
    val scoreText by viewModel.pronunciationScore.collectAsState()
    val feedbackText by viewModel.pronunciationFeedback.collectAsState()

    val wordToPractice = phrases.getOrNull(step % phrases.size) ?: "Practice makes perfect."

    var textInputSpeechSimulation by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth().testTag("pronounce_phrase_card"),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.2f)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("📢 Phrase to Pronounce (উচ্চারণ করুন):", fontSize = 13.sp, color = MaterialTheme.colorScheme.tertiary)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "\" $wordToPractice \"",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Button(
                        onClick = { viewModel.speakText(wordToPractice) },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.testTag("hear_pronunciation_button")
                    ) {
                        Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Hear vocal guide")
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Hear Pronunciation Guide (শুনুন)")
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Vocal Playback Speed (গতি নিয়ন্ত্রণ):", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                    Spacer(modifier = Modifier.height(6.dp))
                    
                    val sRate by viewModel.speechRate.collectAsState()
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        listOf(0.5f to "0.5x turtle", 0.75f to "0.75x slow", 1.0f to "1.0x normal", 1.5f to "1.5x fast").forEach { (speed, label) ->
                            FilterChip(
                                selected = sRate == speed,
                                onClick = { viewModel.speechRate.value = speed },
                                label = { Text(label, fontSize = 10.sp) },
                                modifier = Modifier.testTag("speed_chip_$speed")
                            )
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "AI Voice Evaluator is listening to your microphone... If your device microphone is occupied or offline, simulate voice typing below:",
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                color = Color.Gray
            )
        }

        // Voice recorder UI simulation with nice sound wave visual
        item {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(if (isRecording) Color.Red.copy(alpha = 0.2f) else MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                    .clickable {
                        if (!isRecording) {
                            textInputSpeechSimulation = wordToPractice // quick auto fill simulation for comfort
                            viewModel.startPronunciationScribe(wordToPractice)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                // Sound waves pulsating
                if (isRecording) {
                    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
                    val pulseScale by infiniteTransition.animateFloat(
                        initialValue = 1f,
                        targetValue = 1.4f,
                        animationSpec = infiniteRepeatable(tween(600, easing = EaseInOutSine), RepeatMode.Reverse),
                        label = "pulse_scale"
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer(scaleX = pulseScale, scaleY = pulseScale)
                            .border(2.dp, Color.Red, CircleShape)
                    )
                }

                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Mic Trigger Button",
                    tint = if (isRecording) Color.Red else MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(44.dp)
                )
            }
        }

        item {
            Text(
                text = if (isRecording) "Listening... Tap mic again to stop." else "Tap target mic to dictate pronunciation",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (isRecording) Color.Red else MaterialTheme.colorScheme.onSurface
            )
        }

        // Manual text simulation typing box for easy evaluation
        item {
            OutlinedTextField(
                value = textInputSpeechSimulation,
                onValueChange = { textInputSpeechSimulation = it },
                label = { Text("Dictated Speech transcript...") },
                modifier = Modifier.fillMaxWidth().testTag("speech_dictation_box"),
                shape = RoundedCornerShape(12.dp)
            )
        }

        item {
            Button(
                onClick = {
                    val promptText = if (textInputSpeechSimulation.isNotBlank()) textInputSpeechSimulation else wordToPractice
                    viewModel.submitPronunciationDraft(wordToPractice, promptText)
                },
                enabled = textInputSpeechSimulation.isNotBlank() || isRecording,
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Analyze Pronunciation via AI (উচ্চারণ বিশ্লেষণ)")
            }
        }

        if (feedbackText != null) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "📊 AI Pronunciation Analytics Score: $scoreText%",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = feedbackText!!,
                            fontSize = 13.sp,
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }
    }
}

// --- Leaderboard Competition Screen ---
@Composable
fun LeaderboardScreen(viewModel: MainViewModel) {
    val leaderboardValues by viewModel.leaderboard.collectAsState()
    val profile by viewModel.userProfile.collectAsState()
    val currentLang = profile.nativeLanguage

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = Translator.get("leaderboard", currentLang),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        Text(
            text = Translator.get("practice", currentLang),
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Display ranking podium banner for Top 3
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            val sortedList = leaderboardValues.sortedByDescending { it.points }

            // 2nd Place
            sortedList.getOrNull(1)?.let { u2 ->
                PodiumColumn(u = u2, rank = 2, height = 80.dp)
            }

            // 1st Place
            sortedList.getOrNull(0)?.let { u1 ->
                PodiumColumn(u = u1, rank = 1, height = 110.dp)
            }

            // 3rd Place
            sortedList.getOrNull(2)?.let { u3 ->
                PodiumColumn(u = u3, rank = 3, height = 65.dp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Ranking list below podium
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize().testTag("leaderboard_list_scroll")
        ) {
            items(leaderboardValues) { rankUser ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = if (rankUser.isMe) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                    ),
                    border = BorderStroke(1.dp, Brush.linearGradient(listOf(Color.White.copy(alpha = 0.16f), Color.White.copy(alpha = 0.02f)))),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (rankUser.isMe) "★" else "•",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.width(24.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = rankUser.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Text(
                                text = "Streak count: ${rankUser.streak} ⚡",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        }

                        Text(
                            text = "${rankUser.points} XP",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PodiumColumn(u: com.example.data.LeaderboardUser, rank: Int, height: androidx.compose.ui.unit.Dp) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        val medalColor = when (rank) {
            1 -> Color(0xFFFFD700)
            2 -> Color(0xFFC0C0C0)
            else -> Color(0xFFCD7F32)
        }

        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "",
            tint = medalColor,
            modifier = Modifier.size(28.dp)
        )
        Text(u.name.split(" ")[0], fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
        Text("${u.points} XP", fontSize = 11.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .width(65.dp)
                .height(height)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(medalColor.copy(alpha = 0.55f), medalColor.copy(alpha = 0.15f))
                    ),
                    shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                )
                .border(
                    width = 1.dp,
                    brush = Brush.linearGradient(listOf(Color.White.copy(alpha = 0.3f), Color.White.copy(alpha = 0.05f))),
                    shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "#$rank",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
    }
}

// --- Personal plan configure section ---
@Composable
fun PersonalPlanScreen(viewModel: MainViewModel) {
    val profile by viewModel.userProfile.collectAsState()
    val currentLang = profile.nativeLanguage

    var planLevel by remember { mutableStateOf(profile.skillLevel) }
    var planGoal by remember { mutableStateOf(profile.learningGoal) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = Translator.get("plan", currentLang),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Setup your automated daily personal study plan configuration:",
                fontSize = 13.sp,
                color = Color.Gray
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth().testTag("plan_setup_controls"),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Brush.linearGradient(listOf(Color.White.copy(alpha = 0.16f), Color.White.copy(alpha = 0.02f)))),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Select Skill Level Difficulty:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("Beginner", "Intermediate", "Advanced").forEach { level ->
                            FilterChip(
                                selected = planLevel == level,
                                onClick = { planLevel = level },
                                label = { Text(level) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Select Daily Commitment Target XP:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("Casual", "Regular", "Serious").forEach { goal ->
                            FilterChip(
                                selected = planGoal == goal,
                                onClick = { planGoal = goal },
                                label = { Text(goal) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = { viewModel.updatePersonalizedPlan(planLevel, planGoal) },
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Save Plan Configuration (সংরক্ষণ করুন)")
                    }
                }
            }
        }

        // Smart alarm practice reminder scheduler (Feature 3)
        item {
            val isEnabled by viewModel.isReminderEnabled.collectAsState()
            val timeSelected by viewModel.reminderTime.collectAsState()
            var inputTime by remember { mutableStateOf(timeSelected) }
            
            Card(
                modifier = Modifier.fillMaxWidth().testTag("practice_reminder_card"),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Brush.linearGradient(listOf(Color.White.copy(alpha = 0.16f), Color.White.copy(alpha = 0.02f)))),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("⏰ Smart Daily Practice alarm & reminder", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Text("Receive automated local alarms checking your streak countdown timers.", fontSize = 11.sp, color = Color.Gray)
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Reminder Time Selected:", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                            Text(timeSelected, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        }
                        
                        Switch(
                            checked = isEnabled,
                            onCheckedChange = { viewModel.setReminder(it, inputTime) },
                            modifier = Modifier.testTag("reminder_active_switch")
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Modify Alert Target Time (e.g. 09:30 PM):", fontSize = 12.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(6.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = inputTime,
                            onValueChange = { inputTime = it },
                            modifier = Modifier.weight(1f).height(50.dp).testTag("reminder_time_textfield"),
                            placeholder = { Text("08:00 PM") },
                            shape = RoundedCornerShape(8.dp),
                            textStyle = MaterialTheme.typography.bodyMedium
                        )
                        
                        Button(
                            onClick = { viewModel.setReminder(true, inputTime) },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.height(50.dp).testTag("reminder_save_button")
                        ) {
                            Text("Set Alert", fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        item {
            Text("Automated Daily Study Schedule Items:", fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }

        // List showing details of the personalized course schedule
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Brush.linearGradient(listOf(Color.White.copy(alpha = 0.16f), Color.White.copy(alpha = 0.02f)))),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "", tint = Color(0xFF00FFCC))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("1. Basics Vocabulary drill (20 XP)", fontWeight = FontWeight.SemiBold)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "", tint = Color(0xFF00FFCC))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("2. Chat with AI Tutor Assistant (15 XP)", fontWeight = FontWeight.SemiBold)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Info, contentDescription = "", tint = Color.Gray)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("3. Pronunciation training feedback (40 XP)", fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

// --- Live AI Chatbot Screen Layout ---
@Composable
fun ChatbotScreen(viewModel: MainViewModel) {
    val rState by viewModel.robotState.collectAsState()
    val chatLogs by viewModel.chatHistory.collectAsState()
    val profile by viewModel.userProfile.collectAsState()
    val currentLang = profile.nativeLanguage

    var userText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("chatbot_page_root")
    ) {
        // Chat room header with conversational helper robot
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, Brush.linearGradient(listOf(Color.White.copy(alpha = 0.20f), Color.White.copy(alpha = 0.03f)))),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Micro Robot model drawing inside header
                RobotAssistant(state = rState, modifier = Modifier.size(70.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("🤖 AI Assistant Robot", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("Language Tutor & Translator on demand (অনলাইন)", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { viewModel.clearChatLogs() }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Clear Chat history", tint = Color.Red.copy(alpha = 0.7f))
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Chat Conversation Bubble scroller
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (chatLogs.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "কোনো পূর্ববর্তী বার্তা নেই। চ্যাট শুরু করতে নিচে টেক্সট লিখুন!\n(No messages yet. Speak or type below to converse!)",
                            textAlign = TextAlign.Center,
                            fontSize = 13.sp,
                            color = Color.Gray
                        )
                    }
                }
            } else {
                items(chatLogs) { bubble ->
                    ChatBubble(message = bubble, onSpeak = { viewModel.speakText(bubble.text) })
                }
            }
        }

        // Active typing row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = userText,
                onValueChange = { userText = it },
                label = { Text("Ask anything... ইংরেজী বা আরবী অনুবাদ?") },
                modifier = Modifier.weight(1f).testTag("chat_input_textfield"),
                shape = RoundedCornerShape(16.dp),
                trailingIcon = {
                    if (userText.isNotBlank()) {
                        IconButton(onClick = { userText = "" }) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear Input text")
                        }
                    }
                }
            )

            FloatingActionButton(
                onClick = {
                    if (userText.isNotBlank()) {
                        viewModel.sendMessageToChatbot(userText)
                        userText = ""
                    }
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier.size(54.dp).testTag("chat_send_button")
            ) {
                Icon(imageVector = Icons.Default.Send, contentDescription = "Send message to AI bot")
            }
        }
    }
}

@Composable
fun ChatBubble(message: com.example.data.ChatMessage, onSpeak: () -> Unit) {
    val isUser = message.isUser
    val alignment = if (isUser) Alignment.End else Alignment.Start
    val containerColor = if (isUser) {
        MaterialTheme.colorScheme.primary.copy(alpha = 0.85f)
    } else {
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
    }
    val textColor = if (isUser) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
    val cornerShape = if (isUser) {
        RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 2.dp)
    } else {
        RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 2.dp, bottomEnd = 16.dp)
    }

    Column(
        modifier = Modifier.fillMaxWidth().testTag("chat_bubble_${message.id}"),
        horizontalAlignment = alignment
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp)
        ) {
            if (isUser) {
                IconButton(onClick = onSpeak, modifier = Modifier.size(32.dp).testTag("speak_user_msg_${message.id}")) {
                    Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Read out loud", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                }
            }
            
            Box(
                modifier = Modifier
                    .widthIn(max = 240.dp)
                    .clip(cornerShape)
                    .background(containerColor)
                    .border(
                        width = 1.dp,
                        brush = Brush.linearGradient(
                            colors = listOf(Color.White.copy(alpha = 0.15f), Color.White.copy(alpha = 0.02f))
                        ),
                        shape = cornerShape
                    )
                    .padding(12.dp)
            ) {
                Text(
                    text = message.text,
                    color = textColor,
                    fontSize = 14.sp,
                    lineHeight = 18.sp
                )
            }
            
            if (!isUser) {
                IconButton(onClick = onSpeak, modifier = Modifier.size(32.dp).testTag("speak_tutor_msg_${message.id}")) {
                    Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Read out loud", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                }
            }
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = if (isUser) "Me" else "AI Tutor Support 🔊",
            fontSize = 10.sp,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
    }
}

// --- Control Panel Screen: Language settings, secure Base64 back-ups, updates ---
@Composable
fun SettingsScreen(viewModel: MainViewModel) {
    val profile by viewModel.userProfile.collectAsState()
    val backupCode by viewModel.backupCodeResult.collectAsState()
    val backupMsg by viewModel.backupStatusMessage.collectAsState()

    val currentLang = profile.nativeLanguage
    val targetLang = profile.targetLanguage

    val clipboard = LocalClipboardManager.current

    var restoreKeyInput by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("settings_control_scroller"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = Translator.get("settings", currentLang),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Manage translations preferences, secure encrypted backups, offline sync models & multi-device keys:",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }

        // Language Picker Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth().testTag("language_picker"),
                border = BorderStroke(1.dp, Brush.linearGradient(listOf(Color.White.copy(alpha = 0.16f), Color.White.copy(alpha = 0.02f)))),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "🌐 Language Settings", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    Text(text = "App Display Translation (আপনার মাতৃভাষা):", fontSize = 13.sp)
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("BN" to "বাংলা", "EN" to "English", "AR" to "العربية").forEach { (code, name) ->
                            FilterChip(
                                selected = currentLang == code,
                                onClick = { viewModel.updateLanguages(code, targetLang) },
                                label = { Text(name) }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = "Learning Focus Target (যে ভাষা শিখতে চান):", fontSize = 13.sp)
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("BN" to "বাংলা", "EN" to "English", "AR" to "العربية").forEach { (code, name) ->
                            FilterChip(
                                selected = targetLang == code,
                                onClick = { viewModel.updateLanguages(currentLang, code) },
                                label = { Text(name) }
                            )
                        }
                    }
                }
            }
        }

        // Toggle Switches Card (Dark Mode, Sync, Encryption)
        item {
            Card(
                modifier = Modifier.fillMaxWidth().testTag("preferences_security_card"),
                border = BorderStroke(1.dp, Brush.linearGradient(listOf(Color.White.copy(alpha = 0.16f), Color.White.copy(alpha = 0.02f)))),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "⚙️ Preferences & Security", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    // Dark mode
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Theme Selector Dark Mode")
                        Switch(
                            checked = profile.isDarkMode,
                            onCheckedChange = { viewModel.setDarkMode(it) }
                        )
                    }

                    // Cloud sync
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Simulated Multi-device Cloud Sync")
                        Switch(
                            checked = profile.isSyncEnabled,
                            onCheckedChange = { viewModel.updateSyncEnabled(it) }
                        )
                    }

                    // E2E Encryption
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("E2E Cryto Encryption Keys")
                        Switch(
                            checked = profile.isE2eEncrypted,
                            onCheckedChange = { viewModel.updateE2eEncryption(it) }
                        )
                    }
                }
            }
        }

        // Multi device security indicator key
        item {
            Card(
                modifier = Modifier.fillMaxWidth().testTag("device_credentials_card"),
                border = BorderStroke(1.dp, Brush.linearGradient(listOf(Color.White.copy(alpha = 0.16f), Color.White.copy(alpha = 0.02f)))),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "💻 Multi-Device Connection Credentials", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    Text(
                        text = "Use this authorization key to synchronise other phones/tablets simultaneously:",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = profile.multiDeviceCode,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = {
                                clipboard.setText(AnnotatedString(profile.multiDeviceCode))
                                viewModel.triggerLocalUpdateCheck()
                            }) {
                                Icon(imageVector = Icons.Default.Share, contentDescription = "")
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        // Crypto XOR Backup Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth().testTag("backup_recovery_card"),
                border = BorderStroke(1.dp, Brush.linearGradient(listOf(Color.White.copy(alpha = 0.16f), Color.White.copy(alpha = 0.02f)))),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "💾 E2E Encrypted Recovery Backup", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    Text(
                        text = "Creates structured cryptographic XOR key codes saving local progress & active streak dates securely:",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    Button(
                        onClick = { viewModel.generateBackup() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Generate encrypted backup key")
                    }

                    if (backupCode != null) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text("Generated Key Code (base64 encrypted):", fontSize = 11.sp, color = Color.Gray)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = backupCode!!.take(35) + "...",
                                modifier = Modifier.weight(1f),
                                maxLines = 1,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            IconButton(onClick = {
                                clipboard.setText(AnnotatedString(backupCode!!))
                            }) {
                                Icon(imageVector = Icons.Default.Share, contentDescription = "Copy generated backup key")
                            }
                        }
                    }

                    Divider(modifier = Modifier.padding(vertical = 12.dp))

                    Text(
                        text = "Insert secure progress key code:",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = restoreKeyInput,
                        onValueChange = { restoreKeyInput = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Paste progress base64 key here") }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Button(
                            onClick = {
                                viewModel.restoreBackup(restoreKeyInput)
                                restoreKeyInput = ""
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Recover Progress")
                        }
                        OutlinedButton(
                            onClick = { viewModel.clearBackupState() },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Clear Status")
                        }
                    }

                    if (backupMsg != null) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = backupMsg!!, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontSize = 12.sp)
                    }
                }
            }
        }

        // Update checklist system
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.2f)),
                modifier = Modifier.fillMaxWidth().clickable { viewModel.triggerLocalUpdateCheck() }
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("📌 Maintenance & System Updates", fontWeight = FontWeight.Bold)
                        Text("Check if update matches server. Release version: v1.4.2", fontSize = 11.sp)
                    }
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = "Check update", tint = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}
