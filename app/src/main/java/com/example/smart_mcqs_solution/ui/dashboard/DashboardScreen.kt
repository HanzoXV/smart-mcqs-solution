package com.example.smart_mcqs_solution.ui.dashboard

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    onStartQuizClick: () -> Unit
) {
    val totalQuizzes = viewModel.totalQuizzes
    val averageScores = viewModel.averageScores

    val overallAccuracy = if (averageScores.isEmpty()) 0f else averageScores.average().toFloat()
    val bestScore = averageScores.maxOrNull() ?: 0f
    val recentScore = averageScores.lastOrNull() ?: 0f

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(top = 24.dp, bottom = 24.dp)
        ) {
            item {
                Text(
                    text = "Dashboard",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    StatCard(modifier = Modifier.weight(1f), label = "Quizzes Taken", value = totalQuizzes.toString())
                    StatCard(modifier = Modifier.weight(1f), label = "Overall Accuracy", value = "%.1f%%".format(overallAccuracy))
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    StatCard(modifier = Modifier.weight(1f), label = "Best Score", value = "%.1f%%".format(bestScore))
                    StatCard(modifier = Modifier.weight(1f), label = "Last Score", value = "%.1f%%".format(recentScore))
                }
            }

            item {
                Text(
                    text = "Performance Trends",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    modifier = Modifier.padding(top = 8.dp, bottom = 2.dp)
                )
            }

            item {
                ScoreLineChart(scores = averageScores)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onStartQuizClick,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation(
                        defaultElevation = 2.dp,
                        pressedElevation = 4.dp
                    )
                ) {
                    Text(
                        text = "Start New Quiz",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                    )
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
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 22.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.primary,
                    letterSpacing = (-0.5).sp
                )
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ScoreLineChart(scores: List<Float>) {
    val displayScores = if (scores.size >= 2) scores.takeLast(10) else emptyList()
    val maxScore = 100f

    val themePrimary = MaterialTheme.colorScheme.primary
    val themeSurface = MaterialTheme.colorScheme.surface
    val themeOutline = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant
    val cardBackground = MaterialTheme.colorScheme.surfaceContainerLow

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = cardBackground),
        border = BorderStroke(1.dp, themeOutline),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Score Progression",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 12.dp, start = 4.dp)
            )

            if (scores.size < 2) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Attempt more quizzes to see progression",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = onSurfaceVariant,
                            fontWeight = FontWeight.Medium
                        ),
                        textAlign = TextAlign.Center
                    )
                }
                return@Column
            }

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

                val axisTextPaint = Paint().apply {
                    color = onSurfaceVariant.toArgb()
                    textSize = 10.dp.toPx()
                    isAntiAlias = true
                }

                val valueTextPaint = Paint().apply {
                    color = themePrimary.toArgb()
                    textSize = 10.dp.toPx()
                    typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                    isAntiAlias = true
                }

                listOf(0f, 50f, 100f).forEach { value ->
                    val y = padTop + chartH * (1f - value / maxScore)

                    drawLine(
                        color = themeOutline,
                        start = Offset(padLeft, y),
                        end = Offset(w, y),
                        strokeWidth = 1.dp.toPx()
                    )

                    drawContext.canvas.nativeCanvas.drawText(
                        "${value.toInt()}%",
                        0.dp.toPx(),
                        y + 4.dp.toPx(),
                        axisTextPaint
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

                drawPath(
                    path = fillPath,
                    brush = Brush.verticalGradient(
                        colors = listOf(themePrimary.copy(alpha = 0.2f), Color.Transparent),
                        startY = padTop,
                        endY = padTop + chartH
                    )
                )

                drawPath(
                    path = path,
                    color = themePrimary,
                    style = Stroke(
                        width = 3.dp.toPx(),
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                )

                points.forEachIndexed { i, point ->
                    drawCircle(color = themePrimary, radius = 5.dp.toPx(), center = point)
                    drawCircle(color = themeSurface, radius = 3.dp.toPx(), center = point)

                    drawContext.canvas.nativeCanvas.drawText(
                        "${displayScores[i].toInt()}",
                        point.x - 8.dp.toPx(),
                        point.y - 12.dp.toPx(),
                        valueTextPaint
                    )

                    drawContext.canvas.nativeCanvas.drawText(
                        "#${scores.size - displayScores.size + i + 1}",
                        point.x - 8.dp.toPx(),
                        h - 4.dp.toPx(),
                        axisTextPaint
                    )
                }
            }
        }
    }
}