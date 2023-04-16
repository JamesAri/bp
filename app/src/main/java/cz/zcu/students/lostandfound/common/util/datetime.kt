package cz.zcu.students.lostandfound.common.util

import java.text.SimpleDateFormat
import java.util.*

private const val SECONDS_MONTH = 2419200
private const val SECONDS_WEEK = 604800
private const val SECONDS_DAY = 86400
private const val SECONDS_HOUR = 3600
private const val SECONDS_MINUTE = 60

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

fun getFormattedDateString(timestamp: Long): String? {
    return try {
        val now = System.currentTimeMillis().toSeconds()
        val diff = now - timestamp
        if (diff < SECONDS_MINUTE) {
            return "Few moments ago"
        }
        if (diff < SECONDS_HOUR) {
            val minutes = diff.toMinutes()
            return if (minutes > 1)
                "$minutes minutes ago"
            else
                "A minute ago"
        }
        if (diff < SECONDS_DAY) {
            val hours = diff.toHours()
            return if (hours > 1)
                "$hours hours ago"
            else
                "A hour ago"
        }
        if (diff < SECONDS_WEEK) {
            val days = diff.toDays()
            return if (days > 1)
                "$days days ago"
            else
                "A day ago"
        }
        if (diff < SECONDS_MONTH) {
            val weeks = diff.toWeeks()
            return if (weeks > 1)
                "$weeks weeks ago"
            else
                "A week ago"
        }
        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.US)
        val date = Date(timestamp.toMillis())
        formatter.format(date)
    } catch (e: Exception) {
        "an error occurred"
    }
}