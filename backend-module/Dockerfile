# Multi-stage build for Spring Boot Backend
FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom files
COPY pom.xml .
COPY ejb-module/pom.xml ./ejb-module/

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy source code
COPY ejb-module/src ./ejb-module/src
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre

WORKDIR /app

# Install curl for health checks
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Copy the JAR file
COPY --from=build /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
