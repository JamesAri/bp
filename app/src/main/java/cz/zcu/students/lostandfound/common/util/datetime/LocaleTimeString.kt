package cz.zcu.students.lostandfound.common.util.datetime

interface LocaleTimeString {
    fun getMomentAgo(): String
    fun getMinuteAgo(): String
    fun getMinutesAgo(minutes: Any): String
    fun getHourAgo(): String
    fun getHoursAgo(hours: Any): String
    fun getDayAgo(): String
    fun getDaysAgo(days: Any): String
    fun getWeekAgo(): String
    fun getWeeksAgo(weeks: Any): String
}