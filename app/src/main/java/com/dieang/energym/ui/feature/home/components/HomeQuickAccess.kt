package com.dieang.energym.ui.feature.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeQuickAccess(
    onRecetas: () -> Unit,
    onRutinas: () -> Unit,
    onSesiones: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Button(onClick = onRecetas, modifier = Modifier.fillMaxWidth()) {
            Text("Ver Recetas")
        }
        Button(onClick = onRutinas, modifier = Modifier.fillMaxWidth()) {
            Text("Ver Rutinas")
        }
        Button(onClick = onSesiones, modifier = Modifier.fillMaxWidth()) {
            Text("Ver Sesiones")
        }
    }
}
