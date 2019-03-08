package com.billreminder.domain.interactor.bill

import com.billreminder.domain.interactor.ParametrizedInteractor
import com.billreminder.data.model.Bill
import com.billreminder.data.model.RepeatMode
import io.reactivex.Observable
import org.joda.time.*

open class ExtractRemindersInteractor : ParametrizedInteractor<DateTime, ExtractRemindersInteractor.Parameters> {
    data class Parameters(val bill: Bill, val monthOfYear: Int, val year: Int) : ParametrizedInteractor.Params

    /**
     * Computes difference between two dates using a unit determined by repeat mode.
     */
    private fun computeDiff(dt1: DateTime, dt2: DateTime, repeatMode: RepeatMode): Int {
        val diff = when (repeatMode) {
            RepeatMode.Day -> Days.daysBetween(dt1, dt2).days
            RepeatMode.Week -> Weeks.weeksBetween(dt1, dt2).weeks
            RepeatMode.Month -> Months.monthsBetween(dt1, dt2).months
            RepeatMode.Year -> Years.yearsBetween(dt1, dt2).years
        }

        return Math.abs(diff)
    }

    /**
     * Increments given date by a specified value using a unit determined by repeat mode.
     */
    private fun increment(dt: DateTime, mode: RepeatMode, value: Int): DateTime {
        if (value == 0) {
            return dt
        }

        return when (mode) {
            RepeatMode.Day -> dt.plusDays(value)
            RepeatMode.Week -> dt.plusWeeks(value)
            RepeatMode.Month -> dt.plusMonths(value)
            RepeatMode.Year -> dt.plusYears(value)
        }
    }

    internal fun determineDueDatesForMonthAndYear(reminderStart: DateTime, mode: RepeatMode,
                                                  repeatValue: Int, monthOfYear: Int, year: Int): List<DateTime> {
        val results = mutableListOf<DateTime>()

        if (reminderStart.year > year) {
            return results
        }

        val beginOfMonth = DateTime(year, monthOfYear, 1, 0, 0)
        var current = DateTime(year, reminderStart.monthOfYear,
                reminderStart.dayOfMonth, 0, 0)

        if (reminderStart.monthOfYear != monthOfYear) {
            val diff = computeDiff(beginOfMonth, reminderStart, mode)
            val dt = diff / repeatValue

            current = increment(reminderStart, mode, dt * repeatValue)
            if (current.monthOfYear < monthOfYear) {
                current = increment(current, mode, repeatValue)
            }
        }

        while (current.monthOfYear == monthOfYear && current.year <= year) {
            results.add(current)
            current = increment(current, mode, repeatValue)
        }

        return results
    }

    override fun execute(params: Parameters): Observable<DateTime> {
        val dates = mutableListOf<DateTime>()
        if (params.bill.startDate == null || params.bill.value == null || params.bill.unit == null) {
            return Observable.fromIterable(dates)
        }

        return Observable.just(params.bill)
                .map { b ->
                    determineDueDatesForMonthAndYear(DateTime(b.startDate?.time),
                            b.unit ?: RepeatMode.Month,
                            b.value ?: 1, params.monthOfYear, params.year) }
                .flatMapIterable { it }
                .sorted()
    }
}