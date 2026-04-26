package com.dieang.energym.domain.usecase.auth

import com.dieang.energym.data.remote.dto.request.UsuarioCreateRequestDto
import com.dieang.energym.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val repo: AuthRepository
) {
    suspend operator fun invoke(request: UsuarioCreateRequestDto) =
        repo.register(request)
}
