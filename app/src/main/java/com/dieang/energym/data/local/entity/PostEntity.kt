package com.dieang.energym.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "post",
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
data class PostEntity(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val usuarioId: UUID,
    val contenido: String?,
    val imagenUrl: String?,
    val energiaGenerada: Double = 0.0,
    val fecha: Long,
    val sincronizado: Boolean = false
)
