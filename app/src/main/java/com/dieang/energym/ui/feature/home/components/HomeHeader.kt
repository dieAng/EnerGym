package com.dieang.energym.ui.feature.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieang.energym.ui.theme.EnerGymTextPrimary
import com.dieang.energym.ui.theme.EnerGymTextSecondary
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun HomeHeader(nombre: String) {
    val localeEs = Locale.forLanguageTag("es-ES")
    val formatter = DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM yyyy", localeEs)
    val fechaHoy = LocalDate.now().format(formatter)

    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "¡Hola, $nombre!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                color = EnerGymTextPrimary
            )
            Spacer(Modifier.width(8.dp))
            Text(text = "💪", fontSize = 28.sp)
        }
        Text(
            text = fechaHoy.replaceFirstChar { it.uppercase() },
            fontSize = 14.sp,
            color = EnerGymTextSecondary,
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}
