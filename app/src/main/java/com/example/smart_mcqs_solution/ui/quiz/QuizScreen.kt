package com.example.smart_mcqs_solution.ui.quiz

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smart_mcqs_solution.data.model.Question
import com.example.smart_mcqs_solution.ui.theme.CorrectGreen
import com.example.smart_mcqs_solution.ui.theme.CorrectGreenSurface
import com.example.smart_mcqs_solution.ui.theme.ErrorRed
import com.example.smart_mcqs_solution.ui.theme.ErrorRedSurface

@Composable
fun QuizScreen(
    viewModel: QuizViewModel,
    onCloseQuiz: () -> Unit
) {
    val questions = viewModel.questions
    val selectedAnswers = viewModel.selectedAnswers
    val isSubmitted = viewModel.isSubmitted

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        if (viewModel.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(questions) { question ->
                        QuestionCard(
                            question = question,
                            selectedOption = selectedAnswers[question.questionId],
                            isSubmitted = isSubmitted,
                            onOptionSelected = { option -> viewModel.selectAnswer(question.questionId, option) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (!isSubmitted) {
                    Button(
                        onClick = { viewModel.submitQuiz() },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text("Submit Quiz", fontWeight = FontWeight.SemiBold)
                    }
                } else {
                    Button(
                        onClick = onCloseQuiz,
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    ) {
                        Text("Close Review", fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }

    if (viewModel.showResultDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissDialog() },
            containerColor = MaterialTheme.colorScheme.surface,
            title = {
                Text(
                    "Quiz Results",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("Correct Answers: ${viewModel.correctCount}", color = CorrectGreen, fontWeight = FontWeight.Medium)
                    Text("Wrong Answers: ${viewModel.wrongCount}", color = ErrorRed, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Score: ${viewModel.correctCount * 10}%",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = { viewModel.dismissDialog() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text("See Answers")
                }
            }
        )
    }
}

@Composable
fun QuestionCard(
    question: Question,
    selectedOption: String?,
    isSubmitted: Boolean,
    onOptionSelected: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = question.questionText,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            question.options.forEach { option ->
                val isSelected = option == selectedOption
                val isCorrect = option == question.correctAnswer

                val borderColor = when {
                    isSubmitted && isCorrect -> CorrectGreen
                    isSubmitted && isSelected && !isCorrect -> ErrorRed
                    isSelected -> MaterialTheme.colorScheme.primary
                    else -> MaterialTheme.colorScheme.outline
                }

                val bgColor = when {
                    isSubmitted && isCorrect -> CorrectGreenSurface
                    isSubmitted && isSelected && !isCorrect -> ErrorRedSurface
                    else -> MaterialTheme.colorScheme.surface
                }

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .selectable(
                            selected = isSelected,
                            onClick = { if (!isSubmitted) onOptionSelected(option) }
                        ),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(2.dp, borderColor),
                    color = bgColor
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = isSelected,
                            onClick = { if (!isSubmitted) onOptionSelected(option) },
                            enabled = !isSubmitted,
                            colors = RadioButtonDefaults.colors(
                                selectedColor = MaterialTheme.colorScheme.primary,
                                unselectedColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = option,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}