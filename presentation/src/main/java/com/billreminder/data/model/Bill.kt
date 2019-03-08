package com.billreminder.data.model

import java.io.Serializable
import java.util.*

data class Bill(
        val id: Int,
        val amount: Int,
        val name: String,
        val description: String? = null,
        val lastPayment: Date? = null,
        val startDate: Date? = null,
        val unit: RepeatMode?,
        val value: Int?
) : Serializable