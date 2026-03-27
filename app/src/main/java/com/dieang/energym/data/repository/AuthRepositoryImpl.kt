package com.dieang.energym.data.repository

import com.dieang.energym.data.local.entity.UsuarioEntity
import com.dieang.energym.data.mappers.toEntity
import com.dieang.energym.data.remote.api.AuthApi
import com.dieang.energym.domain.model.Usuario
import com.dieang.energym.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val tokenStore: TokenProvider,
    private val userStore: UserStore
) : AuthRepository {

    override suspend fun login(email: String, password: String): UsuarioEntity {
        val response = api.login(LoginRequest(email, password))

        tokenStore.saveTokens(
            response.token,
            response.refreshToken ?: ""
        )

        val usuario = response.usuario.toEntity()
        userStore.saveUser(usuario)

        return usuario
    }

    override suspend fun refreshToken(): Boolean {
        val refresh = tokenStore.getRefreshToken() ?: return false

        return try {
            val response = api.refresh(RefreshTokenRequest(refresh))

            tokenStore.saveTokens(
                response.token,
                response.refreshToken ?: ""
            )

            userStore.saveUser(response.usuario.toEntity())

            true
        } catch (e: Exception) {
            tokenStore.clearTokens()
            userStore.clearUser()
            false
        }
    }

    override suspend fun logout() {
        tokenStore.clearTokens()
        userStore.clearUser()
    }

    override fun getLoggedUser(): Flow<Usuario?> =
        userStore.getUser()

    override fun isLoggedIn(): Flow<Boolean> =
        tokenStore.observeAccessToken().map { !it.isNullOrEmpty() }
}