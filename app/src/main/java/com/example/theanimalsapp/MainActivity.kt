package com.example.theanimalsapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.theanimalsapp.data.ApiClient
import com.example.theanimalsapp.ui.animals.AnimalDetailScreen
import com.example.theanimalsapp.ui.animals.AnimalListScreen
import com.example.theanimalsapp.ui.theme.TheAnimalsAppTheme
import kotlinx.coroutines.launch
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Text


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configuración de Jetpack Compose
        setContent {
            TheAnimalsApp()
        }

        // Prueba la API
//        lifecycleScope.launch {
//            try {
//                val animals = ApiClient.animalsApi.getAnimals()
//                Log.d("AnimalsApp", "Animales obtenidos: $animals")
//            } catch (e: Exception) {
//                Log.e("AnimalsApp", "Error al obtener animales: ${e.message}")
//            }
//        }
    }
}

@Composable
fun TheAnimalsApp() {
    TheAnimalsAppTheme {
        val navController = rememberNavController()

        // Configurar el NavHost
        NavHost(
            navController = navController,
            startDestination = "animalList"
        ) {
            composable("animalList") {
                AnimalListScreen { animalId ->
                    navController.navigate("animalDetail/$animalId")
                }
            }
            composable("animalDetail/{animalId}") { backStackEntry ->
                val animalId = backStackEntry.arguments?.getString("animalId")?.toIntOrNull()
                if (animalId != null) {
                    AnimalDetailScreen(animalId = animalId)
                } else {
                    Text("Error: ID del animal no válido")
                }
            }
        }
    }
}

@Composable
fun AnimalListScreen(onAnimalClick: (Int) -> Unit) {
    // Simulación de datos de animales (puedes reemplazar esto con datos reales)
    Text("Pantalla de lista de animales")
}

@Composable
fun AnimalDetailScreen(animalId: Int) {
    // Simulación de detalle de animal (puedes reemplazar esto con datos reales)
    Text("Pantalla de detalle del animal con ID: $animalId")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TheAnimalsApp()
}
