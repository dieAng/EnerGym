package com.dieang.energym

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.dieang.energym.ui.navigation.AppNavHost
import com.dieang.energym.ui.navigation.components.EnerGymBottomBar
import com.dieang.energym.ui.theme.EnerGymTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EnerGymTheme {
                val navController = rememberNavController()

                Scaffold(
                    bottomBar = { EnerGymBottomBar(navController = navController) }
                ) { paddingValues ->
                    Surface(
                        modifier = Modifier.padding(paddingValues),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        AppNavHost(navController = navController)
                    }
                }
            }
        }
    }
}
