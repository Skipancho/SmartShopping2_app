package com.jjsh.smartshopping.common

import java.util.*

object DateUtil {
    fun month() : Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.MONTH) + 1
    }

    fun year() : Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.YEAR)
    }
}