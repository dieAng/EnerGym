package com.dieang.energym.ui.feature.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieang.energym.ui.theme.ElectricBlue
import com.dieang.energym.ui.theme.NeonGreen
import com.dieang.energym.ui.theme.TextGray

import androidx.compose.ui.tooling.preview.Preview
import com.dieang.energym.ui.theme.EnerGymTheme

@Composable
fun RegisterScreen(
    state: AuthState,
    onRegister: (String, String, String) -> Unit,
    onNavigateLogin: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF0A0A0A)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(28.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Únete a EnerGym",
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.Black,
                    color = NeonGreen
                )
            )
            Text(
                text = "Empieza a convertir sudor en energía",
                style = MaterialTheme.typography.bodyMedium,
                color = TextGray,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Campo Nombre
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre de Atleta", color = TextGray) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                leadingIcon = {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        tint = NeonGreen
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = NeonGreen,
                    unfocusedBorderColor = TextGray.copy(alpha = 0.3f),
                    focusedLabelColor = NeonGreen
                ),
                singleLine = true
            )

            Spacer(Modifier.height(16.dp))

            // Campo Email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", color = TextGray) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                leadingIcon = {
                    Icon(
                        Icons.Default.Email,
                        contentDescription = null,
                        tint = NeonGreen
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = NeonGreen,
                    unfocusedBorderColor = TextGray.copy(alpha = 0.3f),
                    focusedLabelColor = NeonGreen
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true
            )

            Spacer(Modifier.height(16.dp))

            // Campo Contraseña
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña", color = TextGray) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                leadingIcon = {
                    Icon(
                        Icons.Default.Lock,
                        contentDescription = null,
                        tint = NeonGreen
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = null,
                            tint = TextGray
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ElectricBlue,
                    unfocusedBorderColor = TextGray.copy(alpha = 0.3f),
                    focusedLabelColor = ElectricBlue
                ),
                singleLine = true
            )

            Spacer(Modifier.height(40.dp))

            // Botón de Registro
            Button(
                onClick = { onRegister(nombre, email, password) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(),
                enabled = !state.isLoading && nombre.isNotBlank() && email.isNotBlank() && password.length >= 6
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(
                                listOf(NeonGreen, ElectricBlue)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            color = Color.Black,
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Text(
                            "REGISTRARME AHORA",
                            color = Color.Black,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.sp
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // Enlace a Login
            Row {
                Text("¿Ya eres atleta? ", color = TextGray)
                Text(
                    text = "Inicia sesión",
                    modifier = Modifier.clickable { onNavigateLogin() },
                    color = ElectricBlue,
                    fontWeight = FontWeight.Bold
                )
            }

            state.error?.let {
                Spacer(Modifier.height(16.dp))
                Text(it, color = Color.Red, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(Modifier.height(40.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterScreenPreview() {
    EnerGymTheme {
        RegisterScreen(
            state = AuthState(),
            onRegister = { _, _, _ -> },
            onNavigateLogin = {}
        )
    }
}
