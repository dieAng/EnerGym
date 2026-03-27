package com.dieang.energym.data.remote.api

import com.dieang.energym.data.remote.dto.request.RecetaCreateRequestDto
import com.dieang.energym.data.remote.dto.request.RecetaUpdateRequestDto
import com.dieang.energym.data.remote.dto.response.RecetaResponseDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.util.UUID

interface RecetaApi {

    @GET("api/receta")
    suspend fun getRecetas(): List<RecetaResponseDto>

    @GET("api/receta/{id}")
    suspend fun getReceta(@Path("id") id: UUID): RecetaResponseDto

    @POST("api/receta")
    suspend fun createReceta(@Body request: RecetaCreateRequestDto): RecetaResponseDto

    @PUT("api/receta/{id}")
    suspend fun updateReceta(
        @Path("id") id: UUID,
        @Body request: RecetaUpdateRequestDto
    ): RecetaResponseDto

    @DELETE("api/receta/{id}")
    suspend fun deleteReceta(@Path("id") id: UUID)
}