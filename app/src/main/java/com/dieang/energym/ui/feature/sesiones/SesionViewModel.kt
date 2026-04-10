package com.dieang.energym.ui.feature.sesiones

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dieang.energym.domain.usecase.sesiones.AddSerieUseCase
import com.dieang.energym.domain.usecase.sesiones.CreateSesionUseCase
import com.dieang.energym.domain.usecase.sesiones.GetSesionesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SesionViewModel(
    private val getSesiones: GetSesionesUseCase,
    private val createSesion: CreateSesionUseCase,
    private val addSerie: AddSerieUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SesionState())
    val state = _state.asStateFlow()

    fun loadSesiones() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }

        try {
            val sesiones = getSesiones()
            _state.update { it.copy(isLoading = false, sesiones = sesiones) }
        } catch (e: Exception) {
            _state.update { it.copy(isLoading = false, error = e.message) }
        }
    }
}

