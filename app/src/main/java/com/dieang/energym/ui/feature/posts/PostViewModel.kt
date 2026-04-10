package com.dieang.energym.ui.feature.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dieang.energym.domain.usecase.posts.AddComentarioToPostUseCase
import com.dieang.energym.domain.usecase.posts.CreatePostUseCase
import com.dieang.energym.domain.usecase.posts.GetPostsUseCase
import com.dieang.energym.domain.usecase.posts.LikePostUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostViewModel(
    private val getPosts: GetPostsUseCase,
    private val createPost: CreatePostUseCase,
    private val likePost: LikePostUseCase,
    private val addComentario: AddComentarioToPostUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PostState())
    val state = _state.asStateFlow()

    fun loadPosts() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }

        try {
            val posts = getPosts()
            _state.update { it.copy(isLoading = false, posts = posts) }
        } catch (e: Exception) {
            _state.update { it.copy(isLoading = false, error = e.message) }
        }
    }
}

