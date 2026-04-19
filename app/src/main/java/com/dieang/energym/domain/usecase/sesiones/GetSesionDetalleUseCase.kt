package com.dieang.energym.domain.usecase.sesiones

import com.dieang.energym.domain.model.SesionEntrenamiento
import com.dieang.energym.domain.repository.SesionEntrenamientoRepository
import com.dieang.energym.data.local.dao.SerieRealizadaDao
import com.dieang.energym.data.local.dao.EjercicioDao
import java.util.UUID
import javax.inject.Inject

class GetSesionDetalleUseCase @Inject constructor(
    private val repository: SesionEntrenamientoRepository,
    private val serieDao: SerieRealizadaDao,
    private val ejercicioDao: EjercicioDao
) {
    suspend operator fun invoke(sesionId: UUID): SesionConEjercicios? {
        val sesion = repository.getSesion(sesionId) ?: return null
        val series = serieDao.getBySesion(sesionId)
        
        val ejerciciosAgrupados = series.groupBy { it.ejercicioId }.map { (ejercicioId, seriesList) ->
            val ejercicio = ejercicioDao.getById(ejercicioId)
            EjercicioConSeries(
                nombre = ejercicio?.nombre ?: "Ejercicio Desconocido",
                series = seriesList.map { SerieSimple(it.repeticiones, it.peso) }
            )
        }
        
        return SesionConEjercicios(sesion, ejerciciosAgrupados)
    }
}

data class SesionConEjercicios(
    val sesion: SesionEntrenamiento,
    val ejercicios: List<EjercicioConSeries>
)

data class EjercicioConSeries(
    val nombre: String,
    val series: List<SerieSimple>
)

data class SerieSimple(
    val repeticiones: Int,
    val peso: Float
)
