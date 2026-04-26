package com.dieang.energym.ui.feature.sesiones

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieang.energym.ui.components.ConfettiEffect
import com.dieang.energym.ui.theme.*

import androidx.compose.ui.tooling.preview.Preview
import com.dieang.energym.ui.theme.EnerGymTheme

@Composable
fun SesionSummaryScreen(
    resumen: SesionResumenUI,
    onShare: () -> Unit,
    onClose: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(EnerGymBlue, EnerGymBlue.copy(alpha = 0.8f))
                )
            )
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        if (resumen.energiaTotal > 50) {
            ConfettiEffect()
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(32.dp))
                .background(Color.White)
                .padding(32.dp)
        ) {
            // Icono de Éxito
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = EnerGymGreen,
                modifier = Modifier.size(80.dp)
            )
            
            Spacer(Modifier.height(16.dp))
            
            Text(
                text = "¡Entrenamiento Completado!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                color = EnerGymTextPrimary,
                textAlign = TextAlign.Center
            )
            
            Text(
                text = "Has generado energía limpia para el planeta",
                fontSize = 14.sp,
                color = EnerGymTextSecondary,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(32.dp))

            // Grid de Estadísticas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SummaryStat(icon = Icons.Default.Timer, value = resumen.tiempoTotal, label = "Tiempo", color = EnerGymTextPrimary)
                SummaryStat(icon = Icons.Default.LocalFireDepartment, value = "${resumen.caloriasTotales}", label = "kcal", color = EnerGymOrange)
                SummaryStat(icon = Icons.Default.ElectricBolt, value = "${resumen.energiaTotal} Wh", label = "Energía", color = EnerGymBlue)
            }

            Spacer(Modifier.height(32.dp))

            // Recompensas
            Surface(
                color = EnerGymBackground,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Star, contentDescription = null, tint = EnerGymOrange)
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "+${resumen.puntosGanados} Puntos EnerGym",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = EnerGymTextPrimary
                        )
                        Text(
                            text = "¡Sigue así para subir de nivel!",
                            fontSize = 12.sp,
                            color = EnerGymTextSecondary
                        )
                    }
                }
            }

            Spacer(Modifier.height(40.dp))

            Button(
                onClick = onShare,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = EnerGymBlue)
            ) {
                if (resumen.energiaTotal > 0) {
                    Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                }
                Text("Compartir en Comunidad", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            TextButton(
                onClick = onClose,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Cerrar sin compartir", color = EnerGymTextSecondary)
            }
        }
    }
}

@Composable
fun SummaryStat(icon: androidx.compose.ui.graphics.vector.ImageVector, value: String, label: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(24.dp))
        }
        Spacer(Modifier.height(8.dp))
        Text(text = value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = EnerGymTextPrimary)
        Text(text = label, fontSize = 12.sp, color = EnerGymTextSecondary)
    }
}

@Preview(showBackground = true)
@Composable
private fun SesionSummaryScreenPreview() {
    EnerGymTheme {
        SesionSummaryScreen(
            resumen = SesionResumenUI(
                tiempoTotal = "45:12",
                caloriasTotales = 450,
                energiaTotal = 120,
                puntosGanados = 250,
                logrosDesbloqueados = listOf("Rayo Veloz", "Fuerza Pura")
            ),
            onShare = {},
            onClose = {}
        )
    }
}
