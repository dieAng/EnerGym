package com.dieang.energym.ui.feature.usuario

import com.dieang.energym.domain.model.Usuario

data class UsuarioState(
    val isLoading: Boolean = false,
    val usuarios: List<Usuario> = emptyList(),
    val usuario: Usuario? = null,
    val error: String? = null
)
