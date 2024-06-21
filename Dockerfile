# Use the official Maven image to build the app
FROM maven:3.8.4-openjdk-17-slim AS build

# Install Node.js v18.x and Angular CLI
RUN apt-get update && \
    apt-get install -y curl && \
    curl -sL https://deb.nodesource.com/setup_18.x | bash - && \
    apt-get install -y nodejs && \
    npm install -g @angular/cli

# Set the working directory to /app
WORKDIR /app

# Copy the pom.xml file and the src directory
COPY pom.xml .
COPY src ./src

# Run the Maven build
RUN mvn clean package -DskipTests

# Use the official OpenJDK image to run the app
FROM openjdk:17-jdk-slim

# Set the working directory to /app
WORKDIR /app

# Copy the JAR file from the build stage to the run stage
COPY --from=build /app/target/D387_sample_code-0.0.2-SNAPSHOT.jar /app/app.jar

# Expose port 8080
EXPOSE 8080

# Set the entry point to run the JAR file
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
