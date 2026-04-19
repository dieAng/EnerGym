package com.dieang.energym.ui.feature.home.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import android.graphics.BlurMaskFilter
import android.graphics.Paint as AndroidPaint
import androidx.compose.ui.graphics.toArgb
import com.dieang.energym.ui.theme.ElectricBlue
import com.dieang.energym.ui.theme.NeonGreen
import com.dieang.energym.ui.theme.TextGray
import com.dieang.energym.ui.theme.EnerGymDarkBackground

@Composable
fun HomeProgressCircles(
    calorias: Int,
    objetivoCalorias: Int = 2000,
    energia: Int,
    objetivoEnergia: Int = 500,
    tiempo: Int,
    objetivoTiempo: Int = 60
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .background(EnerGymDarkBackground), // Fondo oscuro para resaltar el neón
        contentAlignment = Alignment.Center
    ) {
        // Círculo de Energía (Exterior - Verde Neón)
        ProgressCircle(
            progress = (energia.toFloat() / objetivoEnergia).coerceIn(0f, 1f),
            color = NeonGreen,
            strokeWidth = 12.dp,
            size = 200.dp,
            glowRadius = 12.dp
        )

        // Círculo de Calorías (Medio - Azul Eléctrico)
        ProgressCircle(
            progress = (calorias.toFloat() / objetivoCalorias).coerceIn(0f, 1f),
            color = ElectricBlue,
            strokeWidth = 12.dp,
            size = 160.dp,
            glowRadius = 10.dp
        )

        // Círculo de Tiempo (Interior - Rosa/Fucsia)
        ProgressCircle(
            progress = (tiempo.toFloat() / objetivoTiempo).coerceIn(0f, 1f),
            color = Color(0xFFFF007A),
            strokeWidth = 12.dp,
            size = 120.dp,
            glowRadius = 8.dp
        )

        // Círculo de Calorías (Medio - Azul Eléctrico)
        ProgressCircle(
            progress = (calorias.toFloat() / objetivoCalorias).coerceIn(0f, 1f),
            color = ElectricBlue,
            strokeWidth = 14.dp,
            size = 160.dp
        )

        // Círculo de Tiempo (Interior - Rosa/Fucsia)
        ProgressCircle(
            progress = (tiempo.toFloat() / objetivoTiempo).coerceIn(0f, 1f),
            color = Color(0xFFFF007A),
            strokeWidth = 14.dp,
            size = 120.dp
        )

        // Texto central
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$energia",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 36.sp
                ),
                color = NeonGreen
            )
            Text(
                text = "WATTS",
                style = MaterialTheme.typography.labelMedium,
                color = TextGray
            )
        }
    }
}

@Composable
fun ProgressCircle(
    progress: Float,
    color: Color,
    strokeWidth: androidx.compose.ui.unit.Dp,
    size: androidx.compose.ui.unit.Dp,
    glowRadius: androidx.compose.ui.unit.Dp = 8.dp
) {
    Canvas(modifier = Modifier.size(size)) {
        val strokePx = strokeWidth.toPx()
        val glowPx = glowRadius.toPx()

        // 1. Dibujar el Brillo (Blur) usando nativeCanvas
        drawIntoCanvas { canvas ->
            val paint = AndroidPaint().apply {
                isAntiAlias = true
                this.color = color.toArgb()
                style = AndroidPaint.Style.STROKE
                this.strokeWidth = strokePx
                strokeCap = AndroidPaint.Cap.ROUND
                maskFilter = BlurMaskFilter(glowPx, BlurMaskFilter.Blur.NORMAL)
            }
            
            canvas.nativeCanvas.drawArc(
                strokePx / 2, strokePx / 2, 
                size.toPx() - strokePx / 2, size.toPx() - strokePx / 2,
                -90f, 360f * progress,
                false, paint
            )
        }

        // 2. Dibujar el Arco Central (el cable brillante)
        drawArc(
            color = color,
            startAngle = -90f,
            sweepAngle = 360f * progress,
            useCenter = false,
            style = Stroke(width = strokePx, cap = StrokeCap.Round)
        )

        // 3. Dibujar un destello blanco muy fino en el centro para el efecto "tubo neón"
        drawArc(
            color = Color.White.copy(alpha = 0.4f),
            startAngle = -90f,
            sweepAngle = 360f * progress,
            useCenter = false,
            style = Stroke(width = strokePx * 0.2f, cap = StrokeCap.Round)
        )
    }
}
