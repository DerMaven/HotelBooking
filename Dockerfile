from eclipse-temurin:21-jdk-alpine AS build
workdir /app
copy . .
run ./mvnw clean package -DskipTests

from eclipse-temurin:21-jre-alpine
workdir /app
copy --from=build /app/target/*.jar app.jar
entrypoint ["java", "-jar", "app.jar"]