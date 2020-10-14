package ru.otus.otuskotlin.user.backend.common.exceptions

class UserRepoWrongId(id: String) : Throwable("Wrong ID in operation: $id")
