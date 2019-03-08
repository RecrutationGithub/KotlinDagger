package com.billreminder.domain.repository.bill

import com.billreminder.domain.repository.Repository

data class BillCriteria(val id: Int?) : Repository.Criteria {
    companion object {
        fun empty(): BillCriteria = BillCriteria(null)
    }
}