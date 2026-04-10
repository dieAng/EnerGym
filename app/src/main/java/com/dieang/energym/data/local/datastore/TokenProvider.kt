package com.dieang.energym.data.local.datastore

import kotlinx.coroutines.flow.Flow

interface TokenProvider {
    fun getAccessToken(): String?
    fun getRefreshToken(): String?
    fun observeAccessToken(): Flow<String?>
    fun saveTokens(access: String, refresh: String)
    fun clearTokens()
}
