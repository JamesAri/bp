package cz.zcu.students.lostandfound.features.profile.presentation.util

import cz.zcu.students.lostandfound.common.constants.Validations.Companion.MAX_PHONE_NUMBER_LENGTH


/**
 * Validates if input string [value] contains only numbers and is not null or empty.
 *
 * The length limit is defined by [MAX_PHONE_NUMBER_LENGTH].
 *
 * Future application should validate the phone number based on the user's
 * provider location.
 */
fun validatePhoneNumber(
    value: String?,
): Boolean {
    if (value.isNullOrBlank() || value.length > MAX_PHONE_NUMBER_LENGTH) return false
    return value.all { char -> char.isDigit() }
}