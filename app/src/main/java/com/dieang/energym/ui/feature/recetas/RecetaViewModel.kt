package com.dieang.energym.ui.feature.recetas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dieang.energym.domain.repository.RecetaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecetaViewModel(
    private val repo: RecetaRepository
) : ViewModel() {

    private val _state = MutableStateFlow(RecetaState())
    val state = _state.asStateFlow()

    fun loadRecetas() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }

        try {
            val recetas = repo.getRecetas()
            _state.update { it.copy(isLoading = false, recetas = recetas) }
        } catch (e: Exception) {
            _state.update { it.copy(isLoading = false, error = e.message) }
        }
    }
}
