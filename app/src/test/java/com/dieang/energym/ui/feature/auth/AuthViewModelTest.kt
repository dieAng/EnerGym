package com.dieang.energym.ui.feature.auth

import app.cash.turbine.test
import com.dieang.energym.data.local.entity.UsuarioEntity
import com.dieang.energym.domain.model.Usuario
import com.dieang.energym.domain.usecase.auth.GetLoggedUserUseCase
import com.dieang.energym.domain.usecase.auth.LoginUserUseCase
import com.dieang.energym.domain.usecase.auth.LogoutUserUseCase
import com.dieang.energym.domain.usecase.auth.RegisterUserUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.UUID

@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val loginUser: LoginUserUseCase = mockk()
    private val logoutUser: LogoutUserUseCase = mockk()
    private val getLoggedUser: GetLoggedUserUseCase = mockk()
    private val registerUser: RegisterUserUseCase = mockk()

    private lateinit var viewModel: AuthViewModel
    private val userFlow = MutableStateFlow<Usuario?>(null)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        every { getLoggedUser() } returns userFlow
        viewModel = AuthViewModel(loginUser, logoutUser, getLoggedUser, registerUser)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createDummyUser(id: UUID = UUID.randomUUID(), nombre: String = "Diego", email: String = "diego@test.com") = Usuario(
        id = id,
        nombre = nombre,
        email = email,
        passwordHash = "hash",
        edad = 30,
        peso = 80f,
        altura = 180f,
        objetivo = "Fuerza",
        fotoUrl = null
    )

    private fun createDummyUsuarioEntity(id: UUID = UUID.randomUUID(), nombre: String = "Diego", email: String = "diego@test.com") = UsuarioEntity(
        id = id,
        nombre = nombre,
        email = email,
        passwordHash = "hash",
        edad = 30,
        peso = 80f,
        altura = 180f,
        objetivo = "Fuerza",
        fotoUrl = null
    )

    @Test
    fun `initial state reflects logged user`() = runTest {
        val user = createDummyUser()
        userFlow.value = user
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertTrue(state.isLoggedIn == true)
            assertEquals(user, state.usuario)
        }
    }

    @Test
    fun `login success updates state`() = runTest {
        val email = "test@test.com"
        val password = "password"
        val entity = createDummyUsuarioEntity(email = email)
        coEvery { loginUser(email, password) } returns entity

        viewModel.state.test {
            // Saltamos el estado inicial del init
            awaitItem() 
            
            viewModel.login(email, password)

            // Estado de carga
            val loadingState = awaitItem()
            assertTrue(loadingState.isLoading)
            
            // Success state (isLoggedIn actualizado vía observeUser flow)
            val user = createDummyUser(email = email)
            userFlow.value = user
            
            val successState = awaitItem()
            assertFalse(successState.isLoading)
            assertTrue(successState.isLoggedIn == true)
            assertEquals(user, successState.usuario)
        }
    }

    @Test
    fun `logout updates state`() = runTest {
        coEvery { logoutUser() } returns Unit
        
        viewModel.state.test {
            // Saltamos el estado inicial
            awaitItem()
            
            viewModel.logout()
            
            val state = awaitItem()
            assertFalse(state.isLoggedIn == true)
            assertEquals(null, state.usuario)
            coVerify { logoutUser.invoke() }
        }
    }
}
