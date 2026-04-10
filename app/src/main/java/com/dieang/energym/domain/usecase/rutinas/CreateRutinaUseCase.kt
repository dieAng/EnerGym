package com.dieang.energym.domain.usecase.rutinas

import com.dieang.energym.data.remote.dto.request.RutinaCreateRequestDto
import com.dieang.energym.domain.repository.RutinaRepository

class CreateRutinaUseCase(
    private val repo: RutinaRepository
) {
    suspend operator fun invoke(request: RutinaCreateRequestDto) =
        repo.createRutina(request)
}
