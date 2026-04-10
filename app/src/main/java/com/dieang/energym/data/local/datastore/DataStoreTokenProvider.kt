package com.dieang.energym.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class DataStoreTokenProvider(
    private val dataStore: DataStore<Preferences>
) : TokenProvider {

    override fun getAccessToken(): String? =
        runBlocking { dataStore.data.first()[ACCESS_TOKEN] }

    override fun getRefreshToken(): String? =
        runBlocking { dataStore.data.first()[REFRESH_TOKEN] }

    override fun saveTokens(access: String, refresh: String) {
        runBlocking {
            dataStore.edit {
                it[ACCESS_TOKEN] = access
                it[REFRESH_TOKEN] = refresh
            }
        }
    }

    override fun clearTokens() {
        runBlocking {
            dataStore.edit {
                it.remove(ACCESS_TOKEN)
                it.remove(REFRESH_TOKEN)
            }
        }
    }

    override fun observeAccessToken(): Flow<String?> =
        dataStore.data.map { it[ACCESS_TOKEN] }

    companion object {
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }
}
