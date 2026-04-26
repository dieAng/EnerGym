package com.dieang.energym.ui.feature.usuario.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieang.energym.ui.theme.NeonGreen
import com.dieang.energym.ui.theme.TextGray

@Composable
fun EnergyChart(
    data: List<Double>,
    modifier: Modifier = Modifier
) {
    val days = listOf("L", "M", "X", "J", "V", "S", "D")
    val maxEnergy = (data.maxOrNull() ?: 1.0).coerceAtLeast(1.0)

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(260.dp),
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "Energía Generada (Semana)",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = TextGray
            )

            Spacer(Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val canvasHeight = size.height
                    val canvasWidth = size.width
                    val barWidth = 32.dp.toPx()
                    val spacing = (canvasWidth - (barWidth * data.size)) / (data.size + 1)

                    data.forEachIndexed { index, value ->
                        val barHeight = (value / maxEnergy) * canvasHeight
                        val xOffset = spacing + index * (barWidth + spacing)
                        val yOffset = canvasHeight - barHeight.toFloat()

                        // Dibujar barra con degradado neón
                        drawRoundRect(
                            brush = Brush.verticalGradient(
                                colors = listOf(NeonGreen, NeonGreen.copy(alpha = 0.3f))
                            ),
                            topLeft = Offset(xOffset, yOffset),
                            size = Size(barWidth, barHeight.toFloat()),
                            cornerRadius = CornerRadius(8.dp.toPx())
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                days.forEach { day ->
                    Text(
                        text = day,
                        style = MaterialTheme.typography.labelSmall,
                        color = TextGray,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.width(32.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        }
    }
}
