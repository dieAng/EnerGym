package com.dieang.energym.data.remote.dto.request

import java.util.UUID

data class UsuarioCreateRequestDto(
    val nombre: String,
    val email: String,
    val password: String
)

data class UsuarioUpdateRequestDto(
    val nombre: String?,
    val email: String?,
    val edad: Int?,
    val peso: Float?,
    val altura: Float?,
    val objetivo: String?,
    val fotoUrl: String?
)