package com.dieang.energym.data.remote.dto.response

import java.util.UUID

data class EjercicioResponseDto(
    val id: UUID,
    val nombre: String,
    val grupoMuscular: String?,
    val equipo: String?,
    val descripcion: String?,
    val imagenUrl: String?,
    val videoUrl: String?
)