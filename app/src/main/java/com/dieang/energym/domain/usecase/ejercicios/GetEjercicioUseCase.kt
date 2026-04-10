package com.dieang.energym.domain.usecase.ejercicios

import com.dieang.energym.domain.repository.EjercicioRepository
import java.util.UUID

class GetEjercicioUseCase(
    private val repo: EjercicioRepository
) {
    suspend operator fun invoke(id: UUID) = repo.getEjercicio(id)
}
