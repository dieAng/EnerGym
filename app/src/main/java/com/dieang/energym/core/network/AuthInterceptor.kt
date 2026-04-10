package com.dieang.energym.core.network

import com.dieang.energym.data.local.datastore.TokenProvider
import com.dieang.energym.data.remote.api.AuthApi
import com.dieang.energym.data.remote.dto.request.RefreshTokenRequestDto
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val tokenProvider: TokenProvider,
    private val authApi: AuthApi
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        // 1. Obtener token actual
        val accessToken = tokenProvider.getAccessToken()

        // 2. Añadir Authorization si existe
        if (!accessToken.isNullOrEmpty()) {
            request = request.newBuilder()
                .addHeader("Authorization", "Bearer $accessToken")
                .build()
        }

        // 3. Ejecutar petición
        val response = chain.proceed(request)

        // 4. Si el token expiró → intentar refresh
        if (response.code == 401) {
            response.close()

            val refreshToken = tokenProvider.getRefreshToken()
            if (refreshToken.isNullOrEmpty()) {
                tokenProvider.clearTokens()
                return response
            }

            return try {
                // 5. Intentar refresh
                val refreshResponse = authApi.refresh(
                    RefreshTokenRequestDto(refreshToken)
                )

                // 6. Guardar nuevos tokens
                tokenProvider.saveTokens(
                    refreshResponse.token,
                    refreshResponse.refreshToken ?: ""
                )

                // 7. Repetir petición original con el nuevo token
                val newRequest = request.newBuilder()
                    .removeHeader("Authorization")
                    .addHeader("Authorization", "Bearer ${refreshResponse.token}")
                    .build()

                chain.proceed(newRequest)

            } catch (e: Exception) {
                // Refresh falló → cerrar sesión
                tokenProvider.clearTokens()
                response
            }
        }

        return response
    }
}
