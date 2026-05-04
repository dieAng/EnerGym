package com.dieang.energym.data.repository

import com.dieang.energym.data.local.dao.IngredienteDao
import com.dieang.energym.data.local.dao.RecetaDao
import com.dieang.energym.data.mappers.toDomain
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
            // Los ingredientes se tendran que obtener en otra llamada
        }
    }

    override suspend fun getRecetas(): List<Receta> =
        dao.getAll().map { it.toDomain() }

    override suspend fun getReceta(id: UUID): Receta? =
        dao.getById(id)?.toDomain()

    override suspend fun createReceta(request: RecetaCreateRequestDto): Receta {
        val response = api.createReceta(request)
        val entity = response.toEntity()

        dao.insert(entity)

        return entity.toDomain()
    }

    override suspend fun updateReceta(
        id: UUID,
        request: RecetaUpdateRequestDto
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteReceta(id: UUID) {
        api.deleteReceta(id)
        dao.getById(id)?.let { dao.delete(it) }
    }
}