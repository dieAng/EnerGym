package com.dieang.energym.ui.feature.rutinas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ChevronRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieang.energym.ui.theme.*
import java.util.UUID

@Composable
fun RutinasScreen(
    state: RutinaState,
    onNavigateDetail: (UUID) -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Mis Rutinas", "Recomendadas", "Historial")

    Scaffold(
        topBar = {
            Column(modifier = Modifier.background(Color.White)) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Entrenamientos",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = EnerGymTextPrimary
                    )
                    FilledIconButton(
                        onClick = { },
                        colors = IconButtonDefaults.filledIconButtonColors(containerColor = EnerGymBlue),
                        modifier = Modifier.size(32.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(20.dp))
                    }
                }
            }
        },
        containerColor = EnerGymBackground
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Selector de Pestañas (Tabs)
            item {
                RutinaTabSelector(
                    options = tabs,
                    selectedOption = selectedTab,
                    onOptionSelected = { selectedTab = it }
                )
            }

            // 2. Lista de Rutinas
            items(state.rutinas) { rutina ->
                RutinaListItem(rutina = rutina, onClick = { onNavigateDetail(rutina.id) })
            }

            // 3. Botón Crear Nueva Rutina (Estilo Dash)
            item {
                Surface(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, EnerGymDivider),
                    color = Color.Transparent
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, tint = EnerGymTextSecondary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Crear Nueva Rutina", color = EnerGymTextSecondary, fontWeight = FontWeight.Medium)
                    }
                }
            }
        }
    }
}

@Composable
fun RutinaTabSelector(options: List<String>, selectedOption: Int, onOptionSelected: (Int) -> Unit) {
    Surface(
        color = EnerGymDivider.copy(alpha = 0.3f),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            options.forEachIndexed { index, title ->
                val isSelected = selectedOption == index
                Surface(
                    modifier = Modifier.weight(1f).clickable { onOptionSelected(index) },
                    color = if (isSelected) Color.White else Color.Transparent,
                    shape = RoundedCornerShape(10.dp),
                    shadowElevation = if (isSelected) 2.dp else 0.dp
                ) {
                    Box(modifier = Modifier.padding(vertical = 8.dp), contentAlignment = Alignment.Center) {
                        Text(
                            text = title,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) EnerGymTextPrimary else EnerGymTextSecondary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RutinaListItem(rutina: RutinaUI, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            // Icono Play con fondo azul claro
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(EnerGymLightBlue),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = null, tint = EnerGymBlue)
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = rutina.nombre,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = EnerGymTextPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${rutina.numEjercicios} ejercicios  •  Hace ${rutina.ultimaVez}",
                    fontSize = 14.sp,
                    color = EnerGymTextSecondary
                )
            }
            
            Icon(Icons.AutoMirrored.Filled.ChevronRight, contentDescription = null, tint = EnerGymDivider)
        }
    }
}
