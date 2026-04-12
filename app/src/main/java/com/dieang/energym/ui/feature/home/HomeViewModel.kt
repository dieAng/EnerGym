package com.dieang.energym.ui.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dieang.energym.domain.usecase.usuario.GetUsuarioActualUseCase
import com.dieang.energym.domain.usecase.sesiones.GetSesionesPendientesUseCase
import com.dieang.energym.domain.usecase.recetas.GetRecetasGuardadasUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getUsuarioActual: GetUsuarioActualUseCase,
    private val getSesionesPendientes: GetSesionesPendientesUseCase,
    private val getRecetasGuardadas: GetRecetasGuardadasUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.LoadData -> loadData()
            else -> Unit
        }
    }

    private fun loadData() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }

        try {
            val usuario = getUsuarioActual()
            val sesiones = getSesionesPendientes()
            val recetas = getRecetasGuardadas()

            _state.update {
                it.copy(
                    isLoading = false,
                    nombreUsuario = usuario.nombre,
                    rutinasPendientes = sesiones.size,
                    recetasGuardadas = recetas.size
                )
            }
        } catch (e: Exception) {
            _state.update { it.copy(isLoading = false, error = e.message) }
        }
    }
}
