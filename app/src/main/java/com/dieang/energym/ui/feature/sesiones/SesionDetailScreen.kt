package com.dieang.energym.ui.feature.sesiones

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.dieang.energym.domain.model.RutinaEjercicio
import com.dieang.energym.domain.model.SerieRealizada
import com.dieang.energym.ui.theme.ElectricBlue
import com.dieang.energym.ui.theme.NeonGreen
import com.dieang.energym.ui.theme.TextGray
import androidx.compose.ui.tooling.preview.Preview
import com.dieang.energym.ui.theme.EnerGymTheme
import com.dieang.energym.domain.model.Rutina
import java.util.*

@Composable
fun SesionDetailScreen(
    rutinaId: UUID,
    usuarioId: UUID,
    state: SesionState,
    onStart: (UUID, UUID) -> Unit,
    onRegisterSerie: (UUID, Int, Float) -> Unit,
    onFinish: () -> Unit,
    onBack: () -> Unit
) {
    LaunchedEffect(rutinaId) {
        onStart(rutinaId, usuarioId)
    }

    Scaffold(
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                tonalElevation = 8.dp,
                color = MaterialTheme.colorScheme.surface
            ) {
                Button(
                    onClick = onFinish,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = NeonGreen,
                        contentColor = Color.Black
                    )
                ) {
                    Text("FINALIZAR Y GUARDAR ENERGÍA", fontWeight = FontWeight.Black)
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
        ) {
            // Dashboard de Sesión Activa
            item {
                Spacer(Modifier.height(20.dp))
                SesionHeader(state.tiempoTranscurrido, state.energiaGenerada)
                Spacer(Modifier.height(24.dp))
                Text(
                    text = state.activeRutina?.nombre ?: "Entrenamiento",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(16.dp))
            }

            // Lista de Ejercicios con Registro de Series
            items(state.ejercicios) { ejercicio ->
                ExerciseTrackingCard(
                    ejercicio = ejercicio,
                    seriesCompletadas = state.seriesCompletadas[ejercicio.ejercicioId]
                        ?: emptyList(),
                    onAddSerie = { reps, peso ->
                        onRegisterSerie(
                            ejercicio.ejercicioId,
                            reps,
                            peso
                        )
                    }
                )
                Spacer(Modifier.height(16.dp))
            }

            item { Spacer(Modifier.height(100.dp)) }
        }
    }
}

@Composable
fun SesionHeader(tiempo: Long, energia: Double) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Timer,
                    contentDescription = null,
                    tint = ElectricBlue,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(6.dp))
                Text("TIEMPO", style = MaterialTheme.typography.labelMedium, color = TextGray)
            }
            Text(
                text = formatTime(tiempo),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Black,
                color = ElectricBlue
            )
        }

        VerticalDivider(modifier = Modifier.height(40.dp), color = TextGray.copy(alpha = 0.2f))

        Column(horizontalAlignment = Alignment.End) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("ENERGÍA", style = MaterialTheme.typography.labelMedium, color = TextGray)
                Spacer(Modifier.width(6.dp))
                Icon(
                    Icons.Default.ElectricBolt,
                    contentDescription = null,
                    tint = NeonGreen,
                    modifier = Modifier.size(18.dp)
                )
            }
            Text(
                text = "${String.format("%.1f", energia)} kWh",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Black,
                color = NeonGreen
            )
        }
    }
}

@Composable
fun ExerciseTrackingCard(
    ejercicio: RutinaEjercicio,
    seriesCompletadas: List<SerieRealizada>,
    onAddSerie: (Int, Float) -> Unit
) {
    var repsInput by remember { mutableStateOf("") }
    var pesoInput by remember { mutableStateOf("") }

    Surface(
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = "Ejercicio #${ejercicio.orden + 1}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${ejercicio.series} series objetivo",
                style = MaterialTheme.typography.bodySmall,
                color = TextGray
            )

            Spacer(Modifier.height(12.dp))

            // Lista de series ya registradas
            seriesCompletadas.forEachIndexed { index, serie ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(NeonGreen),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                    Spacer(Modifier.width(12.dp))
                    Text(
                        text = "Serie ${index + 1}: ${serie.repeticiones} reps x ${serie.peso} kg",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            if (seriesCompletadas.size < ejercicio.series) {
                HorizontalDivider(Modifier.padding(vertical = 12.dp), color = TextGray.copy(alpha = 0.1f))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = repsInput,
                        onValueChange = { if (it.length <= 3) repsInput = it },
                        label = { Text("Reps") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = RoundedCornerShape(12.dp)
                    )
                    OutlinedTextField(
                        value = pesoInput,
                        onValueChange = { if (it.length <= 5) pesoInput = it },
                        label = { Text("Kg") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = RoundedCornerShape(12.dp)
                    )
                    IconButton(
                        onClick = {
                            val r = repsInput.toIntOrNull() ?: 0
                            val p = pesoInput.toFloatOrNull() ?: 0f
                            if (r > 0 && p >= 0) {
                                onAddSerie(r, p)
                                repsInput = ""
                            }
                        },
                        modifier = Modifier
                            .size(56.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Icon(Icons.Default.Check, contentDescription = "Añadir")
                    }
                }
            }
        }
    }
}

fun formatTime(seconds: Long): String {
    val h = seconds / 3600
    val m = (seconds % 3600) / 60
    val s = seconds % 60
    return if (h > 0) String.format("%02d:%02d:%02d", h, m, s) else String.format("%02d:%02d", m, s)
}

@Preview(showBackground = true)
@Composable
private fun SesionDetailScreenPreview() {
    val rutinaId = UUID.randomUUID()
    val userId = UUID.randomUUID()
    val ejercicio1Id = UUID.randomUUID()
    
    EnerGymTheme {
        SesionDetailScreen(
            rutinaId = rutinaId,
            usuarioId = userId,
            state = SesionState(
                activeRutina = Rutina(id = rutinaId, usuarioId = userId, nombre = "Empuje (Push Day)", descripcion = "", nivel = "Intermedio", objetivo = "Fuerza"),
                ejercicios = listOf(
                    RutinaEjercicio(rutinaId = rutinaId, ejercicioId = ejercicio1Id, series = 4, repeticiones = 10, pesoObjetivo = 60f, descansoSeg = 90, orden = 0),
                    RutinaEjercicio(rutinaId = rutinaId, ejercicioId = UUID.randomUUID(), series = 3, repeticiones = 12, pesoObjetivo = 40f, descansoSeg = 60, orden = 1)
                ),
                seriesCompletadas = mapOf(
                    ejercicio1Id to listOf(
                        SerieRealizada(id = UUID.randomUUID(), sesionId = UUID.randomUUID(), ejercicioId = ejercicio1Id, repeticiones = 10, peso = 60f),
                        SerieRealizada(id = UUID.randomUUID(), sesionId = UUID.randomUUID(), ejercicioId = ejercicio1Id, repeticiones = 8, peso = 60f)
                    )
                ),
                tiempoTranscurrido = 1500,
                energiaGenerada = 3.5
            ),
            onStart = { _, _ -> },
            onRegisterSerie = { _, _, _ -> },
            onFinish = {},
            onBack = {}
        )
    }
}
