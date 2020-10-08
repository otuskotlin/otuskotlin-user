package ru.otus.otuskotlin.backend.repository.inmemory.exceptions

class UserRepoNotFound(id: String) : RuntimeException("Object with ID=$id is not found")
