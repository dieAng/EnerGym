package com.dieang.energym.data.repository

import com.dieang.energym.data.local.dao.SerieRealizadaDao
import com.dieang.energym.data.local.dao.SesionEntrenamientoDao
import com.dieang.energym.data.local.entity.SesionEntrenamientoEntity
import com.dieang.energym.data.mappers.toEntity
import com.dieang.energym.data.remote.api.SesionEntrenamientoApi
import com.dieang.energym.data.remote.dto.request.SerieRealizadaCreateRequestDto
import com.dieang.energym.data.remote.dto.request.SesionCreateRequestDto
import com.dieang.energym.domain.model.SesionEntrenamiento
import com.dieang.energym.domain.repository.SesionEntrenamientoRepository
import java.util.UUID

class SesionEntrenamientoRepositoryImpl(
    private val api: SesionEntrenamientoApi,
    private val dao: SesionEntrenamientoDao,
    private val serieDao: SerieRealizadaDao
) : SesionEntrenamientoRepository {

    override suspend fun syncSesiones() {
        val remote = api.getSesiones()

        remote.forEach { sesion ->
            dao.insert(sesion.toEntity())
            serieDao.insertAll(
                sesion.series.map { it.toEntity(sesion.id) }
            )
        }
    }

    override suspend fun getSesiones(): List<SesionEntrenamiento> =
        dao.getAll()

    override suspend fun getSesion(id: UUID): SesionEntrenamiento? =
        dao.getById(id)

    override suspend fun createSesion(request: SesionCreateRequestDto): SesionEntrenamientoEntity {
        val response = api.createSesion(request)
        val entity = response.toEntity()

        dao.insert(entity)
        return entity
    }

    override suspend fun addSerie(sesionId: UUID, request: SerieRealizadaCreateRequestDto) {
        api.addSerie(sesionId, request)
    }
}