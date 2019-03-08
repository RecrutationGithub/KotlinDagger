package com.billreminder.di

import com.billreminder.ui.bills.BillsFragment
import com.billreminder.ui.bills.BillsModule
import com.billreminder.ui.dashboard.DashboardFragment
import com.billreminder.ui.dashboard.DashboardModule
import com.billreminder.ui.settings.SettingsFragment
import com.billreminder.ui.settings.SettingsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {
    @ContributesAndroidInjector(modules = [BillsModule::class])
    abstract fun bindBillsFragment(): BillsFragment

    @ContributesAndroidInjector(modules = [DashboardModule::class])
    abstract fun bindDashboardFragment(): DashboardFragment

    @ContributesAndroidInjector(modules = [SettingsModule::class])
    abstract fun bindSettingsFragment(): SettingsFragment
}