package com.dieang.energym.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "ingrediente",
    foreignKeys = [
        ForeignKey(
            entity = Receta::class,
            parentColumns = ["id"],
            childColumns = ["recetaId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("recetaId")]
)
data class Ingrediente(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val recetaId: UUID,
    val nombre: String,
    val cantidad: String?
)