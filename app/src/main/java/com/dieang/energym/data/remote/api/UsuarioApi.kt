package com.dieang.energym.data.remote.api

import com.dieang.energym.data.remote.dto.request.UsuarioCreateRequestDto
import com.dieang.energym.data.remote.dto.request.UsuarioUpdateRequestDto
import com.dieang.energym.data.remote.dto.response.UsuarioResponseDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.util.UUID

interface UsuarioApi {

    @GET("api/usuario")
    suspend fun getUsuarios(): List<UsuarioResponseDto>

    @GET("api/usuario/{id}")
    suspend fun getUsuario(@Path("id") id: UUID): UsuarioResponseDto

    @POST("api/usuario")
    suspend fun createUsuario(@Body request: UsuarioCreateRequestDto): UsuarioResponseDto

    @PUT("api/usuario/{id}")
    suspend fun updateUsuario(
        @Path("id") id: UUID,
        @Body request: UsuarioUpdateRequestDto
    ): UsuarioResponseDto

    @DELETE("api/usuario/{id}")
    suspend fun deleteUsuario(@Path("id") id: UUID)
}