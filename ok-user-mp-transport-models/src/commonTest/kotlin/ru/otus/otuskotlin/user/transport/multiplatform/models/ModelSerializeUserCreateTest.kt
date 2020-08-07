package ru.otus.otuskotlin.user.transport.multiplatform.models

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlin.test.Test
import kotlin.test.assertFalse

class ModelSerializeUserCreateTest {
    @Test
    fun serialize() {
        val json = Json(JsonConfiguration.Stable)
        val data = KmpUserCreate(
                fname = "First",
                mname = "Middle",
                lname = "Last",
                dob = "2012-01-01",
                email = "email@email.email",
                phone = "+78989"
        )

        val jsonData = json.stringify(KmpUserCreate.serializer(), data)
        println(jsonData)
        assertFalse {
            jsonData.contains("\"id\":")
        }
    }
}
