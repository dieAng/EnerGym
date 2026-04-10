package com.dieang.energym.domain.usecase.usuario

import com.dieang.energym.data.remote.dto.request.UsuarioCreateRequestDto
import com.dieang.energym.domain.repository.UsuarioRepository

class CreateUsuarioUseCase(
    private val repo: UsuarioRepository
) {
    suspend operator fun invoke(request: UsuarioCreateRequestDto) =
        repo.createUsuario(request)
}
