package com.dieang.energym.data.mappers

import com.dieang.energym.data.local.entity.ComentarioEntity
import com.dieang.energym.data.remote.dto.request.ComentarioCreateRequestDto
import com.dieang.energym.data.remote.dto.response.ComentarioResponseDto
import java.util.UUID

// Response → Entity
fun ComentarioResponseDto.toEntity(postId: UUID) = ComentarioEntity(
    id = id,
    postId = postId,
    usuarioId = usuarioId,
    contenido = contenido,
    fecha = fecha
)

// Request → Entity
fun ComentarioCreateRequestDto.toEntity(postId: UUID, usuarioId: UUID) = ComentarioEntity(
    id = UUID.randomUUID(),
    postId = postId,
    usuarioId = usuarioId,
    contenido = contenido,
    fecha = System.currentTimeMillis()
)