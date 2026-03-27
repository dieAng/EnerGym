package com.dieang.energym.data.remote.api

import com.dieang.energym.data.remote.dto.request.RutinaCreateRequestDto
import com.dieang.energym.data.remote.dto.response.RutinaResponseDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.UUID

interface RutinaApi {

    @GET("api/rutina")
    suspend fun getRutinas(): List<RutinaResponseDto>

    @GET("api/rutina/{id}")
    suspend fun getRutina(@Path("id") id: UUID): RutinaResponseDto

    @POST("api/rutina")
    suspend fun createRutina(@Body request: RutinaCreateRequestDto): RutinaResponseDto

    @DELETE("api/rutina/{id}")
    suspend fun deleteRutina(@Path("id") id: UUID)
}