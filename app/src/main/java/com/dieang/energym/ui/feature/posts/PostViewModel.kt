package com.dieang.energym.ui.feature.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dieang.energym.domain.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostViewModel(
    private val repo: PostRepository
) : ViewModel() {

    private val _state = MutableStateFlow(PostState())
    val state = _state.asStateFlow()

    fun loadPosts() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }

        try {
            val posts = repo.getPosts()
            _state.update { it.copy(isLoading = false, posts = posts) }
        } catch (e: Exception) {
            _state.update { it.copy(isLoading = false, error = e.message) }
        }
    }
}
