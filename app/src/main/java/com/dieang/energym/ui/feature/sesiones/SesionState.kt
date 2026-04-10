package com.dieang.energym.ui.feature.sesiones

import com.dieang.energym.domain.model.SesionEntrenamiento

data class SesionState(
    val isLoading: Boolean = false,
    val sesiones: List<SesionEntrenamiento> = emptyList(),
    val sesionSeleccionada: SesionEntrenamiento? = null,
    val error: String? = null
)
