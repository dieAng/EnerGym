package com.dieang.energym.data.remote.dto.response

import java.util.UUID

data class LikeResponseDto(
    val postId: UUID,
    val usuarioId: UUID
)