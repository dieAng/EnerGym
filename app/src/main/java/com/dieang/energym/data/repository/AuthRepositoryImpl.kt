package com.dieang.energym.data.repository

import com.dieang.energym.data.local.datastore.UserStore
import com.dieang.energym.data.local.entity.UsuarioEntity
import com.dieang.energym.data.mappers.toEntity
import com.dieang.energym.data.remote.api.AuthApi
import com.dieang.energym.data.remote.api.UsuarioApi
import com.dieang.energym.data.remote.dto.request.LoginRequestDto
import com.dieang.energym.data.remote.dto.request.UsuarioCreateRequestDto
import com.dieang.energym.domain.model.Usuario
import com.dieang.energym.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val usuarioApi: UsuarioApi,
    private val userStore: UserStore
) : AuthRepository {

    override suspend fun login(email: String, password: String): UsuarioEntity {
        val response = api.login(LoginRequestDto(email, password))

        val usuario = UsuarioEntity(
            id = response.usuarioId,
            nombre = response.nombre,
            email = email,
            passwordHash = "", // Requerido por la entidad local
            edad = null,
            peso = null,
            altura = null,
            objetivo = null,
            fotoUrl = null,
            rol = response.rol
        )

        userStore.saveUser(usuario)

        return usuario
    }

    override suspend fun register(request: UsuarioCreateRequestDto): UsuarioEntity {
        val response = usuarioApi.createUsuario(request)
        val entity = response.toEntity()
        userStore.saveUser(entity)
        return entity
    }

    override suspend fun logout() {
        userStore.clearUser()
    }

    override fun getLoggedUser(): Flow<Usuario?> =
        userStore.getUser()

    override fun isLoggedIn(): Flow<Boolean> =
        userStore.getUser().map { it != null }
}
