package com.example.smart_mcqs_solution.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.smart_mcqs_solution.data.dao.QuestionDao
import com.example.smart_mcqs_solution.data.dao.QuizDao
import com.example.smart_mcqs_solution.data.model.Question
import com.example.smart_mcqs_solution.data.model.Quiz

@Database(
    entities = [Question::class,
        Quiz::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
    abstract fun quizDao(): QuizDao
}