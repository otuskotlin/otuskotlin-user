package ru.otus.otuskotlin.user.backend.app.jetty

import kotlinx.coroutines.runBlocking
import ru.otus.otuskotlin.user.backend.common.UserContext
import ru.otus.otuskotlin.user.backend.common.UserContextStatus
import ru.otus.otuskotlin.user.backend.common.models.UserModel
import ru.otus.otuskotlin.user.backend.common.models.UserPermissionsModel
import ru.otus.otuskotlin.user.transport.multiplatform.backend.resultIndex
import ru.otus.otuskotlin.user.transport.multiplatform.backend.resultItem
import ru.otus.otuskotlin.user.transport.multiplatform.backend.setQuery
import ru.otus.otuskotlin.user.transport.multiplatform.models.*
import java.time.LocalDate
import java.util.*

class JettyUserService {

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

    fun get(query: KmpUserGet): KmpUserResponseItem = runBlocking {
        val context = UserContext()
        context
                .setQuery(query)
                .apply {
                    responseUser = userModel
                    status = UserContextStatus.SUCCESS
                }
                .resultItem()
    }

    fun index(query: KmpUserIndex): KmpUserResponseIndex = runBlocking {
        val context = UserContext()
        context
                .setQuery(query)
                .apply {
                    responseUser = userModel
                    status = UserContextStatus.SUCCESS
                }
                .resultIndex()
    }

    fun create(query: KmpUserCreate): KmpUserResponseItem = runBlocking {
        val context = UserContext()
        context
                .setQuery(query)
                .apply {
                    responseUser = requestUser.copy(id = UUID.randomUUID().toString())
                    status = UserContextStatus.SUCCESS
                }
                .resultItem()
    }

    fun update(query: KmpUserUpdate): KmpUserResponseItem = runBlocking {
        val context = UserContext()
        context
                .setQuery(query)
                .apply {
                    responseUser = requestUser.copy()
                    status = UserContextStatus.SUCCESS
                }
                .resultItem()
    }

    fun delete(query: KmpUserDelete): KmpUserResponseItem = runBlocking {
        val context = UserContext()
        context
                .setQuery(query)
                .apply {
                    responseUser = requestUser.copy()
                    status = UserContextStatus.SUCCESS
                }
                .resultItem()
    }
}
