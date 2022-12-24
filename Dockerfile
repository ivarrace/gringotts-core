FROM openjdk:11-jre-slim
MAINTAINER ivarrace
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]