package com.dieang.energym.ui.feature.rutinas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dieang.energym.domain.usecase.rutinas.CreateRutinaUseCase
import com.dieang.energym.domain.usecase.rutinas.DeleteRutinaUseCase
import com.dieang.energym.domain.usecase.rutinas.GetRutinaUseCase
import com.dieang.energym.domain.usecase.rutinas.GetRutinasUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RutinaViewModel(
    private val getRutinas: GetRutinasUseCase,
    private val getRutina: GetRutinaUseCase,
    private val createRutina: CreateRutinaUseCase,
    private val deleteRutina: DeleteRutinaUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RutinaState())
    val state = _state.asStateFlow()

    fun loadRutinas() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }

        try {
            val rutinas = getRutinas()
            _state.update { it.copy(isLoading = false, rutinas = rutinas) }
        } catch (e: Exception) {
            _state.update { it.copy(isLoading = false, error = e.message) }
        }
    }
}

