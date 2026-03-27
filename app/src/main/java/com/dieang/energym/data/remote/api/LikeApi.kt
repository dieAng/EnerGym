package com.dieang.energym.data.remote.api

import retrofit2.http.POST
import retrofit2.http.Path
import java.util.UUID

interface LikeApi {

    @POST("api/like/{postId}")
    suspend fun likePost(@Path("postId") postId: UUID)
}