package com.dieang.energym.domain.repository

import com.dieang.energym.data.remote.dto.request.SerieRealizadaCreateRequestDto
import com.dieang.energym.domain.model.SerieRealizada
import java.util.UUID

interface SerieRealizadaRepository {

    suspend fun getSeriesBySesion(sesionId: UUID): List<SerieRealizada>

    suspend fun createSerie(sesionId: UUID, request: SerieRealizadaCreateRequestDto): SerieRealizada

    suspend fun getVolumenTotal(usuarioId: UUID): Float
}
