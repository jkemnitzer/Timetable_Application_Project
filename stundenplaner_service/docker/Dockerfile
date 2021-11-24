FROM maven:3.8-amazoncorretto-16 as build

ARG MVN_SETTINGS_OPTION

WORKDIR /usr/local/app
COPY . /usr/local/app/

#Install fonts for dataformat xlsx export
#RUN yum install -y freetype fontconfig dejavu-sans-fonts

RUN mvn ${MVN_SETTINGS_OPTION} clean install -P prod -DskipTests


FROM amazoncorretto:17
#todo: JVM Optimization needed?

COPY --from=build /usr/local/app/target/timetable-*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]