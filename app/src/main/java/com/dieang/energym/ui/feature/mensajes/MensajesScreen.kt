package com.dieang.energym.ui.feature.mensajes

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
fun MensajesScreen(
    state: MensajeState,
    onRefresh: () -> Unit,
    onSelectChat: (UUID, UUID) -> Unit
) {
    LaunchedEffect(Unit) { onRefresh() }

    Column(Modifier.fillMaxSize()) {
        Text("Mensajes", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(16.dp))

        if (state.isLoading) CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))

        LazyColumn {
            items(state.mensajes) { mensaje ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { onSelectChat(mensaje.emisorId, mensaje.receptorId) }
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("De: ${mensaje.emisorNombre}", style = MaterialTheme.typography.titleMedium)
                        Text(mensaje.texto)
                    }
                }
            }
        }
    }
}
