package com.dieang.energym.domain.usecase.sesiones

import com.dieang.energym.data.local.entity.SesionEntrenamientoEntity
import com.dieang.energym.domain.repository.SesionEntrenamientoRepository
import java.util.UUID
import javax.inject.Inject

class SaveSesionUseCase @Inject constructor(
    private val repository: SesionEntrenamientoRepository
) {
    suspend operator fun invoke(
        usuarioId: UUID,
        rutinaId: UUID?,
        duracion: Int,
        energia: Int,
        calorias: Int
    ) {
        val entity = SesionEntrenamientoEntity(
            usuarioId = usuarioId,
            rutinaId = rutinaId,
            fecha = System.currentTimeMillis(),
            duracionSegundos = duracion,
            energiaGeneradaWh = energia,
            caloriasQuemadas = calorias
        )
        repository.saveSesionLocal(entity)
    }
}
