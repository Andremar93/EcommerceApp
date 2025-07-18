# EcommerceApp

Aplicación de ecommerce desarrollada con **Kotlin**, **Jetpack Compose**, **MVVM** y **Clean Architecture**.


## 🔔 NOTAS: 
    La aplicación llama directamente al backend harcodeado porque hice modificaciones para que
    los productos tuviesen categorias. 

    Se puede hacer Login con el usario: test@demo.com , password: RGVtbzEyMyE=

   Cualquier duda por favor consultarme. 

## 🚀 Características principales

- Registro e inicio de sesión de usuarios (Se guardan en la base de datos, simple. NO encripta el password)
- Carga de productos (Desde el backend)
- Agregado al carrito (Guarda el carrito en base de datos con ROOM)
- Listado y detalles de órdenes (Se guardan las ordenes en la base de datos y hay un screen para ver las ordenes)

## 🧩 Cosas por mejorar o agregar
- [ ] Mejorar experiencia de usuario con animaciones (transiciones, feedback visual)
- [ ] Modo oscuro para toda la app
- [ ] Agregar soporte offline con Room y sincronización automática
- [ ] Internacionalización: soporte para varios idiomas
- [ ] Mejorar el manejo de errores y mostrar mensajes claros al usuario
- [ ] Mejorar todo la interfaz gráfica
- [ ] y muchas mas... 

## 🐞 Bugs conocidos


## 🧪 Tests
Próximamente...

## ⚙️ Cómo correr la app

1. Cloná el repo:
   ```bash
   git clone https://github.com/Andremar93/EcommerceApp.git


## 🛠 Tecnologías

- Kotlin
- Jetpack Compose
- MVVM
- Clean Architecture
- Hilt (Inyección de dependencias)
- Retrofit (Consumo de APIs)
- Room (Base de datos local)

## 📦 Estructura del proyecto


📦EcommerceApp
┣ 📂data
┃ ┣ 📂local
┃ ┣ 📂model
┃ ┣ 📂remote
┃ ┗ 📂repository
┣ 📂di
┃ ┣ 📂CartModule
┃ ┣ 📂NetworkModule
┃ ┣ 📂OrdersModule
┃ ┣ 📂ProductsModule
┃ ┣ 📂RoomModule
┃ ┗ 📂UsersModule
┣ 📂domain
┃ ┣ 📂local.data_source
┃ ┣ 📂model
┃ ┣ 📂remote.data_source
┃ ┣ 📂repository
┃ ┗ 📂use_case
┣ 📂presentation
┃ ┣ 📂navigation
┃ ┗ 📂view
┣ 📂utils
┃ EcommerceApplication
┗ MainActivity.kt

## ✨ Autora
Andrea Martínez — @andremar93






