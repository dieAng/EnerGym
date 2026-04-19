package com.dieang.energym.data.mappers

import com.dieang.energym.data.local.entity.PostEntity
import com.dieang.energym.data.remote.dto.request.PostCreateRequestDto
import com.dieang.energym.data.remote.dto.response.PostResponseDto
import java.util.UUID

// Response → Entity
fun PostResponseDto.toEntity() = PostEntity(
    id = id,
    usuarioId = usuarioId,
    contenido = contenido,
    imagenUrl = imagenUrl,
    energiaGenerada = energiaGenerada,
    fecha = fecha
)

// Request → Entity (si guardas local antes de enviar)
fun PostCreateRequestDto.toEntity(generatedId: UUID) = PostEntity(
    id = generatedId,
    usuarioId = usuarioId,
    contenido = contenido,
    imagenUrl = imagenUrl,
    energiaGenerada = energiaGenerada ?: 0.0,
    fecha = System.currentTimeMillis()
)
