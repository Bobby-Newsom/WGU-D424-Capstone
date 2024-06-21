# Use an official Maven image to build the application
FROM maven:3.8.4-openjdk-17-slim AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and download the dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the source code
COPY src ./src

# Install Node.js, npm, and Angular CLI
RUN apt-get update && apt-get install -y curl \
    && curl -sL https://deb.nodesource.com/setup_18.x | bash - \
    && apt-get install -y nodejs \
    && npm install -g @angular/cli

# Copy the Angular project and install dependencies
COPY src/main/UI/package.json ./src/main/UI/package.json
COPY src/main/UI/package-lock.json ./src/main/UI/package-lock.json
RUN cd src/main/UI && npm install

# Build the Angular project
RUN cd src/main/UI && ng build --prod

# Package the Spring Boot application
RUN mvn clean package -DskipTests -e -X

# Use an official OpenJDK image to run the application
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built application from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
