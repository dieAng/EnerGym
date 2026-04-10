package com.dieang.energym.domain.usecase.usuario

import com.dieang.energym.domain.repository.UsuarioRepository
import java.util.UUID

class GetUsuarioUseCase(
    private val repo: UsuarioRepository
) {
    suspend operator fun invoke(id: UUID) = repo.getUsuario(id)
}
