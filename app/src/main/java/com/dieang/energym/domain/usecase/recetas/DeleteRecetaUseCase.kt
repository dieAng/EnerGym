package com.dieang.energym.domain.usecase.recetas

import com.dieang.energym.domain.repository.RecetaRepository
import java.util.UUID

class DeleteRecetaUseCase(
    private val repo: RecetaRepository
) {
    suspend operator fun invoke(id: UUID) = repo.deleteReceta(id)
}

