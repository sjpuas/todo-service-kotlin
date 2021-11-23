FROM gradle:7.3.0-jdk17-alpine as build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM eclipse-temurin:17.0.1_12-jdk-alpine
RUN mkdir /app
COPY --from=build /project/build/libs/todo-service-0.0.1.jar /app/todo-service-0.0.1.jar
WORKDIR /app
CMD "java" "-jar" "todo-service-0.0.1.jar"
