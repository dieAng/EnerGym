package com.dieang.energym.data.remote.api

import com.dieang.energym.data.remote.dto.request.ComentarioCreateRequestDto
import com.dieang.energym.data.remote.dto.response.ComentarioResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.UUID

interface ComentarioApi {

    @GET("api/comentario/post/{postId}")
    suspend fun getComentarios(@Path("postId") postId: UUID): List<ComentarioResponseDto>

    @POST("api/comentario")
    suspend fun createComentario(@Body request: ComentarioCreateRequestDto): ComentarioResponseDto
}