package com.example.smart_mcqs_solution.repository

import com.example.smart_mcqs_solution.data.dao.QuestionDao
import com.example.smart_mcqs_solution.data.model.Question

class QuestionRepository(private val questionDao: QuestionDao) {

    suspend fun fetchQuestions(): List<Question> {
        return questionDao.fetchQuestions()
    }

    suspend fun incrementWrongAttempts(id: Int) {
        questionDao.incrementWrongAttempts(id)
    }

    suspend fun getQuestionByQuizId(id: Int): List<Int> {
        return questionDao.getQuestionIdsByQuiz(id)
    }

    suspend fun insertQuestions(questions: List<Question>) {
        questionDao.insertQuestions(questions)
    }

    suspend fun getQuestionCount(): Int {
        return questionDao.getQuestionCount()
    }

    suspend fun incrementCorrectAttempts(id: Int) {
        questionDao.incrementCorrectAttempts(id)
    }
}