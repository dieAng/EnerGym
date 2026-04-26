package com.dieang.energym.data.repository

import com.dieang.energym.data.local.dao.MensajeDao
import com.dieang.energym.data.mappers.toDomain
import com.dieang.energym.data.mappers.toEntity
import com.dieang.energym.data.remote.api.MensajeApi
import com.dieang.energym.data.remote.dto.request.MensajeCreateRequestDto
import com.dieang.energym.domain.model.Mensaje
import com.dieang.energym.domain.repository.MensajeRepository
import java.util.UUID

class MensajeRepositoryImpl(
    private val api: MensajeApi,
    private val dao: MensajeDao
) : MensajeRepository {

    override suspend fun syncConversacion(u1: UUID, u2: UUID) {
        val remote = api.getConversacion(u1, u2)
        dao.insertAll(remote.map { it.toEntity() })
    }

    override suspend fun getConversacion(u1: UUID, u2: UUID): List<Mensaje> =
        dao.getConversacion(u1, u2).map { it.toDomain() }

    override suspend fun enviarMensaje(request: MensajeCreateRequestDto, emisorId: UUID): Mensaje {
        val response = api.enviarMensaje(request)
        val entity = response.toEntity()
        dao.insert(entity)
        return entity.toDomain()
    }
}

