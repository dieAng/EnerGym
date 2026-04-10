package com.dieang.energym.domain.usecase.recetas

import com.dieang.energym.data.remote.dto.request.RecetaCreateRequestDto
import com.dieang.energym.domain.repository.RecetaRepository
import java.util.UUID

class CreateRecetaUseCase(
    private val repo: RecetaRepository
) {
    suspend operator fun invoke(request: RecetaCreateRequestDto) =
        repo.createReceta(request)
}
