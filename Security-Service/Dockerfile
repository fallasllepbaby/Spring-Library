# Этап сборки
FROM gradle:7.3.3-jdk17 AS build
WORKDIR /
COPY /src /src
COPY build.gradle settings.gradle /
RUN gradle clean build

# Этап запуска
FROM openjdk:17-jdk-slim
WORKDIR /
COPY --from=build /build/libs/*.jar application.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "application.jar"]
