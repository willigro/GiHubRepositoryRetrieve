package com.rittmann.githubapiapp.support

import android.app.Activity
import android.graphics.ColorFilter
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.Button
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.core.internal.deps.guava.collect.Iterables
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import br.com.gerencianet.support.drawable.DrawableMatcher
import com.rittmann.githubapiapp.support.CustomMatchers.hasChildren
import com.rittmann.githubapiapp.support.CustomMatchers.withHtml
import com.rittmann.githubapiapp.support.CustomViewActions.setTextInTextView
import com.rittmann.githubapiapp.support.WithIdMatcherConcat.withIdConcatened
import com.rittmann.githubapiapp.support.recyclerview.RecyclerViewItemCountAssertion
import com.rittmann.githubapiapp.support.recyclerview.TestUtils.withRecyclerView
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matchers
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.containsString


object ExpressoUtil {

    fun checkValue(id: Int, value: String?, withScroll: Boolean = false) {
        onView(allOf(withId(id), isCompletelyDisplayed())).apply {
            if (withScroll)
                perform(scrollTo())
        }.check(matches(withText(containsString(value))))
    }

    fun checkValue(id: Int, value: Int?, withScroll: Boolean = false) {
        checkValue(id, value.toString(), withScroll)
    }

    fun checkValueFromHtml(id: Int, value: String, withScroll: Boolean = false) {
        onView(withId(id)).inRoot(isDialog()).apply {
            if (withScroll)
                perform(scrollTo())
        }.check(matches(withHtml(value)))
    }

    fun checkValueOnDisplayed(value: String, withScroll: Boolean = false) {
        onView(allOf(withText(value), isCompletelyDisplayed())).apply {
            if (withScroll)
                perform(scrollTo())
        }.check(matches(isDisplayed()))
    }

    fun checkValue(value: String, withScroll: Boolean = false) {
        onView(allOf(withText(value))).apply {
            if (withScroll)
                perform(scrollTo())
        }.check(matches(isDisplayed()))
    }

    fun checkValueError(id: Int, value: String, withScroll: Boolean = false) {
        onView(withId(id)).check(matches(hasErrorText(value)))
    }

    fun checkValueAtRecycler(recyclerId: Int, targetId: Int, position: Int, value: String) {
        onView(withRecyclerView(recyclerId).atPositionOnView(position, targetId)).check(
            matches(withText(value))
        )
    }

    fun viewIsChecked(id: Int) {
        onView(withId(id)).check(matches(isChecked()))
    }

    fun viewIsNotChecked(id: Int) {
        onView(withId(id)).check(matches(not(isChecked())))
    }

    fun checkSizeFromRecycler(id: Int, size: Int) {
        onView(allOf(withId(id))).check(RecyclerViewItemCountAssertion(size))
    }

    fun performClick(id: Int, withScroll: Boolean = false) {
        onView(withId(id)).apply {
            if (withScroll)
                perform(scrollTo())
            perform(click())
        }
    }

    fun performClick(value: String, withScroll: Boolean = false) {
        onView(withText(value)).apply {
            if (withScroll)
                perform(scrollTo())
            perform(click())
        }
    }

    fun performClickRecycler(recyclerId: Int, position: Int) {
        onView(withRecyclerView(recyclerId).atPosition(position)).perform(click())
    }

    fun performClickByTag(tag: String, withScroll: Boolean = false) {
        onView(withTagValue(`is`(tag as Any))).apply {
            if (withScroll)
                perform(scrollTo())
            perform(click())
        }
    }

    fun putValue(id: Int, value: String, withScroll: Boolean = false) {
        onView(withId(id)).apply {
            if (withScroll)
                perform(scrollTo())
            perform(replaceText(value), closeSoftKeyboard())
        }
    }

    fun putValueTextView(id: Int, value: String) {
        onView(withId(id)).perform(setTextInTextView(value))
    }

    fun hasBackground(id: Int, resId: Int) {
        onView(allOf(withId(id), hasBackground(resId), isDisplayed()))
    }

    fun hasBackgroundByTag(tag: String, resId: Int) {
        onView(allOf(withTagValue(`is`(tag as Any)), hasBackground(resId), isDisplayed()))
    }

    fun childrenOnViewByTag(tag: String, count: Int, withScroll: Boolean = false) {
        onView(withTagValue(`is`(tag as Any))).apply {
            if (withScroll)
                perform(scrollTo())
        }.check(
            matches(
                allOf(
                    isDisplayed(),
                    hasChildren(`is`(count))
                )
            )
        )
    }

    fun scrollTo(id: Int) {
        onView(allOf(withId(id), isDisplayed())).perform(scrollTo())
    }

    fun viewIsDisplayed(id: Int, withScroll: Boolean = false) {
        onView(allOf(withId(id))).apply {
            if (withScroll)
                perform(scrollTo())
        }.check(matches(isDisplayed()))
    }

    fun viewIsCompletelyDisplayed(id: Int, withScroll: Boolean = false) {
        onView(allOf(withId(id), isCompletelyDisplayed())).apply {
            if (withScroll)
                perform(scrollTo())
        }.check(matches(isCompletelyDisplayed()))
    }

    fun viewIsNotDisplayed(id: Int, withScroll: Boolean = false) {
        onView(withId(id)).apply {
            if (withScroll)
                perform(scrollTo())
        }.check(matches(not(isDisplayed())))
    }

    fun viewDoesNotExists(value: String) {
        onView(withText(value)).check(doesNotExist())
    }

    fun isEnabled(id: Int) {
        onView(withId(id)).check(matches(isEnabled()))
    }

    fun isEnabled(id: Int, idConcat: Int) {
        onView(withIdConcatened(id, idConcat)).check(matches(isEnabled()))
    }

    fun isDisabled(id: Int) {
        onView(withId(id)).check(matches(not(isEnabled())))
    }

    fun checkInputType(id: Int, inputType: Int) {
        onView(withId(id)).check(matches(allOf(withInputType(inputType))))
    }

    fun checkInputTypeIsNot(id: Int, inputType: Int) {
        onView(withId(id)).check(matches(not(allOf(withInputType(inputType)))))
    }

    fun checkToast(value: String) {
        onView(withText(value)).inRoot(
            RootMatchers.withDecorView(
                Matchers.not(
                    getCurrentActivity()!!.window.decorView
                )
            )
        ).check(
            matches(
                isDisplayed()
            )
        )
    }

    fun scrollToBottom(resId: Int) {
        onView(withId(resId)).perform(CustomViewActions.ScrollToBottomAction())
    }

    fun withColor(id: Int, resId: Int, color: ColorFilter) {
        onView(allOf(withId(id), isCompletelyDisplayed())).check(
            matches(
                DrawableMatcher(
                    resId,
                    color,
                    object : DrawableMatcher.Extractor {
                        override fun extract(v: View?): Drawable? {
                            return v?.background
                        }
                    })
            )
        )
    }

    fun buttonWithColor(id: Int, resId: Int, color: ColorFilter) {
        onView(withId(id)).check(
            matches(
                DrawableMatcher(
                    resId,
                    color,
                    object : DrawableMatcher.Extractor {
                        override fun extract(v: View?): Drawable? {
                            val targetImageView: Button = v as Button
                            return targetImageView.background
                        }
                    })
            )
        )
    }

    fun checkHasTextColor(id: Int, color: Int) {
        onView(allOf(withId(id), isCompletelyDisplayed())).check(matches(hasTextColor(color)))
    }

    class ExecuteOn(private val callTime: Int) {
        var current = 1

        fun next(callback: () -> Unit) {
            if (current == callTime)
                callback()
            current++
        }
    }

    @Throws(Throwable::class)
    fun getCurrentActivity(): Activity? {
        getInstrumentation().waitForIdleSync()
        val activity = arrayOfNulls<Activity>(1)
        onView(isRoot()).check { _, _ ->
            val activities =
                ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED)
            activity[0] = Iterables.getOnlyElement(activities)
        }
        return activity[0]
    }

    fun pressBack() {
        onView(isRoot()).perform(ViewActions.pressBack())
    }
}