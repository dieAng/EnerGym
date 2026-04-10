package com.dieang.energym.domain.usecase.auth

import com.dieang.energym.domain.repository.AuthRepository

class GetLoggedUserUseCase(
    private val repo: AuthRepository
) {
    operator fun invoke() = repo.getLoggedUser()
}
