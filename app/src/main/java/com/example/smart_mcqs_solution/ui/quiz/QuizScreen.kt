package com.example.smart_mcqs_solution.ui.quiz

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        if (viewModel.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 4.dp
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = if (isSubmitted) "Review Assessment" else "Quiz",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
                Text(
                    text = if (isSubmitted) "Analyze your results below" else "Select the correct answer for each statement",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    ),
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    itemsIndexed(questions, key = { _, question -> question.questionId }) { index, question ->
                        QuestionCard(
                            questionNumber = index + 1,
                            question = question,
                            selectedOption = selectedAnswers[question.questionId],
                            isSubmitted = isSubmitted,
                            onOptionSelected = { option -> viewModel.selectAnswer(question.questionId, option) }
                        )
                    }
                }
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        Spacer(modifier = Modifier.height(12.dp))
                        if (!isSubmitted) {
                            Button(
                                onClick = { viewModel.submitQuiz() },
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = MaterialTheme.colorScheme.onPrimary
                                ),
                                elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 2.dp)
                            ) {
                                Text("Submit Answers", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                            }
                        } else {
                            Button(
                                onClick = onCloseQuiz,
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            ) {
                                Text("Return to Dashboard", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                            }
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }

    if (viewModel.showResultDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissDialog() },
            shape = RoundedCornerShape(24.dp),
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            title = {
                Text(
                    "Performance Summary",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                ) {
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Text("Correct breakdown:", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("${viewModel.correctCount} right", color = CorrectGreen, fontWeight = FontWeight.Bold)
                    }
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Text("Incorrect breakdown:", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("${viewModel.wrongCount} wrong", color = ErrorRed, fontWeight = FontWeight.Bold)
                    }
                    Divider(color = MaterialTheme.colorScheme.outlineVariant, modifier = Modifier.padding(vertical = 4.dp))
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Text("Final Score:", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                        Text(
                            "${viewModel.correctCount * 10}%",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { viewModel.dismissDialog() },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text("Review Corrections", fontWeight = FontWeight.Bold)
                }
            }
        )
    }
}

@Composable
fun QuestionCard(
    questionNumber: Int,
    question: Question,
    selectedOption: String?,
    isSubmitted: Boolean,
    onOptionSelected: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "QUESTION $questionNumber",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    letterSpacing = 1.sp
                ),
                modifier = Modifier.padding(bottom = 6.dp)
            )

            Text(
                text = question.questionText,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 24.sp
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            question.options.forEachIndexed { index, option ->
                val isSelected = option == selectedOption
                val isCorrect = option == question.correctAnswer

                val optionLetter = ('A' + index).toString()

                val borderColor = when {
                    isSubmitted && isCorrect -> CorrectGreen
                    isSubmitted && isSelected && !isCorrect -> ErrorRed
                    isSelected -> MaterialTheme.colorScheme.primary
                    else -> MaterialTheme.colorScheme.outlineVariant
                }

                val bgColor = when {
                    isSubmitted && isCorrect -> CorrectGreenSurface
                    isSubmitted && isSelected && !isCorrect -> ErrorRedSurface
                    isSelected -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                    else -> MaterialTheme.colorScheme.surfaceContainerLowest
                }

                val contentTextColor = when {
                    isSubmitted && isCorrect -> CorrectGreen
                    isSubmitted && isSelected && !isCorrect -> ErrorRed
                    isSelected -> MaterialTheme.colorScheme.primary
                    else -> MaterialTheme.colorScheme.onSurface
                }

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .clickable(enabled = !isSubmitted) { onOptionSelected(option) },
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(if (isSelected || (isSubmitted && isCorrect)) 2.dp else 1.dp, borderColor),
                    color = bgColor
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .background(
                                    color = if (isSelected) contentTextColor else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = optionLetter,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = if (isSelected || (isSubmitted && isCorrect)) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSubmitted && (isCorrect || isSelected)) contentTextColor else MaterialTheme.colorScheme.onSurface
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}