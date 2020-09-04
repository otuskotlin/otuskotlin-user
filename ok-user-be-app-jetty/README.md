# `ok-user-be-app-jetty`

Модуль для минимального тестирования транспортных моделей

Entrypoint находится в [JettyMain](src/main/kotlin/ru/otus/otuskotlin/user/backend/app/jetty/JettyMain.kt).

Контроллер с определениями эндпоинтов располагается в [UserController](src/main/kotlin/ru/otus/otuskotlin/user/backend/app/jetty/UserController.kt).

Основная логика интеграции транспорта, фреймворка и бизнеса размещена в [JettyUserService](src/main/kotlin/ru/otus/otuskotlin/user/backend/app/jetty/JettyUserService.kt).
