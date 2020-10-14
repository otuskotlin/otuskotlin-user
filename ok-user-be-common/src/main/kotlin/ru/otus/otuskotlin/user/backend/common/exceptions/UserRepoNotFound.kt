package ru.otus.otuskotlin.user.backend.common.exceptions

class UserRepoNotFound(id: String) : RuntimeException("Object with ID=$id is not found")
