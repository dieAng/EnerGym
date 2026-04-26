package com.dieang.energym.ui.feature.usuario

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieang.energym.domain.model.Usuario
import com.dieang.energym.ui.theme.ElectricBlue
import com.dieang.energym.ui.theme.NeonGreen
import com.dieang.energym.ui.theme.TextGray
import java.util.UUID

import androidx.compose.ui.tooling.preview.Preview
import com.dieang.energym.ui.theme.EnerGymTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarPerfilScreen(
    usuario: Usuario?,
    onSave: (String, String?) -> Unit,
    onBack: () -> Unit
) {
    var nombre by remember { mutableStateOf(usuario?.nombre ?: "") }
    var objetivo by remember { mutableStateOf(usuario?.objetivo ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Perfil", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Avatar con efecto neón
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF1A1A1A))
                    .border(
                        2.dp,
                        Brush.linearGradient(listOf(NeonGreen, ElectricBlue)),
                        CircleShape
                    )
                    .clickable { /* Simular selección de imagen */ },
                contentAlignment = Alignment.Center
            ) {
                if (usuario?.fotoUrl != null) {
                    // Cargar imagen
                } else {
                    Icon(
                        Icons.Default.CameraAlt,
                        contentDescription = null,
                        tint = NeonGreen,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Text(
                text = "Cambiar foto de avatar",
                style = MaterialTheme.typography.labelMedium,
                color = NeonGreen,
                modifier = Modifier.padding(top = 12.dp)
            )

            Spacer(Modifier.height(40.dp))

            // Campo Nombre
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre de Atleta", color = TextGray) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = NeonGreen,
                    unfocusedBorderColor = TextGray.copy(alpha = 0.3f),
                    focusedLabelColor = NeonGreen
                )
            )

            Spacer(Modifier.height(20.dp))

            // Campo Objetivo
            OutlinedTextField(
                value = objetivo,
                onValueChange = { objetivo = it },
                label = { Text("Tu Objetivo (ej: Ganar masa muscular)", color = TextGray) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                minLines = 3,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ElectricBlue,
                    unfocusedBorderColor = TextGray.copy(alpha = 0.3f),
                    focusedLabelColor = ElectricBlue
                )
            )

            Spacer(Modifier.weight(1f))

            // Botón Guardar con Gradiente
            Button(
                onClick = { onSave(nombre, objetivo) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Brush.linearGradient(listOf(NeonGreen, Color(0xFF99FF00))))
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "GUARDAR CAMBIOS",
                        color = Color.Black,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp
                    )
                }
            }

            Spacer(Modifier.height(20.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EditarPerfilScreenPreview() {
    EnerGymTheme {
        EditarPerfilScreen(
            usuario = Usuario(
                id = UUID.randomUUID(),
                nombre = "Diego Angulo",
                email = "diego@example.com",
                passwordHash = "",
                edad = 28,
                peso = 75f,
                altura = 180f,
                objetivo = "Hipertrofia",
                fotoUrl = null
            ),
            onSave = { _, _ -> },
            onBack = {}
        )
    }
}
