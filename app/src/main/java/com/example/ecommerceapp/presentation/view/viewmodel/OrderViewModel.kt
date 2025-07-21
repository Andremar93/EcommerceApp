package com.example.ecommerceapp.presentation.view.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.domain.model.OrderItem
import com.example.ecommerceapp.domain.use_case.order.GetOrdersUseCase
import com.example.ecommerceapp.domain.use_case.user.GetActiveUserUseCase
import com.example.ecommerceapp.presentation.view.components.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val getOrdersUseCase: GetOrdersUseCase,
    private val getActiveUserUseCase: GetActiveUserUseCase
) : ViewModel() {

    var uiState by mutableStateOf<UIState<List<OrderItem>>>(UIState.Loading)
        private set

    fun getOrders() {
        viewModelScope.launch {
            uiState = UIState.Loading
            try {
                val user = getActiveUserUseCase()
                val orders = getOrdersUseCase(user.id)
                uiState = UIState.Success(orders)
            } catch (e: IOException) {
                Log.e("OrderViewModel", "No internet", e)
                uiState = UIState.Error("Sin conexión a Internet")
            } catch (e: HttpException) {
                Log.e("OrderViewModel", "HTTP error: ${e.code()}", e)
                if (e.code() == 404) {
                    // 404 = sin pedidos, lo tratamos como éxito con lista vacía
                    uiState = UIState.Success(emptyList())
                } else {
                    uiState = UIState.Error("Error al cargar pedidos: ${e.message()}")
                }
            } catch (e: Exception) {
                Log.e("OrderViewModel", "Unknown error", e)
                uiState = UIState.Error("Error inesperado: ${e.message ?: "desconocido"}")
            }
        }
    }
}
