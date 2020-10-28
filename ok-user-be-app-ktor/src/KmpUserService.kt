package ru.otus.otuskotlin.user

import org.slf4j.LoggerFactory
import ru.otus.otuskotlin.user.backend.common.UserContext
import ru.otus.otuskotlin.user.backend.common.UserContextStatus
import ru.otus.otuskotlin.user.backend.common.errors.InternalServerError
import ru.otus.otuskotlin.user.backend.common.models.UserModel
import ru.otus.otuskotlin.user.backend.common.models.UserPermissionsModel
import ru.otus.otuskotlin.user.backend.logics.UserCrud
import ru.otus.otuskotlin.user.transport.multiplatform.backend.resultIndex
import ru.otus.otuskotlin.user.transport.multiplatform.backend.resultItem
import ru.otus.otuskotlin.user.transport.multiplatform.backend.setQuery
import ru.otus.otuskotlin.user.transport.multiplatform.models.*
import java.lang.RuntimeException
import java.time.LocalDate
import java.util.*

class KmpUserService(val crud: UserCrud) {

    private val log = LoggerFactory.getLogger(this::class.java)!!

    suspend fun get(query: KmpUserGet, requestId: String): KmpUserResponseItem = UserContext(requestUserId = requestId).run {
        try {
            crud.get(setQuery(query))
        } catch (e: Throwable) {
            log.error("Get chain error", e)
            errors += InternalServerError.instance
        }
        resultItem()
    }

    suspend fun index(query: KmpUserIndex, requestId: String): KmpUserResponseIndex = UserContext(requestUserId = requestId).run {
        try {
            crud.index(setQuery(query))
        } catch (e: Throwable) {
            log.error("Index chain error", e)
            errors += InternalServerError.instance
        }
        resultIndex()
    }

    suspend fun create(query: KmpUserCreate, requestId: String): KmpUserResponseItem = UserContext(requestUserId = requestId).run {
        try {
            crud.create(setQuery(query))
        } catch (e: Throwable) {
            log.error("Create chain error", e)
            errors += InternalServerError.instance
        }
        resultItem()
    }

    suspend fun update(query: KmpUserUpdate, requestId: String): KmpUserResponseItem = UserContext(requestUserId = requestId).run {
        try {
            crud.update(setQuery(query))
        } catch (e: Throwable) {
            log.error("Update chain error", e)
            errors += InternalServerError.instance
        }
        resultItem()
    }

    suspend fun delete(query: KmpUserDelete, requestId: String): KmpUserResponseItem = UserContext(requestUserId = requestId).run {
        try {
            crud.delete(setQuery(query))
        } catch (e: Throwable) {
            log.error("Delete chain error", e)
            errors += InternalServerError.instance
        }
        resultItem()
    }

}
