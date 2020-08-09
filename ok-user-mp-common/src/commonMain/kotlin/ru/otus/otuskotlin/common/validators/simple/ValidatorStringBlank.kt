package ru.otus.otuskotlin.common.validators.simple

import ru.otus.otuskotlin.common.validators.HandleError
import ru.otus.otuskotlin.common.validators.IValidator
import ru.otus.otuskotlin.common.validators.ValidationResult

class ValidatorStringBlank(
        val field: String = ""
) : IValidator<String?> {
    override fun validate(arg: String?): ValidationResult = ValidationResult(
            if (arg == null || arg.isBlank())
                HandleError(
                        message = "Field ${field.takeIf { it.isNotBlank() } ?: ""} cannot be blank",
                        level = HandleError.Level.ERROR
                )
            else null
    )
}
