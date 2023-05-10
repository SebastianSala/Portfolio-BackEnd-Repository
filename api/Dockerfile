FROM openjdk:17-ea-jdk-slim

LABEL maintainer="Sebastian Sala"

COPY target/api-0.3.1.jar api-backend.jar

ENTRYPOINT ["java","-jar","/api-backend.jar"]
