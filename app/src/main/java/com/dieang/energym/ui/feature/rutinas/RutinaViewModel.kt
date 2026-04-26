package com.dieang.energym.ui.feature.rutinas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dieang.energym.domain.usecase.rutinas.GetRutinaEjerciciosUseCase
import com.dieang.energym.domain.usecase.rutinas.GetRutinaUseCase
import com.dieang.energym.domain.usecase.rutinas.GetRutinasUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class RutinaViewModel @Inject constructor(
    private val getRutinas: GetRutinasUseCase,
    private val getRutina: GetRutinaUseCase,
    private val getRutinaEjercicios: GetRutinaEjerciciosUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RutinaState())
    val state = _state.asStateFlow()

    init {
        loadTrainingData()
    }

    fun loadTrainingData() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }

        val dummyRutinas = listOf(
            RutinaUI(
                id = UUID.randomUUID(),
                nombre = "Push (Empuje)",
                numEjercicios = 8,
                ultimaVez = "2 días"
            ),
            RutinaUI(
                id = UUID.randomUUID(),
                nombre = "Pull (Tirón)",
                numEjercicios = 7,
                ultimaVez = "4 días"
            ),
            RutinaUI(
                id = UUID.randomUUID(),
                nombre = "Legs (Piernas)",
                numEjercicios = 9,
                ultimaVez = "6 días"
            )
        )

        _state.update {
            it.copy(
                isLoading = false,
                rutinas = dummyRutinas
            )
        }
    }

    fun loadRutinaDetalle(rutinaId: UUID) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        try {
            val dummyRutina = RutinaUI(
                id = rutinaId,
                nombre = "Push Day - Pecho y Tríceps",
                numEjercicios = 8,
                ultimaVez = "2 días",
                descripcion = "Enfoque en fuerza e hipertrofia para el torso superior (empuje).",
                nivel = "Avanzado",
                objetivo = "Hipertrofia"
            )

            val dummyEjercicios = listOf(
                EjercicioUI("1", "Press de Banca con Barra", 4, "8-10", 90, 80f),
                EjercicioUI("2", "Press Inclinado con Mancuernas", 3, "10-12", 60, 26f),
                EjercicioUI("3", "Aperturas en Polea", 3, "12-15", 45, 15f),
                EjercicioUI("4", "Press Militar", 3, "10", 60, 20f),
                EjercicioUI("5", "Extensiones de Tríceps", 4, "12", 45, 25f)
            )

            _state.update {
                it.copy(
                    isLoading = false,
                    rutinaSeleccionada = dummyRutina,
                    ejercicios = dummyEjercicios
                )
            }
        } catch (e: Exception) {
            _state.update { it.copy(isLoading = false, error = e.message) }
        }
    }
}
