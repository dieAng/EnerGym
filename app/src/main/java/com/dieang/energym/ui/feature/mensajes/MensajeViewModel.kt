package com.dieang.energym.ui.feature.mensajes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dieang.energym.domain.model.Mensaje
import com.dieang.energym.domain.usecase.mensajes.GetConversacionUseCase
import com.dieang.energym.domain.usecase.auth.GetLoggedUserUseCase
import com.dieang.energym.domain.usecase.usuario.GetUsuariosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class MensajeViewModel @Inject constructor(
    private val getConversacion: GetConversacionUseCase,
    private val getLoggedUser: GetLoggedUserUseCase,
    private val getUsuarios: GetUsuariosUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MensajeState())
    val state = _state.asStateFlow()

    fun loadChats() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        getLoggedUser().collectLatest { usuario ->
            if (usuario != null) {
                // Simulación de lista de chats (en producción vendría del Repo con GROUP BY emisor/receptor)
                val todosLosUsuarios = getUsuarios()
                val previews = todosLosUsuarios
                    .filter { it.id != usuario.id }
                    .map { u ->
                        ChatPreview(
                            contactoId = u.id,
                            nombreContacto = u.nombre ?: "Atleta",
                            ultimoMensaje = "¡Buen entrenamiento hoy!",
                            fecha = System.currentTimeMillis()
                        )
                    }
                _state.update { it.copy(isLoading = false, chats = previews) }
            }
        }
    }

    fun loadConversacion(contactoId: UUID) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        getLoggedUser().collectLatest { usuario ->
            if (usuario != null) {
                try {
                    val mensajes = getConversacion(usuario.id, contactoId)
                    val contacto = getUsuarios().find { it.id == contactoId }
                    _state.update {
                        it.copy(
                            isLoading = false,
                            mensajesActuales = mensajes,
                            contactoActual = contacto
                        )
                    }
                } catch (e: Exception) {
                    _state.update { it.copy(isLoading = false, error = e.message) }
                }
            }
        }
    }

    fun enviarMensaje(contactoId: UUID, contenido: String) = viewModelScope.launch {
        // Lógica para llamar al UseCase de enviar mensaje
        // Por ahora simulamos la actualización local
        val usuario = _state.value.contactoActual // Mock o del State
        // repo.enviarMensaje(...)
        loadConversacion(contactoId)
    }
}
