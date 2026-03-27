package com.dieang.energym.data.remote.dto.request

import java.util.UUID

data class SerieRealizadaCreateRequestDto(
    val ejercicioId: UUID,
    val repeticiones: Int,
    val peso: Float
)