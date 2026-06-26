package com.example.smart_mcqs_solution.ui.quiz

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smart_mcqs_solution.data.model.Question
import com.example.smart_mcqs_solution.data.model.QuestionsInQuiz
import com.example.smart_mcqs_solution.data.model.Quiz
import com.example.smart_mcqs_solution.repository.QuestionRepository
import com.example.smart_mcqs_solution.repository.QuizRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class QuizViewModel(
    private val questionRepository: QuestionRepository,
    private val quizRepository: QuizRepository
) : ViewModel() {

    var questions by mutableStateOf<List<Question>>(emptyList())
        private set

    var selectedAnswers by mutableStateOf<Map<Int, String>>(emptyMap())
        private set

    var isSubmitted by mutableStateOf(false)
        private set

    var showResultDialog by mutableStateOf(false)
        private set

    var correctCount by mutableIntStateOf(0)
        private set

    var wrongCount by mutableIntStateOf(0)
        private set

    var isLoading by mutableStateOf(true)
        private set

    fun startNewQuiz() {
        viewModelScope.launch {
            isLoading = true
            isSubmitted = false
            showResultDialog = false
            selectedAnswers = emptyMap()
            correctCount = 0
            wrongCount = 0

            val allQuestions = questionRepository.fetchQuestions()
            questions = allQuestions.take(10)
            isLoading = false
        }
    }

    fun selectAnswer(questionId: Int, option: String) {
        if (isSubmitted) return

        selectedAnswers = selectedAnswers + (questionId to option)
    }

    fun submitQuiz() {
        if (isSubmitted)
            return

        var tempCorrect = 0
        var tempWrong = 0

        val wrongQuestionIds = questions.filter { selectedAnswers[it.questionId] != it.correctAnswer }.map { it.questionId }
        val correctIds = questions.filter { selectedAnswers[it.questionId] == it.correctAnswer }.map { it.questionId }

        viewModelScope.launch {
            correctIds.forEach {
                questionRepository.incrementCorrectAttempts(it)
            }
            wrongQuestionIds.forEach { id ->
                questionRepository.incrementWrongAttempts(id)
            }

            questions.forEach { question ->
                val userAnswer = selectedAnswers[question.questionId]
                if (userAnswer == question.correctAnswer) {
                    tempCorrect++
                } else {
                    tempWrong++
                }
            }

            correctCount = tempCorrect
            wrongCount = tempWrong

            val total = questions.size
            val scorePercentage = if (total > 0) (tempCorrect.toFloat() / total) * 100f else 0f
            val currentDate =
                SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())

            isSubmitted = true
            showResultDialog = true

            viewModelScope.launch {
                val newQuiz = Quiz(
                    correctAnswers = tempCorrect,
                    wrongAnswers = tempWrong,
                    totalQuestions = total,
                    averageScore = scorePercentage,
                    dateSubmitted = currentDate
                )

                val generatedQuizId = quizRepository.insertQuiz(newQuiz).toInt()

                val junctionEntries = questions.map { question ->
                    QuestionsInQuiz(quizId = generatedQuizId, questionId = question.questionId)
                }
                quizRepository.insertQuizQuestions(junctionEntries)
            }
        }
    }

    fun dismissDialog() {
        showResultDialog = false
    }
}