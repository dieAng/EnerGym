package com.dieang.energym.domain.repository

import com.dieang.energym.data.local.entity.SesionEntrenamientoEntity
import com.dieang.energym.data.remote.dto.request.SerieRealizadaCreateRequestDto
import com.dieang.energym.data.remote.dto.request.SesionCreateRequestDto
import com.dieang.energym.domain.model.SesionEntrenamiento
import java.util.UUID

interface SesionEntrenamientoRepository {

    suspend fun syncSesiones()

    suspend fun getSesiones(): List<SesionEntrenamiento>

    suspend fun getSesion(id: UUID): SesionEntrenamiento?

    suspend fun createSesion(request: SesionCreateRequestDto): SesionEntrenamientoEntity

    suspend fun addSerie(sesionId: UUID, request: SerieRealizadaCreateRequestDto)
}