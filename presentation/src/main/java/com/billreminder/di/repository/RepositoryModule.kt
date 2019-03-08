package com.billreminder.di.repository

import com.billreminder.data.repository.BillsRepositoryImpl
import com.billreminder.domain.repository.bill.BillsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

//TODO DELETED FOR PRIVACY

@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideBillsRepository(): BillsRepository {
        return BillsRepositoryImpl()
    }
}