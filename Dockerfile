FROM openjdk:8-alpine

# Undertow files created in /tmp
VOLUME /tmp

COPY target/text-transformer-1.0.0-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'

ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar" ]
