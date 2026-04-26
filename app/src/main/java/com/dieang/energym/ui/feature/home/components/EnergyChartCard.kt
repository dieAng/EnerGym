package com.dieang.energym.ui.feature.home.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieang.energym.ui.theme.*

@Composable
fun EnergyChartCard(
    modifier: Modifier = Modifier,
    semanalWh: List<Int>,
    totalSemana: Int
) {
    var isVisible by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        isVisible = true
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // ... (rest of the code same until Row)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Energía Generada",
                        fontSize = 14.sp,
                        color = EnerGymTextSecondary
                    )
                    Text(
                        text = "$totalSemana Wh",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black,
                        color = EnerGymBlue
                    )
                }
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(EnerGymBlue.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.ElectricBolt, contentDescription = null, tint = EnerGymBlue)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Gráfico de Barras Simple
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                val dias = listOf("L", "M", "X", "J", "V", "S", "D")
                val maxVal = (semanalWh.maxOrNull() ?: 1).coerceAtLeast(100)

                semanalWh.forEachIndexed { index, valWh ->
                    BarItem(
                        value = valWh,
                        maxVal = maxVal,
                        label = dias[index],
                        isToday = index == getCurrentDayIndex(),
                        isVisible = isVisible,
                        animationDelay = index * 100
                    )
                }
            }
        }
    }
}

@Composable
private fun BarItem(
    value: Int, 
    maxVal: Int, 
    label: String, 
    isToday: Boolean,
    isVisible: Boolean,
    animationDelay: Int
) {
    val targetHeight = (value.toFloat() / maxVal).coerceIn(0.05f, 1f)
    
    val animatedHeight by animateFloatAsState(
        targetValue = if (isVisible) targetHeight else 0f,
        animationSpec = tween(durationMillis = 1000, delayMillis = animationDelay),
        label = "BarHeightAnimation"
    )
    
    val color = if (isToday) EnerGymBlue else EnerGymBlue.copy(alpha = 0.3f)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.fillMaxHeight()
    ) {
        Box(
            modifier = Modifier
                .width(12.dp)
                .fillMaxHeight(animatedHeight)
                .clip(RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                .background(color)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = if (isToday) FontWeight.Bold else FontWeight.Medium,
            color = if (isToday) EnerGymTextPrimary else EnerGymTextSecondary
        )
    }
}

private fun getCurrentDayIndex(): Int {
    val calendar = java.util.Calendar.getInstance()
    return (calendar.get(java.util.Calendar.DAY_OF_WEEK) + 5) % 7
}
