# Базовий образ. Використовується офіційний образ openjdk:17, який містить Java Development Kit
FROM openjdk:17
# Встановлення робочої директорії. Всі наступні команди будуть виконуватися в каталозі /app
# всередині контейнера. Якщо каталогу /app ще не існує, він буде створений автоматично.
WORKDIR /app
# Копіювання зібраного JAR-файлу.
# -- target/user-service-0.0.1-SNAPSHOT.jar - згенерований JAR-файл Spring Boot-додатка
# (знаходиться у директорії target після збірки за допомогою Maven).
# -- order-service.jar — ім'я файлу в контейнері (він буде збережений у /app/user-service.jar).
COPY build/order-service-0.0.1-SNAPSHOT.jar order-service.jar
# Команда для запуску додатка:
# -- CMD визначає команду, яка буде виконана під час запуску контейнера.
# -- Запускає Spring Boot-додаток за допомогою команди java -jar order-service.jar
CMD ["java", "-jar", "order-service.jar"]
