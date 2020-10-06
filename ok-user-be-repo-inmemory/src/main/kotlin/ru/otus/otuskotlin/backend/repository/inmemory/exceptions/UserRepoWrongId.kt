package ru.otus.otuskotlin.backend.repository.inmemory.exceptions

class UserRepoWrongId(id: String) : Throwable("Wrong ID in operation: $id")
