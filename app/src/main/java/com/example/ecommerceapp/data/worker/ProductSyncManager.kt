package com.example.ecommerceapp.data.worker


import javax.inject.Inject

class ProductSyncManager @Inject constructor(
    private val workManagerHelper: WorkManagerHelper
) {

    fun scheduleSync() {
        workManagerHelper.schedulePeriodicTask<ProductSyncWorker>(
            uniqueWorkName = "PRODUCT_SYNC_WORK",
            repeatInterval = 2,
            timeUnit = java.util.concurrent.TimeUnit.HOURS
        )
    }

    fun syncNow() {
        workManagerHelper.scheduleOneTimeTask<ProductSyncWorker>(
            uniqueWorkName = "PRODUCT_SYNC_WORK_NOW"
        )
    }
}