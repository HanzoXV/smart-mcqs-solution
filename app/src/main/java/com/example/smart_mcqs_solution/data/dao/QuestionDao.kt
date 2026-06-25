package com.example.smart_mcqs_solution.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.smart_mcqs_solution.data.model.Question
import com.example.smart_mcqs_solution.data.model.QuestionsInQuiz

@Dao
interface QuestionDao {

    @Query("SELECT * FROM question ORDER BY wrongAttempts DESC, RANDOM()")
    suspend fun fetchQuestions(): List<Question>

    @Query("UPDATE question SET wrongAttempts = wrongAttempts + 1 WHERE questionId = :id")
    suspend fun incrementWrongAttempts(id: Int)

    @Query("SELECT questionId FROM questionsinquiz WHERE quizId = :quizId")
    suspend fun getQuestionIdsByQuiz(quizId: Int): List<Int>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertQuestions(questions: List<Question>)

    @Query("SELECT COUNT(*) FROM question")
    suspend fun getQuestionCount(): Int
}