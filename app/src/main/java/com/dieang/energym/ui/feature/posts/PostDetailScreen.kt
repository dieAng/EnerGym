package com.dieang.energym.ui.feature.posts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dieang.energym.domain.model.Comentario
import com.dieang.energym.ui.feature.posts.components.PostCard
import com.dieang.energym.ui.theme.NeonGreen
import com.dieang.energym.ui.theme.TextGray
import java.util.UUID

import androidx.compose.ui.tooling.preview.Preview
import com.dieang.energym.ui.theme.EnerGymTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailScreen(
    postId: UUID,
    usuarioId: UUID,
    state: PostState,
    onLoad: (UUID) -> Unit,
    onLike: (UUID) -> Unit,
    onComment: (UUID, UUID, String) -> Unit,
    onBack: () -> Unit
) {
    var nuevoComentario by remember { mutableStateOf("") }

    LaunchedEffect(postId) {
        onLoad(postId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Publicación", style = MaterialTheme.typography.titleMedium) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding(),
                tonalElevation = 4.dp
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = nuevoComentario,
                        onValueChange = { nuevoComentario = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Escribe un comentario...") },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        maxLines = 3
                    )
                    IconButton(
                        onClick = {
                            if (nuevoComentario.isNotBlank()) {
                                onComment(postId, usuarioId, nuevoComentario)
                                nuevoComentario = ""
                            }
                        },
                        enabled = nuevoComentario.isNotBlank()
                    ) {
                        Icon(Icons.Default.Send, contentDescription = "Enviar", tint = NeonGreen)
                    }
                }
            }
        }
    ) { padding ->
        if (state.isLoading && state.postSeleccionado == null) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = NeonGreen)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    state.postSeleccionado?.let { post ->
                        PostCard(
                            post = post,
                            isLiked = state.posts.find { it.id == post.id }?.isLiked ?: false,
                            onLike = { onLike(post.id) },
                            onComment = { /* Ya estamos en detalle */ }
                        )
                    }
                    Divider(Modifier.padding(vertical = 16.dp), color = TextGray.copy(alpha = 0.1f))
                    Text(
                        text = "Comentarios (${state.comentarios.size})",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = TextGray
                    )
                    Spacer(Modifier.height(16.dp))
                }

                items(state.comentarios) { comentario ->
                    ComentarioItem(comentario)
                    Spacer(Modifier.height(16.dp))
                }

                item { Spacer(Modifier.height(32.dp)) }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PostDetailScreenPreview() {
    val postId = UUID.randomUUID()
    val userId = UUID.randomUUID()
    EnerGymTheme {
        PostDetailScreen(
            postId = postId,
            usuarioId = userId,
            state = PostState(
                postSeleccionado = PostUI(
                    id = postId,
                    usuarioId = UUID.randomUUID(),
                    usuarioNombre = "Carlos Ruiz",
                    usuarioFotoUrl = null,
                    tiempoHace = "2h",
                    contenido = "¡Nuevo récord en Press de Banca! La energía generada hoy ha sido increíble.",
                    tipoPost = TipoPost.Record("Press de Banca", "100 kg x 5", "+5 kg"),
                    energiaWh = 85,
                    numLikes = 12,
                    numComentarios = 2,
                    isLiked = true
                ),
                comentarios = listOf(
                    Comentario(id = UUID.randomUUID(), postId = postId, usuarioId = UUID.randomUUID(), contenido = "¡Qué grande! Enhorabuena.", fecha = System.currentTimeMillis()),
                    Comentario(id = UUID.randomUUID(), postId = postId, usuarioId = UUID.randomUUID(), contenido = "Increíble progreso, a seguir dándole.", fecha = System.currentTimeMillis())
                )
            ),
            onLoad = {},
            onLike = {},
            onComment = { _, _, _ -> },
            onBack = {}
        )
    }
}

@Composable
fun ComentarioItem(comentario: Comentario) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(NeonGreen.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "U",
                color = NeonGreen,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(Modifier.width(12.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                .padding(12.dp)
        ) {
            Text(
                text = "Atleta",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = NeonGreen
            )
            Text(
                text = comentario.contenido,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
