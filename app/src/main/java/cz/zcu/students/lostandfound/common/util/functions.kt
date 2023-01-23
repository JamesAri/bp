package cz.zcu.students.lostandfound.common.util

fun anyStringsContainsTargets(terms: List<String>, targets: List<String>): Boolean {
    return targets.any { target ->
        terms.any { term ->
            term in target
        }
    }
}