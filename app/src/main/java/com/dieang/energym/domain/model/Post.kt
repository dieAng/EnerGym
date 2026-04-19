package com.dieang.energym.domain.model

import androidx.room.PrimaryKey
import java.util.UUID

data class Post(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val usuarioId: UUID,
    val contenido: String?,
    val imagenUrl: String?,
    val energiaGenerada: Double = 0.0,
    val fecha: Long
)