package com.dieang.energym.data.remote.dto.request

import java.util.UUID

data class IngredienteCreateRequestDto(
    val nombre: String,
    val cantidad: String?
)