package com.anazumk.baseapp.utils

import  android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateParser {

    enum class FormatType(val pattern: String){
        DayMonthYearSlashed("dd/MM/YY"),
        HourMinute("HH:mm")
    }

    fun format(date: Date, format: FormatType): String {
        val locale = Locale.getDefault()
        val localePattern = DateFormat.getBestDateTimePattern(locale, format.pattern)

        return SimpleDateFormat(localePattern, locale).format(date) ?: ""
    }
}