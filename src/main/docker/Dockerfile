FROM openjdk:17-alpine AS jre-build
WORKDIR /app

# copy the dependencies into the docker image
COPY build/lib/* build/lib/

# copy the executable jar into the docker image
COPY build/libs/amdm_parser*.jar build/app.jar

# find JDK dependencies dynamically from jar
RUN jdeps \
# dont worry about missing modules
--ignore-missing-deps \
# suppress any warnings printed to console
-q \
# java release version targeting
--multi-release 17 \
# output the dependencies at end of run
--print-module-deps \
# specify the the dependencies for the jar
--class-path build/lib/* \
# pipe the result of running jdeps on the app jar to file
build/app.jar > jre-deps.info

EXPOSE 8050

# temp print jdeps output
ENTRYPOINT cat /app/jre-deps.info