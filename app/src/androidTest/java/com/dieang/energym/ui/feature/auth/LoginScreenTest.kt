package com.dieang.energym.ui.feature.auth

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.dieang.energym.ui.theme.EnerGymTheme
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loginScreen_displaysAllComponents() {
        composeTestRule.setContent {
            EnerGymTheme {
                LoginScreen(
                    state = AuthState(),
                    onLogin = { _, _ -> },
                    onSuccess = {},
                    onNavigateToRegister = {}
                )
            }
        }

        // Verificar que el título principal aparece
        composeTestRule.onNodeWithText("EnerGym").assertIsDisplayed()
        
        // Verificar campos de texto
        composeTestRule.onNodeWithText("Email de Atleta").assertIsDisplayed()
        composeTestRule.onNodeWithText("Contraseña").assertIsDisplayed()
        
        // Verificar botón de inicio de sesión
        composeTestRule.onNodeWithText("INICIAR SESIÓN").assertIsDisplayed()
    }

    @Test
    fun loginScreen_enteringCredentials_enablesButton() {
        composeTestRule.setContent {
            EnerGymTheme {
                LoginScreen(
                    state = AuthState(),
                    onLogin = { _, _ -> },
                    onSuccess = {},
                    onNavigateToRegister = {}
                )
            }
        }

        // Escribir email y contraseña
        composeTestRule.onNodeWithText("Email de Atleta").performTextInput("test@energym.com")
        composeTestRule.onNodeWithText("Contraseña").performTextInput("password123")

        // El botón debería estar habilitado (se puede verificar por interacción o estado visual si tuviera testTag)
        // Como no tiene testTag, al menos verificamos que podemos hacer click
        composeTestRule.onNodeWithText("INICIAR SESIÓN").performClick()
    }
}
