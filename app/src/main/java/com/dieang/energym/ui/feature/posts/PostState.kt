package com.dieang.energym.ui.feature.posts

import com.dieang.energym.domain.model.Post

data class PostState(
    val isLoading: Boolean = false,
    val posts: List<Post> = emptyList(),
    val postSeleccionado: Post? = null,
    val error: String? = null
)
