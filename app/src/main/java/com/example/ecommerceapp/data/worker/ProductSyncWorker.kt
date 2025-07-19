package com.example.ecommerceapp.data.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.ecommerceapp.domain.use_case.product.GetProductsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

@HiltWorker
class ProductSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val entryPoint = EntryPointAccessors.fromApplication(
                applicationContext,
                ProductSyncWorkerEntryPoint::class.java
            )
            val getProductsUseCase = entryPoint.getProductsUseCase()
            Log.d("ProductSyncWorker", "Llamando a getProductUseCase...")
            getProductsUseCase(refreshData = true)
            Log.d("ProductSyncWorker", "getProductsUseCase completed successfully.")
            Result.success()
        } catch (e: Exception) {
            Log.d("ProductSyncWorker", "Error in ProductSyncWorker: ${e.message}")
            Result.failure()
        }
    }


    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface ProductSyncWorkerEntryPoint {
        fun getProductsUseCase(): GetProductsUseCase
    }
}