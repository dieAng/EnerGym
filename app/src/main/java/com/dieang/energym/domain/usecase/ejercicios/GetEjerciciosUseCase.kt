package com.dieang.energym.domain.usecase.ejercicios

import com.dieang.energym.domain.repository.EjercicioRepository

class GetEjerciciosUseCase(
    private val repo: EjercicioRepository
) {
    suspend operator fun invoke() = repo.getEjercicios()
}
