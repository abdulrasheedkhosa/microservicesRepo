# Start with an official lightweight OpenJDK image as base
FROM openjdk:17-jdk-slim

MAINTAINER smartfusions

COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

ENTRYPOINT["java","-jar","accounts-0.0.1-SNAPSHOT.jar"]