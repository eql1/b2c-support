FROM openjdk:20-jdk-slim
WORKDIR /app
COPY target/b2c-support-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
