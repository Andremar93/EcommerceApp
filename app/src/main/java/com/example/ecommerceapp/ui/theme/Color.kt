package com.example.ecommerceapp.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val Pink = Color(0xFFF28482)
val Green = Color(0xFF84A59D)
val DarkGreen = Color(0xFF547A6A)
val Yellow = Color(0xFFF7EDE2)
val YellowLight = Color(0xFFFFFFF2)
val Dark = Color(0xFF3D405B)
val Red = Color(0xFFB00020)

val SurfaceVariantLight = Green//Color(0xFFEAE2F2)
val SurfaceVariantDark = Color(0xFF484459)
val OutlineLight = Color(0xFFB0AFC2)
val OutlineDark = Color(0xFF807E9C)
val Scrim = Color(0x66000000)

val LightColorScheme = lightColorScheme(
    primary = Green,
    onPrimary = Color.White,
    secondary = DarkGreen,
    onSecondary = Color.White,
    tertiary = Green,
    onTertiary = Color.White,
    background = Color.White,
    onBackground = Dark,
    surface = Color.White,
    onSurface = Dark,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = DarkGreen,
    outline = OutlineLight,
    error = Red,
    onError = Color.White,
    inversePrimary = Green,
    inverseSurface = Dark,
    inverseOnSurface = YellowLight,
    scrim = Scrim
)

val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    onPrimary = Dark,
    secondary = Pink80,
    onSecondary = Dark,
    tertiary = Pink40,
    onTertiary = Color.White,
    background = Dark,
    onBackground = YellowLight,
    surface = Color(0xFF2C2C3A),
    onSurface = YellowLight,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = PurpleGrey80,
    outline = OutlineDark,
    error = Color(0xFFCF6679),
    onError = Color.Black,
    inversePrimary = PurpleGrey80,
    inverseSurface = YellowLight,
    inverseOnSurface = Dark,
    scrim = Scrim
)
