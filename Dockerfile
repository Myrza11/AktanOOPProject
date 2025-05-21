# Используем официальный Java образ
FROM eclipse-temurin:17-jdk

# Указываем рабочую директорию
WORKDIR /app

# Копируем файлы проекта и собираем JAR
COPY . .

# Собираем проект
RUN ./mvnw clean package -DskipTests

# Запускаем JAR (замени на имя своего файла!)
CMD ["java", "-jar", "target/AktanOOPProject-0.0.1-SNAPSHOT.jar"]
