package com.dieang.energym.domain.repository

import com.dieang.energym.data.local.entity.HistorialPesoEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface PesoRepository {
    fun getHistorialPeso(usuarioId: UUID): Flow<List<HistorialPesoEntity>>
    suspend fun registrarPeso(usuarioId: UUID, peso: Float, fecha: Long)
}
