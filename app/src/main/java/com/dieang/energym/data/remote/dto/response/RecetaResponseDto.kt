package com.dieang.energym.data.remote.dto.response

import java.util.UUID

data class RecetaResponseDto(
    val id: UUID,
    val usuarioId: UUID,
    val nombre: String,
    val tiempoPreparacion: Int?,
    val alergenos: String?,
    val imagenUrl: String?,
    val descripcion: String?,
    val esPredisenada: Boolean,
    val ingredientes: List<IngredienteResponseDto>
)