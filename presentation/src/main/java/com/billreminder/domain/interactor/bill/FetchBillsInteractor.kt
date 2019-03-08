package com.billreminder.domain.interactor.bill

import com.billreminder.domain.interactor.Interactor
import com.billreminder.data.model.Bill
import com.billreminder.data.model.Reminder
import com.billreminder.domain.repository.bill.BillCriteria
import com.billreminder.domain.repository.bill.BillsRepository
import com.billreminder.domain.storage.BillsLocalStorage
import com.billreminder.domain.storage.RemindersLocalStorage
import io.reactivex.Observable
import org.joda.time.DateTime

open class FetchBillsInteractor(private val billsRepository: BillsRepository,
                                private val billsLocalStorage: BillsLocalStorage,
                                private val remindersLocalStorage: RemindersLocalStorage,
                                private val extractRemindersInteractor: ExtractRemindersInteractor) : Interactor<List<Bill>> {
    override fun execute(): Observable<List<Bill>> =
            billsRepository.fetch(BillCriteria.empty())
                    .doOnNext {
                        billsLocalStorage.deleteAll()
                        billsLocalStorage.insertAll(it)
                    }
                    .flatMapIterable { it }
                    .flatMap { bill ->
                        extractRemindersInteractor.execute(
                                ExtractRemindersInteractor.Parameters(
                                        bill, DateTime.now().monthOfYear, DateTime.now().year))
                                .map { Pair(bill, it) }
                    }
                    .map { Reminder(it.first.id, it.second.toDate()) }
                    .toList()
                    .map { remindersLocalStorage.insertAll(it) }
                    .map { billsLocalStorage.getAll() }
                    .toObservable()
                    .onErrorReturn { billsLocalStorage.getAll() }

    fun hasCachedData() = billsRepository.hasCachedData()

    fun clearCache() = billsRepository.clearCache()
}