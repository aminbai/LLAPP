package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.ui.MainViewModel
import com.example.ui.components.*
import com.example.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val mainViewModel = androidx.lifecycle.ViewModelProvider(this)[MainViewModel::class.java]
        setContent {
            val profile by mainViewModel.userProfile.collectAsState()
            val currentTab by mainViewModel.currentTab.collectAsState()
            val toastMsg by mainViewModel.notificationToast.collectAsState()

            // Handle alert notifications on demand
            val context = LocalContext.current
            LaunchedEffect(toastMsg) {
                toastMsg?.let {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    mainViewModel.clearToast()
                }
            }

            // Real dynamic theme selector observing the local profile state in SQLite!
            MyApplicationTheme(darkTheme = profile.isDarkMode) {
                Scaffold(
                    modifier = Modifier.fillMaxSize().frostedBackground(),
                    containerColor = Color.Transparent,
                    bottomBar = {
                        NavigationBar(
                            modifier = Modifier.height(84.dp),
                            containerColor = Color(0xFF0F172A).copy(alpha = 0.5f)
                        ) {
                            val curLang = profile.nativeLanguage

                            val navItems = listOf(
                                Triple("HOME", Icons.Default.Home, "ড্যাশবোর্ড"),
                                Triple("GAMES", Icons.Default.PlayArrow, "গেমস"),
                                Triple("CHATBOT", Icons.Default.Send, "এআই টিউটর"),
                                Triple("LEADERBOARD", Icons.Default.Star, "লিডার"),
                                Triple("PLAN", Icons.Default.DateRange, "প্ল্যান"),
                                Triple("SETTINGS", Icons.Default.Settings, "কন্ট্রোল")
                            )

                            navItems.forEach { (tabId, icon, label_bn) ->
                                val label = when (tabId) {
                                    "HOME" -> Translator.get("welcome", curLang).take(5)
                                    "GAMES" -> Translator.get("games", curLang).split(" ")[0]
                                    "CHATBOT" -> "Tutor AI"
                                    "LEADERBOARD" -> Translator.get("leaderboard", curLang).take(6)
                                    "PLAN" -> "Plan"
                                    else -> "Control"
                                }

                                NavigationBarItem(
                                    selected = currentTab == tabId,
                                    onClick = { mainViewModel.navigateTo(tabId) },
                                    icon = { Icon(imageVector = icon, contentDescription = tabId) },
                                    label = { Text(label, style = MaterialTheme.typography.labelSmall) }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        when (currentTab) {
                            "HOME" -> DashboardScreen(viewModel = mainViewModel)
                            "GAMES" -> GamesScreen(viewModel = mainViewModel)
                            "CHATBOT" -> ChatbotScreen(viewModel = mainViewModel)
                            "LEADERBOARD" -> LeaderboardScreen(viewModel = mainViewModel)
                            "PLAN" -> PersonalPlanScreen(viewModel = mainViewModel)
                            "SETTINGS" -> SettingsScreen(viewModel = mainViewModel)
                            "GRAMMAR" -> ArabicGrammarAcademyScreen(viewModel = mainViewModel, onBack = { mainViewModel.navigateTo("HOME") })
                        }
                    }
                }
            }
        }
    }
}
