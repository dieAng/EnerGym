package com.dieang.energym.data.remote.api

import com.dieang.energym.data.remote.dto.request.LoginRequestDto
import com.dieang.energym.data.remote.dto.response.LoginResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequestDto): LoginResponseDto
}
