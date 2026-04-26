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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieang.energym.ui.theme.*

import androidx.compose.ui.tooling.preview.Preview
import com.dieang.energym.domain.model.Usuario
import com.dieang.energym.ui.theme.EnerGymTheme
import java.util.UUID

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
                .background(EnerGymBackground)
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
                            tint = EnerGymTextPrimary
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

            // 3. Logros Desbloqueados
            LogrosSection()

            Spacer(modifier = Modifier.height(24.dp))

            // 4. Opciones de Menú
            MenuOptions(
                onNavigateSettings = onNavigateSettings,
                onNavigateStats = onNavigateStats
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun LogrosSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Logros Desbloqueados",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = EnerGymTextPrimary
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            LogroBadge(icon = "🎯", label = "Primera Rutina", color = EnerGymLightOrange, modifier = Modifier.weight(1f))
            LogroBadge(icon = "🔥", label = "Racha de 7 días", color = EnerGymLightOrange, modifier = Modifier.weight(1f))
            LogroBadge(icon = "💪", label = "Racha de 30 días", color = EnerGymLightOrange, modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            LogroBadge(icon = "👨‍🍳", label = "Maestro Chef", color = EnerGymLightOrange, modifier = Modifier.weight(1f))
            LogroBadge(icon = "🦋", label = "Social Butterfly", color = EnerGymDivider, modifier = Modifier.weight(1f), unlocked = false)
            LogroBadge(icon = "🏆", label = "Racha de 100 días", color = EnerGymDivider, modifier = Modifier.weight(1f), unlocked = false)
        }
    }
}

@Composable
private fun LogroBadge(icon: String, label: String, color: Color, modifier: Modifier, unlocked: Boolean = true) {
    Surface(
        modifier = modifier.height(110.dp),
        shape = RoundedCornerShape(16.dp),
        color = color.copy(alpha = if (unlocked) 1f else 0.5f)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = icon, fontSize = 28.sp, modifier = Modifier.graphicsLayer(alpha = if (unlocked) 1f else 0.3f))
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = label,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = if (unlocked) EnerGymTextPrimary else EnerGymTextSecondary,
                textAlign = TextAlign.Center
            )
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
                .background(EnerGymBackground),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.size(50.dp),
                tint = EnerGymTextSecondary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = nombre, fontSize = 24.sp, fontWeight = FontWeight.Black, color = EnerGymTextPrimary)
        Text(text = username, fontSize = 14.sp, color = EnerGymTextSecondary)
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            LevelTag(text = "Nivel Intermedio")
            LevelTag(text = "Hipertrofia")
        }
    }
}

@Composable
private fun LevelTag(text: String) {
    Surface(
        color = EnerGymDivider,
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(
            text = text,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = EnerGymTextSecondary,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}

@Composable
private fun ImpactStatsRow(stats: PerfilStats) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatCard(value = "${stats.entrenamientos}", label = "Entrenamientos", icon = Icons.Default.CalendarToday, color = EnerGymBlue, modifier = Modifier.weight(1f))
        StatCard(value = "${stats.rachaMaxima}", label = "Racha Máxima", icon = Icons.Default.EmojiEvents, color = EnerGymPurple, modifier = Modifier.weight(1f))
        StatCard(value = "23", label = "PRs Logrados", icon = Icons.Default.TrendingUp, color = EnerGymBlue, modifier = Modifier.weight(1f))
    }
}

@Composable
private fun StatCard(value: String, label: String, icon: ImageVector, color: Color, modifier: Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        color = Color.White,
        border = BorderStroke(1.dp, EnerGymDivider),
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(24.dp))
            Spacer(Modifier.height(8.dp))
            Text(text = value, fontSize = 24.sp, fontWeight = FontWeight.Black, color = EnerGymTextPrimary)
            Text(text = label, fontSize = 11.sp, color = EnerGymTextSecondary)
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
        color = Color.White,
        border = BorderStroke(1.dp, EnerGymDivider)
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = EnerGymTextPrimary, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = title, color = EnerGymTextPrimary, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1f))
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

@Preview(showBackground = true)
@Composable
private fun PerfilScreenPreview() {
    val userId = UUID.randomUUID()
    EnerGymTheme {
        PerfilScreen(
            state = UsuarioState(
                usuario = Usuario(
                    id = userId,
                    nombre = "Diego Angulo",
                    email = "diego@example.com",
                    passwordHash = "",
                    edad = 28,
                    peso = 75f,
                    altura = 180f,
                    objetivo = "Hipertrofia",
                    fotoUrl = null
                ),
                username = "@dieang",
                stats = PerfilStats(
                    entrenamientos = 124,
                    rachaMaxima = 15,
                    prsLogrados = 23,
                    energiaTotalWh = 4500
                )
            ),
            onNavigateSettings = {},
            onNavigateStats = {},
            onLogout = {},
            onSesionClick = {}
        )
    }
}
