package com.rittmann.githubapiapp.utils

import android.annotation.SuppressLint
import com.rittmann.androidtools.dateutil.DateUtil
import java.text.SimpleDateFormat
import java.util.*

object DateUtils : DateUtil {
    const val REPRESENTATIVE_FORMAT = "dd 'de' MMM yyyy, HH:mm:ss"
    const val API_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    override fun setPattern(format: String) {
    }

    override fun today(): Calendar {
        return Calendar.getInstance()
    }

    @SuppressLint("SimpleDateFormat")
    fun parse(
        date: String?,
        formatFrom: String? = API_DATE_FORMAT,
        formatTo: String? = REPRESENTATIVE_FORMAT
    ): String {
        if (date == null) return ""
        val d = SimpleDateFormat(formatFrom).parse(date) ?: return ""
        return SimpleDateFormat(formatTo).format(d)
    }
}