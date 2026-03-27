package com.dieang.energym.data.remote.dto.response

import java.util.UUID

data class SesionResponseDto(
    val id: UUID,
    val usuarioId: UUID,
    val rutinaId: UUID?,
    val fecha: Long,
    val series: List<SerieRealizadaResponseDto>
)