import java.util.Properties


val localProperties = Properties()
localProperties.load(rootProject.file("local.properties").inputStream())

val baseUrl = localProperties["BASE_URL"]?.toString()
    ?: throw GradleException("BASE_URL not found in local.properties")

val cloudinaryName = localProperties["CLOUDINARY_NAME"]?.toString()
    ?: throw GradleException("CLOUDINARY_NAME not found in local.properties")

val cloudinaryKey = localProperties["CLOUDINARY_API_KEY"]?.toString()
    ?: throw GradleException("CLOUDINARY_API_KEY not found in local.properties")

val cloudinarySecret = localProperties["CLOUDINARY_API_SECRET"]?.toString()
    ?: throw GradleException("CLOUDINARY_API_SECRET not found in local.properties")

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.ecommerceapp"
    compileSdk = 35

    buildFeatures {
        buildConfig = true
        compose = true
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.example.ecommerceapp"
        minSdk = 27
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
        buildConfigField("String", "CLOUDINARY_NAME", "\"$cloudinaryName\"")
        buildConfigField("String", "CLOUDINARY_API_KEY", "\"$cloudinaryKey\"")
        buildConfigField("String", "CLOUDINARY_API_SECRET", "\"$cloudinarySecret\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.benchmark.macro)
    implementation(libs.androidx.navigation.testing.android)
    // OkHttp Logging Interceptor
    implementation(libs.logging.interceptor)
    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    debugImplementation(libs.leakcanary.android)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.navigation.compose)
    implementation(project(":feature:cart"))

//    implementation("androidx.work.work-runtime-ktx:2.8.1")

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(libs.coil.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.material3)

    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)

    implementation(libs.androidx.room.ktx)

    implementation(libs.cloudinary.android)

}