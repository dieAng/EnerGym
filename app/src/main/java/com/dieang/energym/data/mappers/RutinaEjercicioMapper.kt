package com.dieang.energym.data.mappers

import com.dieang.energym.data.local.entity.RutinaEjercicioEntity
import com.dieang.energym.data.remote.dto.request.RutinaEjercicioCreateRequestDto
import com.dieang.energym.data.remote.dto.response.RutinaEjercicioResponseDto
import java.util.UUID

// Response → Entity
fun RutinaEjercicioResponseDto.toEntity(rutinaId: UUID) = RutinaEjercicioEntity(
    rutinaId = rutinaId,
    ejercicioId = ejercicioId,
    series = series,
    repeticiones = repeticiones,
    pesoObjetivo = pesoObjetivo,
    descansoSeg = descansoSeg,
    orden = orden
)

// Request → Entity
fun RutinaEjercicioCreateRequestDto.toEntity(rutinaId: UUID) = RutinaEjercicioEntity(
    rutinaId = rutinaId,
    ejercicioId = ejercicioId,
    series = series,
    repeticiones = repeticiones,
    pesoObjetivo = pesoObjetivo,
    descansoSeg = descansoSeg,
    orden = orden
)