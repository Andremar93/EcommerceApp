package com.example.ecommerceapp.presentation.view.views

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.ecommerceapp.presentation.view.components.UnderConstructionScreen
import com.example.ecommerceapp.presentation.view.components.layout.MainLayout

@Composable
fun SettingsScreen(navController: NavHostController) {

    MainLayout(
        navController = navController,
        selectedItem = "settings",
        topBarMessage = "Settings",
        mainContent = {
            UnderConstructionScreen(
                title = "Página en construcción",
                message = "Estamos trabajando para traerte esta sección pronto.",
                showButton = false,
                buttonText = "Volver al inicio",
//            onButtonClick: {}
            )

        }
    )
}