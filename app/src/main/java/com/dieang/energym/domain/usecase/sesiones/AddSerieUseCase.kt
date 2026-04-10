package com.dieang.energym.domain.usecase.sesiones

import com.dieang.energym.data.remote.dto.request.SerieRealizadaCreateRequestDto
import com.dieang.energym.domain.repository.SesionEntrenamientoRepository
import java.util.UUID

class AddSerieUseCase(
    private val repo: SesionEntrenamientoRepository
) {
    suspend operator fun invoke(sesionId: UUID, request: SerieRealizadaCreateRequestDto) =
        repo.addSerie(sesionId, request)
}
