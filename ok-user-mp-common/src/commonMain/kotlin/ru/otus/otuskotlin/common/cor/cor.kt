package ru.otus.otuskotlin.common.cor

typealias CorMatcher<T> = T.() -> Boolean
typealias CorHandler<T> = T.() -> Unit
typealias CorOnError<T> = T.(Throwable) -> Unit

fun <T> cor(block: Processor.Builder<T>.() -> Unit) = Processor.Builder<T>().apply(block).build()
