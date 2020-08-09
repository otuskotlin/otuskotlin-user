package ru.otus.otuskotlin.common.validators

import ru.otus.otuskotlin.common.validators.simple.validator
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class ValidatorFuncTest {

    @Test
    fun funcValidationTest() {
        val validator = validator<String> {
            ValidationResult(
                    if (!it.contains("ok")) HandleError(
                            message = noOkMessage,
                            level = HandleError.Level.ERROR
                    ) else null
            )
        }

        assertEquals(noOkMessage, validator.validate("error").errors.first().message)
    }

    companion object {
        const val noOkMessage = "String does not contain 'ok' symbols"
    }
}
