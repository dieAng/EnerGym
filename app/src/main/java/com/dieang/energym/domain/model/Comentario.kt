package com.dieang.energym.domain.model

import androidx.room.PrimaryKey
import java.util.UUID

data class Comentario(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val postId: UUID,
    val usuarioId: UUID,
    val contenido: String,
    val fecha: Long
)