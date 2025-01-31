FROM openjdk:17-jdk-alpine
VOLUME /tmp
EXPOSE 8080

ARG JAR_FILE=target/loan-simulator-1.0.0.jar

ADD ${JAR_FILE} app.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]