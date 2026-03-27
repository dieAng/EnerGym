package com.dieang.energym.data.remote.api

import com.dieang.energym.data.remote.dto.request.RutinaEjercicioCreateRequestDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.util.UUID

interface RutinaEjercicioApi {

    @POST("api/rutina/{rutinaId}/ejercicio")
    suspend fun addEjercicio(
        @Path("rutinaId") rutinaId: UUID,
        @Body request: RutinaEjercicioCreateRequestDto
    )

    @PUT("api/rutina/{rutinaId}/ejercicio/{ejercicioId}")
    suspend fun updateEjercicio(
        @Path("rutinaId") rutinaId: UUID,
        @Path("ejercicioId") ejercicioId: UUID,
        @Body request: RutinaEjercicioCreateRequestDto
    )

    @DELETE("api/rutina/{rutinaId}/ejercicio/{ejercicioId}")
    suspend fun deleteEjercicio(
        @Path("rutinaId") rutinaId: UUID,
        @Path("ejercicioId") ejercicioId: UUID
    )
}