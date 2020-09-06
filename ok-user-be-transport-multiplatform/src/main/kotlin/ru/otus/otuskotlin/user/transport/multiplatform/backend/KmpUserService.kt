package ru.otus.otuskotlin.user.transport.multiplatform.backend

import org.slf4j.LoggerFactory
import ru.otus.otuskotlin.user.backend.common.UserContext
import ru.otus.otuskotlin.user.backend.common.UserContextStatus
import ru.otus.otuskotlin.user.backend.common.errors.InternalServerError
import ru.otus.otuskotlin.user.backend.common.models.UserModel
import ru.otus.otuskotlin.user.backend.common.models.UserPermissionsModel
import ru.otus.otuskotlin.user.transport.multiplatform.models.*
import java.lang.RuntimeException
import java.time.LocalDate
import java.util.*

class KmpUserService() {

    private val log = LoggerFactory.getLogger(this::class.java)!!

    private val userModel = UserModel(
            id = "1234123",
            fname = "First",
            mname = "Middle",
            lname = "Last",
            dob = LocalDate.of(2000, 1, 1),
            email = "email@email.email",
            phone = "+790988768768",
            permissions = mutableSetOf(
                    UserPermissionsModel.VIEW,
                    UserPermissionsModel.VIEW
            )
    )

    suspend fun get(query: KmpUserGet): KmpUserResponseItem = UserContext().run {
        try {
            setQuery(query)
            responseUser = userModel.copy(id = query.userId ?: throw RuntimeException("No userId"))
            status = UserContextStatus.SUCCESS
        } catch (e: Throwable) {
            log.error("Get chain error", e)
            errors += InternalServerError.instance
        }
        resultItem()
    }

    suspend fun index(query: KmpUserIndex): KmpUserResponseIndex = UserContext().run {
        try {
            setQuery(query)
            responseUser = userModel
            status = UserContextStatus.SUCCESS
        } catch (e: Throwable) {
            log.error("Index chain error", e)
            errors += InternalServerError.instance
        }
        resultIndex()
    }

    fun create(query: KmpUserCreate): KmpUserResponseItem = UserContext().run {
        try {
            setQuery(query)
            responseUser = requestUser.copy(id = UUID.randomUUID().toString())
            status = UserContextStatus.SUCCESS
        } catch (e: Throwable) {
            log.error("Create chain error", e)
            errors += InternalServerError.instance
        }
        resultItem()
    }

    fun update(query: KmpUserUpdate): KmpUserResponseItem = UserContext().run {
        try {
            setQuery(query)
            responseUser = requestUser.copy()
            status = UserContextStatus.SUCCESS
        } catch (e: Throwable) {
            log.error("Update chain error", e)
            errors += InternalServerError.instance
        }
        resultItem()
    }

    fun delete(query: KmpUserDelete): KmpUserResponseItem = UserContext().run {
        try {
            setQuery(query)
            responseUser = requestUser.copy()
            status = UserContextStatus.SUCCESS
        } catch (e: Throwable) {
            log.error("Delete chain error", e)
            errors += InternalServerError.instance
        }
        resultItem()
    }

}
