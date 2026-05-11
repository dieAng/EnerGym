package com.dieang.energym.domain.usecase.posts

import com.dieang.energym.domain.model.Post
import com.dieang.energym.domain.repository.PostRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.UUID

class GetPostsUseCaseTest {

    private val repository: PostRepository = mockk()
    private val getPostsUseCase = GetPostsUseCase(repository)

    @Test
    fun `invoke should return list of posts from repository`() = runBlocking {
        // Given
        val expectedPosts = listOf(
            Post(
                id = UUID.randomUUID(),
                usuarioId = UUID.randomUUID(),
                contenido = "Hoy entrené pierna a tope! ⚡️",
                imagenUrl = null,
                energiaGenerada = 150.5,
                fecha = System.currentTimeMillis()
            )
        )
        coEvery { repository.getPosts() } returns expectedPosts

        // When
        val result = getPostsUseCase()

        // Then
        assertEquals(expectedPosts, result)
    }
}
