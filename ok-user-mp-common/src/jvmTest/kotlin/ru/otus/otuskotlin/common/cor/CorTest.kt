package ru.otus.otuskotlin.common.cor

import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

internal class CorTest {

//    private val job = Job()
//    private val coroutineScope = CoroutineScope(job + Dispatchers.Default)

    @Test
    fun createChain() {
        val chain: IExec<TestContext> = cor {
            exec { a = "a" }
            exec(object: IExec<TestContext> {
                override suspend fun exec(ctx: TestContext) {
                    ctx.a += "c"
                }
            })

            handler {
                isApplicable {
                    println("Check a: ${a}")
                    a.isNotEmpty()
                }
                exec {
                    a += "b"
                }
            }
        }
        val ctx = TestContext()
        println("Starting test")
        runBlocking {
            chain.exec(ctx)
            assertEquals("acb", ctx.a)
            println("Test Done")
        }
        println("Finishing test")
    }

    @Test
    fun nestedProcessor() {
        val chain: IExec<TestContext> = cor {
            exec { a = "a" }
            processor {
                isApplicable { true }

                exec(object : IExec<TestContext> {
                    override suspend fun exec(ctx: TestContext) {
                        ctx.a += "c"
                    }
                })

                handler {
                    isApplicable {
                        println("Check a: ${a}")
                        a.isNotEmpty()
                    }
                    exec {
                        a += "b"
                    }
                }
            }
        }
        val ctx = TestContext()
        println("Starting test")
        runBlocking {
            chain.exec(ctx)
            assertEquals("acb", ctx.a)
            println("Test Done")
        }
        println("Finishing test")
    }

    data class TestContext(
            var a: String = ""
    )
}

