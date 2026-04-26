package com.dieang.energym.ui.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dieang.energym.domain.usecase.auth.GetLoggedUserUseCase
import com.dieang.energym.domain.usecase.auth.LoginUserUseCase
import com.dieang.energym.domain.usecase.auth.LogoutUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUser: LoginUserUseCase,
    private val logoutUser: LogoutUserUseCase,
    private val getLoggedUser: GetLoggedUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state = _state.asStateFlow()

    init {
        observeUser()
    }

    private fun observeUser() {
        viewModelScope.launch {
            getLoggedUser().collect { user ->
                _state.update {
                    it.copy(
                        usuario = user,
                        isLoggedIn = user != null
                    )
                }
            }
        }
    }

    fun login(email: String, password: String) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }

        try {
            loginUser(email, password)
            _state.update { it.copy(isLoading = false) }
        } catch (e: Exception) {
            _state.update { it.copy(isLoading = false, error = e.message) }
        }
    }

    fun register(nombre: String, email: String, password: String) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        try {
            // Implementación real llamaría a un RegisterUserUseCase
            // Por ahora simulamos con login
            login(email, password)
        } catch (e: Exception) {
            _state.update { it.copy(isLoading = false, error = e.message) }
        }
    }

    fun logout() = viewModelScope.launch {
        logoutUser()
        _state.update { AuthState() }
    }
}

