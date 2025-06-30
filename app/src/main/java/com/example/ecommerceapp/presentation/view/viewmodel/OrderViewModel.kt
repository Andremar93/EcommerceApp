package com.example.ecommerceapp.presentation.view.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ecommerceapp.data.orders.OrdersDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class OrderViewModel @Inject constructor(
    private val ordersDataSource: OrdersDataSource
) : ViewModel() {

    val orders = ordersDataSource.orders

    fun createOrder(){

    }

}