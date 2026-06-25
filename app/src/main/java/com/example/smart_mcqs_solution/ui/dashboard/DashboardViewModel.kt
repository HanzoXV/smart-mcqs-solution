package com.example.smart_mcqs_solution.ui.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smart_mcqs_solution.repository.QuizRepository
import kotlinx.coroutines.launch

class DashboardViewModel(private val quizRepository: QuizRepository) : ViewModel() {
    init {
        loadDashboardStats()
    }
    var totalQuizzes by mutableIntStateOf(0)
        private set

    var averageScores by mutableStateOf<List<Float>>(emptyList())
        private set

    fun loadDashboardStats() {
        viewModelScope.launch {
            totalQuizzes = quizRepository.getQuizCount()
            averageScores = quizRepository.getAverageScores()
        }
    }
}