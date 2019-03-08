package com.billreminder.domain.storage

import com.billreminder.data.model.Bill

interface BillsLocalStorage {
    fun getAll(): List<Bill>

    fun insertAll(bills: List<Bill>)

    fun deleteAll()
}