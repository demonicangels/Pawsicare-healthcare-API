# Use the OpenJDK base image suitable for running Java applications
FROM openjdk:17-jdk-alpine AS builder

# Working directory inside the container
WORKDIR /app

# Copy the compiled JAR file into the container
COPY build/libs/PawsiCare-0.0.1-SNAPSHOT.jar /app/app.jar
COPY jwtSecretKey.env jwtSecretKey.env
COPY src/main/resources/application.properties application.properties

# Install bash
RUN apk --no-cache add bash

# Expose the port that the application runs on
EXPOSE 8080

# Command to run your application
CMD ["java", "-jar", "app.jar"]