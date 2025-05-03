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

# Configuración para persistencia H2
RUN mkdir -p /data
VOLUME /data

COPY --from=build /app/target/*.jar app.jar

# Variables de entorno para H2 persistente
ENV SPRING_DATASOURCE_URL=jdbc:h2:file:/data/productcode-db;DB_CLOSE_ON_EXIT=FALSE;AUTO_SERVER=TRUE
ENV SPRING_DATASOURCE_USERNAME=sa
ENV SPRING_DATASOURCE_PASSWORD=

EXPOSE 8081
CMD ["java", "-jar", "app.jar"]