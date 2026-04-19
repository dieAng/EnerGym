package com.dieang.energym.domain.model

import androidx.room.PrimaryKey
import java.util.UUID

data class SesionEntrenamiento(
    val id: UUID = UUID.randomUUID(),
    val usuarioId: UUID,
    val rutinaId: UUID?,
    val fecha: Long,
    val duracionSegundos: Int? = 0,
    val energiaGeneradaWh: Int? = 0,
    val caloriasQuemadas: Int? = 0
)