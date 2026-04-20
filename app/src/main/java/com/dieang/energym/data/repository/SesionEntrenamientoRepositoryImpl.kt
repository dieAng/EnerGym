package com.dieang.energym.data.repository

import com.dieang.energym.data.local.dao.SerieRealizadaDao
import com.dieang.energym.data.local.dao.SesionEntrenamientoDao
import com.dieang.energym.data.local.entity.SesionEntrenamientoEntity
import com.dieang.energym.data.mappers.toDomain
import com.dieang.energym.data.mappers.toEntity
import com.dieang.energym.data.remote.api.SesionEntrenamientoApi
import com.dieang.energym.data.remote.dto.request.SerieRealizadaCreateRequestDto
import com.dieang.energym.data.remote.dto.request.SesionCreateRequestDto
import com.dieang.energym.domain.model.SesionEntrenamiento
import com.dieang.energym.domain.repository.SesionEntrenamientoRepository
import java.util.UUID

class SesionEntrenamientoRepositoryImpl(
    private val api: SesionEntrenamientoApi,
    private val dao: SesionEntrenamientoDao,
    private val serieDao: SerieRealizadaDao
) : SesionEntrenamientoRepository {

    override suspend fun syncSesiones() {
        val remote = api.getSesiones()

        remote.forEach { sesion ->
            dao.insert(sesion.toEntity())
            serieDao.insertAll(
                sesion.series.map { it.toEntity(sesion.id) }
            )
        }
    }

    override suspend fun getSesiones(): List<SesionEntrenamiento> =
        dao.getAll().map { it.toDomain() }.map { it.toDomain() }

    override suspend fun getSesion(id: UUID): SesionEntrenamiento? =
        dao.getById(id)?.toDomain()?.toDomain()

    override suspend fun createSesion(request: SesionCreateRequestDto): SesionEntrenamientoEntity {
        // 1. Crear entidad local (Offline First)
        val entity = SesionEntrenamientoEntity(
            usuarioId = request.usuarioId,
            rutinaId = request.rutinaId,
            fecha = System.currentTimeMillis(),
            duracionSegundos = 0,
            energiaGeneradaWh = 0,
            caloriasQuemadas = 0,
            sincronizado = false
        )
        dao.insert(entity)

        // 2. Intentar subir al servidor
        try {
            api.createSesion(request)
            dao.insert(entity.copy(sincronizado = true))
        } catch (e: Exception) {
            // Se sincronizará luego por el SyncWorker
        }

        return entity.toDomain()
    }

    override suspend fun saveSesionLocal(sesion: SesionEntrenamientoEntity) {
        // Actualizamos localmente. Si ya estaba sincronizado pero cambió (ej: terminó), 
        // lo marcamos como NO sincronizado para que el Worker suba la versión final.
        dao.insert(sesion.copy(sincronizado = false))
        
        // Intentar actualizar en API si hay red
        try {
            // api.updateSesion(sesion.id, sesion.toRequest()) // Suponiendo que existe
        } catch (e: Exception) {}
    }

    override suspend fun addSerie(sesionId: UUID, request: SerieRealizadaCreateRequestDto) {
        api.addSerie(sesionId, request)
    }
}

