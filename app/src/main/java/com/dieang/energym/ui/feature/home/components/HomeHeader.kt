package com.dieang.energym.ui.feature.home.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun HomeHeader(nombre: String) {
    Text(
        text = "Hola, $nombre",
        style = MaterialTheme.typography.headlineMedium
    )
}
