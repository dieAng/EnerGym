package com.dieang.energym.domain.repository

import com.dieang.energym.data.local.entity.IngredienteEntity
import com.dieang.energym.data.remote.dto.request.IngredienteCreateRequestDto
import com.dieang.energym.domain.model.Ingrediente
import java.util.UUID

interface IngredienteRepository {

    suspend fun getIngredientesByReceta(recetaId: UUID): List<Ingrediente>

    suspend fun createIngrediente(recetaId: UUID, request: IngredienteCreateRequestDto): IngredienteEntity
}