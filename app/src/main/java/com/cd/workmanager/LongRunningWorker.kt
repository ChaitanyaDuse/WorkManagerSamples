package com.cd.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.IOException

class LongRunningWorker(val context: Context, val workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            if (runAttemptCount < 5) {
                try {
                    delay(500)
                } catch (ex: IOException) {
                    Result.retry()
                }
                Result.success()
            } else {
                Result.failure()
            }
        }
    }

}