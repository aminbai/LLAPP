package com.example.data

enum class LessonType {
    VOCABULARY,
    QUIZ,
    PRONUNCIATION
}

data class VocabPair(
    val word: String,          // Word in Target Language (e.g., "Apple" / "تفاحة")
    val translation: String,   // Translation in Native Language (e.g., "আপেল")
    val pronunciation: String  // Transliteration / Guide (e.g., "ap-ul" / "tuf-fa-hah")
)

data class QuizQuestion(
    val text: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

data class Lesson(
    val id: String,
    val title: String,
    val description: String,
    val type: LessonType,
    val targetLanguage: String, // EN, AR, BN
    val xpReward: Int = 20,
    val difficulty: String = "Beginner", // Beginner, Intermediate, Advanced
    val vocabPairs: List<VocabPair> = emptyList(),
    val quizQuestions: List<QuizQuestion> = emptyList(),
    val pronunciationPhrases: List<String> = emptyList()
)

object StaticLessons {
    val lessonsList = listOf(
        // --- ENGLISH LESSONS (Learning English) ---
        Lesson(
            id = "en_vocab_basics",
            title = "Basics Vocabulary",
            description = "Learn essential day-to-day English nouns.",
            type = LessonType.VOCABULARY,
            targetLanguage = "EN",
            xpReward = 20,
            difficulty = "Beginner",
            vocabPairs = listOf(
                VocabPair("Water", "পানি", "ওয়াটার"),
                VocabPair("Book", "বই", "বুক"),
                VocabPair("House", "বাড়ি", "হাউজ"),
                VocabPair("Friend", "বন্ধু", "ফ্রেন্ড"),
                VocabPair("School", "বিদ্যালয় / স্কুল", "স্কুল")
            )
        ),
        Lesson(
            id = "en_quiz_greetings",
            title = "Greetings Conversational",
            description = "Test your skills on daily English greetings.",
            type = LessonType.QUIZ,
            targetLanguage = "EN",
            xpReward = 30,
            difficulty = "Beginner",
            quizQuestions = listOf(
                QuizQuestion(
                    text = "What is the proper English greeting for 'শুভ সকাল'?",
                    options = listOf("Good Afternoon", "Good Morning", "Good Night", "Hello"),
                    correctIndex = 1,
                    explanation = "'Good Morning' represents 'শুভ সকাল' used until noon."
                ),
                QuizQuestion(
                    text = "If someone says 'Thank you', how should you reply?",
                    options = listOf("No problem", "You are welcome", "Thank you too", "Excuse me"),
                    correctIndex = 1,
                    explanation = "'You are welcome' is the most standard, polite reply."
                ),
                QuizQuestion(
                    text = "What does 'How are you?' mean in Bengali?",
                    options = listOf("আপনি কেমন আছেন?", "আপনার বয়স কত?", "আপনার নাম কি?", "শুভ বিদায়"),
                    correctIndex = 0,
                    explanation = "'How are you?' is a standard inquiry about someone's welfare (আপনি কেমন আছেন?)."
                )
            )
        ),
        Lesson(
            id = "en_pronounce_phrases",
            title = "A.I. Phrase Pronunciation",
            description = "Practice speaking phrases correctly. Speak to our AI Tutor!",
            type = LessonType.PRONUNCIATION,
            targetLanguage = "EN",
            xpReward = 40,
            difficulty = "Intermediate",
            pronunciationPhrases = listOf(
                "An apple a day keeps the doctor away.",
                "Practice makes perfect.",
                "Hello, virtual assistant chatbot!"
            )
        ),

        // --- ARABIC LESSONS (Learning Arabic) ---
        Lesson(
            id = "ar_vocab_basics",
            title = "Basics Arabic Words",
            description = "Master common Arabic words with correct pronunciation.",
            type = LessonType.VOCABULARY,
            targetLanguage = "AR",
            xpReward = 20,
            difficulty = "Beginner",
            vocabPairs = listOf(
                VocabPair("مَرْحَبًا", "হ্যালো / স্বাগতম", "Marhaban"),
                VocabPair("شُكْرًا", "ধন্যবাদ", "Shukran"),
                VocabPair("كِتَابٌ", "বই", "Kitabun"),
                VocabPair("مَاءٌ", "পানি", "Ma'un"),
                VocabPair("بَيْتٌ", "ঘর / বাড়ি", "Baytun")
            )
        ),
        Lesson(
            id = "ar_quiz_phrases",
            title = "Arabic Daily Phrases",
            description = "Quiz on day-to-day conversation scripts.",
            type = LessonType.QUIZ,
            targetLanguage = "AR",
            xpReward = 30,
            difficulty = "Beginner",
            quizQuestions = listOf(
                QuizQuestion(
                    text = "How do you say 'How are you?' in Arabic (Masculine)?",
                    options = listOf("كَيْفَ حَالُكَ؟ (Kayfa Haluka)", "صَبَاحُ الْخَيْرِ (Sabah Al-Khayr)", "مَا اسْمُكَ؟ (Ma Ismuka)", "أَهْلًا وَسَهْلًا (Ahlan wa Sahlan)"),
                    correctIndex = 0,
                    explanation = "'Kayfa haluka' is the standard Arabic phrase for 'How are you?' (Masculine)."
                ),
                QuizQuestion(
                    text = "What is the reply to 'صَبَاحُ الْخَيْرِ' (Good Morning)?",
                    options = listOf("شُكْرًا (Shukran)", "صَبَاحُ النُّورِ (Sabah An-Noor)", "مَعَ السَّلَامَةِ (Ma'as Salamah)", "عَفْوًا (Afwan)"),
                    correctIndex = 1,
                    explanation = "The standard, polite response to Sabah al-Khayr is Sabah an-Noor (Morning of light)."
                ),
                QuizQuestion(
                    text = "Which word represents 'ধন্যবাদ / Thank you'?",
                    options = listOf("لَا (La)", "نَعَمْ (Na'am)", "شُكْرًا (Shukran)", "مَرْحَبًا (Marhaban)"),
                    correctIndex = 2,
                    explanation = "'Shukran' translates directly to 'Thanks / Thank you'."
                )
            )
        ),
        Lesson(
            id = "ar_pronounce_phrases",
            title = "Arabic Pronunciation",
            description = "Speak standard Arabic classic phrases clearly.",
            type = LessonType.PRONUNCIATION,
            targetLanguage = "AR",
            xpReward = 45,
            difficulty = "Intermediate",
            pronunciationPhrases = listOf(
                "الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ (Alhamdulillah)",
                "أَهْلًا وَسَهْلًا بِكُمْ (Ahlan wa Sahlan bikum)",
                "التَّعْلِيمُ هُوَ السِّلَاحُ الأَقْوَى (Education is power)"
            )
        ),

        // --- BENGALI LESSONS (Learning Bengali) ---
        Lesson(
            id = "bn_vocab_basics",
            title = "বাংলা শব্দ শিক্ষা (Bengali Nouns)",
            description = "Learn Bengali letters and everyday utility nouns.",
            type = LessonType.VOCABULARY,
            targetLanguage = "BN",
            xpReward = 20,
            difficulty = "Beginner",
            vocabPairs = listOf(
                VocabPair("ভাষা (Bhasha)", "Language", "Bha-sha"),
                VocabPair("সূর্য (Shurjo)", "Sun", "Shur-jo"),
                VocabPair("চন্দ্র (Chondro)", "Moon", "Chon-dro"),
                VocabPair("পাখি (Pakhi)", "Bird", "Pa-khi"),
                VocabPair("ফল (Fol)", "Fruit", "Fol")
            )
        ),
        Lesson(
            id = "bn_quiz_basics",
            title = "বাংলা কুইজ পরীক্ষা (Bengali Quiz)",
            description = "Benchmark your basic vocabulary skills in Bengali.",
            type = LessonType.QUIZ,
            targetLanguage = "BN",
            xpReward = 30,
            difficulty = "Beginner",
            quizQuestions = listOf(
                QuizQuestion(
                    text = "What is the English word for 'ধন্যবাদ'?",
                    options = listOf("Please", "Excuse me", "Thank you", "Welcome"),
                    correctIndex = 2,
                    explanation = "'ধন্যবাদ' translates to 'Thank you' in English."
                ),
                QuizQuestion(
                    text = "What does 'জল' or 'পানি' mean in English?",
                    options = listOf("Apple", "Water", "House", "Friend"),
                    correctIndex = 1,
                    explanation = "Both 'জল' and 'পানি' refer to 'Water'."
                ),
                QuizQuestion(
                    text = "Which of the following refers to 'Book' in Bengali?",
                    options = listOf("কলম (Kolom)", "খাতা (Khata)", "বই (Boi)", "টেবিল (Tebil)"),
                    correctIndex = 2,
                    explanation = "'বই' is 'Book'. 'কলম' is 'Pen', and 'খাতা' is 'Notebook'."
                )
            )
        ),
        Lesson(
            id = "bn_pronounce_phrases",
            title = "বাংলা উচ্চারণ অনুশীলন",
            description = "Speak traditional beautiful Bengali literary lines aloud.",
            type = LessonType.PRONUNCIATION,
            targetLanguage = "BN",
            xpReward = 40,
            difficulty = "Intermediate",
            pronunciationPhrases = listOf(
                "আমার সোনার বাংলা, আমি তোমায় ভালোবাসি",
                "সবার উপরে মানুষ সত্য, তাহার উপরে নাই",
                "আজকের দিনটি অনেক সুন্দর এবং চমৎকার"
            )
        )
    ) + generate70ArabicLessons()

    private fun getThemeInfo(i: Int): Triple<String, String, String> {
        return when (i) {
            1 -> Triple("আলিফ ও বা বর্ণমালা", "Alif & Ba", "আলিফ, বা, তা এবং ছা হরফের উচ্চারণ ও ব্যবহার শিখুন।")
            2 -> Triple("জিম ও হা বর্ণমালা", "Jeem & Ha", "জিম, হা, খা এবং দাল হরফের উচ্চারণ ও ব্যবহার শিখুন।")
            3 -> Triple("ঝাল ও রা বর্ণমালা", "Thal & Ra", "ঝাল, রা, ঝা এবং সিন হরফের উচ্চারণ ও ব্যবহার শিখুন।")
            4 -> Triple("شين و صاد বর্ণমালা", "Sheen & Sad", "শিন, সোয়াদ, দোয়াদ এবং তোয়া হরফের চমৎকার উচ্চারণ অনুশীলন।")
            5 -> Triple("ظاء ও عين বর্ণমালা", "Zha & Ayn", "জোয়া, আইন, গাইন এবং ফা হরফের উচ্চারণ পদ্ধতি ও উদাহরণ।")
            6 -> Triple("قاف ও كاف বর্ণমালা", "Qaf & Kaf", "ক্বফ, কাফ, লাম এবং মিম হরফের হরকত সহ উচ্চারণ।")
            7 -> Triple("نون ও واو বর্ণমালা", "Noon & Waw", "নুন, ওয়াও, হা এবং ইয়া হরফের ব্যবহার ও শব্দ গঠন।")
            8 -> Triple("হরকত বা স্বরচিহ্ন", "Vowels (Harokat)", "আরবি ভাষার অপরিহার্য জবর, জের এবং পেশ স্বরচিহ্নের পরিচয়।")
            9 -> Triple("তানবীন শিক্ষা", "Double Vowels", "দুই জবর, দুই জের এবং দুই পেশের মাধ্যমে শব্দ উচ্চারণের নিয়মাবলী।")
            10 -> Triple("সুকুন ও তাশদীদ", "Sukun & Tashdeed", "হরফের উপর সুকুন (জজম) এবং তাশদীদ দেওয়ার নিয়ম ও উচ্চারণ পদ্ধতি।")
            11 -> Triple("অভিবাদন ও কুশলাদি ১", "Greetings 1", "দৈনন্দিন জীবনে ব্যবহৃত প্রধান আরবি অভিবাদন ও সকালের শুভেচ্ছা।")
            12 -> Triple("অভিবাদন ও কুশলাদি ২", "Greetings 2", "ধন্যবাদ জ্ঞাপন ও স্বাগতম জানানোর চমৎকার বাক্যসমূহ।")
            13 -> Triple("নিজের ছোট পরিচয়", "My Profile", "আরবিতে নিজের নাম, পেশা ও জাতীয়তা প্রকাশ করার পদ্ধতি।")
            14 -> Triple("বন্ধুত্ব ও আড্ডা", "Meeting Friends", "নতুন বন্ধুর সাথে পরিচিত হওয়ার সময় দরকারি কথোপকথন।")
            15 -> Triple("শ্রেণীকক্ষের পরিবেশ ১", "Classroom 1", "শ্রেণীকক্ষে ব্যবহৃত বই, খাতা, কলম ও ডেক্স সমূহের আরবি অর্থ।")
            16 -> Triple("শ্রেণীকক্ষের পরিবেশ ২", "Classroom 2", "শিক্ষক ও ছাত্রের মাঝে ব্যবহৃত কিছু সাধারণ প্রশ্ন ও উত্তর।")
            17 -> Triple("শ্রদ্ধেয় পিতামাতা", "Beloved Parents", "বাবা ও মায়ের প্রতি ভালোবাসা ও উৎসর্গীকৃত আরবি শব্দভাণ্ডার।")
            18 -> Triple("পরিবারের সদস্যবৃন্দ", "My Family", "ভাই, বোন, দাদা-দাদী ও রক্তের আত্মীয়দের আরবি নাম।")
            19 -> Triple("সংখ্যা গণনা ১-৫", "Numbers 1-5", "আরবি ভাষায় ১ থেকে ৫ পর্যন্ত সংখ্যার উচ্চারণ ও লিখনের নিয়ম।")
            20 -> Triple("সংখ্যা গণনা ৬-১০", "Numbers 6-10", "আরবি ভাষায় ৬ থেকে ১০ পর্যন্ত সংখ্যার সাবলীল উচ্চারণ।")
            21 -> Triple("সপ্তাহের দিনগুলো", "Days of Week", "শনিবার থেকে শুরু করে সপ্তাহের ৭টি দিনের প্রাচীন আরবি নাম।")
            22 -> Triple("হিজরি ১২ মাস", "Islamic Months", "মহররম, সফরসহ আরবি হিজরি সনের ১২টি মাসের নাম ও ক্রম।")
            23 -> Triple("প্রকৃতির বিভিন্ন রং", "Colors", "লাল, নীল, সবুজ, সাদা ও কালো রঙের আরবি পরিভাষা।")
            24 -> Triple("আবহাওয়া ও জলবায়ু", "Weather Seasons", "গরম, ঠাণ্ডা, কুয়াশা ও বৃষ্টির আরবি কথোপকথন।")
            25 -> Triple("সুস্বাদু ফলফলাদি", "Tasty Fruits", "আপেল, খেজুর, কলা ও আঙ্গুর ফলের সুমিষ্ট আরবি নাম।")
            26 -> Triple("হরেক রকম সবজি", "Fresh Vegetables", "আলু, টমেটো, পিয়াজ ও রসুন সবজির আরবি প্রতিশব্দ।")
            27 -> Triple("রান্নাঘরের আসবাবপত্র", "Kitchenware", "চুলা, পাতিল, প্লেট ও চামচের মতো দরকারী আরবি শব্দাবলি।")
            28 -> Triple("সকালের সুস্বাদু পুষ্টিকর নাস্তা", "Breakfast Items", "রুটি, মধু, ডিম ও চায়ের মতো সকালের নাস্তার উপাদান।")
            29 -> Triple("দুপুরের রাজকীয় খাবার", "Lunch Delicacies", "ভাত, ডাল, মাছ ও মাংসের মতো মধ্যাহ্নভোজের খাবার।")
            30 -> Triple("রাতের হালকা খাবার", "Dinner Gathering", "হালকা স্যুপ, সালাদ ও ফলমূল দিয়ে রাতের সুস্বাদ আহার।")
            31 -> Triple("মানব শরীর ১: অঙ্গসমূহ", "Body Organs 1", "মাথা, চুল, চোখ এবং কানের মতো বাহ্যিক অঙ্গের আরবি অর্থ।")
            32 -> Triple("মানব শরীর ২: অঙ্গসমূহ", "Body Organs 2", "হাত, পা, বুক এবং পেটের মতো গুরুত্বপূর্ণ অঙ্গের পরিচিতি।")
            33 -> Triple("আমাদের সুসজ্জিত বাড়ি", "Beautiful House", "শোবার ঘর, ড্রয়িং রুম এবং রান্নাঘরের আরবি পরিভাষা।")
            34 -> Triple("বসার ঘরের আসবাব", "Living Room Decor", "সোফা, টেলিভিশন, ঘড়ি ও জানালার আরবি শব্দ মালা।")
            35 -> Triple("শোবার ঘরের সাজ", "Cozy Bedroom", "খাট, বালিশ, চাদর ও আলমারির দরকারী আরবি নাম।")
            36 -> Triple("স্থানীয় কাঁচা বাজার", "Traditional Market", "কেনাবেচা, দাম জিগ্যেস করা এবং দরদাম করার আরবি বাক্য।")
            37 -> Triple("সুন্দর ও পরিপাটি পোশাক", "Nice Clothing", "পাঞ্জাবি, জামা, চশমা ও জুতার মতো পোশাক সামগ্রী।")
            38 -> Triple("ব্যস্ত শহরের জীবন", "Busy City", "রাস্তা, বড় দালান, ব্যাংক ও হাসপাতালের আরবি শব্দভাণ্ডার।")
            39 -> Triple("গণপরিবহন ও যানবাহন", "Public Transport", "বাস, ট্রেন, এরোপ্লেন ও গাড়ি চড়ার সময় দরকারি শব্দ।")
            40 -> Triple("রাস্তার সঠিক দিকনির্দেশ", "Travel Directions", "ডানে, বামে, সোজা ও মোড় ঘোরার আরবি ভ্রমণ নির্দেশ।")
            41 -> Triple("গৃহপালিত উপকারী পশু", "Domestic Animals", "গরু, ছাগল, ভেড়া ও বিড়ালের মতো গৃহপালিত প্রাণীর নাম।")
            42 -> Triple("বন্য জীবজন্তু", "Wild Animals", "সিংহ, বাঘ, হাতি ও ভালুকের মতো গভীর বনের পশুর নাম।")
            43 -> Triple("পাখির সুমধুর কিচিরমিচির", "Lovely Birds", "কবুতর, দোয়েল, কাক ও ঈগল পাখির আরবি নাম।")
            44 -> Triple("সমুদ্রের বৈচিত্রময় জীব", "Sea Creatures", "মাছ, হাঙ্গর, তিমি ও কচ্ছপের আরবি প্রতিশব্দ।")
            45 -> Triple("সম্মানজনক পেশা ১", "Noble Careers 1", "শিক্ষক, ডাক্তার, প্রকৌশলী ও ছাত্রের সুন্দর আরবি নাম।")
            46 -> Triple("সম্মানজনক পেশা ২", "Noble Careers 2", "ব্যবসায়ী, কৃষক, লেখক ও চালকের আরবি পরিভাষা।")
            47 -> Triple("অফিসিয়াল কর্মক্ষেত্র", "At the Office", "ফাইল, কম্পিউটার, মিটিং ও বেতনের আরবি কথকতা।")
            48 -> Triple("সুস্বাস্থ্য ও প্রাথমিক চিকিৎসা", "Health & Wellness", "ঔষধ, জ্বর, ব্যথা, সুস্থতা ও হাসপাতালের শব্দাবলি।")
            49 -> Triple("দৈনন্দিন সকালের রুটিন", "Daily Morning Routine", "ঘুম থেকে ওঠা ও অজুর আরবি সাধারণ রূপ।")
            50 -> Triple("দৈনন্দিন কাজের অংশ ২", "Daily Work Routine", "পড়ালেখা ও কর্মক্ষেত্রে যাওয়ার আরবি রূপ।")
            51 -> Triple("সময় ও ঘড়ি নির্ধারণ", "Time and Clock", "কয়টা বাজে, সেকেন্ড, মিনিট ও ঘন্টার সঠিক উত্তর।")
            52 -> Triple("মানুষের বিবিধ অনুভূতি", "Human Feelings", "সুখ, দুঃখ, রাগ ও ভয়ের সুন্দর আবেগ প্রকাশ।")
            53 -> Triple("ইসলামিক পরিভাষা ১", "Islamic Terms 1", "তাওহীদ, ইমান, তাকওয়া ও রহমতের গভীর অর্থ।")
            54 -> Triple("ইসলামিক পরিভাষা ২", "Islamic Terms 2", "সালাত, সাওম, হজ্জ ও জাকাতের আমল সংক্রান্ত পরিভাষা।")
            55 -> Triple("নামাজ ও রুকু-সেজদার দোয়া", "Prayer Duas", "রুকু ও সেজদায় পড়া প্রধান প্রধান তসবিহ।")
            56 -> Triple("কুরআনুল কারীমের মূল শব্দ ১", "Quranic Words 1", "কুরআনে বারবার ব্যবহৃত রহমত ও জান্নাতের শব্দসমূহ।")
            57 -> Triple("কুরআনুল কারীমের মূল শব্দ ২", "Quranic Words 2", "কুরআনের আয়াত বুঝার জন্য প্রয়োজনীয় ছোট ছোট বিশেষ্য।")
            58 -> Triple("আরবি দিক ও দূরবর্তী অব্যয়", "Useful Prepositions", "উপরে, নিচে, ভিতরে ও বাইরে বুঝাতে আরবি অব্যয়।")
            59 -> Triple("গুরুত্বপূর্ণ প্রধান প্রধান ক্রিয়াপদ", "Core Action Verbs", "পড়া, লেখা, খাওয়া ও যাওয়ার মত মূল ক্রিয়াপদ।")
            60 -> Triple("ক্রিয়াপদ রূপান্তর ও গঠন", "Verb Conjugation", "একবচন ও বহুবচনের সহজ ক্রিয়াপদ রূপান্তর নিয়ম।")
            61 -> Triple("অперед অতীতের কথা", "Past Tense", "অতীতে করা কোন কাজের আরবি কথন অনুশীলন।")
            62 -> Triple("বর্তমান কালের সাধারণ কাজ", "Present Tense", "বর্তমানে চলমান বা নিয়মিত কাজের রূপান্তর।")
            63 -> Triple("ভবিষ্যৎ উজ্জ্বল স্বপ্নের কথা", "Future Tense", "ভবিষ্যতে কিছু করার ইচ্ছার দৃঢ় আরবি অভিব্যক্তি।")
            64 -> Triple("অনুমতি ও প্রশ্নবোধক কথন", "Inquiry & Questions", "কাউকে প্রশ্ন করার সময় কী, কেন, কখন ও কোথায় এর ব্যবহার।")
            65 -> Triple("স্কুলের প্রিয় বিষয়সমূহ", "Favorite Subjects", "আরবি, গণিত, বিজ্ঞান ও ইতিহাসের আরবি প্রতিশব্দ।")
            66 -> Triple("প্রিয় খেলাধুলা ও বিনোদন", "Sports & Play", "ফুটবল, সাঁতার এবং দৌড় প্রতিযোগিতার সুন্দর আরবি শব্দ।")
            67 -> Triple("সুন্দর প্রকৃতি ও পাহাড়-পর্বত", "Nature & Wonders", "পাহাড়, নদী, সমুদ্র ও নক্ষত্রের আরবি শব্দ মালা।")
            68 -> Triple("গাছপালা ও কৃষিকাজ", "Plants & Farming", "গাছ, ফুল, লতা ও ফসলের সাথে পরিচিতি।")
            69 -> Triple("প্রয়োজনীয় সব যন্ত্রপাতি", "Essential Tools", "চাবি, তালা, কলম, ল্যাপটপ ও সুতোর আরবি।")
            70 -> Triple("পৃথিবীর নাম করা দেশসমূহ", "Global Countries", "সৌদি আরব, বাংলাদেশ, মক্কা ও মদীনার নামের ব্যবহার।")
            71 -> Triple("মনোজ্ঞ আরবি প্রবাদ প্রবচন", "Arab Wisdom", "আরবের চমৎকার প্রবাদ ও সত্য বাণী অনুধাবন।")
            72 -> Triple("চূড়ান্ত আরবি মূল্যায়ন কুইজ", "Final Arabic evaluation", "আরবি ভাষা শিক্ষার ৭০টি অধ্যায়ের চূড়ান্ত মূল্যায়ন পর্ব।")
            else -> Triple("সাধারণ পাঠ", "General Lesson", "সাধারণ আরবি বাক্য এবং ব্যাকরণ চর্চা।")
        }
    }

    private fun getVocabForLesson(i: Int): List<VocabPair> {
        return when (i) {
            1 -> listOf(
                VocabPair("أَلِف", "আলিফ (স্বরবর্ণের প্রথম অক্ষর)", "Alif"),
                VocabPair("بَاء", "বা (ব্যঞ্জনবর্ণের দ্বিতীয় অক্ষর)", "Ba"),
                VocabPair("تَاء", "তা (তৃতীয় অক্ষর)", "Ta"),
                VocabPair("ثَاء", "ছা (চতুর্থ অক্ষর)", "Tha")
            )
            2 -> listOf(
                VocabPair("جِيم", "জিম (পঞ্চম অক্ষর)", "Jeem"),
                VocabPair("حَاء", "হা (ষষ্ঠ অক্ষর)", "Ha"),
                VocabPair("خَاء", "খা (সপ্তম অক্ষর)", "Kha"),
                VocabPair("دَال", "দাল (অষ্টম অক্ষর)", "Dal")
            )
            3 -> listOf(
                VocabPair("ذَال", "ঝাল (নবম অক্ষর)", "Thal"),
                VocabPair("رَاء", "রা (দশম অক্ষর)", "Ra"),
                VocabPair("زَاي", "ঝা (একাদশ অক্ষর)", "Zay"),
                VocabPair("سِين", "সিন (দ্বাদশ অক্ষর)", "Seen")
            )
            4 -> listOf(
                VocabPair("شِين", "শিন (ত্রয়োদশ অক্ষর)", "Sheen"),
                VocabPair("صَاد", "সোয়াদ (চতুর্দশ অক্ষর)", "Sad"),
                VocabPair("ضَاد", "দোয়াদ (পঞ্চদশ অক্ষর)", "Dad"),
                VocabPair("طَاء", "তোয়া (ষোড়শ অক্ষর)", "Ta")
            )
            5 -> listOf(
                VocabPair("ظَاء", "জোয়া (সপ্তদশ অক্ষর)", "Zha"),
                VocabPair("عَيْن", "আইন (অষ্টাদশ অক্ষর)", "Ayn"),
                VocabPair("غَيْن", "গাইন (উনবিংশ অক্ষর)", "Ghayn"),
                VocabPair("فَاء", "ফা (বিংশ অক্ষর)", "Fa")
            )
            6 -> listOf(
                VocabPair("قَاف", "ক্বফ (একবিংশ অক্ষর)", "Qaf"),
                VocabPair("كَاف", "কাফ (দ্বাবিংশ অক্ষর)", "Kaf"),
                VocabPair("لاَم", "লাম (ত্রয়োবিংশ অক্ষর)", "Lam"),
                VocabPair("مِيم", "মিম (চতুর্বিংশ অক্ষর)", "Meem")
            )
            7 -> listOf(
                VocabPair("نُون", "নুন (পঞ্চবিংশ অক্ষর)", "Noon"),
                VocabPair("وَاو", "ওয়াও (ষড়বিংশ অক্ষর)", "Waw"),
                VocabPair("هَاء", "হা (সপ্তবিংশ অক্ষর)", "Ha"),
                VocabPair("يَاء", "ইয়া (অষ্টাবিংশ অক্ষর)", "Ya")
            )
            8 -> listOf(
                VocabPair("فَتْحَة", "জবর (টান দেওয়ার স্বরচিহ্ন)", "Fathah"),
                VocabPair("كَسْرَة", "জের (নিচের স্বরচিহ্ন)", "Kasrah"),
                VocabPair("ضَمَّة", "পেশ (উচ্চারণে ওষ্ঠ কুঞ্চন)", "Dammah"),
                VocabPair("سُكُون", "জজম / সুকুন", "Sukun")
            )
            9 -> listOf(
                VocabPair("فَتْحَتَيْنِ", "দুই জবর (তানবীন)", "Fathatayn"),
                VocabPair("كَسْرَتَيْنِ", "দুই জের (তানবীন)", "Kasratayn"),
                VocabPair("ضَمَّتَيْنِ", "দুই পেশ (তানবীন)", "Dammatayn")
            )
            10 -> listOf(
                VocabPair("مَاضِي", "অতীত কাল", "Madi"),
                VocabPair("مُضَارِع", "বর্তমান ও ভবিষ্যৎ কাল", "Mudari'"),
                VocabPair("تَشْدِيد", "তাشদীদ (দ্বিত্ব)", "Tashdeed"),
                VocabPair("جَزْم", "জজম (নীরব)", "Jazm")
            )
            15 -> listOf(
                VocabPair("كِتَابٌ", "বই / বইপত্র", "Kitabun"),
                VocabPair("قَلَمٌ", "কলম", "Qalamun"),
                VocabPair("مَكْتَبٌ", "টেবিল / ডেস্ক", "Maktabun"),
                VocabPair("مَدْرَسَةٌ", "স্কুল / বিদ্যালয়", "Madrasatun")
            )
            17 -> listOf(
                VocabPair("أَبٌ", "বাবা / পিতা", "Abun"),
                VocabPair("أُمٌّ", "মা / মাতা", "Ummun"),
                VocabPair("وَالِدِي", "আমার পিতা", "Walidee"),
                VocabPair("وَالِدَتِي", "আমার মাতা", "Walidatee")
            )
            18 -> listOf(
                VocabPair("أَخٌ", "ভাই", "Akhun"),
                VocabPair("أُخْتٌ", "বোন", "Ukhtun"),
                VocabPair("جَدٌّ", "দাদা / বৃদ্ধ", "Jaddun"),
                VocabPair("جَدَّةٌ", "দাদী / নানী", "Jaddatun")
            )
            19 -> listOf(
                VocabPair("وَاحِدٌ", "এক (১)", "Wahidun"),
                VocabPair("اِثْنَانِ", "দুই (২)", "Ithnani"),
                VocabPair("ثَلَاثَةٌ", "তিন (৩)", "Thalathatun"),
                VocabPair("أَرْبَعَةٌ", "চার (৪)", "Arba'atun"),
                VocabPair("خَمْسَةٌ", "পাঁচ (৫)", "Khamsatun")
            )
            20 -> listOf(
                VocabPair("سِتَّةٌ", "ছয় (৬)", "Sittatun"),
                VocabPair("سَبْعَةٌ", "সাত (৭)", "Sab'atun"),
                VocabPair("ثَمَانِيَةٌ", "আট (৮)", "Thamaniyatun"),
                VocabPair("تِسْعَةٌ", "নয় (৯)", "Tis'atun"),
                VocabPair("عَشَرَةٌ", "দশ (১০)", "Asharatun")
            )
            22 -> listOf(
                VocabPair("مُحَرَّم", "মহররম (১ম মাস)", "Muharram"),
                VocabPair("صَفَر", "সফর (২য় মাস)", "Safar"),
                VocabPair("رَمَضَان", "রমজান (৯ম পবিত্র মাস)", "Ramadan"),
                VocabPair("شَوَّال", "শাওয়াল (উৎসবের মাস)", "Shawwal")
            )
            23 -> listOf(
                VocabPair("أَحْمَرُ", "লাল (রঙ)", "Ahmaru"),
                VocabPair("أَزْرَقُ", "নীল", "Azraqu"),
                VocabPair("أَخْضَرُ", "সবুজ (প্রকৃতি)", "Akhdaru"),
                VocabPair("أَبْيَضُ", "সাদা (শান্তি)", "Abyadu"),
                VocabPair("أَسْوَدُ", "কালো", "Aswadu")
            )
            25 -> listOf(
                VocabPair("تُفَّاحٌ", "আপেল", "Tuffahun"),
                VocabPair("تَمْرٌ", "খেজুর (মিষ্টি)", "Tamrun"),
                VocabPair("مَوْزٌ", "কলা (ফল)", "Mawzun"),
                VocabPair("عِنَبٌ", "আঙ্গুর ফল", "Inabun")
            )
            26 -> listOf(
                VocabPair("بَطَاطِس", "আলু", "Batatis"),
                VocabPair("طَمَاطِم", "টমেটো", "Tamatim"),
                VocabPair("بَصَل", "পেঁয়াজ", "Basal"),
                VocabPair("ثُوم", "রসুন", "Thoom")
            )
            27 -> listOf(
                VocabPair("مَوْقِد", "চুলা", "Mawqid"),
                VocabPair("قِدْر", "पातিল", "Qidr"),
                VocabPair("صَحْن", "প্লেট", "Sahn"),
                VocabPair("مِلْعَقَة", "চামচ", "Mil'aqah")
            )
            31 -> listOf(
                VocabPair("رَأْسٌ", "মাথা", "Ra'sun"),
                VocabPair("شَعْرٌ", "চুল", "Sha'run"),
                VocabPair("عَيْنٌ", "চোখ", "Aynun"),
                VocabPair("أُذُنٌ", "কান", "Uthunun")
            )
            32 -> listOf(
                VocabPair("يَدٌ", "হাত", "Yadun"),
                VocabPair("رِجْلٌ", "পা", "Rijlun"),
                VocabPair("صَدْرٌ", "বুক", "Sadrun"),
                VocabPair("قَلْبٌ", "হৃদয়", "Qalbun")
            )
            34 -> listOf(
                VocabPair("أَرِيكَة", "সোফা", "Areekah"),
                VocabPair("تِلْفَاز", "টেলিভিশন", "Tilfaz"),
                VocabPair("سَاعَة", "ঘড়ি", "Sa'ah"),
                VocabPair("نَافِذَة", "জানালা", "Nafidhah")
            )
            37 -> listOf(
                VocabPair("ثَوْبٌ", "জামা / পাঞ্জাবি", "Thawbun"),
                VocabPair("قَمِيصٌ", "শার্ট", "Qameesun"),
                VocabPair("نَظَّارَةٌ", "চশমা", "Nazzaratun"),
                VocabPair("حِذَاءٌ", "জুতো", "Hitha'un")
            )
            38 -> listOf(
                VocabPair("شَارِع", "রাস্তা", "Shari'"),
                VocabPair("عِمَارَة", "দালান", "Imarah"),
                VocabPair("مَصْرِف", "ব্যাংক", "Masrif"),
                VocabPair("مُسْتَشْفَى", "হাসপাতাল", "Mustashfa")
            )
            41 -> listOf(
                VocabPair("بَقَرَة", "গরু", "Baqarah"),
                VocabPair("غَنَم", "ছাগল", "Ghanam"),
                VocabPair("أَرْنَب", "খরগোশ", "Arnab"),
                VocabPair("قِطَّة", "বিড়াল", "Qittah")
            )
            44 -> listOf(
                VocabPair("سَمَكٌ", "মাছ", "Samakun"),
                VocabPair("قِرْشٌ", "হাঙ্গর", "Qirshun"),
                VocabPair("حُوتٌ", "তিমি মাছ", "Hootun"),
                VocabPair("سُلْحَفَاةٌ", "কচ্ছপ", "Sulhafatun")
            )
            45 -> listOf(
                VocabPair("مُعَلِّمٌ", "শিক্ষক", "Mu'allimun"),
                VocabPair("طَبِيبٌ", "ডাক্তার", "Tabeebun"),
                VocabPair("مُهَنْدِسٌ", "প্রকৌশলী", "Muhandisun"),
                VocabPair("طَالِبٌ", "ছাত্র", "Talibun")
            )
            48 -> listOf(
                VocabPair("دَوَاءٌ", "ঔষধ", "Dawa'un"),
                VocabPair("حُمَّى", "জ্বর", "Humma"),
                VocabPair("أَلَمٌ", "ব্যথা", "Alamun"),
                VocabPair("عَافِيَةٌ", "সুস্থতা", "Afiyatun")
            )
            52 -> listOf(
                VocabPair("حُزْن", "দুঃখ", "Huzn"),
                VocabPair("فَرَح", "খুশি", "Farah"),
                VocabPair("غَضَب", "রাগ", "Ghadab"),
                VocabPair("خَوْف", "ভয়", "Khawf")
            )
            53 -> listOf(
                VocabPair("تَوْحِيد", "তাওহীদ", "Tawheed"),
                VocabPair("إِيمَان", "ইমান", "Iman"),
                VocabPair("تَقْوَى", "তাকওয়া", "Taqwa"),
                VocabPair("رَحْمَة", "রহমত", "Rahmah")
            )
            56 -> listOf(
                VocabPair("جَنَّة", "জান্নাত", "Jannah"),
                VocabPair("نَار", "জাহান্নাম", "Naar"),
                VocabPair("نُور", "জ্যোতি", "Noor"),
                VocabPair("قُرْآن", "কুরআন", "Quran")
            )
            58 -> listOf(
                VocabPair("فَوْقَ", "উপরে", "Fawqa"),
                VocabPair("تَحْتَ", "নিচে", "Tahta"),
                VocabPair("فِي", "ভিতরে", "Fee"),
                VocabPair("خَارِجَ", "বাইরে", "Kharija")
            )
            59 -> listOf(
                VocabPair("قَرَأَ", "সে পড়েছে", "Qara'a"),
                VocabPair("كَتَبَ", "সে লিখেছে", "Kataba"),
                VocabPair("أَكَلَ", "সে খেয়েছে", "Akala"),
                VocabPair("ذَهَبَ", "সে গিয়েছে", "Thahaba")
            )
            63 -> listOf(
                VocabPair("سَأَفْعَلُ", "আমি শীঘ্রই করব", "Sa-af'alu"),
                VocabPair("سَوْفَ نَعْمَلُ", "আমরা কাজ করব", "Sawfa Na'malu"),
                VocabPair("مُسْتَقْبَلٌ", "ভবিষ্যৎ সময়", "Mustaqbalun"),
                VocabPair("رِحْلَةٌ", "ভ্রমণ / ট্রিপ", "Rihlatun")
            )
            65 -> listOf(
                VocabPair("حِسَابٌ", "গণিত", "Hisabun"),
                VocabPair("عُلُومٌ", "বিজ্ঞান", "Uloomun"),
                VocabPair("تَارِيخٌ", "ইতিহাস", "Tareekhun"),
                VocabPair("جُغْرَافِيَا", "ভূগোল", "Jughrafiya")
            )
            68 -> listOf(
                VocabPair("وَرْدَةٌ", "গোলাপ", "Wardatun"),
                VocabPair("شَجَرَةٌ", "গাছ", "Shajaratun"),
                VocabPair("زَهْرَةٌ", "ফুল", "Zahratun"),
                VocabPair("زِرَاعَةٌ", "কৃষিকাজ", "Zira'atun")
            )
            69 -> listOf(
                VocabPair("مِفْتَاحٌ", "চাবি", "Miftahun"),
                VocabPair("قُفْلٌ", "তালা", "Quflun"),
                VocabPair("حَاسُوبٌ", "কম্পিউটার", "Hasoobun"),
                VocabPair("رَسَائِلُ", "মেসেজ", "Rasa'il")
            )
            else -> listOf(
                VocabPair("كَلِمَة", "শব্দ", "Kalimah"),
                VocabPair("جُمْلَة", "বাক্য", "Jumlah"),
                VocabPair("لُغَة", "ভাষা", "Lughah"),
                VocabPair("قِرَاءَة", "পড়া বা আবৃত্তি", "Qira'ah")
            )
        }
    }

    private fun getQuizForLesson(i: Int): List<QuizQuestion> {
        return when (i) {
            11 -> listOf(
                QuizQuestion(
                    "কোন বাক্যটি সকালের অভিবাদন 'শুভ সকাল' প্রকাশ করে?",
                    listOf("صَبَاحُ الْخَيْرِ (Sabah Al-Khayr)", "مَسَاءُ الْخَيْرِ (Masa Al-Khayr)", "تَصْبَحُ عَلَى خَيْرٍ", "شُكْرًا"),
                    0,
                    "'Sabah Al-Khayr' হল শুভ সকালের শুভেচ্ছা।"
                ),
                QuizQuestion(
                    "শুভ সকালের উত্তরে কী বলতে হয়?",
                    listOf("مَعَ السَّلَامَةِ", "صَبَاحُ النُّورِ (Sabah An-Noor)", "مَرْحَبًا", "أَهْلًا"),
                    1,
                    "উত্তরে বলতে হয় 'Sabah An-Noor' মানে নূরময় সকাল।"
                )
            )
            12 -> listOf(
                QuizQuestion(
                    "কেউ সাহায্য করলে তাকে ধন্যবাদ জানাতে কী বলতে হয়?",
                    listOf("عَفْوًا", "شُكْرًا جَزِيلًا (Shukran Jazeelan)", "صَبَاحُ الْخَيْرِ", "لَا بَأْسَ"),
                    1,
                    "'Shukran Jazeelan' অর্থ অনেক ধন্যবাদ।"
                ),
                QuizQuestion(
                    "ধন্যবাদের জবাবে 'আপনাকে স্বাগত' বা 'স্বাগতম' বুঝাতে কী ব্যবহৃত হয়?",
                    listOf("شُكْرًا", "مَرْحَبًا", "عَفْوًا (Afwan)", "مَعَ السَّلَامَةِ"),
                    2,
                    "'Afwan' মানে বিনয়ী স্বাগত ধন্যবাদ উত্তর।"
                )
            )
            14 -> listOf(
                QuizQuestion(
                    "নতুন কোনো বন্ধুর সাথে পরিচিত হতে প্রথমে কী বলা উত্তম?",
                    listOf("مَرْحَبًا، مَا اسْمُكَ؟", "كَيْفَ الْحَالُ؟", "شُكْرًا لَكَ", "مَعَ السَّلَامَةِ"),
                    0,
                    "'Marhaban, Ma Ismuka?' মানে 'হ্যালো, তোমার নাম কী?'"
                ),
                QuizQuestion(
                    "কেমন আছেন প্রশ্নের জবাবে ভালো আছি বুঝাতে কী বলে?",
                    listOf("صَبَاحُ الْخَيْرِ", "أَنَا بِخَيْرٍ، وَالْحَمْدُ لِلَّهِ", "لاَ أَعْرِفُ", "أَهْلًا وَسَهْلًا"),
                    1,
                    "'Ana bikhairin' বা ভালো আছি।"
                )
            )
            21 -> listOf(
                QuizQuestion(
                    "শনিবার এর সঠিক আরবি নাম কোনটি?",
                    listOf("يَوْمُ الأَحَدِ", "يَوْمُ السَّبْتِ (Yawmus Sabt)", "يَوْمُ الْجُمُعَةِ", "يَوْمُ الثُّلَاثَاءِ"),
                    1,
                    "'Yawmus Sabt' মানে শনিবার।"
                ),
                QuizQuestion(
                    "শুক্রবার এর পবিত্র আরবি সাপ্তাহিক নাম কোনটি?",
                    listOf("يَوْمُ الْجُمُعَةِ (Yawmul Jumu'ah)", "يَوْمُ السَّبْتِ", "يَوْمُ الْخَمِيسِ", "يَوْمُ الاِثْنَيْنِ"),
                    0,
                    "'Yawmul Jumu'ah' মানে হলো শুক্রবার।"
                )
            )
            28 -> listOf(
                QuizQuestion(
                    "সকালের নাস্তায় প্রোটিনযুক্ত খাবার 'ডিম' এর আরবি কী?",
                    listOf("خُبْزٌ", "بَيْضٌ (Baydun)", "عَسَلٌ", "حَلِيبٌ"),
                    1,
                    "'Baydun' অর্থ হলো ডিম।"
                ),
                QuizQuestion(
                    "সকালে পান করা গরম 'চা' এর খাঁটি আরবি কী?",
                    listOf("مَاءٌ", "قَهْوَةٌ", "شَايٌ (Shayun)", "عَصِيرٌ"),
                    2,
                    "'Shayun' অর্থ চা।"
                )
            )
            30 -> listOf(
                QuizQuestion(
                    "হালকা স্বাস্থ্যকর 'সালাদ' এর জনপ্রিয় আরবি শব্দ নির্ধারণ করো:",
                    listOf("شَوْرَبَة", "سَلَطَة (Salatah)", "فَاكِهَة", "خُبْز"),
                    1,
                    "'Salatah' অর্থ সালাদ।"
                )
            )
            33 -> listOf(
                QuizQuestion(
                    "বাড়ির শোবার ঘর বা বেডরুমের যথার্থ আরবি পরিভাষা কোনটি?",
                    listOf("غُرْفَةُ النَّوْمِ (Ghurfatun Nawm)", "غُرْفَةُ الجُلُوسِ", "الْمَطْبَخُ", "الْحَمَّامُ"),
                    0,
                    "'Ghurfatun Nawm' অর্থ শোবার ঘর।"
                )
            )
            36 -> listOf(
                QuizQuestion(
                    "জিনিসপত্রের 'দাম কত?' জিগ্যেস করার সবচেয়ে প্রচলিত আরবি রূপ:",
                    listOf("بِكَمْ هَذَا؟ (Bikam hatha)", "أَيْنَ الطَّرِيقُ؟", "مَا اسْمُكَ؟", "كَمِ السَّاعَةُ؟"),
                    0,
                    "'Bikam hatha?' অর্থ 'এটির দাম কত?'"
                )
            )
            42 -> listOf(
                QuizQuestion(
                    "মরুভূমির ঐতিহ্যবাহী জাহাজ খ্যাত প্রাণী 'উট' এর আরবি কী?",
                    listOf("أَسَدٌ", "جَمَلٌ (Jamalun)", "فِيلٌ", "حِصَانٌ"),
                    1,
                    "'Jamalun' অর্থ উট।"
                )
            )
            46 -> listOf(
                QuizQuestion(
                    "একজন বিশ্বস্ত 'ব্যবসায়ী' এর সঠিক আরবি প্রতিশব্দ নির্বাচন করুন:",
                    listOf("طَبِيبٌ", "كَاتِبٌ", "تَاجِرٌ (Tajirun)", "فَلَّاحٌ"),
                    2,
                    "'Tajirun' অর্থ ব্যবসায়ী বা সওদাগর।"
                )
            )
            49 -> listOf(
                QuizQuestion(
                    "পবিত্র নামাজ আদায়ের জন্য অঙ্গ ধৌতকরণ 'অজু' সম্পন্ন করাকে কি বলে?",
                    listOf("وُضُوء (Wudu)", "غُسْل", "صَلَاة", "صَوْم"),
                    0,
                    "'Wudu' বা অজু।"
                )
            )
            51 -> listOf(
                QuizQuestion(
                    "ঘড়িতে 'এখন কয়টা বাজে?' এই প্রয়োজনীয় প্রশ্নটি আরবিতে কী হবে?",
                    listOf("كَمِ السَّاعَةُ الآنَ؟ (Kamis Sa'atulan)", "بِكَمْ هَذَا؟", "مَاذَا تَفْعَلُ؟", "أَيْنَ أَنْتَ؟"),
                    0,
                    "'Kamis Sa'atul-An?' মানে এখন কয়টা বাজে?"
                )
            )
            54 -> listOf(
                QuizQuestion(
                    "পবিত্র রমজান মাসে যে ফরজ রোজা রাখা বা উপবাস করা হয়, তাকে আরবিতে কী বলা হয়?",
                    listOf("صَلَاة", "صَوْمٌ (Sawm)", "زَكَاة", "حَجّ"),
                    1,
                    "'Sawm' অর্থ রোজা।"
                )
            )
            57 -> listOf(
                QuizQuestion(
                    "কোরআনে ব্যবহৃত 'আয়াত' শব্দের অর্থ কী?",
                    listOf("ছবি", "চিহ্ন / নিদর্শন", "কবিতা", "কাহিনী"),
                    1,
                    "'Ayat' অর্থ মহান স্রষ্টার পবিত্র কুদরত ও নিদর্শন।"
                )
            )
            60 -> listOf(
                QuizQuestion(
                    "কোনো পুরুষের বেলায় 'সে পড়েছে' বুঝাতে কি বলা হয়?",
                    listOf("كَتَبَ", "قَرَأَ (Qara'a)", "يَقْرَأُ", "قَرَأَتْ"),
                    1,
                    "'Qara'a' হলো অতীত কালের রূপ।"
                )
            )
            62 -> listOf(
                QuizQuestion(
                    "আমি লিখছি বা লিখব বুঝাতে বর্তমান কালে কী বলা হয়?",
                    listOf("أَكْتُبُ (Aktubu)", "كَتَبَ", "يَكْتُبُ", "نَكْتُبُ"),
                    0,
                    "'Aktubu' মানে 'আমি লিখছি'।"
                )
            )
            66 -> listOf(
                QuizQuestion(
                    "আরবের এবং সারা বিশ্বের জনপ্রিয় খেলা 'ফুটবল' এর আরবি प्रतिশব্দ:",
                    listOf("كُرَةُ السَّلَّةِ", "كُرَةُ الْقَدَمِ (Kuratal Qadam)", "السِّبَاحَةُ", "الْجَرْيُ"),
                    1,
                    "'Kuratal Qadam' হলো ফুটবল।"
                )
            )
            70 -> listOf(
                QuizQuestion(
                    "পবিত্রতম শহর 'মক্কা' এর আরবি নাম:",
                    listOf("القَاهِرَة", "مَكَّةُ الْمُكَرَّمَةُ", "المَدِينَة", "الرِّيَاض"),
                    1,
                    "'Makkahul Mukarramah' হলো অতি সম্মানিত মক্কা নগরী।"
                )
            )
            72 -> listOf(
                QuizQuestion(
                    "আরবি বিদায় 'আল্লাহর হেফাজতে থেকো বা শুভ বিদায়' বাক্যটি কীভাবে বলা হয়?",
                    listOf("أَهْلًا وَسَهْلًا", "مَعَ السَّلَامَةِ (Ma'as Salamah)", "شُكْرًا", "كَيْفَ حَالُكَ"),
                    1,
                    "'Ma'as Salamah' হলো বিদায় নেওয়ার বাক্য।"
                ),
                QuizQuestion(
                    "কোন শব্দটির মাধ্যমে জ্ঞানার্জনের জন্য 'শিক্ষা' কে প্রকাশ করা হয়?",
                    listOf("الْمَاءُ", "الْجَنَّةُ", "التَّعْلِيمُ (Al-Ta'leem)", "الرَّجُلُ"),
                    2,
                    "'Al-Ta'leem' অর্থ হলো শিক্ষা।"
                )
            )
            else -> listOf(
                QuizQuestion(
                    "কোন আরবি হরফটি প্রথম স্বরবর্ণ প্রকাশ করে?",
                    listOf("بَاء", "أَلِف (Alif)", "تَاء", "ثَاء"),
                    1,
                    "আলিফ হচ্ছে আরবি আদি হরফ।"
                ),
                QuizQuestion(
                    "কোনটি ধন্যবাদ জ্ঞাপক শব্দ?",
                    listOf("أَهْلًا", "عَفْوًا", "شُكْرًا (Shukran)", "لَا"),
                    2,
                    "'Shukran' অর্থ ধন্যবাদ।"
                )
            )
        }
    }

    private fun getPronunciationForLesson(i: Int): List<String> {
        return when (i) {
            13 -> listOf(
                "اسْمِي عَبْدُ اللَّهِ (Ismee Abdullah - আমার নাম আবদুল্লাহ)",
                "أَنَا مِنْ بَنْغْلَادِيشَ (Ana min Bangladesh - আমি বাংলাদেশ থেকে এসেছি)",
                "أَنَا طَالِبٌ فِي الْمَدْرَسَةِ (Ana talibun fil madrasah - আমি মাদ্রাসার ছাত্র)"
            )
            16 -> listOf(
                "أَيْنَ الْقَلَمُ؟ (Aynal Qalamu? - কলমটি কোথায়?)",
                "الْقَلَمُ عَلَى الْمَكْتَبِ (Al-Qalamu 'alal maktab - কলমটি টেবিলের উপরে আছে)"
            )
            24 -> listOf(
                "الْطَّقْسُ جَمِيلٌ الْيَوْمَ (At-Taqsu jameelun al-yawm - আজকে আবহাওয়া খুব চমৎকার)",
                "الْجَوُّ بَارِدٌ جِدًّا (Al-Jawwu baridun jiddan - আবহাওয়া অনেক ঠাণ্ডা)"
            )
            29 -> listOf(
                "أَنَا آكُلُ الأُرْزَ وَالسَّمَكَ (Ana akulul urza was-samaka - আমি ভাত ও মাছ খাচ্ছি)",
                "الْطَّعَامُ لَذِيذٌ جِدًّا (At-Ta'amu latheethun jiddan - খাবারটি অত্যন্ত সুস্বাদু)"
            )
            35 -> listOf(
                "أُرِيدُ أَنْ أَنَامَ عَلَى السَّرِيرِ (Ureedu an anama 'alas sareer - আমি খাটের ওপর ঘুমাতে চাই)"
            )
            39 -> listOf(
                "أَرْكَبُ الْحَافِلَةَ كُلَّ يَوْمٍ (Arkabul hafilata kulla yawm - আমি প্রতিদিন বাসে চড়ি)"
            )
            43 -> listOf(
                "الْعُصْفُورُ يُغَرِّدُ عَلَى الشَّجَرَةِ (Al-Usfooru yugharridu 'alash shajarati - পাখিটি গাছের ডাল থেকে গান গাইছে)"
            )
            47 -> listOf(
                "أَعْمَلُ عَلَى الْحَاسُوبِ فِي الْمَكْتَبِ (A'malu 'alal hasoobi fil maktab - আমি অফিসে কম্পিউটারে কাজ করি)"
            )
            50 -> listOf(
                "أَذْهَبُ إِلَى الْمَدْرَسَةِ لِلْمُطَالَعَةِ (Athhabu ilal madrasati lil mutala'ah - আমি অধ্যয়নের জন্য স্কুলে যাচ্ছি)"
            )
            55 -> listOf(
                "سُبْحَانَ رَبِّيَ الْعَظِيمِ (Subhana Rabbiyal Azeem - আমার মহান প্রভুর পবিত্রতা ঘোষণা করছি)",
                "سُبْحَانَ رَبِّيَ الأَعْلَى (Subhana Rabbiyal A'la - আমার সর্বোচ্চ প্রভুর পবিত্রতা ঘোষণা করছি)"
            )
            61 -> listOf(
                "قَرَأْتُ كِتَابَ اللَّهِ الْقُرْآنَ (Qara'tu kitaballahi al-Quran - আমি আল্লাহর কিতাব কুরআন পড়েছি)"
            )
            64 -> listOf(
                "مَاذَا تَفْعَلُ يَا أَخِي؟ (Matha taf'alu ya akhee? - ভাই তুমি কী করছ?)",
                "أَيْنَ بَيْتُكَ الْجَمِيلُ؟ (Ayna baytukal jameel? - তোমার সুন্দর বাড়িটি কোথায়?)"
            )
            67 -> listOf(
                "الْحَمْدُ لِلَّهِ عَلَى كُلِّ حَالٍ (Alhamdulillah 'ala kulli haal - সব অবস্থায় আল্লাহর শুকরিয়া)",
                "إِنْ شَاءَ اللَّهُ سَنَنْجَحُ (In sha Allah sananjah - ইনশাআল্লাহ আমরা সফল হব)"
            )
            71 -> listOf(
                "التَّعْلِيمُ هُوَ السِّلَاحُ الأَقْوَى (At-ta'leemu huwas silahul aqwa - শিক্ষাই হলো সবচেয়ে শক্তিশালী অস্ত্র)",
                "الْوَقْتُ ثَمِينٌ جِدًّا كَالذَّهَبِ (Al-waqtu thameenun jiddan kath-thahab - সময় সোনার মতোই দামী)"
            )
            else -> listOf(
                "الصَّبْرُ مِفْتَاحُ الْفَرَجِ (As-Sabru miftahul faraj - ধৈর্যই হলো সুখের চাবিকাঠি)",
                "مَنْ جَدَّ وَجَدَ (Man jadda wajada - যে চেষ্টা করে সেই সফল হয়)"
            )
        }
    }

    private fun generate70ArabicLessons(): List<Lesson> {
        val list = mutableListOf<Lesson>()
        for (i in 1..72) {
            val difficulty = when {
                i <= 25 -> "Beginner"
                i <= 50 -> "Intermediate"
                else -> "Advanced"
            }
            val type = when (i % 3) {
                1 -> LessonType.VOCABULARY
                2 -> LessonType.QUIZ
                else -> LessonType.PRONUNCIATION
            }
            val id = "ar_seq_lesson_$i"
            val (title, arabicTitle, desc) = getThemeInfo(i)
            val vocab = if (type == LessonType.VOCABULARY) getVocabForLesson(i) else emptyList()
            val quiz = if (type == LessonType.QUIZ) getQuizForLesson(i) else emptyList()
            val pronunciation = if (type == LessonType.PRONUNCIATION) getPronunciationForLesson(i) else emptyList()

            list.add(
                Lesson(
                    id = id,
                    title = "আরবি পাঠ $i: $title ($arabicTitle)",
                    description = desc,
                    type = type,
                    targetLanguage = "AR",
                    xpReward = 20 + (i % 5) * 5,
                    difficulty = difficulty,
                    vocabPairs = vocab,
                    quizQuestions = quiz,
                    pronunciationPhrases = pronunciation
                )
            )
        }
        return list
    }
}

