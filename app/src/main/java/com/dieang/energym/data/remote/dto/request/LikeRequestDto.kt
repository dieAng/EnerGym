package com.dieang.energym.data.remote.dto.request

import java.util.UUID

data class LikeRequestDto(
    val postId: UUID,
    val usuarioId: UUID
)
