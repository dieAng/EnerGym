package com.dieang.energym.domain.model

import androidx.room.PrimaryKey
import java.util.UUID

data class Ingrediente(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val recetaId: UUID,
    val nombre: String,
    val cantidad: String?
)