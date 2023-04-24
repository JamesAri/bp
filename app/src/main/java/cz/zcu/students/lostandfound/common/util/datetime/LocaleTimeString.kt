package cz.zcu.students.lostandfound.common.util.datetime

/** Interface for date time utilities respecting current locale settings. */
interface LocaleTimeString {
    /**
     * Get string representing "moment ago" meaning.
     *
     * @return date/time string.
     */
    fun getMomentAgo(): String

    /**
     * Get string representing "minute ago" meaning.
     *
     * @return date/time string.
     */
    fun getMinuteAgo(): String

    /**
     * Get string representing "[minutes] ago" meaning.
     *
     * @param minutes
     * @return date/time string.
     */
    fun getMinutesAgo(minutes: Any): String

    /**
     * Get string representing "hour ago" meaning.
     *
     * @return date/time string.
     */
    fun getHourAgo(): String

    /**
     * Get string representing "[hours] ago" meaning.
     *
     * @param hours
     * @return date/time string.
     */
    fun getHoursAgo(hours: Any): String

    /**
     * Get string representing "day ago" meaning.
     *
     * @return date/time string.
     */
    fun getDayAgo(): String

    /**
     * Get string representing "[days] ago" meaning.
     *
     * @param days
     * @return date/time string.
     */
    fun getDaysAgo(days: Any): String

    /**
     * Get string representing "week ago" meaning.
     *
     * @return date/time string.
     */
    fun getWeekAgo(): String

    /**
     * Get string representing "[weeks] ago" meaning.
     *
     * @param weeks
     * @return date/time string.
     */
    fun getWeeksAgo(weeks: Any): String
}