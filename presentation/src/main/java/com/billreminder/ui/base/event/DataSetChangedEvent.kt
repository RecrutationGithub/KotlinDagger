package com.billreminder.ui.base.event

class DataSetChangedEvent : SingleLiveEvent<Pair<Int, Int>>() {
    fun call(start: Int, end: Int) {
        value = Pair(start, end)
    }
}