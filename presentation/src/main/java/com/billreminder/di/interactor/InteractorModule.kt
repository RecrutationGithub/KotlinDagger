package com.billreminder.di.interactor

import com.billreminder.domain.interactor.bill.*
import com.billreminder.domain.repository.bill.BillsRepository
import com.billreminder.domain.storage.BillsLocalStorage
import com.billreminder.domain.storage.RemindersLocalStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class InteractorModule {
    @Singleton
    @Provides
    fun provideFetchBillsInteractor(billsRepository: BillsRepository,
                                    billsLocalStorage: BillsLocalStorage,
                                    remindersLocalStorage: RemindersLocalStorage,
                                    extractRemindersInteractor: ExtractRemindersInteractor): FetchBillsInteractor =
            FetchBillsInteractor(billsRepository, billsLocalStorage, remindersLocalStorage,
                    extractRemindersInteractor)

    @Singleton
    @Provides
    fun provideDeleteBillInteractor(billsRepository: BillsRepository): DeleteBillInteractor =
            DeleteBillInteractor(billsRepository)

    @Singleton
    @Provides
    fun provideExtractRemindersInteractor(): ExtractRemindersInteractor = ExtractRemindersInteractor()
}