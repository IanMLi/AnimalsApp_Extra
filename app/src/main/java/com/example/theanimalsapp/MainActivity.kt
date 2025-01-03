package com.example.theanimalsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.theanimalsapp.ui.theme.TheAnimalsAppTheme
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.theanimalsapp.data.ApiClient
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Tu contenido de Jetpack Compose aquí
        }

        // Prueba la API
        lifecycleScope.launch {
            try {
                val animals = ApiClient.animalsApi.getAnimals()
                Log.d("AnimalsApp", "Animales obtenidos: $animals")
            } catch (e: Exception) {
                Log.e("AnimalsApp", "Error al obtener animales: ${e.message}")
            }
        }
    }
}
