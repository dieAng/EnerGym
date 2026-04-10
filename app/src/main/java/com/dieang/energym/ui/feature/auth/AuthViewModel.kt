package com.dieang.energym.ui.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dieang.energym.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repo: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state = _state.asStateFlow()

    init {
        observeUser()
    }

    private fun observeUser() {
        viewModelScope.launch {
            repo.getLoggedUser().collect { user ->
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
        _state.update { it.copy(isLoading = true, error = null) }

        try {
            val user = repo.login(email, password)
            _state.update { it.copy(isLoading = false, usuario = user, isLoggedIn = true) }
        } catch (e: Exception) {
            _state.update { it.copy(isLoading = false, error = e.message) }
        }
    }

    fun logout() = viewModelScope.launch {
        repo.logout()
        _state.update { AuthState(isLoggedIn = false) }
    }
}
