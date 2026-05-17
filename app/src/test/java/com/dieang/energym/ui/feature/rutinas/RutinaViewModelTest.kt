package com.dieang.energym.ui.feature.rutinas

import app.cash.turbine.test
import com.dieang.energym.domain.model.Usuario
import com.dieang.energym.domain.usecase.auth.GetLoggedUserUseCase
import com.dieang.energym.domain.usecase.ejercicios.GetEjercicioUseCase
import com.dieang.energym.domain.usecase.rutinas.CreateRutinaUseCase
import com.dieang.energym.domain.usecase.rutinas.GetRutinaEjerciciosUseCase
import com.dieang.energym.domain.usecase.rutinas.GetRutinaUseCase
import com.dieang.energym.domain.usecase.rutinas.GetRutinasUseCase
import com.dieang.energym.domain.usecase.rutinas.GetRutinasFlowUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.UUID

@OptIn(ExperimentalCoroutinesApi::class)
class RutinaViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val getRutinas: GetRutinasUseCase = mockk()
    private val getRutinasFlow: GetRutinasFlowUseCase = mockk()
    private val getRutina: GetRutinaUseCase = mockk()
    private val getRutinaEjercicios: GetRutinaEjerciciosUseCase = mockk()
    private val getEjercicio: GetEjercicioUseCase = mockk()
    private val getLoggedUser: GetLoggedUserUseCase = mockk()
    private val createRutinaUseCase: CreateRutinaUseCase = mockk()

    private lateinit var viewModel: RutinaViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        coEvery { getRutinas() } returns emptyList()
        coEvery { getRutinasFlow() } returns flowOf(emptyList())
        viewModel = RutinaViewModel(getRutinas, getRutinasFlow, getRutina, getRutinaEjercicios, getEjercicio, getLoggedUser, createRutinaUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `createRutina calls usecase and notifies success`() = runTest {
        val userId = UUID.randomUUID()
        val user = Usuario(
            id = userId,
            nombre = "Diego",
            email = "diego@test.com",
            passwordHash = "hash",
            edad = 25,
            peso = 70f,
            altura = 175f,
            objetivo = "Fuerza",
            fotoUrl = null
        )
        coEvery { getLoggedUser() } returns flowOf(user)
        coEvery { createRutinaUseCase(any()) } returns mockk()

        var successCalled = false
        viewModel.createRutina("Nueva Rutina", "Desc", "Avanzado", "Fuerza") {
            successCalled = true
        }

        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { createRutinaUseCase(match { it.nombre == "Nueva Rutina" && it.usuarioId == userId }) }
        assertTrue(successCalled)
    }
}
