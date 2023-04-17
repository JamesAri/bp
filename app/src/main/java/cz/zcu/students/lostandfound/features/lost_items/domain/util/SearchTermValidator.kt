package cz.zcu.students.lostandfound.features.lost_items.domain.util

interface SearchTermValidator {
    fun validate(term: String): Boolean
}