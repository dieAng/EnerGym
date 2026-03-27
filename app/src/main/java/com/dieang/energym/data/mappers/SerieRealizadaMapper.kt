package com.dieang.energym.data.mappers

import com.dieang.energym.data.local.entity.SerieRealizadaEntity
import com.dieang.energym.data.remote.dto.request.SerieRealizadaCreateRequestDto
import com.dieang.energym.data.remote.dto.response.SerieRealizadaResponseDto
import java.util.UUID

// Response → Entity
fun SerieRealizadaResponseDto.toEntity(sesionId: UUID) = SerieRealizadaEntity(
    id = id,
    sesionId = sesionId,
    ejercicioId = ejercicioId,
    repeticiones = repeticiones,
    peso = peso
)

// Request → Entity
fun SerieRealizadaCreateRequestDto.toEntity(sesionId: UUID) = SerieRealizadaEntity(
    id = UUID.randomUUID(),
    sesionId = sesionId,
    ejercicioId = ejercicioId,
    repeticiones = repeticiones,
    peso = peso
)