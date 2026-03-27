package com.dieang.energym.data.remote.api

import com.dieang.energym.data.remote.dto.request.SerieRealizadaCreateRequestDto
import com.dieang.energym.data.remote.dto.request.SesionCreateRequestDto
import com.dieang.energym.data.remote.dto.response.SesionResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.UUID

interface SesionApi {

    @GET("api/sesion")
    suspend fun getSesiones(): List<SesionResponseDto>

    @GET("api/sesion/{id}")
    suspend fun getSesion(@Path("id") id: UUID): SesionResponseDto

    @POST("api/sesion")
    suspend fun createSesion(@Body request: SesionCreateRequestDto): SesionResponseDto

    @POST("api/sesion/{id}/serie")
    suspend fun addSerie(
        @Path("id") sesionId: UUID,
        @Body request: SerieRealizadaCreateRequestDto
    )
}