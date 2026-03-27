package com.dieang.energym.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "usuario")
data class Usuario(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val nombre: String,
    val email: String,
    val passwordHash: String,
    val edad: Int?,
    val peso: Float?,
    val altura: Float?,
    val objetivo: String?,
    val fotoUrl: String?,
    val rol: String = "usuario"
)