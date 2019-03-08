package com.billreminder.domain.repository.bill

import com.billreminder.data.model.Bill
import com.billreminder.domain.repository.Repository
import io.reactivex.Observable

interface BillsRepository : Repository<Bill, BillCriteria, BillCreationData> {
    fun delete(id: Int): Observable<Int>

    fun update(data: BillCreationData): Observable<Bill>
}