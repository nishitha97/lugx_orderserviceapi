# ----------- STAGE 1: Build the application -----------
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy the pom.xml and download dependencies first (leverages Docker cache)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Now copy the entire source and build the app
COPY src ./src
RUN mvn clean package -DskipTests

# ----------- STAGE 2: Run the application -----------
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy only the built jar from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose port (update if your app runs on a different port)
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
