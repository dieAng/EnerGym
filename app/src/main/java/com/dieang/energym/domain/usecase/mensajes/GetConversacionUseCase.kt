package com.dieang.energym.domain.usecase.mensajes

import com.dieang.energym.domain.repository.MensajeRepository
import java.util.UUID

class GetConversacionUseCase(
    private val repo: MensajeRepository
) {
    suspend operator fun invoke(u1: UUID, u2: UUID) =
        repo.getConversacion(u1, u2)
}
