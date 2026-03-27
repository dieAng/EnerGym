package com.dieang.energym.data.remote.api

import com.dieang.energym.data.remote.dto.request.SerieRealizadaCreateRequestDto
import com.dieang.energym.data.remote.dto.request.SesionCreateRequestDto
import com.dieang.energym.data.remote.dto.response.SesionEntrenamientoResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.UUID

interface SesionEntrenamientoApi {

    @GET("api/sesion")
    suspend fun getSesiones(): List<SesionEntrenamientoResponseDto>

    @GET("api/sesion/{id}")
    suspend fun getSesion(@Path("id") id: UUID): SesionEntrenamientoResponseDto

    @POST("api/sesion")
    suspend fun createSesion(@Body request: SesionCreateRequestDto): SesionEntrenamientoResponseDto

    @POST("api/sesion/{id}/serie")
    suspend fun addSerie(
        @Path("id") sesionId: UUID,
        @Body request: SerieRealizadaCreateRequestDto
    )
}