FROM openjdk:8-jdk-alpine
COPY target/babyalbum*.jar app.jar
CMD java -jar ./app.jar
