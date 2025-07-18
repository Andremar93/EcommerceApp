package com.example.ecommerceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.ecommerceapp.data.worker.ProductSyncManager
import com.example.ecommerceapp.presentation.navigation.AppNavigation
import com.example.ecommerceapp.ui.theme.EcommerceAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var productSyncManager: ProductSyncManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        productSyncManager.scheduleSync()

        productSyncManager.syncNow()

        enableEdgeToEdge()
        setContent {
            EcommerceAppTheme {
                val navController = rememberNavController()
                AppNavigation(navController)
            }
        }
    }
}
