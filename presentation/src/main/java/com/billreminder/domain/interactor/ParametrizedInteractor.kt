package com.billreminder.domain.interactor

import io.reactivex.Observable

interface ParametrizedInteractor<T, in P : ParametrizedInteractor.Params> {
    interface Params

    fun execute(params: P): Observable<T>
}