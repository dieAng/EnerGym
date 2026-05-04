package com.dieang.energym.data.remote.dto.response

import java.util.UUID

data class RutinaResponseDto(
    val id: UUID,
    val usuarioId: UUID,
    val nombre: String,
    val descripcion: String?,
    val nivel: String?,
    val objetivo: String?,
    val esPredisenada: Boolean
)