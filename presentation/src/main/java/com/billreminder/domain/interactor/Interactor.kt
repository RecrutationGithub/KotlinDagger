package com.billreminder.domain.interactor

import io.reactivex.Observable

interface Interactor<T> {
    fun execute(): Observable<T>
}