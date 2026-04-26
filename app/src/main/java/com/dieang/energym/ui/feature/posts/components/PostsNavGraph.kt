package com.dieang.energym.ui.feature.posts.components

import androidx.compose.runtime.Composable
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

@Composable
fun PostsNavGraph(navController: NavController) {
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
