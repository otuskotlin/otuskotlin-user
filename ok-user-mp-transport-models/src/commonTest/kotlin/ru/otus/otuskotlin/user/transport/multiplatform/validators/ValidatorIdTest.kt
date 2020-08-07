package ru.otus.otuskotlin.user.transport.multiplatform.validators

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class ValidatorIdTest {
    @Test
    fun success() {
        val validation = ValidatorId(field = "id").validate("123423-123423-sdfsd")
        assertTrue(validation.isOk)
    }

    @Test
    fun error() {
        val validation = ValidatorId(field = "id").validate("@#123423-123423-sdfsd@#")
        assertFalse(validation.isOk)
        assertTrue(validation.errors.first().message.contains("@#@#"))
    }
}
