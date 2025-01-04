package com.example.theanimalsapp.ui.environments

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.theanimalsapp.data.ApiClient
import com.example.theanimalsapp.data.Environment
import kotlinx.coroutines.launch

@Composable
fun EnvironmentDetailScreen(environmentId: String) {
    var environment by remember { mutableStateOf<Environment?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Cargar datos desde la API
    LaunchedEffect(environmentId) {
        isLoading = true
        errorMessage = null
        try {
            environment = ApiClient.animalsApi.getEnvironmentDetail(environmentId)
        } catch (e: Exception) {
            errorMessage = "Error al cargar ambiente: ${e.message}"
        } finally {
            isLoading = false
        }
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (errorMessage != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(errorMessage ?: "Error desconocido")
        }
    } else {
        environment?.let { env ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                AsyncImage(
                    model = env.image,
                    contentDescription = env.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = env.name, style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = env.description, style = androidx.compose.material3.MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Animales en este ambiente:", style = androidx.compose.material3.MaterialTheme.typography.titleMedium)
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(env.animals.size) { index ->
                        Text(text = "- ${env.animals[index].name}")
                    }
                }
            }
        }
    }
}
