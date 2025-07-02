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
- Agregado al carrito (Guarda el carrito en base de datos)
- Listado y detalles de órdenes (Se guardan las ordenes en la base de datos y hay un screen para ver las ordenes)
- Pagina de perfil de usuario (Se puede modificar, aun no tiene Cloudinary)

## 🧩 Cosas por mejorar o agregar
- [ ] En la pagina de perfil el usuario esta harcodeado
- [ ] Validaciones más robustas en formularios (registro y login)
- [ ] Mejorar experiencia de usuario con animaciones (transiciones, feedback visual)
- [ ] Modo oscuro para toda la app
- [ ] Agregar soporte offline con Room y sincronización automática
- [ ] Internacionalización: soporte para varios idiomas
- [ ] Refactor de algunos viewmodels para mejorar separación de responsabilidades
- [ ] Mejorar el manejo de errores y mostrar mensajes claros al usuario
- [ ] Mejorar todo la interfaz gráfica
- [ ] Las ordenes no estan asociadas a los usarios todavia.
- [ ] No hay forma de cerrar sesion
- [ ] y muchas mas... 

## 🐞 Bugs conocidos

- [ ] 🔄 La página de inicio/productos parece que esta cargando dos veces o algo parecido porque a veces hace un glitch

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
┃ ┣ 📂cart
┃ ┣ 📂database
┃ ┣ 📂fakeRepositories (se uso cuando no habia backend)
┃ ┣ 📂orders
┃ ┣ 📂products
┃ ┣ 📂remote
┃ ┗ 📂users
┣ 📂di
┣ 📂domain
┃ ┣ 📂model
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






