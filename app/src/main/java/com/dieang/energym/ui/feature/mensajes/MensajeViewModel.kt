package com.dieang.energym.ui.feature.mensajes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dieang.energym.domain.usecase.mensajes.EnviarMensajeUseCase
import com.dieang.energym.domain.usecase.mensajes.GetConversacionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class MensajeViewModel(
    private val getConversacion: GetConversacionUseCase,
    private val enviarMensaje: EnviarMensajeUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MensajeState())
    val state = _state.asStateFlow()

    fun loadConversacion(u1: UUID, u2: UUID) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }

        try {
            val mensajes = getConversacion(u1, u2)
            _state.update { it.copy(isLoading = false, mensajes = mensajes) }
        } catch (e: Exception) {
            _state.update { it.copy(isLoading = false, error = e.message) }
        }
    }
}

