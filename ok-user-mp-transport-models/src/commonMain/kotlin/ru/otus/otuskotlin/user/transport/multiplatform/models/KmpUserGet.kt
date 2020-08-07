package ru.otus.otuskotlin.user.transport.multiplatform.models

import kotlinx.serialization.Serializable

@Serializable
data class KmpUserGet(
        val userId: String? = null,
        val debug: KmpDebug? = null
) {
    @Serializable
    class KmpDebug {}
}
