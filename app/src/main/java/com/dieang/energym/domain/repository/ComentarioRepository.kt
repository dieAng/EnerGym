package com.dieang.energym.domain.repository

import com.dieang.energym.data.remote.dto.request.ComentarioCreateRequestDto
import com.dieang.energym.domain.model.Comentario
import java.util.UUID

interface ComentarioRepository {

    suspend fun getComentarios(postId: UUID): List<Comentario>

    suspend fun addComentario(postId: UUID, request: ComentarioCreateRequestDto, usuarioId: UUID)
}