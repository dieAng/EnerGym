package com.dieang.energym.ui.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dieang.energym.domain.usecase.auth.GetLoggedUserUseCase
import com.dieang.energym.domain.usecase.auth.LoginUserUseCase
import com.dieang.energym.domain.usecase.auth.LogoutUserUseCase
import com.dieang.energym.domain.usecase.auth.RegisterUserUseCase
import com.dieang.energym.data.remote.dto.request.UsuarioCreateRequestDto
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
    private val getLoggedUser: GetLoggedUserUseCase,
    private val registerUser: RegisterUserUseCase
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
        _state.update { it.copy(isLoading = true, isLoggedIn = null) }

        try {
            loginUser(email, password)
            // observeUser se encargará de actualizar isLoggedIn
        } catch (e: Exception) {
            _state.update { it.copy(isLoading = false, error = e.message, isLoggedIn = false) }
        }
    }

    fun register(nombre: String, email: String, password: String) = viewModelScope.launch {
        android.util.Log.d("AuthViewModel", "Iniciando registro para: $email")
        _state.update { it.copy(isLoading = true, error = null) }
        try {
            registerUser(UsuarioCreateRequestDto(nombre, email, password))
            android.util.Log.d("AuthViewModel", "Registro exitoso, iniciando login...")
            // Auto login after registration
            loginUser(email, password)
            _state.update { it.copy(isLoading = false) }
            android.util.Log.d("AuthViewModel", "Login post-registro completado.")
        } catch (e: Exception) {
            android.util.Log.e("AuthViewModel", "Error en registro/login: ${e.message}", e)
            _state.update { it.copy(isLoading = false, error = e.message) }
        }
    }

    fun logout() = viewModelScope.launch {
        logoutUser()
        _state.update { AuthState() }
    }
}

