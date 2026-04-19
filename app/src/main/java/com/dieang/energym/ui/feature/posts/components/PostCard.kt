package com.dieang.energym.ui.feature.posts.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.dieang.energym.domain.model.Post
import com.dieang.energym.ui.theme.NeonGreen
import com.dieang.energym.ui.theme.TextGray
import java.text.SimpleDateFormat
import java.util.*

import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.ui.unit.sp
import com.dieang.energym.ui.theme.ElectricBlue
import kotlinx.coroutines.launch

@Composable
fun PostCard(
    post: Post,
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
                        text = "Atleta EnerGym",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = formatPostDate(post.fecha),
                        style = MaterialTheme.typography.labelSmall,
                        color = TextGray
                    )
                }

                // Badge de Energía (NUEVO)
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
                            text = "+${String.format("%.1f", post.energiaGenerada)} kWh",
                            style = MaterialTheme.typography.labelMedium,
                            color = ElectricBlue,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            // Contenido de texto
            post.contenido?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyLarge,
                    lineHeight = 22.sp
                )
            }

            // Imagen (si existe)
            if (!post.imagenUrl.isNullOrEmpty()) {
                Spacer(Modifier.height(12.dp))
                AsyncImage(
                    model = post.imagenUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
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
                    label = if (isLiked) "25" else "24", // Simulado
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
                    modifier = Modifier.graphicsLayer(scaleX = heartScale.value, scaleY = heartScale.value)
                )

                Spacer(Modifier.width(24.dp))

                InteractionButton(
                    icon = Icons.Default.Message,
                    label = "8", // Simulado
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
