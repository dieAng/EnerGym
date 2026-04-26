package com.dieang.energym.ui.feature.posts

import com.dieang.energym.domain.model.Comentario
import com.dieang.energym.domain.model.Post
import com.dieang.energym.domain.model.SesionEntrenamiento
import java.util.UUID

data class PostState(
    val isLoading: Boolean = false,
    val posts: List<PostUI> = emptyList(),
    val stories: List<StoryUI> = emptyList(),
    val misSesionesRecientes: List<SesionEntrenamiento> = emptyList(),
    val postSeleccionado: PostUI? = null,
    val comentarios: List<Comentario> = emptyList(),
    val isPostCreated: Boolean = false,
    val error: String? = null
)

data class PostUI(
    val id: UUID,
    val usuarioId: UUID,
    val usuarioNombre: String,
    val usuarioFotoUrl: String?,
    val tiempoHace: String,
    val contenido: String,
    val tipoPost: TipoPost,
    val energiaWh: Int? = null,
    val stats: String? = null,
    val numLikes: Int = 0,
    val numComentarios: Int = 0,
    val isLiked: Boolean = false
)

sealed class TipoPost {
    object Texto : TipoPost()
    data class Record(val ejercicio: String, val valor: String, val incremento: String) : TipoPost()
    data class Receta(val titulo: String, val calorias: String, val tiempo: String) : TipoPost()
    data class Logro(val titulo: String, val dias: String) : TipoPost()
}

data class StoryUI(
    val id: String,
    val nombre: String,
    val fotoUrl: String?,
    val vista: Boolean = false,
    val esUsuarioActual: Boolean = false
)
