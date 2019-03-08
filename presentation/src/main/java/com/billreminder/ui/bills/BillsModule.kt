package com.billreminder.ui.bills

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides

@Module
class BillsModule {
    @Provides
    fun provideBillsViewModelFactory(factory: BillsViewModelFactory): ViewModelProvider.Factory = factory
}