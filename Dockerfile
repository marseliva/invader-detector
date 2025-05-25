FROM openjdk:21-jdk-slim
WORKDIR /invaders-detector
COPY build/libs/*.jar invaders-detector.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "invaders-detector.jar"]