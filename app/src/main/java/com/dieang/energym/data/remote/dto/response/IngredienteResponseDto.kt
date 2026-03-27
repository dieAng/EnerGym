package com.dieang.energym.data.remote.dto.response

import java.util.UUID

data class IngredienteResponseDto(
    val id: UUID,
    val nombre: String,
    val cantidad: String?
)