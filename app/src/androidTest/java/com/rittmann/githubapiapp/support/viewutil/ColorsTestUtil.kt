package com.rittmann.githubapiapp.support.viewutil

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import androidx.core.content.ContextCompat

object ColorsTestUtil {
    fun getColor(context:Context, color: Int) = PorterDuffColorFilter(
            ContextCompat.getColor(context, color),
            PorterDuff.Mode.SRC_IN
    )
}