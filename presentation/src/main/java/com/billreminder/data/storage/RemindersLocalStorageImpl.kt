package com.billreminder.data.storage

import com.billreminder.data.model.Reminder
import com.billreminder.domain.storage.RemindersLocalStorage

//TODO DELETED FOR PRIVACY

class RemindersLocalStorageImpl : RemindersLocalStorage {
    override fun getAll(): List<Reminder> = emptyList()

    override fun insertAll(reminders: List<Reminder>) {
    }

    override fun deleteAll() {
    }
}