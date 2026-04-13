package com.dieang.energym.ui.feature.posts.components

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.dieang.energym.ui.feature.posts.PostViewModel
import com.dieang.energym.ui.feature.posts.PostsScreen
import com.dieang.energym.ui.navigation.Routes

fun NavGraphBuilder.postsNavGraph(navController: NavController) {
    navigation(
        startDestination = Routes.POSTS,
        route = Routes.POSTS
    ) {
        composable(Routes.POSTS) {
            val vm: PostViewModel = hiltViewModel()
            PostsScreen(
                state = vm.state.collectAsState().value,
                onEvent = vm::onEvent,
                onNavigateToDetail = { id -> navController.navigate("${Routes.POST_DETAIL}/$id") }
            )
        }

        composable("${Routes.POST_DETAIL}/{id}") {
            val vm: PostViewModel = hiltViewModel()
            PostDetailScreen(
                state = vm.state.collectAsState().value,
                onEvent = vm::onEvent,
                onBack = { navController.popBackStack() }
            )
        }
    }
}