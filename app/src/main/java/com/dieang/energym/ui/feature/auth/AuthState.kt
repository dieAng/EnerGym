package com.dieang.energym.ui.feature.auth

import com.dieang.energym.domain.model.Usuario

data class AuthState(
    val isLoading: Boolean = false,
    val usuario: Usuario? = null,
    val isLoggedIn: Boolean? = null,
    val error: String? = null
)
