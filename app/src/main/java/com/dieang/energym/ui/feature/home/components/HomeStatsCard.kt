package com.dieang.energym.ui.feature.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeStatsCard(
    caloriasHoy: Int,
    rutinasPendientes: Int,
    recetasGuardadas: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(16.dp)) {
            Text("Calorías hoy: $caloriasHoy")
            Text("Rutinas pendientes: $rutinasPendientes")
            Text("Recetas guardadas: $recetasGuardadas")
        }
    }
}
