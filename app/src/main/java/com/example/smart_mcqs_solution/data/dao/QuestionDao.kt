package com.example.smart_mcqs_solution.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.smart_mcqs_solution.data.model.Question

@Dao
interface QuestionDao {

    @Query("SELECT * FROM question ORDER BY wrongAttempts DESC, RANDOM()")
    suspend fun fetchQuestions(): List<Question>

    @Query("UPDATE question SET wrongAttempts = wrongAttempts + 1 WHERE questionId = :id")
    suspend fun incrementWrongAttempts(id: Int)
}