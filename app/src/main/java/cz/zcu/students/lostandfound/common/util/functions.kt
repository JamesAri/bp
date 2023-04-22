package cz.zcu.students.lostandfound.common.util

fun fullTextSearch(terms: List<String>, targets: List<String>): Boolean {
    return targets.any { target ->
        terms.any { term ->
            term.lowercase() in target.lowercase()
        }
    }
}