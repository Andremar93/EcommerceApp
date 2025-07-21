package com.example.ecommerceapp.presentation.view.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.ecommerceapp.R
import com.example.ecommerceapp.presentation.view.components.UnderConstructionScreen
import com.example.ecommerceapp.presentation.view.components.layout.MainLayout
import com.example.ecommerceapp.presentation.view.viewmodel.CartViewModel

@Composable
fun SettingsScreen(navController: NavHostController, cartViewModel: CartViewModel) {
    MainLayout(
        navController = navController,
        selectedItem = "settings",
        cartViewModel = cartViewModel,
        topBarMessage = stringResource(id = R.string.settings),
        mainContent = {
            UnderConstructionScreen(
                title = stringResource(id = R.string.under_construction_title),
                message = stringResource(id = R.string.under_construction_message),
                showButton = true,
                buttonText = stringResource(id = R.string.back_to_home),
                onButtonClick = {
                    navController.navigate("products") {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    )
}
