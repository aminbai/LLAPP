package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.MainViewModel

// Arabic Grammar and Morphology Study Academy Screen designed for Quranic Comprehension (Bengali Language)
@Composable
fun ArabicGrammarAcademyScreen(viewModel: MainViewModel, onBack: () -> Unit) {
    var activeSubTab by remember { mutableStateOf("HARAKAT") } // HARAKAT, CONJUGATION, SARF_SHIFTS, QURAN_PARSER

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // High fidelity custom Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.85f))
                    .statusBarsPadding()
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(onClick = onBack, modifier = Modifier.testTag("grammar_back_btn")) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back to home", tint = MaterialTheme.colorScheme.primary)
                }

                Icon(imageVector = Icons.Default.Menu, contentDescription = "Academy Icon", tint = MaterialTheme.colorScheme.primary)
                
                Text(
                    text = "কুরআনিক আরবি ব্যাকরণ একাডেমি",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            // Horizontal custom selector tab view for Grammar Sections
            ScrollableTabRow(
                selectedTabIndex = when(activeSubTab) {
                    "HARAKAT" -> 0
                    "CONJUGATION" -> 1
                    "SARF_SHIFTS" -> 2
                    "QURAN_PARSER" -> 3
                    else -> 0
                },
                edgePadding = 12.dp,
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                contentColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth()
            ) {
                Tab(
                    selected = activeSubTab == "HARAKAT",
                    onClick = { activeSubTab = "HARAKAT" },
                    text = { Text("১. হরকত ও ইরাব (জের-জবর-পেশ)", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                )
                Tab(
                    selected = activeSubTab == "CONJUGATION",
                    onClick = { activeSubTab = "CONJUGATION" },
                    text = { Text("২. ১৪ প্রকার রূপান্তর (সরফ)", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                )
                Tab(
                    selected = activeSubTab == "SARF_SHIFTS",
                    onClick = { activeSubTab = "SARF_SHIFTS" },
                    text = { Text("৩. ৩৪ ও ৪৮ রূপান্তর", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                )
                Tab(
                    selected = activeSubTab == "QURAN_PARSER",
                    onClick = { activeSubTab = "QURAN_PARSER" },
                    text = { Text("৪. কুরআনিক শব্দ বিশ্লেষণ", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                )
            }

            // Screen content switcher based on Selected Grammar Tab
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                MaterialTheme.colorScheme.surface,
                                MaterialTheme.colorScheme.background
                            )
                        )
                    )
            ) {
                AnimatedContent(
                    targetState = activeSubTab,
                    transitionSpec = {
                        fadeIn() togetherWith fadeOut()
                    },
                    label = "grammar_tab_switch"
                ) { targetState ->
                    when (targetState) {
                        "HARAKAT" -> HarakatGrammarSegment(viewModel)
                        "CONJUGATION" -> ConjugationGrammarSegment(viewModel)
                        "SARF_SHIFTS" -> SarfShiftsGrammarSegment(viewModel)
                        "QURAN_PARSER" -> QuranParserGrammarSegment(viewModel)
                    }
                }
            }
        }
    }
}

// ==========================================
// SEGMENT 1: Harakat, Jer Jobor Pesh & I'rab Explanation
// ==========================================
@Composable
fun HarakatGrammarSegment(viewModel: MainViewModel) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f)),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(imageVector = Icons.Default.Info, contentDescription = "Intro", tint = MaterialTheme.colorScheme.primary)
                        Text("জের, জবর ও পেশের কুরআনিক প্রভাব", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "আরবি ভাষায় শব্দের শেষ হরফের জের, জবর বা পেশ পরিবর্তনের প্রক্রিয়াকে 'ই'রাব' (إعرাব) বা ব্যাকরণগত পরিবর্তন বলা হয়। অনেক সময় সামান্য হরকতের (Vowel) হেরফেরে পুরো বাক্যের কর্তা ও কর্মের অবস্থান উল্টে যায়। কুরআন পড়ার সময় ভুল হরকত পড়া বিশাল গুনাহ ও অর্থ বিকৃতির কারণ হতে পারে। নিচে সহজ উদাহরণের মাধ্যমে শিখুন কীভাবে ইরাব কাজ করে।",
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    )
                }
            }
        }

        // Vowel State Visual Grid
        item {
            Text("প্রধান তিনটি হরকত ও তাদের ভূমিকা", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = MaterialTheme.colorScheme.secondary)
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                HarakatIntroCard(
                    symbol = "◌ُ",
                    title = "পেশ (যম্মাহ - الضَّمَّة)",
                    state = "রাফ' বা মারফূ অবস্থা (Nominative - কর্তা/Subject)",
                    desc = "যখন কোনো শব্দের শেষ অক্ষরে পেশ (ُ) থাকে, তখন সাধারণত সেটি বাক্যের মূল কাজ সম্পাদনকারী অর্থাৎ 'কর্তা' (Fa'il - ফায়েল) বোঝায়। যেমন: زَيْدٌ (যাইদুন - যাইদ নিজে কাজটি করেছে)।",
                    color = Color(0xFF10B981)
                )

                HarakatIntroCard(
                    symbol = "◌َ",
                    title = "জবর (ফাতহাহ - الْفَتْحَة)",
                    state = "নাসব বা মানসূব অবস্থা (Accusative - কর্ম/Object)",
                    desc = "শব্দের শেষে জবর (َ) থাকলে সেটি সাধারণত বাক্যের 'কর্ম' বা অবজেক্ট (Maf'ul - মাফউইল) বোঝায়, যার উপর কাজটি করা হয়েছে। যেমন: زَيْدًا (যাইদান - অর্থাৎ যাইদের উপর কাজটি করা হয়েছে)।",
                    color = Color(0xFFF59E0B)
                )

                HarakatIntroCard(
                    symbol = "◌ِ",
                    title = "জের (কাসরাহ - الْكَسْرَة)",
                    state = "জার বা মাজরূর অবস্থা (Genitive - অধিকারী/Possessive/Prepositional)",
                    desc = "শব্দের শেষে জের (ِ) থাকলে সেটি সাধারণত পজেসিভ সম্বন্ধপদ (মুদাফ ইলাইহি) বা প্রিপজিশন (হরফে জার, যেমন فِي, بِ, لِ) এর পরে যুক্ত হয়ে বসে। যেমন: فِي الْبَيْتِ (ফিল বাইতি - ঘরে)।",
                    color = Color(0xFF3B82F6)
                )
            }
        }

        // Live interactive comparison demonstrating meaning reversal
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "🔥 হরকত বদলে অর্থ উল্টে যাওয়ার বাস্তব প্রমাণ:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    // Sentence 1
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                            .padding(12.dp)
                    ) {
                        Text(
                            text = "ضَرَبَ زَيْدٌ عَمْرًا",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "উচ্চারণ: দারাবা যাইদুন আমরান",
                            fontSize = 11.sp,
                            color = Color.Gray,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "• যাইদুন (পেশ) = কর্তা (যাইদ প্রহারকারী)\n• আমরান (জবর) = কর্ম (আমর মার খেয়েছে)",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "অর্থ: যাইদ আমরকে প্রহার করেছে।",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF10B981)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Sentence 2
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                            .padding(12.dp)
                    ) {
                        Text(
                            text = "ضَرَبَ زَيْدًا عَمْرٌو",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "উচ্চারণ: দারাবা যাইদান আমরুন",
                            fontSize = 11.sp,
                            color = Color.Gray,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "• যাইদান (জবর) = কর্ম (যাইদ মার খেয়েছে এবার!)\n• আমরুন (পেশ) = কর্তা (আমর নিজেই প্রহারকারী)",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "অর্থ: আমর যাইদকে প্রহার করেছে!",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFEF4444)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "লক্ষ্য করুন: শব্দের স্থান এবং বর্ণগুলো একদম একই আছে। শুধুমাত্র পেশ এবং জবরের অদল-বদল হওয়ায় মার খাদক কর্তা হয়ে গেল আর প্রহারকারী মারধোর খেয়ে বসল! কুরআনে ভুল হরকত পড়ার পরিণাম কতটা ভয়ানক তা এই ছোট্ট উদাহরণেই পরিষ্কার।",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        lineHeight = 15.sp
                    )
                }
            }
        }
    }
}

@Composable
fun HarakatIntroCard(symbol: String, title: String, state: String, desc: String, color: Color) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, color.copy(alpha = 0.25f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = symbol, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = color)
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(text = state, fontSize = 11.sp, color = color, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = desc, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, lineHeight = 16.sp)
            }
        }
    }
}


// ==========================================
// SEGMENT 2: 14 Morphs Verbs Conjugation Study
// ==========================================
data class PronounItem(
    val pronoun: String,
    val genderAndNumber: String,
    val person: String, // 3rd, 2nd, 1st
    val key: String
)

@Composable
fun ConjugationGrammarSegment(viewModel: MainViewModel) {
    var selectedRoot by remember { mutableStateOf("نَصَرَ") } // نَصَرَ, كَتَبَ, فَعَلَ, ضَرَبَ
    var selectedTense by remember { mutableStateOf("MAZI") } // MAZI (Past), MUDARI (Present/Future)

    val roots = listOf("نَصَرَ", "كَتَبَ", "فَعَلَ", "ضَرَبَ")
    
    val pronouns = listOf(
        PronounItem("هُوَ", "একবচন পুরুষবাচক (৩য় ব্যক্তি / He)", "3rd Masculine", "he"),
        PronounItem("هُمَا", "দ্বিবচন পুরুষবাচক (৩য় ব্যক্তি / They both)", "3rd Masculine", "they_two_m"),
        PronounItem("هُمُ", "বহুবচন পুরুষবাচক (৩য় ব্যক্তি / They all)", "3rd Masculine", "they_all_m"),
        PronounItem("هِيَ", "একবচন স্ত্রীবাচক (৩য় ব্যক্তি / She)", "3rd Feminine", "she"),
        PronounItem("هُمَا", "দ্বিবচন স্ত্রীবাচক (৩য় ব্যক্তি / They both)", "3rd Feminine", "they_two_f"),
        PronounItem("هُنَّ", "বহুবচন স্ত্রীবাচক (৩য় ব্যক্তি / They all)", "3rd Feminine", "they_all_f"),
        PronounItem("أَنْتَ", "একবচন পুরুষবাচক (২য় ব্যক্তি / You)", "2nd Masculine", "you_s_m"),
        PronounItem("أَنْتُمَا", "দ্বিবচন পুরুষবাচক (২য় ব্যক্তি / You both)", "2nd Masculine", "you_two_m"),
        PronounItem("أَنْتُمْ", "বহুবচন Con. (২য় ব্যক্তি / You all)", "2nd Masculine", "you_all_m"),
        PronounItem("أَنْتِ", "একবচন স্ত্রীবাচক (২য় ব্যক্তি / You)", "2nd Feminine", "you_s_f"),
        PronounItem("أَنْتُمَا", "দ্বিবচন স্ত্রীবাচক (২য় ব্যক্তি / You both)", "2nd Feminine", "you_two_f"),
        PronounItem("أَنْتُنَّ", "বহুবচন স্ত্রীবাচক (২য় ব্যক্তি / You all)", "2nd Feminine", "you_all_f"),
        PronounItem("أَنَا", "একবচন সাধারণ (১ম ব্যক্তি / I)", "1st Common", "i"),
        PronounItem("نَحْنُ", "বহুবচন সাধারণ (১ম ব্যক্তি / We)", "1st Common", "we")
    )

    // Helper functions translating morphs on-the-fly depending on select root and tense
    fun getVerbMorph(pronounKey: String, root: String, tense: String): Pair<String, String> {
        return if (tense == "MAZI") {
            when (root) {
                "نَصَرَ" -> when (pronounKey) {
                    "he" -> Pair("نَصَرَ", "সে সাহায্য করেছে")
                    "they_two_m" -> Pair("نَصَرَا", "তারা দু'জন সাহায্য করেছে")
                    "they_all_m" -> Pair("نَصَرُوا", "তারা সবাই সাহায্য করেছে")
                    "she" -> Pair("نَصَرَتْ", "সে সাহায্য করেছে [স্ত্রী]")
                    "they_two_f" -> Pair("نَصَرَتَا", "তারা দু'জন সাহায্য করেছে [স্ত্রী]")
                    "they_all_f" -> Pair("نَصَرْنَ", "তারা সব নারী সাহায্য করেছে")
                    "you_s_m" -> Pair("نَصَرْتَ", "তুমি সাহায্য করেছো")
                    "you_two_m" -> Pair("نَصَرْتُمَا", "তোমরা দু'জন সাহায্য করেছো")
                    "you_all_m" -> Pair("نَصَرْتُمْ", "তোমরা সবাই সাহায্য করেছো")
                    "you_s_f" -> Pair("نَصَرْتِ", "তুমি সাহায্য করেছো [স্ত্রী]")
                    "you_two_f" -> Pair("نَصَرْتُمَا", "তোমরা দু'জন সাহায্য করেছো [স্ত্রী]")
                    "you_all_f" -> Pair("نَصَرْتُنَّ", "তোমরা সব নারী সাহায্য করেছো")
                    "i" -> Pair("نَصَرْتُ", "আমি সাহায্য করেছি")
                    "we" -> Pair("نَصَرْنَا", "আমরা সাহায্য করেছি")
                    else -> Pair("نَصَرَ", "")
                }
                "كَتَبَ" -> when (pronounKey) {
                    "he" -> Pair("كَتَبَ", "সে লিখেছে")
                    "they_two_m" -> Pair("كَتَبَا", "তারা দু'জন লিখেছে")
                    "they_all_m" -> Pair("كَتَبُوا", "তারা সবাই লিখেছে")
                    "she" -> Pair("كَتَبَتْ", "সে লিখেছে [স্ত্রী]")
                    "they_two_f" -> Pair("كَتَبَتَا", "তারা দু'জন লিখেছে [স্ত্রী]")
                    "they_all_f" -> Pair("كَتَبْنَ", "তারা সব নারী লিখেছে")
                    "you_s_m" -> Pair("كَتَبْتَ", "তুমি লিখেছো")
                    "you_two_m" -> Pair("كَتَبْتُمَا", "তোমরা দু'জন লিখেছো")
                    "you_all_m" -> Pair("كَتَبْتُمْ", "তোমরা সবাই লিখেছো")
                    "you_s_f" -> Pair("كَتَبْتِ", "তুমি লিখেছো [স্ত্রী]")
                    "you_two_f" -> Pair("كَتَبْتُمَا", "তোমরা দু'জন লিখেছো [স্ত্রী]")
                    "you_all_f" -> Pair("كَتَبْتُنَّ", "তোমরা সব নারী লিখেছো")
                    "i" -> Pair("كَتَبْتُ", "আমি লিখেছি")
                    "we" -> Pair("كَتَبْنَا", "আমরা লিখেছি")
                    else -> Pair("كَتَبَ", "")
                }
                "فَعَلَ" -> when (pronounKey) {
                    "he" -> Pair("فَعَلَ", "সে করেছে")
                    "they_two_m" -> Pair("فَعَلَا", "তারা দু'জন করেছে")
                    "they_all_m" -> Pair("فَعَلُوا", "তারা সবাই করেছে")
                    "she" -> Pair("فَعَلَتْ", "সে করেছে [স্ত্রী]")
                    "they_two_f" -> Pair("فَعَلَتَا", "তারা দু'জন করেছে [স্ত্রী]")
                    "they_all_f" -> Pair("فَعَلْنَ", "তারা সব নারী করেছে")
                    "you_s_m" -> Pair("فَعَلْتَ", "তুমি করেছো")
                    "you_two_m" -> Pair("فَعَلْتُمَا", "তোমরা দু'জন করেছো")
                    "you_all_m" -> Pair("فَعَلْتُمْ", "তোমরা সবাই করেছো")
                    "you_s_f" -> Pair("فَعَلْتِ", "তুমি করেছো [স্ত্রী]")
                    "you_two_f" -> Pair("فَعَلْتُمَا", "তোমরা দু'জন করেছো [স্ত্রী]")
                    "you_all_f" -> Pair("فَعَلْتُنَّ", "তোমরা সব নারী করেছো")
                    "i" -> Pair("فَعَلْتُ", "আমি করেছি")
                    "we" -> Pair("فَعَلْنَا", "আমরা করেছি")
                    else -> Pair("فَعَلَ", "")
                }
                "ضَرَبَ" -> when (pronounKey) {
                    "he" -> Pair("ضَرَبَ", "সে প্রহার করেছে")
                    "they_two_m" -> Pair("ضَرَبَا", "তারা দু'জন প্রহার করেছে")
                    "they_all_m" -> Pair("ضَرَبُوا", "তারা সবাই প্রহার করেছে")
                    "she" -> Pair("ضَرَبَتْ", "সে প্রহার করেছে [স্ত্রী]")
                    "they_two_f" -> Pair("ضَرَبَتَا", "তারা দু'জন প্রহার করেছে [স্ত্রী]")
                    "they_all_f" -> Pair("ضَرَبْنَ", "তারা সব নারী প্রহার করেছে")
                    "you_s_m" -> Pair("ضَرَبْتَ", "তুমি প্রহার করেছো")
                    "you_two_m" -> Pair("ضَرَبْتُمَا", "তোমরা দু'জন প্রহার করেছো")
                    "you_all_m" -> Pair("ضَرَبْتُمْ", "তোমরা সবাই প্রহার করেছো")
                    "you_s_f" -> Pair("ضَرَبْتِ", "তুমি প্রহার করেছো [স্ত্রী]")
                    "you_two_f" -> Pair("ضَرَبْتُمَا", "তোমরা দু'জন প্রহার করেছো [স্ত্রী]")
                    "you_all_f" -> Pair("ضَرَبْتُنَّ", "তোমরা সব নারী প্রহার করেছো")
                    "i" -> Pair("ضَرَبْتُ", "আমি প্রহার করেছি")
                    "we" -> Pair("ضَرَبْنَا", "আমরা প্রহার করেছি")
                    else -> Pair("ضَرَبَ", "")
                }
                else -> Pair(root, "")
            }
        } else {
            // MUDARI present / future tense
            when (root) {
                "نَصَرَ" -> when (pronounKey) {
                    "he" -> Pair("يَنْصُرُ", "সে সাহায্য করছে/করবে")
                    "they_two_m" -> Pair("يَنْصُرَانِ", "তারা দু'জন সাহায্য করছে/করবে")
                    "they_all_m" -> Pair("يَنْصُرُونَ", "তারা সবাই সাহায্য করছে/করবে")
                    "she" -> Pair("تَنْصُرُ", "সে সাহায্য করছে/করবে [স্ত্রী]")
                    "they_two_f" -> Pair("تَنْصُرَانِ", "তারা দু'জন সাহায্য করছে/করবে [স্ত্রী]")
                    "they_all_f" -> Pair("يَنْصُرْنَ", "তারা সব নারী সাহায্য করছে/করবে")
                    "you_s_m" -> Pair("تَنْصُرُ", "তুমি সাহায্য করছো/করবে")
                    "you_two_m" -> Pair("تَنْصُرَانِ", "তোমরা দু'জন সাহায্য করছো/করবে")
                    "you_all_m" -> Pair("تَنْصُرُونَ", "তোমরা সবাই সাহায্য করছো/করবে")
                    "you_s_f" -> Pair("تَنْصُرِينَ", "তুমি সাহায্য করছো/করবে [স্ত্রী]")
                    "you_two_f" -> Pair("تَنْصُرَانِ", "তোমরা দু'জন সাহায্য করছো/করবে [স্ত্রী]")
                    "you_all_f" -> Pair("تَنْصُرْنَ", "তোমরা সব নারী সাহায্য করছো/করবে")
                    "i" -> Pair("أَنْصُرُ", "আমি সাহায্য করছি/করব")
                    "we" -> Pair("نَنْصُرُ", "আমরা সাহায্য করছি/করব")
                    else -> Pair("يَنْصُرُ", "")
                }
                "كَتَبَ" -> when (pronounKey) {
                    "he" -> Pair("يَكْتُبُ", "সে লিখছে/লিখবে")
                    "they_two_m" -> Pair("يَكْتُبَانِ", "তারা দু'জন লিখছে/লিখবে")
                    "they_all_m" -> Pair("يَكْتُبُونَ", "তারা সবাই লিখছে/লিখবে")
                    "she" -> Pair("تَكْتُبُ", "সে লিখছে/লিখবে [স্ত্রী]")
                    "they_two_f" -> Pair("تَكْتُبَانِ", "তারা দু'জন লিখছে/লিখবে [স্ত্রী]")
                    "they_all_f" -> Pair("يَكْتُبْنَ", "তারা সব নারী লিখছে/লিখবে")
                    "you_s_m" -> Pair("تَكْتُبُ", "তুমি লিখছো/লিখবে")
                    "you_two_m" -> Pair("تَكْتُبَانِ", "তোমরা দু'জন লিখছো/লিখবে")
                    "you_all_m" -> Pair("تَكْتُبُونَ", "তোমরা সবাই লিখছো/লিখবে")
                    "you_s_f" -> Pair("تَكْتُبِينَ", "তুমি লিখছো/লিখবে [স্ত্রী]")
                    "you_two_f" -> Pair("تَكْتُبَانِ", "তোমরা দু'জন লিখছো/লিখবে [স্ত্রী]")
                    "you_all_f" -> Pair("تَكْتُبْنَ", "তোমরা সব নারী লিখছো/লিখবে")
                    "i" -> Pair("أَكْتُبُ", "আমি লিখছি/লিখব")
                    "we" -> Pair("نَكْتُبُ", "আমরা লিখছি/লিখব")
                    else -> Pair("يَكْتُبُ", "")
                }
                "فَعَلَ" -> when (pronounKey) {
                    "he" -> Pair("يَفْعَلُ", "সে করছে/করবে")
                    "they_two_m" -> Pair("يَفْعَلَانِ", "তারা দু'জন করছে/করবে")
                    "they_all_m" -> Pair("يَفْعَلُونَ", "তারা সবাই করছে/করবে")
                    "she" -> Pair("تَفْعَلُ", "সে করছে/করবে [স্ত্রী]")
                    "they_two_f" -> Pair("تَفْعَلَانِ", "তারা দু'জন করছে/করবে [স্ত্রী]")
                    "they_all_f" -> Pair("يَفْعَلْنَ", "তারা সব নারী করছে/করবে")
                    "you_s_m" -> Pair("تَفْعَلُ", "তুমি করছো/করবে")
                    "you_two_m" -> Pair("تَفْعَلَانِ", "তোমরা দু'জন করছো/করবে")
                    "you_all_m" -> Pair("تَفْعَلُونَ", "তোমরা সবাই করছো/করবে")
                    "you_s_f" -> Pair("تَفْعَلِينَ", "তুমি করছো/করবে [স্ত্রী]")
                    "you_two_f" -> Pair("تَفْعَلَانِ", "তোমরা দু'জন করছো/করবে [স্ত্রী]")
                    "you_all_f" -> Pair("تَفْعَلْنَ", "তোমরা সব নারী করছো/করবে")
                    "i" -> Pair("أَفْعَلُ", "আমি করছি/করব")
                    "we" -> Pair("نَفْعَلُ", "আমরা করছি/করব")
                    else -> Pair("يَفْعَلُ", "")
                }
                "ضَرَبَ" -> when (pronounKey) {
                    "he" -> Pair("يَضْرِبُ", "সে প্রহার করছে/করবে")
                    "they_two_m" -> Pair("يَضْرِبَانِ", "তারা দু'জন প্রহার করছে/করবে")
                    "they_all_m" -> Pair("يَضْرِبُونَ", "তারা সবাই প্রহার করছে/করবে")
                    "she" -> Pair("تَضْرِبُ", "সে প্রহার করছে/করবে [স্ত্রী]")
                    "they_two_f" -> Pair("تَضْرِبَانِ", "তারা দু'জন প্রহার করছে/করবে [স্ত্রী]")
                    "they_all_f" -> Pair("يَضْرِبْنَ", "তারা সব নারী প্রহার করছে/করবে")
                    "you_s_m" -> Pair("تَضْرِبُ", "তুমি প্রহার করছো/করবে")
                    "you_two_m" -> Pair("تَضْرِبَانِ", "তোমরা দু'জন প্রহার করছো/করবে")
                    "you_all_m" -> Pair("تَضْرِبُونَ", "তোমরা সবাই প্রহার করছো/করবে")
                    "you_s_f" -> Pair("تَضْرِبِينَ", "তুমি প্রহার করছো/করবে [স্ত্রী]")
                    "you_two_f" -> Pair("تَضْرِبَانِ", "তোমরা দু'জন প্রহার করছো/করবে [স্ত্রী]")
                    "you_all_f" -> Pair("تَضْرِبْنَ", "তোমরা সব নারী প্রহার করছো/করবে")
                    "i" -> Pair("أَضْرِبُ", "আমি প্রহার করছি/করব")
                    "we" -> Pair("نَضْرِبُ", "আমরা প্রহার করছি/করব")
                    else -> Pair("يَضْرِبُ", "")
                }
                else -> Pair(root, "")
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.12f)),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.25f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(imageVector = Icons.Default.Done, contentDescription = "", tint = MaterialTheme.colorScheme.primary)
                        Text("ক্রিয়াপদের ১৪ প্রকার রূপান্তর (সরফ - تصريف)", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "আরবি ভাষায় প্রতিটি ক্রিয়াপদের পুরুষ, স্ত্রী, একবচন, দ্বিবচন, বহুবচন এবং উত্তম/মধ্যম/নাম পুরুষের প্রেক্ষিতে সর্বমোট ১৪টি নির্দিষ্ট রূপ রয়েছে। কুরআনুল কারীমের প্রতিটি ক্রিয়া এই ১৪ অবস্থার কোনো না কোনো একটির অধীনে ব্যবহৃত হয়ে থাকে। নিচে মূল ক্রিয়ামূল বা রুট সিলেক্ট করুন এবং জাদু দেখুন!",
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    )
                }
            }
        }

        // Roots Selector Row
        item {
            Column {
                Text("১. ক্রিয়ামূল (Root Word) সিলেক্ট করুন:", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    roots.forEach { root ->
                        val isSelected = selectedRoot == root
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .border(
                                    1.dp,
                                    if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray.copy(alpha = 0.5f),
                                    RoundedCornerShape(8.dp)
                                )
                                .background(if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent)
                                .clickable { selectedRoot = root }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(root, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface)
                                val mean = when (root) {
                                    "نَصَرَ" -> "হেল্প করা"
                                    "كَتَبَ" -> "লেখা"
                                    "فَعَلَ" -> "কাজ করা"
                                    "ضَرَبَ" -> "আঘাত"
                                    else -> ""
                                }
                                Text(mean, fontSize = 10.sp, color = Color.Gray)
                            }
                        }
                    }
                }
            }
        }

        // Tense Selector
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { selectedTense = "MAZI" },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedTense == "MAZI") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
                    ),
                    modifier = Modifier.weight(1f).height(40.dp)
                ) {
                    Text("মাযী (অতীত কাল - Past)", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = if (selectedTense == "MAZI") Color.White else MaterialTheme.colorScheme.onSurfaceVariant)
                }

                Button(
                    onClick = { selectedTense = "MUDARI" },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedTense == "MUDARI") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
                    ),
                    modifier = Modifier.weight(1f).height(40.dp)
                ) {
                    Text("মুযারি (বর্তমান/ভবিষ্যত - Pres)", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = if (selectedTense == "MUDARI") Color.White else MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }

        // 14 Conjugations Header list
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f), RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("সর্বনাম ও লিঙ্গ", modifier = Modifier.weight(1.3f), fontWeight = FontWeight.Bold, fontSize = 11.sp)
                Text("আরবি শব্দ", modifier = Modifier.weight(1.0f), fontWeight = FontWeight.Bold, fontSize = 11.sp, textAlign = TextAlign.Center)
                Text("বাংলা অর্থ ও রূপ", modifier = Modifier.weight(1.7f), fontWeight = FontWeight.Bold, fontSize = 11.sp, textAlign = TextAlign.End)
            }
        }

        // 14 Elements
        items(pronouns) { pronoun ->
            val (arabicVal, bengaliVal) = getVerbMorph(pronoun.key, selectedRoot, selectedTense)
            
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.3f))
                        .clickable { viewModel.speakText(arabicVal) }
                        .padding(horizontal = 10.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Pronoun
                    Column(modifier = Modifier.weight(1.3f)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(MaterialTheme.colorScheme.outlineVariant)
                                    .padding(horizontal = 4.dp, vertical = 2.dp)
                            ) {
                                Text(pronoun.pronoun, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                            }
                            Text(pronoun.person.split(" ")[0], fontSize = 10.sp, color = Color.Gray)
                        }
                        Text(pronoun.genderAndNumber, fontSize = 10.sp, color = Color.Gray, lineHeight = 12.sp)
                    }

                    // Arabic Verb Text with tap to speech
                    Row(
                        modifier = Modifier.weight(1.0f),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = arabicVal,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Listen", tint = Color.Yellow, modifier = Modifier.size(14.dp))
                    }

                    // Bengali translation
                    Text(
                        text = bengaliVal,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.weight(1.7f),
                        textAlign = TextAlign.End
                    )
                }
                Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
            }
        }
    }
}


// ==========================================
// SEGMENT 3: 34 & 48 Morphological Shift Patterns
// ==========================================
@Composable
fun SarfShiftsGrammarSegment(viewModel: MainViewModel) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.12f)),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.25f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(imageVector = Icons.Default.Star, contentDescription = "", tint = MaterialTheme.colorScheme.primary)
                        Text("৩৪ ও ৪৮ প্রকার রূপান্তর এবং শব্দ গঠন", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "আরবি ভাষায় রূপান্তরের শাখা দুটি ভাগে বিভক্ত। একটিকে বলা হয় '১৬ প্রকার রূপান্তর' বা ক্রিয়াভিত্তিক রূপান্তর, অন্যটি হচ্ছে 'ইসম' বা বিশেষ্যের প্রকারভেদ সহ সর্বমোট ৩৪ বা ৪৮ প্রকার বৃহৎ সরফ রূপান্তর গ্রিড (যেমন: বাহসে ইসমে ফায়েল, ইসমে মাফউল সহ)। নিচের প্রধান ১০টি রূপান্তর ফর্মুলা এবং কুরআন বুঝার ক্ষেত্রে তাদের অর্থ পরিবর্তনের ধরণ শিখুন।",
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    )
                }
            }
        }

        item {
            Text("প্রধান ৩৪/৪৮ প্রকার রূপান্তরের মূল উপাদানসমূহ (ن-ص-র থেকে উদাহরণ):", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = MaterialTheme.colorScheme.secondary)
            Spacer(modifier = Modifier.height(4.dp))
        }

        // Derived items list
        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                SarfFormulaCard(
                    title = "১. বাহসে আল-মাযী আল-মা'রুফ (Past Tense Active)",
                    formula = "فَعَلَ (নমুনা: نَصَرَ - সে সাহায্য করেছে)",
                    desc = "যেকোনো ৩ অক্ষরের অতীত কালের প্রথম ও ইতিবাচক সক্রিয় কাজকে নির্দেশ করে। এটি থেকেই অন্যসব ফর্ম তৈরি হয়।",
                    importance = "কুরআনের যেকোনো ঐতিহাসিক ঘটনা বর্ণনা করতে এটি সবচেয়ে বেশী আসবে!"
                )

                SarfFormulaCard(
                    title = "২. বাহসে আল-মাযী আল-মাজহুল (Past Tense Passive)",
                    formula = "فُعِلَ (নমুনা: نُصِرَ - সে সাহায্য প্রাপ্ত হয়েছে)",
                    desc = "অতীত কালের কর্মবাচ্য (Passive) যেখানে কে করেছে তার চেয়ে কার ওপর করা হয়েছে তা মুখ্য।",
                    importance = "কুরআনে 'সে সৃষ্টি হয়েছে' বা 'তোমাদের উপর ফরয করা হয়েছে' (যেমন: কুতীবা 'كُتِبَ') এই রূপে থাকে।"
                )

                SarfFormulaCard(
                    title = "৩. বাহসে আল-মুযারে আল-মা'রুফ (Present/Future Active)",
                    formula = "يَفْعَلُ (নমুনা: يَنْصُرُ - সে সাহায্য করছে বা করবে)",
                    desc = "আরবিতে বর্তমান এবং ভবিষ্যৎ এই একটি কাঠামোর মাধ্যমেই একই সাথে নিয়ন্ত্রিত হয়।",
                    importance = "কুরআনের নিয়মিত আদেশ-নিষেধ ও চিরন্তন নীতিগুলো এই কালে প্রকাশ পায়।"
                )

                SarfFormulaCard(
                    title = "৪. ইসমুল ফায়িল (Active Participle / কর্তৃকারক)",
                    formula = "فَاعِلٌ (নমুনা: نَاصِرٌ - সাহায্যকারী বা সহায়)",
                    desc = "ক্রিয়া থেকে উৎপন্ন বিশেষ্য যা সরাসরি কাজ সম্পাদনকারী ব্যক্তি বা বস্তুর নামের গুণ প্রকাশ করে।",
                    importance = "কুরআনুল কারীমের বহুল ব্যবহৃত শব্দ যেমন: خالِقٌ (সৃষ্টিকর্তা), عابِدٌ (এবাদতকারী), ظالِمٌ (অত্যাচারী) ইত্যাদি এই গ্রুপভুক্ত।"
                )

                SarfFormulaCard(
                    title = "৫. ইসমুল মাফউইল (Passive Participle / কর্মবাচক নাম)",
                    formula = "مَفْعُولٌ (নমুনা: مَنْصُورٌ - সাহায্যপ্রাপ্ত বা ভিকটিম)",
                    desc = "ক্রিয়া থেকে উৎপন্ন বিশেষ্য যার ওপর কাজটি পতিত হয়েছে উদাহরণস্বরূপ যাকে সাহায্য করা হয়েছে।",
                    importance = "কুরআনের শব্দ যেমন: مَخْلُوقٌ (সৃষ্টি), مَغْضُوبِ (অভিশপ্ত), مَحْمُودٌ (প্রশংসিত) ইত্যাদি এই ৩৪/৪৮ প্রকারের অবিচ্ছেদ্য অঙ্গ।"
                )

                SarfFormulaCard(
                    title = "৬. আল-আমরু বিল-মা'রুফ (Imperative Command / আদেশ)",
                    formula = "اُفْعُلْ (নমুনা: اُنْصُرْ - তুমি সাহায্য করো!)",
                    desc = "সামনে থাকা ব্যক্তিকে সরাসরি কোনো শুভ কাজ করার জন্য আদেশ বা অনুরোধ নির্দেশ করে।",
                    importance = "কুরআনের সবচেয়ে গুরুত্বপূর্ণ ধর্মীয় নির্দেশাবলি যেমন: أَقِيمُوا (নামায কায়েম কর), اقْرَأْ (পড়ো) এই আদেশ গ্রুপেই তৈরি!"
                )

                SarfFormulaCard(
                    title = "৭. আন-নাহয়ু আনিল মুন্কার (Prohibition / নিষেধ)",
                    formula = "لَا تَفْعَلْ (নমুনা: لَا تَنْصُرْ - তুমি সাহায্য করো না!)",
                    desc = "সামনে থাকা ব্যক্তিকে কোনো অসমর্থিত কাজ করা থেকে কড়া বারণ বা নিষেধ করা বুঝায়।",
                    importance = "কুরআনের সব অন্যায় কাজের নিষেধাজ্ঞা যেমন: لَا تَقْرَبُوا (তোমরা কাছে যেও না) এই নিষেধ রূপে তৈরি।"
                )

                SarfFormulaCard(
                    title = "৮. লান বাহসে তাকিদ বিলান (Future Strong Negative / কখনই করবে না)",
                    formula = "لَنْ يَفْعَلَ (নমুনা: لَنْ يَنْصُرَ - সে কখনই সাহায্য করবে না)",
                    desc = "ভবিষ্যৎ কালে কোনো কাজের সম্ভাবনাকে চরমভাবে জোর দিয়ে অস্বীকার করা।",
                    importance = "কুরআনে কাফেরদের সম্পর্কে যেমন বলা হয়েছে: لَنْ تَنَالُوا (তোমরা কখনোই পুণ্য পাবে না যতক্ষন না...)।"
                )

                SarfFormulaCard(
                    title = "৯. লাম বাহসে নফী জহদ বিলম (Negative Emphatic Past)",
                    formula = "لَمْ يَفْعَلْ (নমুনা: لَمْ يَنْصُرْ - সে সাহায্য করেনি)",
                    desc = "বর্তমান কালের ক্রিয়ার পূর্বে 'লাম' (لَمْ) যুক্ত হয়ে এর অর্থকে দৃঢ় অতীতে নিয়ে যায় এবং শেষে জজম দেয়।",
                    importance = "কুরআনে সুরা ইখলাসে আল্লাহ বলেন: لَمْ يَلِدْ وَلَمْ يُولَدْ (তিনি জন্ম দেননি এবং জন্ম নেননি)।"
                )

                SarfFormulaCard(
                    title = "১০. ইসমুয যারফ (Noun of Place & Time)",
                    formula = "مَفْعَلٌ (নমুনা: مَنْصَرٌ - সাহায্য করার স্থান বা সময়)",
                    desc = "যেকোনো ক্রিয়া সম্পন্ন হওয়ার স্থান বা সময়কে নির্দেশ করতে মূল শব্দ থেকে এই রূপ তৈরি হয়।",
                    importance = "যেমন: مَسْجِدٌ (সিজদা করার স্থান - আমল থেকে উৎপত্তি), مَدْرَسَةٌ (পাঠ করার স্থান)।"
                )
            }
        }
    }
}

@Composable
fun SarfFormulaCard(title: String, formula: String, desc: String, importance: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
                    .padding(8.dp)
            ) {
                Text(text = formula, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSecondaryContainer, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = desc, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, lineHeight = 16.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = "💡 কুরআনিক গুরুত্ব: $importance", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF00FFCC), lineHeight = 15.sp)
        }
    }
}


// ==========================================
// SEGMENT 4: Dynamic Interactive Quran Word Parser
// ==========================================
data class QuranWordInfo(
    val word: String,
    val bengaliMeaning: String,
    val grammarType: String, // Noun (ইসেম), Verb (ফে'ল), Particle (হরফ)
    val grammarDetailCheck: String
)

data class QuranVerseInfo(
    val verseText: String,
    val translation: String,
    val surahAndVerse: String,
    val words: List<QuranWordInfo>
)

@Composable
fun QuranParserGrammarSegment(viewModel: MainViewModel) {
    val verses = listOf(
        QuranVerseInfo(
            verseText = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ",
            translation = "পরম করুণাময় অসীম দয়ালু আল্লাহর নামে শুরু করছি।",
            surahAndVerse = "সূরা ফাতিহা: ১",
            words = listOf(
                QuranWordInfo("بِسْمِ", "নামে / শুরু করছি যার নামে", "হরফ + ইসেম (بِ + اسْمِ)", "বি হরফে জারের জন্য ইসমি শব্দের শেষে জের হয়েছে।"),
                QuranWordInfo("اللَّهِ", "আল্লাহর", "ইসেম (বিশেষ্য)", "মুদাফ ইলাইহি (সম্বন্ধ পদ) হওয়ায় শেষে মাজরুর তথা জের অবস্হা গ্রহণ করেছে।"),
                QuranWordInfo("الرَّحْمَٰنِ", "পরম করুণাময়", "সিফাত (বিশেষণ)", "আল্লাহর গুণবাচক নাম, তাই পূর্ববর্তী শব্দের অনুসরণে জের হয়েছে।"),
                QuranWordInfo("الرَّحِيمِ", "অতি দয়ালু", "সিফাত (বিশেষণ)", "আল্লাহর দ্বিতীয় বিশেষণ, তাই এটিও জের বিশিষ্ট।")
            )
        ),
        QuranVerseInfo(
            verseText = "الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ",
            translation = "সকল প্রশংসা একমাত্র জগৎসমূহের প্রতিপালক আল্লাহর জন্য।",
            surahAndVerse = "সূরা ফাতিহা: ২",
            words = listOf(
                QuranWordInfo("الْحَمْدُ", "সকল প্রশংসা", "ইসেম (বিশেষ্য)", "মুক্তাদা বা বাক্যের সাবজেক্ট হওয়ায় পেশযুক্ত 'মারফূ' অবস্থায় রয়েছে।"),
                QuranWordInfo("لِلَّهِ", "আল্লাহর জন্য", "হরফ + ইসেম (لِ + اللَّهِ)", "'লি' হরফে জারের কারণে শেষে জের তথা মাজরূর অবস্থা ধারণ করেছে।"),
                QuranWordInfo("رَبِّ", "প্রতিপালক", "ইসেম (মুদাফ)", "আল্লাহ শব্দের বদল/সিফাত হিসেবে জের বা মাজরূর অবস্থায় এসেছে।"),
                QuranWordInfo("الْعَالَمِينَ", "জগৎসমূহের", "ইসেম (মুদাফ ইলাইহি)", "বহুবচন শব্দ, মাজরূর অবস্থায় ইয়া-নুন যুক্ত হয়েছে (عَالَمُونَ থেকে عَالَمِينَ)।")
            )
        ),
        QuranVerseInfo(
            verseText = "قُلْ هُوَ اللَّهُ أَحَدٌ",
            translation = "বলুন, তিনিই আল্লাহ, একক অদ্বিতীয়!",
            surahAndVerse = "সূরা ইখলাস: ১",
            words = listOf(
                QuranWordInfo("قُلْ", "বলুন / ঘোষণা দিন", "ফে'ল (ক্রিয়াপদ - আমরে হাযির)", "আদেশসূচক ক্রিয়াপদ, তাই শেষে সুকুন (জজম) হয়েছে।"),
                QuranWordInfo("هُوَ", "তিনি", "ইসেম (সর্বনাম / দ্বমীর)", "একবচন পুরুষবাচক সর্বনাম, মোবাতিদা রুপে ব্যবহৃত।"),
                QuranWordInfo("اللَّهُ", "আল্লাহ", "ইসেম (বিশেষ্য)", "মুবতাদা বা প্রধান কর্তা হওয়ার কারণে পেশযুক্ত মারফূ অবস্থা ধারণ করেছে।"),
                QuranWordInfo("أَحَدٌ", "একক / অদ্বিতীয়", "ইসেম (খবর / প্রেডিকেট)", "আল্লাহ সম্পর্কে খবর বা তথ্য দেওয়ার কারণে তানবীন সহ ডবল পেশযুক্ত মারফূ অবস্থা।")
            )
        ),
        QuranVerseInfo(
            verseText = "لَمْ يَلِدْ وَلَمْ يُولَدْ",
            translation = "তিনি কাউকে জন্ম দেননি এবং নিজেও জন্ম নেননি।",
            surahAndVerse = "সূরা ইখলাস: ৩",
            words = listOf(
                QuranWordInfo("لَمْ", "নহে / কখনই না / নহে অতীত", "হরফ (নফী)", "নেতিবাচক হরফ যা বর্তমান ক্রিয়ায় প্রবেশ করে শেষে জজম দেয় ও অতীত অর্থ দাঁড় করায়।"),
                QuranWordInfo("يَلِدْ", "সন্তান জন্ম দিয়েছেন", "ফে'ল (ক্রিয়া - মুযারে মা'রুফ)", "'লাম' যুক্ত হওয়ায় শেষে জজম এসে নেতিবাচকরূপ ধারণ করেছে।"),
                QuranWordInfo("وَلَمْ", "এবং নহে", "হরফে আতফ + হরফে নফী", "এবং না বা এবং কখনই নহে।"),
                QuranWordInfo("يُولَدْ", "জন্মগ্রহণ করেছেন", "ফে'ল (ক্রিয়া - মুযারে মাজহুল বা প্যাসিভ)", "'লাম' এসে জজম হয়েছে। কর্মবাচ্য হিসেবে তিনি কার সৃষ্টি বা জাত নন বুঝায়।")
            )
        ),
        QuranVerseInfo(
            verseText = "اللَّهُ لَا إِلَٰهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ",
            translation = "আল্লাহ, তিনি ছাড়া আর কোনো ইলাহ নেই, তিনি চিরঞ্জীব ও অনাদি সত্ত্বা।",
            surahAndVerse = "আয়াতুল কুরসী (অংশ)",
            words = listOf(
                QuranWordInfo("اللَّهُ", "আল্লাহ", "ইসেম (বিশেষ্য)", "বাক্যের প্রধান কর্তা, তাই পেশযুক্ত মারফু।"),
                QuranWordInfo("لَا", "নেই / না", "হরফ (লা নাফী জিল জিন্স)", "সম্পূর্ণ অস্থিত্ম জোরালোভাবে অস্বীকারকারী অব্যয়।"),
                QuranWordInfo("إِلَٰهَ", "কোনো মাবুদ", "ইসেম (লা এর ইসিম)", "লা এর প্রভাবে শেষ অক্ষরে জবর অবস্থা (মানসূব) ধারণ করেছে।"),
                QuranWordInfo("إِلَّا", "ছাড়া / ব্যতীত", "হরফ (ইস্তিসনা)", "ব্যতিক্রম বুঝাতে ব্যবহৃত অব্যয়।"),
                QuranWordInfo("هُوَ", "তিনি", "ইসেম (সর্বনাম)", "একবচন পুরুষবাচক সর্বনাম।"),
                QuranWordInfo("الْحَيُّ", "চিরঞ্জীব", "ইসেম (সিফাত/গুণ)", "আল্লাহর সিফাত, পেশযুক্ত মারফূ অবস্থা।"),
                QuranWordInfo("الْقَيُّومُ", "চিরস্থায়ী / নিখিল বিশ্বের নিয়ন্ত্রক", "ইসেম (সিফাত/গুণ)", "আল্লাহর আরেকটি সিফাত, পেশযুক্ত মারফূ অবস্থা।")
            )
        )
    )

    var selectedVerseIndex by remember { mutableStateOf(0) }
    var clickedWord by remember { mutableStateOf<QuranWordInfo?>(null) }

    val activeVerse = verses[selectedVerseIndex]

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f)),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "", tint = MaterialTheme.colorScheme.primary)
                        Text("কুরআনিক শব্দ-বাই-শব্দ বিশ্লেষণ ল্যাব", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "কুরআন পাঠের সময় প্রতিটি শব্দের ব্যকরণগত ধরণ (ইসেম, ফে'ল, হরফ), ইরাব বা শেষ হরকত পরিবর্তনের কারণ বুঝতে পারলে আপনি সহজে যেকোনো আরবি বাক্য বুঝতে পারবেন। নিচে যেকোনো মাক্কী বা মাদানী আয়াত সিলেক্ট করুন এবং লাইভ বিশ্লেষণ বুঝতে আয়াতের যেকোনো শব্দের ওপর স্পর্শ করুন!",
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    )
                }
            }
        }

        // Verse selector buttons
        item {
            Text("১. কুরআনের বাণী সিলেক্ট করুন:", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                verses.forEachIndexed { index, v ->
                    val isSelected = selectedVerseIndex == index
                    FilterChip(
                        selected = isSelected,
                        onClick = {
                            selectedVerseIndex = index
                            clickedWord = null // reset
                        },
                        label = { Text(v.surahAndVerse, fontSize = 11.sp, fontWeight = FontWeight.SemiBold) }
                    )
                }
            }
        }

        // Selected Verse Calligraphy Display Board
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = activeVerse.verseText,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)
                    )
                    
                    Text(
                        text = "অনুবাদ: ${activeVerse.translation}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Light,
                        lineHeight = 16.sp,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "👇 আয়াতের প্রতিটি শব্দের অর্থ ও ব্যাকরণ দেখতে নিচের যেকোনো শব্দ স্পর্শ করুন:",
                        fontSize = 11.sp,
                        color = Color(0xFFFFCC00),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    // Flex words row using Flow-like design
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        activeVerse.words.forEach { qWord ->
                            val isClicked = clickedWord?.word == qWord.word
                            Box(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .border(
                                        1.dp,
                                        if (isClicked) Color(0xFF00FFCC) else MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                                        RoundedCornerShape(8.dp)
                                    )
                                    .background(if (isClicked) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surface)
                                    .clickable {
                                        clickedWord = qWord
                                        viewModel.speakText(qWord.word)
                                    }
                                    .padding(horizontal = 10.dp, vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = qWord.word,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        }

        // Live Grammatic Detail Sheet for Clicked Word
        item {
            AnimatedVisibility(
                visible = clickedWord != null,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                clickedWord?.let { w ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.2.dp, Color(0xFF00FFCC)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(MaterialTheme.colorScheme.primaryContainer)
                                            .padding(horizontal = 8.dp, vertical = 4.dp)
                                    ) {
                                        Text(text = w.word, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                    }
                                    IconButton(onClick = { viewModel.speakText(w.word) }) {
                                        Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Hear", tint = Color.Yellow)
                                    }
                                }

                                Badge {
                                    Text(w.grammarType, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))
                            Divider(color = MaterialTheme.colorScheme.outlineVariant)
                            Spacer(modifier = Modifier.height(10.dp))

                            Text("বাংলা অর্থ:", fontSize = 11.sp, color = Color.Gray)
                            Text(w.bengaliMeaning, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)

                            Spacer(modifier = Modifier.height(10.dp))

                            Text("ব্যাকরণ ও হরকতের কারণ (ইরাব বিশ্লেষণ):", fontSize = 11.sp, color = Color.Gray)
                            Text(
                                text = w.grammarDetailCheck,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
