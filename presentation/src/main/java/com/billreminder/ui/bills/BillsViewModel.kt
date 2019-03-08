package com.billreminder.ui.bills

import androidx.databinding.ObservableBoolean
import com.billreminder.R
import com.billreminder.ui.base.event.DataSetChangedEvent
import com.billreminder.ui.base.event.TextMessageEvent
import com.billreminder.ui.base.model.BaseViewModel
import com.billreminder.ui.base.model.RowViewModel
import com.billreminder.ui.bills.events.DeleteBillEvent
import com.billreminder.ui.bills.events.ShowDetailsEvent
import com.billreminder.data.converter.Converter
import com.billreminder.domain.interactor.bill.DeleteBillInteractor
import com.billreminder.domain.interactor.bill.FetchBillsInteractor
import com.billreminder.data.model.Bill
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject
import javax.inject.Named

class BillsViewModel @Inject constructor() : BaseViewModel(), BillRowViewModel.ActionHandler {
    @Inject
    lateinit var fetchBillsInteractor: FetchBillsInteractor

    @Inject
    lateinit var deleteBillInteractor: DeleteBillInteractor

    @field:[Inject Named("date")]
    lateinit var dateConverter: Converter<Date, String>

    @field:[Inject Named("amount")]
    lateinit var amountConverter: Converter<Int, String>

    val bills = arrayListOf<RowViewModel>()
    val refreshingInProgress = ObservableBoolean()

    val dataSetChangedEvent = DataSetChangedEvent()
    val errorMessageEvent = TextMessageEvent()
    val deleteEvent = DeleteBillEvent()
    val showDetailsEvent = ShowDetailsEvent()

    var billsMap = mutableMapOf<Int, BillRowViewModel>()

    private fun handleBillsFetchError() = errorMessageEvent.call(R.string.errorCannotFetchBills)

    private fun handleDeletionError() = errorMessageEvent.call(R.string.errorCannotDeleteBill)

    private fun handleBillsFetched(rows: MutableList<BillRowViewModel>) {
        bills.clear()
        bills.addAll(rows.sortedWith(compareBy { it.name.get() }))

        dataSetChangedEvent.call(0, bills.size)
    }

    private fun handleBillDeleted(bill: Bill) {
        for (i in 0 until bills.size) {
            (bills[i] as? BillRowViewModel?)?.let {
                if (bill == it.bill) {
                    bills.remove(it)
                    dataSetChangedEvent.call(i, i)
                    return
                }
            }
        }
    }

    fun delete(bill: Bill) {
        val d = deleteBillInteractor.execute(DeleteBillInteractor.Parameters(bill))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { billsMap[bill.id]?.enabled?.set(false) }
                .doFinally { billsMap[bill.id]?.enabled?.set(true) }
                .subscribe(
                        { handleBillDeleted(bill) },
                        { handleDeletionError() }
                )

        registerDisposable(d)
    }

    override fun onDeleteRequest(bill: Bill) {
        deleteEvent.call(bill)
    }

    override fun onShowDetails(bill: Bill) {
        showDetailsEvent.call(bill)
    }

    fun fetchBills() {
        fetchBillsInteractor.clearCache()

        val d = fetchBillsInteractor.execute()
                .flatMapIterable { it }
                .map { b -> BillRowViewModel(b, dateConverter, amountConverter, this) }
                .doOnNext { b -> billsMap[b.bill.id] = b }
                .toList()
                .doOnSubscribe { billsMap = mutableMapOf() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { refreshingInProgress.set(true) }
                .doFinally { refreshingInProgress.set(false) }
                .subscribe(
                        { handleBillsFetched(it) },
                        { handleBillsFetchError() }
                )

        registerDisposable(d)
    }
}