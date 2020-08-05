package ru.otus.otuskotlin.common.validators

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class ValidatorStringRegexTest {
    @Test
    fun success() {
        val validation = ValidatorStringRegex(regex = Regex("[0-9]")).validate("3845938")
        assertEquals(HandleError.Level.NONE, validation.level)
    }

    @Test
    fun error() {
        val validation = ValidatorStringRegex(regex = Regex("[0-9]")).validate("3845938abc")
        assertEquals(HandleError.Level.ERROR, validation.level)
        assertTrue(validation.errors.first().message.contains("abc"))
    }
}
