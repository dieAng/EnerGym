package com.dieang.energym.data.remote.api

import com.dieang.energym.data.remote.dto.request.IngredienteCreateRequestDto
import com.dieang.energym.data.remote.dto.response.IngredienteResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.UUID

interface IngredienteApi {

    @GET("api/ingrediente/receta/{recetaId}")
    suspend fun getIngredientes(@Path("recetaId") recetaId: UUID): List<IngredienteResponseDto>

    @POST("api/ingrediente")
    suspend fun createIngrediente(@Body request: IngredienteCreateRequestDto): IngredienteResponseDto
}