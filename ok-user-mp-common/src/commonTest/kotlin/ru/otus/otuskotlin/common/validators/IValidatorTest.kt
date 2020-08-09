package ru.otus.otuskotlin.common.validators

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

internal class IValidatorTest {

    @Test
    fun validatorTest() {
        val validator = SomeValidator()

        val negative = validator.validate(-3)
        assertEquals("negative", negative.errors.first().code)
        assertEquals(HandleError.Level.FATAL, negative.level)
        assertFalse { negative.isOk }
    }

    class SomeValidator: IValidator<Int> {
        private val field: String = "field"
        override fun validate(arg: Int): ValidationResult = ValidationResult(
                if(arg < 0) HandleError(code = "negative", field = field, level = HandleError.Level.FATAL) else null,
                if(arg < 10) HandleError(code = "too-big", field = field, level = HandleError.Level.ERROR) else null,
                if(arg == 5) HandleError(code = "exact", field = field, level = HandleError.Level.INFO) else null,
                HandleError(code = "some", field = field, level = HandleError.Level.INFO)
        )

    }
}
