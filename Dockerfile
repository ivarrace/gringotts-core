FROM maven:3.8.5-eclipse-temurin-11-alpine as maven
COPY ./pom.xml ./pom.xml
COPY ./src ./src
RUN mvn dependency:go-offline -B
RUN mvn package

FROM openjdk:11-jre-slim
MAINTAINER ivarrace
ARG JAR_FILE=target/*.jar
COPY --from=maven ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]