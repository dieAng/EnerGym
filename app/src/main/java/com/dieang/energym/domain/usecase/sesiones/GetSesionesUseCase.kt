package com.dieang.energym.domain.usecase.sesiones

import com.dieang.energym.domain.repository.SesionEntrenamientoRepository

class GetSesionesUseCase(
    private val repo: SesionEntrenamientoRepository
) {
    suspend operator fun invoke() = repo.getSesiones()
}
