package com.dieang.energym.domain.usecase.rutinas

import com.dieang.energym.domain.repository.RutinaRepository

class GetRutinasUseCase(
    private val repo: RutinaRepository
) {
    suspend operator fun invoke() = repo.getRutinas()
}
