package com.dieang.energym.ui.feature.mensajes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieang.energym.ui.theme.NeonGreen
import com.dieang.energym.ui.theme.TextGray
import java.text.SimpleDateFormat
import java.util.*

import androidx.compose.ui.tooling.preview.Preview
import com.dieang.energym.ui.theme.EnerGymTheme

@Composable
fun MensajesScreen(
    state: MensajeState,
    onLoad: () -> Unit,
    onChatClick: (UUID) -> Unit
) {
    LaunchedEffect(Unit) {
        onLoad()
    }

    Scaffold(
        topBar = {
            Column(Modifier.padding(horizontal = 24.dp, vertical = 20.dp)) {
                Text(
                    text = "Mensajes",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = (-1).sp
                    )
                )
                Text(
                    text = "Conecta con otros atletas",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextGray
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Buscador (Simulado)
            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                placeholder = { Text("Buscar atleta...", color = TextGray) },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = null,
                        tint = TextGray
                    )
                },
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface
                )
            )

            Spacer(Modifier.height(16.dp))

            if (state.isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = NeonGreen)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    items(state.chats) { chat ->
                        ChatItem(chat = chat, onClick = { onChatClick(chat.contactoId) })
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 24.dp),
                            color = TextGray.copy(alpha = 0.1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ChatItem(chat: ChatPreview, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(NeonGreen.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = chat.nombreContacto.take(1).uppercase(),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Black,
                color = NeonGreen
            )
        }

        Spacer(Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = chat.nombreContacto,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = formatTime(chat.fecha),
                    style = MaterialTheme.typography.labelSmall,
                    color = TextGray
                )
            }
            Text(
                text = chat.ultimoMensaje,
                style = MaterialTheme.typography.bodyMedium,
                color = TextGray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

fun formatTime(timestamp: Long): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

@Preview(showBackground = true)
@Composable
private fun MensajesScreenPreview() {
    EnerGymTheme {
        MensajesScreen(
            state = MensajeState(
                chats = listOf(
                    ChatPreview(
                        contactoId = UUID.randomUUID(),
                        nombreContacto = "Juan Pérez",
                        ultimoMensaje = "Genial, nos vemos ahí.",
                        fecha = System.currentTimeMillis(),
                        sinLeer = 0
                    ),
                    ChatPreview(
                        contactoId = UUID.randomUUID(),
                        nombreContacto = "Maria García",
                        ultimoMensaje = "¿Tienes la receta de los panqueques?",
                        fecha = System.currentTimeMillis() - 3600000,
                        sinLeer = 2
                    ),
                    ChatPreview(
                        contactoId = UUID.randomUUID(),
                        nombreContacto = "Carlos Ruiz",
                        ultimoMensaje = "¡Buen entrenamiento!",
                        fecha = System.currentTimeMillis() - 86400000,
                        sinLeer = 0
                    )
                )
            ),
            onLoad = {},
            onChatClick = {}
        )
    }
}
