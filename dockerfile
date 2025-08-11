# Use a base image with Java
FROM openjdk:11-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the jar file (adjust your .jar file name accordingly)
COPY build/libs/demo-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
