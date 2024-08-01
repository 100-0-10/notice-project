FROM openjdk:17-jdk
ARG JAR_FILE=build/libs/*.jar
ADD ${JAR_FILE} notice.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/notice.jar"]