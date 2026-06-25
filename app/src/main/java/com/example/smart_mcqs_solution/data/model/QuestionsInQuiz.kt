package com.example.smart_mcqs_solution.data.model

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["quizId", "questionId"],
    foreignKeys = [
        ForeignKey(entity = Quiz::class, parentColumns = ["quizId"], childColumns = ["quizId"]),
        ForeignKey(entity = Question::class, parentColumns = ["questionId"], childColumns = ["questionId"])
    ]
)
data class QuestionsInQuiz(
    val quizId: Int,
    @androidx.room.ColumnInfo(index = true) val questionId: Int
)