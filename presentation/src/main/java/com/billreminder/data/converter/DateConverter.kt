package com.billreminder.data.converter

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import java.util.*

class DateConverter(dateFormat: String) : Converter<Date, String> {
    private val formatter: DateTimeFormatter = DateTimeFormat.forPattern(dateFormat)

    override fun convert(input: Date?): String {
        if (input == null) {
            return ""
        }

        return formatter.print(DateTime(input))
    }
}