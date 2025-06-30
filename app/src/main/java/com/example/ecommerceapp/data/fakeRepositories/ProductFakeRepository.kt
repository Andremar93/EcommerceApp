package com.example.ecommerceapp.data.fakeRepositories

import com.example.ecommerceapp.domain.model.Product

object ProductFakeRepository {
    private val allProducts = mutableListOf(
        Product("1", "Hamburguesa Clásica", "Pan, carne, queso", 5.99, true, "comida"),
        Product("2", "Camiseta Azul", "100% algodón", 9.99, false, "ropa"),
        Product("3", "Smartphone XYZ", "Android 12", 249.99, false, "tecnología"),
        Product("4", "Pantalón Jeans", "Talle M", 19.99, true, "ropa"),
        Product("5", "Laptop ABC", "16GB RAM", 899.99, true, "tecnología")
    )

    fun getAllProducts(): List<Product> = allProducts

}