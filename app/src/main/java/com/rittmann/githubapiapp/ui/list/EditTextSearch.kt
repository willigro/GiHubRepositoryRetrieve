package com.rittmann.githubapiapp.ui.list

import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import java.util.*
import kotlin.concurrent.schedule

class EditTextSearch(
    editText: EditText, val delay: Long = DELAY, val callback: (String) -> Unit
) {
    private var timer: Timer? = null

    var onStart: (() -> Unit)? = null

    init {
        editText.doAfterTextChanged { value ->
            timer = newTimer()
            timer?.schedule(delay) {
                callback(value.toString())
            }
        }
    }

    private fun newTimer(): Timer {
        timer?.cancel()
        return Timer("Timer to update content", false).also {
            onStart?.invoke()
        }
    }

    companion object {
        const val DELAY = 600L
    }
}