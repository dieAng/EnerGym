package com.dieang.energym.data.remote.dto.request

import java.util.UUID

data class PostCreateRequestDto(
    val usuarioId: UUID,
    val contenido: String?,
    val imagenUrl: String?,
    val energiaGenerada: Double = 0.0,
    val fecha: Long = System.currentTimeMillis()
)