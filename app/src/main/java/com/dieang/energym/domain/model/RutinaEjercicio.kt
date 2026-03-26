package com.dieang.energym.domain.model

import java.util.UUID

data class RutinaEjercicio(
    val rutinaId: UUID,
    val ejercicioId: UUID,
    val series: Int,
    val repeticiones: Int,
    val pesoObjetivo: Float?,
    val descansoSeg: Int?,
    val orden: Int
)