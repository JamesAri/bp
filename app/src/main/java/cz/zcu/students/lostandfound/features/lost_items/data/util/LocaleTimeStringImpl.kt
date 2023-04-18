package cz.zcu.students.lostandfound.features.lost_items.data.util

import android.content.Context
import cz.zcu.students.lostandfound.R
import cz.zcu.students.lostandfound.common.util.datetime.LocaleTimeString

class LocaleTimeStringImpl(val context: Context) : LocaleTimeString {
    override fun getMomentAgo(): String {
        return context.getString(R.string.locale_time_string_few_moments_ago)
    }

    override fun getMinuteAgo(): String {
        return context.getString(R.string.locale_time_string_minute_ago)
    }

    override fun getMinutesAgo(minutes: Any): String {
        return context.getString(R.string.locale_time_string_minutes_ago, minutes)
    }

    override fun getHourAgo(): String {
        return context.getString(R.string.locale_time_string_hour_ago)
    }

    override fun getHoursAgo(hours: Any): String {
        return context.getString(R.string.locale_time_string_hours_ago, hours)
    }

    override fun getDayAgo(): String {
        return context.getString(R.string.locale_time_string_day_ago)
    }

    override fun getDaysAgo(days: Any): String {
        return context.getString(R.string.locale_time_string_days_ago, days)
    }

    override fun getWeekAgo(): String {
        return context.getString(R.string.locale_time_string_week_ago)
    }

    override fun getWeeksAgo(weeks: Any): String {
        return context.getString(R.string.locale_time_string_weeks_ago, weeks)
    }
}