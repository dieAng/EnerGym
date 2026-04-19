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
import com.dieang.energym.ui.navigation.Routes
import java.util.UUID

fun NavGraphBuilder.postsNavGraph(navController: NavController) {
    navigation(
        startDestination = Routes.POSTS,
        route = "posts_graph"
    ) {
        composable(Routes.POSTS) {
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
        }

        composable("create_post") {
            val vm: PostViewModel = hiltViewModel()
            val state by vm.state.collectAsState()
            val mockUsuarioId = UUID.randomUUID()

            CreatePostScreen(
                usuarioId = mockUsuarioId,
                state = state,
                onPostClick = { uid, content -> vm.crearPost(uid, content) },
                onBack = { navController.popBackStack() },
                onResetCreated = { vm.resetPostCreated() }
            )
        }

        composable(
            route = Routes.POST_DETAIL,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val idString = backStackEntry.arguments?.getString("id")
            val postId = idString?.let { UUID.fromString(it) } ?: UUID.randomUUID()
            val vm: PostViewModel = hiltViewModel()
            val state by vm.state.collectAsState()
            val mockUsuarioId = UUID.randomUUID()

            PostDetailScreen(
                postId = postId,
                usuarioId = mockUsuarioId,
                state = state,
                onLoad = { id -> vm.loadPostDetalle(id) },
                onLike = { id -> vm.darLike(id) },
                onComment = { pid, uid, text -> vm.comentar(pid, uid, text) },
                onBack = { navController.popBackStack() }
            )
        }
    }
}
