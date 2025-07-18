package com.example.ecommerceapp.data.worker

import android.content.Context
import java.util.concurrent.TimeUnit
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class WorkManagerHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {

    val workManager = WorkManager.getInstance(context)

    inline fun <reified  T : CoroutineWorker> schedulePeriodicTask(
        uniqueWorkName: String,
        repeatInterval: Long = 2,
        timeUnit: TimeUnit = TimeUnit.HOURS,
        networkRequired: Boolean = true,
    ) {

        val constraints = Constraints.Builder().apply {
            if (networkRequired) {
                setRequiredNetworkType(androidx.work.NetworkType.CONNECTED)
            }
        }.build()


        val periodicWorkRequest = PeriodicWorkRequestBuilder<T>(
            repeatInterval, timeUnit
        ).setConstraints(constraints)
            .build()


        workManager.enqueueUniquePeriodicWork(
            uniqueWorkName,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorkRequest
        )

    }


    inline fun <reified  T : CoroutineWorker> scheduleOneTimeTask(
        uniqueWorkName: String,
        networkRequired: Boolean = true,
    ) {

        val constraints = Constraints.Builder().apply {
            if (networkRequired) {
                setRequiredNetworkType(androidx.work.NetworkType.CONNECTED)
            }
        }.build()

        val oneTimeWorkRequest = OneTimeWorkRequestBuilder<T>()
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniqueWork(
            uniqueWorkName,
            ExistingWorkPolicy.REPLACE,
            oneTimeWorkRequest
        )
    }

    fun cancelWork(uniqueWorkName: String) {
        workManager.cancelUniqueWork(uniqueWorkName)
    }
}