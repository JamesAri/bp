package cz.zcu.students.lostandfound.features.lost_items.data.validators

import cz.zcu.students.lostandfound.common.constants.Validations.Companion.MAX_SEARCH_TERM_LENGTH
import cz.zcu.students.lostandfound.features.lost_items.domain.validators.SearchTermValidator

class SearchTermValidatorImpl: SearchTermValidator {
    override fun validate(term: String): Boolean {
        return (term.length <= MAX_SEARCH_TERM_LENGTH) and ("\n" !in term)
    }
}