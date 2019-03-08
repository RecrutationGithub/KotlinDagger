package com.billreminder.ui.bills.events

import com.billreminder.ui.base.event.SingleLiveEvent
import com.billreminder.data.model.Bill

class ShowDetailsEvent : SingleLiveEvent<Bill>() {
    fun call(bill: Bill) {
        value = bill
    }
}