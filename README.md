# Инструкция по запуску проекта
Данный проект состоит из нескольких сервисов: Eureka-Server, Api-Gateway, Security-Service, Book-Service и Library-Service. Ниже приведена инструкция по запуску проекта с использованием пользовательского интерфейса в вашей интегрированной среде разработки (IDE).

## Шаг 1: Клонирование репозитория

## Шаг 2: Создание баз данных
Создайте три базы данных для каждого из следующих сервисов: Book-Service, Library-Service, Security-Service.

## Шаг 3: Настройка данных доступа к базам данных
В каждом из трех application.properties файлов для каждого сервиса (Book-Service, Library-Service, Security-Service), укажите данные для доступа к соответствующей базе данных.

## Шаг 4: Запуск сервисов

### Eureka-Server:

- Откройте проект Eureka-Server в вашей IDE.
- Найдите и запустите класс EurekaServerApplication.

### Api-Gateway:

- Откройте проект Api-Gateway в вашей IDE.
- Найдите и запустите класс ApiGatewayApplication.

### Security-Service:

- Откройте проект Security-Service в вашей IDE.
- Найдите и запустите класс SecurityServiceApplication.

### Book-Service:

- Откройте проект Book-Service в вашей IDE.
- Найдите и запустите класс BookServiceApplication.

### Library-Service:

- Откройте проект Library-Service в вашей IDE.
- Найдите и запустите класс LibraryServiceApplication.

## Шаг 5: Доступ к документации
Используйте следующие ссылки для доступа к документации по сервисам:

#### http://localhost:8081/swagger-ui/index.html#/ - Документация Book-Service
#### http://localhost:8082/swagger-ui/index.html#/ - Документация Library-Service
#### http://localhost:9898/swagger-ui/index.html#/ - Документация Security-Service