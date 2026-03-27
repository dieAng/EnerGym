package com.dieang.energym.data.mappers

import com.dieang.energym.data.local.entity.SesionEntrenamientoEntity
import com.dieang.energym.data.remote.dto.request.SesionCreateRequestDto
import com.dieang.energym.data.remote.dto.response.SesionEntrenamientoResponseDto
import java.util.UUID

// Response → Entity
fun SesionEntrenamientoResponseDto.toEntity() = SesionEntrenamientoEntity(
    id = id,
    usuarioId = usuarioId,
    rutinaId = rutinaId,
    fecha = fecha
)

// Request → Entity (cuando creas una sesión local)
fun SesionCreateRequestDto.toEntity(generatedId: UUID) = SesionEntrenamientoEntity(
    id = generatedId,
    usuarioId = usuarioId,
    rutinaId = rutinaId,
    fecha = System.currentTimeMillis()
)