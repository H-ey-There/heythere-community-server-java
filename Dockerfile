FROM openjdk:11-jre-slim
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} eureka-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/video-0.0.1-SNAPSHOT.jar"]