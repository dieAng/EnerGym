package com.dieang.energym.ui.feature.posts.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieang.energym.ui.feature.posts.PostUI
import com.dieang.energym.ui.feature.posts.TipoPost
import com.dieang.energym.ui.theme.ElectricBlue
import com.dieang.energym.ui.theme.EnerGymBlue
import com.dieang.energym.ui.theme.EnerGymGreen
import com.dieang.energym.ui.theme.EnerGymLightBlue
import com.dieang.energym.ui.theme.EnerGymLightOrange
import com.dieang.energym.ui.theme.EnerGymOrange
import com.dieang.energym.ui.theme.EnerGymTextPrimary
import com.dieang.energym.ui.theme.EnerGymTextSecondary
import com.dieang.energym.ui.theme.NeonGreen
import com.dieang.energym.ui.theme.TextGray
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PostCard(
    post: PostUI,
    isLiked: Boolean = false,
    onLike: () -> Unit,
    onComment: () -> Unit
) {
    val heartScale = remember { Animatable(1f) }
    val coroutineScope = rememberCoroutineScope()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(NeonGreen.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "A",
                        color = NeonGreen,
                        fontWeight = FontWeight.Black,
                        fontSize = 18.sp
                    )
                }

                Spacer(Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = post.usuarioNombre,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = post.tiempoHace,
                        style = MaterialTheme.typography.labelSmall,
                        color = TextGray
                    )
                }

                // Badge de Energía (NUEVO)
                post.energiaWh?.let { wh ->
                    Surface(
                        color = ElectricBlue.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.ElectricBolt,
                                contentDescription = null,
                                tint = ElectricBlue,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(Modifier.width(4.dp))
                            Text(
                                text = "+$wh Wh",
                                style = MaterialTheme.typography.labelMedium,
                                color = ElectricBlue,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            // Contenido de texto
            Text(
                text = post.contenido,
                style = MaterialTheme.typography.bodyLarge,
                lineHeight = 22.sp
            )

            // Imagen (si existe) - Nota: PostUI no tiene imagenUrl actualmente, 
            // pero podríamos añadirlo o ignorarlo por ahora. 
            // En este caso, PostUI tiene tipoPost que puede tener info extra.

            // Cuerpo del Post según tipo (Copiado de PostsScreen para consistencia)
            when (val tipo = post.tipoPost) {
                is TipoPost.Record -> {
                    Spacer(Modifier.height(12.dp))
                    RecordCardDetalle(tipo)
                }

                is TipoPost.Receta -> {
                    Spacer(Modifier.height(12.dp))
                    RecetaCardDetalle(tipo)
                }

                is TipoPost.Logro -> {
                    Spacer(Modifier.height(12.dp))
                    LogroCardDetalle(tipo)
                }

                TipoPost.Texto -> {}
            }

            Spacer(Modifier.height(16.dp))

            // Footer: Botones de interacción
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                InteractionButton(
                    icon = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    label = post.numLikes.toString(),
                    onClick = {
                        onLike()
                        if (!isLiked) {
                            coroutineScope.launch {
                                heartScale.animateTo(
                                    targetValue = 1.4f,
                                    animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                                )
                                heartScale.animateTo(
                                    targetValue = 1f,
                                    animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                                )
                            }
                        }
                    },
                    color = if (isLiked) Color.Red else TextGray,
                    modifier = Modifier.graphicsLayer(
                        scaleX = heartScale.value,
                        scaleY = heartScale.value
                    )
                )

                Spacer(Modifier.width(24.dp))

                InteractionButton(
                    icon = Icons.Default.Message,
                    label = post.numComentarios.toString(),
                    onClick = onComment,
                    color = NeonGreen
                )

                Spacer(Modifier.weight(1f))

                IconButton(onClick = { /* Compartir */ }) {
                    Icon(
                        Icons.Default.Share,
                        contentDescription = null,
                        tint = TextGray,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun InteractionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit,
    color: Color,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Transparent)
    ) {
        IconButton(onClick = onClick, modifier = Modifier.size(32.dp)) {
            Icon(
                icon,
                contentDescription = null,
                tint = color,
                modifier = modifier.size(20.dp)
            )
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = TextGray,
            fontWeight = FontWeight.Medium
        )
    }
}

fun formatPostDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd MMM, HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

@Composable
fun RecordCardDetalle(record: TipoPost.Record) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = EnerGymLightBlue),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Nuevo Récord Personal", fontSize = 12.sp, color = EnerGymTextSecondary)
                Text(
                    text = record.ejercicio,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = EnerGymTextPrimary
                )
                Text(
                    text = record.valor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = EnerGymBlue
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.TrendingUp,
                    contentDescription = null,
                    tint = EnerGymGreen,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = record.incremento, color = EnerGymGreen, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun RecetaCardDetalle(receta: TipoPost.Receta) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = EnerGymLightOrange),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = receta.titulo,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = EnerGymTextPrimary
            )
            Row(
                modifier = Modifier.padding(top = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.LocalFireDepartment,
                        contentDescription = null,
                        tint = EnerGymOrange,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = receta.calorias, fontSize = 12.sp, color = EnerGymTextSecondary)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.AccessTime,
                        contentDescription = null,
                        tint = EnerGymTextSecondary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = receta.tiempo, fontSize = 12.sp, color = EnerGymTextSecondary)
                }
            }
        }
    }
}

@Composable
fun LogroCardDetalle(logro: TipoPost.Logro) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = EnerGymLightOrange.copy(alpha = 0.5f)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Default.EmojiEvents,
                contentDescription = null,
                tint = EnerGymOrange,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = logro.titulo,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = EnerGymTextPrimary
                )
                Text(
                    text = logro.dias,
                    fontSize = 14.sp,
                    color = EnerGymOrange,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
