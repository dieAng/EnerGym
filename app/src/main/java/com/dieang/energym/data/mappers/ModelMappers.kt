package com.dieang.energym.data.mappers

import com.dieang.energym.data.local.entity.ComentarioEntity
import com.dieang.energym.data.local.entity.EjercicioEntity
import com.dieang.energym.data.local.entity.IngredienteEntity
import com.dieang.energym.data.local.entity.MensajeEntity
import com.dieang.energym.data.local.entity.PostEntity
import com.dieang.energym.data.local.entity.RecetaEntity
import com.dieang.energym.data.local.entity.RutinaEntity
import com.dieang.energym.data.local.entity.SesionEntrenamientoEntity
import com.dieang.energym.data.local.entity.UsuarioEntity
import com.dieang.energym.domain.model.Comentario
import com.dieang.energym.domain.model.Ejercicio
import com.dieang.energym.domain.model.Ingrediente
import com.dieang.energym.domain.model.Mensaje
import com.dieang.energym.domain.model.Post
import com.dieang.energym.domain.model.Receta
import com.dieang.energym.domain.model.Rutina
import com.dieang.energym.domain.model.SesionEntrenamiento
import com.dieang.energym.domain.model.Usuario

fun EjercicioEntity.toDomain() =
    Ejercicio(id, nombre, grupoMuscular, equipo, descripcion, imagenUrl, videoUrl)

fun Ejercicio.toEntity() =
    EjercicioEntity(id, nombre, grupoMuscular, equipo, descripcion, imagenUrl, videoUrl)

fun IngredienteEntity.toDomain() = Ingrediente(id, recetaId, nombre, cantidad)
fun Ingrediente.toEntity() = IngredienteEntity(id, recetaId, nombre, cantidad)
fun MensajeEntity.toDomain() = Mensaje(id, emisorId, receptorId, contenido, fecha)
fun Mensaje.toEntity() = MensajeEntity(id, emisorId, receptorId, contenido, fecha)
fun PostEntity.toDomain() = Post(id, usuarioId, contenido, imagenUrl, energiaGenerada, fecha)
fun Post.toEntity() = PostEntity(id, usuarioId, contenido, imagenUrl, energiaGenerada, fecha)
fun RecetaEntity.toDomain() = Receta(
    id,
    usuarioId,
    nombre,
    tiempoPreparacion,
    alergenos,
    imagenUrl,
    descripcion,
    esPredisenada
)

fun Receta.toEntity() = RecetaEntity(
    id,
    usuarioId,
    nombre,
    tiempoPreparacion,
    alergenos,
    imagenUrl,
    descripcion,
    esPredisenada
)

fun RutinaEntity.toDomain() =
    Rutina(id, usuarioId, nombre, descripcion, nivel, objetivo, esPredisenada)

fun Rutina.toEntity() =
    RutinaEntity(id, usuarioId, nombre, descripcion, nivel, objetivo, esPredisenada)

fun UsuarioEntity.toDomain() =
    Usuario(id, nombre, email, passwordHash, edad, peso, altura, objetivo, fotoUrl, rol)

fun Usuario.toEntity() =
    UsuarioEntity(id, nombre, email, passwordHash, edad, peso, altura, objetivo, fotoUrl, rol)

fun ComentarioEntity.toDomain() = Comentario(id, postId, usuarioId, contenido, fecha)
fun Comentario.toEntity() = ComentarioEntity(id, postId, usuarioId, contenido, fecha)
fun SesionEntrenamientoEntity.toDomain() = SesionEntrenamiento(
    id,
    usuarioId,
    rutinaId,
    fecha,
    duracionSegundos,
    energiaGeneradaWh,
    caloriasQuemadas
)

fun SesionEntrenamiento.toEntity() = SesionEntrenamientoEntity(
    id,
    usuarioId,
    rutinaId,
    fecha,
    duracionSegundos,
    energiaGeneradaWh,
    caloriasQuemadas
)
