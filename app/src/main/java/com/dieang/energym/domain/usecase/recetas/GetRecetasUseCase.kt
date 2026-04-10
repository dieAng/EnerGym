package com.dieang.energym.domain.usecase.recetas

import com.dieang.energym.domain.repository.RecetaRepository

class GetRecetasUseCase(
    private val repo: RecetaRepository
) {
    suspend operator fun invoke() = repo.getRecetas()
}
