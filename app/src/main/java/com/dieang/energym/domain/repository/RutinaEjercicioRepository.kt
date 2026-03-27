package com.dieang.energym.domain.repository

import com.dieang.energym.data.remote.dto.request.RutinaEjercicioCreateRequestDto
import com.dieang.energym.domain.model.RutinaEjercicio
import java.util.UUID

interface RutinaEjercicioRepository {

    suspend fun getEjerciciosDeRutina(rutinaId: UUID): List<RutinaEjercicio>

    suspend fun addEjercicioToRutina(rutinaId: UUID, request: RutinaEjercicioCreateRequestDto)

    suspend fun updateRutinaEjercicio(entity: RutinaEjercicio)

    suspend fun removeEjercicioFromRutina(rutinaId: UUID, ejercicioId: UUID)
}