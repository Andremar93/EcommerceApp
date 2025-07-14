package com.example.ecommerceapp.data.fakeRepositories

import com.example.ecommerceapp.domain.model.ProductItem

object ProductFakeRepository {
    private val allProductItems = mutableListOf(
        ProductItem("1", "Hamburguesa Clásica", "Pan, carne, queso", 5.99, true, "comida"),
        ProductItem("2", "Camiseta Azul", "100% algodón", 9.99, false, "ropa"),
        ProductItem("3", "Smartphone XYZ", "Android 12", 249.99, false, "tecnología"),
        ProductItem("4", "Pantalón Jeans", "Talle M", 19.99, true, "ropa"),
        ProductItem("5", "Laptop ABC", "16GB RAM", 899.99, true, "tecnología")
    )

    fun getAllProducts(): List<ProductItem> = allProductItems

}