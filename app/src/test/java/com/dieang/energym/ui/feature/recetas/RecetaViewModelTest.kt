package com.dieang.energym.ui.feature.recetas

import app.cash.turbine.test
import com.dieang.energym.domain.usecase.ingredientes.GetIngredientesByRecetaUseCase
import com.dieang.energym.domain.usecase.recetas.GetRecetaUseCase
import com.dieang.energym.domain.usecase.recetas.GetRecetasUseCase
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RecetaViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val getRecetas: GetRecetasUseCase = mockk()
    private val getReceta: GetRecetaUseCase = mockk()
    private val getIngredientesByReceta: GetIngredientesByRecetaUseCase = mockk()

    private lateinit var viewModel: RecetaViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = RecetaViewModel(getRecetas, getReceta, getIngredientesByReceta)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should load nutrition data`() = runTest {
        viewModel.state.test {
            // El primer estado puede ser el inicial o el cargado dependiendo de cómo se ejecute el init
            // En el ViewModel actual, init llama a loadNutritionData() que hace update
            val state = awaitItem()
            assertFalse(state.isLoading)
            assertNotNull(state.resumenHoy)
            assertFalse(state.recetas.isEmpty())
        }
    }

    @Test
    fun `loadRecetaDetalle should update state with selected recipe`() = runTest {
        val recetaId = "1"
        
        viewModel.state.test {
            // Saltamos el estado inicial (que se emite por el init/loadNutritionData)
            awaitItem() 
            
            viewModel.loadRecetaDetalle(recetaId)
            
            // Esperamos al estado de carga
            val loadingState = awaitItem()
            assertTrue(loadingState.isLoading)
            
            // Esperamos al estado final con la receta seleccionada
            val finalState = awaitItem()
            assertEquals(recetaId, finalState.recetaSeleccionada?.id)
            assertFalse(finalState.isLoading)
        }
    }
}
