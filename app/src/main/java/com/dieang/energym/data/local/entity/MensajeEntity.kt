package com.dieang.energym.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "mensaje",
    foreignKeys = [
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["id"],
            childColumns = ["emisorId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["id"],
            childColumns = ["receptorId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("emisorId"), Index("receptorId")]
)
data class MensajeEntity(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val emisorId: UUID,
    val receptorId: UUID,
    val contenido: String,
    val fecha: Long
)