package com.billreminder.ui.bills

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class BillsViewModelFactory @Inject constructor(private val viewModel: BillsViewModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            if (modelClass.isAssignableFrom(BillsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                viewModel as T
            } else {
                throw IllegalArgumentException("Unknown class name")
            }
}