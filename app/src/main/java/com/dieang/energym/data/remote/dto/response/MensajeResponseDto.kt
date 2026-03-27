package com.dieang.energym.data.remote.dto.response

import java.util.UUID

data class MensajeResponseDto(
    val id: UUID,
    val emisorId: UUID,
    val receptorId: UUID,
    val contenido: String,
    val fecha: Long
)