package com.dieang.energym.data.local.datastore

import com.dieang.energym.data.local.entity.UsuarioEntity
import com.dieang.energym.domain.model.Usuario
import kotlinx.coroutines.flow.Flow

interface UserStore {
    fun getUser(): Flow<Usuario?>
    suspend fun saveUser(usuario: UsuarioEntity)
    suspend fun clearUser()
}
