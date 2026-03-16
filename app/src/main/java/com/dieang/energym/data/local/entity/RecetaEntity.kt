package com.dieang.energym.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "receta",
    foreignKeys = [
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["id"],
            childColumns = ["usuarioId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("usuarioId")]
)
data class RecetaEntity(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val usuarioId: UUID,
    val nombre: String,
    val tiempoPreparacion: Int?,
    val alergenos: String?,
    val imagenUrl: String?,
    val descripcion: String?,
    val esPredisenada: Boolean = false
)