package com.dieang.energym.domain.usecase.usuario

import com.dieang.energym.data.remote.dto.request.UsuarioUpdateRequestDto
import com.dieang.energym.domain.repository.UsuarioRepository
import java.util.UUID

class UpdateUsuarioUseCase(
    private val repo: UsuarioRepository
) {
    suspend operator fun invoke(id: UUID, request: UsuarioUpdateRequestDto) =
        repo.updateUsuario(id, request)
}
