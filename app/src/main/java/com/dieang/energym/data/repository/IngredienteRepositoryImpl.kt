package com.dieang.energym.data.repository

import com.dieang.energym.data.local.dao.IngredienteDao
import com.dieang.energym.data.local.entity.IngredienteEntity
import com.dieang.energym.data.mappers.toEntity
import com.dieang.energym.data.remote.api.IngredienteApi
import com.dieang.energym.data.remote.dto.request.IngredienteCreateRequestDto
import com.dieang.energym.domain.model.Ingrediente
import com.dieang.energym.domain.repository.IngredienteRepository
import java.util.UUID

class IngredienteRepositoryImpl(
    private val api: IngredienteApi,
    private val dao: IngredienteDao
) : IngredienteRepository {

    override suspend fun getIngredientesByReceta(recetaId: UUID): List<Ingrediente> =
        dao.getByReceta(recetaId)

    override suspend fun createIngrediente(recetaId: UUID, request: IngredienteCreateRequestDto): IngredienteEntity {
        val response = api.createIngrediente(request)
        val entity = response.toEntity(recetaId)
        dao.insert(entity)
        return entity
    }
}