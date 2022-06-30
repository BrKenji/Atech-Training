FROM openjdk:12
LABEL maintainer="vhirata@atech.com.br"
VOLUME /main-app
ARG JAR_FILE=build/libs/backEnd-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
