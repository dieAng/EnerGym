package com.dieang.energym.ui.feature.ejercicios

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dieang.energym.domain.usecase.ejercicios.GetEjercicioUseCase
import com.dieang.energym.domain.usecase.ejercicios.GetEjerciciosUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EjercicioViewModel(
    private val getEjercicios: GetEjerciciosUseCase,
    private val getEjercicio: GetEjercicioUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(EjercicioState())
    val state = _state.asStateFlow()

    fun loadEjercicios() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }

        try {
            val ejercicios = getEjercicios()
            _state.update { it.copy(isLoading = false, ejercicios = ejercicios) }
        } catch (e: Exception) {
            _state.update { it.copy(isLoading = false, error = e.message) }
        }
    }
}

