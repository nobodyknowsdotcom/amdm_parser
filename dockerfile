# build jar with gradle
FROM gradle:7.1.0-jdk11 AS build
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY build.gradle settings.gradle $APP_HOME
COPY gradle $APP_HOME/gradle
COPY --chown=gradle:gradle . /home/gradle/src
USER root
RUN chown -R gradle /home/gradle/src

RUN gradle build || return 0
COPY . .
RUN gradle clean build

# actual container
FROM adoptopenjdk/openjdk11
ENV ARTIFACT_NAME=amdm_parser-1.0.0.jar
ENV APP_HOME=/usr/app/

WORKDIR $APP_HOME
COPY --from=build $APP_HOME/build/libs/$ARTIFACT_NAME .

EXPOSE 50
ENTRYPOINT exec java -jar ${ARTIFACT_NAME}