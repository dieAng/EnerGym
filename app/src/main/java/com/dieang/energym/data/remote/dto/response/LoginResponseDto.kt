package com.dieang.energym.data.remote.dto.response

data class LoginResponseDto(
    val token: String,
    val refreshToken: String?,
    val expiresAt: Long,
    val usuario: UsuarioResponseDto
)