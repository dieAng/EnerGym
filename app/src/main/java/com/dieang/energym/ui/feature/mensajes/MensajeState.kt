package com.dieang.energym.ui.feature.mensajes

import com.dieang.energym.domain.model.Mensaje
import com.dieang.energym.domain.model.Usuario
import java.util.UUID

data class MensajeState(
    val isLoading: Boolean = false,
    val chats: List<ChatPreview> = emptyList(), // Lista de conversaciones para la pantalla principal
    val mensajesActuales: List<Mensaje> = emptyList(), // Mensajes del chat abierto
    val contactoActual: Usuario? = null,
    val error: String? = null
)

data class ChatPreview(
    val contactoId: UUID,
    val nombreContacto: String,
    val ultimoMensaje: String,
    val fecha: Long,
    val sinLeer: Int = 0
)
