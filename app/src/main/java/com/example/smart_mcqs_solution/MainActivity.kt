package com.example.smart_mcqs_solution

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.smart_mcqs_solution.data.fetchQuestions
import com.example.smart_mcqs_solution.data.local.AppDatabase
import com.example.smart_mcqs_solution.navigation.Screen
import com.example.smart_mcqs_solution.repository.QuestionRepository
import com.example.smart_mcqs_solution.repository.QuizRepository
import com.example.smart_mcqs_solution.ui.SmartMcqsViewModelFactory
import com.example.smart_mcqs_solution.ui.dashboard.DashboardScreen
import com.example.smart_mcqs_solution.ui.dashboard.DashboardViewModel
import com.example.smart_mcqs_solution.ui.quiz.QuizScreen
import com.example.smart_mcqs_solution.ui.quiz.QuizViewModel
import com.example.smart_mcqs_solution.ui.splash.SplashScreen
import com.example.smart_mcqs_solution.ui.theme.SmartMcqsSolutionTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var database: AppDatabase
    private lateinit var questionRepository: QuestionRepository
    private lateinit var quizRepository: QuizRepository
    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var quizViewModel: QuizViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "smart_mcqs_db"
        ).build()

        questionRepository = QuestionRepository(database.questionDao())
        quizRepository = QuizRepository(database.quizDao())

        val factory = SmartMcqsViewModelFactory(questionRepository, quizRepository)
        dashboardViewModel = ViewModelProvider(this, factory)[DashboardViewModel::class.java]
        quizViewModel = ViewModelProvider(this, factory)[QuizViewModel::class.java]

        lifecycleScope.launch(Dispatchers.IO) {
            if (questionRepository.getQuestionCount() == 0) {
                val seedQuestions = fetchQuestions()
                questionRepository.insertQuestions(seedQuestions)
            }
        }

        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()

            SmartMcqsSolutionTheme {
                NavHost(
                    navController = navController,
                    startDestination = Screen.Splash.route
                ) {
                    composable(Screen.Splash.route) {
                        SplashScreen(navController = navController)
                    }

                    composable(Screen.Dashboard.route) {
                        DashboardScreen(
                            viewModel = dashboardViewModel,
                            onStartQuizClick = {
                                quizViewModel.startNewQuiz()
                                navController.navigate(Screen.Quiz.route)
                            }
                        )
                    }
                    composable(Screen.Quiz.route) {
                        QuizScreen(
                            viewModel = quizViewModel,
                            onCloseQuiz = {
                                dashboardViewModel.loadDashboardStats()
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}