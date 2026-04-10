package com.dieang.energym.domain.usecase.rutinas

import com.dieang.energym.domain.repository.RutinaRepository
import java.util.UUID

class GetRutinaUseCase(
    private val repo: RutinaRepository
) {
    suspend operator fun invoke(id: UUID) = repo.getRutina(id)
}
