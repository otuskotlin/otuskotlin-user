package ru.otus.otuskotlin.user.transport.multiplatform.models

import kotlinx.serialization.*

@Serializable
data class KmpUser(
        var id: String? = null,
        var fname: String? = null,
        var mname: String? = null,
        var lname: String? = null,
        var dob: String? = null,
        var email: String? = null,
        var phone: String? = null,
        var permissions: MutableSet<String>? = null
) {
}
