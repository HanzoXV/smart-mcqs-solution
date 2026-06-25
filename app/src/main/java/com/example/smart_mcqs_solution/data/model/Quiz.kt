package com.example.smart_mcqs_solution.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Quiz(
    @PrimaryKey(autoGenerate = true)
    val quizId: Int = 0,
    val correctAnswers: Int,
    val wrongAnswers: Int,
    val totalQuestions: Int,
    val averageScore: Float,
    val dateSubmitted: String,
)
