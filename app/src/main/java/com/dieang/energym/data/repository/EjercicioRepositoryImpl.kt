package com.dieang.energym.data.repository

import com.dieang.energym.data.local.dao.EjercicioDao
import com.dieang.energym.data.mappers.toEntity
import com.dieang.energym.data.remote.api.EjercicioApi
import com.dieang.energym.domain.model.Ejercicio
import com.dieang.energym.domain.repository.EjercicioRepository
import java.util.UUID

class EjercicioRepositoryImpl(
    private val api: EjercicioApi,
    private val dao: EjercicioDao
) : EjercicioRepository {

    override suspend fun syncEjercicios() {
        val remote = api.getEjercicios()
        dao.insertAll(remote.map { it.toEntity() })
    }

    override suspend fun getEjercicios(): List<Ejercicio> =
        dao.getAll()

    override suspend fun getEjercicio(id: UUID): Ejercicio? =
        dao.getById(id)
}