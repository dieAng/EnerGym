package com.dieang.energym.ui.feature.rutinas

import com.dieang.energym.domain.model.Rutina

data class RutinaState(
    val isLoading: Boolean = false,
    val rutinas: List<Rutina> = emptyList(),
    val rutinaSeleccionada: Rutina? = null,
    val error: String? = null
)
