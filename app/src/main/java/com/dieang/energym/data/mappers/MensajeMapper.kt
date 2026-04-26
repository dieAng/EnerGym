package com.dieang.energym.data.mappers

import com.dieang.energym.data.local.entity.MensajeEntity
import com.dieang.energym.data.remote.dto.request.MensajeCreateRequestDto
import com.dieang.energym.data.remote.dto.response.MensajeResponseDto
import com.dieang.energym.domain.model.Mensaje
import java.util.UUID

// Entity → Domain
fun MensajeEntity.toDomain() = Mensaje(
    id = id,
    emisorId = emisorId,
    receptorId = receptorId,
    contenido = contenido,
    fecha = fecha
)

// Domain → Entity
fun Mensaje.toEntity() = MensajeEntity(
    id = id,
    emisorId = emisorId,
    receptorId = receptorId,
    contenido = contenido,
    fecha = fecha
)

// Response → Entity
fun MensajeResponseDto.toEntity() = MensajeEntity(
    id = id,
    emisorId = emisorId,
    receptorId = receptorId,
    contenido = contenido,
    fecha = fecha
)

// Request → Entity
fun MensajeCreateRequestDto.toEntity(emisorId: UUID) = MensajeEntity(
    id = UUID.randomUUID(),
    emisorId = emisorId,
    receptorId = receptorId,
    contenido = contenido,
    fecha = System.currentTimeMillis()
)