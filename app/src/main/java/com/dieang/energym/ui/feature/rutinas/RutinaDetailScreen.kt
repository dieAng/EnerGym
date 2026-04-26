package com.dieang.energym.ui.feature.rutinas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieang.energym.ui.theme.*
import java.util.UUID

import androidx.compose.ui.tooling.preview.Preview
import com.dieang.energym.ui.theme.EnerGymTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RutinaDetailScreen(
    rutinaId: UUID,
    state: RutinaState,
    onLoad: (UUID) -> Unit,
    onStartWorkout: (UUID) -> Unit,
    onBack: () -> Unit
) {
    LaunchedEffect(rutinaId) {
        onLoad(rutinaId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Detalle de Rutina",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = EnerGymTextPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás", tint = EnerGymTextPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        containerColor = EnerGymBackground
    ) { padding ->
        if (state.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = EnerGymBlue)
            }
        } else {
            state.rutinaSeleccionada?.let { rutina ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    // Cabecera con Info
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .padding(20.dp)
                        ) {
                            Text(
                                text = rutina.nombre,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = EnerGymTextPrimary
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = rutina.descripcion.ifEmpty { "Plan enfocado en el desarrollo muscular y fuerza del tren superior." },
                                fontSize = 14.sp,
                                color = EnerGymTextSecondary,
                                lineHeight = 20.sp
                            )
                            Spacer(Modifier.height(16.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                BadgeInfo(text = rutina.nivel, color = EnerGymBlue)
                                BadgeInfo(text = rutina.objetivo, color = EnerGymGreen)
                                BadgeInfo(text = "${rutina.numEjercicios} Ejercicios", color = EnerGymOrange)
                            }
                        }
                    }

                    item {
                        Text(
                            text = "Lista de Ejercicios",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = EnerGymTextPrimary,
                            modifier = Modifier.padding(20.dp)
                        )
                    }

                    // Lista de Ejercicios
                    items(state.ejercicios) { ejercicio ->
                        EjercicioItem(ejercicio)
                    }

                    // Botón para empezar
                    item {
                        Spacer(Modifier.height(24.dp))
                        Padding(horizontal = 20.dp) {
                            Button(
                                onClick = { onStartWorkout(rutina.id) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = EnerGymBlue,
                                    contentColor = Color.White
                                )
                            ) {
                                Icon(Icons.Default.PlayArrow, contentDescription = null)
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    "EMPEZAR ENTRENAMIENTO",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RutinaDetailScreenPreview() {
    val rutinaId = UUID.randomUUID()
    EnerGymTheme {
        RutinaDetailScreen(
            rutinaId = rutinaId,
            state = RutinaState(
                rutinaSeleccionada = RutinaUI(
                    id = rutinaId,
                    nombre = "Empuje (Push Day)",
                    numEjercicios = 4,
                    ultimaVez = "2 días",
                    descripcion = "Enfoque en pecho, hombros y tríceps para máxima hipertrofia."
                ),
                ejercicios = listOf(
                    EjercicioUI(id = "1", nombre = "Press de Banca", series = 4, repeticiones = "8-10", descansoSeg = 90, pesoSugerido = 60f),
                    EjercicioUI(id = "2", nombre = "Press Militar", series = 3, repeticiones = "10-12", descansoSeg = 60, pesoSugerido = 40f),
                    EjercicioUI(id = "3", nombre = "Aperturas con Mancuerna", series = 3, repeticiones = "12-15", descansoSeg = 45, pesoSugerido = 12f),
                    EjercicioUI(id = "4", nombre = "Extensiones de Tríceps", series = 3, repeticiones = "15", descansoSeg = 45, pesoSugerido = 20f)
                )
            ),
            onLoad = {},
            onStartWorkout = {},
            onBack = {}
        )
    }
}

@Composable
fun Padding(horizontal: androidx.compose.ui.unit.Dp, content: @Composable () -> Unit) {
    Box(modifier = Modifier.padding(horizontal = horizontal)) {
        content()
    }
}

@Composable
fun BadgeInfo(text: String, color: Color) {
    Surface(
        color = color.copy(alpha = 0.1f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            fontSize = 12.sp,
            color = color,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun EjercicioItem(ejercicio: EjercicioUI) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Placeholder de imagen de ejercicio
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(EnerGymBackground),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.FitnessCenter, contentDescription = null, tint = EnerGymBlue.copy(alpha = 0.5f))
            }

            Spacer(Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = ejercicio.nombre,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = EnerGymTextPrimary
                )
                Spacer(Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "${ejercicio.series} x ${ejercicio.repeticiones}",
                        fontSize = 14.sp,
                        color = EnerGymTextSecondary
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Timer,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = EnerGymTextSecondary
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            "${ejercicio.descansoSeg}s",
                            fontSize = 14.sp,
                            color = EnerGymTextSecondary
                        )
                    }
                }
            }

            if (ejercicio.pesoSugerido != null) {
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "${ejercicio.pesoSugerido}kg",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = EnerGymBlue
                    )
                    Text(
                        text = "Sugerido",
                        fontSize = 10.sp,
                        color = EnerGymTextSecondary
                    )
                }
            }
        }
    }
}
