FROM openjdk:latest
EXPOSE 8080
ADD ./build/libs/*.jar app.jar
ENTRYPOINT["java","-jar","app.jar"]