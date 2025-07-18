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

    fun getOrders() {
        viewModelScope.launch {
            uiState = UIState.Loading

            try {
                val user = getActiveUserUseCase.invoke()
                val orders = getOrdersUseCase.invoke(user.id)
                uiState = UIState.Success(orders)

            } catch (e: IOException) {
                uiState = UIState.Error("Sin conexión a Internet")
                Log.e(
                    "ProductListViewModel",
                    "Sin conexión a Internet, error al cargar los productos",
                    e
                )
            } catch (e: HttpException) {
                uiState = UIState.Error("Error al cargar los productos: ${e.message}")
            } catch (e: Exception) {
                uiState = UIState.Error(e.message ?: "Unknown error")
            }

        }
    }
}