# Recommendation Service (Банк «Стар»)

Микросервис для выдачи персональных банковских рекомендаций. Анализирует транзакционную активность клиента и предлагает продукты на основе статических (Java) и динамических (JSON) правил.

## Стек технологий
*   **Java 17**
*   **Spring Boot** (Web, Data JPA, Actuator)
*   **PostgreSQL** (хранение динамических правил)
*   **H2 Database** (хранение транзакций клиентов, read-only)
*   **Liquibase** (миграции БД)
*   **Caffeine** (кеширование)
*   **SpringDoc OpenAPI** (Swagger UI)
*   **Telegram Bots API**

## Быстрый старт
Для запуска проекта локально вам понадобятся JDK 17, Maven и PostgreSQL.

1. Клонируйте репозиторий:
   ```bash
   git clone https://github.com/Dmitry-132/recommendation.service
   ```
2. Соберите проект:
   ```bash
   ./mvnw clean package
   ```
3. Запустите приложение (подробнее в инструкции по развертыванию):
   ```bash
   java -jar target/recommendation-service-0.0.1-SNAPSHOT.jar
   ```
## Документация
Вся подробная информация находится в Wiki:

[Требования и User Stories](https://github.com/Dmitry-132/recommendation.service/wiki/project-requirements)

[Архитектура приложения](https://github.com/Dmitry-132/recommendation.service/wiki/application-architecture)

[REST API (OpenAPI)](https://github.com/Dmitry-132/recommendation.service/wiki/OpenAPI-(Swagger))

[Руководство по развертыванию (Deployment)](https://github.com/Dmitry-132/recommendation.service/wiki/requirements-for-deployment)

[Памятка по созданию динамических правил](https://github.com/Dmitry-132/recommendation.service/wiki/Creating-dynamic-rules)

## Эндпоинты
*   **GET /recommendation/{user_id}** — получить рекомендации для пользователя.
*   **GET /rule** — получить список всех динамических правил.
*   **POST /rule** — добавить новое правило.
*   **PUT /rule** — изменить существующее правило.
*   **DELETE /rule/delete/{id}** — удалить правило.
*   **POST /rule/clear-cache** — сбросить кеш рекомендаций.
*   **GET /management/info** — проверить статус и версию сервиса.

## Лицензия
Проект создан сугубо в учебных целях.