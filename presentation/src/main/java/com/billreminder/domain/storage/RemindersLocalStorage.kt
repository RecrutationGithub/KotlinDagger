package com.billreminder.domain.storage

import com.billreminder.data.model.Reminder

interface RemindersLocalStorage {
    fun getAll(): List<Reminder>

    fun insertAll(reminders: List<Reminder>)

    fun deleteAll()
}