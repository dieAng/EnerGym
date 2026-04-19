package com.dieang.energym.ui.feature.sesiones

import java.util.UUID

data class SesionState(
    val isLoading: Boolean = false,
    val isActive: Boolean = false,
    val isFinished: Boolean = false,
    val tiempoSegundos: Int = 0,
    val progresoEjercicio: Float = 0.5f, // 50% completado
    val ejercicioActual: String = "Press de Banca",
    val seriesRestantes: Int = 2,
    val caloriasQuemadas: Int = 145,
    val energiaGenerada: Int = 42, // En Watts o Wh (según Figma)
    val series: List<SerieUI> = emptyList(),
    val sesiones: List<SesionUI> = emptyList(), // Historial de sesiones
    val filtroMes: String? = null,
    val filtroTipo: String? = null,
    val resumenFinal: SesionResumenUI? = null,
    val error: String? = null
)

data class SesionUI(
    val id: UUID,
    val fecha: String,
    val mes: String,
    val tipo: String, // E.g. "Fuerza", "Cardio"
    val series: List<SerieUI> = emptyList(),
    val energiaWh: Int = 0
)

data class SesionResumenUI(
    val tiempoTotal: String,
    val caloriasTotales: Int,
    val energiaTotal: Int,
    val puntosGanados: Int,
    val logrosDesbloqueados: List<String>
)

data class SerieUI(
    val numero: Int,
    val reps: Int,
    val peso: Float,
    val completada: Boolean = false
)
