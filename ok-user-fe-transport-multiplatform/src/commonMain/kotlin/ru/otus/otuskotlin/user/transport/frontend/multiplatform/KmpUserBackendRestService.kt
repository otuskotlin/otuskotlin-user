package ru.otus.otuskotlin.user.transport.frontend.multiplatform

import kotlin.reflect.KClass

expect class KmpUserBackendRestService() {
    suspend inline fun <reified T: Any, reified U: Any> request(endpoint: String, query: T): U
    suspend fun <T : Any, U: Any> requestFull(endpoint: String, query: T, queryClass: KClass<T>, responseClass: KClass<U>): U
}
