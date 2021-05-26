package com.rittmann.githubapiapp.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class DateUtilsTest {

    @Test
    fun date() {
        val date = DateUtils.parse("2017-02-16T02:10:13Z")
        assertEquals("16 de fev 2017, 02:10:13", date)
    }
}