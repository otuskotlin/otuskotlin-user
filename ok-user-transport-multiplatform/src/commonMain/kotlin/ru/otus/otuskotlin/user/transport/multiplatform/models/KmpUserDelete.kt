package ru.otus.otuskotlin.user.transport.multiplatform.models

import kotlinx.serialization.Serializable

@Serializable
data class KmpUserDelete(
        var userId: String? = null,
        var debug: Debug? = null
) {
    @Serializable
    class Debug {}
}
