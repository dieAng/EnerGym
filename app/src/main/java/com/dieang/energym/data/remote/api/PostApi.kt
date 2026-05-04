package com.dieang.energym.data.remote.api

import com.dieang.energym.data.remote.dto.request.PostCreateRequestDto
import com.dieang.energym.data.remote.dto.response.PostResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PostApi {

    @GET("api/post")
    suspend fun getPosts(): List<PostResponseDto>

    @POST("api/post")
    suspend fun createPost(@Body request: PostCreateRequestDto): PostResponseDto
}
