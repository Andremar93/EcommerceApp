package com.example.ecommerceapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ecommerceapp.presentation.view.views.CartScreen
import com.example.ecommerceapp.presentation.view.views.LoginScreen
import com.example.ecommerceapp.presentation.view.views.OrdersScreen
import com.example.ecommerceapp.presentation.view.views.ProductListScreen
import com.example.ecommerceapp.presentation.view.views.RegisterScreen
import com.example.ecommerceapp.presentation.view.views.SettingsScreen
import com.example.ecommerceapp.presentation.view.views.SplashScreen
import com.example.ecommerceapp.presentation.view.views.UserScreen


object Routes {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val PRODUCTS = "products"
    const val REGISTER = "register"
    const val CART = "cart"
    const val PROFILE = "profile"
    const val SETTINGS = "settings"
    const val ORDERS = "orders"
}

@Composable
fun AppNavigation(navController: NavHostController) {


    NavHost(navController = navController, startDestination = Routes.LOGIN) {

        composable(Routes.SPLASH) {
            SplashScreen(navController)
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                navController = navController,
                onLoginSuccess = {
                    navController.navigate(Routes.PRODUCTS) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.REGISTER) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.REGISTER) { inclusive = true }
                    }
                },
            )
        }

        composable(Routes.PRODUCTS) {
            ProductListScreen(navController = navController)
        }

        composable(Routes.CART) {
            CartScreen(
                onBack = {
                    navController.navigate(Routes.PRODUCTS) {
                        popUpTo(Routes.CART) { inclusive = true }
                    }
                }, navController = navController,
                onCheckoutSuccess = {
                    navController.navigate(Routes.ORDERS) {
                        popUpTo(Routes.CART) { inclusive = true }
                    }
                })
        }

        composable(Routes.PROFILE) {
            UserScreen(navController = navController)
        }

        composable(Routes.SETTINGS) {
            SettingsScreen(navController = navController)
        }

        composable(Routes.ORDERS) {
            OrdersScreen(navController = navController)
        }

    }
}

