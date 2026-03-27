package com.dieang.energym.data.remote.dto.request

import java.util.UUID

data class MensajeCreateRequestDto(
    val receptorId: UUID,
    val contenido: String
)