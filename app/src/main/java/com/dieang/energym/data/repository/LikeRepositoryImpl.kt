package com.dieang.energym.data.repository

import com.dieang.energym.data.remote.api.LikeApi
import com.dieang.energym.data.remote.dto.request.LikeRequestDto
import com.dieang.energym.domain.repository.LikeRepository
import java.util.UUID

class LikeRepositoryImpl(
    private val api: LikeApi
) : LikeRepository {

    override suspend fun likePost(postId: UUID, usuarioId: UUID) {
        api.toggleLike(LikeRequestDto(postId, usuarioId))
    }
}
