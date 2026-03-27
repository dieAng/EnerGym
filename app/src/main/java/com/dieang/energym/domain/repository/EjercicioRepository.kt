package com.dieang.energym.domain.repository

import com.dieang.energym.domain.model.Ejercicio
import java.util.UUID

interface EjercicioRepository {

    suspend fun syncEjercicios()

    suspend fun getEjercicios(): List<Ejercicio>

    suspend fun getEjercicio(id: UUID): Ejercicio?
}