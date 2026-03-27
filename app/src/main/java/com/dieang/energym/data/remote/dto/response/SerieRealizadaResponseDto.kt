package com.dieang.energym.data.remote.dto.response

import java.util.UUID

data class SerieRealizadaResponseDto(
    val id: UUID,
    val ejercicioId: UUID,
    val repeticiones: Int,
    val peso: Float
)
