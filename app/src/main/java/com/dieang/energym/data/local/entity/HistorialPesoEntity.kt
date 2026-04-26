package com.dieang.energym.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "historial_peso",
    foreignKeys = [
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["id"],
            childColumns = ["usuarioId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class HistorialPesoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val usuarioId: UUID,
    val peso: Float,
    val fecha: Long // Timestamp
)
