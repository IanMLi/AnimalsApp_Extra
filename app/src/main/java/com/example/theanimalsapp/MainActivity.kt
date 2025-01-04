package com.example.theanimalsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.theanimalsapp.ui.animals.AnimalDetailScreen
import com.example.theanimalsapp.ui.animals.AnimalListScreen
import com.example.theanimalsapp.ui.environments.EnvironmentDetailScreen
import com.example.theanimalsapp.ui.environments.EnvironmentListScreen
import com.example.theanimalsapp.ui.theme.TheAnimalsAppTheme
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.foundation.layout.padding

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheAnimalsApp()
        }
    }
}

@Composable
fun TheAnimalsApp() {
    TheAnimalsAppTheme {
        val navController = rememberNavController()

        Scaffold(
            bottomBar = {
                BottomNavigationBar(navController = navController)
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "animalList",
                modifier = Modifier.padding(innerPadding)
            ) {
                // Lista de animales
                composable("animalList") {
                    AnimalListScreen { animalId ->
                        navController.navigate("animalDetail/$animalId")
                    }
                }
                // Detalle de animales
                composable("animalDetail/{animalId}") { backStackEntry ->
                    val animalId = backStackEntry.arguments?.getString("animalId")
                    if (animalId != null) {
                        AnimalDetailScreen(animalId = animalId)
                    } else {
                        Text("Error: ID del animal no válido")
                    }
                }
                // Lista de ambientes
                composable("environmentList") {
                    EnvironmentListScreen { environmentId ->
                        navController.navigate("environmentDetail/$environmentId")
                    }
                }
                // Detalle de ambientes
                composable("environmentDetail/{environmentId}") { backStackEntry ->
                    val environmentId = backStackEntry.arguments?.getString("environmentId")
                    if (environmentId != null) {
                        EnvironmentDetailScreen(environmentId = environmentId)
                    } else {
                        Text("Error: ID del ambiente no válido")
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        NavigationItem("Animales", "animalList", Icons.Default.Pets),
        NavigationItem("Ambientes", "environmentList", Icons.Default.Home)
    )

    NavigationBar {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}

data class NavigationItem(val label: String, val route: String, val icon: ImageVector)
