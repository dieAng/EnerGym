package com.dieang.energym.ui.feature.comentarios

import com.dieang.energym.domain.model.Comentario

data class ComentarioState(
    val isLoading: Boolean = false,
    val comentarios: List<Comentario> = emptyList(),
    val error: String? = null
)
