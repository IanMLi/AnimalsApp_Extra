package com.example.theanimalsapp.ui.animals

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
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
import androidx.compose.ui.graphics.Color
import com.example.theanimalsapp.ui.theme.*


@Composable
fun AnimalListScreen(onAnimalClick: (String) -> Unit) {
    var animals by remember { mutableStateOf<List<Animal>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Cargar datos desde la API
    LaunchedEffect(Unit) {
        isLoading = true
        errorMessage = null
        try {
            animals = ApiClient.animalsApi.getAnimals()
        } catch (e: Exception) {
            errorMessage = "Error al cargar animales: ${e.message}"
        } finally {
            isLoading = false
        }
    }

    // Mostrar contenido
    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Cargando animales...")
        }
    } else if (errorMessage != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(errorMessage ?: "Error desconocido")
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(animals.size) { index ->
                AnimalItem(animal = animals[index], onClick = { onAnimalClick(animals[index]._id) })
            }
        }
    }
}

@Composable
fun AnimalItem(animal: Animal, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = animal.image,
                contentDescription = animal.name,
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 16.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = animal.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
