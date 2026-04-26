package com.dieang.energym.ui.feature.recetas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocalFireDepartment
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

import androidx.compose.ui.tooling.preview.Preview
import com.dieang.energym.ui.theme.EnerGymTheme

@Composable
fun RecetasScreen(
    state: RecetaState,
    onNavigateDetail: (String) -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Mis Recetas", "Comunidad")

    Scaffold(
        topBar = {
            Column(modifier = Modifier.background(Color.White)) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Nutrición",
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
            item {
                NutritionSummaryCard(summary = state.resumenHoy)
            }

            item {
                TabSelector(
                    options = tabs,
                    selectedOption = selectedTab,
                    onOptionSelected = { selectedTab = it }
                )
            }

            items(state.recetas) { receta ->
                RecetaListItem(receta = receta, onClick = { onNavigateDetail(receta.id) })
            }

            item {
                OutlinedButton(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, EnerGymDivider),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = EnerGymTextSecondary)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Crear Nueva Receta")
                }
            }
        }
    }
}

@Composable
fun NutritionSummaryCard(summary: NutricionResumen) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = EnerGymLightBlue.copy(alpha = 0.3f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Resumen de Hoy", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = EnerGymTextPrimary)
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                NutritionStat(value = summary.caloriasConsumidas.toString(), label = "Calorías", subLabel = "${summary.caloriasRestantes} restantes", subLabelColor = EnerGymGreen)
                NutritionStat(value = "${summary.proteinaG}g", label = "Proteína", subLabel = summary.estadoProteina, subLabelColor = EnerGymGreen)
                NutritionStat(value = "${summary.carbohidratosG}g", label = "Carbos", subLabel = summary.estadoCarbos, subLabelColor = EnerGymBlue)
            }
        }
    }
}

@Composable
fun NutritionStat(value: String, label: String, subLabel: String, subLabelColor: Color) {
    Column {
        Text(text = value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = EnerGymTextPrimary)
        Text(text = label, fontSize = 12.sp, color = EnerGymTextSecondary)
        Text(text = subLabel, fontSize = 10.sp, color = subLabelColor)
    }
}

@Composable
fun TabSelector(options: List<String>, selectedOption: Int, onOptionSelected: (Int) -> Unit) {
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
                            fontSize = 14.sp,
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
fun RecetaListItem(receta: RecetaUI, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(EnerGymDivider)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = receta.nombre, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = EnerGymTextPrimary)
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocalFireDepartment, contentDescription = null, tint = EnerGymOrange, modifier = Modifier.size(14.dp))
                    Text(text = " ${receta.calorias} kcal  ", fontSize = 12.sp, color = EnerGymTextSecondary)
                    Icon(Icons.Default.AccessTime, contentDescription = null, tint = EnerGymTextSecondary, modifier = Modifier.size(14.dp))
                    Text(text = " ${receta.tiempoMin} min", fontSize = 12.sp, color = EnerGymTextSecondary)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    TagItemSmall(text = "${receta.proteinaG}g proteína")
                    if (receta.categoria.isNotEmpty()) {
                        TagItemSmall(text = receta.categoria)
                    }
                }
            }
            Icon(Icons.AutoMirrored.Filled.ArrowRight, contentDescription = null, tint = EnerGymDivider)
        }
    }
}

@Composable
fun TagItemSmall(text: String) {
    Surface(
        color = EnerGymBackground,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, EnerGymDivider.copy(alpha = 0.5f))
    ) {
        Text(
            text = text,
            fontSize = 10.sp,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            color = EnerGymTextSecondary,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RecetasScreenPreview() {
    EnerGymTheme {
        RecetasScreen(
            state = RecetaState(
                resumenHoy = NutricionResumen(
                    caloriasConsumidas = 1850,
                    caloriasRestantes = 650,
                    proteinaG = 120,
                    carbohidratosG = 210,
                    estadoProteina = "Faltan 30g",
                    estadoCarbos = "¡Casi listo!"
                ),
                recetas = listOf(
                    RecetaUI(
                        id = "1",
                        nombre = "Pollo con Arroz y Brócoli",
                        calorias = 450,
                        tiempoMin = 25,
                        proteinaG = 35,
                        categoria = "Almuerzo"
                    ),
                    RecetaUI(
                        id = "2",
                        nombre = "Batido de Proteína y Avena",
                        calorias = 320,
                        tiempoMin = 5,
                        proteinaG = 25,
                        categoria = "Snack"
                    ),
                    RecetaUI(
                        id = "3",
                        nombre = "Ensalada de Atún",
                        calorias = 280,
                        tiempoMin = 10,
                        proteinaG = 30,
                        categoria = "Cena"
                    )
                )
            ),
            onNavigateDetail = {}
        )
    }
}
