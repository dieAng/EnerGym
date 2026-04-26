package com.dieang.energym.data.mappers

import com.dieang.energym.data.local.entity.EjercicioEntity
import com.dieang.energym.data.remote.dto.response.EjercicioResponseDto
import com.dieang.energym.domain.model.Ejercicio

// Entity → Domain
fun EjercicioEntity.toDomain() = Ejercicio(
    id = id,
    nombre = nombre,
    grupoMuscular = grupoMuscular,
    equipo = equipo,
    descripcion = descripcion,
    imagenUrl = imagenUrl,
    videoUrl = videoUrl
)

// Domain → Entity
fun Ejercicio.toEntity() = EjercicioEntity(
    id = id,
    nombre = nombre,
    grupoMuscular = grupoMuscular,
    equipo = equipo,
    descripcion = descripcion,
    imagenUrl = imagenUrl,
    videoUrl = videoUrl
)

// Response → Entity
fun EjercicioResponseDto.toEntity() = EjercicioEntity(
    id = id,
    nombre = nombre,
    grupoMuscular = grupoMuscular,
    equipo = equipo,
    descripcion = descripcion,
    imagenUrl = imagenUrl,
    videoUrl = videoUrl
)