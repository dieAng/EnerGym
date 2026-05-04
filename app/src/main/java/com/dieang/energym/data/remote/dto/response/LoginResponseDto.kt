package com.dieang.energym.data.remote.dto.response

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class LoginResponseDto(
    @SerializedName("accessToken")
    val accessToken: String?,
    @SerializedName("refreshToken")
    val refreshToken: String?,
    @SerializedName("usuarioId")
    val usuarioId: UUID,
    @SerializedName("nombre")
    val nombre: String,
    @SerializedName("rol")
    val rol: String
)