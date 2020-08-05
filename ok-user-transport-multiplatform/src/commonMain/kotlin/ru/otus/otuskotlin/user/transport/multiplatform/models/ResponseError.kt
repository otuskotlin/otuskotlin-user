package ru.otus.otuskotlin.user.transport.multiplatform.models

import kotlinx.serialization.Serializable

@Serializable
data class ResponseError(

        /**
         * Уникальный код ошибки
         */
        val code: String? = null,

        /**
         * код группы ошибок: валидация, база данных, внешний сервис и др.
         */
        val group: String? = null,
        /**
         * Название поля, в котором произошла ошибка
         */
        val field: String? = null,

        /**
         *
         */
        val message: String? = null
)
