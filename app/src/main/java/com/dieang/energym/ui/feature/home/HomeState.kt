package com.dieang.energym.ui.feature.home

data class HomeState(
    val isLoading: Boolean = false,
    val nombreUsuario: String = "",
    val rachaActual: Int = 0,
    val entrenamientosSemana: String = "0/0",
    val entrenamientoHoy: EntrenamientoHoy? = null,
    val pesoReciente: Float = 0f,
    val historialPeso: List<Float> = emptyList(),
    val energiaSemanalWh: List<Int> = listOf(0, 0, 0, 0, 0, 0, 0), // Lunes a Domingo
    val energiaTotalSemana: Int = 0,
    val error: String? = null
)

data class EntrenamientoHoy(
    val nombre: String,
    val numEjercicios: Int,
    val duracionMin: Int,
    val tags: List<String>
)
