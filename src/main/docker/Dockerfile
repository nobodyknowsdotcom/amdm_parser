# Вычленяем из jdk java runtime enviroment (jre), чтобы не тащить с собой весь jdk
FROM eclipse-temurin:11-jdk-alpine as jre-build

RUN apk add --no-cache binutils
# Собираем jre
RUN $JAVA_HOME/bin/jlink \
         --verbose \
         --add-modules ALL-MODULE-PATH \
         --strip-debug \
         --no-man-pages \
         --no-header-files \
         --compress=2 \
         --output /customjre

# Упакуем собранный jar и jre в супер легкий alpine linux и запустим приложение
FROM alpine:latest
ENV JAVA_HOME=/jre
ENV PATH="${JAVA_HOME}/bin:${PATH}"

# Копируем JRE из jre-build
COPY --from=jre-build /customjre $JAVA_HOME
COPY build/libs/*.jar /app/app.jar
WORKDIR /app

EXPOSE 8050
ENTRYPOINT [ "/jre/bin/java", "-jar", "/app/app.jar" ]