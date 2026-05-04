package com.dieang.energym.data.remote.api

import com.dieang.energym.data.remote.dto.request.LikeRequestDto
import retrofit2.http.Body
import retrofit2.http.POST

interface LikeApi {

    @POST("api/like")
    suspend fun toggleLike(@Body request: LikeRequestDto)
}
