package com.example.ecommerceapp.presentation.view.components.layout

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.ecommerceapp.R
import com.example.ecommerceapp.presentation.view.viewmodel.CartViewModel
import kotlinx.coroutines.flow.StateFlow


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
    val fakeCartItemCount = kotlinx.coroutines.flow.MutableStateFlow(3)

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

