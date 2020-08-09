package ru.otus.otuskotlin.common.validators

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class JsTest {

    @Test
    @JsName("createTest")
    fun `create test`() {
        val result: dynamic = js("""
            var module = require('otuskotlin-user-ok-user-mp-common');
            new module.ru.otus.otuskotlin.common.validators.ValidationResult();             
        """)
        assertEquals("ValidationResult", result::class.simpleName)
    }

    @Test
    @JsName("externalSqrtTest")
    fun `external test`() {
        val res = mathjs.sqrt(16)
        assertEquals(4, res)
    }

    @Test
    @JsName("externalSqrt1Test")
    fun `external test 1`() {
        val res = sqrt1(81)
        assertEquals(9, res)
    }

    @Test
    @JsName("externalIsSortedTest")
    fun `is-sorted test`() {
        assertFalse { sorted(arrayOf(3,1,7)) }
        assertTrue { sorted(arrayOf(1,2,3)) }
    }

}

@JsModule("mathjs")
@JsNonModule
@JsName("mathjs")
external val mathjs: dynamic

@JsModule("is-sorted")
@JsNonModule
external fun <T> sorted(a: Array<T>): Boolean
