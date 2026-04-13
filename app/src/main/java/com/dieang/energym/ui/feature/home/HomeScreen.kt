package com.dieang.energym.ui.feature.home

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dieang.energym.ui.feature.home.components.HomeHeader
import com.dieang.energym.ui.feature.home.components.HomeStatsCard
import com.dieang.energym.ui.feature.home.components.HomeQuickAccess

@Composable
fun HomeScreen(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
    onNavigateRecetas: () -> Unit,
    onNavigateRutinas: () -> Unit,
    onNavigateSesiones: () -> Unit
) {
    LaunchedEffect(Unit) { onEvent(HomeEvent.LoadData) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        HomeHeader(nombre = state.nombreUsuario)

        Spacer(Modifier.height(24.dp))

        HomeStatsCard(
            caloriasHoy = state.caloriasHoy,
            rutinasPendientes = state.rutinasPendientes,
            recetasGuardadas = state.recetasGuardadas
        )

        Spacer(Modifier.height(24.dp))

        HomeQuickAccess(
            onRecetas = onNavigateRecetas,
            onRutinas = onNavigateRutinas,
            onSesiones = onNavigateSesiones
        )
    }
}
