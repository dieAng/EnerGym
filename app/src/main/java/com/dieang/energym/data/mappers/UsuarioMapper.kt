package com.dieang.energym.data.mappers

import com.dieang.energym.data.local.entity.UsuarioEntity
import com.dieang.energym.data.remote.dto.request.UsuarioUpdateRequestDto
import com.dieang.energym.data.remote.dto.response.UsuarioResponseDto
import com.dieang.energym.domain.model.Usuario

// Entity → Domain
fun UsuarioEntity.toDomain() = Usuario(
    id = id,
    nombre = nombre,
    email = email,
    passwordHash = passwordHash,
    edad = edad,
    peso = peso,
    altura = altura,
    objetivo = objetivo,
    fotoUrl = fotoUrl,
    rol = rol
)

// Domain → Entity
fun Usuario.toEntity() = UsuarioEntity(
    id = id,
    nombre = nombre,
    email = email,
    passwordHash = passwordHash,
    edad = edad,
    peso = peso,
    altura = altura,
    objetivo = objetivo,
    fotoUrl = fotoUrl,
    rol = rol
)

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