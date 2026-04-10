package com.dieang.energym.domain.usecase.posts

import com.dieang.energym.domain.repository.PostRepository
import java.util.UUID

class LikePostUseCase(
    private val repo: PostRepository
) {
    suspend operator fun invoke(postId: UUID, usuarioId: UUID) =
        repo.likePost(postId, usuarioId)
}
