package com.dieang.energym.data.remote.dto.request

import java.util.UUID

data class RecetaCreateRequestDto(
    val usuarioId: UUID,
    val nombre: String,
    val tiempoPreparacion: Int?,
    val alergenos: String?,
    val imagenUrl: String?,
    val descripcion: String?,
    val ingredientes: List<IngredienteCreateRequestDto>
)

data class RecetaUpdateRequestDto(
    val nombre: String?,
    val tiempoPreparacion: Int?,
    val alergenos: String?,
    val imagenUrl: String?,
    val descripcion: String?
)