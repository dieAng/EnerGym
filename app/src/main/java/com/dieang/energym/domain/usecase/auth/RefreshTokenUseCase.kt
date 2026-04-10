package com.dieang.energym.domain.usecase.auth

import com.dieang.energym.domain.repository.AuthRepository

class RefreshTokenUseCase(
    private val repo: AuthRepository
) {
    suspend operator fun invoke() = repo.refreshToken()
}
