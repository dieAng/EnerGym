package com.dieang.energym.domain.usecase.ingredientes

import com.dieang.energym.data.remote.dto.request.IngredienteCreateRequestDto
import com.dieang.energym.domain.repository.IngredienteRepository
import java.util.UUID

class CreateIngredienteUseCase(
    private val repo: IngredienteRepository
) {
    suspend operator fun invoke(recetaId: UUID, request: IngredienteCreateRequestDto) =
        repo.createIngrediente(recetaId, request)
}
