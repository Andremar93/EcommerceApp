# EcommerceApp

Aplicación de ecommerce desarrollada con **Kotlin**, **Jetpack Compose**, **MVVM** y **Clean Architecture**.


## 🔔 NOTAS: 
    Es necesiario tener corriendo el backend de la API para que la app funcione correctamente.
    Este es la url a la API: https://peya-backend.onrender.com/
    El backend se encuentra en el siguiente repositorio: https://github.com/Andremar93/peya-backend

    El backend es diferente al que nos provieron en el curso, agregue más funcionalidades y lo adapte a la app.

    Se puede hacer Login con el usario: test@demo.com , password: RGVtbzEyMyE=

   Cualquier duda por favor consultarme. 

## 🚀 Características principales

- Registro e inicio de sesión de usuarios (Se guardan en la base de datos, simple. NO encripta el password)
- Carga de productos (Desde el backend)
- Agregado al carrito (Guarda el carrito en base de datos con ROOM)
- Listado y detalles de órdenes (Se guardan las ordenes en la base de datos,en la API y hay un screen para ver las ordenes)
- User profile (Se guarda el usuario en la base de datos y en el BACKEND, se puede editar el nombre y apellido)

## 🧩 Cosas por mejorar o agregar
- [ ] Mejorar experiencia de usuario con animaciones (transiciones, feedback visual)
- [ ] Internacionalización: soporte para varios idiomas
- [ ] Mejorar todo la interfaz gráfica
- [ ] y muchas mas... 

## 🐞 Bugs conocidos


## 🧪 Tests
  Los test se deben mejorar

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






