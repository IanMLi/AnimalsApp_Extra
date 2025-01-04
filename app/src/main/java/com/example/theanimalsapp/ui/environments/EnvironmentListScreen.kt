package com.example.theanimalsapp.ui.environments

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.theanimalsapp.data.ApiClient
import com.example.theanimalsapp.data.Environment

@Composable
fun EnvironmentListScreen(onEnvironmentClick: (String) -> Unit) {
    var environments by remember { mutableStateOf<List<Environment>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Cargar datos desde la API
    LaunchedEffect(Unit) {
        isLoading = true
        errorMessage = null
        try {
            environments = ApiClient.animalsApi.getEnvironments()
        } catch (e: Exception) {
            errorMessage = "Error al cargar ambientes: ${e.message}"
        } finally {
            isLoading = false
        }
    }

    // Mostrar contenido
    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Cargando ambientes...")
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
            items(environments.size) { index ->
                EnvironmentItem(environment = environments[index], onClick = { onEnvironmentClick(environments[index]._id) })
            }
        }
    }
}

@Composable
fun EnvironmentItem(environment: Environment, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = environment.image,
                contentDescription = environment.name,
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 16.dp),
                contentScale = ContentScale.Crop
            )
            Column {
                Text(text = environment.name, style = androidx.compose.material3.MaterialTheme.typography.titleMedium)
            }
        }
    }
}
