package com.cd.workmanager

import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.ListenableWorker
import androidx.work.testing.TestListenableWorkerBuilder
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LongRunningWorkerInstrumentationTest {
    val MAX_RUN_RETRIES = 5

    @Test
    fun testWorkerSucceedsIfRunAttemptIsLessThanMax() {
        val worker = TestListenableWorkerBuilder<LongRunningWorker>(
            context = ApplicationProvider.getApplicationContext(),
            runAttemptCount = MAX_RUN_RETRIES - 1
        ).build()
        runBlocking {
            val result = worker.doWork()
            assertThat(result, `is`(ListenableWorker.Result.Success()))
        }

    }

    @Test
    fun testWorkerFailsIfRunAttemptIsAtMax() {
        val worker = TestListenableWorkerBuilder<LongRunningWorker>(
            context = ApplicationProvider.getApplicationContext(),
            runAttemptCount = MAX_RUN_RETRIES
        ).build()
        runBlocking {
            val result = worker.doWork()
            assertThat(result, `is`(ListenableWorker.Result.Failure()))
        }
    }
}