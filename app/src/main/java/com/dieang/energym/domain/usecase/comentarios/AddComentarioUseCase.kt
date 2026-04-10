package com.dieang.energym.domain.usecase.comentarios

import com.dieang.energym.data.remote.dto.request.ComentarioCreateRequestDto
import com.dieang.energym.domain.repository.ComentarioRepository
import java.util.UUID

class AddComentarioUseCase(
    private val repo: ComentarioRepository
) {
    suspend operator fun invoke(postId: UUID, request: ComentarioCreateRequestDto, usuarioId: UUID) =
        repo.addComentario(postId, request, usuarioId)
}
