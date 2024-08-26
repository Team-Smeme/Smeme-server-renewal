FROM bellsoft/liberica-openjdk-alpine:17
ARG JAR_FILE=/smeem-bootstrap/build/libs/smeem-bootstrap-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} smeem.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "/smeem.jar"]
