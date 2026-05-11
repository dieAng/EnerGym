package com.dieang.energym.domain.usecase.recetas

import com.dieang.energym.domain.model.Receta
import com.dieang.energym.domain.repository.RecetaRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.UUID

class GetRecetasUseCaseTest {

    private val repository: RecetaRepository = mockk()
    private val getRecetasUseCase = GetRecetasUseCase(repository)

    @Test
    fun `invoke should return list of recetas from repository`() = runBlocking {
        // Given
        val expectedRecetas = listOf(
            Receta(
                id = UUID.randomUUID(),
                usuarioId = UUID.randomUUID(),
                nombre = "Ensalada Cesar",
                tiempoPreparacion = 15,
                alergenos = "Gluten, Lácteos",
                imagenUrl = null,
                descripcion = "Clásica ensalada",
                esPredisenada = true
            )
        )
        coEvery { repository.getRecetas() } returns expectedRecetas

        // When
        val result = getRecetasUseCase()

        // Then
        assertEquals(expectedRecetas, result)
    }
}
