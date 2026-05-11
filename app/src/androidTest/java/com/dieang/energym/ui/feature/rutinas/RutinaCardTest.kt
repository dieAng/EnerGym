package com.dieang.energym.ui.feature.rutinas

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.dieang.energym.domain.model.Rutina
import com.dieang.energym.ui.feature.rutinas.components.RutinaCard
import com.dieang.energym.ui.theme.EnerGymTheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import java.util.UUID

class RutinaCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun rutinaCard_displaysCorrectInfo() {
        val rutina = Rutina(
            id = UUID.randomUUID(),
            usuarioId = UUID.randomUUID(),
            nombre = "Empuje Pesado",
            descripcion = "Enfocado en fuerza",
            nivel = "Avanzado",
            objetivo = "Fuerza máxima",
            esPredisenada = true
        )

        composeTestRule.setContent {
            EnerGymTheme {
                RutinaCard(rutina = rutina, onClick = {})
            }
        }

        composeTestRule.onNodeWithText("Empuje Pesado").assertIsDisplayed()
        composeTestRule.onNodeWithText("Fuerza máxima").assertIsDisplayed()
        composeTestRule.onNodeWithText("Avanzado").assertIsDisplayed()
        composeTestRule.onNodeWithText("OFFICIAL").assertIsDisplayed()
    }

    @Test
    fun rutinaCard_click_triggersCallback() {
        var clicked = false
        val rutina = Rutina(
            id = UUID.randomUUID(),
            usuarioId = UUID.randomUUID(),
            nombre = "Cardio",
            descripcion = null,
            nivel = null,
            objetivo = null
        )

        composeTestRule.setContent {
            EnerGymTheme {
                RutinaCard(rutina = rutina, onClick = { clicked = true })
            }
        }

        composeTestRule.onNodeWithText("Cardio").performClick()
        assertTrue(clicked)
    }
}
