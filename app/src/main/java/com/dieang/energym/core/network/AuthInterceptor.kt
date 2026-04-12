package com.dieang.energym.core.network

import okhttp3.Interceptor
import okhttp3.Response
import com.dieang.energym.data.local.datastore.TokenProvider
import com.dieang.energym.data.remote.api.AuthApi
import com.dieang.energym.data.remote.dto.request.RefreshTokenRequestDto

class AuthInterceptor(
    private val tokenProvider: TokenProvider,
    private val authApi: AuthApi
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val accessToken = tokenProvider.getAccessToken()

        if (!accessToken.isNullOrEmpty()) {
            request = request.newBuilder()
                .addHeader("Authorization", "Bearer $accessToken")
                .build()
        }

        val response = chain.proceed(request)

        if (response.code == 401) {
            response.close()

            val refreshToken = tokenProvider.getRefreshToken()
            if (refreshToken.isNullOrEmpty()) {
                tokenProvider.clearTokens()
                return response
            }

            return try {
                val refreshResponse = authApi.refresh(
                    RefreshTokenRequestDto(refreshToken)
                )

                tokenProvider.saveTokens(
                    refreshResponse.token,
                    refreshResponse.refreshToken ?: ""
                )

                val newRequest = request.newBuilder()
                    .removeHeader("Authorization")
                    .addHeader("Authorization", "Bearer ${refreshResponse.token}")
                    .build()

                chain.proceed(newRequest)
            } catch (e: Exception) {
                tokenProvider.clearTokens()
                response
            }
        }

        return response
    }
}
