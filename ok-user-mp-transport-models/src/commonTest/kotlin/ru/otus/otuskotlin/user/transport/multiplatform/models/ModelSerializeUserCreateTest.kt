package ru.otus.otuskotlin.user.transport.multiplatform.models

import kotlin.test.Test
import kotlinx.serialization.json.Json
import kotlin.test.assertFalse

class ModelSerializeUserCreateTest {
    @Test
    fun serialize() {
//        val json = Json(JsonConfiguration.Stable)
        val data = KmpUserCreate(
                fname = "First",
                mname = "Middle",
                lname = "Last",
                dob = "2012-01-01",
                email = "email@email.email",
                phone = "+78989"
        )

        val jsonData = Json.encodeToString(KmpUserCreate.serializer(), data)
        println(jsonData)
        assertFalse {
            jsonData.contains("\"id\":")
        }
    }
}
