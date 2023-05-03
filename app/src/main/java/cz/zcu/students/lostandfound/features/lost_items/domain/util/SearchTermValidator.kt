package cz.zcu.students.lostandfound.features.lost_items.domain.util

import cz.zcu.students.lostandfound.common.constants.Validations.Companion.MAX_SEARCH_TERM_LENGTH

/**
 * Search term validation for lost item title and description full text
 * queries.
 */
class SearchTermValidator {
    /**
     * Validates if search term is valid.
     *
     * Valid terms are non-empty strings, which do not exceed
     * [MAX_SEARCH_TERM_LENGTH] length and which do not have new line at the
     * end of the string.
     *
     * @param term to validate.
     * @return `true` if valid, `false` otherwise.
     */
    fun validate(term: String): Boolean {
        val hasCorrectLength = (term.length <= MAX_SEARCH_TERM_LENGTH) and term.isNotEmpty()
        val hasNewLine = ("\n" in term)
        return hasCorrectLength and !hasNewLine
    }
}