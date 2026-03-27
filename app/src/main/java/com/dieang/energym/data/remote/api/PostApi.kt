package com.dieang.energym.data.remote.api

import com.dieang.energym.data.remote.dto.request.ComentarioCreateRequestDto
import com.dieang.energym.data.remote.dto.request.PostCreateRequestDto
import com.dieang.energym.data.remote.dto.response.PostResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.UUID

interface PostApi {

    @GET("api/post")
    suspend fun getPosts(): List<PostResponseDto>

    @POST("api/post")
    suspend fun createPost(@Body request: PostCreateRequestDto): PostResponseDto

    @POST("api/post/{id}/like")
    suspend fun likePost(@Path("id") postId: UUID)

    @POST("api/post/{id}/comentario")
    suspend fun addComentario(
        @Path("id") postId: UUID,
        @Body request: ComentarioCreateRequestDto
    )
}