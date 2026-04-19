package com.dieang.energym.ui.feature.usuario

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dieang.energym.domain.usecase.sesiones.GetSesionDetalleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SesionDetailViewModel @Inject constructor(
    private val getSesionDetalle: GetSesionDetalleUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(SesionDetailState())
    val state = _state.asStateFlow()

    private val sesionId: String? = savedStateHandle["id"]

    init {
        loadDetail()
    }

    fun loadDetail() = viewModelScope.launch {
        sesionId?.let { id ->
            _state.update { it.copy(isLoading = true) }
            try {
                val uuid = UUID.fromString(id)
                val result = getSesionDetalle(uuid)
                
                if (result != null) {
                    val dateFormat = SimpleDateFormat("dd MMMM, yyyy - HH:mm", Locale.getDefault())
                    _state.update {
                        it.copy(
                            isLoading = false,
                            fecha = dateFormat.format(Date(result.sesion.fecha)),
                            duracion = formatDuration(result.sesion.duracionSegundos ?: 0),
                            energia = result.sesion.energiaGeneradaWh ?: 0,
                            calorias = result.sesion.caloriasQuemadas ?: 0,
                            ejercicios = result.ejercicios.map { ej ->
                                EjercicioRealizadoUI(
                                    nombre = ej.nombre,
                                    series = ej.series.map { s -> SerieInfo(s.repeticiones, s.peso) }
                                )
                            }
                        )
                    }
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun formatDuration(seconds: Int): String {
        val mins = seconds / 60
        val secs = seconds % 60
        return "%02d:%02d".format(mins, secs)
    }
}
