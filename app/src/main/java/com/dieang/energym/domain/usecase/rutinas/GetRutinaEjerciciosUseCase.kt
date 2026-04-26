package com.dieang.energym.domain.usecase.rutinas

import com.dieang.energym.data.local.dao.RutinaEjercicioDao
import com.dieang.energym.domain.model.RutinaEjercicio
import java.util.UUID
import javax.inject.Inject

class GetRutinaEjerciciosUseCase @Inject constructor(
    private val dao: RutinaEjercicioDao
) {
    suspend operator fun invoke(rutinaId: UUID): List<RutinaEjercicio> {
        return dao.getByRutina(rutinaId).map { entity ->
            RutinaEjercicio(
                rutinaId = entity.rutinaId,
                ejercicioId = entity.ejercicioId,
                series = entity.series,
                repeticiones = entity.repeticiones,
                pesoObjetivo = entity.pesoObjetivo,
                descansoSeg = entity.descansoSeg,
                orden = entity.orden
            )
        }
    }
}
