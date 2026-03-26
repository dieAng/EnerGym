package com.dieang.energym.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import java.util.UUID

@Entity(
    tableName = "rutina_ejercicio",
    primaryKeys = ["rutinaId", "ejercicioId"],
    foreignKeys = [
        ForeignKey(
            entity = RutinaEntity::class,
            parentColumns = ["id"],
            childColumns = ["rutinaId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = EjercicioEntity::class,
            parentColumns = ["id"],
            childColumns = ["ejercicioId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("rutinaId"), Index("ejercicioId")]
)
data class RutinaEjercicioEntity(
    val rutinaId: UUID,
    val ejercicioId: UUID,
    val series: Int,
    val repeticiones: Int,
    val pesoObjetivo: Float?,
    val descansoSeg: Int?,
    val orden: Int
)