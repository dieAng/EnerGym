package com.dieang.energym.domain.repository

import com.dieang.energym.data.local.entity.RecetaEntity
import com.dieang.energym.data.remote.dto.request.RecetaCreateRequestDto
import com.dieang.energym.data.remote.dto.request.RecetaUpdateRequestDto
import com.dieang.energym.domain.model.Receta
import java.util.UUID

interface RecetaRepository {

    suspend fun syncRecetas()

    suspend fun getRecetas(): List<Receta>

    suspend fun getReceta(id: UUID): Receta?

    suspend fun createReceta(request: RecetaCreateRequestDto): RecetaEntity

    suspend fun updateReceta(id: UUID, request: RecetaUpdateRequestDto)

    suspend fun deleteReceta(id: UUID)
}