package com.dieang.energym.ui.feature.sesiones

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dieang.energym.domain.usecase.rutinas.GetRutinaEjerciciosUseCase
import com.dieang.energym.domain.usecase.rutinas.GetRutinaUseCase
import com.dieang.energym.domain.usecase.sesiones.AddSerieUseCase
import com.dieang.energym.domain.usecase.sesiones.CreateSesionUseCase
import com.dieang.energym.domain.usecase.sesiones.SaveSesionUseCase
import com.dieang.energym.domain.usecase.auth.GetLoggedUserUseCase
import com.dieang.energym.domain.usecase.posts.CreatePostUseCase
import com.dieang.energym.domain.model.Post
import com.dieang.energym.data.remote.dto.PostCreateRequestDto
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
    private val createSesion: CreateSesionUseCase,
    private val addSerie: AddSerieUseCase,
    private val saveSesion: SaveSesionUseCase,
    private val getLoggedUser: GetLoggedUserUseCase,
    private val createPost: CreatePostUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SesionState())
    val state = _state.asStateFlow()

    private var timerJob: Job? = null

    init {
        // Simulamos el inicio de una sesión para desarrollo visual
        loadDummyActiveSession()
    }

    private fun loadDummyActiveSession() {
        val dummySeries = listOf(
            SerieUI(1, 10, 80f, true),
            SerieUI(2, 10, 80f, true),
            SerieUI(3, 8, 80f, false),
            SerieUI(4, 8, 80f, false)
        )

        _state.update {
            it.copy(
                isActive = true,
                ejercicioActual = "Press de Banca con Barra",
                seriesRestantes = 2,
                tiempoSegundos = 455, // 07:35
                caloriasQuemadas = 145,
                energiaGenerada = 42,
                series = dummySeries,
                progresoEjercicio = 0.5f
            )
        }
        startTimer()
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                _state.update { it.copy(tiempoSegundos = it.tiempoSegundos + 1) }
            }
        }
    }

    fun toggleSerie(numero: Int) {
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
        
        // Datos finales
        val duracion = currentState.tiempoSegundos
        val energia = currentState.energiaGenerada
        val calorias = currentState.caloriasQuemadas
        
        // Guardar en Room
        val usuario = getLoggedUser().first()
        usuario?.let { user ->
            saveSesion(
                usuarioId = user.id,
                rutinaId = null, // Podría pasarse el ID real si se inició desde una rutina
                duracion = duracion,
                energia = energia,
                calorias = calorias
            )
        }

        val resumen = SesionResumenUI(
            tiempoTotal = formatTime(duracion),
            caloriasTotales = calorias,
            energiaTotal = energia,
            puntosGanados = (energia * 10), // Ejemplo: 10 puntos por Wh
            logrosDesbloqueados = listOf("Generador de Energía")
        )
        _state.update { it.copy(isActive = false, isFinished = true, resumenFinal = resumen) }
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
                    energiaWh = resumen.energiaTotal
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
