package com.dieang.energym.data.mappers

import com.dieang.energym.data.local.entity.IngredienteEntity
import com.dieang.energym.data.remote.dto.request.IngredienteCreateRequestDto
import com.dieang.energym.data.remote.dto.response.IngredienteResponseDto
import java.util.UUID

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