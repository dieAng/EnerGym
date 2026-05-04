package com.dieang.energym.data.remote.dto.request

import java.util.UUID

data class RutinaCreateRequestDto(
    val usuarioId: UUID,
    val nombre: String,
    val descripcion: String?,
    val nivel: String?,
    val objetivo: String?,
    val esPredisenada: Boolean
)