package com.dieang.energym.ui.feature.recetas

import com.dieang.energym.domain.model.Receta

data class RecetaState(
    val isLoading: Boolean = false,
    val recetas: List<Receta> = emptyList(),
    val recetaSeleccionada: Receta? = null,
    val error: String? = null
)
