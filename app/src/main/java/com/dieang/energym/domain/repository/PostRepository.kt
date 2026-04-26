package com.dieang.energym.domain.repository

import com.dieang.energym.data.remote.dto.request.ComentarioCreateRequestDto
import com.dieang.energym.data.remote.dto.request.PostCreateRequestDto
import com.dieang.energym.domain.model.Post
import java.util.UUID

interface PostRepository {

    suspend fun syncPosts()

    suspend fun getPosts(): List<Post>

    suspend fun createPost(request: PostCreateRequestDto): Post

    suspend fun likePost(postId: UUID, usuarioId: UUID)

    suspend fun addComentario(postId: UUID, request: ComentarioCreateRequestDto, usuarioId: UUID)
}
