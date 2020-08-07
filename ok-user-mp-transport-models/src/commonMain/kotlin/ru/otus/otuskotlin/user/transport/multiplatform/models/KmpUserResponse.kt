package ru.otus.otuskotlin.user.transport.multiplatform.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
open class KmpUserResponse(
        @Transient open val status: KmpUserResultStatuses? = null,
        @Transient open val errors: List<KmpUserError>? = null
)
