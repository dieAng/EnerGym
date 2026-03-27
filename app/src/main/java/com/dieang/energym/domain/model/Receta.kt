package com.dieang.energym.domain.model

import androidx.room.PrimaryKey
import java.util.UUID

data class Receta(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val usuarioId: UUID,
    val nombre: String,
    val tiempoPreparacion: Int?,
    val alergenos: String?,
    val imagenUrl: String?,
    val descripcion: String?,
    val esPredisenada: Boolean = false
)