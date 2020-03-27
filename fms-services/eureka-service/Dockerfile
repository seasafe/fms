FROM openjdk:8-jdk-alpine
RUN addgroup -S kesav && adduser -S kesav -G kesav
USER kesav:kesav
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} eureka.jar
ENTRYPOINT ["java","-jar","/eureka.jar"]