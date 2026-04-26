package com.dieang.energym.data.mappers

import com.dieang.energym.data.local.entity.SesionEntrenamientoEntity
import com.dieang.energym.data.remote.dto.request.SesionCreateRequestDto
import com.dieang.energym.data.remote.dto.response.SesionEntrenamientoResponseDto
import com.dieang.energym.domain.model.SesionEntrenamiento
import java.util.UUID

// Entity → Domain
fun SesionEntrenamientoEntity.toDomain() = SesionEntrenamiento(
    id = id,
    usuarioId = usuarioId,
    rutinaId = rutinaId,
    fecha = fecha,
    duracionSegundos = duracionSegundos,
    energiaGeneradaWh = energiaGeneradaWh,
    caloriasQuemadas = caloriasQuemadas
)

// Domain → Entity
fun SesionEntrenamiento.toEntity() = SesionEntrenamientoEntity(
    id = id,
    usuarioId = usuarioId,
    rutinaId = rutinaId,
    fecha = fecha,
    duracionSegundos = duracionSegundos ?: 0,
    energiaGeneradaWh = energiaGeneradaWh ?: 0,
    caloriasQuemadas = caloriasQuemadas ?: 0
)

// Response → Entity
fun SesionEntrenamientoResponseDto.toEntity() = SesionEntrenamientoEntity(
    id = id,
    usuarioId = usuarioId,
    rutinaId = rutinaId,
    fecha = fecha,
    duracionSegundos = 0, // DTOs podrían no tenerlo aún
    energiaGeneradaWh = 0,
    caloriasQuemadas = 0
)

// Request → Entity (cuando creas una sesión local)
fun SesionCreateRequestDto.toEntity(generatedId: UUID) = SesionEntrenamientoEntity(
    id = generatedId,
    usuarioId = usuarioId,
    rutinaId = rutinaId,
    fecha = System.currentTimeMillis()
)