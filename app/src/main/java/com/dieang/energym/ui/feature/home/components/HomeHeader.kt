package com.dieang.energym.ui.feature.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieang.energym.ui.theme.EnerGymBlue
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun HomeHeader(nombre: String) {
    val formatter = DateTimeFormatter.ofPattern("d 'de' MMMM", Locale("es", "ES"))
    val fechaHoy = LocalDate.now().format(formatter)

    Column {
        Text(
            text = fechaHoy.uppercase(),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            color = EnerGymBlue
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "¡Hola, $nombre!",
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                color = Color.White
            )
            Spacer(Modifier.width(8.dp))
            Text(text = "⚡️", fontSize = 28.sp)
        }
    }
}
