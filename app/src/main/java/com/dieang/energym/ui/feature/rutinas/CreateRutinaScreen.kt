package com.dieang.energym.ui.feature.rutinas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieang.energym.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRutinaScreen(
    state: RutinaState,
    onCreate: (String, String, String, String) -> Unit,
    onBack: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var nivel by remember { mutableStateOf("Intermedio") }
    var objetivo by remember { mutableStateOf("Fuerza") }

    val niveles = listOf("Básico", "Intermedio", "Avanzado")
    val objetivos = listOf("Fuerza", "Hipertrofia", "Resistencia", "Salud")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nueva Rutina", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = EnerGymBackground
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Define tu entrenamiento",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = EnerGymTextPrimary
            )

            // Nombre
            CreateRutinaTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = "Nombre de la rutina",
                icon = Icons.Default.FitnessCenter,
                placeholder = "Ej: Empuje Pesado"
            )

            // Descripción
            CreateRutinaTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = "Descripción",
                icon = Icons.Default.Description,
                placeholder = "Breve detalle del enfoque",
                singleLine = false
            )

            // Nivel (Dropdown simplificado con botones)
            SelectorChipRow(label = "Nivel", options = niveles, selected = nivel, onSelect = { nivel = it }, icon = Icons.Default.TrendingUp)

            // Objetivo
            SelectorChipRow(label = "Objetivo", options = objetivos, selected = objetivo, onSelect = { objetivo = it }, icon = Icons.Default.Flag)

            Spacer(modifier = Modifier.weight(1f))

            if (state.error != null) {
                Text(state.error, color = Color.Red, fontSize = 12.sp)
            }

            Button(
                onClick = { onCreate(nombre, descripcion, nivel, objetivo) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = EnerGymBlue),
                enabled = nombre.isNotBlank() && !state.isLoading
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text("Guardar Rutina", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
fun CreateRutinaTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    placeholder: String,
    singleLine: Boolean = true
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = label, fontWeight = FontWeight.Medium, color = EnerGymTextPrimary, fontSize = 14.sp)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            leadingIcon = { Icon(icon, contentDescription = null, tint = EnerGymBlue) },
            placeholder = { Text(placeholder, color = EnerGymTextSecondary) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = EnerGymBlue,
                unfocusedBorderColor = EnerGymDivider
            ),
            singleLine = singleLine
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SelectorChipRow(
    label: String,
    options: List<String>,
    selected: String,
    onSelect: (String) -> Unit,
    icon: ImageVector
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = EnerGymBlue, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(8.dp))
            Text(text = label, fontWeight = FontWeight.Medium, color = EnerGymTextPrimary, fontSize = 14.sp)
        }
        
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            options.forEach { option ->
                val isSelected = option == selected
                FilterChip(
                    selected = isSelected,
                    onClick = { onSelect(option) },
                    label = { Text(option) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = EnerGymBlue.copy(alpha = 0.1f),
                        selectedLabelColor = EnerGymBlue,
                        selectedLeadingIconColor = EnerGymBlue
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        borderColor = EnerGymDivider,
                        selectedBorderColor = EnerGymBlue,
                        borderWidth = 1.dp,
                        selectedBorderWidth = 1.5.dp,
                        enabled = true,
                        selected = isSelected
                    )
                )
            }
        }
    }
}
