package ru.otus.otuskotlin.user.transport.frontend.multiplatform

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise
import ru.otus.otuskotlin.user.transport.multiplatform.models.*
import kotlin.js.Promise

class KmpUserBackendServiceJS(
        val endpoint: String
) {
    private val service = KmpUserBackendService(endpoint)

    fun get(query: KmpUserGet): Promise<KmpUserResponseItem> = GlobalScope.promise {
        service.get(query)
    }
    fun index(query: KmpUserIndex): Promise<KmpUserResponseIndex> = GlobalScope.promise {
        service.index(query)
    }
    fun create(query: KmpUserCreate): Promise<KmpUserResponseItem> = GlobalScope.promise {
        service.create(query)
    }
    fun update(query: KmpUserUpdate): Promise<KmpUserResponseItem> = GlobalScope.promise {
        service.update(query)
    }
    fun delete(query: KmpUserDelete): Promise<KmpUserResponseItem> = GlobalScope.promise {
        service.delete(query)
    }
}
