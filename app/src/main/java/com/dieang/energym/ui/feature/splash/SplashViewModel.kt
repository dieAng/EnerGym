package com.dieang.energym.ui.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SplashViewModel(
    private val isUserLoggedIn: IsUserLoggedInUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SplashState())
    val state = _state.asStateFlow()

    fun onEvent(event: SplashEvent) {
        when (event) {
            SplashEvent.CheckSession -> checkSession()
        }
    }

    private fun checkSession() = viewModelScope.launch {
        try {
            val logged = isUserLoggedIn()
            _state.update { it.copy(isLoading = false, isLoggedIn = logged) }
        } catch (e: Exception) {
            _state.update { it.copy(isLoading = false, error = e.message) }
        }
    }
}
