package com.example.ecommerceapp.presentation.view.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ecommerceapp.domain.repository.OrdersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class OrderViewModel @Inject constructor(
    private val ordersRepository: OrdersRepository
) : ViewModel() {

    val orders = ordersRepository.orders

    fun createOrder(){

    }

}