FROM openjdk:11-jre-slim

WORKDIR /app

COPY ./sshAgent/target/sshAgent-0.0.1-SNAPSHOT.jar ./app.jar

ENV JAVA_OPTS=""

CMD ["java", "-jar", "app.jar"]
