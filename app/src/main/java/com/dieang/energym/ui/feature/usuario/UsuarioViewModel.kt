package com.dieang.energym.ui.feature.usuario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dieang.energym.domain.usecase.usuario.CreateUsuarioUseCase
import com.dieang.energym.domain.usecase.usuario.DeleteUsuarioUseCase
import com.dieang.energym.domain.usecase.usuario.GetUsuarioUseCase
import com.dieang.energym.domain.usecase.usuario.GetUsuariosUseCase
import com.dieang.energym.domain.usecase.usuario.UpdateUsuarioUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsuarioViewModel(
    private val getUsuarios: GetUsuariosUseCase,
    private val getUsuario: GetUsuarioUseCase,
    private val createUsuario: CreateUsuarioUseCase,
    private val updateUsuario: UpdateUsuarioUseCase,
    private val deleteUsuario: DeleteUsuarioUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UsuarioState())
    val state = _state.asStateFlow()

    fun loadUsuarios() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }

        try {
            val usuarios = getUsuarios()
            _state.update { it.copy(isLoading = false, usuarios = usuarios) }
        } catch (e: Exception) {
            _state.update { it.copy(isLoading = false, error = e.message) }
        }
    }
}

