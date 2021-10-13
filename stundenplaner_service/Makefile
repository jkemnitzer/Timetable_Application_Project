.PHONY: run

run: target/timetable-0.0.1-SNAPSHOT.jar
	java -jar $<

target/timetable-0.0.1-SNAPSHOT.jar: $(shell find src -type f)
	mvn clean package
