# Service-oriented distributed computing (SODC)
## Order service


docker run --name postgres-db -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=admin -e POSTGRES_DB=orderdb -p 5432:5432 -d postgres
### -- Виконайте наступну команду, щоб підключитися до PostgreSQL:
    $ docker exec -it postgres-db psql -U admin -d userdb
    $ CREATE DATABASE orderdb;

де:
exec – виконує команду всередині запущеного Docker контейнера.
-it – Вмикає інтерактивний режим (-i для інтерактивного введення, -t для призначення псевдо-TTY).
postgres-db – ім’я запущеного контейнера Docker, який містить базу даних PostgreSQL.
psql – інструмент командного рядка PostgreSQL.
-U admin – визначає ім’я користувача PostgreSQL (admin), яке використовується для підключення.
-d userdb – вказує назву бази даних (userdb), до якої потрібно підключитися.
-- Створіть нову базу даних:
CREATE DATABASE orderdb;
-- Додайте налаштувати PostgreSQL в src/main/resources/application.properties:
# Сервер
server.port=8082
# Налаштування бази даних
spring.datasource.url=jdbc:postgresql://localhost:5432/orderdb
spring.datasource.username=admin
spring.datasource.password=admin
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect


6.1. Створити Docker контейнер з сервісом Order Serivice
-- У кореневій директорії проєкту створити файл з назвою Dockerfile з таким вмістом:
# Базовий образ. Використовується офіційний образ openjdk:17, який містить Java Development Kit
FROM openjdk:17
# Встановлення робочої директорії. Всі наступні команди будуть виконуватися в каталозі /app
# всередині контейнера. Якщо каталогу /app ще не існує, він буде створений автоматично.
WORKDIR /app
# Копіювання зібраного JAR-файлу.
# -- target/user-service-0.0.1-SNAPSHOT.jar - згенерований JAR-файл Spring Boot-додатка
# (знаходиться у директорії target після збірки за допомогою Maven).
# -- order-service.jar — ім'я файлу в контейнері (він буде збережений у /app/user-service.jar).
COPY target/order-service-0.0.1-SNAPSHOT.jar order-service.jar
# Команда для запуску додатка:
# -- CMD визначає команду, яка буде виконана під час запуску контейнера.
# -- Запускає Spring Boot-додаток за допомогою команди java -jar order-service.jar
CMD ["java", "-jar", "order-service.jar"]

6.2. Запустити застосунок у Docker контейнері
-- в IDE відкрити вкладку Maven
-- запустити стадію clean
-- запустити стадію package
-- в структурі проєкту має зявитись папка target із jar файлом
-- відкрийте термінал IDE та виконайте команди
docker build -t order-service .
docker run -p 8082:8082 -p 5432:5432 order-service
6.3. Створити Docker контейнер з сервісом User Serivice
-- У кореневій директорії проєкту створити файл з назвою Dockerfile з таким вмістом:
FROM openjdk:17
WORKDIR /app
COPY target/user-service-0.0.1-SNAPSHOT.jar user-service.jar
CMD ["java", "-jar", "user-service.jar"]

6.4. Запустити застосунок у Docker контейнері
-- в IDE відкрити вкладку Maven
-- запустити стадію clean
-- запустити стадію package
-- в структурі проєкту має зявитись папка target із jar файлом
-- відкрийте термінал IDE та виконайте команди
docker build -t user-service .
docker run -p 8081:8081 -p 5432:5432 user-service
6.5. Перевірте CRUD єндпоінти за допомогою Postman
Створіть користувача, відправивши POST запит на http://localhost:8081/users з тілом:
{
"name": "Alice",
"email": "alice@example.com"
}
Створіть замовлення, відправивши POST запит на http://localhost:8082/orders з тілом:
{
"userId": 1,
"product": "Laptop",
"quantity": 1
}
# order-service
