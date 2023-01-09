FROM eclipse-temurin:11-jdk-alpine AS jre-build

RUN apk add --no-cache binutils
# собираем маленький JRE-образ
RUN $JAVA_HOME/bin/jlink \
         --verbose \
         --add-modules ALL-MODULE-PATH \
         --strip-debug \
         --no-man-pages \
         --no-header-files \
         --compress=2 \
         --output /customjre

FROM alpine:latest
ENV JAVA_HOME=/jre
ENV PATH="${JAVA_HOME}/bin:${PATH}"

# копируем JRE из jre-build
COPY --from=jre-build /customjre $JAVA_HOME

# добавляем пользователя приложения
ARG APPLICATION_USER=appuser
RUN adduser --no-create-home -u 1000 -D $APPLICATION_USER

# конфигурируем рабочий каталог
RUN mkdir /app && \
    chown -R $APPLICATION_USER /app

USER 1000

COPY --chown=1000:1000 build/libs/*.jar /app/app.jar
WORKDIR /app

EXPOSE 50
ENTRYPOINT [ "/jre/bin/java", "-jar", "/app/app.jar" ]