package com.dieang.energym.domain.model

import androidx.room.PrimaryKey
import java.util.UUID

data class Mensaje(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val emisorId: UUID,
    val receptorId: UUID,
    val contenido: String,
    val fecha: Long
)