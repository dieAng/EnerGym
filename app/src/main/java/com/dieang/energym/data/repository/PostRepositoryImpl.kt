package com.dieang.energym.data.repository

import com.dieang.energym.data.local.dao.ComentarioDao
import com.dieang.energym.data.local.dao.PostDao
import com.dieang.energym.data.local.entity.PostEntity
import com.dieang.energym.data.mappers.toDomain
import com.dieang.energym.data.mappers.toEntity
import com.dieang.energym.data.remote.api.PostApi
import com.dieang.energym.data.remote.dto.request.ComentarioCreateRequestDto
import com.dieang.energym.data.remote.dto.request.PostCreateRequestDto
import com.dieang.energym.domain.model.Post
import com.dieang.energym.domain.repository.PostRepository
import java.util.UUID

class PostRepositoryImpl(
    private val api: PostApi,
    private val dao: PostDao,
    private val comentarioDao: ComentarioDao
) : PostRepository {

    override suspend fun syncPosts() {
        val remote = api.getPosts()

        remote.forEach { post ->
            dao.insert(post.toEntity())
            comentarioDao.insertAll(
                post.comentarios.map { it.toEntity(post.id) }
            )
        }
    }

    override suspend fun getPosts(): List<Post> =
        dao.getAll().map { it.toDomain() }

    override suspend fun createPost(request: PostCreateRequestDto): Post {
        // 1. Guardar localmente primero (Offline First)
        val entity = PostEntity(
            usuarioId = request.usuarioId,
            contenido = request.contenido,
            imagenUrl = request.imagenUrl,
            energiaGenerada = request.energiaGenerada ?: 0.0,
            fecha = System.currentTimeMillis(),
            sincronizado = false // Marcado para el Worker
        )
        dao.insert(entity)

        // 2. Intentar subir al servidor de inmediato
        try {
            val response = api.createPost(request)
            // Si tiene éxito, marcamos como sincronizado
            dao.insert(entity.copy(sincronizado = true))
        } catch (e: Exception) {
            // Si falla (offline), el SyncWorker se encargará más tarde
            // No lanzamos excepción para que la UI no se bloquee
        }

        return entity.toDomain()
    }

    override suspend fun likePost(postId: UUID, usuarioId: UUID) {
        api.likePost(postId)
    }

    override suspend fun addComentario(postId: UUID, request: ComentarioCreateRequestDto, usuarioId: UUID) {
        api.addComentario(postId, request)
    }
}

