package com.dieang.energym.domain.usecase.posts

import com.dieang.energym.data.local.dao.ComentarioDao
import com.dieang.energym.domain.model.Comentario
import java.util.UUID
import javax.inject.Inject

class GetComentariosByPostUseCase @Inject constructor(
    private val dao: ComentarioDao
) {
    suspend operator fun invoke(postId: UUID): List<Comentario> {
        return dao.getByPost(postId).map { entity ->
            Comentario(
                id = entity.id,
                postId = entity.postId,
                usuarioId = entity.usuarioId,
                contenido = entity.contenido,
                fecha = entity.fecha
            )
        }
    }
}
