package com.dieang.energym.ui.feature.mensajes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieang.energym.domain.model.Mensaje
import com.dieang.energym.domain.model.Usuario
import com.dieang.energym.ui.theme.NeonGreen
import com.dieang.energym.ui.theme.TextGray
import androidx.compose.ui.tooling.preview.Preview
import com.dieang.energym.ui.theme.EnerGymTheme
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    contactoId: UUID,
    usuarioLogueadoId: UUID,
    state: MensajeState,
    onLoad: (UUID) -> Unit,
    onSend: (UUID, String) -> Unit,
    onBack: () -> Unit
) {
    var nuevoMensaje by remember { mutableStateOf("") }

    LaunchedEffect(contactoId) {
        onLoad(contactoId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(NeonGreen.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = state.contactoActual?.nombre?.take(1) ?: "A",
                                color = NeonGreen,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text(
                                text = state.contactoActual?.nombre ?: "Atleta",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "En línea",
                                style = MaterialTheme.typography.labelSmall,
                                color = NeonGreen
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding(),
                tonalElevation = 4.dp
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = nuevoMensaje,
                        onValueChange = { nuevoMensaje = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Escribe un mensaje...", color = TextGray) },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        maxLines = 4
                    )
                    IconButton(
                        onClick = {
                            if (nuevoMensaje.isNotBlank()) {
                                onSend(contactoId, nuevoMensaje)
                                nuevoMensaje = ""
                            }
                        },
                        enabled = nuevoMensaje.isNotBlank()
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Enviar",
                            tint = NeonGreen
                        )
                    }
                }
            }
        }
    ) { padding ->
        if (state.isLoading && state.mensajesActuales.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = NeonGreen)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp),
                reverseLayout = true // Para que los mensajes fluyan de abajo hacia arriba
            ) {
                item { Spacer(Modifier.height(16.dp)) }

                // En una app real, los mensajes vendrían ordenados por fecha DESC para reverseLayout
                items(state.mensajesActuales.reversed()) { mensaje ->
                    val isMine = mensaje.emisorId == usuarioLogueadoId
                    ChatBubble(mensaje = mensaje, isMine = isMine)
                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun ChatBubble(mensaje: Mensaje, isMine: Boolean) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = if (isMine) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Surface(
            color = if (isMine) NeonGreen.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant,
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = if (isMine) 16.dp else 0.dp,
                bottomEnd = if (isMine) 0.dp else 16.dp
            ),
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = mensaje.contenido,
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (isMine) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "12:45", // Formatear fecha
                    style = MaterialTheme.typography.labelSmall,
                    color = if (isMine) NeonGreen.copy(alpha = 0.7f) else TextGray,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatScreenPreview() {
    val myId = UUID.randomUUID()
    val otherId = UUID.randomUUID()
    EnerGymTheme {
        ChatScreen(
            contactoId = otherId,
            usuarioLogueadoId = myId,
            state = MensajeState(
                contactoActual = Usuario(
                    id = otherId,
                    nombre = "Juan Pérez",
                    email = "juan@example.com",
                    passwordHash = "",
                    edad = null,
                    peso = null,
                    altura = null,
                    objetivo = null,
                    fotoUrl = null
                ),
                mensajesActuales = listOf(
                    Mensaje(id = UUID.randomUUID(), emisorId = otherId, receptorId = myId, contenido = "Hola, ¿vas a entrenar hoy?", fecha = System.currentTimeMillis()),
                    Mensaje(id = UUID.randomUUID(), emisorId = myId, receptorId = otherId, contenido = "¡Sí! En 30 minutos llego.", fecha = System.currentTimeMillis()),
                    Mensaje(id = UUID.randomUUID(), emisorId = otherId, receptorId = myId, contenido = "Genial, nos vemos ahí.", fecha = System.currentTimeMillis())
                )
            ),
            onLoad = {},
            onSend = { _, _ -> },
            onBack = {}
        )
    }
}
