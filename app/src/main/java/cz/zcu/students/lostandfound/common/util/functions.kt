package cz.zcu.students.lostandfound.common.util

/**
 * Function for case-insensitive fulltext search. Checks whether [terms]
 * contain any string which matches with some string in [targets].
 *
 * @param terms terms to look for.
 * @param targets strings which we want to match with some term from [terms].
 * @return `true` if there was term from [terms] in [targets], otherwise `false`.
 */
fun fullTextSearch(terms: List<String>, targets: List<String>): Boolean {
    return targets.any { target ->
        terms.any { term ->
            term.lowercase() in target.lowercase()
        }
    }
}