package com.example.ecommerceapp.presentation.view.components.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.ecommerceapp.R

@Composable
fun MainLayout(
    navController: NavHostController,
    showTopBar: Boolean = true,
    showBottomBar: Boolean = true,
    topBarMessage: String = "",
    selectedItem: String = "Home",
    mainContent: @Composable () -> Unit = { Text("Main Content") }
) {

    val navigationItems = listOf(
        Triple(stringResource(R.string.home_feed), Icons.Filled.Home, "products"),
        Triple(stringResource(R.string.home_cart), Icons.Filled.ShoppingCart, "cart"),
        Triple(stringResource(R.string.home_profile), Icons.Filled.Person, "profile"),
        Triple(stringResource(R.string.home_settings), Icons.Filled.Settings, "settings"),
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (showTopBar) {
                TopBar(topBarMessage)
            }
        },
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(navigationItems, selectedItem, navController)
            }
        },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            mainContent()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun MainLayoutPreview() {

    val navItems = listOf(
        Triple("Home", Icons.Filled.Home, "products"),
        Triple("Cart", Icons.Filled.ShoppingCart, "cart"),
        Triple("Profile", Icons.Filled.Person, "profile"),
        Triple("Settings", Icons.Filled.Settings, "settings")
    )

    Scaffold(
        bottomBar = {
            BottomNavBar(
                navigationItems = navItems,
                selectedItem = "cart",
                navController = androidx.navigation.compose.rememberNavController(),
            )
        }
    ) {
        Text("Preview content", modifier = Modifier.padding(it))
    }
}

