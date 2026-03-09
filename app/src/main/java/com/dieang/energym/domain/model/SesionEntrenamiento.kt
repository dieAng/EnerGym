package com.dieang.energym.domain.model

import androidx.room.PrimaryKey
import java.util.UUID

data class SesionEntrenamiento(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val usuarioId: UUID,
    val rutinaId: UUID?,
    val fecha: Long
)