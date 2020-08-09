package ru.otus.otuskotlin.common.validators.fields

import ru.otus.otuskotlin.common.validators.IValidator
import ru.otus.otuskotlin.common.validators.ValidationResult
import ru.otus.otuskotlin.common.validators.simple.ValidatorStringBlank
import ru.otus.otuskotlin.common.validators.simple.ValidatorStringRegex

class ValidatorLastName(
        val field: String = ""
): IValidator<String?> {
    private val validatorEmpty = ValidatorStringBlank(field = field)
    private val validatorRegex = ValidatorStringRegex(
            regex = Regex("""[\p{L}'-]+"""),
            code = "id-validation-symbols",
            group = "validation",
            field = field,
            message = ""
    )
    override fun validate(arg: String?): ValidationResult = ValidationResult(
            *validatorEmpty.validate(arg).errors.toTypedArray(),
            *(arg?.let { validatorRegex.validate(it).errors.toTypedArray() } ?: emptyArray())
    )
}
