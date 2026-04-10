package com.dieang.energym.domain.usecase.mensajes

import com.dieang.energym.data.remote.dto.request.MensajeCreateRequestDto
import com.dieang.energym.domain.repository.MensajeRepository
import java.util.UUID

class EnviarMensajeUseCase(
    private val repo: MensajeRepository
) {
    suspend operator fun invoke(request: MensajeCreateRequestDto, emisorId: UUID) =
        repo.enviarMensaje(request, emisorId)
}
