package com.dieang.energym.domain.usecase.auth
import com.dieang.energym.domain.repository.AuthRepository


class LogoutUserUseCase(
    private val repo: AuthRepository
) {
    suspend operator fun invoke() = repo.logout()
}
