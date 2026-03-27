package com.dieang.energym.data.remote.dto.response

import java.util.UUID

data class UsuarioResponseDto(
    val id: UUID,
    val nombre: String,
    val email: String,
    val edad: Int?,
    val peso: Float?,
    val altura: Float?,
    val objetivo: String?,
    val fotoUrl: String?,
    val rol: String
)