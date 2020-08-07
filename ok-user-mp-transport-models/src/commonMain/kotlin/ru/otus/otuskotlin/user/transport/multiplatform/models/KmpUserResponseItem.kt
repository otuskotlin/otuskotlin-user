package ru.otus.otuskotlin.user.transport.multiplatform.models

import kotlinx.serialization.Serializable

@Serializable
data class KmpUserResponseItem(
        val data: KmpUser? = null,
        override val status: KmpUserResultStatuses? = null
): KmpUserResponse(
        status = status
)
