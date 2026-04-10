package com.dieang.energym.domain.usecase.recetas

import com.dieang.energym.data.remote.dto.request.RecetaUpdateRequestDto
import com.dieang.energym.domain.repository.RecetaRepository
import java.util.UUID

class UpdateRecetaUseCase(
    private val repo: RecetaRepository
) {
    suspend operator fun invoke(id: UUID, request: RecetaUpdateRequestDto) =
        repo.updateReceta(id, request)
}
