package ru.otus.otuskotlin.user.backend.common.models

data class UserIndexFilter(
        val searchString: String = ""
) {
    companion object {
        val NONE = UserIndexFilter()
    }
}
