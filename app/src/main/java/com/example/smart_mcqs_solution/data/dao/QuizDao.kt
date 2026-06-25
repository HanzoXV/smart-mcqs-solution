package com.example.smart_mcqs_solution.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.smart_mcqs_solution.data.model.Quiz

@Dao
interface QuizDao {
    @Insert
    suspend fun insertQuiz(quiz: Quiz)

    @Query("SELECT * FROM quiz")
    suspend fun getAllQuizzes(): List<Quiz>

    @Query("SELECT averageScore FROM quiz")
    suspend fun getAverageScores(): List<Float>

    @Query("SELECT COUNT(*) FROM quiz")
    suspend fun getQuizCount(): Int
}