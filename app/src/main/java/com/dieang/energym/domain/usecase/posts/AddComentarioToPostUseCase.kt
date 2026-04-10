package com.dieang.energym.domain.usecase.posts

import com.dieang.energym.data.remote.dto.request.ComentarioCreateRequestDto
import com.dieang.energym.domain.repository.PostRepository
import java.util.UUID

class AddComentarioToPostUseCase(
    private val repo: PostRepository
) {
    suspend operator fun invoke(postId: UUID, request: ComentarioCreateRequestDto, usuarioId: UUID) =
        repo.addComentario(postId, request, usuarioId)
}
