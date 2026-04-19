package com.dieang.energym.domain.usecase.auth

import com.dieang.energym.domain.repository.AuthRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class IsUserLoggedInUseCase @Inject constructor(
    private val repo: AuthRepository
) {
    suspend operator fun invoke(): Boolean = repo.isLoggedIn().first()
}
