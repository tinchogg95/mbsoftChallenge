# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# H2 Database (en memoria)
ENV SPRING_DATASOURCE_URL=jdbc:h2:mem:medicationdb

EXPOSE 8082
CMD ["java", "-jar", "app.jar"]