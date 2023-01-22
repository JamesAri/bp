package cz.zcu.students.lostandfound.features.lost_items.domain.validators

interface SearchTermValidator {
    fun validate(term: String): Boolean
}