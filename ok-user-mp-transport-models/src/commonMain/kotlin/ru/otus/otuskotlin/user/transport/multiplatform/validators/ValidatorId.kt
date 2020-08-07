package ru.otus.otuskotlin.user.transport.multiplatform.validators

import ru.otus.otuskotlin.common.validators.IValidator
import ru.otus.otuskotlin.common.validators.ValidationResult
import ru.otus.otuskotlin.common.validators.ValidatorStringBlank
import ru.otus.otuskotlin.common.validators.ValidatorStringRegex

class ValidatorId(
        val field: String = ""
): IValidator<String> {
    private val validatorEmpty = ValidatorStringBlank(field = field)
    private val validatorRegex = ValidatorStringRegex(
            regex = Regex("""[0-9a-zA-Z-]+"""),
            code = "id-validation-symbols",
            group = "validation",
            field = field,
            message = ""
    )
    override fun validate(arg: String): ValidationResult = ValidationResult(
            *validatorEmpty.validate(arg).errors.toTypedArray(),
            *validatorRegex.validate(arg).errors.toTypedArray()
    )
}
