package cz.zcu.students.lostandfound.features.lost_items.domain.util

import cz.zcu.students.lostandfound.common.constants.Validations

class SearchTermValidator {
    fun validate(term: String): Boolean {
        val hasCorrectLength = (term.length <= Validations.MAX_SEARCH_TERM_LENGTH) and term.isNotEmpty()
        val hasNewLine = ("\n" in term)
        return hasCorrectLength and !hasNewLine
    }
}