package com.example.smart_mcqs_solution.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.geometry.Offset
import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.nativeCanvas

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    onStartQuizClick: () -> Unit
) {
    val totalQuizzes = viewModel.totalQuizzes
    val averageScores = viewModel.averageScores

    val overallAccuracy = if (averageScores.isEmpty()) 0f
    else averageScores.average().toFloat()

    val bestScore = averageScores.maxOrNull() ?: 0f
    val recentScore = averageScores.lastOrNull() ?: 0f

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Dashboard",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard(
                        modifier = Modifier.weight(1f),
                        label = "Quizzes Taken",
                        value = totalQuizzes.toString()
                    )
                    StatCard(
                        modifier = Modifier.weight(1f),
                        label = "Overall Accuracy",
                        value = "%.1f%%".format(overallAccuracy)
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard(
                        modifier = Modifier.weight(1f),
                        label = "Best Score",
                        value = "%.1f%%".format(bestScore)
                    )
                    StatCard(
                        modifier = Modifier.weight(1f),
                        label = "Last Score",
                        value = "%.1f%%".format(recentScore)
                    )
                }
            }

            if (averageScores.isNotEmpty()) {
                item {
                    Text(
                        text = "Score History",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                item {
                    ScoreLineChart(scores = averageScores)
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = onStartQuizClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    Text("Start Quiz", fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
fun StatCard(
    modifier: Modifier = Modifier,
    label: String,
    value: String
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(text = value, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text(text = label, fontSize = 12.sp, color = Color.Gray)
        }
    }
}

@Composable
fun ScoreLineChart(scores: List<Float>) {
    if (scores.isEmpty()) return

    val displayScores = scores.takeLast(10)
    val maxScore = 100f

    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Score Progression",
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                val w = size.width
                val h = size.height
                val padLeft = 40.dp.toPx()
                val padBottom = 24.dp.toPx()
                val padTop = 16.dp.toPx()
                val chartW = w - padLeft
                val chartH = h - padBottom - padTop

                listOf(0f, 50f, 100f).forEach { value ->
                    val y = padTop + chartH * (1f - value / maxScore)
                    drawLine(
                        color = Color(0xFFE2E8F0),
                        start = Offset(padLeft, y),
                        end = Offset(w, y),
                        strokeWidth = 1.dp.toPx()
                    )
                    drawContext.canvas.nativeCanvas.drawText(
                        "${value.toInt()}",
                        4.dp.toPx(),
                        y + 4.dp.toPx(),
                        android.graphics.Paint().apply {
                            color = android.graphics.Color.parseColor("#4A5568")
                            textSize = 10.dp.toPx()
                        }
                    )
                }

                val points = displayScores.mapIndexed { i, score ->
                    val x = padLeft + i * (chartW / (displayScores.size - 1).coerceAtLeast(1))
                    val y = padTop + chartH * (1f - score / maxScore)
                    Offset(x, y)
                }

                val path = Path().apply {
                    points.forEachIndexed { i, point ->
                        if (i == 0) moveTo(point.x, point.y) else lineTo(point.x, point.y)
                    }
                }

                val fillPath = Path().apply {
                    addPath(path)
                    lineTo(points.last().x, padTop + chartH)
                    lineTo(points.first().x, padTop + chartH)
                    close()
                }
                drawPath(fillPath, brush = Brush.verticalGradient(
                    colors = listOf(Color(0x330D1B2A), Color(0x000D1B2A)),
                    startY = padTop,
                    endY = padTop + chartH
                ))

                drawPath(path, color = Color(0xFF0D1B2A), style = Stroke(
                    width = 2.5.dp.toPx(),
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                ))

                points.forEachIndexed { i, point ->
                    drawCircle(color = Color(0xFFFFC845), radius = 5.dp.toPx(), center = point)
                    drawCircle(color = Color(0xFF0D1B2A), radius = 3.dp.toPx(), center = point)

                    drawContext.canvas.nativeCanvas.drawText(
                        "${displayScores[i].toInt()}%",
                        point.x - 10.dp.toPx(),
                        point.y - 8.dp.toPx(),
                        android.graphics.Paint().apply {
                            color = android.graphics.Color.parseColor("#0D1B2A")
                            textSize = 9.dp.toPx()
                            isFakeBoldText = true
                        }
                    )

                    drawContext.canvas.nativeCanvas.drawText(
                        "#${scores.size - displayScores.size + i + 1}",
                        point.x - 8.dp.toPx(),
                        h - 4.dp.toPx(),
                        android.graphics.Paint().apply {
                            color = android.graphics.Color.parseColor("#4A5568")
                            textSize = 9.dp.toPx()
                        }
                    )
                }
            }
        }
    }
}