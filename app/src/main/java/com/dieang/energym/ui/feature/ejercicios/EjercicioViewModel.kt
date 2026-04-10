package com.dieang.energym.ui.feature.ejercicios

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dieang.energym.domain.repository.EjercicioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EjercicioViewModel(
    private val repo: EjercicioRepository
) : ViewModel() {

    private val _state = MutableStateFlow(EjercicioState())
    val state = _state.asStateFlow()

    fun loadEjercicios() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }

        try {
            val ejercicios = repo.getEjercicios()
            _state.update { it.copy(isLoading = false, ejercicios = ejercicios) }
        } catch (e: Exception) {
            _state.update { it.copy(isLoading = false, error = e.message) }
        }
    }
}
