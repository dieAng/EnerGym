package com.dieang.energym.ui.feature.rutinas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.UUID

@Composable
fun RutinasScreen(
    state: RutinaState,
    onRefresh: () -> Unit,
    onSelect: (UUID) -> Unit
) {
    LaunchedEffect(Unit) { onRefresh() }

    Column(Modifier.fillMaxSize()) {
        Text("Rutinas", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(16.dp))

        if (state.isLoading) CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))

        LazyColumn {
            items(state.rutinas) { rutina ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { onSelect(rutina.id) }
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(rutina.nombre, style = MaterialTheme.typography.titleMedium)
                        Text("Ejercicios: ${rutina.ejercicios.size}")
                    }
                }
            }
        }
    }
}
