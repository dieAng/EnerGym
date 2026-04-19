package com.dieang.energym.data.remote.dto.response

import java.util.UUID

data class PostResponseDto(
    val id: UUID,
    val usuarioId: UUID,
    val contenido: String?,
    val imagenUrl: String?,
    val energiaGenerada: Double,
    val fecha: Long,
    val likes: Int,
    val comentarios: List<ComentarioResponseDto>
)