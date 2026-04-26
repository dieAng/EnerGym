package com.dieang.energym.ui.feature.ejercicios

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

import androidx.compose.ui.tooling.preview.Preview
import com.dieang.energym.domain.model.Ejercicio
import com.dieang.energym.ui.theme.EnerGymTheme
import java.util.UUID

@Composable
fun EjerciciosScreen(
    state: EjercicioState,
    onRefresh: () -> Unit
) {
    LaunchedEffect(Unit) { onRefresh() }

    Column(Modifier.fillMaxSize()) {
        Text("Ejercicios", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(16.dp))

        if (state.isLoading) CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))

        LazyColumn {
            items(state.ejercicios) { ejercicio ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(ejercicio.nombre, style = MaterialTheme.typography.titleMedium)
                        Text("Músculo: ${ejercicio.grupoMuscular}")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EjerciciosScreenPreview() {
    EnerGymTheme {
        EjerciciosScreen(
            state = EjercicioState(
                ejercicios = listOf(
                    Ejercicio(id = UUID.randomUUID(), nombre = "Press de Banca", grupoMuscular = "Pecho", equipo = "Barra", descripcion = "Ejercicio básico de empuje.", imagenUrl = null, videoUrl = null),
                    Ejercicio(id = UUID.randomUUID(), nombre = "Sentadilla", grupoMuscular = "Piernas", equipo = "Barra", descripcion = "Rey de los ejercicios de pierna.", imagenUrl = null, videoUrl = null),
                    Ejercicio(id = UUID.randomUUID(), nombre = "Dominadas", grupoMuscular = "Espalda", equipo = "Peso Corporal", descripcion = "Excelente para amplitud de espalda.", imagenUrl = null, videoUrl = null)
                )
            ),
            onRefresh = {}
        )
    }
}
