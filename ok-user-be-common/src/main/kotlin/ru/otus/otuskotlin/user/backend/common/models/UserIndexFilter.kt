package ru.otus.otuskotlin.user.backend.common.models

data class UserIndexFilter(
        val searchString: String = "",
        val dob: String? = null,
) {
    companion object {
        val NONE = UserIndexFilter()
    }
}
