package com.dieang.energym.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.dieang.energym.data.local.entity.UsuarioEntity
import com.dieang.energym.domain.model.Usuario
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserDataStore(
    private val dataStore: DataStore<Preferences>,
    private val gson: Gson = Gson()
) : UserStore {

    companion object {
        val USER_JSON = stringPreferencesKey("user_json")
    }

    override fun getUser(): Flow<Usuario?> =
        dataStore.data.map { prefs ->
            prefs[USER_JSON]?.let { json ->
                gson.fromJson(json, Usuario::class.java)
            }
        }

    override suspend fun saveUser(usuario: UsuarioEntity) {
        dataStore.edit { prefs ->
            prefs[USER_JSON] = gson.toJson(usuario)
        }
    }

    override suspend fun clearUser() {
        dataStore.edit { prefs ->
            prefs.remove(USER_JSON)
        }
    }
}
