package com.dieang.energym.data.repository

import com.dieang.energym.data.local.dao.ComentarioDao
import com.dieang.energym.data.local.dao.PostDao
import com.dieang.energym.data.local.entity.PostEntity
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
        dao.getAll()

    override suspend fun createPost(request: PostCreateRequestDto): PostEntity {
        val response = api.createPost(request)
        val entity = response.toEntity()
        dao.insert(entity)
        return entity
    }

    override suspend fun likePost(postId: UUID, usuarioId: UUID) {
        api.likePost(postId)
    }

    override suspend fun addComentario(postId: UUID, request: ComentarioCreateRequestDto, usuarioId: UUID) {
        api.addComentario(postId, request)
    }
}