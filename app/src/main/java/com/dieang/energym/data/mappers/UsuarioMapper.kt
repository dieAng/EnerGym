package com.dieang.energym.data.mappers

import com.dieang.energym.data.local.entity.UsuarioEntity
import com.dieang.energym.data.remote.dto.request.UsuarioUpdateRequestDto
import com.dieang.energym.data.remote.dto.response.UsuarioResponseDto

// Response → Entity
fun UsuarioResponseDto.toEntity() = UsuarioEntity(
    id = id,
    nombre = nombre,
    email = email,
    passwordHash = "",
    edad = edad,
    peso = peso,
    altura = altura,
    objetivo = objetivo,
    fotoUrl = fotoUrl,
    rol = rol
)

// Entity → Request (solo si actualizas usuario)
fun UsuarioEntity.toUpdateRequest() = UsuarioUpdateRequestDto(
    nombre = nombre,
    email = email,
    edad = edad,
    peso = peso,
    altura = altura,
    objetivo = objetivo,
    fotoUrl = fotoUrl
)