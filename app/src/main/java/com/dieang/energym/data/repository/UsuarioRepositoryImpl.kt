package com.dieang.energym.data.repository

import com.dieang.energym.data.local.dao.UsuarioDao
import com.dieang.energym.data.local.entity.UsuarioEntity
import com.dieang.energym.data.mappers.toEntity
import com.dieang.energym.data.remote.api.UsuarioApi
import com.dieang.energym.data.remote.dto.request.UsuarioCreateRequestDto
import com.dieang.energym.data.remote.dto.request.UsuarioUpdateRequestDto
import com.dieang.energym.domain.model.Usuario
import com.dieang.energym.domain.repository.UsuarioRepository
import java.util.UUID

class UsuarioRepositoryImpl(
    private val api: UsuarioApi,
    private val dao: UsuarioDao
) : UsuarioRepository {

    override suspend fun syncUsuarios() {
        val remote = api.getUsuarios()
        dao.insertAll(remote.map { it.toEntity() })
    }

    override suspend fun getUsuarios(): List<Usuario> =
        dao.getAll()

    override suspend fun getUsuario(id: UUID): Usuario? =
        dao.getById(id)

    override suspend fun createUsuario(request: UsuarioCreateRequestDto): UsuarioEntity {
        val response = api.createUsuario(request)
        val entity = response.toEntity()
        dao.insert(entity)
        return entity
    }

    override suspend fun updateUsuario(id: UUID, request: UsuarioUpdateRequestDto) {
        val response = api.updateUsuario(id, request)
        dao.insert(response.toEntity())
    }

    override suspend fun deleteUsuario(id: UUID) {
        api.deleteUsuario(id)
        dao.delete(id)
    }
}