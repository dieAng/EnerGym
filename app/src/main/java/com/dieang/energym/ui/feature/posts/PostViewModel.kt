package com.dieang.energym.ui.feature.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dieang.energym.domain.usecase.posts.CreatePostUseCase
import com.dieang.energym.domain.usecase.posts.GetPostsUseCase
import com.dieang.energym.domain.usecase.posts.LikePostUseCase
import com.dieang.energym.domain.usecase.posts.GetComentariosByPostUseCase
import com.dieang.energym.domain.usecase.posts.AddComentarioToPostUseCase
import com.dieang.energym.domain.usecase.sesiones.GetSesionesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val getPosts: GetPostsUseCase,
    private val createPost: CreatePostUseCase,
    private val likePostUseCase: LikePostUseCase,
    private val getComentariosByPost: GetComentariosByPostUseCase,
    private val addComentarioUseCase: AddComentarioToPostUseCase,
    private val getSesiones: GetSesionesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PostState())
    val state = _state.asStateFlow()

    init {
        loadCommunityContent()
        loadMisSesiones()
    }

    private fun loadMisSesiones() = viewModelScope.launch {
        try {
            val sesiones = getSesiones()
            _state.update { it.copy(misSesionesRecientes = sesiones.take(5)) }
        } catch (e: Exception) {
            // Manejar error silenciosamente
        }
    }

    fun crearPost(usuarioId: UUID, contenido: String, energiaWh: Int? = null) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        // En una implementación real llamaríamos a createPost(usuarioId, contenido, energiaWh)
        _state.update { it.copy(isLoading = false, isPostCreated = true) }
    }

    fun resetPostCreated() {
        _state.update { it.copy(isPostCreated = false) }
    }

    fun loadCommunityContent() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        
        // Datos de prueba para Stories
        val dummyStories = listOf(
            StoryUI("0", "Tú", null, esUsuarioActual = true),
            StoryUI("1", "María", null, vista = false),
            StoryUI("2", "Jorge", null, vista = false),
            StoryUI("3", "Ana", null, vista = true),
            StoryUI("4", "Carlos", null, vista = true),
            StoryUI("5", "Elena", null, vista = true)
        )

        // Datos de prueba para Posts con los formatos del Figma
        val dummyPosts = listOf(
            PostUI(
                id = UUID.randomUUID(),
                usuarioId = UUID.randomUUID(),
                usuarioNombre = "María González",
                usuarioFotoUrl = null,
                tiempoHace = "Hace 2 horas",
                contenido = "¡Nuevo PR en Press Banca! 💪",
                tipoPost = TipoPost.Record(
                    ejercicio = "Press Banca",
                    valor = "85kg",
                    incremento = "+5kg"
                ),
                energiaWh = 45,
                numLikes = 124,
                numComentarios = 18
            ),
            PostUI(
                id = UUID.randomUUID(),
                usuarioId = UUID.randomUUID(),
                usuarioNombre = "Jorge Ramírez",
                usuarioFotoUrl = null,
                tiempoHace = "Hace 5 horas",
                contenido = "Mi nueva receta pre-entreno favorita 🍌",
                tipoPost = TipoPost.Receta(
                    titulo = "Batido Energético de Plátano",
                    calorias = "320 kcal",
                    tiempo = "5 min"
                ),
                numLikes = 89,
                numComentarios = 12
            ),
            PostUI(
                id = UUID.randomUUID(),
                usuarioId = UUID.randomUUID(),
                usuarioNombre = "Ana Martínez",
                usuarioFotoUrl = null,
                tiempoHace = "Hace 8 horas",
                contenido = "¡30 días de racha consecutiva! 🔥",
                tipoPost = TipoPost.Logro(
                    titulo = "Constancia de Hierro",
                    dias = "30 días"
                ),
                energiaWh = 120,
                numLikes = 256,
                numComentarios = 34
            )
        )

        _state.update { 
            it.copy(
                isLoading = false,
                stories = dummyStories,
                posts = dummyPosts
            )
        }
    }

    fun darLike(postId: String) {
        _state.update { currentState ->
            val updatedPosts = currentState.posts.map { post ->
                if (post.id.toString() == postId) {
                    val newIsLiked = !post.isLiked
                    post.copy(
                        isLiked = newIsLiked,
                        numLikes = if (newIsLiked) post.numLikes + 1 else post.numLikes - 1
                    )
                } else post
            }
            currentState.copy(posts = updatedPosts)
        }
    }

    fun loadPostDetalle(id: UUID) { /* Implementar si es necesario */ }
    fun comentar(postId: UUID, usuarioId: UUID, texto: String) { /* Implementar si es necesario */ }
}
