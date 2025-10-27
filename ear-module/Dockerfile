# Multi-stage build for WildFly with EAR
FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom files
COPY pom.xml .
COPY ear-module/pom.xml ./ear-module/
COPY ejb-module/pom.xml ./ejb-module/
COPY backend-module/pom.xml ./backend-module/

# Copy source code
COPY ejb-module/src ./ejb-module/src
COPY backend-module/src ./backend-module/src
COPY ear-module/src ./ear-module/src

# Install parent POM first
RUN mvn clean install -N -DskipTests

# Build EJB module
RUN cd ejb-module && mvn clean install -DskipTests

# Build WAR module
RUN cd backend-module && mvn clean install -DskipTests

# Build EAR module
RUN cd ear-module && mvn clean package -DskipTests

# Runtime stage with WildFly
FROM quay.io/wildfly/wildfly:latest

# No need to install curl, using wget instead

# Copy the EAR file to deployments
COPY --from=build /app/ear-module/target/*.ear /opt/jboss/wildfly/standalone/deployments/

# H2 driver will be provided by WildFly

# Expose ports
EXPOSE 8080 9990

# Health check using wget (already available in WildFly)
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/api/v1/beneficios || exit 1

# Start WildFly
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
