package com.dieang.energym.domain.usecase.comentarios

import com.dieang.energym.domain.repository.ComentarioRepository
import java.util.UUID

class GetComentariosUseCase(
    private val repo: ComentarioRepository
) {
    suspend operator fun invoke(postId: UUID) =
        repo.getComentarios(postId)
}
