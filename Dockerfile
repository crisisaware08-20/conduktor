# Build jar
FROM gradle:8.5-jdk17 AS builder
WORKDIR /app

COPY settings.gradle ./

COPY gradle gradle
COPY gradlew ./
COPY app/build.gradle app/build.gradle
RUN ./gradlew app:dependencies --no-daemon

# Copy the application source code
COPY app/src app/src
RUN ./gradlew app:clean app:build -x test --no-daemon

# Create the final image
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/app/build/libs/app-fat.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
