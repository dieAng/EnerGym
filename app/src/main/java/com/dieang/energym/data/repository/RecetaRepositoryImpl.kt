package com.dieang.energym.data.repository

import com.dieang.energym.data.local.dao.IngredienteDao
import com.dieang.energym.data.local.dao.RecetaDao
import com.dieang.energym.data.local.entity.RecetaEntity
import com.dieang.energym.data.mappers.toEntity
import com.dieang.energym.data.remote.api.RecetaApi
import com.dieang.energym.data.remote.dto.request.RecetaCreateRequestDto
import com.dieang.energym.data.remote.dto.request.RecetaUpdateRequestDto
import com.dieang.energym.domain.model.Receta
import com.dieang.energym.domain.repository.RecetaRepository
import java.util.UUID

class RecetaRepositoryImpl(
    private val api: RecetaApi,
    private val dao: RecetaDao,
    private val ingredienteDao: IngredienteDao
) : RecetaRepository {

    override suspend fun syncRecetas() {
        val remote = api.getRecetas()

        remote.forEach { receta ->
            dao.insert(receta.toEntity())
            ingredienteDao.insertAll(
                receta.ingredientes.map { it.toEntity(receta.id) }
            )
        }
    }

    override suspend fun getRecetas(): List<Receta> =
        dao.getAll()

    override suspend fun getReceta(id: UUID): Receta? =
        dao.getById(id)

    override suspend fun createReceta(request: RecetaCreateRequestDto): RecetaEntity {
        val response = api.createReceta(request)
        val entity = response.toEntity()

        dao.insert(entity)
        ingredienteDao.insertAll(
            response.ingredientes.map { it.toEntity(entity.id) }
        )

        return entity
    }

    override suspend fun updateReceta(id: UUID, request: RecetaUpdateRequestDto) {
        val response = api.updateReceta(id, request)
        dao.insert(response.toEntity())
    }

    override suspend fun deleteReceta(id: UUID) {
        api.deleteReceta(id)
        dao.delete(id)
    }
}