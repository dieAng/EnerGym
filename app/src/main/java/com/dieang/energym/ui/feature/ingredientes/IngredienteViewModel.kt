package com.dieang.energym.ui.feature.ingredientes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dieang.energym.domain.usecase.ingredientes.CreateIngredienteUseCase
import com.dieang.energym.domain.usecase.ingredientes.GetIngredientesByRecetaUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class IngredienteViewModel(
    private val getIngredientes: GetIngredientesByRecetaUseCase,
    private val createIngrediente: CreateIngredienteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(IngredienteState())
    val state = _state.asStateFlow()

    fun loadIngredientes(recetaId: UUID) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }

        try {
            val ingredientes = getIngredientes(recetaId)
            _state.update { it.copy(isLoading = false, ingredientes = ingredientes) }
        } catch (e: Exception) {
            _state.update { it.copy(isLoading = false, error = e.message) }
        }
    }
}

