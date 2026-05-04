package com.dieang.energym.domain.repository

import com.dieang.energym.data.remote.dto.request.PostCreateRequestDto
import com.dieang.energym.domain.model.Post

interface PostRepository {

    suspend fun syncPosts()

    suspend fun getPosts(): List<Post>

    suspend fun createPost(request: PostCreateRequestDto): Post
}
