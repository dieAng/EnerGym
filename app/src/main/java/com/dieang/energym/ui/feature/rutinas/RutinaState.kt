package com.dieang.energym.ui.feature.rutinas

import com.dieang.energym.domain.model.Rutina
import java.util.UUID

data class RutinaState(
    val isLoading: Boolean = false,
    val rutinas: List<RutinaUI> = emptyList(),
    val rutinaSeleccionada: RutinaUI? = null,
    val ejercicios: List<EjercicioUI> = emptyList(),
    val error: String? = null
)

data class RutinaUI(
    val id: UUID,
    val nombre: String,
    val numEjercicios: Int,
    val ultimaVez: String, // Ej: "Hace 2 días"
    val nivel: String = "Intermedio",
    val objetivo: String = "Fuerza",
    val descripcion: String = ""
)

data class EjercicioUI(
    val id: String,
    val nombre: String,
    val series: Int,
    val repeticiones: String, // "10-12" o "15"
    val descansoSeg: Int,
    val pesoSugerido: Float? = null,
    val imagenUrl: String? = null
)
