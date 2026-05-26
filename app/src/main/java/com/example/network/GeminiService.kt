package com.example.network

import com.example.BuildConfig
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

@JsonClass(generateAdapter = true)
data class Part(
    val text: String
)

@JsonClass(generateAdapter = true)
data class Content(
    val parts: List<Part>,
    val role: String = "user"
)

@JsonClass(generateAdapter = true)
data class GenerateContentRequest(
    val contents: List<Content>,
    @Json(name = "systemInstruction") val systemInstruction: Content? = null
)

@JsonClass(generateAdapter = true)
data class Candidate(
    val content: Content
)

@JsonClass(generateAdapter = true)
data class GenerateContentResponse(
    val candidates: List<Candidate>?
)

interface GeminiApi {
    @POST("v1beta/models/gemini-3.5-flash:generateContent")
    suspend fun generateContent(
        @Query("key") apiKey: String,
        @Body request: GenerateContentRequest
    ): GenerateContentResponse
}

object RetrofitClient {
    private const val BASE_URL = "https://generativelanguage.googleapis.com/"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    val apiService: GeminiApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(GeminiApi::class.java)
    }
}

suspend fun queryGemini(prompt: String, sysInstruction: String? = null): String {
    val apiKey = BuildConfig.GEMINI_API_KEY
    if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY" || apiKey == "null") {
        return "Virtual Assistant Simulation Mode:\n(To activate the live AI chatbot, please configure your GEMINI_API_KEY under the Secrets panel in AI Studio)\n\n" +
                getOfflineBotResponse(prompt)
    }

    return try {
        val contentsList = listOf(Content(parts = listOf(Part(text = prompt))))
        val systemContent = sysInstruction?.let { Content(parts = listOf(Part(text = it))) }
        val request = GenerateContentRequest(contents = contentsList, systemInstruction = systemContent)

        val response = RetrofitClient.apiService.generateContent(apiKey, request)
        response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text ?: "No response from AI Assistant."
    } catch (e: Exception) {
        "Error contacting AI model: ${e.localizedMessage ?: "Unknown network error."}\n\nFalling back to intelligent local assistant:\n${getOfflineBotResponse(prompt)}"
    }
}

private fun getOfflineBotResponse(prompt: String): String {
    val p = prompt.lowercase()
    return when {
        p.contains("hello") || p.contains("hi") || p.contains("হ্যালো") || p.contains("হাই") || p.contains("مرحبا") -> {
            "আসসালামু আলাইকুম! হ্যালো! আমি আপনার ৩ডি রোবোটিক ভাষা শিক্ষা অ্যাসিস্ট্যান্ট। আমি আপনাকে বাংলা, ইংরেজী ও আরবী শিখতে সাহায্য করতে পারি। কোনো ব্যাকরণ, ভোকাবুলারি বা উচ্চারণের সাহায্য লাগবে?\n\n(Hello! I am your 3D Robotic Language Assistant. I can help you learn Bengali, English, and Arabic. Do you need any grammar, vocabulary, or pronunciation support?)"
        }
        p.contains("english") || p.contains("ইংরেজী") || p.contains("ইংরেজি") -> {
            "ইংরেজী অনুশীলনের সেরা পদ্ধতি হলো প্রতিদিন নতুন ৫টি শব্দ মুখস্থ করা এবং তা বাক্যে প্রয়োগ করা। আমাদের 'Games' ট্যাবে গিয়ে 'Basics Vocabulary' গেমটি খেলে দেখুন!\n\n(The best way to practice English is to memorize 5 new words daily and use them. Play 'Basics Vocabulary' in the 'Games' tab!)"
        }
        p.contains("arabic") || p.contains("আরবী") || p.contains("আরবি") -> {
            "আরবী একটি অত্যন্ত সমৃদ্ধ ভাষা। আপনি 'Basics Arabic Words' দিয়ে শুরু করতে পারেন। উদাহরণস্বরূপ: 'মাবরুক' মানে অভিনন্দন এবং 'শুকরান' মানে ধন্যবাদ!\n\n(Arabic is a rich language. Start with 'Basics Arabic Words'. For example: 'Mabrouk' is Congratulations and 'Shukran' is Thanks!)"
        }
        p.contains("bengali") || p.contains("বাংলা") -> {
            "বাংলা ভাষায় স্বাগতম! বাংলা শেখার জন্য বর্ণমালা ও সাধারণ শুভেচ্ছা জানা জরুরি। যেমন: 'ধন্যবাদ' (Thank you) এবং 'কেমন আছেন' (How are you?).\n\n(Welcome to Bengali! Learn alphabet and basic greetings like 'Dhonnobad' and 'Kemon achen'.)"
        }
        p.contains("game") || p.contains("গেম") -> {
            "আমাদের এই অ্যাপে ৩ ধরণের গেম আছে:\n1. ভোকাবুলারি ম্যাচিং কার্ড গেম\n2. মাল্টিপল চয়েস কুইজ\n3. এআই উচ্চারণ বা স্পীচ রিকগনিশন গেম।\nপয়েন্ট বা রিওয়ার্ড পেতে ও ডেইলি স্ট্রিক ধরে রাখতে অংশ নিন!"
        }
        p.contains("streak") || p.contains("স্ট্রিক") -> {
            "ডেইলি স্ট্রিক আপনার নিয়মিত অনুশীলনের প্রতীক। প্রতিদিন অন্তত ১টি গেম খেলে কুইজ সমাধান করলে স্ট্রিক চালু থাকবে!"
        }
        else -> {
            "চমৎকার প্রশ্ন! ভাষা শেখার সর্বোত্তম উপায় হলো নিয়মিত অনুশীলন করা। আমার আপনার প্রশ্নের উত্তর দেওয়ার ক্ষমতা আরও চমৎকার করতে এআই স্টুডিওতে আপনার এপিআই কি কানেক্ট করুন। আরবী, বাংলা ও ইংরেজীর যেকোনো প্রশ্নের উত্তর আমি দিতে প্রস্তুত!"
        }
    }
}
