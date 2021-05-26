package com.rittmann.githubapiapp.support

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.idling.CountingIdlingResource
import androidx.test.platform.app.InstrumentationRegistry
import com.rittmann.androidtools.log.log
import org.junit.Rule

open class ActivityTest {

    private val mCountingIdlingResource = CountingIdlingResource(RESOURCE)
    protected val targetContext: Context =
        InstrumentationRegistry.getInstrumentation().targetContext
    protected val context: Context = ApplicationProvider.getApplicationContext()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    fun increment() {
        mCountingIdlingResource.increment()
    }

    fun decrement() {
        mCountingIdlingResource.decrement()
    }

    fun getIdlingResource() = mCountingIdlingResource

    inline fun <reified A : Activity> getActivity(): ActivityScenario<A> {
        return ActivityScenario.launch(A::class.java)
    }

    inline fun <reified A : Activity> getActivity(intent: Intent? = null): ActivityScenario<A> {
        return ActivityScenario.launch(intent)
    }

    fun setup() {
    }

    fun release() {
    }

    fun getString(resId: Int) = targetContext.getString(resId)

    fun dumpThreads() {
        val activeCount = Thread.activeCount()
        val threads = arrayOfNulls<Thread>(activeCount)
        Thread.enumerate(threads)
        for (thread in threads) {
            "${thread!!.name} + \": \" + ${thread.state}".log()
            System.err.println()
            for (stackTraceElement in thread.stackTrace) {
                "\t$stackTraceElement".log()
            }
        }
    }

    companion object {
        private const val RESOURCE = "GLOBAL"
    }
}


