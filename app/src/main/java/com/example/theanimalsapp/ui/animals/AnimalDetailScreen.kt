package com.example.theanimalsapp.ui.animals

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.theanimalsapp.data.ApiClient
import com.example.theanimalsapp.data.Animal
import kotlinx.coroutines.launch
import com.example.theanimalsapp.ui.theme.*


@Composable
fun AnimalDetailScreen(animalId: String) {
    var animal by remember { mutableStateOf<Animal?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Cargar datos desde la API
    LaunchedEffect(animalId) {
        isLoading = true
        errorMessage = null
        try {
            animal = ApiClient.animalsApi.getAnimalDetail(animalId)
        } catch (e: Exception) {
            errorMessage = "Error al cargar el animal: ${e.message}"
        } finally {
            isLoading = false
        }
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (errorMessage != null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = errorMessage ?: "Error desconocido")
        }
    } else {
        animal?.let { animalData ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Imagen principal
                AsyncImage(
                    model = animalData.image,
                    contentDescription = animalData.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Nombre
                Text(
                    text = animalData.name,
                    style = androidx.compose.material3.MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Descripción
                Text(
                    text = animalData.description,
                    style = androidx.compose.material3.MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Galería de imágenes
                Text(
                    text = "Galería de Imágenes",
                    style = androidx.compose.material3.MaterialTheme.typography.titleMedium
                )
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(animalData.facts.size) { index ->
                        AsyncImage(
                            model = animalData.image,
                            contentDescription = "Imagen ${index + 1}",
                            modifier = Modifier
                                .size(100.dp)
                                .padding(4.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Lista de hechos interesantes
                Text(
                    text = "Hechos Interesantes",
                    style = androidx.compose.material3.MaterialTheme.typography.titleMedium
                )
                animalData.facts.forEach { fact ->
                    Text(
                        text = "- $fact",
                        style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
