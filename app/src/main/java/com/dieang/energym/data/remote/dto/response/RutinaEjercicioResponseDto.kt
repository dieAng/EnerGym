package com.dieang.energym.data.remote.dto.response

import java.util.UUID

data class RutinaEjercicioResponseDto(
    val ejercicioId: UUID,
    val series: Int,
    val repeticiones: Int,
    val pesoObjetivo: Float?,
    val descansoSeg: Int?,
    val orden: Int
)