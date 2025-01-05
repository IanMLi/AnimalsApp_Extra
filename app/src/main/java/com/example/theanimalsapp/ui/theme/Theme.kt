package com.example.theanimalsapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val BlueMountain = Color(0xFF3F51B5)
val GreenWater = Color(0xFF009688)
val LightGrayBackground = Color(0xFFF5F5F5)

private val LightColorScheme = lightColorScheme(
    primary = BlueMountain,
    secondary = GreenWater,
    background = LightGrayBackground,
    surface = Color.White
)

private val DarkColorScheme = darkColorScheme(
    primary = BlueMountain,
    secondary = GreenWater,
    background = Color.Black,
    surface = Color.DarkGray
)

@Composable
fun TheAnimalsAppTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}
