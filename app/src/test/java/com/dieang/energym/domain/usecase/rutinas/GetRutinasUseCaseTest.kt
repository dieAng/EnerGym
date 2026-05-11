package com.dieang.energym.domain.usecase.rutinas

import com.dieang.energym.domain.model.Rutina
import com.dieang.energym.domain.repository.RutinaRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.UUID

class GetRutinasUseCaseTest {

    private val repository: RutinaRepository = mockk()
    private val getRutinasUseCase = GetRutinasUseCase(repository)

    @Test
    fun `invoke should return list of rutinas from repository`() = runBlocking {
        // Given
        val expectedRutinas = listOf(
            Rutina(
                id = UUID.randomUUID(),
                usuarioId = UUID.randomUUID(),
                nombre = "Rutina 1",
                descripcion = "Desc 1",
                nivel = "Principiante",
                objetivo = "Fuerza"
            ),
            Rutina(
                id = UUID.randomUUID(),
                usuarioId = UUID.randomUUID(),
                nombre = "Rutina 2",
                descripcion = "Desc 2",
                nivel = "Avanzado",
                objetivo = "Hipertrofia"
            )
        )
        coEvery { repository.getRutinas() } returns expectedRutinas

        // When
        val result = getRutinasUseCase()

        // Then
        assertEquals(expectedRutinas, result)
    }
}
