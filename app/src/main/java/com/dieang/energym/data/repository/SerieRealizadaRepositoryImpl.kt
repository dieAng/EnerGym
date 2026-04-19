package com.dieang.energym.data.repository

import com.dieang.energym.data.local.dao.SerieRealizadaDao
import com.dieang.energym.data.remote.dto.request.SerieRealizadaCreateRequestDto
import com.dieang.energym.domain.model.SerieRealizada
import com.dieang.energym.domain.repository.SerieRealizadaRepository
import java.util.UUID
import javax.inject.Inject

class SerieRealizadaRepositoryImpl @Inject constructor(
    private val dao: SerieRealizadaDao
) : SerieRealizadaRepository {

    override suspend fun getSeriesBySesion(sesionId: UUID): List<SerieRealizada> {
        // Mapeo simple para el ejemplo
        return emptyList()
    }

    override suspend fun createSerie(
        sesionId: UUID,
        request: SerieRealizadaCreateRequestDto
    ): SerieRealizada {
        // Implementación de guardado
        TODO("Not yet implemented")
    }

    suspend fun getVolumenTotal(usuarioId: UUID): Float {
        return dao.getVolumenTotalUsuario(usuarioId) ?: 0f
    }
}
