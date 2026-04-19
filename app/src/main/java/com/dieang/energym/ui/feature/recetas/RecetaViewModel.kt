package com.dieang.energym.ui.feature.recetas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dieang.energym.domain.usecase.ingredientes.GetIngredientesByRecetaUseCase
import com.dieang.energym.domain.usecase.recetas.GetRecetaUseCase
import com.dieang.energym.domain.usecase.recetas.GetRecetasUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class RecetaViewModel @Inject constructor(
    private val getRecetas: GetRecetasUseCase,
    private val getReceta: GetRecetaUseCase,
    private val getIngredientesByReceta: GetIngredientesByRecetaUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RecetaState())
    val state = _state.asStateFlow()

    init {
        loadNutritionData()
    }

    fun loadNutritionData() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }

        // Datos del Resumen de Hoy (según Figma)
        val dummyResumen = NutricionResumen(
            caloriasConsumidas = 2150,
            caloriasRestantes = 250,
            proteinaG = 165,
            carbohidratosG = 180,
            estadoProteina = "En objetivo",
            estadoCarbos = "Bien"
        )

        // Lista de Recetas (según Figma)
        val dummyRecetas = listOf(
            RecetaUI(
                id = "1",
                nombre = "Pollo al Horno con Verduras",
                calorias = 450,
                tiempoMin = 35,
                proteinaG = 45,
                categoria = "Aves"
            ),
            RecetaUI(
                id = "2",
                nombre = "Bowl de Quinoa y Salmón",
                calorias = 520,
                tiempoMin = 25,
                proteinaG = 38,
                categoria = "Pescado"
            ),
            RecetaUI(
                id = "3",
                nombre = "Tortilla de Claras con Avena",
                calorias = 280,
                tiempoMin = 15,
                proteinaG = 28,
                categoria = "Huevo"
            )
        )

        _state.update {
            it.copy(
                isLoading = false,
                resumenHoy = dummyResumen,
                recetas = dummyRecetas
            )
        }
    }

    fun loadRecetaDetalle(recetaId: String) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        try {
            // Simulación de detalle de receta (Figma Match)
            val dummyDetalle = RecetaDetailUI(
                id = recetaId,
                nombre = "Pollo al Horno con Verduras",
                calorias = 450,
                tiempoMin = 35,
                proteinaG = 45,
                grasasG = 12,
                carbosG = 30,
                descripcion = "Una receta clásica, saludable y llena de proteínas, ideal para tus almuerzos de post-entrenamiento.",
                ingredientes = listOf(
                    "200g de Pechuga de Pollo",
                    "1 Pimiento Rojo",
                    "1 Calabacín pequeño",
                    "100g de Brócoli",
                    "1 cucharada de Aceite de Oliva",
                    "Sal, pimienta y romero al gusto"
                ),
                pasos = listOf(
                    "Precalienta el horno a 200°C.",
                    "Corta el pollo y las verduras en trozos medianos.",
                    "Coloca todo en una bandeja, añade aceite y especias.",
                    "Hornea durante 25-30 minutos hasta que el pollo esté dorado.",
                    "Sirve caliente y disfruta de tu comida saludable."
                )
            )

            _state.update {
                it.copy(
                    isLoading = false,
                    recetaSeleccionada = dummyDetalle
                )
            }
        } catch (e: Exception) {
            _state.update { it.copy(isLoading = false, error = e.message) }
        }
    }
}
