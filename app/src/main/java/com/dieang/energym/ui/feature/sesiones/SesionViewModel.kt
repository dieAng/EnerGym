package com.dieang.energym.ui.feature.sesiones

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dieang.energym.domain.usecase.rutinas.GetRutinaEjerciciosUseCase
import com.dieang.energym.domain.usecase.rutinas.GetRutinaUseCase
import com.dieang.energym.domain.usecase.sesiones.AddSerieUseCase
import com.dieang.energym.domain.usecase.sesiones.SaveSesionUseCase
import com.dieang.energym.domain.usecase.auth.GetLoggedUserUseCase
import com.dieang.energym.domain.usecase.posts.CreatePostUseCase
import com.dieang.energym.data.remote.dto.request.PostCreateRequestDto
import com.dieang.energym.domain.model.SerieRealizada
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SesionViewModel @Inject constructor(
    private val getRutina: GetRutinaUseCase,
    private val getRutinaEjercicios: GetRutinaEjerciciosUseCase,
    private val addSerie: AddSerieUseCase,
    private val saveSesion: SaveSesionUseCase,
    private val getLoggedUser: GetLoggedUserUseCase,
    private val createPost: CreatePostUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SesionState())
    val state = _state.asStateFlow()

    private var timerJob: Job? = null

    fun startSesion(rutinaId: UUID) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        try {
            val rutina = getRutina(rutinaId)
            val ejercicios = getRutinaEjercicios(rutinaId)
            
            _state.update {
                it.copy(
                    isLoading = false,
                    isActive = true,
                    activeRutina = rutina,
                    ejercicios = ejercicios,
                    tiempoTranscurrido = 0,
                    energiaGenerada = 0.0,
                    caloriasQuemadas = 0,
                    seriesCompletadas = emptyMap()
                )
            }
            startTimer()
        } catch (e: Exception) {
            _state.update { it.copy(isLoading = false, error = "Error al iniciar sesión: ${e.message}") }
        }
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                _state.update { it.copy(tiempoTranscurrido = it.tiempoTranscurrido + 1) }
            }
        }
    }

    fun registrarSerie(ejercicioId: UUID, repeticiones: Int, peso: Float) = viewModelScope.launch {
        val nuevaSerie = SerieRealizada(
            id = UUID.randomUUID(),
            sesionId = UUID.randomUUID(), // En producción usaríamos un ID de sesión real
            ejercicioId = ejercicioId,
            repeticiones = repeticiones,
            peso = peso
        )
        
        _state.update { currentState ->
            val updatedMap = currentState.seriesCompletadas.toMutableMap()
            val seriesList = updatedMap[ejercicioId]?.toMutableList() ?: mutableListOf()
            seriesList.add(nuevaSerie)
            updatedMap[ejercicioId] = seriesList
            
            // Simulación de energía generada: (reps * peso) / factor
            val energiaGanada = (repeticiones * peso * 0.05)
            val caloriasGanadas = (repeticiones * 0.5).toInt()
            
            currentState.copy(
                seriesCompletadas = updatedMap,
                energiaGenerada = currentState.energiaGenerada + energiaGanada,
                caloriasQuemadas = currentState.caloriasQuemadas + caloriasGanadas
            )
        }
    }

    fun toggleSerie(numero: Int) {
        // Mantenemos este para compatibilidad si alguna pantalla lo usa
        _state.update { currentState ->
            val updatedSeries = currentState.series.map {
                if (it.numero == numero) it.copy(completada = !it.completada) else it
            }
            currentState.copy(series = updatedSeries)
        }
    }

    fun finishSesion() = viewModelScope.launch {
        timerJob?.cancel()
        val currentState = _state.value
        
        _state.update { it.copy(isLoading = true) }
        
        try {
            // Datos finales
            val duracion = currentState.tiempoTranscurrido.toInt()
            val energia = currentState.energiaGenerada.toInt()
            val calorias = currentState.caloriasQuemadas
            
            // Intentar obtener usuario
            val usuario = getLoggedUser().first()
            if (usuario == null) {
                _state.update { it.copy(isLoading = false, error = "Usuario no identificado") }
                return@launch
            }

            // Guardar localmente
            saveSesion(
                usuarioId = usuario.id,
                rutinaId = currentState.activeRutina?.id,
                duracion = duracion,
                energia = energia,
                calorias = calorias
            )

            val resumen = SesionResumenUI(
                tiempoTotal = formatTime(duracion),
                caloriasTotales = calorias,
                energiaTotal = energia,
                puntosGanados = (energia * 10),
                logrosDesbloqueados = if (energia > 50) listOf("Generador de Energía") else emptyList()
            )
            
            _state.update { 
                it.copy(
                    isLoading = false,
                    isActive = false, 
                    isFinished = true, 
                    resumenFinal = resumen 
                ) 
            }
        } catch (e: Exception) {
            _state.update { it.copy(isLoading = false, error = "Error al finalizar: ${e.message}") }
        }
    }

    fun compartirLogro() = viewModelScope.launch {
        val resumen = _state.value.resumenFinal ?: return@launch
        val usuario = getLoggedUser().first() ?: return@launch
        
        _state.update { it.copy(isLoading = true) }
        
        try {
            val contenido = "¡Acabo de terminar mi entrenamiento en EnerGym! ⚡️ " +
                    "He generado ${resumen.energiaTotal} Wh de energía limpia para el planeta. 🌍💪"
            
            createPost(
                PostCreateRequestDto(
                    usuarioId = usuario.id,
                    contenido = contenido,
                    imagenUrl = null,
                    energiaGenerada = resumen.energiaTotal.toDouble()
                )
            )
            // Aquí podríamos añadir un estado de éxito para mostrar un Toast
        } catch (e: Exception) {
            _state.update { it.copy(error = "No se pudo compartir el logro") }
        } finally {
            _state.update { it.copy(isLoading = false) }
        }
    }

    private fun formatTime(seconds: Int): String {
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return "%02d:%02d".format(minutes, remainingSeconds)
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}
