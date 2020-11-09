FROM openjdk:11-jre-slim
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} community-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/community-0.0.1-SNAPSHOT.jar"]