package com.dieang.energym.domain.usecase.posts

import com.dieang.energym.domain.repository.PostRepository

class GetPostsUseCase(
    private val repo: PostRepository
) {
    suspend operator fun invoke() = repo.getPosts()
}
