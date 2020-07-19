FROM openjdk:8-jre-alpine AS copy-prebuild-package
EXPOSE 8080
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} springboot-assignment-telenor.jar
ENTRYPOINT ["java","-jar","/usr/app/springboot-assignment-telenor.jar"]