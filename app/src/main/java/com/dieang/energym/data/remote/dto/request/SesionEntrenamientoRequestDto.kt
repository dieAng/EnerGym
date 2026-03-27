package com.dieang.energym.data.remote.dto.request

import java.util.UUID

data class SesionCreateRequestDto(
    val rutinaId: UUID,
    val usuarioId: UUID
)