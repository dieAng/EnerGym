package com.dieang.energym.data.remote.api

import com.dieang.energym.data.remote.dto.request.MensajeCreateRequestDto
import com.dieang.energym.data.remote.dto.response.MensajeResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.UUID

interface MensajeApi {

    @GET("api/mensaje/conversacion/{u1}/{u2}")
    suspend fun getConversacion(
        @Path("u1") u1: UUID,
        @Path("u2") u2: UUID
    ): List<MensajeResponseDto>

    @POST("api/mensaje")
    suspend fun enviarMensaje(@Body request: MensajeCreateRequestDto): MensajeResponseDto
}