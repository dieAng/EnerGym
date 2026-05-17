package com.dieang.energym.domain.usecase.rutinas

import com.dieang.energym.domain.repository.RutinaRepository

class GetRutinasFlowUseCase(
    private val repo: RutinaRepository
) {
    operator fun invoke() = repo.getRutinasFlow()
}
