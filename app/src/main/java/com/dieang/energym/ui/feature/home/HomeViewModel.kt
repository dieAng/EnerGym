package com.dieang.energym.ui.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dieang.energym.domain.usecase.auth.GetLoggedUserUseCase
import com.dieang.energym.domain.usecase.recetas.GetRecetasUseCase
import com.dieang.energym.domain.usecase.rutinas.GetRutinasUseCase
import com.dieang.energym.domain.usecase.sesiones.GetSesionesUseCase
import com.dieang.energym.domain.usecase.auth.LogoutUserUseCase
import com.dieang.energym.domain.usecase.peso.GetHistorialPesoUseCase
import com.dieang.energym.domain.usecase.peso.RegistrarPesoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLoggedUser: GetLoggedUserUseCase,
    private val getSesiones: GetSesionesUseCase,
    private val getRutinas: GetRutinasUseCase,
    private val getRecetas: GetRecetasUseCase,
    private val logoutUser: LogoutUserUseCase,
    private val getHistorialPeso: GetHistorialPesoUseCase,
    private val registrarPeso: RegistrarPesoUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getLoggedUser().collectLatest { usuario ->
                _state.update { it.copy(nombreUsuario = usuario?.nombre ?: "Usuario") }
                
                usuario?.let { user ->
                    getHistorialPeso(user.id).collectLatest { historial ->
                        _state.update {
                            it.copy(
                                historialPeso = historial.map { p -> p.peso },
                                pesoReciente = historial.lastOrNull()?.peso ?: 0f
                            )
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.LoadData -> loadData()
            HomeEvent.OnLogoutClick -> logout()
            is HomeEvent.OnPesoSubmit -> submitPeso(event.peso)
        }
    }

    private fun submitPeso(peso: Float) = viewModelScope.launch {
        val usuario = getLoggedUser().first()
        usuario?.let { registrarPeso(it.id, peso) }
    }

    private fun logout() = viewModelScope.launch { logoutUser() }

    private fun loadData() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }

        try {
            val sesiones = getSesiones()
            
            // Lógica para el gráfico de barras semanal (Lunes a Domingo)
            val energiaSemanal = MutableList(7) { 0 }
            val calendar = Calendar.getInstance()
            
            // Obtener el inicio de la semana actual (Lunes)
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            val inicioSemana = calendar.timeInMillis

            sesiones.filter { it.fecha >= inicioSemana }.forEach { sesion ->
                calendar.timeInMillis = sesion.fecha
                val dia = (calendar.get(Calendar.DAY_OF_WEEK) + 5) % 7 // Convertir a 0=Lunes, 6=Domingo
                energiaSemanal[dia] += sesion.energiaGeneradaWh ?: 0
            }

            val dummyEntrenamiento = EntrenamientoHoy(
                nombre = "Push Day - Pecho y Tríceps",
                numEjercicios = 8,
                duracionMin = 55,
                tags = listOf("Hipertrofia", "Intenso")
            )

            _state.update {
                it.copy(
                    isLoading = false,
                    rachaActual = 12,
                    entrenamientosSemana = "${sesiones.filter { s -> s.fecha >= inicioSemana }.size}/5",
                    entrenamientoHoy = dummyEntrenamiento,
                    energiaSemanalWh = energiaSemanal,
                    energiaTotalSemana = energiaSemanal.sum()
                )
            }
        } catch (e: Exception) {
            _state.update { it.copy(isLoading = false, error = e.message) }
        }
    }
}
