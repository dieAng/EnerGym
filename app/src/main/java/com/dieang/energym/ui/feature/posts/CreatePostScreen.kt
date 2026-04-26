package com.dieang.energym.ui.feature.posts

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieang.energym.domain.model.SesionEntrenamiento
import com.dieang.energym.ui.theme.*
import androidx.compose.ui.tooling.preview.Preview
import com.dieang.energym.ui.theme.EnerGymTheme
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostScreen(
    usuarioId: UUID,
    state: PostState,
    onPostClick: (UUID, String, Int?) -> Unit,
    onBack: () -> Unit,
    onResetCreated: () -> Unit
) {
    var contenido by remember { mutableStateOf("") }
    var sesionSeleccionada by remember { mutableStateOf<SesionEntrenamiento?>(null) }

    LaunchedEffect(state.isPostCreated) {
        if (state.isPostCreated) {
            onResetCreated()
            onBack()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Nueva Publicación", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.Close, contentDescription = "Cerrar")
                    }
                },
                actions = {
                    Button(
                        onClick = { onPostClick(usuarioId, contenido, sesionSeleccionada?.energiaGeneradaWh) },
                        enabled = contenido.isNotBlank() && !state.isLoading,
                        colors = ButtonDefaults.buttonColors(containerColor = EnerGymBlue),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("Publicar", fontWeight = FontWeight.Bold)
                    }
                }
            )
        },
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Header con Avatar
            Row(
                modifier = Modifier.padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(45.dp)
                        .clip(CircleShape)
                        .background(EnerGymDivider)
                )
                Spacer(Modifier.width(12.dp))
                Text(
                    "¿Qué has logrado hoy?",
                    fontSize = 16.sp,
                    color = EnerGymTextSecondary
                )
            }

            // Input de Texto
            TextField(
                value = contenido,
                onValueChange = { contenido = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                placeholder = { Text("Comparte tu energía con la comunidad...") },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            // Selector de Sesiones Recientes
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(EnerGymBackground)
                    .padding(vertical = 20.dp)
            ) {
                Text(
                    text = "Vincular entrenamiento reciente",
                    modifier = Modifier.padding(horizontal = 20.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = EnerGymTextPrimary
                )
                Spacer(Modifier.height(12.dp))
                
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.misSesionesRecientes) { sesion ->
                        SesionChip(
                            sesion = sesion,
                            isSelected = sesionSeleccionada?.id == sesion.id,
                            onClick = {
                                sesionSeleccionada = if (sesionSeleccionada?.id == sesion.id) null else sesion
                                if (sesionSeleccionada != null && contenido.isBlank()) {
                                    contenido = "¡He generado ${sesion.energiaGeneradaWh} Wh en mi sesión de hoy! 💪⚡ #EnerGym"
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SesionChip(
    sesion: SesionEntrenamiento,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val dateStr = SimpleDateFormat("dd MMM", Locale.getDefault()).format(Date(sesion.fecha))
    
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = if (isSelected) EnerGymBlue else Color.White,
        border = if (isSelected) null else BorderStroke(1.dp, EnerGymDivider),
        modifier = Modifier.width(140.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = dateStr,
                fontSize = 10.sp,
                color = if (isSelected) Color.White.copy(alpha = 0.7f) else EnerGymTextSecondary
            )
            Text(
                text = "Sesión de Fuerza",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                maxLines = 1,
                color = if (isSelected) Color.White else EnerGymTextPrimary
            )
            Spacer(Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.ElectricBolt,
                    contentDescription = null,
                    tint = if (isSelected) Color.White else EnerGymBlue,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = "${sesion.energiaGeneradaWh} Wh",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) Color.White else EnerGymBlue
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CreatePostScreenPreview() {
    val userId = UUID.randomUUID()
    EnerGymTheme {
        CreatePostScreen(
            usuarioId = userId,
            state = PostState(
                misSesionesRecientes = listOf(
                    SesionEntrenamiento(id = UUID.randomUUID(), usuarioId = userId, rutinaId = UUID.randomUUID(), fecha = System.currentTimeMillis(), duracionSegundos = 3600, energiaGeneradaWh = 120),
                    SesionEntrenamiento(id = UUID.randomUUID(), usuarioId = userId, rutinaId = UUID.randomUUID(), fecha = System.currentTimeMillis() - 86400000, duracionSegundos = 3000, energiaGeneradaWh = 95)
                )
            ),
            onPostClick = { _, _, _ -> },
            onBack = {},
            onResetCreated = {}
        )
    }
}
