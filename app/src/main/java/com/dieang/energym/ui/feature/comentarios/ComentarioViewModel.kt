package com.dieang.energym.ui.feature.comentarios

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dieang.energym.domain.usecase.comentarios.AddComentarioUseCase
import com.dieang.energym.domain.usecase.comentarios.GetComentariosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ComentarioViewModel @Inject constructor(
    private val getComentarios: GetComentariosUseCase,
    private val addComentario: AddComentarioUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ComentarioState())
    val state = _state.asStateFlow()

    fun loadComentarios(postId: UUID) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }

        try {
            val comentarios = getComentarios(postId)
            _state.update { it.copy(isLoading = false, comentarios = comentarios) }
        } catch (e: Exception) {
            _state.update { it.copy(isLoading = false, error = e.message) }
        }
    }
}

