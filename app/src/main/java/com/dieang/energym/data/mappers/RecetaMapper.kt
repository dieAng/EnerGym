package com.dieang.energym.data.mappers

import com.dieang.energym.data.local.entity.RecetaEntity
import com.dieang.energym.data.remote.dto.request.RecetaCreateRequestDto
import com.dieang.energym.data.remote.dto.response.RecetaResponseDto
import com.dieang.energym.domain.model.Receta
import java.util.UUID

// Entity → Domain
fun RecetaEntity.toDomain() = Receta(
    id = id,
    usuarioId = usuarioId,
    nombre = nombre,
    tiempoPreparacion = tiempoPreparacion,
    alergenos = alergenos,
    imagenUrl = imagenUrl,
    descripcion = descripcion,
    esPredisenada = esPredisenada
)

// Domain → Entity
fun Receta.toEntity() = RecetaEntity(
    id = id,
    usuarioId = usuarioId,
    nombre = nombre,
    tiempoPreparacion = tiempoPreparacion,
    alergenos = alergenos,
    imagenUrl = imagenUrl,
    descripcion = descripcion,
    esPredisenada = esPredisenada
)

// Response → Entity
fun RecetaResponseDto.toEntity() = RecetaEntity(
    id = id,
    usuarioId = usuarioId,
    nombre = nombre,
    tiempoPreparacion = tiempoPreparacion,
    alergenos = alergenos,
    imagenUrl = imagenUrl,
    descripcion = descripcion,
    esPredisenada = esPredisenada
)

// Request → Entity (cuando creas una receta local antes de enviarla)
fun RecetaCreateRequestDto.toEntity(generatedId: UUID) = RecetaEntity(
    id = generatedId,
    usuarioId = usuarioId,
    nombre = nombre,
    tiempoPreparacion = tiempoPreparacion,
    alergenos = alergenos,
    imagenUrl = imagenUrl,
    descripcion = descripcion,
    esPredisenada = false
)