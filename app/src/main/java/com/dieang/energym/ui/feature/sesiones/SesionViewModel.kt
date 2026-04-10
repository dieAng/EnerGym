package com.dieang.energym.ui.feature.sesiones

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dieang.energym.domain.repository.SesionEntrenamientoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SesionViewModel(
    private val repo: SesionEntrenamientoRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SesionState())
    val state = _state.asStateFlow()

    fun loadSesiones() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }

        try {
            val sesiones = repo.getSesiones()
            _state.update { it.copy(isLoading = false, sesiones = sesiones) }
        } catch (e: Exception) {
            _state.update { it.copy(isLoading = false, error = e.message) }
        }
    }
}
