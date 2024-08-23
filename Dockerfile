# Stage 1: Build
FROM ubuntu:latest AS build
LABEL authors="gouravmodi"

# Install OpenJDK and Maven
RUN apt-get update && apt-get install -y openjdk-17-jdk maven

# Set the working directory
WORKDIR /app

# Copy the entire project
COPY . .

# Package the application using Maven
RUN mvn clean package -DskipTests

# Stage 2: Run
FROM openjdk:17-jdk-slim

# Expose the application port
EXPOSE 8080

# Copy the jar file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
