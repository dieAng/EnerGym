package com.dieang.energym.ui.feature.posts.components

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.dieang.energym.ui.feature.posts.CreatePostScreen
import com.dieang.energym.ui.feature.posts.PostDetailScreen
import com.dieang.energym.ui.feature.posts.PostViewModel
import com.dieang.energym.ui.feature.posts.PostsScreen
import com.dieang.energym.ui.navigation.AuthGuard
import com.dieang.energym.ui.navigation.Routes
import java.util.UUID

fun NavGraphBuilder.postsNavGraph(navController: NavController) {
    navigation(
        startDestination = "posts_main",
        route = Routes.POSTS
    ) {
        composable("posts_main") {
            AuthGuard(
                content = {
                    val vm: PostViewModel = hiltViewModel()
                    val state by vm.state.collectAsState()
                    PostsScreen(
                        state = state,
                        onLikePost = { id -> vm.darLike(id) },
                        onCommentPost = { id ->
                            navController.navigate(Routes.POST_DETAIL.replace("{id}", id))
                        },
                        onUserClick = { userId ->
                            navController.navigate(Routes.USUARIO_DETAIL.replace("{id}", userId.toString()))
                        },
                        onCreatePost = { navController.navigate("create_post") },
                        onRefresh = { vm.loadCommunityContent() }
                    )
                },
                onNotLoggedIn = { navController.navigate(Routes.LOGIN) }
            )
        }

        composable(
            route = Routes.POST_DETAIL,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = UUID.fromString(backStackEntry.arguments?.getString("id") ?: "")
            AuthGuard(
                content = {
                    val vm: PostViewModel = hiltViewModel()
                    // Necesitamos el ID del usuario logueado para comentar
                    // Por ahora simulamos un ID o lo obtenemos del estado si está disponible
                    val state by vm.state.collectAsState()
                    val userId = state.posts.firstOrNull()?.usuarioId ?: UUID.randomUUID()

                    PostDetailScreen(
                        postId = id,
                        usuarioId = userId,
                        state = state,
                        onLoad = vm::loadPostDetalle,
                        onLike = { vm.darLike(it.toString()) },
                        onComment = vm::comentar,
                        onBack = { navController.popBackStack() }
                    )
                },
                onNotLoggedIn = { navController.navigate(Routes.LOGIN) }
            )
        }

        composable("create_post") {
            AuthGuard(
                content = {
                    val vm: PostViewModel = hiltViewModel()
                    val state by vm.state.collectAsState()
                    val userId = state.posts.firstOrNull()?.usuarioId ?: UUID.randomUUID()
                    
                    CreatePostScreen(
                        usuarioId = userId,
                        state = state,
                        onPostClick = vm::crearPost,
                        onBack = { navController.popBackStack() },
                        onResetCreated = vm::resetPostCreated
                    )
                },
                onNotLoggedIn = { navController.navigate(Routes.LOGIN) }
            )
        }
    }
}
