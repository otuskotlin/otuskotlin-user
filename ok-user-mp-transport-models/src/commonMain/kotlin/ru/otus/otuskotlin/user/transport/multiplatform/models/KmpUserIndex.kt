package ru.otus.otuskotlin.user.transport.multiplatform.models

import kotlinx.serialization.Serializable

@Serializable
data class KmpUserIndex(
        var limit: Long? = null,
        var offset: Long? = null,
        var filter: Filter? = null
) {
    @Serializable
    data class Filter(
            var searchString: String? = null
    )
}
