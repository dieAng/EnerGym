package com.dieang.energym.domain.model

import java.util.UUID

data class LikePost(
    val postId: UUID,
    val usuarioId: UUID
)