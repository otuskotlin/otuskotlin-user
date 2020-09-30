package ru.otus.otuskotlin.common.cor

typealias CorMatcher<T> = suspend T.() -> Boolean
typealias CorHandler<T> = suspend T.() -> Unit
typealias CorOnError<T> = suspend T.(Throwable) -> Unit

fun <T> cor(block: Processor.Builder<T>.() -> Unit) = Processor.Builder<T>().apply(block).build()

fun <T> corHandler(block: Handler.Builder<T>.() -> Unit): Handler<T> = Handler.Builder<T>().apply(block).build()
