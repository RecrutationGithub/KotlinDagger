package com.billreminder.domain.interactor.bill

import com.billreminder.domain.interactor.ParametrizedInteractor
import com.billreminder.data.model.Bill
import com.billreminder.domain.repository.bill.BillsRepository

open class DeleteBillInteractor(private val repository: BillsRepository) : ParametrizedInteractor<Int, DeleteBillInteractor.Parameters> {
    data class Parameters(val bill: Bill) : ParametrizedInteractor.Params

    override fun execute(params: Parameters) = repository.delete(params.bill.id)
}