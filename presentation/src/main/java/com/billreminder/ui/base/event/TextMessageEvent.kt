package com.billreminder.ui.base.event

class TextMessageEvent : SingleLiveEvent<Int>() {
    fun call(stringId: Int) {
        value = stringId
    }
}