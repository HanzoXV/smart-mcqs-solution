package com.example.smart_mcqs_solution.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smart_mcqs_solution.repository.QuestionRepository
import com.example.smart_mcqs_solution.repository.QuizRepository
import com.example.smart_mcqs_solution.ui.dashboard.DashboardViewModel
import com.example.smart_mcqs_solution.ui.quiz.QuizViewModel

class SmartMcqsViewModelFactory(
    private val questionRepository: QuestionRepository,
    private val quizRepository: QuizRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DashboardViewModel(quizRepository) as T
        }
        if (modelClass.isAssignableFrom(QuizViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuizViewModel(questionRepository, quizRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}