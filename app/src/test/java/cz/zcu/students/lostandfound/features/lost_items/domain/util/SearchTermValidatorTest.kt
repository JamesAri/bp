package cz.zcu.students.lostandfound.features.lost_items.domain.util

import com.google.common.truth.Truth.assertThat
import cz.zcu.students.lostandfound.common.constants.Validations.Companion.MAX_SEARCH_TERM_LENGTH

import org.junit.Before
import org.junit.Test

class SearchTermValidatorTest {

    private lateinit var searchTermValidator: SearchTermValidator

    @Before
    fun setUp() {
        searchTermValidator = SearchTermValidator()
    }

    @Test
    fun `Search term with length smaller than MAX_SEARCH_TERM_LENGTH, is valid`() {
        val searchTerm = "a".repeat(MAX_SEARCH_TERM_LENGTH - 1)
        val isValid = searchTermValidator.validate(searchTerm)
        assertThat(isValid).isTrue()
    }

    @Test
    fun `Search term with new line, is invalid`() {
        val searchTerm = "a".repeat(MAX_SEARCH_TERM_LENGTH - 2) + "\n"
        val isValid = searchTermValidator.validate(searchTerm)
        assertThat(isValid).isFalse()
    }

    @Test
    fun `Search term with new length MAX_SEARCH_TERM_LENGTH, is valid`() {
        val searchTerm = "a".repeat(MAX_SEARCH_TERM_LENGTH)
        val isValid = searchTermValidator.validate(searchTerm)
        assertThat(isValid).isTrue()
    }

    @Test
    fun `Empty search term, is invalid`() {
        val searchTerm = ""
        val isValid = searchTermValidator.validate(searchTerm)
        assertThat(isValid).isFalse()
    }

}