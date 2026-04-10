package com.dieang.energym.domain.usecase.recetas

import com.dieang.energym.domain.repository.RecetaRepository
import java.util.UUID

class GetRecetaUseCase(
    private val repo: RecetaRepository
) {
    suspend operator fun invoke(id: UUID) = repo.getReceta(id)
}
