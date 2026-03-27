package com.dieang.energym.domain.repository

import com.dieang.energym.domain.model.Usuario
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun login(email: String, password: String): Usuario

    suspend fun refreshToken(): Boolean

    suspend fun logout()

    fun getLoggedUser(): Flow<Usuario?>

    fun isLoggedIn(): Flow<Boolean>
}