package com.dieang.energym.domain.usecase.ingredientes

import com.dieang.energym.domain.repository.IngredienteRepository
import java.util.UUID

class GetIngredientesByRecetaUseCase(
    private val repo: IngredienteRepository
) {
    suspend operator fun invoke(recetaId: UUID) =
        repo.getIngredientesByReceta(recetaId)
}
