package com.dieang.energym.ui.feature.sesiones

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.LocalFireDepartment
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SesionActiveScreen(
    state: SesionState,
    onToggleSerie: (Int) -> Unit,
    onFinish: () -> Unit,
    onClose: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = formatTime(state.tiempoSegundos),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = EnerGymTextPrimary
                        )
                        Text(
                            text = "Sesión Activa",
                            fontSize = 12.sp,
                            color = EnerGymTextSecondary
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onClose) {
                        Icon(Icons.Default.Close, contentDescription = "Cerrar")
                    }
                },
                actions = {
                    TextButton(onClick = onFinish) {
                        Text("Terminar", color = EnerGymBlue, fontWeight = FontWeight.Bold)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = EnerGymBackground
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Panel de Progreso y Energía
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                shadowElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StatActive(icon = Icons.Default.LocalFireDepartment, value = "${state.caloriasQuemadas}", label = "kcal", color = EnerGymOrange)
                    
                    // Círculo Central de Energía
                    Box(contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(
                            progress = { state.progresoEjercicio },
                            modifier = Modifier.size(80.dp),
                            color = EnerGymBlue,
                            strokeWidth = 6.dp,
                            trackColor = EnerGymLightBlue.copy(alpha = 0.3f)
                        )
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.ElectricBolt, contentDescription = null, tint = EnerGymBlue, modifier = Modifier.size(20.dp))
                            Text(text = "${state.energiaGenerada}", fontSize = 18.sp, fontWeight = FontWeight.Black, color = EnerGymBlue)
                            Text(text = "Watts", fontSize = 8.sp, color = EnerGymTextSecondary)
                        }
                    }

                    StatActive(icon = Icons.Default.ElectricBolt, value = "4.2", label = "kWh total", color = EnerGymBlue)
                }
            }

            // Ejercicio Actual
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "EJERCICIO ACTUAL",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = EnerGymTextSecondary,
                    letterSpacing = 1.sp
                )
                Text(
                    text = state.ejercicioActual,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = EnerGymTextPrimary
                )
                Spacer(Modifier.height(16.dp))

                // Lista de Series
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(state.series) { serie ->
                        SerieRow(serie = serie, onToggle = { onToggleSerie(serie.numero) })
                    }
                }
            }
        }
    }
}

@Composable
fun StatActive(icon: androidx.compose.ui.graphics.vector.ImageVector, value: String, label: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(24.dp))
        Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = EnerGymTextPrimary)
        Text(text = label, fontSize = 10.sp, color = EnerGymTextSecondary)
    }
}

@Composable
fun SerieRow(serie: SerieUI, onToggle: () -> Unit) {
    Surface(
        onClick = onToggle,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = if (serie.completada) EnerGymBlue.copy(alpha = 0.05f) else Color.White,
        border = if (serie.completada) androidx.compose.foundation.BorderStroke(1.dp, EnerGymBlue) else null
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    modifier = Modifier.size(32.dp),
                    shape = CircleShape,
                    color = if (serie.completada) EnerGymBlue else EnerGymBackground
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        if (serie.completada) {
                            Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                        } else {
                            Text(text = "${serie.numero}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = EnerGymTextSecondary)
                        }
                    }
                }
                Spacer(Modifier.width(16.dp))
                Text(
                    text = "${serie.peso} kg x ${serie.reps} reps",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (serie.completada) EnerGymBlue else EnerGymTextPrimary
                )
            }
            
            if (!serie.completada) {
                Text(text = "Pendiente", fontSize = 12.sp, color = EnerGymTextSecondary)
            } else {
                Text(text = "¡Hecho!", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = EnerGymBlue)
            }
        }
    }
}

private fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return "%02d:%02d".format(minutes, remainingSeconds)
}
