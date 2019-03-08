package com.billreminder.data.model

import java.io.Serializable
import java.util.*

data class Reminder(val billId: Int, val date: Date) : Serializable