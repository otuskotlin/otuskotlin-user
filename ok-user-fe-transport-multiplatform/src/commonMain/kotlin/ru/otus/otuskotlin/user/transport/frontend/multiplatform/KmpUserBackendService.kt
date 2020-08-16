package ru.otus.otuskotlin.user.transport.frontend.multiplatform

import ru.otus.otuskotlin.user.transport.multiplatform.models.*

class KmpUserBackendService(
        val endpoint: String
) {

    private val service = KmpUserBackendRestService()

    suspend fun get(query: KmpUserGet): KmpUserResponseItem = service.request("$endpoint/get", query)
    suspend fun index(query: KmpUserIndex): KmpUserResponseIndex = service.request("$endpoint/index", query)
    suspend fun create(query: KmpUserCreate): KmpUserResponseItem = service.request("$endpoint/create", query)
    suspend fun update(query: KmpUserUpdate): KmpUserResponseItem = service.request("$endpoint/update", query)
    suspend fun delete(query: KmpUserDelete): KmpUserResponseItem = service.request("$endpoint/delete", query)
}
