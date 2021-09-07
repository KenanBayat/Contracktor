FROM adoptopenjdk/openjdk11:x86_64-alpine-jdk11u-2021-09-03-02-30-beta-nightly-slim

COPY target/app.jar app.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "app.jar" ]