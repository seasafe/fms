FROM openjdk:8-jdk-alpine
RUN addgroup -S kesav && adduser -S kesav -G kesav
USER kesav:kesav
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} employee-service.jar
ENTRYPOINT ["java","-jar","/employee-service.jar"]