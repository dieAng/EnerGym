package com.dieang.energym.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "ingrediente",
    foreignKeys = [
        ForeignKey(
            entity = RecetaEntity::class,
            parentColumns = ["id"],
            childColumns = ["recetaId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("recetaId")]
)
data class IngredienteEntity(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val recetaId: UUID,
    val nombre: String,
    val cantidad: String?
)