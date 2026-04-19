package com.dieang.energym.data.repository

import com.dieang.energym.data.local.dao.ComentarioDao
import com.dieang.energym.data.mappers.toDomain
import com.dieang.energym.data.mappers.toEntity
import com.dieang.energym.data.remote.api.ComentarioApi
import com.dieang.energym.data.remote.dto.request.ComentarioCreateRequestDto
import com.dieang.energym.domain.model.Comentario
import com.dieang.energym.domain.repository.ComentarioRepository
import java.util.UUID

class ComentarioRepositoryImpl(
    private val api: ComentarioApi,
    private val dao: ComentarioDao
) : ComentarioRepository {

    override suspend fun getComentarios(postId: UUID): List<Comentario> =
        dao.getByPost(postId).map { it.toDomain() }

    override suspend fun addComentario(postId: UUID, request: ComentarioCreateRequestDto, usuarioId: UUID) {
        val response = api.createComentario(request)
        dao.insert(response.toEntity(postId))
    }
}