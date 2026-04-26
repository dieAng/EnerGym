package com.dieang.energym.ui.feature.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.dieang.energym.ui.feature.home.components.*
import com.dieang.energym.ui.theme.EnerGymBlue
import com.dieang.energym.ui.theme.EnerGymTextPrimary

@Composable
fun HomeScreen(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
    onNavigateRecetas: () -> Unit,
    onNavigateRutinas: () -> Unit,
    onNavigateSesiones: () -> Unit
) {
    var showPesoDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) { onEvent(HomeEvent.LoadData) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showPesoDialog = true },
                containerColor = EnerGymBlue,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Registrar Peso")
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(16.dp))
            
            HomeHeader(nombre = state.nombreUsuario)

            Spacer(Modifier.height(24.dp))

            HomeSummaryCards(
                racha = state.rachaActual,
                entrenamientosSemana = state.entrenamientosSemana
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Entrenamiento de Hoy",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = EnerGymTextPrimary,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            TodayTrainingCard(
                entrenamiento = state.entrenamientoHoy,
                onStartTraining = { onNavigateSesiones() }
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Objetivo Actual",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = EnerGymTextPrimary,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            GoalProgressCard()

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Progreso Reciente",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = EnerGymTextPrimary,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            WeightChartCard(
                pesoActual = state.pesoReciente,
                historial = state.historialPeso
            )

            Spacer(Modifier.height(24.dp))

            HomeQuickAccess(
                onRecetas = onNavigateRecetas,
                onRutinas = onNavigateRutinas,
                onSesiones = onNavigateSesiones
            )
            
            Spacer(Modifier.height(24.dp))
        }
    }

    if (showPesoDialog) {
        AddPesoDialog(
            onDismiss = { showPesoDialog = false },
            onSubmit = { peso ->
                onEvent(HomeEvent.OnPesoSubmit(peso))
                showPesoDialog = false
            }
        )
    }
}

@Composable
fun AddPesoDialog(onDismiss: () -> Unit, onSubmit: (Float) -> Unit) {
    var pesoText by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Registrar Peso",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = EnerGymTextPrimary
                )
                Spacer(Modifier.height(16.dp))
                OutlinedTextField(
                    value = pesoText,
                    onValueChange = { pesoText = it },
                    label = { Text("Peso en kg") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                Spacer(Modifier.height(24.dp))
                Button(
                    onClick = { pesoText.toFloatOrNull()?.let { onSubmit(it) } },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = EnerGymBlue)
                ) {
                    Text("Guardar Progreso", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
