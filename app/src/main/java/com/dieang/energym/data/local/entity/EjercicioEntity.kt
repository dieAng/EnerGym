package com.dieang.energym.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "ejercicio")
data class EjercicioEntity(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val nombre: String,
    val grupoMuscular: String?,
    val equipo: String?,
    val descripcion: String?,
    val imagenUrl: String?,
    val videoUrl: String?
)