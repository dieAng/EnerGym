package com.dieang.energym.domain.model

import androidx.room.PrimaryKey
import java.util.UUID

data class SerieRealizada(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val sesionId: UUID,
    val ejercicioId: UUID,
    val repeticiones: Int,
    val peso: Float
)