package com.dieang.energym.ui.feature.ingredientes

import com.dieang.energym.domain.model.Ingrediente

data class IngredienteState(
    val isLoading: Boolean = false,
    val ingredientes: List<Ingrediente> = emptyList(),
    val error: String? = null
)
