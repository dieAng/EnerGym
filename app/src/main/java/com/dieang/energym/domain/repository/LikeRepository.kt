package com.dieang.energym.domain.repository

import java.util.UUID

interface LikeRepository {

    suspend fun likePost(postId: UUID, usuarioId: UUID)
}