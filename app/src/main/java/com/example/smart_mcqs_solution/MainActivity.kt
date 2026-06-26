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
import com.example.smart_mcqs_solution.data.local.AppDatabase
import com.example.smart_mcqs_solution.data.model.Question
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
// HARD-CODING THESE JUST FOR THE TASK -> CAN BE MADE DYNAMIC LATER
fun fetchQuestions(): List<Question> {
    return listOf(
        Question(
            questionText = "Which event sparked the official beginning of World War II in Europe?",
            options = listOf("The assassination of Archduke Franz Ferdinand", "The invasion of Poland", "The bombing of Pearl Harbor", "The signing of the Treaty of Versailles"),
            correctAnswer = "The invasion of Poland"
        ),
        Question(
            questionText = "In which year did the United States officially enter World War II?",
            options = listOf("1939", "1940", "1941", "1942"),
            correctAnswer = "1941"
        ),
        Question(
            questionText = "What was the code name for the Allied invasion of Normandy on June 6, 1944?",
            options = listOf("Operation Barbarossa", "Operation Overlord", "Operation Torch", "Operation Sea Lion"),
            correctAnswer = "Operation Overlord"
        ),
        Question(
            questionText = "Who was the Prime Minister of Great Britain during most of World War II?",
            options = listOf("Neville Chamberlain", "Clement Attlee", "Winston Churchill", "Anthony Eden"),
            correctAnswer = "Winston Churchill"
        ),
        Question(
            questionText = "Which battle is widely considered the major turning point on the Eastern Front, ending German advances into the Soviet Union?",
            options = listOf("The Battle of Stalingrad", "The Battle of the Bulge", "The Battle of Berlin", "The Battle of Kursk"),
            correctAnswer = "The Battle of Stalingrad"
        ),
        Question(
            questionText = "What military strategy did Germany use to quickly overwhelm Poland and France?",
            options = listOf("Trench Warfare", "Blitzkrieg", "Island Hopping", "Attrition Warfare"),
            correctAnswer = "Blitzkrieg"
        ),
        Question(
            questionText = "Which Japanese city was the first to be struck by an atomic bomb in August 1945?",
            options = listOf("Nagasaki", "Tokyo", "Hiroshima", "Kyoto"),
            correctAnswer = "Hiroshima"
        ),
        Question(
            questionText = "What was the name of the research project that developed the atomic bomb for the United States?",
            options = listOf("The Manhattan Project", "The Apollo Program", "The Manhattan Plan", "Project Trinity"),
            correctAnswer = "The Manhattan Project"
        ),
        Question(
            questionText = "Which conference in 1945 saw Churchill, Roosevelt, and Stalin discuss the post-war reorganization of Europe?",
            options = listOf("The Potsdam Conference", "The Yalta Conference", "The Tehran Conference", "The Casablanca Conference"),
            correctAnswer = "The Yalta Conference"
        ),
        Question(
            questionText = "The Battle of Midway was a decisive naval battle fought primarily between which two nations?",
            options = listOf("United States and Germany", "Great Britain and Japan", "United States and Japan", "Soviet Union and Japan"),
            correctAnswer = "United States and Japan"
        ),
        Question(
            questionText = "Who was the supreme commander of the Allied Expeditionary Forces in Europe?",
            options = listOf("Douglas MacArthur", "George S. Patton", "Bernard Montgomery", "Dwight D. Eisenhower"),
            correctAnswer = "Dwight D. Eisenhower"
        ),
        Question(
            questionText = "Which country signed a non-aggression pact with Germany in 1939, which was later broken in 1941?",
            options = listOf("The Soviet Union", "Italy", "Japan", "France"),
            correctAnswer = "The Soviet Union"
        ),
        Question(
            questionText = "What was the name of the German air force that engaged the British RAF during the Battle of Britain?",
            options = listOf("Wehrmacht", "Luftwaffe", "Kriegsmarine", "Bundeswehr"),
            correctAnswer = "Luftwaffe"
        ),
        Question(
            questionText = "Which Axis power was the first to surrender to the Allies?",
            options = listOf("Germany", "Japan", "Italy", "Hungary"),
            correctAnswer = "Italy"
        ),
        Question(
            questionText = "What international peacekeeping organization was created immediately following the end of World War II to replace the League of Nations?",
            options = listOf("NATO", "The Warsaw Pact", "The United Nations", "The European Union"),
            correctAnswer = "The United Nations"
        ),
        Question(
            questionText = "What was the name of the system of fortifications built by France along its border with Germany before WWII?",
            options = listOf("The Siegfried Line", "The Maginot Line", "The Hindenburg Line", "The Atlantic Wall"),
            correctAnswer = "The Maginot Line"
        ),
        Question(
            questionText = "Which battle fought in late 1944 was Germany's last major offensive campaign on the Western Front?",
            options = listOf("The Battle of the Bulge", "The Battle of Dunkirk", "The Battle of Somme", "The Battle of Verdun"),
            correctAnswer = "The Battle of the Bulge"
        ),
        Question(
            questionText = "Who was known as the 'Desert Fox' due to his tactical leadership of the German Afrika Korps?",
            options = listOf("Heinz Guderian", "Erwin Rommel", "Hermann Göring", "Karl Dönitz"),
            correctAnswer = "Erwin Rommel"
        ),
        Question(
            questionText = "What was the code name for the evacuation of over 330,000 Allied soldiers from the beaches of France in 1940?",
            options = listOf("Operation Dynamo", "Operation Market Garden", "Operation Sea Lion", "Operation Valkyrie"),
            correctAnswer = "Operation Dynamo"
        ),
        Question(
            questionText = "Which island was the site of a fierce 1945 Pacific battle where U.S. Marines famously raised the American flag on Mount Suribachi?",
            options = listOf("Okinawa", "Guadalcanal", "Iwo Jima", "Saipan"),
            correctAnswer = "Iwo Jima"
        ),
        Question(
            questionText = "What was the primary purpose of the Nuremberg Trials held after the war?",
            options = listOf("To redraw European borders", "To prosecute major Nazi war criminals", "To establish the United Nations", "To negotiate the surrender of Japan"),
            correctAnswer = "To prosecute major Nazi war criminals"
        ),
        Question(
            questionText = "Which treaty, signed at the end of WWI, created harsh economic conditions in Germany that contributed to the rise of WWII?",
            options = listOf("The Treaty of Versailles", "The Treaty of Paris", "The Treaty of Utrecht", "The Brest-Litovsk Treaty"),
            correctAnswer = "The Treaty of Versailles"
        ),
        Question(
            questionText = "What was the code name for Germany's massive, ill-fated invasion of the Soviet Union in June 1941?",
            options = listOf("Operation Barbarossa", "Operation Citadel", "Operation Reinhard", "Operation Winter Storm"),
            correctAnswer = "Operation Barbarossa"
        ),
        Question(
            questionText = "Who was the Emperor of Japan during World War II?",
            options = listOf("Hirohito", "Mutsuhito", "Akihito", "Hideki Tojo"),
            correctAnswer = "Hirohito"
        ),
        Question(
            questionText = "Which famous female pilot organization flew military aircraft within the United States to free up male pilots for combat?",
            options = listOf("The Night Witches", "The WAVES", "The WASPs", "The WACs"),
            correctAnswer = "The WASPs"
        ),
        Question(
            questionText = "What was the name of the German encryption machine cracked by Allied codebreakers at Bletchley Park?",
            options = listOf("The Lorenz Cipher", "The Enigma Machine", "The Purple Machine", "The Hebern Rotor"),
            correctAnswer = "The Enigma Machine"
        ),
        Question(
            questionText = "Which major Allied country fell to German forces in June 1940, leaving Britain to fight largely alone in Western Europe?",
            options = listOf("Belgium", "Poland", "France", "The Netherlands"),
            correctAnswer = "France"
        ),
        Question(
            questionText = "The Tripartite Pact of 1940 formally established the Alliance between which three nations?",
            options = listOf("Germany, Italy, and Japan", "Great Britain, USA, and USSR", "Germany, USSR, and Japan", "France, Great Britain, and USA"),
            correctAnswer = "Germany, Italy, and Japan"
        ),
        Question(
            questionText = "What strategy did Allied forces use in the Pacific theater to bypass heavily fortified Japanese islands and capture strategic ones closer to Japan?",
            options = listOf("Blitzkrieg", "Island Hopping", "Trench Warfare", "Total War"),
            correctAnswer = "Island Hopping"
        ),
        Question(
            questionText = "In which month and year did Germany officially surrender, marking the end of WWII in Europe (V-E Day)?",
            options = listOf("September 1945", "May 1945", "November 1944", "June 1944"),
            correctAnswer = "May 1945"
        )
    )
}