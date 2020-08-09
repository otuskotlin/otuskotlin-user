package ru.otus.otuskotlin.user.backend.app.jetty

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import ru.otus.otuskotlin.user.transport.multiplatform.models.KmpUserCreate
import ru.otus.otuskotlin.user.transport.multiplatform.models.KmpUserResultStatuses
import kotlin.test.assertEquals

class UserControllerSpec : Spek({
    describe("MyResource") {
        val resource = UserController()

        it("should return a MyData with an id, message, and date") {
            val data = resource.create(KmpUserCreate())
//            assertEquals(1, data.id)
//            assertEquals("Got it!", data.msg)
            assertEquals(KmpUserResultStatuses.SUCCESS, data.status)
        }

//        it("should return a MyData with msg reversed and id and date the same") {
//            val data = resource.reverse(MyData(42, "panama", Date(2000)))
//            assertEquals(MyData(42, "amanap", Date(2000)), data)
//        }
    }
})
