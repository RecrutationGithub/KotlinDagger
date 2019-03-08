package com.billreminder.domain.repository.bill

import com.billreminder.data.model.Bill
import com.billreminder.domain.repository.Repository

data class BillCreationData(val bill: Bill) : Repository.CreationData