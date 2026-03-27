package com.dieang.energym.data.remote.dto.request

import java.util.UUID

data class RutinaEjercicioCreateRequestDto(
    val ejercicioId: UUID,
    val series: Int,
    val repeticiones: Int,
    val pesoObjetivo: Float?,
    val descansoSeg: Int?,
    val orden: Int
)