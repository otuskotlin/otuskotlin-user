package ru.otus.otuskotlin.common.validators

import kotlin.jvm.JvmOverloads

data class HandleError @JvmOverloads constructor(
        val code: String = "",
        val group: String = "",
        val level: Level = Level.NONE,
        val field: String = "",
        val message: String = "",
        val description: String = ""
) {
    enum class Level(val lvl: Int) {
        NONE(-1),
        INFO(0),
        WARNING(10),
        ERROR(20),
        FATAL(30),
        PANIC(40)
    }
}
