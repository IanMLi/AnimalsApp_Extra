package com.example.theanimalsapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.example.theanimalsapp.R

val Roboto = FontFamily(
    Font(R.font.roboto_regular),
    Font(R.font.roboto_bold)
)

val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = Roboto,
        fontSize = 22.sp,
        lineHeight = 28.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Roboto,
        fontSize = 16.sp,
        lineHeight = 22.sp
    )
)
