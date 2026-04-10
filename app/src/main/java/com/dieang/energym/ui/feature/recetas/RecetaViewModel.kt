package com.dieang.energym.ui.feature.recetas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dieang.energym.domain.usecase.recetas.CreateRecetaUseCase
import com.dieang.energym.domain.usecase.recetas.DeleteRecetaUseCase
import com.dieang.energym.domain.usecase.recetas.GetRecetaUseCase
import com.dieang.energym.domain.usecase.recetas.GetRecetasUseCase
import com.dieang.energym.domain.usecase.recetas.UpdateRecetaUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecetaViewModel(
    private val getRecetas: GetRecetasUseCase,
    private val getReceta: GetRecetaUseCase,
    private val createReceta: CreateRecetaUseCase,
    private val updateReceta: UpdateRecetaUseCase,
    private val deleteReceta: DeleteRecetaUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RecetaState())
    val state = _state.asStateFlow()

    fun loadRecetas() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }

        try {
            val recetas = getRecetas()
            _state.update { it.copy(isLoading = false, recetas = recetas) }
        } catch (e: Exception) {
            _state.update { it.copy(isLoading = false, error = e.message) }
        }
    }
}

