FROM amazoncorretto:17.0.0-alpine3.14

#todo: JVM Optimization needed?

COPY target/timetable-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]