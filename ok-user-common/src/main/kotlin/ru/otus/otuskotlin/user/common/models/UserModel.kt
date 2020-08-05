package ru.otus.otuskotlin.user.common.models

import java.time.LocalDate

data class UserModel(
        var id: String = "",
        var fname: String = "",
        var mname: String = "",
        var lname: String = "",
        var dob: LocalDate = LocalDate.MIN,
        var email: String = "",
        var phone: String = "",
        var permissions: MutableSet<UserPermissionsModel> = mutableSetOf()
) {
    companion object {
        val NONE = UserModel()
    }
}
