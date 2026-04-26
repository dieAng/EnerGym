package com.dieang.energym.data.repository

import com.dieang.energym.data.local.dao.HistorialPesoDao
import com.dieang.energym.data.local.entity.HistorialPesoEntity
import com.dieang.energym.domain.repository.PesoRepository
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class PesoRepositoryImpl @Inject constructor(
    private val pesoDao: HistorialPesoDao
) : PesoRepository {
    override fun getHistorialPeso(usuarioId: UUID): Flow<List<HistorialPesoEntity>> {
        return pesoDao.getHistorialByUsuario(usuarioId)
    }

    override suspend fun registrarPeso(usuarioId: UUID, peso: Float, fecha: Long) {
        pesoDao.insertPeso(HistorialPesoEntity(usuarioId = usuarioId, peso = peso, fecha = fecha))
    }
}


