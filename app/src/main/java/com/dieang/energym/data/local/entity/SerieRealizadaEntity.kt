package com.dieang.energym.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "serie_realizada",
    foreignKeys = [
        ForeignKey(
            entity = SesionEntrenamientoEntity::class,
            parentColumns = ["id"],
            childColumns = ["sesionId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = EjercicioEntity::class,
            parentColumns = ["id"],
            childColumns = ["ejercicioId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("sesionId"), Index("ejercicioId")]
)
data class SerieRealizadaEntity(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val sesionId: UUID,
    val ejercicioId: UUID,
    val repeticiones: Int,
    val peso: Float,
    val sincronizado: Boolean = false
)
