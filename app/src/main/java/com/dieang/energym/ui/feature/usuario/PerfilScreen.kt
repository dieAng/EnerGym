package com.dieang.energym.ui.feature.usuario

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieang.energym.ui.theme.*

@Composable
fun PerfilScreen(
    state: UsuarioState,
    isPublicProfile: Boolean = false,
    onNavigateSettings: () -> Unit,
    onNavigateStats: () -> Unit,
    onLogout: () -> Unit,
    onSesionClick: (String) -> Unit,
    onBack: (() -> Unit)? = null
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(EnerGymDarkBackground)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 0. Botón de Logout o Volver
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isPublicProfile && onBack != null) {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.width(48.dp))
                }

                if (!isPublicProfile) {
                    IconButton(onClick = onLogout) {
                        Icon(
                            imageVector = Icons.Default.Logout,
                            contentDescription = "Cerrar Sesión",
                            tint = EnerGymOrange
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.width(48.dp))
                }
            }

            // 1. Header: Foto y Info Principal
            ProfileHeader(
                nombre = state.usuario?.nombre ?: "Usuario",
                username = state.username
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 2. Stats Rápidas de Impacto
            ImpactStatsRow(stats = state.stats)

            Spacer(modifier = Modifier.height(24.dp))

            // 3. Historial de Sesiones
            HistorialSection(
                sesiones = state.historialSesiones,
                onSesionClick = onSesionClick,
                title = if (isPublicProfile) "Actividad Reciente" else "Tu Historial"
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 4. Opciones de Menú (Solo si es mi perfil)
            if (!isPublicProfile) {
                MenuOptions(
                    onNavigateSettings = onNavigateSettings,
                    onNavigateStats = onNavigateStats
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun ProfileHeader(nombre: String, username: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Brush.linearGradient(PrimaryGradient))
                .padding(3.dp)
                .clip(CircleShape)
                .background(EnerGymDarkBackground),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.size(50.dp),
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = nombre, fontSize = 24.sp, fontWeight = FontWeight.Black, color = Color.White)
        Text(text = username, fontSize = 14.sp, color = EnerGymTextSecondary)
    }
}

@Composable
private fun ImpactStatsRow(stats: PerfilStats) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatCard(value = "${stats.energiaTotalWh}", label = "Wh Totales", icon = Icons.Default.ElectricBolt, color = EnerGymBlue, modifier = Modifier.weight(1f))
        StatCard(value = "${stats.entrenamientos}", label = "Sesiones", icon = Icons.Default.FitnessCenter, color = EnerGymGreen, modifier = Modifier.weight(1f))
        StatCard(value = "${stats.rachaMaxima}", label = "Días Racha", icon = Icons.Default.Whatshot, color = EnerGymOrange, modifier = Modifier.weight(1f))
    }
}

@Composable
private fun StatCard(value: String, label: String, icon: ImageVector, color: Color, modifier: Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        color = Color(0xFF1A1A1A),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(20.dp))
            Spacer(Modifier.height(8.dp))
            Text(text = value, fontSize = 20.sp, fontWeight = FontWeight.Black, color = Color.White)
            Text(text = label, fontSize = 10.sp, color = EnerGymTextSecondary)
        }
    }
}

@Composable
private fun HistorialSection(
    sesiones: List<SesionHistorialUI>,
    onSesionClick: (String) -> Unit,
    title: String = "Historial de Actividad"
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = title, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
        Spacer(modifier = Modifier.height(16.dp))

        if (sesiones.isEmpty()) {
            Text(
                text = "Aún no has completado ninguna sesión.",
                fontSize = 14.sp,
                color = EnerGymTextSecondary,
                modifier = Modifier.padding(vertical = 20.dp)
            )
        } else {
            sesiones.forEach { sesion ->
                SesionItem(sesion, onClick = { onSesionClick(sesion.id) })
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun SesionItem(sesion: SesionHistorialUI, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFF1A1A1A)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = sesion.fecha, fontSize = 12.sp, color = EnerGymTextSecondary)
                Text(text = sesion.rutinaNombre, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Timer, contentDescription = null, tint = EnerGymTextSecondary)
                    Spacer(Modifier.width(4.dp))
                    Text(text = sesion.duracion, fontSize = 12.sp, color = EnerGymTextSecondary)
                    Spacer(Modifier.width(12.dp))
                    Icon(Icons.Default.LocalFireDepartment, contentDescription = null, tint = EnerGymOrange)
                    Spacer(Modifier.width(4.dp))
                    Text(text = "${sesion.calorias} kcal", fontSize = 12.sp, color = EnerGymTextSecondary)
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(text = "+${sesion.energia} Wh", fontSize = 16.sp, fontWeight = FontWeight.Black, color = EnerGymBlue)
                Text(text = "Generado", fontSize = 10.sp, color = EnerGymBlue, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun MenuOptions(onNavigateSettings: () -> Unit, onNavigateStats: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        MenuButton(icon = Icons.Outlined.BarChart, title = "Estadísticas Mensuales", onClick = onNavigateStats)
        MenuButton(icon = Icons.Outlined.Settings, title = "Ajustes de Cuenta", onClick = onNavigateSettings)
    }
}

@Composable
private fun MenuButton(icon: ImageVector, title: String, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFF1A1A1A)
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = Color.White.copy(alpha = 0.7f), modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = title, color = Color.White, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = EnerGymTextSecondary, modifier = Modifier.size(20.dp))
        }
    }
}

@Composable
private fun Icon(imageVector: ImageVector, contentDescription: String?, tint: Color) {
    androidx.compose.material3.Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = Modifier.size(16.dp),
        tint = tint
    )
}
