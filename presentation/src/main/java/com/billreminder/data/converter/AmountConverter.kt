package com.billreminder.data.converter

import java.text.NumberFormat

class AmountConverter : Converter<Int, String> {
    private var formatter: NumberFormat = NumberFormat.getCurrencyInstance()

    override fun convert(input: Int?): String {
        if (input == null) {
            return ""
        }

        return formatter.format(input.toDouble() / 100.0)
    }
}