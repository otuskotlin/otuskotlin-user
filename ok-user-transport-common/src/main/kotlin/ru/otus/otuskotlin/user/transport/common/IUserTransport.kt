package ru.otus.otuskotlin.user.transport.common

import ru.otus.otuskotlin.user.common.UserContext

interface IUserTransport<Tget, Tindex, Tcreate, Tupdate, Tremove> {
    /**
     * Метод вызывается при получении запроса для получения информации о пользователе
     */
    suspend fun consumeGet(message: Tget, context: UserContext): UserContext

    /**
     * Метод вызывается при получении запроса для получения списка пользователей, в том числе с фильтрацией
     */
    suspend fun consumeIndex(message: Tindex, context: UserContext): UserContext

    /**
     * Метод вызывается при получении запроса для создания пользователя
     */
    suspend fun consumeCreate(message: Tcreate, context: UserContext): UserContext

    /**
     * Метод вызывается при получении запроса для изменения данных о пользователе
     */
    suspend fun consumeUpdate(message: Tupdate, context: UserContext): UserContext

    /**
     * Метод вызывается при получении запроса для удаления пользователя
     */
    suspend fun consumeRemove(message: Tremove, context: UserContext): UserContext

    /**
     * Метод вызывается для оповещения клиента об изменении данных о пользователе.
     * В случае HTTP 1.x требуется реализовать с пустым обработчиком.
     * В остальных случаях реализуем функционал отправки сообщений подписчикам на стороне клиента.
     */
    suspend fun notifyUpdated(context: UserContext): UserContext
}
