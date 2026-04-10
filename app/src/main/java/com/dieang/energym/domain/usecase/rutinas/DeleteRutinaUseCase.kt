package com.dieang.energym.domain.usecase.rutinas

import com.dieang.energym.domain.repository.RutinaRepository
import java.util.UUID

class DeleteRutinaUseCase(
    private val repo: RutinaRepository
) {
    suspend operator fun invoke(id: UUID) = repo.deleteRutina(id)
}
