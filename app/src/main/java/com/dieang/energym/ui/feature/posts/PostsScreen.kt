package com.dieang.energym.ui.feature.posts

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import java.util.UUID
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieang.energym.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostsScreen(
    state: PostState,
    onLikePost: (String) -> Unit,
    onCommentPost: (String) -> Unit,
    onUserClick: (UUID) -> Unit,
    onCreatePost: () -> Unit,
    onRefresh: () -> Unit
) {
    Scaffold(
        topBar = {
            Column(modifier = Modifier.background(Color.White)) {
                Text(
                    text = "Comunidad",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = EnerGymTextPrimary,
                    modifier = Modifier.padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 8.dp)
                )
                StoriesRow(stories = state.stories)
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = EnerGymDivider, thickness = 1.dp)
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreatePost,
                containerColor = EnerGymBlue,
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Crear Post")
            }
        },
        containerColor = EnerGymBackground
    ) { paddingValues ->
        // Pull to refresh would normally use a PullToRefreshBox here in M3
        // For simplicity and compatibility, we'll focus on the content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(state.posts) { post ->
                PostItem(
                    post = post, 
                    onLike = { onLikePost(post.id.toString()) },
                    onUserClick = { onUserClick(post.usuarioId) }
                )
            }
        }
    }
}

@Composable
fun StoriesRow(stories: List<StoryUI>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(stories) { story ->
            StoryItem(story = story)
        }
    }
}

@Composable
fun StoryItem(story: StoryUI) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .border(
                    if (story.vista) {
                        BorderStroke(2.dp, EnerGymDivider)
                    } else {
                        BorderStroke(2.dp, Brush.linearGradient(PrimaryGradient))
                    },
                    CircleShape
                )
                .padding(3.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(EnerGymDivider),
                contentAlignment = Alignment.Center
            ) {
                if (story.esUsuarioActual) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = EnerGymBlue)
                } else {
                    Icon(Icons.Default.Person, contentDescription = null, tint = EnerGymTextSecondary)
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = if (story.esUsuarioActual) "Tu historia" else story.nombre,
            fontSize = 11.sp,
            color = EnerGymTextPrimary
        )
    }
}

@Composable
fun PostItem(post: PostUI, onLike: () -> Unit, onUserClick: () -> Unit) {
    val scale = remember { Animatable(1f) }
    val coroutineScope = rememberCoroutineScope()

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header: Usuario y Tiempo
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(EnerGymDivider)
                        .clickable(onClick = onUserClick)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(onClick = onUserClick)
                ) {
                    Text(text = post.usuarioNombre, fontWeight = FontWeight.Bold, color = EnerGymTextPrimary)
                    Text(text = post.tiempoHace, fontSize = 12.sp, color = EnerGymTextSecondary)
                }
                
                // Badge de Energía si existe
                post.energiaWh?.let { wh ->
                    Surface(
                        color = EnerGymBlue.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                Icons.Default.ElectricBolt, 
                                contentDescription = null, 
                                tint = EnerGymBlue,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(Modifier.width(4.dp))
                            Text(
                                text = "$wh Wh", 
                                color = EnerGymBlue, 
                                fontSize = 12.sp, 
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                
                IconButton(onClick = { }) {
                    Icon(Icons.Default.MoreHoriz, contentDescription = null, tint = EnerGymTextSecondary)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text(text = post.contenido, color = EnerGymTextPrimary, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(16.dp))

            // Cuerpo del Post según tipo
            when (val tipo = post.tipoPost) {
                is TipoPost.Record -> RecordCard(tipo)
                is TipoPost.Receta -> RecetaCard(tipo)
                is TipoPost.Logro -> LogroCard(tipo)
                TipoPost.Texto -> {}
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Footer: Acciones
            Row(verticalAlignment = Alignment.CenterVertically) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = {
                            onLike()
                            if (!post.isLiked) {
                                coroutineScope.launch {
                                    scale.animateTo(
                                        targetValue = 1.4f,
                                        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                                    )
                                    scale.animateTo(
                                        targetValue = 1f,
                                        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                                    )
                                }
                            }
                        }, 
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = if (post.isLiked) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = null,
                            tint = if (post.isLiked) Color.Red else EnerGymTextSecondary,
                            modifier = Modifier
                                .graphicsLayer(scaleX = scale.value, scaleY = scale.value)
                                .size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = post.numLikes.toString(), fontSize = 14.sp, color = EnerGymTextSecondary)
                }
                Spacer(modifier = Modifier.width(20.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.ChatBubbleOutline, contentDescription = null, tint = EnerGymTextSecondary, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = post.numComentarios.toString(), fontSize = 14.sp, color = EnerGymTextSecondary)
                }
                Spacer(modifier = Modifier.weight(1f))
                Icon(Icons.Outlined.Share, contentDescription = null, tint = EnerGymTextSecondary, modifier = Modifier.size(20.dp))
            }
        }
    }
}

@Composable
fun RecordCard(record: TipoPost.Record) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = EnerGymLightBlue),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Nuevo Récord Personal", fontSize = 12.sp, color = EnerGymTextSecondary)
                Text(text = record.ejercicio, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = EnerGymTextPrimary)
                Text(text = record.valor, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = EnerGymBlue)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.TrendingUp, contentDescription = null, tint = EnerGymGreen, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = record.incremento, color = EnerGymGreen, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun RecetaCard(receta: TipoPost.Receta) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = EnerGymLightOrange),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = receta.titulo, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = EnerGymTextPrimary)
            Row(modifier = Modifier.padding(top = 4.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocalFireDepartment, contentDescription = null, tint = EnerGymOrange, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = receta.calorias, fontSize = 12.sp, color = EnerGymTextSecondary)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.AccessTime, contentDescription = null, tint = EnerGymTextSecondary, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = receta.tiempo, fontSize = 12.sp, color = EnerGymTextSecondary)
                }
            }
        }
    }
}

@Composable
fun LogroCard(logro: TipoPost.Logro) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = EnerGymLightOrange.copy(alpha = 0.5f)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = EnerGymOrange, modifier = Modifier.size(40.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = logro.titulo, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = EnerGymTextPrimary)
                Text(text = logro.dias, fontSize = 14.sp, color = EnerGymOrange, fontWeight = FontWeight.Bold)
            }
        }
    }
}
