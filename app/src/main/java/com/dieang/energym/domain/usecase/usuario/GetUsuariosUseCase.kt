package com.dieang.energym.domain.usecase.usuario

import com.dieang.energym.domain.repository.UsuarioRepository

class GetUsuariosUseCase(
    private val repo: UsuarioRepository
) {
    suspend operator fun invoke() = repo.getUsuarios()
}
