package com.billreminder.di.base

import com.billreminder.data.converter.AmountConverter
import com.billreminder.data.converter.Converter
import com.billreminder.data.converter.DateConverter
import dagger.Module
import dagger.Provides
import java.util.*
import javax.inject.Named
import javax.inject.Singleton

@Module
class ConverterModule {
    @Provides
    @Singleton
    @Named("date")
    fun provideDateConverter(@Named("date_time") dateFormat: String): Converter<Date, String> = DateConverter(dateFormat)

    @Provides
    @Singleton
    @Named("amount")
    fun provideAmountConverter(): Converter<Int, String> = AmountConverter()
}