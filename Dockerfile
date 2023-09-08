# Use an official Java runtime as a parent image
FROM openjdk:11-jre-slim

# Set the working directory inside the container
WORKDIR /app

# Copy your Java program JAR file to the container
COPY sshAgent/target/sshAgent-0.0.1-SNAPSHOT.jar ./app.jar

# Define any environment variables if needed (e.g., for configuration)
ENV JAVA_OPTS=""

# Specify the command to run when the container starts
CMD ["java", "-jar", "app.jar"]
