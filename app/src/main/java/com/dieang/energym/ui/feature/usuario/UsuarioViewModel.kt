package com.dieang.energym.ui.feature.usuario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dieang.energym.domain.usecase.auth.LogoutUserUseCase
import com.dieang.energym.domain.usecase.auth.GetLoggedUserUseCase
import com.dieang.energym.domain.usecase.sesiones.GetSesionesUseCase
import com.dieang.energym.data.remote.dto.request.UsuarioUpdateRequestDto
import com.dieang.energym.domain.usecase.usuario.GetEstadisticasUsuarioUseCase
import com.dieang.energym.domain.usecase.usuario.UpdateUsuarioUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class UsuarioViewModel @Inject constructor(
    private val getLoggedUser: GetLoggedUserUseCase,
    private val getSesiones: GetSesionesUseCase,
    private val updateUsuario: UpdateUsuarioUseCase,
    private val getEstadisticas: GetEstadisticasUsuarioUseCase,
    private val logoutUser: LogoutUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UsuarioState())
    val state = _state.asStateFlow()

    init {
        loadPerfil()
    }

    fun loadPerfil(userId: UUID? = null) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }

        if (userId == null) {
            getLoggedUser().collectLatest { usuario ->
                if (usuario != null) {
                    fetchUserData(usuario)
                }
            }
        } else {
            // Cargar datos de otro usuario (simulado para el MVP)
            // En una app real esto vendría de un GetUserByIdUseCase
            _state.update { 
                it.copy(
                    isLoading = false,
                    username = "@usuario_pro",
                    stats = PerfilStats(15, 20, 10, 2500),
                    historialSesiones = emptyList() // O sesiones públicas
                )
            }
        }
    }

    private suspend fun fetchUserData(usuario: com.dieang.energym.domain.model.Usuario) {
        try {
            val sesiones = getSesiones()
            
            // Calcular estadísticas totales
            val energiaTotal = sesiones.sumOf { it.energiaGenerada ?: 0 }
            
            val perfilStats = PerfilStats(
                entrenamientos = sesiones.size,
                rachaMaxima = 12, 
                prsLogrados = 8,
                energiaTotalWh = energiaTotal
            )

            // Mapear historial de sesiones
            val dateFormat = SimpleDateFormat("dd MMM, yyyy", Locale.getDefault())
            val historial = sesiones.map { sesion ->
                SesionHistorialUI(
                    id = sesion.id.toString(),
                    fecha = dateFormat.format(Date(sesion.fecha)),
                    duracion = formatDuration(sesion.duracionSegundos ?: 0),
                    energia = sesion.energiaGenerada ?: 0,
                    calorias = sesion.caloriasQuemadas ?: 0,
                    rutinaNombre = "Entrenamiento Libre"
                )
            }.reversed()

            _state.update {
                it.copy(
                    isLoading = false,
                    usuario = usuario,
                    username = "@${usuario.nombre.lowercase().replace(" ", "")}",
                    stats = perfilStats,
                    historialSesiones = historial
                )
            }
        } catch (e: Exception) {
            _state.update { it.copy(isLoading = false, error = e.message) }
        }
    }

    private fun formatDuration(seconds: Int): String {
        val mins = seconds / 60
        return "${mins} min"
    }

    fun updatePerfil(nombre: String, objetivo: String?) = viewModelScope.launch {
        // Lógica de actualización...
    }

    fun logout() = viewModelScope.launch {
        logoutUser()
    }
}
