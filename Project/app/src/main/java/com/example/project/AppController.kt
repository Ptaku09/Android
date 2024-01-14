package com.example.project

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.*
import com.example.project.screens.AddPhotoScreen
import com.example.project.screens.AddScreen
import com.example.project.screens.DetailsScreen
import com.example.project.screens.EditScreen
import com.example.project.screens.HomeScreen
import com.example.project.screens.ListScreen
import com.example.project.screens.PhotoScreen
import com.example.project.screens.PhotoSlider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun MyApp() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            MyDrawerContent(
                navController = navController,
                drawerState = drawerState,
                scope
            )
        },
        content = {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = { MyBottomBar(navController) },
                content = { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Home.route) {
                            HomeScreen()
                        }
                        composable(Screen.Photos.route) {
                            PhotoScreen(navController)
                        }
                        composable(Screen.Players.route) {
                            ListScreen(navController)
                        }
                        composable("add") {
                            AddScreen(navController)
                        }
                        composable("addPhoto") {
                            AddPhotoScreen(navController)
                        }
                        composable("slider/{clickedIndex}") { backStackEntry ->
                            val clickedIndex =
                                backStackEntry.arguments?.getString("clickedIndex")?.toIntOrNull()
                                    ?: 0

                            PhotoSlider(clickedIndex)
                        }
                        composable("details/{PlayerDataId}") { backStackEntry ->
                            val playerDataId =
                                backStackEntry.arguments?.getString("PlayerDataId")?.toIntOrNull()
                                    ?: 0

                            DetailsScreen(navController, playerDataId)
                        }
                        composable("edit/{PlayerDataId}") { backStackEntry ->
                            val playerDataId =
                                backStackEntry.arguments?.getString("PlayerDataId")?.toIntOrNull()
                                    ?: 0

                            EditScreen(navController, playerDataId)
                        }
                    }
                }
            )
        }
    )
}
