# otuskotlin-user
Учебный проект курса Kotlin Backend. Проект описывает базовый CRUD для управления пользователями.

Проект используется для демонстрации преподавателями учебного материала студентам

## Модули

### Общие
1. [ok-userl-mp-common](ok-user-mp-common/README.md) - компоненты, общие для всего проекта: бэкенд + фронтенд
1. [ok-userl-be-common](ok-user-be-common/README.md) - компоненты, общие для различных модулей бэкенда

### Транспорт
1. [ok-user-mp-trasnport-models](ok-user-mp-transport-models/README.md) - определение API на Kotlin Multiplatform
1. [ok-user-be-transport-multiplatform](ok-user-be-transport-multiplatform/README.md) - модуль для подключения к фреймворку.
Содержит мапперы для свизи API с внутренними моделями
1. [ok-user-oa-transport-models-jvm](ok-user-oa-transport-models-jvm/README.md) - модуль с автогенерированными из спеки OpenAPI моделями для бэкенда.

### Фронтенды
1. [ok-user-fe-angular](ok-user-fe-angular/README.md) - фронтенд на Angular
1. [front_flutter](front_flutter/README.md) - фронт на Flutter

### Backend-приложения
1. [ok-user-be-app-jetty](ok-user-be-app-jetty/README.md) - Минимальное тестирование транспортных моделей на примере Jetty
1. [ok-user-oa-transport-models-jvm](ok-user-oa-transport-models-jvm/README.md) - Приложение на Spring Boot, сгенерированное с использованием OpenAPI
1. [ok-user-be-app-ktor](ok-user-be-app-ktor/README.md) - "Микросервисное" преложение на KTOR
