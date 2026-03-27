package com.dieang.energym.data.remote.dto.response

import java.util.UUID

data class ComentarioResponseDto(
    val id: UUID,
    val usuarioId: UUID,
    val contenido: String,
    val fecha: Long
)