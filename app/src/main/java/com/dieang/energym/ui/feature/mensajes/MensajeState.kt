package com.dieang.energym.ui.feature.mensajes

import com.dieang.energym.domain.model.Mensaje

data class MensajeState(
    val isLoading: Boolean = false,
    val mensajes: List<Mensaje> = emptyList(),
    val error: String? = null
)
