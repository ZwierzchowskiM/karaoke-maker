FROM openjdk:17-alpine
COPY target/karaokemaker-0.0.1-SNAPSHOT.jar karaokemaker.jar
ENTRYPOINT ["java", "-jar", "karaokemaker.jar"]

