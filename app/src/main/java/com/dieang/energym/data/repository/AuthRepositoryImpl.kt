package com.dieang.energym.data.repository

import com.dieang.energym.data.local.datastore.TokenProvider
import com.dieang.energym.data.local.datastore.UserStore
import com.dieang.energym.data.local.entity.UsuarioEntity
import com.dieang.energym.data.mappers.toEntity
import com.dieang.energym.data.remote.api.AuthApi
import com.dieang.energym.data.remote.dto.request.LoginRequestDto
import com.dieang.energym.data.remote.dto.request.RefreshTokenRequestDto
import com.dieang.energym.data.remote.dto.request.UsuarioCreateRequestDto
import com.dieang.energym.domain.model.Usuario
import com.dieang.energym.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val tokenStore: TokenProvider,
    private val userStore: UserStore
) : AuthRepository {

    override suspend fun login(email: String, password: String): UsuarioEntity {
        val response = api.login(LoginRequestDto(email, password))

        tokenStore.saveTokens(
            response.token,
            response.refreshToken ?: ""
        )

        val usuario = response.usuario.toEntity()
        userStore.saveUser(usuario)

        return usuario
    }

    override suspend fun register(request: UsuarioCreateRequestDto): UsuarioEntity {
        // En una implementación real llamaríamos a api.register(request)
        // Por ahora simulamos el comportamiento para compilar
        throw NotImplementedError("Registro no implementado aún en API")
    }

    override suspend fun refreshToken(): Boolean {
        val refresh = tokenStore.getRefreshToken() ?: return false

        return try {
            val response = api.refresh(RefreshTokenRequestDto(refresh))

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


