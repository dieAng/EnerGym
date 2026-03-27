package com.dieang.energym.domain.repository

import com.dieang.energym.data.remote.dto.request.MensajeCreateRequestDto
import com.dieang.energym.domain.model.Mensaje
import java.util.UUID

interface MensajeRepository {

    suspend fun syncConversacion(u1: UUID, u2: UUID)

    suspend fun getConversacion(u1: UUID, u2: UUID): List<Mensaje>

    suspend fun enviarMensaje(request: MensajeCreateRequestDto, emisorId: UUID): Mensaje
}