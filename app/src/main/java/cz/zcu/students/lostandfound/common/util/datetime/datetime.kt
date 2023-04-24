package cz.zcu.students.lostandfound.common.util.datetime

import java.text.SimpleDateFormat
import java.util.*

// time units
private const val SECONDS_MONTH = 2419200
private const val SECONDS_WEEK = 604800
private const val SECONDS_DAY = 86400
private const val SECONDS_HOUR = 3600
private const val SECONDS_MINUTE = 60

// helper extensions
private fun Long.toMillis(): Long {
    return this * 1000
}

private fun Long.toSeconds(): Long {
    return this / 1000
}

private fun Long.toWeeks(): Long {
    return this / SECONDS_WEEK
}

private fun Long.toDays(): Long {
    return this / SECONDS_DAY
}

private fun Long.toHours(): Long {
    return this / SECONDS_HOUR
}

private fun Long.toMinutes(): Long {
    return this / SECONDS_MINUTE
}

/**
 * Gets formatted date time from [timestamp] as string, based on the passed
 * [LocaleTimeString].
 *
 * @param timestamp unix timestamp (since January 1, 1970 UTC).
 * @param localeTimeString time utility for formatting time respecting locale settings.
 * @return formatted date as string, representing event in time.
 */
fun getFormattedDateString(timestamp: Long, localeTimeString: LocaleTimeString): String? {
    return try {
        val now = System.currentTimeMillis().toSeconds()
        val diff = now - timestamp
        if (diff < SECONDS_MINUTE) {
            return localeTimeString.getMomentAgo()
        }
        if (diff < SECONDS_HOUR) {
            val minutes = diff.toMinutes()
            return if (minutes > 1)
                localeTimeString.getMinutesAgo(minutes)
            else
                localeTimeString.getMinuteAgo()
        }
        if (diff < SECONDS_DAY) {
            val hours = diff.toHours()
            return if (hours > 1)
                localeTimeString.getHoursAgo(hours)
            else
                localeTimeString.getHourAgo()
        }
        if (diff < SECONDS_WEEK) {
            val days = diff.toDays()
            return if (days > 1)
                localeTimeString.getDaysAgo(days)
            else
                localeTimeString.getDayAgo()
        }
        if (diff < SECONDS_MONTH) {
            val weeks = diff.toWeeks()
            return if (weeks > 1)
                localeTimeString.getWeeksAgo(weeks)
            else
                localeTimeString.getWeekAgo()
        }
        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.US)
        val date = Date(timestamp.toMillis())
        formatter.format(date)
    } catch (e: Exception) {
        "an error occurred"
    }
}