package com.rittmann.githubapiapp.support.recyclerview

import android.view.View
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.NoMatchingViewException
import androidx.recyclerview.widget.RecyclerView
import com.rittmann.androidtools.log.log
import org.hamcrest.Matchers
import org.junit.Assert
import java.util.*

class RecyclerViewItemCountAssertion(private val expectedCount: Int) : ViewAssertion {
    override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }

        expectedCount.log("expected ")

        val recyclerView = view as RecyclerView

        Assert.assertThat(
            Objects.requireNonNull(recyclerView.adapter).itemCount,
            Matchers.`is`(expectedCount)
        )
    }
}