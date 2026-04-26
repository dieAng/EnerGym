package com.dieang.energym.ui.feature.recetas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecetaDetailScreen(
    recetaId: String,
    state: RecetaState,
    onLoad: (String) -> Unit,
    onBack: () -> Unit
) {
    LaunchedEffect(recetaId) {
        onLoad(recetaId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de Receta", fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = EnerGymBackground
    ) { padding ->
        if (state.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = EnerGymBlue)
            }
        } else {
            state.recetaSeleccionada?.let { receta ->
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(padding)
                ) {
                    // Imagen / Header Visual
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .background(EnerGymDivider)
                        )
                    }

                    // Info Principal
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .padding(20.dp)
                        ) {
                            Text(
                                text = receta.nombre,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = EnerGymTextPrimary
                            )
                            Spacer(Modifier.height(12.dp))
                            
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                InfoTag(icon = Icons.Default.LocalFireDepartment, text = "${receta.calorias} kcal", color = EnerGymOrange)
                                InfoTag(icon = Icons.Default.AccessTime, text = "${receta.tiempoMin} min", color = EnerGymTextSecondary)
                                BadgeInfo(text = "Fácil", color = EnerGymGreen)
                            }
                            
                            Spacer(Modifier.height(20.dp))
                            
                            // Macros
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                MacroItem(value = "${receta.proteinaG}g", label = "Proteína", color = EnerGymGreen)
                                MacroItem(value = "${receta.carbosG}g", label = "Carbos", color = EnerGymBlue)
                                MacroItem(value = "${receta.grasasG}g", label = "Grasas", color = EnerGymOrange)
                            }
                        }
                    }

                    // Ingredientes
                    item {
                        SectionTitle("Ingredientes")
                    }
                    items(receta.ingredientes) { ingrediente ->
                        IngredientItem(ingrediente)
                    }

                    // Pasos
                    item {
                        SectionTitle("Preparación")
                    }
                    receta.pasos.forEachIndexed { index, paso ->
                        item {
                            StepItem(index + 1, paso)
                        }
                    }
                    
                    item { Spacer(Modifier.height(32.dp)) }
                }
            }
        }
    }
}

@Composable
fun BadgeInfo(text: String, color: Color) {
    Surface(
        color = color.copy(alpha = 0.1f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            fontSize = 12.sp,
            color = color,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RecetaDetailScreenPreview() {
    EnerGymTheme {
        RecetaDetailScreen(
            recetaId = "1",
            state = RecetaState(
                recetaSeleccionada = RecetaDetailUI(
                    id = "1",
                    nombre = "Pollo con Arroz y Brócoli",
                    calorias = 450,
                    tiempoMin = 25,
                    proteinaG = 35,
                    grasasG = 10,
                    carbosG = 50,
                    descripcion = "Una comida equilibrada ideal para después de entrenar.",
                    ingredientes = listOf(
                        "200g de pechuga de pollo",
                        "100g de arroz integral",
                        "150g de brócoli al vapor",
                        "1 cucharada de aceite de oliva",
                        "Especias al gusto"
                    ),
                    pasos = listOf(
                        "Cocinar el arroz integral en agua hirviendo durante 20 minutos.",
                        "Cortar el pollo en dados y saltear en una sartén con aceite de oliva.",
                        "Lavar y cocinar el brócoli al vapor durante 5-7 minutos.",
                        "Mezclar todo en un plato y añadir especias."
                    )
                )
            ),
            onLoad = {},
            onBack = {}
        )
    }
}

@Composable
fun InfoTag(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(18.dp))
        Spacer(Modifier.width(4.dp))
        Text(text = text, fontSize = 14.sp, color = EnerGymTextSecondary, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun MacroItem(value: String, label: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = EnerGymTextPrimary)
        Text(text = label, fontSize = 12.sp, color = color, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = EnerGymTextPrimary,
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
    )
}

@Composable
fun IngredientItem(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(EnerGymBlue)
        )
        Spacer(Modifier.width(12.dp))
        Text(text = text, fontSize = 14.sp, color = EnerGymTextSecondary)
    }
}

@Composable
fun StepItem(number: Int, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp)
    ) {
        Text(
            text = "$number.",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = EnerGymBlue,
            modifier = Modifier.width(24.dp)
        )
        Text(
            text = text,
            fontSize = 14.sp,
            color = EnerGymTextSecondary,
            lineHeight = 20.sp
        )
    }
}
