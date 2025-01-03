package com.example.theanimalsapp.ui.animals

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.theanimalsapp.data.Animal
import com.example.theanimalsapp.data.ApiClient

@Composable
fun AnimalDetailScreen(animalId: Int) {
    val animal = remember { mutableStateOf<Animal?>(null) }

    // Cargar datos del animal
    LaunchedEffect(animalId) {
        try {
            val apiResponse = ApiClient.animalsApi.getAnimalDetail(animalId)
            animal.value = apiResponse
        } catch (e: Exception) {
            // Manejar errores
        }
    }

    animal.value?.let { animalData ->
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
                    .height(200.dp)
                    .padding(bottom = 16.dp),
                contentScale = ContentScale.Crop
            )

            // Nombre
            Text(
                text = animalData.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Descripción
            Text(
                text = animalData.description,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Galería de imágenes
            Text(
                text = "Galería de imágenes",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
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

            // Lista de hechos interesantes
            Text(
                text = "Hechos interesantes",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            for (fact in animalData.facts) {
                Text(
                    text = "- $fact",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }
    } ?: run {
        // Pantalla de carga
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}
