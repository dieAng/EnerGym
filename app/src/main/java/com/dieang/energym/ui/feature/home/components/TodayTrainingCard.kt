package com.dieang.energym.ui.feature.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieang.energym.ui.feature.home.EntrenamientoHoy
import com.dieang.energym.ui.theme.*

@Composable
fun TodayTrainingCard(
    entrenamiento: EntrenamientoHoy?,
    onStartTraining: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Entrenamiento de Hoy",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = EnerGymTextPrimary
        )
        Spacer(modifier = Modifier.height(12.dp))
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(EnerGymLightBlue, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.TrackChanges,
                            contentDescription = null,
                            tint = EnerGymBlue
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = entrenamiento?.nombre ?: "Sin entrenamiento",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = EnerGymTextPrimary
                        )
                        Text(
                            text = "${entrenamiento?.numEjercicios ?: 0} ejercicios • ${entrenamiento?.duracionMin ?: 0} min aprox.",
                            fontSize = 14.sp,
                            color = EnerGymTextSecondary
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    entrenamiento?.tags?.take(2)?.forEach { tag ->
                        TagItem(text = tag)
                    }
                    if ((entrenamiento?.tags?.size ?: 0) > 2) {
                        TagItem(text = "+${entrenamiento!!.tags.size - 2} más")
                    }
                }
                
                Spacer(modifier = Modifier.height(20.dp))
                
                Button(
                    onClick = onStartTraining,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = EnerGymBlue)
                ) {
                    Text(
                        text = "Iniciar Entrenamiento",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TagItem(text: String) {
    Surface(
        color = EnerGymLightBlue,
        shape = CircleShape
    ) {
        Text(
            text = text,
            color = EnerGymBlue,
            fontSize = 12.sp,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            fontWeight = FontWeight.Medium
        )
    }
}
