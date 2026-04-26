package com.dieang.energym.domain.repository

import com.dieang.energym.data.local.entity.UsuarioEntity
import com.dieang.energym.data.remote.dto.request.UsuarioCreateRequestDto
import com.dieang.energym.domain.model.Usuario
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun login(email: String, password: String): UsuarioEntity

    suspend fun register(request: UsuarioCreateRequestDto): UsuarioEntity

    suspend fun refreshToken(): Boolean

    suspend fun logout()

    fun getLoggedUser(): Flow<Usuario?>

    fun isLoggedIn(): Flow<Boolean>
}