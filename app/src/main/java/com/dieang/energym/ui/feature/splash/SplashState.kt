package com.dieang.energym.ui.feature.splash

data class SplashState(
    val isLoading: Boolean = true,
    val isLoggedIn: Boolean? = null,
    val error: String? = null
)
