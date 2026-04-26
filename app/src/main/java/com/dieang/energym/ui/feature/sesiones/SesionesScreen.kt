package com.dieang.energym.ui.feature.sesiones

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieang.energym.ui.theme.*
import java.util.UUID

import androidx.compose.ui.tooling.preview.Preview
import com.dieang.energym.ui.theme.EnerGymTheme

@Composable
fun SesionesScreen(
    state: SesionState,
    onRefresh: () -> Unit,
    onSelect: (UUID) -> Unit,
    onFilterMes: (String?) -> Unit,
    onFilterTipo: (String?) -> Unit
) {
    LaunchedEffect(Unit) { onRefresh() }

    Scaffold(
        containerColor = EnerGymBackground,
        topBar = {
            Column(modifier = Modifier.background(Color.White).padding(bottom = 16.dp)) {
                Text(
                    "Mi Historial", 
                    style = MaterialTheme.typography.headlineMedium, 
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(20.dp)
                )
                
                // Filtros de Mes
                val meses = listOf("Enero", "Febrero", "Marzo", "Abril", "Mayo")
                FilterRow(
                    items = meses,
                    selectedItem = state.filtroMes,
                    onItemSelected = onFilterMes,
                    label = "Mes"
                )
                
                Spacer(Modifier.height(8.dp))
                
                // Filtros de Tipo
                val tipos = listOf("Fuerza", "Cardio", "HIIT", "Yoga")
                FilterRow(
                    items = tipos,
                    selectedItem = state.filtroTipo,
                    onItemSelected = onFilterTipo,
                    label = "Tipo"
                )
            }
        }
    ) { padding ->
        if (state.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = EnerGymBlue)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val filteredSessions = state.sesiones.filter {
                    (state.filtroMes == null || it.mes == state.filtroMes) &&
                    (state.filtroTipo == null || it.tipo == state.filtroTipo)
                }

                if (filteredSessions.isEmpty()) {
                    item {
                        EmptyHistory()
                    }
                }

                items(filteredSessions) { sesion ->
                    SesionHistoryItem(sesion = sesion, onClick = { onSelect(sesion.id) })
                }
            }
        }
    }
}

@Composable
fun FilterRow(
    items: List<String>,
    selectedItem: String?,
    onItemSelected: (String?) -> Unit,
    label: String
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        item {
            FilterChip(
                selected = selectedItem == null,
                onClick = { onItemSelected(null) },
                label = { Text("Todos") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = EnerGymBlue,
                    selectedLabelColor = Color.White
                )
            )
        }
        items(items) { item ->
            FilterChip(
                selected = selectedItem == item,
                onClick = { onItemSelected(item) },
                label = { Text(item) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = EnerGymBlue,
                    selectedLabelColor = Color.White
                )
            )
        }
    }
}

@Composable
fun SesionHistoryItem(sesion: SesionUI, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        if (sesion.tipo == "Fuerza") EnerGymLightBlue else EnerGymLightGreen,
                        RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    if (sesion.tipo == "Fuerza") Icons.Default.FilterList else Icons.Default.ElectricBolt,
                    contentDescription = null,
                    tint = if (sesion.tipo == "Fuerza") EnerGymBlue else EnerGymGreen
                )
            }
            
            Spacer(Modifier.width(16.dp))
            
            Column(Modifier.weight(1f)) {
                Text(sesion.tipo, fontWeight = FontWeight.Bold, color = EnerGymTextPrimary)
                Text(sesion.fecha, fontSize = 12.sp, color = EnerGymTextSecondary)
            }
            
            Column(horizontalAlignment = Alignment.End) {
                Text("${sesion.energiaWh} Wh", fontWeight = FontWeight.Black, color = EnerGymBlue)
                Text("${sesion.series.size} series", fontSize = 12.sp, color = EnerGymTextSecondary)
            }
        }
    }
}

@Composable
fun EmptyHistory() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("No hay sesiones que coincidan", color = EnerGymTextSecondary)
        Text("Prueba con otros filtros", fontSize = 12.sp, color = EnerGymTextSecondary.copy(alpha = 0.7f))
    }
}

@Preview(showBackground = true)
@Composable
private fun SesionesScreenPreview() {
    EnerGymTheme {
        SesionesScreen(
            state = SesionState(
                sesiones = listOf(
                    SesionUI(
                        id = UUID.randomUUID(),
                        fecha = "12 Mayo, 2024",
                        mes = "Mayo",
                        tipo = "Fuerza",
                        energiaWh = 45,
                        series = listOf(SerieUI(1, 10, 50f))
                    ),
                    SesionUI(
                        id = UUID.randomUUID(),
                        fecha = "10 Mayo, 2024",
                        mes = "Mayo",
                        tipo = "Cardio",
                        energiaWh = 120,
                        series = listOf(SerieUI(1, 1, 0f))
                    ),
                    SesionUI(
                        id = UUID.randomUUID(),
                        fecha = "05 Mayo, 2024",
                        mes = "Mayo",
                        tipo = "Fuerza",
                        energiaWh = 38,
                        series = listOf(SerieUI(1, 12, 40f))
                    )
                )
            ),
            onRefresh = {},
            onSelect = {},
            onFilterMes = {},
            onFilterTipo = {}
        )
    }
}
