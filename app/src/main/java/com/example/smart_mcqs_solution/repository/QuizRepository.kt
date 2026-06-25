package com.example.smart_mcqs_solution.repository

import com.example.smart_mcqs_solution.data.dao.QuizDao
import com.example.smart_mcqs_solution.data.model.QuestionsInQuiz
import com.example.smart_mcqs_solution.data.model.Quiz

class QuizRepository(private val quizDao: QuizDao) {

    suspend fun insertQuiz(quiz: Quiz): Long {
        return quizDao.insertQuiz(quiz)
    }

    suspend fun getAllQuizzes(): List<Quiz> {
        return quizDao.getAllQuizzes()
    }

    suspend fun getAverageScores(): List<Float> {
        return quizDao.getAverageScores()
    }

    suspend fun getQuizCount(): Int {
        return quizDao.getQuizCount()
    }

    suspend fun insertQuizQuestions(questions: List<QuestionsInQuiz>) {
        quizDao.insertQuizQuestions(questions)
    }
}