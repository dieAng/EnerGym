package com.dieang.energym.ui.feature.ejercicios

import com.dieang.energym.domain.model.Ejercicio

data class EjercicioState(
    val isLoading: Boolean = false,
    val ejercicios: List<Ejercicio> = emptyList(),
    val ejercicioSeleccionado: Ejercicio? = null,
    val error: String? = null
)

