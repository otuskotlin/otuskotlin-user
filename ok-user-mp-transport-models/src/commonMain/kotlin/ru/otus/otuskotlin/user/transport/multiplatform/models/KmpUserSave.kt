package ru.otus.otuskotlin.user.transport.multiplatform.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
abstract class KmpUserSave(
        @Transient open var fname: String? = null,
        @Transient open var mname: String? = null,
        @Transient open var lname: String? = null,
        @Transient open var dob: String? = null,
        @Transient open var email: String? = null,
        @Transient open var phone: String? = null
)
