package com.dieang.energym.domain.model

import androidx.room.PrimaryKey
import java.util.UUID

data class Rutina(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val usuarioId: UUID,
    val nombre: String,
    val descripcion: String?,
    val nivel: String?,
    val objetivo: String?,
    val esPredisenada: Boolean = false
)