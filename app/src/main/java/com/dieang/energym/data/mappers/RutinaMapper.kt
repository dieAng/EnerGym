package com.dieang.energym.data.mappers

import com.dieang.energym.data.local.entity.RutinaEntity
import com.dieang.energym.data.remote.dto.request.RutinaCreateRequestDto
import com.dieang.energym.data.remote.dto.response.RutinaResponseDto
import com.dieang.energym.domain.model.Rutina
import java.util.UUID

// Entity → Domain
fun RutinaEntity.toDomain() = Rutina(
    id = id,
    usuarioId = usuarioId,
    nombre = nombre,
    descripcion = descripcion,
    nivel = nivel,
    objetivo = objetivo,
    esPredisenada = esPredisenada
)

// Domain → Entity
fun Rutina.toEntity() = RutinaEntity(
    id = id,
    usuarioId = usuarioId,
    nombre = nombre,
    descripcion = descripcion,
    nivel = nivel,
    objetivo = objetivo,
    esPredisenada = esPredisenada
)

// Response → Entity
fun RutinaResponseDto.toEntity() = RutinaEntity(
    id = id,
    usuarioId = usuarioId,
    nombre = nombre,
    descripcion = descripcion,
    nivel = nivel,
    objetivo = objetivo,
    esPredisenada = esPredisenada
)

// Request → Entity
fun RutinaCreateRequestDto.toEntity(generatedId: UUID) = RutinaEntity(
    id = generatedId,
    usuarioId = usuarioId,
    nombre = nombre,
    descripcion = descripcion,
    nivel = nivel,
    objetivo = objetivo,
    esPredisenada = false
)