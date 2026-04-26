package com.dieang.energym.data.mappers

import com.dieang.energym.data.local.entity.IngredienteEntity
import com.dieang.energym.data.remote.dto.request.IngredienteCreateRequestDto
import com.dieang.energym.data.remote.dto.response.IngredienteResponseDto
import com.dieang.energym.domain.model.Ingrediente
import java.util.UUID

// Entity → Domain
fun IngredienteEntity.toDomain() = Ingrediente(
    id = id,
    recetaId = recetaId,
    nombre = nombre,
    cantidad = cantidad
)

// Domain → Entity
fun Ingrediente.toEntity() = IngredienteEntity(
    id = id,
    recetaId = recetaId,
    nombre = nombre,
    cantidad = cantidad
)

// Response → Entity
fun IngredienteResponseDto.toEntity(recetaId: UUID) = IngredienteEntity(
    id = id,
    recetaId = recetaId,
    nombre = nombre,
    cantidad = cantidad
)

// Request → Entity
fun IngredienteCreateRequestDto.toEntity(recetaId: UUID) = IngredienteEntity(
    id = UUID.randomUUID(),
    recetaId = recetaId,
    nombre = nombre,
    cantidad = cantidad
)