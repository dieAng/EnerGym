package com.dieang.energym.data.remote.api

import com.dieang.energym.data.remote.dto.response.EjercicioResponseDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.util.UUID

interface EjercicioApi {

    @GET("api/ejercicio")
    suspend fun getEjercicios(): List<EjercicioResponseDto>

    @GET("api/ejercicio/{id}")
    suspend fun getEjercicio(@Path("id") id: UUID): EjercicioResponseDto

    @POST("api/ejercicio")
    suspend fun createEjercicio(@Body request: EjercicioResponseDto): EjercicioResponseDto

    @PUT("api/ejercicio/{id}")
    suspend fun updateEjercicio(
        @Path("id") id: UUID,
        @Body request: EjercicioResponseDto
    ): EjercicioResponseDto

    @DELETE("api/ejercicio/{id}")
    suspend fun deleteEjercicio(@Path("id") id: UUID)
}