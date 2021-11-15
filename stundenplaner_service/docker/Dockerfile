FROM maven:3.8-amazoncorretto-16 as build

WORKDIR /usr/local/app
COPY . /usr/local/app/

RUN mvn clean install -P prod -DskipTests


FROM amazoncorretto:16-alpine
#todo: JVM Optimization needed?

COPY --from=build /usr/local/app/target/timetable-*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]