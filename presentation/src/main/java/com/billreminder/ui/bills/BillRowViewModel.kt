package com.billreminder.ui.bills

import androidx.databinding.ObservableBoolean
import com.billreminder.ui.base.binding.ObservableString
import com.billreminder.ui.base.model.BaseViewModel
import com.billreminder.ui.base.model.RowViewModel
import com.billreminder.data.converter.Converter
import com.billreminder.data.model.Bill
import java.util.*

open class BillRowViewModel(val bill: Bill,
                            dateConverter: Converter<Date, String>,
                            amountConverter: Converter<Int, String>,
                            private val handler: ActionHandler?) : BaseViewModel(), RowViewModel {
    interface ActionHandler {
        fun onDeleteRequest(bill: Bill)

        fun onShowDetails(bill: Bill)
    }

    val name = ObservableString()
    val description = ObservableString()
    val amount = ObservableString()
    val lastPayment = ObservableString()

    val notPaidYetVisible = ObservableBoolean()
    val enabled = ObservableBoolean(true)

    init {
        name.set(bill.name)
        description.set(bill.description)
        amount.set(amountConverter.convert(bill.amount))
        lastPayment.set(dateConverter.convert(bill.lastPayment))

        notPaidYetVisible.set(lastPayment.get().isNullOrEmpty())
    }

    open fun delete() {
        handler?.let { handler.onDeleteRequest(bill) }
    }

    open fun showDetails() {
        handler?.let { handler.onShowDetails(bill) }
    }
}