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

            questions = questionRepository.fetchQuestions().take(10)
            isLoading = false
        }
    }

    fun selectAnswer(questionId: Int, option: String) {
        if (isSubmitted) return
        selectedAnswers = selectedAnswers + (questionId to option)
    }

    fun submitQuiz() {
        if (isSubmitted) return

        val correctIds = questions
            .filter { selectedAnswers[it.questionId] == it.correctAnswer }
            .map { it.questionId }

        val wrongIds = questions
            .filter { selectedAnswers[it.questionId] != it.correctAnswer }
            .map { it.questionId }

        val tempCorrect = correctIds.size
        val tempWrong = wrongIds.size
        val total = questions.size
        val scorePercentage = if (total > 0) (tempCorrect.toFloat() / total) * 100f else 0f
        val currentDate = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())
        val now = System.currentTimeMillis()
        val oneDayMillis = 86_400_000L

        correctCount = tempCorrect
        wrongCount = tempWrong
        isSubmitted = true
        showResultDialog = true

        viewModelScope.launch {
            // Update attempt counts
            correctIds.forEach { questionRepository.incrementCorrectAttempts(it) }
            wrongIds.forEach { questionRepository.incrementWrongAttempts(it) }

            questions.forEach { question ->
                val isCorrect = selectedAnswers[question.questionId] == question.correctAnswer

                val newInterval: Int
                val nextReviewDate: Long

                if (isCorrect) {
                    newInterval = (question.intervalDays * 2.5f).toInt().coerceAtLeast(1)
                    nextReviewDate = now + newInterval * oneDayMillis
                } else {
                    newInterval = 1
                    nextReviewDate = now + oneDayMillis
                }

                questionRepository.updateSpacedRepetition(
                    id = question.questionId,
                    intervalDays = newInterval,
                    nextReviewDate = nextReviewDate
                )
            }

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

    fun dismissDialog() {
        showResultDialog = false
    }
}