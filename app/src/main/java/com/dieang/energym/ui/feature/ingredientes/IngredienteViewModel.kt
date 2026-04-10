package com.dieang.energym.ui.feature.ingredientes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dieang.energym.domain.repository.IngredienteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class IngredienteViewModel(
    private val repo: IngredienteRepository
) : ViewModel() {

    private val _state = MutableStateFlow(IngredienteState())
    val state = _state.asStateFlow()

    fun loadIngredientes(recetaId: UUID) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }

        try {
            val ingredientes = repo.getIngredientesByReceta(recetaId)
            _state.update { it.copy(isLoading = false, ingredientes = ingredientes) }
        } catch (e: Exception) {
            _state.update { it.copy(isLoading = false, error = e.message) }
        }
    }
}
