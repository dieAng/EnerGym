package com.dieang.energym.domain.usecase.sesiones

import com.dieang.energym.data.remote.dto.request.SesionCreateRequestDto
import com.dieang.energym.domain.repository.SesionEntrenamientoRepository

class CreateSesionUseCase(
    private val repo: SesionEntrenamientoRepository
) {
    suspend operator fun invoke(request: SesionCreateRequestDto) =
        repo.createSesion(request)
}
