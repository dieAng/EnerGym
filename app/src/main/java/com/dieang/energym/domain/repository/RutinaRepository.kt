package com.dieang.energym.domain.repository

import com.dieang.energym.data.remote.dto.request.RutinaCreateRequestDto
import com.dieang.energym.domain.model.Rutina
import java.util.UUID

interface RutinaRepository {

    suspend fun syncRutinas()

    suspend fun getRutinas(): List<Rutina>

    suspend fun getRutina(id: UUID): Rutina?

    suspend fun createRutina(request: RutinaCreateRequestDto): Rutina

    suspend fun deleteRutina(id: UUID)
}
