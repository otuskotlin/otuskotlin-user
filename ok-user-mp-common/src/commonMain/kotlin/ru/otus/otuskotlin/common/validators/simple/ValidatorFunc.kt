package ru.otus.otuskotlin.common.validators.simple

import ru.otus.otuskotlin.common.validators.IValidator
import ru.otus.otuskotlin.common.validators.ValidationResult

class ValidatorFunc<T>(
        private val validator: (T) -> ValidationResult
) : IValidator<T> {
    override fun validate(arg: T): ValidationResult = validator(arg)
}

fun <T> validator(validator: (T) -> ValidationResult) = ValidatorFunc<T>(validator)
