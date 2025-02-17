FROM openjdk:17-jdk-alpine
ARG JAR_FILE=*.jar
COPY target/${JAR_FILE} application.jar

EXPOSE 8080 5005


ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "application.jar"]
