package com.billreminder.di

import android.app.Application
import com.billreminder.BillReminderApplication
import com.billreminder.di.base.ConverterModule
import com.billreminder.di.interactor.InteractorModule
import com.billreminder.di.repository.RepositoryModule
import com.billreminder.di.storage.StorageModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
        AndroidInjectionModule::class, ActivityBuilder::class, FragmentBuilder::class,
        InteractorModule::class, RepositoryModule::class, StorageModule::class, ConverterModule::class,
        AppModule::class
    ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun inject(application: BillReminderApplication)
}