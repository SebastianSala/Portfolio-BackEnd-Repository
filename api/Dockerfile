FROM openjdk:17-ea-jdk-slim

LABEL maintainer="Sebastian Sala"

COPY target/api-3.0.0.jar api-backend.jar

ENTRYPOINT ["java","-jar","/api-backend.jar"]

EXPOSE 8080