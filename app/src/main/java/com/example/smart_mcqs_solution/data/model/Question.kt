package com.example.smart_mcqs_solution.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class Question(
    @PrimaryKey(autoGenerate = true)
    val questionId: Int = 0,
    val questionText: String,
    val options: List<String>,
    val correctAnswer: String,
    val wrongAttempts: Int = 0,
    val correctAttempts: Int = 0,
    val intervalDays: Int = 1,
    val nextReviewDate: Long = 0L
)