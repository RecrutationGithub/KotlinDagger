package com.billreminder.data.storage

import com.billreminder.data.model.Bill
import com.billreminder.domain.storage.BillsLocalStorage

//TODO DELETED FOR PRIVACY

class BillsLocalStorageImpl : BillsLocalStorage {
    override fun getAll(): List<Bill> = emptyList()

    override fun insertAll(bills: List<Bill>) {
    }

    override fun deleteAll() {
    }

}