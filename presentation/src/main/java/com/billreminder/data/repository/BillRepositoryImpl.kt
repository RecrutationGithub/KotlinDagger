package com.billreminder.data.repository

import com.billreminder.data.model.Bill
import com.billreminder.domain.repository.bill.BillCreationData
import com.billreminder.domain.repository.bill.BillCriteria
import com.billreminder.domain.repository.bill.BillsRepository
import io.reactivex.Observable

class BillsRepositoryImpl : BillsRepository {

    override fun delete(id: Int): Observable<Int> = Observable.empty()

    override fun update(data: BillCreationData): Observable<Bill> = Observable.empty()

    override fun fetch(criteria: BillCriteria): Observable<List<Bill>> = Observable.empty()

    override fun add(data: BillCreationData): Observable<Bill> = Observable.empty()

    override fun hasCachedData() = false

    override fun clearCache() {
    }
}