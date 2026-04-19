package com.dieang.energym.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.neonGlow(
    color: Color,
    borderRadius: Dp = 16.dp,
    glowRadius: Dp = 8.dp,
    isAnimated: Boolean = true
): Modifier {
    val infiniteTransition = rememberInfiniteTransition(label = "neon_glow")
    
    val alpha by if (isAnimated) {
        infiniteTransition.animateFloat(
            initialValue = 0.3f,
            targetValue = 0.7f,
            animationSpec = infiniteRepeatable(
                animation = tween(2000, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "glow_alpha"
        )
    } else {
        androidx.compose.runtime.rememberUpdatedState(0.5f)
    }

    val scale by if (isAnimated) {
        infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 1.02f,
            animationSpec = infiniteRepeatable(
                animation = tween(2000, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "glow_scale"
        )
    } else {
        androidx.compose.runtime.rememberUpdatedState(1f)
    }

    return this.drawWithContent {
        drawContent()
    }.then(
        Modifier
            .graphicsLayer {
                this.shadowElevation = 0f
                this.renderEffect = BlurEffect(glowRadius.toPx(), glowRadius.toPx(), TileMode.Decal)
            }
            .border(2.dp, color.copy(alpha = alpha), RoundedCornerShape(borderRadius))
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
    )
}

@Composable
fun NeonCard(
    modifier: Modifier = Modifier,
    glowColor: Color = Color(0xFF1D5DFF),
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .neonGlow(color = glowColor)
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(1.dp)
    ) {
        content()
    }
}
