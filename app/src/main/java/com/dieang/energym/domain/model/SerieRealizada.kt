package com.dieang.energym.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "serie_realizada",
    foreignKeys = [
        ForeignKey(
            entity = SesionEntrenamiento::class,
            parentColumns = ["id"],
            childColumns = ["sesionId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Ejercicio::class,
            parentColumns = ["id"],
            childColumns = ["ejercicioId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("sesionId"), Index("ejercicioId")]
)
data class SerieRealizada(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val sesionId: UUID,
    val ejercicioId: UUID,
    val repeticiones: Int,
    val peso: Float
)