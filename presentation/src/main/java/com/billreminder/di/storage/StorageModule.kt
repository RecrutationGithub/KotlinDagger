package com.billreminder.di.storage

import com.billreminder.data.storage.BillsLocalStorageImpl
import com.billreminder.data.storage.RemindersLocalStorageImpl
import com.billreminder.domain.storage.BillsLocalStorage
import com.billreminder.domain.storage.RemindersLocalStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

//TODO DELETED FOR PRIVACY

@Module
class StorageModule {
    @Provides
    @Singleton
    fun provideBillsLocalStorage(): BillsLocalStorage = BillsLocalStorageImpl()

    @Provides
    @Singleton
    fun provideRemindersLocalStorage(): RemindersLocalStorage = RemindersLocalStorageImpl()
}