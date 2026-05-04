package com.dieang.energym.ui.feature.rutinas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dieang.energym.domain.usecase.rutinas.GetRutinaEjerciciosUseCase
import com.dieang.energym.domain.usecase.rutinas.GetRutinaUseCase
import com.dieang.energym.domain.usecase.rutinas.GetRutinasUseCase
import com.dieang.energym.domain.usecase.ejercicios.GetEjercicioUseCase
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
    private val getRutinaEjercicios: GetRutinaEjerciciosUseCase,
    private val getEjercicio: GetEjercicioUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RutinaState())
    val state = _state.asStateFlow()

    init {
        loadTrainingData()
    }

    fun loadTrainingData() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        try {
            val dbRutinas = getRutinas()
            val rutinasUI = dbRutinas.map {
                RutinaUI(
                    id = it.id,
                    nombre = it.nombre,
                    numEjercicios = 0, // Podríamos contar ejercicios si quisiéramos
                    ultimaVez = "Hoy",
                    nivel = it.nivel ?: "Intermedio",
                    objetivo = it.objetivo ?: "Fuerza",
                    descripcion = it.descripcion ?: ""
                )
            }
            _state.update {
                it.copy(
                    isLoading = false,
                    rutinas = rutinasUI
                )
            }
        } catch (e: Exception) {
            _state.update { it.copy(isLoading = false, error = e.message) }
        }
    }

    fun loadRutinaDetalle(rutinaId: UUID) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        try {
            val rutina = getRutina(rutinaId)
            val ejerciciosRutina = getRutinaEjercicios(rutinaId)

            if (rutina != null) {
                val rutinaUI = RutinaUI(
                    id = rutina.id,
                    nombre = rutina.nombre,
                    numEjercicios = ejerciciosRutina.size,
                    ultimaVez = "Hoy",
                    descripcion = rutina.descripcion ?: "",
                    nivel = rutina.nivel ?: "Intermedio",
                    objetivo = rutina.objetivo ?: "Fuerza"
                )

                val ejerciciosUI = ejerciciosRutina.map {
                    val nombreEjercicio = getEjercicio(it.ejercicioId)?.nombre ?: "Ejercicio"
                    EjercicioUI(
                        id = it.ejercicioId.toString(),
                        nombre = nombreEjercicio,
                        series = it.series,
                        repeticiones = it.repeticiones.toString(),
                        descansoSeg = it.descansoSeg ?: 60,
                        pesoSugerido = it.pesoObjetivo
                    )
                }

                _state.update {
                    it.copy(
                        isLoading = false,
                        rutinaSeleccionada = rutinaUI,
                        ejercicios = ejerciciosUI
                    )
                }
            } else {
                _state.update { it.copy(isLoading = false, error = "Rutina no encontrada") }
            }
        } catch (e: Exception) {
            _state.update { it.copy(isLoading = false, error = e.message) }
        }
    }
}
