FROM openjdk:17-jdk-slim

COPY build/libs/project-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-Dspring.profiles.active=dev", "-jar", "app.jar"]