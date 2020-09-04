package ru.otus.otuskotlin.user.transport.multiplatform.models

import kotlinx.serialization.Serializable

@Serializable
data class KmpUserResponseIndex(
        val data: List<KmpUser>? = null,
        val limit: Long? = null,
        val offset: Long? = null,
        override val status: KmpUserResultStatuses? = null,
        override val errors: List<KmpUserError>? = null
): KmpUserResponse(
        status = status,
        errors = errors
)
