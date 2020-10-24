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
1. [ok-user-be-app-kotless](ok-user-be-app-kotless/README.md) - Бессерверное (Serverless) преложение на Kotless

### Модуль бизнес-логики
1. [ok-user-be-logics](ok-user-be-logics/README.md) - Модуль, содержащий цепочки бизнес-правил обработки запросов

### Работа с БД
1. [ok-user-be-repo-inmemory](ok-user-be-repo-inmemory/README.md) - Репозитарий для InMemory базы данных, предназначенной для тестирования
1. [ok-user-be-repo-cassandra](ok-user-be-repo-cassandra/README.md) - Репозитарий для базы данных Cassandra, предназначенной для работы в составе Ktor
1. [ok-user-be-repo-dynamodb](ok-user-be-repo-dynamodb/README.md) - Репозитарий для базы данных DynamoDB, предназначенной для работы в составе Kotless

### Другое
1. [rests](rests) - набор REST-скриптов для тестирования сервиса
1. [elk-stack](elk-stack/README.md) - набор скриптов для подъема инфраструктуры логирования, мониторинга и анализа логов
на базе ELK-Stack

#### Сборка

Особенность текущей версии в том, что Kotless 0.1.6 работаент с `kotlinx.serialization 0.20.0`, тогда как 
`ok-user-be-app-ktor` не может работать с этой версией и требует `1.0.0-RC`.

Поэтому в скриптах сборки проверяется версия Kotlin и `kotlinx.sertialization`. При версии Kotlin `1.3.*` и 
`kotlinx.serialization` `0.*` автоматически подключается `ok-user-be-app-kotless`, иначе будет собираться 
`ok-user-be-app-ktor`.
