package ru.otus.otuskotlin.user.transport.multiplatform.validators

import ru.otus.otuskotlin.common.validators.IValidator
import ru.otus.otuskotlin.common.validators.ValidationResult
import ru.otus.otuskotlin.common.validators.fields.ValidatorId
import ru.otus.otuskotlin.common.validators.fields.ValidatorLastName
import ru.otus.otuskotlin.user.transport.multiplatform.models.KmpUserUpdate

class KmpValidatorUserUpdate: IValidator<KmpUserUpdate> {

    private val validatorId = ValidatorId()
    private val validatorLName = ValidatorLastName()

    override fun validate(arg: KmpUserUpdate): ValidationResult = ValidationResult(
            *validatorId.validate(arg.id).errors.toTypedArray(),
            *validatorLName.validate(arg.id).errors.toTypedArray()
    )
}
