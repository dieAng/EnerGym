package com.dieang.energym.ui.feature.rutinas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dieang.energym.domain.repository.RutinaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RutinaViewModel(
    private val repo: RutinaRepository
) : ViewModel() {

    private val _state = MutableStateFlow(RutinaState())
    val state = _state.asStateFlow()

    fun loadRutinas() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }

        try {
            val rutinas = repo.getRutinas()
            _state.update { it.copy(isLoading = false, rutinas = rutinas) }
        } catch (e: Exception) {
            _state.update { it.copy(isLoading = false, error = e.message) }
        }
    }
}
