package com.dieang.energym.domain.repository

import com.dieang.energym.data.local.entity.UsuarioEntity
import com.dieang.energym.data.remote.dto.request.UsuarioCreateRequestDto
import com.dieang.energym.data.remote.dto.request.UsuarioUpdateRequestDto
import com.dieang.energym.domain.model.Usuario
import java.util.UUID

interface UsuarioRepository {

    suspend fun syncUsuarios()

    suspend fun getUsuarios(): List<Usuario>

    suspend fun getUsuario(id: UUID): Usuario?

    suspend fun createUsuario(request: UsuarioCreateRequestDto): UsuarioEntity

    suspend fun updateUsuario(id: UUID, request: UsuarioUpdateRequestDto)

    suspend fun deleteUsuario(id: UUID)
}