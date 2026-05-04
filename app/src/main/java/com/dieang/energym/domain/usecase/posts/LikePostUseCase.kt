package com.dieang.energym.domain.usecase.posts

import com.dieang.energym.domain.repository.LikeRepository
import java.util.UUID

class LikePostUseCase(
    private val repo: LikeRepository
) {
    suspend operator fun invoke(postId: UUID, usuarioId: UUID) =
        repo.likePost(postId, usuarioId)
}
