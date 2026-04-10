package com.dieang.energym.domain.usecase.auth

import com.dieang.energym.domain.repository.AuthRepository

class LoginUserUseCase(
    private val repo: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String) =
        repo.login(email, password)
}
