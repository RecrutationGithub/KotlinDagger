package com.billreminder.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import javax.inject.Named
import javax.inject.Singleton

private const val ISO_8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
private const val MONTH_DATE_FORMAT = "dd MMMM"
private const val DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm"

@Module
class AppModule {
    @Provides
    @Singleton
    @Named("defaultDateFormat")
    fun provideDefaultDateFormat() = ISO_8601_DATE_FORMAT

    @Provides
    @Singleton
    @Named("month")
    fun provideMonthDateFormat() = MONTH_DATE_FORMAT

    @Provides
    @Singleton
    @Named("date_time")
    fun provideDateTimeFormat() = DATE_TIME_FORMAT

    @Provides
    @Singleton
    @Named("iso8601DateFormatter")
    fun provideIso8601DateFormatter(@Named("defaultDateFormat") dateFormat: String): DateTimeFormatter =
        DateTimeFormat.forPattern(dateFormat)

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }
}