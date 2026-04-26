package com.dieang.energym.ui.feature.recetas

import com.dieang.energym.domain.model.Receta

data class RecetaState(
    val isLoading: Boolean = false,
    val resumenHoy: NutricionResumen = NutricionResumen(),
    val recetas: List<RecetaUI> = emptyList(),
    val recetaSeleccionada: RecetaDetailUI? = null,
    val error: String? = null
)

data class NutricionResumen(
    val caloriasConsumidas: Int = 0,
    val caloriasRestantes: Int = 0,
    val proteinaG: Int = 0,
    val carbohidratosG: Int = 0,
    val estadoProteina: String = "En objetivo",
    val estadoCarbos: String = "Bien"
)

data class RecetaUI(
    val id: String,
    val nombre: String,
    val calorias: Int,
    val tiempoMin: Int,
    val proteinaG: Int,
    val categoria: String, // Ej: "Pescado", "Huevo"
    val imagenUrl: String? = null
)

data class RecetaDetailUI(
    val id: String,
    val nombre: String,
    val calorias: Int,
    val tiempoMin: Int,
    val proteinaG: Int,
    val grasasG: Int,
    val carbosG: Int,
    val descripcion: String,
    val ingredientes: List<String>,
    val pasos: List<String>,
    val imagenUrl: String? = null
)
