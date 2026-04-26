package com.dieang.energym.data.repository

import com.dieang.energym.data.local.dao.RutinaDao
import com.dieang.energym.data.local.dao.RutinaEjercicioDao
import com.dieang.energym.data.mappers.toDomain
import com.dieang.energym.data.mappers.toEntity
import com.dieang.energym.data.remote.api.RutinaApi
import com.dieang.energym.data.remote.dto.request.RutinaCreateRequestDto
import com.dieang.energym.domain.model.Rutina
import com.dieang.energym.domain.repository.RutinaRepository
import java.util.UUID

class RutinaRepositoryImpl(
    private val api: RutinaApi,
    private val dao: RutinaDao,
    private val rutinaEjercicioDao: RutinaEjercicioDao
) : RutinaRepository {

    override suspend fun syncRutinas() {
        val remote = api.getRutinas()

        remote.forEach { rutina ->
            dao.insert(rutina.toEntity())
            rutinaEjercicioDao.insertAll(
                rutina.ejercicios.map { it.toEntity(rutina.id) }
            )
        }
    }

    override suspend fun getRutinas(): List<Rutina> =
        dao.getAll().map { it.toDomain() }

    override suspend fun getRutina(id: UUID): Rutina? =
        dao.getById(id)?.toDomain()

    override suspend fun createRutina(request: RutinaCreateRequestDto): Rutina {
        val response = api.createRutina(request)
        val entity = response.toEntity()

        dao.insert(entity)
        rutinaEjercicioDao.insertAll(
            response.ejercicios.map { it.toEntity(entity.id) }
        )

        return entity.toDomain()
    }

    override suspend fun deleteRutina(id: UUID) {
        api.deleteRutina(id)
        dao.getById(id)?.let { dao.delete(it) }
    }
}

