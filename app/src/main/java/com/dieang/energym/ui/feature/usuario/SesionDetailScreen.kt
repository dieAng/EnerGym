package com.dieang.energym.ui.feature.usuario

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieang.energym.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SesionDetailScreen(
    state: SesionDetailState,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    fun shareSesion() {
        val shareText = """
            ⚡ ¡He generado energía con EnerGym! ⚡
            
            📅 Fecha: ${state.fecha}
            🏋️ Rutina: ${state.nombreRutina}
            ⏱️ Duración: ${state.duracion}
            🔋 Energía Generada: ${state.energia} Wh
            🔥 Calorías: ${state.calorias} kcal
            
            #EnerGym #Sostenibilidad #Fitness #EcoFriendly
        """.trimIndent()

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "Mi entrenamiento en EnerGym")
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        context.startActivity(Intent.createChooser(intent, "Compartir resumen"))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de Sesión", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                },
                actions = {
                    IconButton(onClick = { shareSesion() }) {
                        Icon(Icons.Default.Share, contentDescription = "Compartir", tint = EnerGymBlue)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = EnerGymBackground)
            )
        },
        containerColor = EnerGymBackground
    ) { padding ->
        if (state.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = EnerGymBlue)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    SesionSummaryHeader(state)
                }

                item {
                    Text(
                        "Ejercicios Realizados",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = EnerGymTextPrimary,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                items(state.ejercicios) { ejercicio ->
                    EjercicioRealizadoItem(ejercicio)
                }
                
                item { Spacer(Modifier.height(20.dp)) }
            }
        }
    }
}

@Composable
private fun SesionSummaryHeader(state: SesionDetailState) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = androidx.compose.ui.graphics.Color.White),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(state.fecha, fontSize = 14.sp, color = EnerGymTextSecondary)
            Text(state.nombreRutina, fontSize = 22.sp, fontWeight = FontWeight.Black, color = EnerGymTextPrimary)
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                DetailStatItem(Icons.Default.Timer, state.duracion, "Duración", EnerGymBlue)
                DetailStatItem(Icons.Default.ElectricBolt, "${state.energia} Wh", "Generado", EnerGymBlue)
                DetailStatItem(Icons.Default.LocalFireDepartment, "${state.calorias} kcal", "Quemado", EnerGymOrange)
            }
        }
    }
}

@Composable
private fun DetailStatItem(icon: ImageVector, value: String, label: String, color: androidx.compose.ui.graphics.Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(24.dp))
        Text(value, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = EnerGymTextPrimary)
        Text(label, fontSize = 10.sp, color = EnerGymTextSecondary)
    }
}

@Composable
private fun EjercicioRealizadoItem(ejercicio: EjercicioRealizadoUI) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = androidx.compose.ui.graphics.Color.White
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(ejercicio.nombre, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = EnerGymTextPrimary)
            Spacer(Modifier.height(8.dp))
            
            ejercicio.series.forEachIndexed { index, serie ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Serie ${index + 1}", fontSize = 14.sp, color = EnerGymTextSecondary)
                    Text("${serie.repeticiones} x ${serie.peso} kg", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = EnerGymTextPrimary)
                }
            }
        }
    }
}

data class SesionDetailState(
    val isLoading: Boolean = false,
    val fecha: String = "",
    val nombreRutina: String = "Entrenamiento Libre",
    val duracion: String = "",
    val energia: Int = 0,
    val calorias: Int = 0,
    val ejercicios: List<EjercicioRealizadoUI> = emptyList()
)

data class EjercicioRealizadoUI(
    val nombre: String,
    val series: List<SerieInfo>
)

data class SerieInfo(
    val repeticiones: Int,
    val peso: Float
)
