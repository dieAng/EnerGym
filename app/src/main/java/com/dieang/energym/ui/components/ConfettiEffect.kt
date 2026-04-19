package com.dieang.energym.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import kotlin.random.Random

data class ParticulaConfeti(
    val initialX: Float,
    val initialY: Float,
    val color: Color,
    val width: Float,
    val height: Float,
    val fallSpeed: Float,
    val driftSpeed: Float,
    val rotationSpeed: Float,
    val initialRotation: Float
)

@Composable
fun ConfettiEffect(
    modifier: Modifier = Modifier,
    particulasCount: Int = 40,
    duracionMs: Int = 4000
) {
    val colores = listOf(
        Color(0xFF2196F3), // Azul EnerGym
        Color(0xFF4CAF50), // Verde
        Color(0xFFFFC107), // Oro
        Color(0xFFFF5722), // Naranja
        Color(0xFFE91E63), // Rosa
        Color(0xFFFFFFFF)  // Blanco
    )

    val particulas = remember {
        List(particulasCount) {
            ParticulaConfeti(
                initialX = Random.nextFloat(),
                initialY = -0.2f - Random.nextFloat() * 0.5f,
                color = colores.random(),
                width = 15f + Random.nextFloat() * 15f,
                height = 8f + Random.nextFloat() * 8f,
                fallSpeed = 0.5f + Random.nextFloat() * 0.5f,
                driftSpeed = (Random.nextFloat() - 0.5f) * 0.2f,
                rotationSpeed = (Random.nextFloat() - 0.5f) * 15f,
                initialRotation = Random.nextFloat() * 360f
            )
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "confeti_transition")
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(duracionMs, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "confeti_progress"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        particulas.forEach { p ->
            // Calcular posición actual basada en el progreso
            val yPos = (p.initialY + progress * 1.5f) % 1.2f
            val xPos = (p.initialX + progress * p.driftSpeed) % 1.0f
            val rotation = p.initialRotation + progress * p.rotationSpeed * 100

            if (yPos > -0.1f && yPos < 1.1f) {
                rotate(rotation, pivot = Offset(xPos * size.width, yPos * size.height)) {
                    drawRect(
                        color = p.color.copy(alpha = if (yPos > 0.9f) 1f - (yPos - 0.9f) * 5f else 1f),
                        topLeft = Offset(xPos * size.width, yPos * size.height),
                        size = Size(p.width, p.height)
                    )
                }
            }
        }
    }
}
