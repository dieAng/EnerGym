package com.dieang.energym.domain.usecase.posts

import com.dieang.energym.data.remote.dto.request.PostCreateRequestDto
import com.dieang.energym.domain.repository.PostRepository

class CreatePostUseCase(
    private val repo: PostRepository
) {
    suspend operator fun invoke(request: PostCreateRequestDto) =
        repo.createPost(request)
}
