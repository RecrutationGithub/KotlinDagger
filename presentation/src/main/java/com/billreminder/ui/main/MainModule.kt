package com.billreminder.ui.main

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides

@Module
class MainModule {
    @Provides
    fun provideMainViewModelFactory(factory: MainViewModelFactory): ViewModelProvider.Factory = factory
}