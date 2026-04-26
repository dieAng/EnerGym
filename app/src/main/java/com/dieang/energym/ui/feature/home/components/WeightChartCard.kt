package com.dieang.energym.ui.feature.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieang.energym.ui.theme.*

@Composable
fun WeightChartCard(
    pesoActual: Float,
    historial: List<Float>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Evolución de Peso",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = EnerGymTextPrimary
                    )
                    Text(
                        text = "Últimos 7 días",
                        fontSize = 12.sp,
                        color = EnerGymTextSecondary
                    )
                }
                Text(
                    text = "${pesoActual} kg",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = EnerGymBlue
                )
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
                val maxPeso = (historial.maxOrNull() ?: 1f) + 2f
                val minPeso = (historial.minOrNull() ?: 0f) - 2f
                val rango = maxPeso - minPeso

                historial.forEach { peso ->
                    val normalizedHeight = ((peso - minPeso) / rango).coerceIn(0.1f, 1f)
                    
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Box(
                            modifier = Modifier
                                .width(12.dp)
                                .fillMaxHeight(normalizedHeight)
                                .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                                .background(if (peso == pesoActual) EnerGymBlue else EnerGymLightBlue)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Etiquetas de días (Simuladas)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf("L", "M", "X", "J", "V", "S", "D").forEach { dia ->
                    Text(
                        text = dia,
                        fontSize = 10.sp,
                        color = EnerGymTextSecondary,
                        modifier = Modifier.width(12.dp)
                    )
                }
            }
        }
    }
}
