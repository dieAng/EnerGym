package com.dieang.energym.ui.feature.usuario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dieang.energym.domain.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsuarioViewModel(
    private val repo: UsuarioRepository
) : ViewModel() {

    private val _state = MutableStateFlow(UsuarioState())
    val state = _state.asStateFlow()

    fun loadUsuarios() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }

        try {
            val usuarios = repo.getUsuarios()
            _state.update { it.copy(isLoading = false, usuarios = usuarios) }
        } catch (e: Exception) {
            _state.update { it.copy(isLoading = false, error = e.message) }
        }
    }
}
